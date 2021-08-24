package au.yazilimstaj.perfectplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.et_name
import kotlinx.android.synthetic.main.activity_sign_in.et_password
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        Buton_Baslama.setOnClickListener {

            signInRegisteredUser()
        }

        Buton_Girissiz_Sorgulama.setOnClickListener {

            val intent = Intent(this, GunlerActivityBulunanHafta::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signInSuccess(user: User){
        startActivity(Intent(this, GunlerActivityBulunanHafta::class.java))
        finish()
    }

    private fun signInRegisteredUser(){
        val email: String = et_name.text.toString().trim(){ it <= ' '}
        val password: String = et_password.text.toString().trim(){ it <= ' '}

        if(validateForm(email,password)){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, GunlerActivityBulunanHafta::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean {
        return when {

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Email adresi giriniz")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Şifre giriniz")
                false
            }
            else -> {
                true
            }
        }
    }

}