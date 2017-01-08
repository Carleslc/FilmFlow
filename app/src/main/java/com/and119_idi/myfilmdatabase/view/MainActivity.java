package com.and119_idi.myfilmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView, mFooterNavigationView;
    private Fragment currentFragment;
    private int currentItemId;
    private MenuItem mSearchMenuItem;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFooterNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer_footer);
        mFooterNavigationView.setNavigationItemSelectedListener(this);
        mFooterNavigationView.setElevation(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        MenuItem currentSelectedItem;
        if (savedInstanceState == null) {
            currentItemId = R.id.nav_movies_main;
            currentSelectedItem = mNavigationView.getMenu().findItem(currentItemId);
            setFragment(new MainFilmsFragment());
        }
        else {
            currentSelectedItem = mNavigationView.getMenu().findItem(
                    savedInstanceState.getInt(getString(R.string.bundle_current_item_id)));
            if (currentSelectedItem == null)
                currentSelectedItem = mFooterNavigationView.getMenu().findItem(
                        savedInstanceState.getInt(getString(R.string.bundle_current_item_id)));
            setFragment(getSupportFragmentManager().getFragment(savedInstanceState,
                    getString(R.string.bundle_current_fragment_id)));
        }

        drawerActions(currentSelectedItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search, menu);

        mSearchMenuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setActorFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) setActorFilter(null);
                return false;
            }
        });

        return true;
    }

    private void setActorFilter(@Nullable String actor) {
        MainFilmsFragment moviesFragment = (MainFilmsFragment) currentFragment;
        MainFilmsFragment.OnRefreshFilmsListener onRefreshListener = null;
        if (actor != null) {
            onRefreshListener = (filmsFound) -> {
                String filmsMessage;
                if (filmsFound == 0) {
                    filmsMessage = getString(R.string.no_film_found);
                } else {
                    filmsMessage = getString(R.string.found) + filmsFound + getString(R.string.films);
                }
                Toast.makeText(MainActivity.this,
                        filmsMessage + getString(R.string.with_actor) + actor.trim() + ".",
                        Toast.LENGTH_SHORT)
                        .show();
            };
        }
        moviesFragment.refreshFilmsWithActorFilter(actor, onRefreshListener);
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
        currentItemId = item.getItemId();

        if (currentItemId == R.id.nav_movies_main) {
            mSearchMenuItem.setVisible(true);
            mSearchMenuItem.collapseActionView();
            setFragment(new MainFilmsFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_movies_year) {
            mSearchMenuItem.setVisible(true);
            mSearchMenuItem.collapseActionView();
            setFragment(new DetailedFilmsFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_help) {
            mSearchMenuItem.setVisible(false);
            mSearchMenuItem.collapseActionView();
            setFragment(new HelpFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_about) {
            unCheckItems();
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        return true;
    }

    private void updateToolbar(MenuItem item) {
        currentItemId = item.getItemId();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle(item.getTitle());
    }

    private void drawerActions(MenuItem item) {
        updateToolbar(item);
        checkItem(item);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void unCheckItems() {
        mNavigationView.setCheckedItem(R.id.menu_none);
        mFooterNavigationView.setCheckedItem(R.id.menu_none_footer);
    }

    private void checkItem(MenuItem item) {
        int itemId = item.getItemId();
        unCheckItems();
        mNavigationView.setCheckedItem(itemId);
        mFooterNavigationView.setCheckedItem(itemId);
    }

    private void setFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_placeholder, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.bundle_current_item_id), currentItemId);
        getSupportFragmentManager().putFragment(
                outState, getString(R.string.bundle_current_fragment_id), currentFragment);
        super.onSaveInstanceState(outState);
    }

}
