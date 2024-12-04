package com.iqonic.phonphon_store.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.fragments.ViewAllProductFragment
import com.iqonic.phonphon_store.utils.Constants
import com.iqonic.phonphon_store.utils.extensions.*
import kotlinx.android.synthetic.main.toolbar.*

class ViewAllProductActivity : AppBaseActivity() {

    private var mFragment: ViewAllProductFragment? = null

    private val mCartCountChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (mFragment != null) {
                mFragment!!.setCartCount()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        setToolbar(toolbar)
        registerCartCountChangeReceiver(mCartCountChangeReceiver)

        title = intent.getStringExtra(Constants.KeyIntent.TITLE)
        val mViewAllId = intent.getIntExtra(Constants.KeyIntent.VIEWALLID, 0)
        val mCategoryId = intent.getStringExtra(Constants.KeyIntent.KEYID)

        mFragment = ViewAllProductFragment.getNewInstance(mViewAllId, mCategoryId)
        addFragment(mFragment!!, R.id.fragmentContainer)
        loadBannerAd(R.id.adView)

    }


    override fun onDestroy() {
        unregisterReceiver(mCartCountChangeReceiver)
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}