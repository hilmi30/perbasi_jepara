package com.perusdajepara.perbasijepara.informasi.berita

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.perusdajepara.perbasijepara.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_berita.*
import kotlinx.android.synthetic.main.berita_content.*
import org.jetbrains.anko.toast

class DetailBeritaActivity : AppCompatActivity(), DetailBeritaView {

    private lateinit var presenter: DetailBeritaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        presenter = DetailBeritaPresenter()
        onAttachView()

        setSupportActionBar(detail_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ganti warna panah jadi putih
        detail_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
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
        presenter.removeListener()
    }

    override fun onStart() {
        super.onStart()
        val uid = intent.getStringExtra("uid").toString()
        presenter.setItemBerita(uid)
    }

    override fun getDataError() {
        toast("Terjadi Kesalahan")
    }

    override fun showDataBerita(beritaModel: BeritaModel) {
        Picasso.get().load(beritaModel.thumbnail).into(berita_gambar)
        berita_judul.text = beritaModel.judul
        berita_tanggal.text = beritaModel.updatedAt
        berita_deskripsi.text = beritaModel.deskripsi
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_berita_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.share -> {
                toast("share")
            }
            else -> finish()
        }

        return true
    }
}