package com.sgsoft.facerecognizer.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sgsoft.facerecognizer.R
import com.sgsoft.facerecognizer.common.activity.BaseActivity
import com.sgsoft.facerecognizer.ui.tab.CelebrityFaceTabFragment
import com.sgsoft.facerecognizer.ui.tab.FaceTabFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
    @Inject
    override lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Check Permission
        checkPermission()

        // Initializing the TabLayout
        tab_layout.addTab(tab_layout.newTab().setText(R.string.tab_face))
        tab_layout.addTab(tab_layout.newTab().setText(R.string.tab_celebrity_face))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        // Creating TabPagerAdapter
        val adapter = TabPagerAdapter(supportFragmentManager, tab_layout.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab?.position ?: 0
            }
        })
    }

    private fun checkPermission() {
        TedPermission.with(this)
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {

                    }
                    override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                        finish()
                    }
                })
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }

    fun onClickLogo() {
        val url = getString(R.string.naver_developers_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        startActivity(intent)
    }

    class TabPagerAdapter(fm: FragmentManager, private val tabCount: Int) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FaceTabFragment()
                1 -> CelebrityFaceTabFragment()
                else -> Fragment()
            }
        }

        override fun getCount(): Int {
            return tabCount
        }
    }
}