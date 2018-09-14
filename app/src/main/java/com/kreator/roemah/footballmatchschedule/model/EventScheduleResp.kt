package com.kreator.roemah.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class EventScheduleResp(
        @SerializedName("events")
        val EventSchedule:List<EventSchedule>
)