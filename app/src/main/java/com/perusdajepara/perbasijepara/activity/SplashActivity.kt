package com.perusdajepara.perbasijepara.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 3000)

    }
}
