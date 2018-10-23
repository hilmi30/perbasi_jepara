package com.perusdajepara.perbasijepara.informasi.event.detailkategorievent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import kotlinx.android.synthetic.main.activity_detail_kategori_event.*

class DetailKategoriEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kategori_event)

        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment("Team", TeamsFragment())
        tabAdapter.addFragment("Jadwal", JadwalFragment())
        tabAdapter.addFragment("Standing", StandingsFragment())

        detail_event_pager.adapter = tabAdapter
        detail_event_tab.setupWithViewPager(detail_event_pager)
    }
}
