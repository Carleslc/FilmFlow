package com.and119_idi.myfilmdatabase.controller;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.Film;

import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * Created by Carlos Lázaro Costa on 16/12/16.
 */
public class FilmsRecyclerViewAdapter extends RecyclerView.Adapter<FilmViewHolder> {

    private SortedList<Film> mMoviesList;
    private OnItemClickListener mOnItemClickListener;

    private Class<? extends FilmViewHolder> mViewHolderClass;
    private int mRowLayoutResource;

    public FilmsRecyclerViewAdapter() {
        this(R.layout.film_row_simple, FilmViewHolder.class);
    }

    FilmsRecyclerViewAdapter(int rowLayoutResource,
                             Class<? extends FilmViewHolder> viewHolderClass) {
        mViewHolderClass = viewHolderClass;
        mRowLayoutResource = rowLayoutResource;
        mMoviesList = new SortedList<>(Film.class, getSortCallback());
    }

    private static View inflateFrom(ViewGroup viewGroup, int resource) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resource, null);
    }

    protected SortedList.Callback<Film> getSortCallback() {
        return new SortedListAdapterCallback<Film>(this) {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitle().compareToIgnoreCase(f2.getTitle());
            }

            @Override
            public boolean areContentsTheSame(Film f1, Film f2) {
                return f1.getTitle().equals(f2.getTitle());
            }

            @Override
            public boolean areItemsTheSame(Film f1, Film f2) {
                return f1.getId() == f2.getId();
            }
        };
    }

    @Override
    public void onBindViewHolder(FilmViewHolder viewHolder, int i) {
        viewHolder.bindTo(mMoviesList.get(i));
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    private FilmViewHolder getViewHolder(ViewGroup viewGroup, int resource, Class<? extends FilmViewHolder> viewHolderClass) {
        try {
            Constructor<? extends FilmViewHolder> viewHolderConstructor = viewHolderClass.getConstructor(View.class, SortedList.class, OnItemClickListener.class);
            return viewHolderConstructor.newInstance(inflateFrom(viewGroup, resource), mMoviesList, mOnItemClickListener);
        } catch (Exception e) {
            Log.wtf(FilmsRecyclerViewAdapter.class.getSimpleName(), e);
            return null;
        }
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return getViewHolder(viewGroup, mRowLayoutResource, mViewHolderClass);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setFilms(Collection<Film> films) {
        mMoviesList.clear();
        mMoviesList.addAll(films);
        notifyDataSetChanged();
    }

}
