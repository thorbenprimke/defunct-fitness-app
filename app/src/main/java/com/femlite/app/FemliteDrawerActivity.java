package com.femlite.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.femlite.app.manager.UserManager;
import com.femlite.app.model.User;
import com.femlite.app.model.parse.ParseFemliteUser;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseSession;
import com.parse.ParseUser;

import javax.inject.Inject;

public abstract class FemliteDrawerActivity extends FemliteBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.femlite_drawer_activity);
        getComponent().inject(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View contentLayout = getLayoutInflater().inflate(
                getContentLayoutResId(),
                drawer,
                false);
        drawer.addView(contentLayout, 0);

        TextView headerName = (TextView) findViewById(R.id.header_name);
        TextView headerEmail = (TextView) findViewById(R.id.header_email);

        User user = userManager.getUser();
        headerName.setText(user.getName());
        headerEmail.setText(user.getEmail());
    }

    public abstract int getContentLayoutResId();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_workout && !(this instanceof WorkoutMainActivity)) {
            Intent intent =
                    new Intent(FemliteDrawerActivity.this, WorkoutMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_calorie_tracker
                && !(this instanceof FoodTrackerMainActivity)) {
            Intent intent =
                    new Intent(FemliteDrawerActivity.this, FoodTrackerMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            ParseUser.logOut();
            Intent intent = new Intent(this, FemliteDispatchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
