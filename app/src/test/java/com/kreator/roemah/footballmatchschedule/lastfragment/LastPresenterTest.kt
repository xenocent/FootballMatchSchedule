package com.kreator.roemah.footballmatchschedule.lastfragment

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.api.TheSportDBApi
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.EventScheduleResp
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LastPresenterTest {
    private lateinit var presenter: LastPresenter

    @Mock
    private
    lateinit var view: LastView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastPresenter(view,apiRepository,gson)
    }

    @Test
    fun getPastEventList() {
        val match: MutableList<EventSchedule> = mutableListOf()
        val response = EventScheduleResp(match)
        val league = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastEvent(league)),
                EventScheduleResp::class.java
        )).thenReturn(response)

        presenter.getPastEventList(league)

        verify(view).showLoading()
        verify(view).showLeagueList(match)
        verify(view).hideLoading()
    }
}