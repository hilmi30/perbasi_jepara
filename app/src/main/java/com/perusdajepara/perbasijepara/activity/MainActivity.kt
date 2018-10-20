package com.perusdajepara.perbasijepara.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.utils.disableShiftMode
import com.perusdajepara.perbasijepara.gallery.GalleryFragment
import com.perusdajepara.perbasijepara.informasi.InformasiFragment
import com.perusdajepara.perbasijepara.player.PlayerFragment
import com.perusdajepara.perbasijepara.profil.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, InformasiFragment())
                .commit()

        main_navbottom.disableShiftMode()
        main_navbottom.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.informasi -> fragment = InformasiFragment()
                R.id.gallery -> fragment = GalleryFragment()
                R.id.player -> fragment = PlayerFragment()
                R.id.profil -> fragment = ProfileFragment()
            }

            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment).commit()

            true
        }

    }
}
