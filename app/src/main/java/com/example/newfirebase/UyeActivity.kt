package com.example.newfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.newfirebase.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_uye.*

class UyeActivity : AppCompatActivity() {
    lateinit var binding: ActivityUyeBinding
    private lateinit var auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUyeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        binding.uyeKaydetButon.setOnClickListener {
            var uyeadsoyad = binding.uyeAdSoyad.text.toString()
            var uyeparola = binding.uyeParola.text.toString()
            var uyeemail = binding.uyeEmail.text.toString()
            if (TextUtils.isEmpty(uyeadsoyad)) {
                binding.uyeAdSoyad.error = "Lütfen isim-soyisim bilgisini giriniz"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(uyeparola)) {
                binding.uyeParola.error = "Lütfen parola bilgisini giriniz"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(uyeemail)) {
                binding.uyeEmail.error = "Lütfen e-mail bilgisini giriniz"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(
                binding.uyeEmail.text.toString(),
                binding.uyeParola.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var currentUser = auth.currentUser
                        var currentUserDb =
                            currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                        currentUserDb?.child("adisoyadi")?.setValue(binding.uyeAdSoyad.text.toString())
                        Toast.makeText(this@UyeActivity, "Kayıt Başarılı", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@UyeActivity, "Kayıt Hatalı", Toast.LENGTH_LONG).show()

                    }


                }
        }
        binding.uyeGirisYapButton.setOnClickListener {
            intent= Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}