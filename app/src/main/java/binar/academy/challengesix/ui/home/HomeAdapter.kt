package binar.academy.challengesix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import binar.academy.challengesix.data.remote.modal.Result
import binar.academy.challengesix.databinding.ItemMovieBinding
import com.bumptech.glide.Glide

class HomeAdapter (private val movie : List<Result>): RecyclerView.Adapter<HomeAdapter.ViewHolder>()  {
    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text = movie[position].title
        holder.binding.tvGenreSide.text = movie[position].releaseDate
        holder.binding.itemConstraintlayout.setOnClickListener {
            val movie = Result(
                posterPath = movie[position].posterPath,
                title = movie[position].title,
                releaseDate = movie[position].releaseDate
            )
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie))
        }
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/original/"+movie[position].posterPath)
            .into(holder.binding.recyclerImageView)
    }

    override fun getItemCount(): Int {
        return movie.size
    }


}