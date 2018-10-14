package com.kreator.roemah.footballmatchschedule.favoritefragment

import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.db.database
import com.kreator.roemah.footballmatchschedule.detail.DetailActivity
import com.kreator.roemah.footballmatchschedule.model.Favorite
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteFragment: Fragment(), AnkoComponent<Context>, AnkoLogger{
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onActivityCreated(savedInstanceStaste: Bundle?) {
        super.onActivityCreated(savedInstanceStaste)

        adapter = FavoriteAdapter(favorites){
            ctx.startActivity<DetailActivity>("id" to it.eventId,"date" to it.eventDate,"file" to it.fileName)
        }

        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View =with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    id = R.id.layout_event
                    lparams(width= matchParent,height = wrapContent)

                    textView ("Favorite Event"){
                        id = R.id.pastTitle
                        textSize = 20f
                    }.lparams(width = wrapContent,height = wrapContent){centerHorizontally()}

                    listEvent = recyclerView {
                        id = R.id.ListEvent
                        lparams(width= matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams { below(R.id.pastTitle) }
                }
            }
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)

            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}