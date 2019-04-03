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
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.OrderStatus
import com.trunghoang.orderhub.model.OrderStatusDef.WAITING
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.ui.orderList.OrderListFragment
import com.trunghoang.orderhub.utils.getOrderStatusFromId
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import javax.inject.Inject

class MainScreenFragment() : Fragment(), PopupMenu.OnMenuItemClickListener {
    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }

    @Inject
    lateinit var mainViewModel: MainViewModel
    private val drawerLayout: DrawerLayout by lazy {
        drawerMainScreen.apply {
            addDrawerListener( object: DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) {}

                override fun onDrawerSlide(
                    drawerView: View,
                    slideOffset: Float
                ){}

                override fun onDrawerClosed(drawerView: View) {
                    navigationMainScreen.checkedItem?: return
                    val orderStatus = context.getOrderStatusFromId(navigationMainScreen.checkedItem!!.itemId)
                    orderStatus?: return
                    if ( orderStatus != mainViewModel.orderStatus.value) {
                        getOrderListFragment(orderStatus)
                    }
                }

                override fun onDrawerOpened(drawerView: View) {}
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.supportToolbar.value = true
        initNavigationDrawer()
        getOrderListFragment(WAITING)
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_log_out -> {
            mainViewModel.removeSharedPref()
            true
        }
        else -> false
    }

    private fun initNavigationDrawer() {
        with(view?.navigationMainScreen) {
            this?.getHeaderView(0)
                ?.findViewById<ImageButton>(R.id.buttonSettings)
                ?.setOnClickListener { showSettingsMenu(it) }
            this?.setNavigationItemSelectedListener { item ->
                item.isChecked = true
                drawerLayout.closeDrawers()
                true
            }
            this?.setCheckedItem(R.id.item_status_1)
        }
    }

    private fun getOrderListFragment(@OrderStatus status: Int) {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.constraintMainContent,
                OrderListFragment.newInstance(status)
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
