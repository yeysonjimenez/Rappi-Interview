package com.example.yeyson.rappiinterview.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.toprated.TopRatedResults;
import com.example.yeyson.rappiinterview.event.TopRatedDetailEvent;
import com.example.yeyson.rappiinterview.ui.activity.FilmDetailActivity;
import com.example.yeyson.rappiinterview.util.Constants;
import com.example.yeyson.rappiinterview.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedRecyclerViewAdapter extends RecyclerView.Adapter<TopRatedRecyclerViewAdapter.ViewHolder> {

    private List<TopRatedResults> topRatedResultsList;
    private Context context;
    private RxBus bus;

    @Inject
    public TopRatedRecyclerViewAdapter(List<TopRatedResults> topRatedResultsList, Context context,
                                       RxBus bus) {
        this.topRatedResultsList = topRatedResultsList;
        this.context = context;
        this.bus = bus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopRatedResults results = topRatedResultsList.get(position);

        holder.title.setText(results.getTitle());
        holder.year.setText(results.getRelease_date());

        Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + Constants.IMAGE_W185 + results.getPoster_path())

                .into(holder.poster);

        holder.poster.setOnClickListener(v -> {
            bus.postSticky(new TopRatedDetailEvent(results));
            context.startActivity(new Intent(context, FilmDetailActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return topRatedResultsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.moviePoster)
        ImageView poster;
        @BindView(R.id.movieTitle)
        TextView title;
        @BindView(R.id.movieYear)
        TextView year;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
