package com.kreator.roemah.footballmatchschedule.detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.kreator.roemah.footballmatchschedule.model.EventDetail
import com.kreator.roemah.footballmatchschedule.model.TeamDetail
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity() {

    private var idText: String = ""
    private var date: String = ""
    private var filePatj: String = ""
    lateinit var idTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var scoreHomeTextView: TextView
    lateinit var scoreAwayTextView: TextView
    lateinit var teamHomeTextView: TextView
    lateinit var teamAwayTextView: TextView
    lateinit var teamFormHomeTextView: TextView
    lateinit var teamFormAwayTextView: TextView
    lateinit var shotsHomeTextView: TextView
    lateinit var shotsAwayTextView: TextView
    lateinit var goalsDetailHomeTextView: TextView
    lateinit var goalsDetailAwayTextView: TextView
    lateinit var keeperHomeTextView: TextView
    lateinit var defHomeTextView: TextView
    lateinit var midHomeTextView: TextView
    lateinit var fwdHomeTextView: TextView
    lateinit var subHomeTextView: TextView
    lateinit var keeperAwayTextView: TextView
    lateinit var defAwayTextView: TextView
    lateinit var midAwayTextView: TextView
    lateinit var fwdAwayTextView: TextView
    lateinit var subAwayTextView: TextView

    lateinit var file: TextView

    private var data: MutableList<EventDetail> = mutableListOf<EventDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            topPadding = 20
            bottomPadding = dip(10)
            gravity = Gravity.CENTER_HORIZONTAL

            dateTextView = textView() {
                padding = dip(10)
                textSize = 14f
                textColor = Color.BLUE
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }.lparams(width = matchParent, height = wrapContent)

            //  score
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                scoreHomeTextView = textView {
                    text = "0"
                    textSize = 20f
                    gravity = Gravity.START
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                textView {
                    text = "VS"
                    textSize = 20f
                    gravity = Gravity.CENTER_HORIZONTAL
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                scoreAwayTextView = textView {
                    text = "0"
                    textSize = 20f
                    gravity = Gravity.LEFT
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }.lparams(width = matchParent, height = wrapContent)

            // team
            linearLayout {
                orientation = LinearLayout.VERTICAL
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER

                    linearLayout {
                        teamHomeTextView = textView {
                            text = "Home"
                            textSize = 14f
                            gravity = Gravity.LEFT
                        }
                        teamAwayTextView = textView {
                            text = "Away"
                            textSize = 14f
                            gravity = Gravity.RIGHT
                        }
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER
                        teamFormHomeTextView = textView {
                            textSize = 14f
                            text = "formation"
                        }
                        teamFormAwayTextView = textView {
                            text = "wformation2"
                            textSize = 14f

                        }
                    }
                }.lparams(width = matchParent, height = wrapContent)

                //first block (goals detail)
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    // player goals
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        goalsDetailHomeTextView = textView {
                            text = "home"
                            textSize = 14f
                        }

                        textView {
                            text = "Goals"
                            textSize = 14f
                        }

                        goalsDetailAwayTextView = textView {
                            text = "Away"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                    // number shots
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        shotsHomeTextView = textView {
                            text = "home shots"
                            textSize = 14f
                        }

                        textView {
                            text = "Shots"
                            textSize = 14f
                        }

                        shotsAwayTextView = textView {
                            text = "away shots"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)

                //second block (players detail)
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER

                    textView {
                        text = "Lineup"
                        textSize = 20f
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = matchParent, height = wrapContent)

                    // Keeper
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        keeperHomeTextView = textView {
                            text = "kep"
                            textSize = 14f
                        }

                        textView {
                            text = "Goal Keeper"
                            textSize = 14f
                        }

                        keeperAwayTextView = textView {
                            text = "kep"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                    // Deff
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        defHomeTextView = textView {
                            text = "def"
                            textSize = 14f
                        }

                        textView {
                            text = "Defences"
                            textSize = 14f
                        }

                        defAwayTextView = textView {
                            text = "def"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                    //mid
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        midHomeTextView = textView {
                            text = "mid"
                            textSize = 14f
                        }

                        textView {
                            text = "Midfield"
                            textSize = 14f
                        }

                        midAwayTextView = textView {
                            text = "mid"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                    // forward
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        fwdHomeTextView = textView {
                            text = "fwd"
                            textSize = 14f
                        }
                        textView {
                            text = "Forward"
                            textSize = 14f
                        }
                        fwdAwayTextView = textView {
                            text = "fwd"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                    // subtitue
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER

                        subHomeTextView = textView {
                            text = "sub"
                            textSize = 14f
                        }
                        textView {
                            text = "Subtitutes"
                            textSize = 14f
                        }
                        subAwayTextView = textView {
                            text = "sub"
                            textSize = 14f
                        }
                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = wrapContent)

                idTextView = textView() {
                    textSize = 20f
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    bottomPadding = dip(20)
                }


                file = textView() {
                    padding = dip(10)
                    textSize = 14f
                }
            }
        }


        val intent = intent
        idText = intent.getStringExtra("id")
        date = intent.getStringExtra("date")
        filePatj = intent.getStringExtra("file")

        idTextView.text = idText
        dateTextView.text = date
        file.text = filePatj
    }

    fun showdata(data:List<EventDetail>){
        

    }
}