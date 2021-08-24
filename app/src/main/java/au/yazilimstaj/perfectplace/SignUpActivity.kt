package au.yazilimstaj.perfectplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        var currentUserId = FirestoreClass().getCurrentUserId()

        if(currentUserId.isNotEmpty()){
            startActivity(Intent(this, GunlerActivityBulunanHafta::class.java))
        }

        Buton_Kayit.setOnClickListener {
            registerUser()
        }

    }

    fun userRegisteredSuccess(){
        Toast.makeText(this,"Başarılı bir şekilde giriş yaptınız",Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }


private fun registerUser(){
    val name: String = et_name.text.toString()
    val email: String = et_email.text.toString().trim{ it <= ' '}
    val password: String = et_password.text.toString().trim{ it <= ' '}

    if(validateForm(name, email, password)){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser: FirebaseUser = task.result!!.user!!
                val registeredEmail = firebaseUser.email!!
                val user = User(firebaseUser.uid, name, registeredEmail)
                FirestoreClass().registerUser(this, user)

            } else {
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

    private fun validateForm(name: String, email: String, password: String) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("İsim giriniz")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Email adresi giriniz")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Şifre giriniz")
                false
            }
            else -> {
                true
            }
        }


    }




}