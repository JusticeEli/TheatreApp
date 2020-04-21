package com.justice.theatreapp1.theatre.theatre_first_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.main.MainActivity;
import com.justice.theatreapp1.theatre.TheatreSetupActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_booking.ViewBookingActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.ViewFeedbackActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;
import com.justice.theatreapp1.user.user_first_page.UserFirstPageActivity;
import com.justice.theatreapp1.user.user_register.UserSetupActivity;
import com.justice.theatreapp1.view_show.ViewShowActivity;

public class TheatreFirstPage extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    private RelativeLayout mainRelLayout;
    private LinearLayout createShow;
    private LinearLayout updateShow;
    private LinearLayout viewBooking;
    private LinearLayout viewFeedback;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_first_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();
        initNavigationDrawer();
        setOnClickListeners();
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

    @Override
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

    private void initWidgets() {

        mainRelLayout = findViewById(R.id.mainRelLayout);
        createShow = findViewById(R.id.createShow);
        updateShow = findViewById(R.id.updateShow);
        viewBooking = findViewById(R.id.viewBooking);
        viewFeedback = findViewById(R.id.viewFeedback);


    }

    private void setOnClickListeners() {

        createShow.setOnClickListener(this);
        updateShow.setOnClickListener(this);
        viewBooking.setOnClickListener(this);
        viewFeedback.setOnClickListener(this);

///// Menu Listeners///////////


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.createShow:
                Intent intent = new Intent(this, CreateShowActivity.class);
                startActivity(intent);

                break;
            case R.id.updateShow:
                Intent intent1 = new Intent(this, ViewShowActivity.class);
                startActivity(intent1);


                break;
            case R.id.viewBooking:
                Intent intent2 = new Intent(this, ViewBookingActivity.class);
                startActivity(intent2);

                break;
            case R.id.viewFeedback:
                Intent intent3 = new Intent(this, ViewFeedbackActivity.class);
                startActivity(intent3);

                break;
            default:

                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore.getInstance().collection("theatre_register_data").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    if (!task.getResult().exists()) {
                        startActivity(new Intent(TheatreFirstPage.this, TheatreSetupActivity.class));

                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(TheatreFirstPage.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
