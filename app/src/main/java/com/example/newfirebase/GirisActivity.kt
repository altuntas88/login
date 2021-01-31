package com.example.newfirebase
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.newfirebase.databinding.ActivityGirisBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_giris.*

class GirisActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this@GirisActivity,ProfilActivity::class.java))
            finish()
        }

        binding.girisYapButton.setOnClickListener {
            var girisemail = binding.girisEmail.text.toString()
            var girisparola = binding.girisParola.text.toString()
            if (TextUtils.isEmpty(girisemail)) {
                binding.girisEmail.error = "Lütfen email adresinizi yazınız"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(girisparola)) {
                binding.girisParola.error = "Lütfen parola yazınız"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(girisemail,girisparola)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        intent=Intent(applicationContext,ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Giris Hatalı",Toast.LENGTH_LONG).show()
                    }
                }
        }
        binding.girisYeniUyelik.setOnClickListener {
            intent=Intent(applicationContext,UyeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.girisParolaUnuttum.setOnClickListener {
            intent = Intent(applicationContext, PsifirlaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}