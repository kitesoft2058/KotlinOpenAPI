package com.kitesoft.kotlinopenapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    val btn:Button by lazy { findViewById(R.id.btn) }

    var naverShoppingApiResponse:NaverShoppingApiResponse?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener { Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show() }

        findViewById<Button>(R.id.btn).setOnClickListener { Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show() }

        findViewById<Button>(R.id.btn).setOnClickListener { requestNaverSearchApi() }


    }

    fun requestNaverSearchApi(){

        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://openapi.naver.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()

        retrofit.create(NaverApiRetrofitService::class.java).searchData("패딩").enqueue(object : Callback<NaverShoppingApiResponse>{
            override fun onResponse(call: Call<NaverShoppingApiResponse>,response: Response<NaverShoppingApiResponse>) {
                val shoppingResponse: NaverShoppingApiResponse?= response.body()

                val buffer:StringBuffer= StringBuffer()
                buffer.append("total : " + shoppingResponse?.total +"\n")
                buffer.append("display : " + shoppingResponse?.display + "\n")
                buffer.append("items size : " + shoppingResponse?.items?.size +"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.title+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.link+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.image+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.lprice+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.hprice+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.maker+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.brand+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.mallName+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.productId+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.productType+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.category1+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.category2+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.category3+"\n")
                buffer.append(shoppingResponse?.items?.get(0)?.category4+"\n")

                AlertDialog.Builder(this@MainActivity).setMessage(buffer.toString()).show()
            }

            override fun onFailure(call: Call<NaverShoppingApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "서버오류 : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })

//        retrofit.create(NaverApiRetrofitService::class.java).searchDataByString("패딩").enqueue(object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val s:String?= response.body()
//                AlertDialog.Builder(this@MainActivity).setMessage(s).show()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "서버오류 : " + t.message, Toast.LENGTH_SHORT).show()
//            }
//        })


    }
}