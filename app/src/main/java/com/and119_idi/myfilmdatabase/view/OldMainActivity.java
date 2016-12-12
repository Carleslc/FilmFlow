package com.and119_idi.myfilmdatabase.view;


import android.app.ListActivity;

// TODO: Remove this class if RecyclerView on MainActivity is already completed
public class OldMainActivity extends ListActivity {
    /*private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_main_layout);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in old_main_layout.xmlout.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) getListAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.add:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1]);
                adapter.add(film);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    film = (Film) getListAdapter().getItem(0);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
            case R.id.moviesFragment:
                Intent myIntent = new Intent(OldMainActivity.this, TestingMoviesFragmentActivity.class);
                OldMainActivity.this.startActivity(myIntent);
                break;
            case R.id.AddActivity:
                Intent myIntent2 = new Intent(OldMainActivity.this, AddFilmActivity.class);
                OldMainActivity.this.startActivity(myIntent2);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }*/

}