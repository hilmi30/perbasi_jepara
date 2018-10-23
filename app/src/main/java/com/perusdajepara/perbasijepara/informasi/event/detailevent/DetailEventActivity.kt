package com.perusdajepara.perbasijepara.informasi.event.detailevent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.perusdajepara.perbasijepara.informasi.event.EventModel
import com.perusdajepara.perbasijepara.informasi.event.kategori.KategoriEventFragment
import com.perusdajepara.perbasijepara.informasi.event.overview.OverviewEventFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.toast

class DetailEventActivity : AppCompatActivity(), DetailEventView {

    private lateinit var presenter: DetailEventPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        presenter = DetailEventPresenter()
        onAttachView()

        setSupportActionBar(event_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        event_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    override fun onStart() {
        super.onStart()

        val uid = intent.getStringExtra("uid")
        presenter.showDetailEvent(uid)
    }

    override fun onStop() {
        super.onStop()

        presenter.removeListener()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun getDataError() {
        toast("Terjadi Kesalahan")
    }

    override fun setDataEvent(eventModel: EventModel) {
        Picasso.get()
                .load(eventModel.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(event_thumbnail)

        event_judul.text = eventModel.judul?.toUpperCase()

        val eventUid = eventModel.uid

        val overviewArgs = Bundle()
        overviewArgs.putString("eventUid", eventUid)

        val overviewFragment = OverviewEventFragment()
        overviewFragment.arguments = overviewArgs

        val kategoriEventArgs = Bundle()
        kategoriEventArgs.putString("eventUid", eventUid)

        val kategoriEventFragment = KategoriEventFragment()
        kategoriEventFragment.arguments = kategoriEventArgs

        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment("Overview", overviewFragment)
        tabAdapter.addFragment("Kategori", kategoriEventFragment)

        event_pager.adapter = tabAdapter
        event_tab.setupWithViewPager(event_pager)
    }
}
