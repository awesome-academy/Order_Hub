package com.trunghoang.orderhub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.constraint_main, LoginFragment.newInstance())
            .commit()
    }
}
