package com.kreator.roemah.footballmatchschedule.api

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        @Test
        fun testDoRequest() {
            val apiRepository = mock(ApiRepository::class.java)
            val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
            apiRepository.doRequest(url)
            verify(apiRepository).doRequest(url)
        }
    }
}