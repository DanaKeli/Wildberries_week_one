package com.example.wildberries_week_one.presentation

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wildberries_week_one.databinding.ActivityReceiverBinding
import com.example.wildberries_week_one.data.Receiver

class ReceiverActivity : AppCompatActivity() {

    /* Ресивер получает сообщения от системы о подключении телефона к или отключении от зарядного
    устройста и выводит соответствующие тосты.
    Так же создается и обрабатывается собственное (не системное) широкоформатное сообщение по
    нажатию на кнопку.

    Данный ресивер, интент-фильтр и экшены прописаны в манифесте (статический способ), а так же
    вызываются динамически с помощью регистрации ресивера в активити (для примера сделаны оба способа).
    Начиная с API 26 ресиверы, зарегистрированные статическим способом обрабатывают не все
    экшены, поэтому предпочтительнее использовать динамический способ.

    Примеры: - мессенджер фейсбука (сообщение "Отсутствует подключение к Интернету",
             - камера телефона не снимает видео при заряде батареи ниже 20% и выдает соотвествующее
               предупреждение.
             - яндекс карты не могут построить маршрут от Моего местоположение, если отключен gps и
               выдают сообщение "Не удалось определить местоположение".
    */

    private lateinit var binding: ActivityReceiverBinding
    private val receiver = Receiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnSendMessage.setOnClickListener {
            sendBroadcast(Intent(Receiver.SEND_MESSAGE))
        }

        val intentFilterConnected = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Receiver.SEND_MESSAGE)
        }
        registerReceiver(receiver, intentFilterConnected)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}