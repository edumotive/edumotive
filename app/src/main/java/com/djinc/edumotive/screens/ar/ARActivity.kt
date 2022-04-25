package com.djinc.edumotive.screens.ar

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.djinc.edumotive.R
import io.github.sceneview.utils.doOnApplyWindowInsets
import io.github.sceneview.utils.setFullScreen

class ARActivity : AppCompatActivity(R.layout.activity_ar) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val params = intent.extras

        setFullScreen(
            fullScreen = true,
            hideSystemBars = true,
            fitsSystemWindows = false,
            rootView = findViewById(R.id.rootView)
        )

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar)?.apply {
            doOnApplyWindowInsets { systemBarsInsets ->
                (layoutParams as ViewGroup.MarginLayoutParams).topMargin = systemBarsInsets.top
            }
            title = ""
        })

        if (params != null) {
            val arguments = Bundle()
            arguments.putString("type", params.getString("type"))
            arguments.putString("id", params.getString("id"))
            supportFragmentManager.commit {
                add(R.id.containerFragment, ARFragment::class.java, arguments)
            }
        }
    }
}
