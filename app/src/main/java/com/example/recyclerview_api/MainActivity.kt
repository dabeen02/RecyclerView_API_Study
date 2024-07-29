package com.example.recyclerview_api

import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_api.api.MovieDataResponse
import com.example.recyclerview_api.api.MovieRetrofitItf
import com.example.recyclerview_api.api.MovieRetrofitObj
import com.example.recyclerview_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter by lazy { MovieRVAdapter(dataList) }
    private val dataList = mutableListOf<MovieDataResponse.BoxOfficeResult.DailyBoxOffice?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieRv.adapter = adapter
        binding.movieRv.layoutManager = LinearLayoutManager(this)

        binding.searchBt.setOnClickListener {
            movieRequest()
        }
    }

    fun movieRequest(){

        // 서비스 객체 생성
        val apiService = MovieRetrofitObj.getRetrofit().create(MovieRetrofitItf::class.java)

        // Call 객체 생성
        val inputData = binding.movieDateEt.text.trim().replace(Regex(" "),"")
        val movieCall = apiService.getmovieinfo(
            "API_KEY",
            inputData,
            "10",
            "N",
            "K",
        )

        if(!dataList.isEmpty()){
            dataList.clear()
        }
        movieCall.enqueue(object: Callback<MovieDataResponse>{
            override fun onResponse(call: Call<MovieDataResponse>, response: Response<MovieDataResponse>) {
                Log.d("SUCCESS", response.toString())
                val data = response.body()

                val movieinfo = data?.boxOfficeResult?.dailyBoxOfficeList

                if(!movieinfo.isNullOrEmpty()){
                    movieinfo?.let {info ->
                        info.forEach{
                            dataList.add(it)
                            Log.d("DATALIST/SUCCESS", dataList.toString())
                        }
                    }
                    adapter.notifyDataSetChanged() // 어댑터에게 변경됨을 알려줌
                }
            }

            override fun onFailure(call: Call<MovieDataResponse>, t: Throwable) {
                Log.d("FAILURE", t.message.toString())
            }
        })
    }
}