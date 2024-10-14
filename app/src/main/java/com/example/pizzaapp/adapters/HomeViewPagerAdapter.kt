package com.example.pizzaapp.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pizzaapp.ui.BaseCategoryFragment
import com.example.pizzaapp.ui.FishFragment

class HomeViewPagerAdapter(
    fragmentActivity:FragmentActivity
) :FragmentStateAdapter(fragmentActivity){
    private val categoriesFragments = arrayListOf<Fragment>(
        BaseCategoryFragment(),
        FishFragment()
    )

    override fun getItemCount(): Int {
        return categoriesFragments.size
    }


    override fun createFragment(position: Int): Fragment {
        Log.d("HOMEPAGEADAPTER"," CREATE FRAGMENT")
        return categoriesFragments[position]
    }
}