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

class TransactionsGetApiTest : BaseServerTest() {
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
    fun transactionApiSuccess() {

        enqueue("transaction_success.json")
        runBlocking {
            val apiResponse = apiService.getTransactions("authorisationtokenold")
            assertNotNull(apiResponse)
            assertTrue("The list was empty",apiResponse.isSuccessful)
            assertEquals("The expected data list not received",10,
                apiResponse.body()?.data!!.size)
            assertEquals("The expected transaction id does not match","6228c3ba83bb3d0198d89050",
                apiResponse.body()?.data!!.get(0).transactionId)
        }
    }

    @Test
    fun transactionApiFailure() {

        enqueue("transaction_failure.json")
        runBlocking {
            val apiResponse = apiService.getTransactions("")
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