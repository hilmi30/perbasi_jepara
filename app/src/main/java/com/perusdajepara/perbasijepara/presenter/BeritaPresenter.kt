package com.perusdajepara.perbasijepara.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.perusdajepara.perbasijepara.R
import com.perusdajepara.perbasijepara.basecontract.BasePresenter
import com.perusdajepara.perbasijepara.fragment.BeritaFragment
import com.perusdajepara.perbasijepara.model.BeritaModel
import com.perusdajepara.perbasijepara.view.BeritaView

class BeritaPresenter: BasePresenter<BeritaView> {

    var mView: BeritaView? = null
    private lateinit var adapter: FirebaseRecyclerAdapter<BeritaModel, BeritaFragment.BeritaHolder>

    override fun onAttach(view: BeritaView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showListBerita() {
        val mDatabase = FirebaseDatabase.getInstance().reference

        val query = mDatabase.child("teslist")

        val options = FirebaseRecyclerOptions.Builder<BeritaModel>()
                .setQuery(query, BeritaModel::class.java).build()

        adapter = object : FirebaseRecyclerAdapter<BeritaModel, BeritaFragment.BeritaHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaFragment.BeritaHolder {
                return BeritaFragment.BeritaHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_berita, parent, false))
            }

            override fun onBindViewHolder(holder: BeritaFragment.BeritaHolder, position: Int, model: BeritaModel) {
                mView?.setHolder(holder, position, model)
            }
        }

        mView?.showList(adapter)
    }

    fun startListening() {
        adapter.startListening()
    }

    fun stopListening() {
        adapter.stopListening()
    }
}