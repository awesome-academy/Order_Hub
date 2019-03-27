package com.trunghoang.orderhub.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import com.trunghoang.orderhub.utils.SaveSharedPreferences
import kotlinx.android.synthetic.main.fragment_main_screen.*

class MainActivity : AppCompatActivity(),
                     MainScreenFragment.SupportToolbarCallback,
                     MainScreenFragment.LogoutCallback,
                     LoginFragment.OnLoggedInCallback {
    companion object {
        const val PREF_TOKEN = "com.trunghoang.orderhub.TOKEN"
    }

    private val drawerLayout: DrawerLayout by lazy {
        drawerMainScreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (getPreferences(Context.MODE_PRIVATE).contains(PREF_TOKEN)) {
            openMainScreenFragment()
        } else {
            openLoginFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onLoggedIn(token: String?) {
        with(getPreferences(Context.MODE_PRIVATE).edit()) {
            putString(PREF_TOKEN, token)
            apply()
        }
        SaveSharedPreferences.mToken = token
        openMainScreenFragment()
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
    }

    override fun onClickLogout() {
        with(getPreferences(Context.MODE_PRIVATE).edit()) {
            remove(PREF_TOKEN)
            apply()
        }
        SaveSharedPreferences.mToken = null
        openLoginFragment()
    }

    private fun openLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.constraint_main, LoginFragment.newInstance(this))
            .commit()
    }

    private fun openMainScreenFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.constraint_main,
                MainScreenFragment.newInstance(this, this)
            )
            .commit()
    }
}
