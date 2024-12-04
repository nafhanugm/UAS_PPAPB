package com.iqonic.phonphon_store.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iqonic.phonphon_store.AppBaseActivity
import com.iqonic.phonphon_store.R
import com.iqonic.phonphon_store.activity.OTPActivity
import com.iqonic.phonphon_store.activity.SignInUpActivity
import com.iqonic.phonphon_store.api.repository.UserRepository
import com.iqonic.phonphon_store.models.RequestModel
import com.iqonic.phonphon_store.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFragment()
    }

    private fun initializeFragment() {
        edtEmail.onFocusChangeListener = this
        edtPassword.onFocusChangeListener = this
        edtConfirmPassword.onFocusChangeListener = this
        edtFirstName.onFocusChangeListener = this
        edtLastName.onFocusChangeListener = this
        edtPassword.transformationMethod = BiggerDotTransformation
        edtConfirmPassword.transformationMethod = BiggerDotTransformation

        btnSignUp.onClick {
            when {
                validate() -> {
                    onSignUpClick()
                }
            }
        }
        btnSignIn.onClick {
            (activity as SignInUpActivity).loadSignInFragment()
        }
    }

    private suspend fun createCustomer() {
        val requestModel = RequestModel().apply {
            userEmail = edtEmail.textToString()
            firstName = edtFirstName.textToString()
            lastName = edtLastName.textToString()
            password = edtPassword.textToString()
        }

        try {
            val result = UserRepository.createUser(requestModel)
            result.onSuccess {
                withContext(Dispatchers.Main) {
                    (activity as? AppBaseActivity)?.registerUser(requestModel, false)
                    activity?.launchActivity<OTPActivity>()
                    (activity as? SignInUpActivity)?.loadSignInFragment()
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    edtEmail.showError(getString(R.string.error_something_went_wrong))
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                edtEmail.showError(getString(R.string.error_something_went_wrong))
            }
        }
    }

    private suspend fun isUserExist(email: String): Boolean {
        return try {
            val result = UserRepository.getUserByEmail(email)
            result.getOrNull() != null
        } catch (e: Exception) {
            false
        }
    }

    private fun onSignUpClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            checkUserExist()
        }
    }

    // Atau untuk mengecek user exist
    private fun checkUserExist() {
        val email = edtEmail.textToString()
        viewLifecycleOwner.lifecycleScope.launch {
            val exists = isUserExist(email)
            if (exists) {
                edtEmail.showError(getString(R.string.error_user_already_exists))
            } else {
                // lanjut proses signup
                createCustomer()
            }
        }
    }

    private fun validate(): Boolean {
        return when {
            edtFirstName.checkIsEmpty() -> {
                edtFirstName.showError(getString(R.string.error_field_required))
                false
            }
            edtLastName.checkIsEmpty() -> {
                edtLastName.showError(getString(R.string.error_field_required))
                false
            }
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
            edtConfirmPassword.checkIsEmpty() -> {
                edtConfirmPassword.showError(getString(R.string.error_field_required))
                false
            }
            !edtPassword.text.toString().equals(edtConfirmPassword.text.toString(), false) -> {
                edtConfirmPassword.showError(getString(R.string.error_password_not_matches))
                false
            }
            else -> true
        }
    }
}