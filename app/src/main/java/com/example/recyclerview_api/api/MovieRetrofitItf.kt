package com.example.recyclerview_api.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRetrofitItf {
    @GET("kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    fun getmovieinfo(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String, // 조회하고자 하는 날짜를 yyyymmdd 형식으로 입력
        @Query("itemPerPage") itemPerPage: String, // 결과 ROW의 개수를 지정 (default: "10", 최대: "10")
        @Query("multiMovieYn") multiMovieYn: String, // "Y" : 다양성 영화, "N": 상업영화 (default: 전체)
        @Query("repNationCd") repNationCd: String, // 상영지역별로 조회 가능, 지역코드는 공통코드 조회 서비스에서 "0105000000" 로서 조회된 지역코드이다 (default: 전체)
    ): Call<MovieDataResponse>
}