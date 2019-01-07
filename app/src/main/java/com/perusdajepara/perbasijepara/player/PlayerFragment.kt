package com.perusdajepara.perbasijepara.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

import com.perusdajepara.perbasijepara.R
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.fragment_player.view.*

class PlayerFragment : Fragment() {

    private var adapter: PlayerRecyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val query = FirebaseDatabase.getInstance().reference.child("ranking").orderByChild("point")
        val options = FirebaseRecyclerOptions.Builder<PlayerModel>()
                .setQuery(query, PlayerModel::class.java).build()

        adapter = PlayerRecyAdapter(options)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true

        player_recy.layoutManager = layoutManager
        player_recy.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }
}
