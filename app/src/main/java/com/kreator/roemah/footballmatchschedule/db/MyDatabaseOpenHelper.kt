package com.kreator.roemah.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.kreator.roemah.footballmatchschedule.model.Favorite
import com.kreator.roemah.footballmatchschedule.model.FavoriteDetail
import com.kreator.roemah.footballmatchschedule.model.FavoritesTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorites.db", null, 3) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favorite.TABLE_FAVORITE,true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.EVENT_ID to TEXT+ UNIQUE,
                Favorite.HOME_TEAM to TEXT,
                Favorite.HOME_SCORE to TEXT,
                Favorite.AWAY_TEAM to TEXT,
                Favorite.AWAY_SCORE to TEXT,
                Favorite.EVENT_DATE to TEXT,
                Favorite.FILE_NAME to TEXT)

        db.createTable(FavoriteDetail.TABLE_FAVORITE_DETAIL,true,
                FavoriteDetail.ID to INTEGER+ PRIMARY_KEY + AUTOINCREMENT,
                FavoriteDetail.EVENT_ID to TEXT+ UNIQUE,
                FavoriteDetail.EVENTDATE to TEXT,
                FavoriteDetail.TEAMHOMEID to TEXT,
                FavoriteDetail.TEAMHOME to TEXT,
                FavoriteDetail.TEAMHOMEFORMATION to TEXT,
                FavoriteDetail.TEAMHOMESCORE to TEXT,
                FavoriteDetail.TEAMHOMEGOALDETAIL to TEXT,
                FavoriteDetail.TEAMHOMESHOTS to TEXT,
                FavoriteDetail.HOMEKEEPER to TEXT,
                FavoriteDetail.HOMEDEF to TEXT,
                FavoriteDetail.HOMEMID to TEXT,
                FavoriteDetail.HOMEFORWARD to TEXT,
                FavoriteDetail.HOMESUB to TEXT,
                FavoriteDetail.TEAMAWAYID to TEXT,
                FavoriteDetail.TEAMAWAY to TEXT,
                FavoriteDetail.TEAMAWAYFORMATION to TEXT,
                FavoriteDetail.TEAMAWAYSCORE to TEXT,
                FavoriteDetail.TEAMAWAYGOALDETAIL to TEXT,
                FavoriteDetail.TEAMAWAYSHOTS to TEXT,
                FavoriteDetail.AWAYKEEPER to TEXT,
                FavoriteDetail.AWAYDEF to TEXT,
                FavoriteDetail.AWAYMID to TEXT,
                FavoriteDetail.AWAYFORWARD to TEXT,
                FavoriteDetail.AWAYSUB to TEXT)

        db.createTable(FavoritesTeam.TABLE_FAVORITE_TEAM, true,
                FavoritesTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoritesTeam.TEAM_ID to TEXT + UNIQUE,
                FavoritesTeam.TEAM_NAME to TEXT,
                FavoritesTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE,true)
        db.dropTable(FavoriteDetail.TABLE_FAVORITE_DETAIL,true)
        db.dropTable(FavoritesTeam.TABLE_FAVORITE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)