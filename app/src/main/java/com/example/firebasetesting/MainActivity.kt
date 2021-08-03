package com.example.firebasetesting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val LogInFragment = LogInFragment()
        val SignUpFragment = SignUpFragment()
        val switchLoginBtn : Button = findViewById(R.id.SwitchLoginPageButton)



        var isLoginFragment = true

        fragmentReplace(R.id.login_fragment_view,LogInFragment)

        switchLoginBtn.setOnClickListener {
            isLoginFragment = if(isLoginFragment) {
                fragmentReplace(R.id.login_fragment_view,SignUpFragment)
                switchLoginBtn.setText(R.string.log_in_button)
                false
            } else {
                fragmentReplace(R.id.login_fragment_view,LogInFragment)
                switchLoginBtn.setText(R.string.sign_up_button)
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