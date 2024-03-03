package com.syahrul.danamonfrontendtest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.syahrul.danamonfrontendtest.databinding.ActivityHomeBinding
import com.syahrul.danamonfrontendtest.fragment.AdminFragment
import com.syahrul.danamonfrontendtest.fragment.PhotosFragment
import com.syahrul.danamonfrontendtest.fragment.ProfileFragment
import com.syahrul.danamonfrontendtest.viewmodel.UserViewModel
import com.syahrul.danamonfrontendtest.model.Result

class HomeActivity : BaseViewBindingActivity<ActivityHomeBinding>() {

    companion object {
        fun launchIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private val fragments = arrayListOf<Pair<Fragment, String>>()

    private lateinit var userViewModel: UserViewModel

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = getViewModel()
        userViewModel.getLoginSessionResult().observe(this, Observer {
            when(it){
                is Result.Success -> {
                    val currentUser = it.data
                    val isAdmin = currentUser.role == 1

                    fragments.add(Pair(PhotosFragment.instantiate(), "Photos"))
                    if(isAdmin){
                        fragments.add(Pair(AdminFragment.instantiate(), "Admin"))
                    }
                    fragments.add(Pair(ProfileFragment.instantiate(), "Profile"))

                    setUpTabs()
                }
                is Result.Error -> { }
                else -> { }
            }
        })

        userViewModel.getLoginSession()

    }

    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragments[position].second
        }.attach()
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int): Fragment {
            return fragments[position].first
        }

    }

}