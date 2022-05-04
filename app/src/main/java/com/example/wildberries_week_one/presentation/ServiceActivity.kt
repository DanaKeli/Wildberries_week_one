package com.example.wildberries_week_one.presentation

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.wildberries_week_one.R
import com.example.wildberries_week_one.data.ForegroundService
import com.example.wildberries_week_one.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    /*  При клике на кнопку Play запускается foreground service, который включает MP3 плеер.
        Сервис продолжает воспроизводить аудио даже при закрытом приложении (разрушенной активити).

        Примеры: - Гугл диск. Download Service управляет закрузками в фоновом режиме и выдает
        пользователю соответствующие нотификации.
        - Гугл календарь, вероятно, использует Alarm Service для отправки пользователю напоминаний
        о событиях*/

    private lateinit var binding: ActivityServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnPlay.setOnClickListener {
            ContextCompat.startForegroundService(this, ForegroundService.newIntent(this))
        }
        binding.btnPause.setOnClickListener {
            pauseSound()
        }
    }

    private fun pauseSound() {
        if (ForegroundService.mediaPlayer?.isPlaying == true) ForegroundService.mediaPlayer?.pause()
    }
}
