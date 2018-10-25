package com.kreator.roemah.footballmatchschedule.nextfragment

import com.google.gson.Gson
import com.kreator.roemah.footballmatchschedule.api.ApiRepository
import com.kreator.roemah.footballmatchschedule.model.EventSchedule
import com.kreator.roemah.footballmatchschedule.model.EventScheduleResp
import com.kreator.roemah.footballmatchschedule.util.TestContextProvider
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextPresenterTest {
    private lateinit var presenter: NextPresenter
    @Mock
    private
    lateinit var view: NextView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getNextEventList() {
        val match: MutableList<EventSchedule> = mutableListOf()
        val response = EventScheduleResp(match)
        val league = "4328"

        runBlocking {
            Mockito.`when`(gson.fromJson(apiRepository.
                    doRequest("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"),
                    EventScheduleResp::class.java
            )).thenReturn(response)
        }

        presenter.getNextEventList(league)

        Mockito.verify(view)?.showLoading()

        if(match.isNotEmpty()){
            Mockito.verify(view)?.showLeagueListNext(match)
            Mockito.verify(view)?.hideLoading()
        }
    }
}