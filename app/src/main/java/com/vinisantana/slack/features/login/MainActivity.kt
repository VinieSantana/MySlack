package com.vinisantana.slack.features.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.vinisantana.slack.R
import com.vinisantana.slack.features.base.BaseActivity
import com.vinisantana.slack.features.contacts.ContactsActivity
import com.vinisantana.slack.features.signup.SignupActivity
import com.vinisantana.slack.model.Channel
import com.vinisantana.slack.model.Message
import com.vinisantana.slack.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        btnEmailSignin.setOnClickListener {
        }


        btnSignup.setOnClickListener {

            val extras = Bundle()
            extras.putString(LOGIN_EMAIL, editEmail.text.toString())
            extras.putString(LOGIN_PASSWORD, editPassword.text.toString())
            startActivity(Intent(this, SignupActivity::class.java)
                .putExtras(extras))
        }

//        val messageModel = Message("123","asddasd","123123"
//        )
//
//        val channel = Channel(
//            "xyz","123","asdasd"
//        )
//
//        FirebaseFirestore.getInstance().collection("channels")
//            .document(channel.document)
//            .collection("messages")
//            .add(messageModel)
//            .addOnSuccessListener {
//                Log.d("ADD_USER", "Cadastro efetuado!")
//            }
//            .addOnFailureListener {
//                Log.e("ADD_USER", "Erro no cadastro", it)
//            }
    }


    public override fun onStart() {
        super.onStart()
        hideProgressBar()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LOGIN", "Google sign in failed",e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        Log.d("LOGIN", "firebaseAuthWithGoogle:" + acct.id!!)

        showProgressBar()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LOGIN", "signInWithCredential:success")
                     auth.currentUser?.let {user ->
                         updateUI(user)
                         saveUser(user)}
                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("AUTH", "signInWithCredential:failure", task.exception)
//                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }
                hideProgressBar()
                // ...
            }
    }

    private fun saveUser(firebaseUser: FirebaseUser) {
        val user = User(
            firebaseUser.uid,
            firebaseUser.displayName,
            firebaseUser.email,
            firebaseUser.photoUrl.toString()
        )

        FirebaseFirestore.getInstance().collection("users")
            .document(firebaseUser.uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("ADD_USER", "Cadastro efetuado!")
            }
            .addOnFailureListener {
                Log.e("ADD_USER", "Erro no cadastro", it)
            }
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        hideProgressBar()
        if(firebaseUser != null) {
            startActivity(Intent(this, ContactsActivity::class.java))
            finish()
        }else{
            Log.e("LOGIN", "User NULL")
        }
    }

    companion object {
        const val RC_SIGN_IN = 12344
        const val LOGIN_EMAIL = "mail"
        const val LOGIN_PASSWORD = "password"
    }
}
