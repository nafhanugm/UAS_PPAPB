package com.iqonic.phonphon_store.activity

import android.os.Bundle
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.fragments.ProfileFragment
import com.iqonic.phonphon_store.utils.extensions.addFragment
import kotlinx.android.synthetic.main.toolbar.*

class EditProfileActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setToolbar(toolbar)
        title=getString(R.string.lbl_edit_profile)
        addFragment(ProfileFragment(),R.id.profilecontainer)
        loadBannerAd(R.id.adView)

    }
}
