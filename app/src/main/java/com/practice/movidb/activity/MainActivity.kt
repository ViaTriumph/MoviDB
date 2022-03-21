package com.practice.movidb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.practice.movidb.R
import com.practice.movidb.ShowApplication
import com.practice.movidb.databinding.ActivityMainBinding
import com.practice.movidb.di.BaseComponent
import com.practice.movidb.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var baseComponent: BaseComponent
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        baseComponent = (application as ShowApplication).baseComponent
        baseComponent.inject(this)



        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addFragment()
    }

    fun addFragment() {
        val fragment = SearchFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(binding.fragmentContainerView.id, fragment)

        fragmentTransaction.commitAllowingStateLoss()
    }
}