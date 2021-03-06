package com.uasproject.footballs.player.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.uasproject.footballs.R
import com.uasproject.footballs.api.ApiRepository
import com.uasproject.footballs.player.adapter.PlayerAdapter
import com.uasproject.footballs.player.interfaces.PlayerView
import com.uasproject.footballs.player.model.Player
import com.uasproject.footballs.player.presenter.PlayerPresenter
import com.uasproject.footballs.team.model.Team
import com.uasproject.footballs.team.view.TeamDetailActivity
import kotlinx.android.synthetic.main.fragment_player.view.*

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment(), PlayerView {

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        val team = activity?.intent?.getParcelableExtra(TeamDetailActivity.EXTRA_TEAM) as Team

        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerPresenter(this, request, gson)
        presenter.getData(team.idTeam)

        adapter = PlayerAdapter(players){
            val intent = Intent(activity, PlayerDetailActivity::class.java)
            intent.putExtra(PlayerDetailActivity.EXTRA_PLAYER, it)
            startActivity(intent)
        }

        view.rv_player.layoutManager = LinearLayoutManager(activity)
        view.rv_player.adapter = adapter

        return view
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showData(data: List<Player>?) {
        if (data != null){
            players.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }


}
