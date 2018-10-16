package com.perusdajepara.perbasijepara.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter

import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.activity.DetailBeritaActivity
import com.perusdajepara.perbasijepara.model.BeritaModel
import com.perusdajepara.perbasijepara.presenter.BeritaPresenter
import com.perusdajepara.perbasijepara.view.BeritaView
import kotlinx.android.synthetic.main.fragment_berita.*
import kotlinx.android.synthetic.main.layout_berita.view.*
import org.jetbrains.anko.support.v4.startActivity

class BeritaFragment : Fragment(), BeritaView {

    private lateinit var presenter: BeritaPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berita, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = BeritaPresenter()
        onAttachView()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.showListBerita()
        presenter.startListening()
    }

    override fun onDetachView() {
        presenter.onDetach()
        presenter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetachView()
    }

    override fun setHolder(holder: BeritaHolder, position: Int, model: BeritaModel) {
        holder.titleBerita.text = model.title
        holder.itemView.setOnClickListener {
            startActivity<DetailBeritaActivity>()
        }
    }

    override fun showList(adapter: FirebaseRecyclerAdapter<BeritaModel, BeritaHolder>) {
        val layoutManager = LinearLayoutManager(context)
        berita_recy.layoutManager = layoutManager
        berita_recy.adapter = adapter
    }

    class BeritaHolder(v: View): RecyclerView.ViewHolder(v) {
        val titleBerita: TextView = v.berita_judul
    }
}
