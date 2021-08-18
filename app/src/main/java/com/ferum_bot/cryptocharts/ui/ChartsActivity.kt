package com.ferum_bot.cryptocharts.ui

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ferum_bot.cryptocharts.R
import com.ferum_bot.cryptocharts.databinding.ActivityChartsBinding
import com.ferum_bot.cryptocharts.di.Injector
import com.ferum_bot.cryptocharts.di.components.ChartsComponent

class ChartsActivity : AppCompatActivity() {

    private val component: ChartsComponent by lazy(LazyThreadSafetyMode.NONE) {
        val builder = Injector.appComponent.chartsComponent
        builder.activity(this).build()
    }

    private val binding: ActivityChartsBinding by lazy {
        ActivityChartsBinding.inflate(layoutInflater)
    }

    private val viewModel: ChartsViewModel by viewModels { component.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configureBottomNavigationBar()
    }

    private fun configureBottomNavigationBar() {
        window.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = Color.BLACK
        }
    }
}