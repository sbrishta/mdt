package com.sbr.mdt

import com.sbr.mdt.api.retrofit.MDTAPI
import com.sbr.mdt.transfer.data.payees.PayeesGetResponse
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BalanceGetApiTest : BaseServerTest() {
    private lateinit var apiService:MDTAPI
    @Before
    fun setup() {
        val url = mockWebServer.url("/")

        apiService =Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MDTAPI::class.java)

    }
    @Test
    fun balanceApiSuccess() {

        enqueue("balance_success.json")
        runBlocking {
            val apiResponse = apiService.getBalance("authorisationtoken")
            assertNotNull(apiResponse)
            assertTrue("The list was empty",apiResponse.isSuccessful)
            assertEquals("The expected data list not received",93395.0,
                apiResponse.body()?.balance)
        }
    }
    @Test
    fun balanceApiFailure() {

        enqueue("balance_failure.json")
        runBlocking {
            val apiResponse = apiService.getBalance("authorisationtokenold")
            assertNotNull(apiResponse)
            assertEquals(true,
                apiResponse.body()?.status.equals("failed"
                ))
        }
    }

    @After
    fun terDown(){
        mockWebServer.shutdown()
    }
}