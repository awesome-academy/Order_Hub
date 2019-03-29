package com.trunghoang.orderhub.ui.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_main_screen.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
                     MainScreenFragment.SupportToolbarCallback,
                     MainScreenFragment.LogoutCallback,
                     LoginFragment.OnLoggedInCallback {
    @Inject
    lateinit var viewModel: MainViewModel
    private val drawerLayout: DrawerLayout by lazy {
        drawerMainScreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        viewModel.token.observe(this, Observer { token ->
            consumeToken(token)
        })
        viewModel.getSharedPref()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onLoggedIn(token: String?) {
        viewModel.saveSharedPref(token)
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
    }

    override fun onClickLogout() {
        viewModel.removeSharedPref()
    }

    private fun consumeToken(token: String?) {
        if (token == null) {
            openLoginFragment()
        } else {
            openMainScreenFragment()
        }
    }

    private fun openLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraint_main, LoginFragment.newInstance())
            .commit()
    }

    private fun openMainScreenFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.constraint_main,
                MainScreenFragment.newInstance()
            )
            .commit()
    }
}
