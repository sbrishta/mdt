package com.sbr.mdt

import com.sbr.mdt.api.retrofit.MDTAPI
import com.sbr.mdt.transfer.data.payees.PayeesGetResponse
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PayeeGetApiTest : BaseServerTest() {
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
    fun payeeApiSuccess() {

        enqueue("payees_success.json")
        runBlocking {
            val apiResponse = apiService.getPayees("authorisationtoken")
            assertNotNull(apiResponse)
            assertTrue("The list was empty",apiResponse.isSuccessful)
            assertEquals("The expected data list not received",5,
                apiResponse.body()?.data!!.size
            )
        }
    }

    @Test
    fun payeeApiFailure() {

        enqueue("payees_failure.json")
        runBlocking {
            val apiResponse = apiService.getPayees("authorisationtokenold")
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