package com.perusdajepara.perbasijepara.detailkategorievent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.perusdajepara.perbasijepara.listjadwal.ListJadwalFragment
import com.perusdajepara.perbasijepara.liststandings.ListStandingsFragment
import com.perusdajepara.perbasijepara.listteam.ListTeamEventFragment
import com.perusdajepara.perbasijepara.registerevent.RegisterEventFragment
import kotlinx.android.synthetic.main.activity_detail_kategori_event.*
import org.jetbrains.anko.toast

class DetailKategoriEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kategori_event)

        val uid = intent.getStringExtra("uid")
        val nama = intent.getStringExtra("nama")
        val status = intent.getStringExtra("status")

        setSupportActionBar(detail_kategori_event_toolbar)
        supportActionBar?.title = nama.toUpperCase()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        detail_kategori_event_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        val bundle = Bundle()
        bundle.putString("uid", uid)

        val registerFragment = RegisterEventFragment()
        val teamEventFragment = ListTeamEventFragment()
        val listJadwalFragment = ListJadwalFragment()
        val listStandingsFragment = ListStandingsFragment()
        registerFragment.arguments = bundle
        teamEventFragment.arguments = bundle
        listJadwalFragment.arguments = bundle
        listStandingsFragment.arguments = bundle

        val tabAdapter = TabAdapter(supportFragmentManager)

        val mAuth = FirebaseAuth.getInstance()

        if (status == "1" && mAuth.currentUser != null) {
            tabAdapter.addFragment("Register", RegisterEventFragment())
            tabAdapter.addFragment("Jadwal", listJadwalFragment)
            tabAdapter.addFragment("Team", teamEventFragment)
            tabAdapter.addFragment("Standing", listStandingsFragment)
            detail_event_tab.tabMode = TabLayout.MODE_SCROLLABLE
        } else {
            tabAdapter.addFragment("Jadwal", listJadwalFragment)
            tabAdapter.addFragment("Team", teamEventFragment)
            tabAdapter.addFragment("Standing", listStandingsFragment)
            detail_event_tab.tabMode = TabLayout.MODE_FIXED
        }

        detail_event_pager.adapter = tabAdapter
        detail_event_tab.setupWithViewPager(detail_event_pager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
