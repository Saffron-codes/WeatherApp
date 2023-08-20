package com.sddev.weather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sddev.weather.R
import com.sddev.weather.utils.DateUtils

data class HourlyData(
    val time:String,
    val data:Any
)
class HourlyDataListAdapter(private val data:List<HourlyData>,private val type:String): RecyclerView.Adapter<HourlyDataListAdapter.ViewHolder>() {


    private val dateUtils = DateUtils()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val windSpeedTV: TextView
        val timeTV:TextView
        var hourlyIcon:ImageView

        init {
            // Define click listener for the ViewHolder's View
            windSpeedTV = view.findViewById(R.id.windSpeedText)
            timeTV = view.findViewById(R.id.timeText)
            hourlyIcon = view.findViewById(R.id.hourlyInfoIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_info_card, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.windSpeedTV.text = dateUtils.formatTimeDifference(data[position].time)

        when (type){
            "humidity" -> {
                holder.timeTV.text = String.format("%d%%",data[position].data)
                holder.hourlyIcon.setImageResource(R.drawable.water_drop)
            }
            "wind_speed" -> {
                holder.timeTV.text = String.format("%.1fkm/h",data[position].data)
                holder.hourlyIcon.setImageResource(R.drawable.airewave)
            }
            "visibility" -> {
                holder.timeTV.text = String.format("%.1fm",(data[position].data).toString().toInt()/1000.0)
                holder.hourlyIcon.setImageResource(R.drawable.visibility)
            }
        }
    }
}