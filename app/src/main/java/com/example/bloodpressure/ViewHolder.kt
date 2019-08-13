package com.example.bloodpressure

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.one_result.view.*

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dateText: TextView?=null
    var minMaxText: TextView?=null
    var pulseText:TextView?=null
    init {
        dateText = itemView.dateText
        minMaxText = itemView.minMaxText
        pulseText = itemView.pulseText
    }
}