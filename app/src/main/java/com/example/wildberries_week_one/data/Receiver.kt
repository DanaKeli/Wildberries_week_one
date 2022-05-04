package com.example.wildberries_week_one.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Receiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        when (p1?.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(p0, "Подключено зарядное устройство", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Toast.makeText(p0, "Зарядное утройство отключено", Toast.LENGTH_SHORT).show()
            }
            SEND_MESSAGE -> {
                Toast.makeText(p0, "Широкоформатное сообщение отправлено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val SEND_MESSAGE = "sent"
    }
}