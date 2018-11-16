package com.perusdajepara.perbasijepara.foto


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.perusdajepara.perbasijepara.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_foto.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert

class FotoFragment : Fragment(), FotoView {

    var img: ImageView? = null
    private lateinit var fotoAdapter: FotoAdapter

    val presenter = FotoPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foto, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onAttachView()
    }

    // Setting Area //
    override fun onAttachView() {
        presenter.onAttach(this)
        presenter.setFoto()
    }

    override fun setListFoto(options: FirebaseRecyclerOptions<FotoModel>) {
        val imagePopup = ImagePopup(context)
        imagePopup.isImageOnClickClose = true
        imagePopup.isFullScreen = true

        fotoAdapter = FotoAdapter(options) {
            imagePopup.initiatePopupWithGlide(it.imageUrl)
            imagePopup.viewPopup()
        }

        foto_recy.layoutManager = LinearLayoutManager(context)
        foto_recy.adapter = fotoAdapter
    }

    override fun onStart() {
        super.onStart()
        fotoAdapter.startListening()
    }

    // Destroy Area //
    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDetachView()
    }

    override fun onStop() {
        super.onStop()
        fotoAdapter.stopListening()
    }

    // Error Area //
    // Feedback Area //


}
