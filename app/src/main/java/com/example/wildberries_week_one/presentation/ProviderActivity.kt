package com.example.wildberries_week_one.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.wildberries_week_one.databinding.ActivityProviderBinding
import kotlin.concurrent.thread

class ProviderActivity : AppCompatActivity() {

    /* По нажатию на кнопку "Узнать количество контактов" происходит обращение к встроенному
       провайдеру Contacts, который после соотвествующего разрешения от пользователя
       с помощью курсора получает и выводит данные о количестве
       контактов в списке контактов

       Так же в манифесте создан собственный контент-провайдер, которому можно отправить запрос
       из других приложений, указав записанный в манифесте uri (authorities), и получить необходимые
       данные из бд нашего приложения

       Примеры: - Инстаграм получает доступ к фото-галерее телеофна;
                - Яндекс.Go в разделе доставка можно из списка контактов в телефоне выбрать контакт
                  адресата*/

    companion object {
        private const val READ_CONTACTS_RC = 100
    }

    private lateinit var binding: ActivityProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val permissionGranted = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.apply {

            btnGetCount.setOnClickListener {
                if (permissionGranted) {
                    getContacts()
                } else {
                    requestPermission()
                }
            }
            btnProviderQuery.setOnClickListener {
                contentResolver.query(
                    Uri.parse("content://com.example.wildberries_week_one"),
                    null, null, null, null, null
                )
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            READ_CONTACTS_RC
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_CONTACTS_RC && grantResults.isNotEmpty()) {
            val permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (permissionGranted) {
                getContacts()
            } else {
                Toast.makeText(
                    this, "Доступ к контактам отклонен пользователем",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getContacts() {
        var count = 0
        Thread(Runnable {
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null, null
            )
            while (cursor?.moveToNext() == true) count++

            runOnUiThread {
                Toast.makeText(this, "Количество контактов: $count", Toast.LENGTH_SHORT)
                    .show()
            }
            cursor?.close()
        }).start()
    }
}