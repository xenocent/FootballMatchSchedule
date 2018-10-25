package com.kreator.roemah.footballmatchschedule.teamdetail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.R.color.colorAccent
import com.kreator.roemah.footballmatchschedule.R.id.add_to_favorite
import com.kreator.roemah.footballmatchschedule.R.menu.detail_menu
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.db.database
import com.kreator.roemah.footballmatchschedule.model.FavoritesTeam
import com.kreator.roemah.footballmatchschedule.model.Team
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.visible
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.viewPager
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager


class TeamDetailActivity : AppCompatActivity(),TeamDetailView {


    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var pagerCon:ViewPager
    private var desc:String? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        id = intent.getStringExtra("id")
        desc = intent.getStringExtra("desc")
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout{
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge =  imageView {}.lparams(height = dip(75))

                            teamName = textView{
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams{
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView{
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView{
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }

                            teamDescription = textView().lparams{
                                topMargin = dip(20)
                            }

                            linearLayout {
                                tabLayout {

                                    id = R.id.tabs
                                }.lparams{
                                    width= matchParent
                                    height = wrapContent}

                                pagerCon = viewPager {
                                    id = R.id.containerPager
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent

                                }
                            }.lparams(width= matchParent, height = wrapContent){
                                orientation = LinearLayout.VERTICAL
                            }


                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(id)
        }


//        pager
        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

//        val viewPager = findViewById(R.id.containerPager) as ViewPager

        val models = listOf(
                MyPagerAdapter.Model(getString(R.string.descTeam), DescriptionFragment.newInstance(desc)),
                MyPagerAdapter.Model(getString(R.string.playerTeam), PlayerFragment.newInstance("wow","satu"))
        )

        val myPagerAdapter = MyPagerAdapter(models,supportFragmentManager)
        pagerCon.adapter = myPagerAdapter

        tabLayout.setupWithViewPager(pagerCon)

//        end pager
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoritesTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun ShowTeamDetail(data: List<Team>) {
        teams = Team(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
//        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoritesTeam.TABLE_FAVORITE_TEAM,
                        FavoritesTeam.TEAM_ID to teams.teamId,
                        FavoritesTeam.TEAM_NAME to teams.teamName,
                        FavoritesTeam.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoritesTeam.TABLE_FAVORITE_TEAM,"(TEAM_ID = {id})","id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}