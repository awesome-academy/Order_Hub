package com.trunghoang.orderhub.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModel: LoginViewModel
    var onLoggedInCallback: OnLoggedInCallback? = null
    private val loginButton: Button? by lazy {
        view?.buttonLogin
    }
    private val loginProgress: ProgressBar? by lazy {
        view?.progressLoading
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        onLoggedInCallback = if (context is OnLoggedInCallback) context else null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loginResponse.observe(this, Observer { apiResponse ->
            consumeResponse(apiResponse)
        })
        val emailInput = view.editEmail
        val passwordInput = view.editPassword
        loginButton?.setOnClickListener { v ->
            viewModel.authenticate(
                emailInput.text.toString(),
                passwordInput.text.toString()
            )
        }
    }

    private fun consumeResponse(res: APIResponse<String>) {
        when (res.status) {
            EnumStatus.LOADING -> showLoading(true)
            EnumStatus.SUCCESS -> showSuccess(res)
            EnumStatus.ERROR -> showError(res)
        }
    }

    private fun showLoading(loading: Boolean) {
        loginButton?.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        loginProgress?.visibility =
            if (loading) View.VISIBLE else View.INVISIBLE
    }

    private fun showSuccess(res: APIResponse<String>) {
        showLoading(false)
        context?.toast(
            getString(
                if (res.data == APIResponse.NO_VALUE) {
                    R.string.login_fail_text
                } else {
                    R.string.login_success_text
                }
            )
        )
        if (res.data != APIResponse.NO_VALUE)
            onLoggedInCallback?.onLoggedIn(res.data)
    }

    private fun showError(res: APIResponse<String>) {
        showLoading(false)
        context?.toast(getString(R.string.error_general))
    }

    interface OnLoggedInCallback {
        fun onLoggedIn(token: String?)
    }
}
