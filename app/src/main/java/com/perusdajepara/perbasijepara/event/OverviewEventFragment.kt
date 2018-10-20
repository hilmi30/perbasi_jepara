package com.perusdajepara.perbasijepara.event


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*

import com.perusdajepara.perbasijepara.R
import kotlinx.android.synthetic.main.fragment_overview_event.*
import org.jetbrains.anko.support.v4.toast

class OverviewEventFragment : Fragment() {

    private lateinit var listener: ValueEventListener
    private lateinit var mDatabase: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val eventUid = arguments?.getString("eventUid").toString()

        mDatabase = FirebaseDatabase.getInstance().reference
                .child("event").child(eventUid)

        listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                toast("Terjadi Kesalahan")
            }

            override fun onDataChange(p0: DataSnapshot) {
                event_lokasi.text = p0.child("lokasi").value.toString()
                event_alamat.text = p0.child("alamat").value.toString()
                event_tanggal.text = p0.child("tanggal").value.toString()
                event_team_terdaftar.text = p0.child("teamTerdaftar").value.toString()
                event_deskripsi.text = p0.child("deskripsi").value.toString()
            }

        }

        mDatabase.addValueEventListener(listener)
    }

    override fun onStop() {
        super.onStop()
        mDatabase.removeEventListener(listener)
    }
}
