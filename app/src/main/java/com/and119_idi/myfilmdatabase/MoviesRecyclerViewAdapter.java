package com.and119_idi.myfilmdatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by albert on 29/11/16.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.CustomViewHolder> {
    private List<Film> moviesList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MoviesRecyclerViewAdapter(Context context, List<Film> moviesList) {
        this.moviesList = moviesList;
        this.mContext = context;
    }



    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Film film = moviesList.get(i);

        //Conseguimos los datos para meterlos al view holder

        customViewHolder.textView.setText(film.getTitle());

        //Aqui creamos y implementamos un OnClickListener. En el onClick() usamos nuestro propio interface OnItemClickListener
        //(que hemos seteado a esta clase con una implementación desde MoviesFragment)
        //llamando a onItemClickListener.onItemClick(film).

        //La fiesta de los listeners


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(film);
            }
        };

        //Ahora ,al pulsar el titulo de la peli, pasan cosas.
        customViewHolder.textView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (moviesList != null) {
            ret = moviesList.size();
        }
        return ret;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }

}
