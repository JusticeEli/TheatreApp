package com.justice.theatreapp1.user.update_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.CreateShowActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_booking.ViewBookingActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.ViewFeedbackActivity;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;

import java.util.Arrays;

public class UpdateShowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText filmNameEdtTxt, summaryEdtTxt, castEdtTxt, dateEdtTxt, seatEdtTxt;
    private Button submitBtn;

    private Spinner languageSpinner, categorySpinner, citySpinner;

    private ShowData showData;
    private String filmName;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter1;
    ArrayAdapter<String> arrayAdapter2;

    //////////////////DRAWER LAYOUT////////////////////////

    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_show);


        initWidgets();
        initNavigationDrawer();

        setAdapters();

        showData = ApplicationClass.documentSnapshot.toObject(ShowData.class);
        filmName = showData.getFilmName();
        setDataToEditText();
        setOnClickListeners();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
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
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, ApplicationClass.languageAdapter);
        languageSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.item_spinner, ApplicationClass.categoryAdapter);
        categorySpinner.setAdapter(arrayAdapter1);
        arrayAdapter1.notifyDataSetChanged();

        arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.item_spinner, ApplicationClass.cityLocalityAdapter);
        citySpinner.setAdapter(arrayAdapter2);
        arrayAdapter2.notifyDataSetChanged();


    }


    private void setOnClickListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtnTapped();
            }
        });
    }


    private void setDataToEditText() {
        filmNameEdtTxt.setText(showData.getFilmName());
        setLanguageSpinner();
        setCategorySpinner();
        summaryEdtTxt.setText(showData.getSummary());
        castEdtTxt.setText(showData.getCast());
        dateEdtTxt.setText(showData.getShowData_time());
        seatEdtTxt.setText("" + showData.getSeat());
        setCitySpinner();
    }

    private void setLanguageSpinner() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                languageSpinner.setSelection(Arrays.asList(ApplicationClass.languageAdapter).indexOf(showData.getLanguage()));

            }
        }, 100);
    }

    private void setCategorySpinner() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                categorySpinner.setSelection(Arrays.asList(ApplicationClass.categoryAdapter).indexOf(showData.getCategory()), true);

            }
        }, 100);

    }

    private void setCitySpinner() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                citySpinner.setSelection(Arrays.asList(ApplicationClass.cityLocalityAdapter).indexOf(showData.getCityAndLocality()), true);

            }
        }, 100);


    }


    private void submitBtnTapped() {
        if (filmNameEdtTxt.getText().toString().isEmpty() || summaryEdtTxt.getText().toString().isEmpty() || castEdtTxt.getText().toString().isEmpty() || dateEdtTxt.getText().toString().isEmpty() || seatEdtTxt.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Fill All Fields!!", Toast.LENGTH_SHORT).show();
            return;
        }

        ///SETTING UPDATED VALUES TO THE SHOWDATA OBJECT///////////////
        showData.setFilmName(filmNameEdtTxt.getText().toString());
        showData.setLanguage(languageSpinner.getSelectedItem().toString());
        showData.setCategory(categorySpinner.getSelectedItem().toString());
        showData.setSummary(summaryEdtTxt.getText().toString());
        showData.setCast(castEdtTxt.getText().toString());
        showData.setShowData_time(dateEdtTxt.getText().toString());
        showData.setSeat(Integer.valueOf(seatEdtTxt.getText().toString()));
        showData.setCityAndLocality(citySpinner.getSelectedItem().toString());
        ///////BACKENDLESS////////////////////
        showProgress(true);

        ApplicationClass.documentSnapshot.getReference().set(showData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateFeedbackAndBookDataFilmNames();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UpdateShowActivity.this);

                    dialog.setMessage("Show Updated Successfully");
                    dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateShowActivity.this.onBackPressed();
                        }
                    });
                    dialog.create().show();


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(UpdateShowActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });
        ///////////////////

    }

    private void updateFeedbackAndBookDataFilmNames() {


        FirebaseFirestore.getInstance().collection("feedback").whereEqualTo("filmName", filmName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Feedback feedback = snapshot.toObject(Feedback.class);
                    feedback.setName(showData.getFilmName());

                    snapshot.getReference().set(feedback);
                }

            }
        });
        /////////////////////////////////////


        FirebaseFirestore.getInstance().collection("bookdata").whereEqualTo("filmName",filmName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                    BookData bookData=snapshot.toObject(BookData.class);
                    bookData.setFilmName(showData.getFilmName());
                    snapshot.getReference().set(bookData);

                }

            }
        });
     //////////////////////////////////////

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
        filmNameEdtTxt = findViewById(R.id.filmNameEdtTxt);
        languageSpinner = findViewById(R.id.languageSpinner);
        categorySpinner = findViewById(R.id.categoryDescriptionSpinner);
        summaryEdtTxt = findViewById(R.id.summaryEdtTxt);
        castEdtTxt = findViewById(R.id.castEdtTxt);
        dateEdtTxt = findViewById(R.id.showDateTimeEdtTxt);
        seatEdtTxt = findViewById(R.id.seatEdtTxt);
        citySpinner = findViewById(R.id.cityLocalitySpinner);

        ////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);

        submitBtn = findViewById(R.id.submitBtn);
    }
}
