package com.sddev.weather.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.sddev.weather.R
import com.sddev.weather.data.network.RetrofitClient
import com.sddev.weather.data.model.WeatherResponse
import com.sddev.weather.databinding.FragmentWeatherBinding
import com.sddev.weather.databinding.WeatherInfoCardBinding
import com.sddev.weather.ui.viewmodels.CurrentWeatherViewModel
import com.sddev.weather.ui.viewmodels.WeatherState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment() {


    private lateinit var binding:FragmentWeatherBinding
    private val viewModel:CurrentWeatherViewModel by activityViewModels()


    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadWeatherData()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }


    @SuppressLint("UseSupportActionBar")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val myList = listOf(
            mapOf("type" to "wind_speed","layout" to binding.windSpeedLL),
            mapOf("type" to "humidity","layout" to binding.humidityLL),
            mapOf("type" to "visibility","layout" to binding.visibilityLL)
        )

        viewModel.weatherState.observe(viewLifecycleOwner){state->
            when (state) {
                is WeatherState.Loading -> {
                    binding.pb.visibility = View.VISIBLE
                }

                is WeatherState.Error -> {

                }
                is WeatherState.Success -> {
                    val temperature:Int = state.data.currentWeather.temperature.toInt()
                    val windSpeed:Int = state.data.currentWeather.windSpeed.toInt()
                    val humidity:Int = state.data.hourly.humidity[0]
                    val visibility:Double = state.data.hourly.visibility[0] /1000.0
                    val comment:String = generateWeatherComment(temperature)


                    binding.tvTemperature.text = String.format("%d°", temperature)
                    binding.dailySummary.text = comment
                    binding.date.text = getCurrentDateFormatted()


                    binding.windTV.text = String.format("%d mp/h",windSpeed)
                    binding.humidityTV.text = humidity.toString().plus("%")
                    binding.visibilityTV.text = String.format("%.1fm",visibility)
                    setStatusBarColor(R.color.sunny)
                    binding.pb.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE


                    myList.forEach {
                        val layout = it["layout"] as LinearLayout
                        val type = it["type"] as String
                        layout.setOnClickListener {
                            navigateToHourlyList(type)
                        }
                    }
                }
            }
        }

    }


    private fun setStatusBarColor(colorRes:Int){
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //window.statusBarColor = resources.getColor(colorRes, theme)
    }

    private fun generateWeatherComment(temperature: Int): String {
        val comment = when {
            temperature >= 35 -> "It's scorching hot! Stay hydrated and seek shade."
            temperature >= 30 -> "It feels hot because of the sun.Apply sunscreen if going out."
            temperature >= 25 -> "It's warm and pleasant.Enjoy the weather!"
            temperature >= 15 -> "It's a bit cool. Consider wearing a light jacket."
            temperature >= 5 -> "It's chilly.Wear a jacket or a sweater."
            else -> "It's freezing cold! Bundle up and stay warm."
        }

        // Replace all occurrences of the full stop with a full stop followed by a new line.
        return comment.replace(".", ".\n")
    }

    private fun getCurrentDateFormatted(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun navigateToHourlyList(destination:String){
        val action = WeatherFragmentDirections.actionWeatherFragmentToHourlyDataListFragment(destination)
        findNavController().navigate(action)
    }
}