package com.sgsoft.facerecognizer.common.fragment

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseDialogFragment<in V : IView, T : IPresenter<V>> : AppCompatDialogFragment(),
        HasSupportFragmentInjector, IView {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected abstract var mPresenter: T

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        @Suppress("UNCHECKED_CAST")
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.detachView()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun getContext(): Context = requireActivity()

    override fun showProgress() {
        LoadingIndicatorFragment()
                .show(activity?.supportFragmentManager!!, LoadingIndicatorFragment::class.java.name)

    }
    override fun hideProgress() {
        val fragment =
                activity?.supportFragmentManager?.findFragmentByTag(LoadingIndicatorFragment::class.java.name)

        if(fragment is DialogFragment) {
            fragment.dismissAllowingStateLoss()
        }
    }
    override fun showMessage(message: String) {
        Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    override fun showMessage(resId: Int) {
        Toast.makeText(activity?.applicationContext, resId, Toast.LENGTH_SHORT).show()
    }
}