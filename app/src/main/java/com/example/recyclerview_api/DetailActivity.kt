package com.example.recyclerview_api

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_api.api.MovieDataResponse
import com.example.recyclerview_api.api.MovieRetrofitItf
import com.example.recyclerview_api.api.MovieRetrofitObj
import com.example.recyclerview_api.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity: AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    private val detailList = mutableListOf<MovieDataResponse.BoxOfficeResult.DailyBoxOffice?>()
    private val adapter by lazy { DetailRVAdapter(detailList)}
    private var movieData: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent로부터 데이터 받기
        val movieName = intent.getStringExtra("MOVIE_NAME")
        movieData = intent.getStringExtra("MOVIE_DATE")

        // 어댑터 연결
        binding.movieRv.adapter = adapter
        binding.movieRv.layoutManager = LinearLayoutManager(this)

        // 영화 정보 요청
        movieRequest(movieName)
    }

    fun movieRequest(movieName: String?){

        // 서비스 객체 생성
        val apiService = MovieRetrofitObj.getRetrofit().create(MovieRetrofitItf::class.java)

        // Call 객체 생성
        val inputData = movieData?.trim()?.replace(Regex(" "),"") ?: ""
        val movieCall = apiService.getmovieinfo(
            "API_KEY",
            inputData,
            "10",
            "N",
            "K",
        )

        movieCall.enqueue(object : Callback<MovieDataResponse> {
            override fun onResponse(call: Call<MovieDataResponse>, response: Response<MovieDataResponse>) {
                Log.d("SUCCESS", response.toString())
                val data = response.body()
                val movieinfo = data?.boxOfficeResult?.dailyBoxOfficeList

                if (!movieinfo.isNullOrEmpty()) {
                    val movie = movieinfo.find { it?.movieNm == movieName } // 응답 데이터클래스의 movieNm과 MainActivity에서 받은 movieName가 동일한지
                    if (movie != null) {
                        detailList.add(movie) // 데이터를 리스트에 추가
                        adapter.notifyDataSetChanged() // 어댑터에게 데이터가 변경됨을 알림
                    }
                }
            }

            override fun onFailure(call: Call<MovieDataResponse>, t: Throwable) {
                Log.d("FAILURE", t.message.toString())
            }
        })
    }
}