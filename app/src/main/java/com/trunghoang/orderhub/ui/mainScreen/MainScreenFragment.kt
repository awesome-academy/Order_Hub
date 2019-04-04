package com.trunghoang.orderhub.ui.mainScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.ui.orderList.OrderListFragment
import com.trunghoang.orderhub.utils.EventWrapper
import com.trunghoang.orderhub.utils.getOrderStatusFromId
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import javax.inject.Inject
import javax.inject.Named

class MainScreenFragment() : Fragment(),
                             PopupMenu.OnMenuItemClickListener,
                             HasSupportFragmentInjector {
    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }

    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var mainViewModel: MainViewModel
    @Inject
    @field:Named(MainScreenViewModel.NAME)
    lateinit var mainScreenViewModel: MainScreenViewModel
    private var drawerLayout: DrawerLayout? = null

    override fun supportFragmentInjector() = dispatchingFragmentInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNavigationDrawer()
        initDrawerLayout()
        mainScreenViewModel.orderStatusEvent.observe(this, Observer {
            consumeOrderStatus(it)
        })
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_log_out -> {
            mainViewModel.removeSharedPref()
            true
        }
        else -> false
    }

    private fun initDrawerLayout() {
        drawerLayout = drawerMainScreen.apply {
            addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) {}

                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ) {
                }

                override fun onDrawerClosed(drawerView: View) {
                    navigationMainScreen.checkedItem ?: return
                    context.getOrderStatusFromId(navigationMainScreen.checkedItem!!.itemId)
                        ?.let {
                            if (it != mainScreenViewModel.orderStatusEvent.value?.peekContent()) {
                                mainScreenViewModel.orderStatusEvent.value =
                                    EventWrapper(it)
                            }
                        }
                }

                override fun onDrawerOpened(drawerView: View) {}
            })
        }
    }

    private fun initNavigationDrawer() {
        with(view?.navigationMainScreen) {
            this?.getHeaderView(0)
                ?.findViewById<ImageButton>(R.id.buttonSettings)
                ?.setOnClickListener { showSettingsMenu(it) }
            this?.setNavigationItemSelectedListener { item ->
                item.isChecked = true
                drawerLayout?.closeDrawers()
                true
            }
            this?.setCheckedItem(R.id.item_status_1)
        }
    }

    private fun consumeOrderStatus(orderStatusEvent: EventWrapper<Int>?) {
        orderStatusEvent?.getContentIfNotHandled()?.apply {
            getOrderListFragment()
        }
    }

    private fun getOrderListFragment() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.constraintMainContent,
                OrderListFragment.newInstance()
            )
            .commit()
    }

    private fun showSettingsMenu(view: View) {
        if (context == null) return
        PopupMenu(context!!, view).apply {
            setOnMenuItemClickListener(this@MainScreenFragment)
            inflate(R.menu.menu_settings)
            show()
        }
    }
}
