package com.nerva.io.fruitidentifierapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nerva.io.fruitidentifierapp.R
import com.nerva.io.fruitidentifierapp.databinding.ActivityMainBinding
import com.nerva.io.fruitidentifierapp.view.adapter.SectionPagerAdapter
import com.nerva.io.fruitidentifierapp.view.fragment.Fragment_Fruit
import com.nerva.io.fruitidentifierapp.view.fragment.Fragment_Scanner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainActivity.root)
        val ScannerFragment = Fragment_Scanner()
        val FruitFragment = Fragment_Fruit()
        setCurrentFragment(ScannerFragment)
        activityMainActivity.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.scanner -> setCurrentFragment(ScannerFragment)
                R.id.fruit -> setCurrentFragment(FruitFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentRoot,fragment)
        commit()
    }
}
