package com.example.newfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.newfirebase.databinding.ActivityGirisBinding
import com.example.newfirebase.databinding.ActivityGuncelleBinding
import com.example.newfirebase.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class GuncelleActivity : AppCompatActivity() {
    lateinit var binding:ActivityGuncelleBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        var currentUser=auth.currentUser
        binding.guncelleEmail.setText(currentUser?.email)
        var userReference=databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                binding.guncelleAdSoyad.setText(snapshot.child("adisoyadi").value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
        binding.guncelleBilgilerimiButon.setOnClickListener {
            var guncelleEmail = binding.guncelleEmail.text.toString().trim()
            currentUser!!.updateEmail(guncelleEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Email Güncellendi", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Email Başarısız", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            var guncelleParola = binding.guncelleParola.text.toString().trim()
            currentUser!!.updatePassword(guncelleParola)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Parola Güncellendi", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Parola Başarısız", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            var currentUserDb =
                currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
            currentUserDb?.removeValue()
            currentUserDb?.child("adisoyadi")?.setValue(binding.guncelleAdSoyad.text.toString())
            Toast.makeText(applicationContext, "Ad Soyad Güncellendi", Toast.LENGTH_LONG)

        }

    }
}