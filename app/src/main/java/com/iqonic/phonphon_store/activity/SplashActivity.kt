package com.iqonic.phonphon_store.activity

import android.os.Bundle
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.utils.extensions.launchActivity
import com.iqonic.phonphon_store.utils.extensions.runDelayed
import android.view.WindowManager

class SplashActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_new)
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        runDelayed(500) {
            launchActivity<WalkThroughActivity>(); onBackPressed()
        }
    }
}