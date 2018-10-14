package com.kreator.roemah.footballmatchschedule.model

data class Favorite(val id:Long?,
        val eventId: String?,
                    val eventDate:String?,
                    val homeTeam: String?,
                    val homeScore: String?,
                    val awayTeam: String?,
                    val awayScore: String?,
                    val fileName: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val FILE_NAME: String = "FILE_NAME"
    }
}