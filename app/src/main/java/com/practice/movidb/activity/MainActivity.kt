package com.practice.movidb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.practice.movidb.R
import com.practice.movidb.ShowApplication
import com.practice.movidb.databinding.ActivityMainBinding
import com.practice.movidb.di.BaseComponent
import com.practice.movidb.ui.detail.MovieDetailFragment
import com.practice.movidb.ui.explore.ExploreFragment
import com.practice.movidb.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var baseComponent: BaseComponent
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        baseComponent = (application as ShowApplication).baseComponent
        baseComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addFragment(ExploreFragment.TAG, null)
    }

    //TODO move to navigator
    fun addFragment(tag: String, data: Any?) {
        val fragment = getFragmentInstance(tag, data)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(binding.fragmentContainerView.id, fragment)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun getFragmentInstance(tag: String, data: Any?): Fragment {
        return when (tag) {
            SearchFragment.TAG -> SearchFragment.newInstance()
            ExploreFragment.TAG -> ExploreFragment.newInstance()
            MovieDetailFragment.TAG -> if (data is Int)
                MovieDetailFragment.newInstance(data)
            else MovieDetailFragment.newInstance(
                0
            )
            else -> throw IllegalArgumentException("Unknown tag:$tag")
        }
    }


}