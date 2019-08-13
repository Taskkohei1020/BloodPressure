package com.example.bloodpressure

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import io.realm.RealmResults

class CustomRecyclerViewAdapter(realmResults:RealmResults<BloodPress>) :RecyclerView.Adapter<ViewHolder>(){
    private val rResults:RealmResults<BloodPress> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, position: Int):ViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result,parent,false)
        val viewholder = ViewHolder(view)
        return viewholder
    }

    override fun getItemCount(): Int{
        return rResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int){
        val bloodPress = rResults[position]
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm",bloodPress?.datetime)
        holder.minMaxText?.text = "${bloodPress?.max.toString()}/${bloodPress?.min.toString()}"
        holder.pulseText?.text = bloodPress?.pulse.toString()
        holder.itemView.setBackgroundColor(if(position % 2 == 0) Color.LTGRAY else Color.WHITE)
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", bloodPress?.id)
            it.context.startActivity(intent)
        }
    }

}