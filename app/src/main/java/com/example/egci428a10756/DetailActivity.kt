package com.example.egci428a10756

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.random.Random

class DetailActivity : AppCompatActivity() {
    private var message=""
    private var status=""
    private var date=""
    val num = Random.nextInt(0, 9).toString()
    val jsonURL =
        "https://egci428-d78f6-default-rtdb.firebaseio.com/fortunecookies/" + num + ".json"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val pressButton = findViewById<Button>(R.id.pressBtn)

        backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            finish()
        }
        val onImageText = findViewById<TextView>(R.id.imageText)
        val resultDetail = findViewById<TextView>(R.id.msgText)
        val dateText = findViewById<TextView>(R.id.dateText)
        dateText.setText("Date:  ")


        fun fetchJson(){
            val request = Request.Builder()
                .url(jsonURL)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                            if(name=="Date"){
                                date=value
                            }
                        }
                        val body = response.body!!.string()
                        if(body == null) return@use
                        val gson = GsonBuilder().create()
                        val cookieObj = gson.fromJson(body,
                            CookieData::class.java)
                        message=cookieObj.message
                        status=cookieObj.status

                        dateText.text = date
                        resultDetail.text = "Result: "+ message
                        onImageText.text = message
                        pressButton.setText("Save")

                    }
                }
            })
        }

        pressButton.setOnClickListener {
            if(pressButton.text.equals("Make a Wish")){
                Toast.makeText(this,"Waiting...", Toast.LENGTH_SHORT).show()
                fetchJson()
            } else if(pressButton.text.equals("Save")){
                var sendintent = Intent(this,MainActivity::class.java)
                sendintent.putExtra("msg",message)
                sendintent.putExtra("status",status)
                sendintent.putExtra("date",date)
                setResult(1,sendintent)
                finish()
            }
        }
    }
}