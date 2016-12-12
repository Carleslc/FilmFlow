package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private NavigationView mNavigationView, mFooterNavigationView;

    private MenuItem mCurrentSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_films_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddFilmActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFooterNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer_footer);
        mFooterNavigationView.setNavigationItemSelectedListener(this);

        mCurrentSelectedItem = mNavigationView.getMenu().findItem(R.id.nav_movies_main);
        mCurrentSelectedItem.setChecked(true);
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
        } else if (id == R.id.nav_movies_year) {
            setFragment(new MoviesFragment());
        } else if (id == R.id.nav_help) {
            warnNotImplementedYet();
        } else if (id == R.id.nav_about) {
            warnNotImplementedYet();
        }
        mToolbar.setTitle(item.getTitle());
        checkItem(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkItem(MenuItem item) {
        mCurrentSelectedItem.setChecked(false);
        int itemId = item.getItemId();
        mNavigationView.setCheckedItem(itemId);
        mFooterNavigationView.setCheckedItem(itemId);
        mCurrentSelectedItem = item;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_placeholder, fragment)
                .commit();
    }

    // TODO: Remove this listener and test buttons
    public void onDeleteButtonClick(View view) {
        warnNotImplementedYet();
    }

    // TODO: Remove this when whole class is already implemented
    private void warnNotImplementedYet() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }
}
