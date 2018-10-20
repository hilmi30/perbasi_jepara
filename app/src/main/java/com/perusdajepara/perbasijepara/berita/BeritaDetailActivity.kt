package com.perusdajepara.perbasijepara.berita

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.perusdajepara.perbasijepara.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_berita.*
import kotlinx.android.synthetic.main.berita_content.*
import org.jetbrains.anko.toast

class BeritaDetailActivity : AppCompatActivity() {

    private lateinit var createdAt: String
    private lateinit var deskripsi: String
    private lateinit var judul: String
    private lateinit var thumbnail: String
    private lateinit var updateAt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ganti warna panah jadi putih
        detail_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        setDataItemBerita()
    }

    private fun setDataItemBerita() {

        createdAt = intent.getStringExtra("createdAt")
        deskripsi = intent.getStringExtra("deskripsi")
        judul = intent.getStringExtra("judul")
        thumbnail = intent.getStringExtra("thumbnail")
        updateAt = intent.getStringExtra("updatedAt")

        Picasso.get().load(thumbnail).into(berita_gambar)
        berita_judul.text = judul
        berita_tanggal.text = updateAt
        berita_deskripsi.text = deskripsi

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
