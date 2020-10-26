package com.sgsoft.facerecognizer.common.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sgsoft.facerecognizer.common.fragment.LoadingIndicatorFragment
import com.sgsoft.facerecognizer.common.presenter.IPresenter
import com.sgsoft.facerecognizer.common.view.IView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<V : IView, T : IPresenter<V>> : AppCompatActivity(),
        IView, HasFragmentInjector, HasSupportFragmentInjector {

    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    protected abstract var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.detachView()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = frameworkFragmentInjector

    override fun getContext(): Context = this

    override fun showProgress() {
        LoadingIndicatorFragment()
                .show(supportFragmentManager, LoadingIndicatorFragment::class.java.name)

    }
    override fun hideProgress() {
        val fragment =
                supportFragmentManager.findFragmentByTag(LoadingIndicatorFragment::class.java.name)

        if(fragment is DialogFragment) {
            fragment.dismissAllowingStateLoss()
        }
    }
    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
    override fun showMessage(resId: Int) {
        Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
    }
}