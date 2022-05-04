package com.example.wildberries_week_one.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wildberries_week_one.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnService.setOnClickListener {
                startActivity(Intent(this@MainActivity, ServiceActivity::class.java))
            }
             btnProvider.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProviderActivity::class.java))
            }
             btnReceiver.setOnClickListener {
                startActivity(Intent(this@MainActivity, ReceiverActivity::class.java))
            }

        }
    }
}