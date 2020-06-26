package com.justice.theatreapp1.theatre.theatre_first_page.view_feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.CreateShowActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_booking.ViewBookingActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        initNavigationDrawer();


        setAdapter();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ////////////////////////NAVIGATION DRAWER/////////////////////////////////////////////
    private void initNavigationDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.createShowMenu:
                Intent intent = new Intent(this, CreateShowActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.updateShowMenu:
                Intent intent2 = new Intent(this, UpdateShowActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.viewBookingMenu:
                Intent intent3 = new Intent(this, ViewBookingActivity.class);
                startActivity(intent3);
                finish();

                break;
            case R.id.viewFeedbackMenu:
                Intent intent4 = new Intent(this, ViewFeedbackActivity.class);
                startActivity(intent4);
                finish();

                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    private void setAdapter() {
        Query query = FirebaseFirestore.getInstance().collection("feedback");
        FirestoreRecyclerOptions<Feedback> recyclerOptions = new FirestoreRecyclerOptions.Builder<Feedback>().setLifecycleOwner(this).setQuery(query, Feedback.class).build();

        ViewFeedbackRecyclerAdapter viewFeedbackRecyclerAdapter = new ViewFeedbackRecyclerAdapter(this, recyclerOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewFeedbackRecyclerAdapter);

    }

    private void setListeners() {


    }

    private void initWidgets() {

        recyclerView = findViewById(R.id.recyclerView);
    }
}
