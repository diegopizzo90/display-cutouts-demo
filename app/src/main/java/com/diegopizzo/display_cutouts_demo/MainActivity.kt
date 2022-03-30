package com.diegopizzo.display_cutouts_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.diegopizzo.display_cutouts_demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        binding = ActivityMainBinding.inflate(layoutInflater)
        renderViewComponents()
        setContentView(binding.root)
    }

    private fun hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun renderViewComponents() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsetsCompat ->
            //Get DisplayCutouts object with all info needed to draw/reposition view components
            val cutouts = windowInsetsCompat.displayCutout

            //Pass rect list information in order to draw rectangles around cutouts
            binding.cutoutsBorder.rectList = cutouts?.boundingRects

            //Get the top cutouts height and apply it into layout params for circle view component repositioning
            val insetTop = cutouts?.safeInsetTop
            val params = binding.componentView.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = insetTop ?: 0
            binding.componentView.layoutParams = params
            windowInsetsCompat
        }
    }
}