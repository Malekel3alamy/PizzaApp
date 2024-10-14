package com.example.pizzaapp.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pizzaapp.ui.categories.AmericanFragment
import com.example.pizzaapp.ui.categories.BaseCategoryFragment
import com.example.pizzaapp.ui.categories.ChineseFragment
import com.example.pizzaapp.ui.categories.FishFragment
import com.example.pizzaapp.ui.categories.FrenchFragment
import com.example.pizzaapp.ui.categories.IndianFragment
import com.example.pizzaapp.ui.categories.ItalianFragment
import com.example.pizzaapp.ui.categories.JapaneseFragment
import com.example.pizzaapp.ui.categories.KitchenFragment
import com.example.pizzaapp.ui.categories.MediterraneanFragment
import com.example.pizzaapp.ui.categories.MexicanFragment

class HomeViewPagerAdapter(
    fragmentActivity:FragmentActivity
) :FragmentStateAdapter(fragmentActivity){
    private val categoriesFragments = arrayListOf<Fragment>(
        FishFragment() ,
        KitchenFragment(),
        ItalianFragment() ,
        MexicanFragment(),
        ChineseFragment(),
        JapaneseFragment(),
        AmericanFragment(),
        IndianFragment(),
        FrenchFragment(),
        MediterraneanFragment()

    )

    override fun getItemCount(): Int {
        return categoriesFragments.size
    }


    override fun createFragment(position: Int): Fragment {
        Log.d("HOMEPAGEADAPTER"," CREATE FRAGMENT")
        return categoriesFragments[position]
    }
}