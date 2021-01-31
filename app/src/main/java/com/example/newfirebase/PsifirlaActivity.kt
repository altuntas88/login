package com.example.newfirebase

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.example.newfirebase.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth

class PsifirlaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.psifirlmaButton.setOnClickListener {
            var psifirlamaemail=binding.psifirlaemail.text.toString().trim()
            if(TextUtils.isEmpty(psifirlamaemail)){
                binding.psifirlaemail.error="Lütfen e-mail adresinizi girin"
            }
            else{
                auth.sendPasswordResetEmail(psifirlamaemail)
                    .addOnCompleteListener(this){sifirlama->
                        if(sifirlama.isSuccessful){
                            binding.psifirlamamesaj.text="E-mail adresinize sıfırlama maili gönderildi"
                        }
                        else{
                            binding.psifirlamamesaj.text="Sıfırlama işlemi başarısız"

                        }
                    }
            }
        }
        binding.psifirlamagirisyapButton.setOnClickListener {
            intent= Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}