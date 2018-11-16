package com.perusdajepara.perbasijepara.detailberita

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.model.BeritaModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_berita.*
import kotlinx.android.synthetic.main.berita_content.*
import org.jetbrains.anko.toast

class DetailBeritaActivity : AppCompatActivity(), DetailBeritaView {

    private val presenter = DetailBeritaPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ganti warna panah jadi putih
        detail_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        val uid = intent.getStringExtra("uid").toString()
        presenter.setItemBerita(uid)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
        presenter.removeListener()
    }

    override fun getDataError() {
        toast(getString(R.string.terjadi_kesalahan))
    }

    override fun showDataBerita(beritaModel: BeritaModel?) {
        Picasso.get().load(beritaModel?.thumbnail).into(berita_gambar)
        berita_judul.text = beritaModel?.judul
        berita_tanggal.text = beritaModel?.updatedAt
        berita_deskripsi.text = beritaModel?.deskripsi
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
