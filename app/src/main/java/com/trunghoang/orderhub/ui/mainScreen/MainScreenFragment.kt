package com.trunghoang.orderhub.ui.mainScreen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.trunghoang.orderhub.R
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import kotlinx.android.synthetic.main.toolbar_main_screen.view.*

class MainScreenFragment() : Fragment(), PopupMenu.OnMenuItemClickListener {
    companion object {
        @JvmStatic
        fun newInstance(
            supportToolbarCallback: SupportToolbarCallback,
            logoutCallback: LogoutCallback
        ) = MainScreenFragment().apply {
            this.mSupportToolbarCallback = supportToolbarCallback
            this.mLogoutCallback = logoutCallback
        }
    }

    lateinit var mContext: Context
    var mSupportToolbarCallback: SupportToolbarCallback? = null
    var mLogoutCallback: LogoutCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSupportToolbarCallback?.setToolbar(view.toolbarMainScreen)
        view.navigationMainScreen
            ?.getHeaderView(0)
            ?.findViewById<ImageButton>(R.id.buttonSettings)
            ?.setOnClickListener { showSettingsMenu(it) }
    }

    override fun onMenuItemClick(item: MenuItem) = when (item.itemId) {
        R.id.item_log_out -> {
            mLogoutCallback?.onClickLogout()
            true
        }
        else -> false
    }

    private fun showSettingsMenu(view: View) {
        PopupMenu(mContext, view).apply {
            setOnMenuItemClickListener(this@MainScreenFragment)
            inflate(R.menu.menu_settings)
            show()
        }
    }

    interface SupportToolbarCallback {
        fun setToolbar(toolbar: Toolbar)
    }

    interface LogoutCallback {
        fun onClickLogout()
    }
}
