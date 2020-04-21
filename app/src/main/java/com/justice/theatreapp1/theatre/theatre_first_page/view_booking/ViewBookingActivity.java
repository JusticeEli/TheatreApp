package com.justice.theatreapp1.theatre.theatre_first_page.view_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.CreateShowActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.ViewFeedbackActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;

import java.util.ArrayList;

public class ViewBookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText searchEdtTxt;
    private Button exportBtn;
    private RecyclerView recyclerView;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidget();

        initNavigationDrawer();


        setAdapter();
        setListeners();
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
        Query query = FirebaseFirestore.getInstance().collection("bookdata");
        FirestoreRecyclerOptions<BookData> recyclerOptions = new FirestoreRecyclerOptions.Builder<BookData>().setQuery(query, BookData.class).setLifecycleOwner(this).build();
        ViewBookingRecyclerAdaper viewBookingRecyclerAdaper = new ViewBookingRecyclerAdaper(this, recyclerOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewBookingRecyclerAdaper);

    }

    private void setListeners() {


    }

    private void initWidget() {

        searchEdtTxt = findViewById(R.id.searchEdtTxt);
        exportBtn = findViewById(R.id.exportBtn);
        recyclerView = findViewById(R.id.recyclerView);

    }
}
