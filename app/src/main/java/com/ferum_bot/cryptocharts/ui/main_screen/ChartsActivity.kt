package com.ferum_bot.cryptocharts.ui.main_screen

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.postDelayed
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.databinding.ActivityChartsBinding
import com.ferum_bot.cryptocharts.di.Injector
import com.ferum_bot.cryptocharts.di.components.ChartsComponent
import com.ferum_bot.cryptocharts.ui.recycler.MainAdapter
import com.ferum_bot.cryptocharts.ui.recycler.MarginDecorator
import com.google.android.material.snackbar.Snackbar
import javax.inject.Singleton

@Singleton
class ChartsActivity : AppCompatActivity() {

    private val component: ChartsComponent by lazy {
        val builder = Injector.appComponent.chartsComponent
        builder.activity(this).build()
    }

    private val binding: ActivityChartsBinding by lazy {
        ActivityChartsBinding.inflate(layoutInflater)
    }

    private val viewModel: ChartsViewModel by viewModels { component.viewModelFactory }

    private val adapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAllObservers()
        setAllClickListeners()
        configureLayout()
    }

    override fun onDestroy() {
        super.onDestroy()

        disconnectFromSocket()
    }

    private fun setAllObservers() {
        viewModel.connectionStatus.observe(this) { status ->
            when(status) {
                SocketConnectionStatus.CONNECTING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorLabel.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.mainRecycler.visibility = View.GONE
                }
                SocketConnectionStatus.CONNECTED -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLabel.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.mainRecycler.visibility = View.VISIBLE
                }
                SocketConnectionStatus.DISCONNECTED, null -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLabel.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.mainRecycler.visibility = View.GONE
                }
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            message ?: return@observe
            showError(message)
        }

        viewModel.currentTickers.observe(this) { tickers ->
            adapter.items = tickers
            scrollRecyclerToTop()
        }
    }

    private fun setAllClickListeners() {
        binding.retryButton.setOnClickListener {
            viewModel.reconnect()
        }
    }

    private fun configureLayout() {
        configureBottomNavigationBar()
        enableFullScreen()
        configureRecycler()
    }

    private fun configureBottomNavigationBar() {
        window.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = Color.BLACK
        }
    }

    private fun enableFullScreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun configureRecycler() {
        val marginDecorator = MarginDecorator(
            leftMargin = 8, rightMargin = 8,
            spaceBetweenItems = 16
        )

        binding.mainRecycler.adapter = adapter
        binding.mainRecycler.addItemDecoration(marginDecorator)
    }

    private fun disconnectFromSocket() {
        viewModel.disconnect()
    }

    private fun showError(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(Color.RED)
            setTextColor(Color.BLACK)
            animationMode = Snackbar.ANIMATION_MODE_SLIDE

            val textView = this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    private fun scrollRecyclerToTop() {
        binding.mainRecycler.postDelayed(350L) {
            binding.mainRecycler.smoothScrollToPosition(0)
        }
    }
}