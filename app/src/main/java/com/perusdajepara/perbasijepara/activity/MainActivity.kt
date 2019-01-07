package com.perusdajepara.perbasijepara.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.utils.disableShiftMode
import com.perusdajepara.perbasijepara.gallery.GalleryFragment
import com.perusdajepara.perbasijepara.informasi.InformasiFragment
import com.perusdajepara.perbasijepara.login.LoginActivity
import com.perusdajepara.perbasijepara.player.PlayerFragment
import com.perusdajepara.perbasijepara.profil.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()
        setAuth()
        setNavbottom()
    }

    private fun setAuth() {
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser == null) {
            startActivity<LoginActivity>()
            finish()
        }
    }

    private fun setNavbottom() {
        main_navbottom.disableShiftMode()
        main_navbottom.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.informasi -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, InformasiFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.gallery -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, GalleryFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.player -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, PlayerFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profil -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, ProfileFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        main_navbottom.selectedItemId = R.id.informasi
    }

    private fun setToolbar() {
        setSupportActionBar(main_toolbar)
    }
}
