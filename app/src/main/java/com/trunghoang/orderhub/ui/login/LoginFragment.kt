package com.trunghoang.orderhub.ui.login


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.EnumStatus
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var viewModel: LoginViewModel

    private val loginButton: Button by lazy {
        view!!.findViewById<Button>(R.id.button_login)
    }

    private val loginProgress: ProgressBar by lazy {
        view!!.findViewById<ProgressBar>(R.id.progress_loading)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        viewModel.loginResponse.observe(this, Observer { apiResponse ->
            consumeResponse(apiResponse)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val emailInput = view.findViewById<EditText>(R.id.edit_email)
        val passwordInput = view.findViewById<EditText>(R.id.edit_password)
        loginButton.setOnClickListener { v ->
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
        loginButton.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        loginProgress.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }

    private fun showSuccess(res: APIResponse<String>) {
        showLoading(false)
        Toast.makeText(
            this.context,
            getString(
                if (res.data == APIResponse.NO_VALUE) {
                    R.string.login_fail_text
                } else {
                    R.string.login_success_text
                }
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showError(res: APIResponse<String>) {
        showLoading(false)
        Toast.makeText(
            this.context,
            getString(R.string.error_general),
            Toast.LENGTH_SHORT
        ).show()
    }
}
