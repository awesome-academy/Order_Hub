package com.trunghoang.orderhub.ui.mainActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.EditorEvent
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.ui.EditorFragment
import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorFragment
import com.trunghoang.orderhub.utils.EventWrapper
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main_screen.*
import java.sql.Ref
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity(),
                     HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var viewModel: MainViewModel
    private val drawerLayout: DrawerLayout by lazy {
        drawerMainScreen
    }

    override fun supportFragmentInjector() = dispatchingFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(viewModel) {
            tokenEvent.observe(this@MainActivity, Observer { tokenEvent ->
                consumeToken(tokenEvent)
            })
            toolbarInfo.observe(this@MainActivity, Observer {
                consumeSupportToolbar(it)
            })
            orderEditorEvent.observe(this@MainActivity, Observer {
                consumeOrderEditor(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        viewModel.toolbarInfo.value?.menuId?.let {
            menuInflater.inflate(it, menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            when (viewModel.toolbarInfo.value?.id) {
                R.id.toolbarMainScreen -> drawerLayout.openDrawer(GravityCompat.START)
                R.id.toolbarOrderEditor -> supportFragmentManager.popBackStack()
            }
            true
        }
        R.id.itemSave -> {
            getCurrentMainFragment()?.saveData()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun consumeSupportToolbar(toolbars: ToolbarInfo?) {
        toolbars?.apply {
            id?.let {
                setSupportActionBar(findViewById(it))
            }
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                homeIcon?.let { setHomeAsUpIndicator(it) }
                titleId?.let { setTitle(it) }
            }
        }
    }

    private fun consumeToken(tokenEvent: EventWrapper<String>?) {
        tokenEvent?.getContentIfNotHandled()?.apply {
            when (this) {
                MainViewModel.NO_STRING -> openLoginFragment()
                else -> openMainScreenFragment()
            }
        }
    }

    private fun consumeOrderEditor(orderEditorEvent: EventWrapper<EditorEvent>?) {
        orderEditorEvent?.getContentIfNotHandled()?.apply {
            openOrderEditorFragment(id, editMode)
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

    private fun openOrderEditorFragment(id: String, editMode: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.constraint_main,
                OrderEditorFragment.newInstance(id, editMode)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun getCurrentMainFragment() =
        supportFragmentManager.findFragmentById(R.id.constraint_main) as? EditorFragment
}
