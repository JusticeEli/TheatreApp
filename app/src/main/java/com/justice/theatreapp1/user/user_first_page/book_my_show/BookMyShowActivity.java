package com.justice.theatreapp1.user.user_first_page.book_my_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.user.user_first_page.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class BookMyShowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_my_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidget();
        initNavigationDrawer();

        setAdapter();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);

         return super.onCreateOptionsMenu(menu);
    }

    ///////////////////////NAVIGATION DRAWER/////////////////////////////////////////////
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

            case R.id.bookMyShowMenu:
                Intent intent = new Intent(this, BookMyShowActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.myProfileMenu:
                Intent intent2 = new Intent(this, MyProfileActivity.class);
                startActivity(intent2);
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
        }
        return true;
    }


    private void setAdapter() {
        Query query = FirebaseFirestore.getInstance().collection("showdata");
        FirestoreRecyclerOptions<ShowData> recyclerOptions = new FirestoreRecyclerOptions.Builder<ShowData>().setLifecycleOwner(this).setQuery(query, new SnapshotParser<ShowData>() {
            @NonNull
            @Override
            public ShowData parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                ShowData showData = snapshot.toObject(ShowData.class);
                showData.setShowId(snapshot.getId());
                return showData;
            }
        }).build();

        BookMyShowRecyclerAdapter adapter = new BookMyShowRecyclerAdapter(this, recyclerOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void initWidget() {
        recyclerView = findViewById(R.id.recyclerView);

    }

    private void setListeners() {

    }


}
