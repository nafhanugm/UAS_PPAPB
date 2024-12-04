package com.iqonic.phonphon_store.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.utils.Constants
import com.iqonic.phonphon_store.utils.OTPEditText
import com.iqonic.phonphon_store.utils.extensions.*
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.layout_otp.*
import java.util.*

class OTPActivity : AppBaseActivity() {

    private var mEds: Array<EditText?>? = null
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        mEds = arrayOf(edDigit1, edDigit2, edDigit3, edDigit4)
        OTPEditText(
            mEds!!,
            this,
            getDrawable(R.color.transparent)!!,
            getDrawable(R.drawable.bg_unselected_dot)!!
        )
        mEds!!.forEachIndexed { _, editText ->
            editText?.onFocusChangeListener = focusChangeListener
        }
        timer = startOTPTimer(onTimerTick = {
            tvTimer.text = it
        }, onTimerFinished = {
            tvTimer.hide()
            llResend.show()
        })
        timer?.start()
        tvResend.onClick {
            tvTimer.show()
            llResend.hide()
            timer?.start()

        }
        ivBack.onClick {
            onBackPressed()
        }
        btnVerify.onClick {
            getSharedPrefInstance().setValue(Constants.SharedPref.IS_LOGGED_IN, true)
            getSharedPrefInstance().setValue(Constants.SharedPref.USER_ID, Random().nextInt(10000).toString())
            launchActivityWithNewTask<DashBoardActivity>()
        }
    }

    private val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus)
            (v as EditText).background = getDrawable(R.color.transparent)
    }


}