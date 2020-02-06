package com.okurchenko.weatherdemoapplication.ui.main.fragments

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.okurchenko.weatherdemoapplication.R
import com.okurchenko.weatherdemoapplication.utils.NetworkConnectionListener
import com.okurchenko.weatherdemoapplication.utils.SnackBarBuilder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var networkUtilsListener: NetworkConnectionListener
    private lateinit var snackBar: Snackbar

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    protected open fun <T : ViewDataBinding> bindContentView(
        layoutInflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        parent: ViewGroup?
    ): T {
        return DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
    }

    fun showSnackBar(snackBarBuilder: SnackBarBuilder) {
        activity?.findViewById<FrameLayout>(android.R.id.content)?.let {
            snackBar = Snackbar.make(it, snackBarBuilder.text, snackBarBuilder.duration)
            if (snackBarBuilder.actionText != null && snackBarBuilder.actionListener != null) {
                snackBar.setAction(snackBarBuilder.actionText, snackBarBuilder.actionListener)
            }
            if (snackBarBuilder.icon != SnackBarBuilder.emptyIcon) {
                val textView =
                    snackBar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setCompoundDrawablesWithIntrinsicBounds(snackBarBuilder.icon, 0, 0, 0)
                textView.compoundDrawablePadding = resources.getDimensionPixelOffset(R.dimen.minimal_indent)
                textView.gravity = Gravity.CENTER_VERTICAL
            }
            snackBar.show()
        }
    }

    fun safeDismissSnackBar() {
        if (::snackBar.isInitialized) {
            snackBar.dismiss()
        }
    }
}

interface HasSupportFragmentInjector {
    fun supportFragmentInjector(): AndroidInjector<Fragment>
}

