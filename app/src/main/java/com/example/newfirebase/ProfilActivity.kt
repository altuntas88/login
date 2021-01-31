package com.example.newfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.widget.Toast
import com.example.newfirebase.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        var currentUser=auth.currentUser
        binding.profilEmail.text="Email: "+currentUser?.email

        var userReference=databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilAdSoyad.text="Tam adınız:"+snapshot.child("adisoyadi").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        binding.profilCikisYapButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
            finish()
        }

        binding.profilHesabimiSilButton.setOnClickListener {


            currentUser?.delete()?.addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız Silindi",Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
                    finish()
                    var currentUserDb =currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                    currentUserDb?.removeValue()
                }
            }

        }
        binding.profilBilgilerimiGuncellebutton.setOnClickListener {
            intent= Intent(applicationContext,GuncelleActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}