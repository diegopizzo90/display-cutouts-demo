package com.diegopizzo.display_cutouts_demo

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import com.diegopizzo.display_cutouts_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClickListener()
    }

    override fun onStart() {
        super.onStart()
        hideSystemBars()
    }

    private fun setButtonClickListener() {
        binding.btn.apply {
            setOnClickListener {
                renderViewComponents()
                isEnabled = false
                it.requestApplyInsets()
            }
        }
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

            //Check if cutouts intercept view component
            if (isCutoutsIntersectView(cutouts, binding.componentView)) {
                startViewRepositioning(cutouts)
            }
            windowInsetsCompat
        }
    }

    private fun startViewRepositioning(cutouts: DisplayCutoutCompat?) {
        //Change the position of view component
        val insetTop = cutouts?.safeInsetTop
        val params = binding.componentView.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = insetTop ?: 0
        binding.componentView.layoutParams = params
    }

    private fun isCutoutsIntersectView(cutouts: DisplayCutoutCompat?, view: View): Boolean {
        return cutouts?.boundingRects?.any { cutoutRect ->
            val viewRect = Rect()
            view.getHitRect(viewRect)
            cutoutRect.intersect(viewRect)
        } ?: false
    }
}