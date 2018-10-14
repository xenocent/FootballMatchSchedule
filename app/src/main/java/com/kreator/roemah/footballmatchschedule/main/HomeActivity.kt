package com.kreator.roemah.footballmatchschedule.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kreator.roemah.footballmatchschedule.R
import com.kreator.roemah.footballmatchschedule.R.id.*
import com.kreator.roemah.footballmatchschedule.favoritefragment.FavoriteFragment
import com.kreator.roemah.footballmatchschedule.lastfragment.LastFragment
import com.kreator.roemah.footballmatchschedule.nextfragment.NextFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationMenu: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            frameLayout = frameLayout {
                id = R.id.main_frame
            }.lparams(width = matchParent, height = matchParent) {
                above(R.id.bottom_layout_home)
            }

            linearLayout {
                id = R.id.bottom_layout_home
                orientation = LinearLayout.VERTICAL

                view {
                    backgroundResource = R.drawable.shadow
                }.lparams(width = matchParent, height = dip(4))

                bottomNavigationMenu = bottomNavigationView {
                    id = R.id.bottom_nav_home
                    itemBackgroundResource = android.R.color.white
                    itemIconTintList = ColorStateList.valueOf(R.drawable.nav_item_color_state)
                    itemTextColor = ColorStateList.valueOf(R.drawable.nav_item_color_state)
                    inflateMenu(R.menu.bottom_navigation_menu)
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams{
                alignParentBottom()
            }
        }

        bottomNavigationMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                last -> {
                    loadLastFragment(savedInstanceState)
                }
                next -> {
                    loadNextFragment(savedInstanceState)
                }
                fav -> {
                    loadFavoritesFragment(savedInstanceState)
                }

            }
            true
        }
        bottomNavigationMenu.selectedItemId = last

    }

    private fun loadLastFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, LastFragment(), LastFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, NextFragment(), NextFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                    .commit()
        }
    }
}
