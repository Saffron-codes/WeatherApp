package com.sddev.weather.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.sddev.weather.R
import com.sddev.weather.databinding.FragmentHourlyDataListBinding
import com.sddev.weather.databinding.FragmentWeatherBinding
import com.sddev.weather.ui.adapters.HourlyData
import com.sddev.weather.ui.adapters.HourlyDataListAdapter
import com.sddev.weather.ui.viewmodels.CurrentWeatherViewModel
import com.sddev.weather.ui.viewmodels.WeatherState
import com.sddev.weather.utils.createHourlyDataList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HourlyDataListFragment : Fragment() {


    private lateinit var type:String
    private lateinit var hourlyData: List<HourlyData>
    private lateinit var binding: FragmentHourlyDataListBinding
    private val viewModel:CurrentWeatherViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            type = it?.getString("type").toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHourlyDataListBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherState.observe(viewLifecycleOwner){state->
            run {
                if (state is WeatherState.Success) {
                    val hourly = state.data.hourly
                    when(type){
                        "wind_speed" -> {
                            hourlyData = createHourlyDataList(hourly.time.orEmpty(),hourly.windSpeed)
                        }
                        "humidity" -> {
                            hourlyData = createHourlyDataList(hourly.time.orEmpty(),hourly.humidity)
                        }
                        "visibility" -> {
                            hourlyData = createHourlyDataList(hourly.time.orEmpty(),hourly.visibility)
                        }
                    }

                    val myAdapter = HourlyDataListAdapter(hourlyData,type)
                    binding.rvHum.layoutManager = LinearLayoutManager(context)
                    binding.rvHum.adapter = myAdapter
                }
            }
        }
    }
}