package com.example.yeyson.rappiinterview.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yeyson.rappiinterview.R;
import com.example.yeyson.rappiinterview.entity.search.Movie;
import com.example.yeyson.rappiinterview.event.SearchDetailEvent;
import com.example.yeyson.rappiinterview.ui.activity.FilmDetailActivity;
import com.example.yeyson.rappiinterview.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AutoCompleteAdapter extends ArrayAdapter<Movie> {

    private List<Movie> list;
    List<Movie> filteredList = new ArrayList<>();
    private Context context;
    private RxBus bus;

    public AutoCompleteAdapter(Context context, List<Movie> list, RxBus rxBus) {
        super(context, 0, list);
        this.list = list;
        this.context = context;
        this.bus = rxBus;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new MoviesFilter(this, list);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item from filtered list.
        Movie item = list.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_row, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.movieTitle);
        TextView tvYear = convertView.findViewById(R.id.movieYear);

        tvTitle.setText(item.getTitle());
        tvYear.setText(item.getReleaseDate());
        convertView.setOnClickListener(view -> {
            bus.postSticky(new SearchDetailEvent(item));
            context.startActivity(new Intent(context, FilmDetailActivity.class));
        });

        return convertView;
    }
}