package com.perusdajepara.perbasijepara.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.R.string.lokasi
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.toast

class DetailEventActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var listener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        setSupportActionBar(event_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        event_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    override fun onStart() {
        super.onStart()

        val uid = intent.getStringExtra("uid")

        mDatabase = FirebaseDatabase.getInstance().reference
                .child("event").child(uid)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                toast("Terjadi Kesalahan")
            }

            override fun onDataChange(p0: DataSnapshot) {

                Picasso.get()
                        .load(p0.child("thumbnail").value.toString())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(event_thumbnail)

                event_judul.text = p0.child("judul").value.toString().toUpperCase()

                val eventUid = p0.key.toString()

                // Overview Item Data
                val overviewArgs = Bundle()
                overviewArgs.putString("eventUid", eventUid)

                val overviewFragment = OverviewEventFragment()
                overviewFragment.arguments = overviewArgs

                val tabAdapter = TabAdapter(supportFragmentManager)
                tabAdapter.addFragment("Overview", overviewFragment)
                tabAdapter.addFragment("Kategori", KategoriEventFragment())
                tabAdapter.addFragment("Team", TeamEventFragment())

                event_pager.adapter = tabAdapter
                event_tab.setupWithViewPager(event_pager)
            }

        }

        mDatabase.addListenerForSingleValueEvent(listener)
    }

    override fun onStop() {
        super.onStop()
        mDatabase.removeEventListener(listener)
    }
}
