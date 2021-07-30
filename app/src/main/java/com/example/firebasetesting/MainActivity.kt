package com.example.firebasetesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val LogInFragment = LogInFragment()
        val SignUpFragment = SignUpFragment()
        val signUpButton : Button = findViewById(R.id.SignUpButton)

        var isLoginFragment = true;
        fragmentReplace(R.id.login_fragment_view,LogInFragment)

        signUpButton.setOnClickListener {
            isLoginFragment = if(isLoginFragment) {
                fragmentReplace(R.id.login_fragment_view,SignUpFragment)
                signUpButton.setText(R.string.log_in_button)
                false
            } else {
                fragmentReplace(R.id.login_fragment_view,LogInFragment)
                signUpButton.setText(R.string.sign_up_button)
                true
            }
        }


    }

    private fun fragmentReplace(firstFragment: Int, secondFragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(firstFragment, secondFragment)
            commit()
        }
    }
}