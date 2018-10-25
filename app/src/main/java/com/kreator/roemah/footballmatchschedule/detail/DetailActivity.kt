package com.kreator.roemah.footballmatchschedule.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.db.database
import com.kreator.roemah.footballmatchschedule.model.EventDetail
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.Favorite
import com.kreator.roemah.footballmatchschedule.model.TeamFlag
import com.kreator.roemah.footballmatchschedule.util.invisible
import com.kreator.roemah.footballmatchschedule.util.setFormattedDate
import com.kreator.roemah.footballmatchschedule.util.setFormattedDateFav
import com.kreator.roemah.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity(), DetailView, AnkoLogger {

    private var idText: String = ""
    private var date: String = ""
    private var filePath: String = ""
    private lateinit var event: EventSchedule
    //    view
    private lateinit var dateTextView: TextView
    private lateinit var scoreHomeTextView: TextView
    private lateinit var scoreAwayTextView: TextView
    private lateinit var teamHomeTextView: TextView
    private lateinit var teamAwayTextView: TextView
    private lateinit var teamFormHomeTextView: TextView
    private lateinit var teamFormAwayTextView: TextView
    private lateinit var logoHome: ImageView
    private lateinit var logoAway: ImageView

    private lateinit var shotsHomeTextView: TextView
    private lateinit var shotsAwayTextView: TextView
    private lateinit var goalsDetailHomeTextView: TextView
    private lateinit var goalsDetailAwayTextView: TextView
    private lateinit var keeperHomeTextView: TextView
    private lateinit var defHomeTextView: TextView
    private lateinit var midHomeTextView: TextView
    private lateinit var fwdHomeTextView: TextView
    private lateinit var subHomeTextView: TextView
    private lateinit var keeperAwayTextView: TextView
    private lateinit var defAwayTextView: TextView
    private lateinit var midAwayTextView: TextView
    private lateinit var fwdAwayTextView: TextView
    private lateinit var subAwayTextView: TextView
    private lateinit var swipeRefreshDetail: SwipeRefreshLayout
    private lateinit var progressBarDetail: ProgressBar

    //    data
    private lateinit var presenter: DetailPresenter
    private var dataDetail: MutableList<EventDetail> = mutableListOf()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            padding = dip(18)

            progressBarDetail = progressBar {}.lparams { centerHorizontally() }

            swipeRefreshDetail = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    lparams(width = matchParent,height = wrapContent)
                    linearLayout {

                        orientation = LinearLayout.VERTICAL

                        dateTextView = textView(R.string.na) {
                            id = R.id.DateDetail
                            padding = dip(10)
                            textSize = 20f
                            textColor = Color.BLUE
                        }.lparams(width = wrapContent, height = wrapContent) { gravity = Gravity.CENTER_HORIZONTAL }

                        relativeLayout {

                            linearLayout {
                                id = R.id.layoutLineara
                                orientation = LinearLayout.HORIZONTAL
                                scoreHomeTextView = textView(R.string.na) {
                                    id = R.id.scoreDetailHome
                                    textSize = 24f
                                }.lparams(width = wrapContent) {
                                    gravity = Gravity.CENTER_VERTICAL
                                    rightMargin = dip(18)
                                }
//
                                textView {
                                    id = R.id.vs
                                    text = "VS"
                                    textSize = 20f
                                }.lparams(width = wrapContent) {
                                    gravity = Gravity.CENTER_VERTICAL
                                }

                                scoreAwayTextView = textView (R.string.na){
                                    id = R.id.scoreDetailHome
                                    textSize = 24f
                                }.lparams(width = wrapContent) {
                                    gravity = Gravity.CENTER_VERTICAL
                                    leftMargin=dip(18)
                                }
                            }.lparams { centerHorizontally() }

                            logoAway = imageView {
                                id = R.id.logoAway
                            }.lparams(width = dip(50), height = dip(50)) {
                                alignParentEnd()
                                rightMargin= dip(20)
                                endOf(R.id.layoutLineara)
                            }

                            logoHome = imageView {
                                id = R.id.logoHome
                            }.lparams(width = dip(50), height = dip(50)) {
                                alignParentStart()
                                startOf(R.id.layoutLineara)
                            }

                            teamAwayTextView = textView(R.string.na) {
                                id = R.id.teamDetailAway
                                textSize = 24f
                                maxLines = 1
                                singleLine = true
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentEnd()
                                topMargin = dip(20)
                                below(R.id.layoutLineara)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                            teamHomeTextView = textView (R.string.na) {
                                id = R.id.teamDetailHome
                                textSize = 24f
                                maxLines = 1
                                singleLine = true
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentStart()
                                topMargin = dip(20)
                                below(R.id.layoutLineara)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                            teamFormHomeTextView = textView(R.string.na) {
                                id = R.id.teamHomeForm
                                textSize = 24f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentStart()
                                below(teamHomeTextView)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                            teamFormAwayTextView = textView(R.string.na) {
                                id = R.id.teamHomeForm
                                textSize = 24f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentEnd()
                                below(teamAwayTextView)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }
                        }.lparams(width = matchParent, height = wrapContent) {}

                        view {
                            backgroundColor = Color.BLACK
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(8)
                            bottomMargin = dip(8)
                        }

                        // Goals
                        relativeLayout {
                            id = R.id.relativeLayouta

                            goalsDetailHomeTextView = textView(R.string.na) {
                                id = R.id.goalDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart()
                            rightMargin = dip(10)}

                            textView {
                                text = "Goals"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }

                            goalsDetailAwayTextView = textView(R.string.na) {
                                id = R.id.goalDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) {
                                alignParentEnd()
                            }

                        }.lparams(width = matchParent, height = wrapContent) { topMargin = dip(18) }

                        relativeLayout {
                            id = R.id.relativeLayoutb

                            shotsHomeTextView = textView (R.string.na){
                                id = R.id.shotDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }

                            textView {
                                text = "Shots"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }

                            shotsAwayTextView = textView (R.string.na){
                                id = R.id.shotDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }

                        }.lparams(width = matchParent, height = wrapContent) { topMargin = dip(18) }

                        view {
                            backgroundColor = Color.BLACK
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(8)
                            bottomMargin = dip(8)
                        }

                        textView {
                            text = "Lineup"
                            textSize = 20f
                            this.gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = wrapContent) { Gravity.CENTER_HORIZONTAL }

                        // Team
                        relativeLayout {
                            keeperHomeTextView = textView(R.string.na) {
                                id = R.id.keepDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }

                            textView {
                                text = "Goal Keeper"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }

                            keeperAwayTextView = textView(R.string.na) {
                                id = R.id.keepDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }
                        }.lparams(width = wrapContent, height = wrapContent) { topMargin = dip(18) }

                        relativeLayout {
                            defHomeTextView = textView(R.string.na) {
                                id = R.id.defDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }

                            textView {
                                text = "Defences"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }

                            defAwayTextView = textView(R.string.na) {
                                id = R.id.defDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }
                        }.lparams(width = wrapContent, height = wrapContent) { topMargin = dip(18) }

                        relativeLayout {
                            midHomeTextView = textView (R.string.na){
                                id = R.id.midDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }

                            textView {
                                text = "Midfield"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }

                            midAwayTextView = textView (R.string.na){
                                id = R.id.midDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }
                        }.lparams(width = wrapContent, height = wrapContent) { topMargin = dip(18) }

                        relativeLayout {
                            fwdHomeTextView = textView (R.string.na){
                                id = R.id.fwdDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }
                            textView {
                                text = "Forward"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }
                            fwdAwayTextView = textView (R.string.na){
                                id = R.id.fwdDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }
                        }.lparams(width = wrapContent, height = wrapContent) { topMargin = dip(18) }

                        relativeLayout {
                            subHomeTextView = textView (R.string.na){
                                id = R.id.subDetailHome
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { alignParentStart() }
                            textView {
                                text = "Subtitutes"
                                textSize = 14f
                            }.lparams(width = wrapContent, height = wrapContent) { centerHorizontally() }
                            subAwayTextView = textView (R.string.na){
                                id = R.id.subDetailAway
                                textSize = 14f
                            }.lparams(width = dip(120), height = wrapContent) { alignParentEnd() }
                        }.lparams(width = wrapContent, height = wrapContent) { topMargin = dip(18) }
                    }
                }

            }
        }

        val intent = intent
        idText = intent.getStringExtra("id")
//        date = intent.getStringExtra("date")
        filePath = intent.getStringExtra("file")

//        dateTextView.setFormattedDate(date)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getEventDetail(idText)

        swipeRefreshDetail.onRefresh {
            presenter.getEventDetail(idText)
        }

    }

    override fun showdata(data: List<EventDetail>) {
//        toast("Loading...." )
        swipeRefreshDetail.isRefreshing = false
        dataDetail.clear()
        dataDetail.addAll(data)
//        info{ "detailnya "+dataDetail}
        dateTextView.setFormattedDateFav(dataDetail.map{ it.eventDate }[0].toString())

        teamHomeTextView.text = dataDetail.map{it.teamHome}[0].toString()
        teamAwayTextView.text = dataDetail.map{it.teamAway}[0].toString()


        if(dataDetail.map{it.teamHomeScore}[0] !== null){
            scoreHomeTextView.text = dataDetail.map{it.teamHomeScore}[0].toString()
            scoreAwayTextView.text = dataDetail.map{it.teamAwayScore}[0].toString()

            teamFormHomeTextView.text = dataDetail.map{it.teamHomeFormation}[0].toString()
            teamFormAwayTextView.text = dataDetail.map{it.teamAwayFormation}[0].toString()

            goalsDetailHomeTextView.text = dataDetail.map{it.teamHomeGoalDetail}[0].toString().replace(";","\n")
            goalsDetailAwayTextView.text = dataDetail.map{it.teamAwayGoalDetail}[0].toString().replace(";","\n")

            shotsHomeTextView.text = dataDetail.map{it.teamHomeShots}[0].toString()
            shotsAwayTextView.text = dataDetail.map{it.teamAwayShots}[0].toString()

            keeperHomeTextView.text = dataDetail.map{it.homeGoalKeeper}[0].toString().replace(";","\n")
            keeperAwayTextView.text = dataDetail.map{it.awayGoalKeeper}[0].toString().replace(";","\n")

            defHomeTextView.text = dataDetail.map{it.homeDef}[0].toString().replace(";","\n")
            defAwayTextView.text = dataDetail.map{it.awayDef}[0].toString().replace(";","\n")

            midHomeTextView.text = dataDetail.map{it.homeMid}[0].toString().replace(";","\n")
            midAwayTextView.text = dataDetail.map{it.awayMid}[0].toString().replace(";","\n")

            fwdHomeTextView.text = dataDetail.map{it.homeForward}[0].toString().replace(";","\n")
            fwdAwayTextView.text = dataDetail.map{it.awayForward}[0].toString().replace(";","\n")

            subHomeTextView.text = dataDetail.map{it.homeSub}[0].toString().replace(";","\n")
            subAwayTextView.text = dataDetail.map{it.awaySub }[0].toString().replace(";","\n")
        }

        event = EventSchedule(
                data[0].eventId,
                data[0].eventDate,
                data[0].teamHome,
                data[0].teamHomeScore,
                data[0].teamAway,
                data[0].teamAwayScore,
                filePath)

        presenter.getBadgeTeam(dataDetail.map{it.homeId}[0].toString(),1)
        presenter.getBadgeTeam(dataDetail.map{it.awayId}[0].toString(),2)

    }

    override fun showLoading() {
        progressBarDetail.visible()
    }

    override fun hideLoading() {
        progressBarDetail.invisible()
    }

    override fun loadImage(data: List<TeamFlag>,type:Int) {
        if(type == 1){
//            info { "viewnya Home "  + data.map { it.badge }[0].toString()}
            Glide.with(this).clear(logoHome)
            Glide.with(this).load(data.map { it.badge }[0].toString()).into(logoHome)
        }else if(type == 2 ){
//            info { "viewnya Away "  + data.map { it.badge }.toString()}
            Glide.with(this).clear(logoAway)
            Glide.with(this).load(data.map { it.badge }[0].toString()).into(logoAway)
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idText)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
            R.id.add_to_favorite -> {
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
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to event.eventId,
                        Favorite.EVENT_DATE to event.eventDate,
                        Favorite.HOME_TEAM to event.homeTeam,
                        Favorite.HOME_SCORE to event.homeScore,
                        Favorite.AWAY_TEAM to event.awayTeam,
                        Favorite.AWAY_SCORE to event.awayScore,
                        Favorite.FILE_NAME to event.fileName
                        )
            }
            snackbar(swipeRefreshDetail, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshDetail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE,"(EVENT_ID = {id})","id" to idText)
            }
            snackbar(swipeRefreshDetail, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshDetail, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}