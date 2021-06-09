package com.nerva.io.fruitidentifierapp.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nerva.io.fruitidentifierapp.R
import com.nerva.io.fruitidentifierapp.view.fragment.Fragment_Fruit
import com.nerva.io.fruitidentifierapp.view.fragment.Fragment_Scanner

class SectionPagerAdapter (private val mContext : Context, fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object {
        private val TABTITLE = intArrayOf(R.string.scanner, R.string.fruit)
    }

    override fun getItem(position: Int): Fragment =
        when(position){
            0 -> Fragment_Scanner()
            1 -> Fragment_Fruit()
            else -> Fragment()
        }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TABTITLE[position])
    }
}