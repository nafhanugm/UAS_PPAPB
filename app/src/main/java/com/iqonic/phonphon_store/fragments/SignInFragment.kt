package com.iqonic.phonphon_store.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.activity.AdminActivity
import com.iqonic.phonphon_store.activity.DashBoardActivity
import com.iqonic.phonphon_store.activity.SignInUpActivity
import com.iqonic.phonphon_store.api.apiModel.entity.UserRole
import com.iqonic.phonphon_store.api.repository.UserRepository
import com.iqonic.phonphon_store.models.RequestModel
import com.iqonic.phonphon_store.utils.OTPEditText
import com.iqonic.phonphon_store.utils.extensions.*
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.layout_otp.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFragment()
    }

    private fun initializeFragment() {
        edtEmail.onFocusChangeListener = this
        edtPassword.onFocusChangeListener = this
        edtPassword.transformationMethod = BiggerDotTransformation

        edtEmail.setSelection(edtEmail.length())
        btnSignIn.onClick {
            if (validate()) {
                doLogin()
            }
        }

        tvForget.onClick {
            snackBar("Coming Soon")
        }

        btnSignUp.onClick {
            (activity as SignInUpActivity).loadSignUpFragment()
        }
        tvForget.onClick {
            showChangePasswordDialog()
        }
    }

    private fun validate(): Boolean {
        return when {
            edtEmail.checkIsEmpty() -> {
                edtEmail.showError(getString(R.string.error_field_required))
                false
            }
            !edtEmail.isValidEmail() -> {
                edtEmail.showError(getString(R.string.error_enter_valid_email))
                false
            }
            edtPassword.checkIsEmpty() -> {
                edtPassword.showError(getString(R.string.error_field_required))
                false
            }
            else -> true
        }
    }

    private fun doLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val email = edtEmail.textToString()
                val password = edtPassword.textToString()

                val result = UserRepository.signIn(email, password)
                result.onSuccess { user ->
                    if (user != null) {
                        // User ditemukan, login berhasil
                        withContext(Dispatchers.Main) {
                            // Simpan data user ke SharedPreferences atau DataStore
                            val modelRequest = RequestModel()
                            modelRequest.userEmail = email
                            modelRequest.password = password
                            modelRequest.firstName = user.firstName
                            modelRequest.lastName = user.lastName
                            (activity as? AppBaseActivity)?.registerUser(modelRequest, true)
                            signIn(user.id!!) {
                                // Jika login berhasil, lanjutkan ke halaman dashboard
                                activity?.finish()
                                if (user.role == UserRole.ADMIN) {
                                    activity?.launchActivityWithNewTask<AdminActivity>()
                                } else {
                                    activity?.launchActivityWithNewTask<DashBoardActivity>()
                                }

                            }
                        }
                    } else {
                        // User tidak ditemukan atau password salah
                        withContext(Dispatchers.Main) {
                            edtEmail.showError(getString(R.string.error_invalid_credentials))
                        }
                    }
                }.onFailure { error ->
                    withContext(Dispatchers.Main) {
                        edtEmail.showError(getString(R.string.error_something_went_wrong))
                        error.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    edtEmail.showError(getString(R.string.error_something_went_wrong))
                    e.printStackTrace()
                }
            }
        }
    }
    private var mEds: Array<EditText?>? = null

    private fun showChangePasswordDialog() {
        //if (changePasswordDialog==null){
        val changePasswordDialog = Dialog(activity!!)
        changePasswordDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        changePasswordDialog.setContentView(R.layout.dialog_change_password)
        changePasswordDialog.window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        changePasswordDialog.edtNewPassword.transformationMethod = BiggerDotTransformation
        changePasswordDialog.edtconfirmPassword.transformationMethod = BiggerDotTransformation
        mEds = arrayOf(changePasswordDialog.findViewById(R.id.edDigit1), changePasswordDialog.findViewById(R.id.edDigit2), changePasswordDialog.findViewById(R.id.edDigit3), changePasswordDialog.findViewById(R.id.edDigit4))
        OTPEditText(mEds!!, activity!!, activity?.getDrawable(R.color.transparent)!!, activity?.getDrawable(R.drawable.bg_unselected_dot)!!)
        mEds!!.forEachIndexed { _, editText ->
            editText?.onFocusChangeListener = focusChangeListener
        }
        changePasswordDialog.show()
    }
    private val focusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus)
            (v as EditText).background = activity?.getDrawable(R.color.transparent)
    }


}