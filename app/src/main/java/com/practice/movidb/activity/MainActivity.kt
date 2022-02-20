package com.practice.movidb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practice.movidb.MyApplication
import com.practice.movidb.R
import com.practice.movidb.common.MovieBloc
import com.practice.movidb.di.BaseComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var baseComponent: BaseComponent

    @Inject lateinit var movieBloc: MovieBloc

    override fun onCreate(savedInstanceState: Bundle?) {

        baseComponent = (application as MyApplication).baseComponent
        baseComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButton()
    }

    private fun initButton(){
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            movieBloc.makeCall()
        }
    }
}