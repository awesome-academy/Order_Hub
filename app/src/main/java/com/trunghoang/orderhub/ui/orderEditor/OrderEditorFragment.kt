package com.trunghoang.orderhub.ui.orderEditor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Named

class OrderEditorFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = OrderEditorFragment()
    }
    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var mainViewModel: MainViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.toolbarInfo.value = ToolbarInfo(
            R.id.toolbarOrderEditor,
            R.drawable.ic_close,
            R.string.order_editor_new_order_title,
            R.menu.menu_editor
        )
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}
