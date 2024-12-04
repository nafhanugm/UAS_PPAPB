package com.iqonic.phonphon_store.activity

import android.os.Bundle
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.fragments.SearchFragment
import com.iqonic.phonphon_store.utils.extensions.addFragment

class SearchActivity : AppBaseActivity() {

    private val mSearchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search2)
        addFragment(mSearchFragment, R.id.fragmentContainer)
        loadBannerAd(R.id.adView)
    }

    override fun onBackPressed() {
        super.onBackPressed()


    }
}