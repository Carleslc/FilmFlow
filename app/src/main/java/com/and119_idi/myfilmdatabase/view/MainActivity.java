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

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mToolbar;
    private NavigationView mNavigationView, mFooterNavigationView;

    private MenuItem mCurrentSelectedItem;
    private Fragment currentFragment;
    private int currentItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFooterNavigationView = (NavigationView)
                findViewById(R.id.navigation_drawer_footer);
        mFooterNavigationView.setNavigationItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            currentItemId = R.id.nav_movies_main;
            mCurrentSelectedItem = mNavigationView.getMenu().findItem(currentItemId);
            setFragment(new MainMoviesFragment());
        }
        else {//// FIXME: 26/12/16
            mCurrentSelectedItem = mNavigationView.getMenu().findItem(savedInstanceState.getInt("currentItemId"));
            if (mCurrentSelectedItem == null) mCurrentSelectedItem = mFooterNavigationView.getMenu().findItem(savedInstanceState.getInt("currentItemId"));
            setFragment(getSupportFragmentManager().getFragment(savedInstanceState,"currentFragment"));
        }

        drawerActions(mCurrentSelectedItem);
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
            setFragment(new MainMoviesFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_movies_year) {
            setFragment(new DetailedMoviesFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_help) {
            setFragment(new HelpFragment());
            drawerActions(item);
        } else if (currentItemId == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            updateToolbar(item);
            // FIXME: 26/12/16 Esto esta cutre xD (Aqui queremos que se guarde el titulo pero no que se cierre el drawer)
        }
        
        return true;
    }
    
    private void updateToolbar(MenuItem item) {
        currentItemId = item.getItemId();
        getSupportActionBar().setTitle(item.getTitle());
    }
    private void drawerActions(MenuItem item) {
        updateToolbar(item);
        checkItem(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void checkItem(MenuItem item) {

        int itemId = item.getItemId();
        mNavigationView.setCheckedItem(itemId);
        mFooterNavigationView.setCheckedItem(itemId);

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
        outState.putInt("currentItemId",currentItemId);
        getSupportFragmentManager().putFragment(outState, "currentFragment", currentFragment);
        super.onSaveInstanceState(outState);
    }

}
