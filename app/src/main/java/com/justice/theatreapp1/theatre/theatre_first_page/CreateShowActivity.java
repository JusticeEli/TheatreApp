package com.justice.theatreapp1.theatre.theatre_first_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.view_booking.ViewBookingActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.ViewFeedbackActivity;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;

public class CreateShowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText filmNameEdtTxt, summaryEdtTxt, castEdtTxt, dateEdtTxt, seatEdtTxt;
    private Spinner languageSpinner, categorySpinner, citySpinner;
    private Button submitBtn;
    private ShowData showData;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_show);
setTitle("Create Show");

        initWidgets();
        initNavigationDrawer();

        setAdapters();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtnTapped();
            }
        });
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

    private void setAdapters() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ApplicationClass.languageAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        languageSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ApplicationClass.categoryAdapter);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        categorySpinner.setAdapter(arrayAdapter2);
        arrayAdapter2.notifyDataSetChanged();

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,android. R.layout.simple_spinner_dropdown_item, ApplicationClass.cityLocalityAdapter);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter3);
        arrayAdapter3.notifyDataSetChanged();


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

    private void submitBtnTapped() {
        if (filmNameEdtTxt.getText().toString().isEmpty() || summaryEdtTxt.getText().toString().isEmpty() || castEdtTxt.getText().toString().isEmpty() || dateEdtTxt.getText().toString().isEmpty() || seatEdtTxt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields!!", Toast.LENGTH_SHORT).show();
            return;
        }

        showData = new ShowData(filmNameEdtTxt.getText().toString(), languageSpinner.getSelectedItem().toString(), categorySpinner.getSelectedItem().toString(), summaryEdtTxt.getText().toString(), castEdtTxt.getText().toString(), dateEdtTxt.getText().toString(), Integer.valueOf(seatEdtTxt.getText().toString()), citySpinner.getSelectedItem().toString());
        /////////////////BACKENDLESS/////////////////
        showProgress(true);
        FirebaseFirestore.getInstance().collection("showdata").add(showData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    addFeedBackForAllUsers();
                    Toast.makeText(CreateShowActivity.this, "success", Toast.LENGTH_SHORT).show();


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(CreateShowActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);

            }
        });
        resetEditTexts();

    }

    private void addFeedBackForAllUsers() {
        final Feedback feedback = new Feedback();

        FirebaseFirestore.getInstance().collection("user_register_data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        feedback.setEmail(snapshot.getString("email"));
                        feedback.setFilmName(showData.getFilmName());
                        feedback.setName(snapshot.getString("name"));

                        FirebaseFirestore.getInstance().collection("feedback").add(feedback).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateShowActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(CreateShowActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        ///////////////////

    }


    private void resetEditTexts() {
        filmNameEdtTxt.setText("");
        summaryEdtTxt.setText("");
        castEdtTxt.setText("");
        dateEdtTxt.setText("");
        seatEdtTxt.setText("");

    }

    /////////////////////PROGRESS_BAR////////////////////////////
    private void showProgress(boolean show) {
        if (show) {
            load.setVisibility(View.VISIBLE);
            loadTxtView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        } else {
            load.setVisibility(View.GONE);
            loadTxtView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);


        }

    }


    private void initWidgets() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filmNameEdtTxt = findViewById(R.id.filmNameEdtTxt);
        languageSpinner = findViewById(R.id.languageSpinner);
        categorySpinner = findViewById(R.id.categoryDescriptionSpinner);
        summaryEdtTxt = findViewById(R.id.summaryEdtTxt);
        castEdtTxt = findViewById(R.id.castEdtTxt);
        dateEdtTxt = findViewById(R.id.showDateTimeEdtTxt);
        seatEdtTxt = findViewById(R.id.seatEdtTxt);
        citySpinner = findViewById(R.id.cityLocalitySpinner);

        submitBtn = findViewById(R.id.submitBtn);

        //////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


    }
}
