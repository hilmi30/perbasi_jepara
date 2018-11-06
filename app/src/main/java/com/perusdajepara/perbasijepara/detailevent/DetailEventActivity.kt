package com.perusdajepara.perbasijepara.detailevent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.adapter.TabAdapter
import com.perusdajepara.perbasijepara.model.EventModel
import com.perusdajepara.perbasijepara.listkategori.ListKategoriEventFragment
import com.perusdajepara.perbasijepara.overview.OverviewEventFragment
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

    // ================ Set Area ================ //

    override fun onStart() {
        super.onStart()

        val uid = intent.getStringExtra("uid")
        presenter.showDetailEvent(uid)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun setDataEvent(eventModel: EventModel?, eventKey: String) {
        Picasso.get()
                .load(eventModel?.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(event_thumbnail)

        event_judul.text = eventModel?.judul?.toUpperCase()

        val bundle = Bundle()
        bundle.putString("eventUid", eventKey)

        // Passing data uid / key
        val overviewFragment = OverviewEventFragment()
        val kategoriEventFragment = ListKategoriEventFragment()
        overviewFragment.arguments = bundle
        kategoriEventFragment.arguments = bundle

        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment("Overview", overviewFragment)
        tabAdapter.addFragment("Kategori", kategoriEventFragment)

        event_pager.adapter = tabAdapter
        event_tab.setupWithViewPager(event_pager)
    }

    // ================ Error Area ================ //

    override fun getDataError() {
        toast("Terjadi Kesalahan")
    }

    // ================ Destroy Area ================ //

    override fun onStop() {
        super.onStop()

        presenter.removeListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
