package com.example.egci428a10756

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CookieAdapter(val mContext: Context, val layoutResId: Int,
                    val messageList: ArrayList<CookieData> ): ArrayAdapter<CookieData>(mContext, layoutResId, messageList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(layoutResId, null)
        val msgTextView = view.findViewById<TextView>(R.id.msgResult)
        var status = messageList[position].status
        if(status.equals("positive")){
            msgTextView.setTextColor(Color.BLUE)
        } else if(status.equals("negative")){
            msgTextView.setTextColor(Color.RED)
        }
        msgTextView.text = messageList[position].message
        val dateTextView = view.findViewById<TextView>(R.id.dateResult)
        dateTextView.text = messageList[position].date

        return view
    }
}