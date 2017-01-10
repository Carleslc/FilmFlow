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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String searchOption;
    private NavigationView mNavigationView, mFooterNavigationView;
    private Fragment mCurrentFragment;
    private int mCurrentItemId;
    private MenuItem mSearchMenuItem;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFooterNavigationView = (NavigationView) findViewById(R.id.navigation_drawer_footer);
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
            mCurrentItemId = R.id.nav_movies_main;
            currentSelectedItem = mNavigationView.getMenu().findItem(mCurrentItemId);
            setFragment(new MainFilmsFragment());
            searchOption = "actor";
        }
        else {
            mCurrentItemId = savedInstanceState.getInt(getString(R.string.bundle_current_item_id));
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                    getString(R.string.bundle_current_fragment_id));

            currentSelectedItem = mNavigationView.getMenu().findItem(savedInstanceState.getInt(getString(R.string.bundle_current_item_id)));
            if (currentSelectedItem == null)
                currentSelectedItem = mFooterNavigationView.getMenu().findItem(mCurrentItemId);

            setFragment(mCurrentFragment);

            searchOption = savedInstanceState.getString("currentSearchOption");

        }

        if (currentSelectedItem.getItemId() != R.id.nav_about)drawerActions(currentSelectedItem);
        else updateToolbar(currentSelectedItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search, menu);
        getMenuInflater().inflate(R.menu.menu_search_option, menu);


        mSearchMenuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) setFilter(null);
                return false;
            }
        });

        return true;
    }

    private void setFilter(@Nullable String filter) {
        MainFilmsFragment moviesFragment = (MainFilmsFragment) mCurrentFragment;
        MainFilmsFragment.OnRefreshFilmsListener onRefreshListener = null;
        if (filter != null) {
            onRefreshListener = (filmsFound) -> {
                String filmsMessage;
                if (filmsFound == 0) {
                    filmsMessage = getString(R.string.no_film_found);
                } else {
                    filmsMessage = getString(R.string.found) + filmsFound + getString(R.string.films);
                }
                Toast.makeText(MainActivity.this,
                        filmsMessage + getString((searchOption.equals("actor"))?(R.string.with_actor):(R.string.with_title)) + filter.trim() + ".",
                        Toast.LENGTH_SHORT)
                        .show();
            };
        }
        moviesFragment.refreshFilmsWithActorFilter(filter, searchOption, onRefreshListener);
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        mSearchMenuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        menu.findItem(R.id.by_actor).setChecked(searchOption.equals("actor"));
        menu.findItem(R.id.by_title).setChecked(searchOption.equals("title"));

        if (searchOption.equals("actor"))
            searchView.setQueryHint(getString(R.string.search_by_actor_hint));
        else
            searchView.setQueryHint(getString(R.string.search_by_title_hint));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        switch (item.getItemId()) {
            case R.id.by_actor:
                searchOption = "actor";
                item.setChecked(true);
                searchView.setQueryHint(getString(R.string.search_by_actor_hint));
                return true;
            case R.id.by_title:
                item.setChecked(true);
                searchView.setQueryHint(getString(R.string.search_by_title_hint));
                searchOption = "title";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        if (item.getItemId() == R.id.nav_movies_main) {
            mSearchMenuItem.setVisible(true);
            mSearchMenuItem.collapseActionView();
            setFragment(new MainFilmsFragment());
            drawerActions(item);
        } else if (item.getItemId() == R.id.nav_movies_year) {
            mSearchMenuItem.setVisible(true);
            mSearchMenuItem.collapseActionView();
            setFragment(new DetailedFilmsFragment());
            drawerActions(item);
        } else if (item.getItemId() == R.id.nav_help) {
            mSearchMenuItem.setVisible(false);
            mSearchMenuItem.collapseActionView();
            setFragment(new HelpFragment());
            drawerActions(item);
        } else if (item.getItemId() == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        if (item.getItemId() != R.id.nav_about) mCurrentItemId = item.getItemId();

        return true;
    }

    private void updateToolbar(MenuItem item) {
        mCurrentItemId = item.getItemId();

        Log.d(TAG,item.getTitle().toString());
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
        mCurrentFragment = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_placeholder, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.bundle_current_item_id), mCurrentItemId);
        outState.putString("currentSearchOption", searchOption);
        getSupportFragmentManager().putFragment(
                outState, getString(R.string.bundle_current_fragment_id), mCurrentFragment);
        super.onSaveInstanceState(outState);
    }

}
