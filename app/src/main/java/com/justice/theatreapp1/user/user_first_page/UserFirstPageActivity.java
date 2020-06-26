package com.justice.theatreapp1.user.user_first_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.CreateShowActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookMyShowActivity;
import com.justice.theatreapp1.user.user_register.UserSetupActivity;

public class UserFirstPageActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout first;
    private LinearLayout second;
    private Button bookMyShowBtn;
    private Button myProfileBtn;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initWidgets();
        initNavigationDrawer();


        setListeners();
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


    private void initWidgets() {
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        bookMyShowBtn = findViewById(R.id.bookMyShowBtn);
        myProfileBtn = findViewById(R.id.myProfileBtn);
    }

    private void setListeners() {
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        bookMyShowBtn.setOnClickListener(this);
        myProfileBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:

            case R.id.bookMyShowBtn:
                Intent intent2 = new Intent(this, BookMyShowActivity.class);
                startActivity(intent2);
                break;
            case R.id.second:

            case R.id.myProfileBtn:

                Intent intent1 = new Intent(this, MyProfileActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore.getInstance().collection("user_register_data").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    if (!task.getResult().exists()) {
                        startActivity(new Intent(UserFirstPageActivity.this, UserSetupActivity.class));

                    }


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(UserFirstPageActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
