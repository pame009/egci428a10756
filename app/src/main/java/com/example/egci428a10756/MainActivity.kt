package com.example.egci428a10756

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    lateinit var array:ArrayList<CookieData>
    lateinit var adapter:CookieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val toolBar = findViewById<Toolbar>(R.id.toolBar)

        toolBar.setTitle("Fortune Cookies")
        setSupportActionBar(toolBar)

        val addButton = findViewById<ImageButton>(R.id.addBtn)
        addButton.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            startActivityForResult(intent,1)
        }

        array = ArrayList()
        adapter = CookieAdapter(applicationContext,R.layout.detail,array)
        listView.adapter = adapter

        listView.setOnItemClickListener { adapterView, view, position, id ->
            array.removeAt(position)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==1){
            var msg = data?.getStringExtra("msg").toString()
            var status = data?.getStringExtra("status").toString()
            var date =data?.getStringExtra("date").toString()
            array.add(CookieData(msg,status,date))
            adapter.notifyDataSetChanged()
        }
    }
}


