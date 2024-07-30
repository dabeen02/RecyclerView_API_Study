package com.example.recyclerview_api

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_api.api.MovieDataResponse
import com.example.recyclerview_api.databinding.ItemMovieBinding

class MovieRVAdapter(val items: MutableList<MovieDataResponse.BoxOfficeResult.DailyBoxOffice?>): RecyclerView.Adapter<MovieRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRVAdapter.ViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieRVAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieDataResponse.BoxOfficeResult.DailyBoxOffice?){
            binding.movieNmTv.text = "영화이름:${item?.movieNm}"
            binding.openDtTv.text = "개봉일:${item?.openDt}일"
            binding.salesAmtTv.text = "매출액:${item?.salesAmt}원"
            binding.audiChangeTv.text = "전일대비:${item?.audiChange}%"
            binding.salseAccTv.text = "누적매출:${item?.salesAcc}원"
            binding.audiAcc.text = "누적관객수:${item?.audiAcc}명"

            // 클릭 이벤트 추가
            binding.root.setOnClickListener {
                val context = binding.root.context

                // 영화 제목 클릭 시 DetailActivity로 이동
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("MOVIE_NAME", item?.movieNm) // 영화제목 전달
                    putExtra("MOVIE_DATE", (context as MainActivity).inputData) // 입력한 날짜 전달
                }
                context.startActivity(intent)
            }
        }
    }
}