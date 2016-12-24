package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;
import com.and119_idi.myfilmdatabase.model.FilmData;

/**
 * Created by Carlos Lázaro Costa on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private NavigationView mNavigationView, mFooterNavigationView;

    private MenuItem mCurrentSelectedItem;
    private Fragment currentFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_films_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddFilmActivity.class));
            }
        });

        new InsertFilmsTask().execute();

        mNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFooterNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer_footer);
        mFooterNavigationView.setNavigationItemSelectedListener(this);

        int navMoviesMainId = R.id.nav_movies_main;
        mCurrentSelectedItem = mNavigationView.getMenu().findItem(navMoviesMainId);
        mNavigationView.setCheckedItem(navMoviesMainId);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mCurrentSelectedItem.getTitle());
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Don't really know if a fragment was being set when we had <fragment> instead of <FrameLayout>
        //When orientation changes, activity is destroyed and recreated. But fragments no, or yes, idk.
        if (savedInstanceState == null) {
            setFragment(new MainMoviesFragment());
        }
        else setFragment(getSupportFragmentManager().getFragment(savedInstanceState,"currentFragment"));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movies_main) {
            setFragment(new MainMoviesFragment());
            drawerActions(item);
        } else if (id == R.id.nav_movies_year) {
            setFragment(new MoviesFragment());
            drawerActions(item);
        } else if (id == R.id.nav_help) {
            setFragment(new HelpFragment());
            drawerActions(item);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
        
        return true;
    }
    
    private void drawerActions(MenuItem item) {
        mToolbar.setTitle(item.getTitle());
        checkItem(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void checkItem(MenuItem item) {
        if (!mCurrentSelectedItem.equals(item)) {
            mCurrentSelectedItem.setChecked(false);
            int itemId = item.getItemId();
            mNavigationView.setCheckedItem(itemId);
            mFooterNavigationView.setCheckedItem(itemId);
            mCurrentSelectedItem = item;
        }
    }

    private void setFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_placeholder, fragment)
                .commit();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "currentFragment", currentFragment);
        super.onSaveInstanceState(outState);
    }

    // TODO: Remove this listener and test buttons
    public void onDeleteButtonClick(View view) {
        warnNotImplementedYet();
    }

    // TODO: Remove this when whole class is already implemented
    private void warnNotImplementedYet() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    // TODO: Remove this when AddFilmActivity is already completed
    public class InsertFilmsTask extends AsyncTask<Void, Void, Boolean> {

        private FilmData filmData;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                filmData = new FilmData(MainActivity.this);
                filmData.open();
                if (filmData.getAllFilms().isEmpty()) {
                    for (int i = 0; i < 10; ++i)
                        filmData.createFilm("Titulo " + i, "Director " + i);
                }
                return true;
            } catch (Exception e) {
                Log.e(MainActivity.class.getSimpleName(), "Error inserting test films", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            filmData.close();
            super.onPostExecute(aBoolean);
        }
    }
}
