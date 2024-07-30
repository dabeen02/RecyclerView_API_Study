package com.example.recyclerview_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_api.api.MovieDataResponse
import com.example.recyclerview_api.databinding.ItemMovieDetailBinding

class DetailRVAdapter(val items: MutableList<MovieDataResponse.BoxOfficeResult.DailyBoxOffice?>): RecyclerView.Adapter<DetailRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRVAdapter.ViewHolder {
        val binding: ItemMovieDetailBinding = ItemMovieDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailRVAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ItemMovieDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieDataResponse.BoxOfficeResult.DailyBoxOffice?){
            binding.audienceNbTv.text = "해당일의 관객수: ${item?.audiCnt}"
            binding.screenNbTv.text = "해당일자에 상영된 횟수:: ${item?.showCnt}"
        }
    }

}