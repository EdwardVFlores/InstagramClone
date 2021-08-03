package com.example.firebasetesting

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpBtn: Button = view.findViewById(R.id.SignUpButton)

        signUpBtn.setOnClickListener {
            createAccount(view)
        }

    }

    private fun createAccount(thisView: View) {

        val fullNameEText: EditText = thisView.findViewById(R.id.FullNameSignUpEditText)
        val emailAddressEText: EditText = thisView.findViewById(R.id.EmailAddressSignUpEditText)
        val usernameEText: EditText = thisView.findViewById(R.id.UsernameSignUpEditText)
        val passwordEText: EditText = thisView.findViewById(R.id.PasswordSignUpEditText)
        when {
            TextUtils.isEmpty(fullNameEText.text.toString()) -> Toast.makeText(
                view?.context?.applicationContext,
                "Full Name is required",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(emailAddressEText.text.toString()) -> Toast.makeText(
                view?.context?.applicationContext,
                "Email Address is required",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(usernameEText.text.toString()) -> Toast.makeText(
                view?.context?.applicationContext,
                "Username is required",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(passwordEText.text.toString()) -> Toast.makeText(
                view?.context?.applicationContext,
                "Password is required",
                Toast.LENGTH_LONG
            ).show()

            else -> {
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(
                    emailAddressEText.text.toString(),
                    passwordEText.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserInfo(
                                fullNameEText.text.toString(),
                                usernameEText.text.toString(),
                                emailAddressEText.text.toString()
                            )
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(
                                view?.context?.applicationContext,
                                "Error: $message",
                                Toast.LENGTH_SHORT
                            ).show()
                            mAuth.signOut()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String, username: String, email: String) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullName
        userMap["username"] = username
        userMap["email"] = email
        userMap["bio"] = "Using edwards instagram clone app!"
        userMap["image"] =
            "https://firebasestorage.googleapis.com/v0/b/fir-test-d00fe.appspot.com/o/Default%20Images%2F296542259-HappyRickety.png?alt=media&token=d5378d45-b529-44a1-8add-a5ae3bb45524"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        view?.context?.applicationContext,
                        "Account has been created successfully.",
                        Toast.LENGTH_LONG
                    ).show()
                    TODO("Finish this intent when people log in")
                    /**
                    val intent =
                        Intent(view?.context?.applicationContext, SignUpFragment::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent) **/
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(
                        view?.context?.applicationContext,
                        "Error:$message",
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseAuth.getInstance().signOut()
                }
            }


    }
}