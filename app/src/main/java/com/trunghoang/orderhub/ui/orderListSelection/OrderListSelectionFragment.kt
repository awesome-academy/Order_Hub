package com.trunghoang.orderhub.ui.orderListSelection

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.utils.EventWrapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_selection.*
import javax.inject.Inject
import javax.inject.Named

class OrderListSelectionFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = OrderListSelectionFragment()
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
        return inflater.inflate(
            R.layout.fragment_order_selection,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        measureBottom()
        mainViewModel.toolbarInfo.value = ToolbarInfo(
            R.id.toolbarOrderListSelection,
            R.drawable.ic_back,
            null,
            R.menu.menu_selection
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.orderListSelectionEvent.value = EventWrapper(false)
        mainViewModel.bottomBarHeight.value = 0
    }

    private fun measureBottom() {
        constraintBottom.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    mainViewModel.bottomBarHeight.value =
                        constraintBottom.measuredHeight
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        constraintBottom.viewTreeObserver.removeOnGlobalLayoutListener(
                            this
                        )
                    } else {
                        constraintBottom.viewTreeObserver.removeGlobalOnLayoutListener(
                            this
                        )
                    }
                }
            })
    }
}
