package com.iqonic.phonphon_store.activity

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.api.repository.UserRepository
import com.iqonic.phonphon_store.utils.extensions.*
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.launch

class AccountActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setToolbar(toolbar)
        title = getString(R.string.title_account)

        txtDisplayName.text = getUserFullName()

        btnSignOut.onClick {
            val dialog = getAlertDialog(getString(R.string.lbl_logout_confirmation), onPositiveClick = { _, _ ->
                clearLoginPref()
                launchActivityWithNewTask<DashBoardActivity> ()
            }, onNegativeClick = { dialog, _ ->
                dialog.dismiss()
            })
            dialog.show()
        }

        btnDeleteAcc.onClick {
            var isClicked = false
            val dialog = getAlertDialog(getString(R.string.lbl_delete_account_confirmation), onPositiveClick = { _, _ ->
                if (!isClicked) {
                    isClicked = true
                    lifecycleScope.launch {
                        val result = UserRepository.deleteUser(getUserEmail())
                        if (result.isSuccess) {
                            clearLoginPref()
                            launchActivityWithNewTask<DashBoardActivity> ()
                        } else {
                            snackBar(result.exceptionOrNull()?.message ?: getString(R.string.msg_something_went_wrong))
                        }
                    }
                }
            }, onNegativeClick = { dialog, _ ->
                dialog.dismiss()
            })

            dialog.show()

        }

        tvOrders.onClick {
            launchActivity<OrderActivity>()
        }
        tvQuickPay.onClick {
            launchActivity<QuickPayActivity>()
        }
        tvOffer.onClick {
            launchActivity<OfferActivity>()
        }
        btnVerify.onClick {
            launchActivity<OTPActivity>()
        }
        tvAddressManager.onClick {
            if (getAddressList().size == 0) {
                launchActivity<AddAddressActivity>()
            } else {
                launchActivity<AddressManagerActivity>()
            }
        }
        ivProfileImage.onClick {
            launchActivity<EditProfileActivity> ()
        }
        tvWishlist.onClick {
            setResult(Activity.RESULT_OK)
            finish()
        }
        tvHelpCenter.onClick {
            launchActivity<HelpActivity>()
        }
        loadBannerAd(R.id.adView)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}