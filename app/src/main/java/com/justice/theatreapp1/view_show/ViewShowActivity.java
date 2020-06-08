package com.justice.theatreapp1.view_show;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.CreateShowActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_booking.ViewBookingActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.ViewFeedbackActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewShowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private EditText searchEdtTxt;


    private ViewShowRecyclerView viewShowRecyclerView;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_show);


        initWidget();
        initNavigationDrawer();

        AllData.readAllDataFromFiles();
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


    /**
     * @Override protected void onResume() {
     * super.onResume();
     * viewShowRecyclerView.setList(AllData.showsDataList);
     * }
     */
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

    private void setListeners() {


    }

    /**
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * getMenuInflater().inflate(R.menu.menu_update_show, menu);
     * return super.onCreateOptionsMenu(menu);
     * }
     * @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
     * switch (item.getItemId()) {
     * case R.id.menuUpdateShow:
     * sideBar.setVisibility(View.VISIBLE);
     * default:
     * break;
     * }
     * return true;
     * }
     */


    private void initWidget() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        ////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
    }

    /////////////////////PROGRESS_BAR////////////////////////////
    public void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


        }

    }


    public void setAdapter() {
        Query query = FirebaseFirestore.getInstance().collection("showdata");
        FirestoreRecyclerOptions<ShowData> recyclerOptions = new FirestoreRecyclerOptions.Builder<ShowData>().setLifecycleOwner(this).setQuery(query, ShowData.class).build();
        viewShowRecyclerView = new ViewShowRecyclerView(this, recyclerOptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewShowRecyclerView);
    }


}
