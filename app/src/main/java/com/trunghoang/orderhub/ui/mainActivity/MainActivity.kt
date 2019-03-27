package com.trunghoang.orderhub.ui.mainActivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.OrderStatus
import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import com.trunghoang.orderhub.utils.getOrderStatusText
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main_screen.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
                     HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModel: MainViewModel
    private val drawerLayout: DrawerLayout by lazy {
        drawerMainScreen
    }

    override fun supportFragmentInjector() = dispatchingFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        with(viewModel) {
            token.observe(this@MainActivity, Observer { token ->
                consumeToken(token)
            })
            orderStatus.observe(this@MainActivity, Observer {
                consumeOrderStatus(it)
            })
            supportToolbar.observe(this@MainActivity, Observer {
                consumeSupportToolbar()
            })
            getSharedPref()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun consumeSupportToolbar() {
        setSupportActionBar(findViewById(R.id.toolbarMainScreen))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
    }

    private fun consumeToken(token: String?) {
        if (token == null) {
            openLoginFragment()
        } else {
            openMainScreenFragment()
        }
    }

    private fun consumeOrderStatus(@OrderStatus status: Int) {
        supportActionBar?.title = applicationContext.getOrderStatusText(status)
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
