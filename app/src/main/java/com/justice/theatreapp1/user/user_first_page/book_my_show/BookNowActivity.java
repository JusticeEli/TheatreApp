package com.justice.theatreapp1.user.user_first_page.book_my_show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;

public class BookNowActivity extends AppCompatActivity {

    private EditText showId;
    private EditText filmNameEdtTxt;
    private EditText availableSeats;
    private EditText noOfSeats;
    private Button submitBtn;
    private ShowData showData;
    private BookData bookData;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        setTitle("Book Now");


        showData = ApplicationClass.documentSnapshot.toObject(ShowData.class);

        initWidgets();
        setDataInFields();
        setListeners();
    }


    private void setListeners() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDataIntoDatabase();


            }
        });


    }

    private void putDataIntoDatabase() {
        if (noOfSeats.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Fill the Number of Seats", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(noOfSeats.getText().toString().trim()) > showData.getSeat()) {
            Toast.makeText(this, "No of Seats Remaining are Less", Toast.LENGTH_SHORT).show();
            return;

        }

        bookData = new BookData(showId.getText().toString(), filmNameEdtTxt.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), Integer.valueOf(noOfSeats.getText().toString()));

        ///////BACKENDLESS//////////////////////
        showProgress(true);

        FirebaseFirestore.getInstance().collection("bookdata").add(bookData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookNowActivity.this).setMessage("Booked successfully").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }
                    });
                    builder.create().show();
                    updateNumberOfSeatsRemaining();


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(BookNowActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });


    }

    private void updateNumberOfSeatsRemaining() {

        showData.setSeat(showData.getSeat() - bookData.getNoOfSeats());
        showProgress(true);

        ApplicationClass.documentSnapshot.getReference().set(showData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BookNowActivity.this, "Update remaining number of seats: ", Toast.LENGTH_SHORT).show();

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(BookNowActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                }
                showProgress(false);

            }
        });


    }

    private void setDataInFields() {
        showId.setText(ApplicationClass.documentSnapshot.getId());

        // TODO: 28-Jan-20 create field for show ids in the ShowData class
        filmNameEdtTxt.setText(showData.getFilmName());
        availableSeats.setText("" + showData.getSeat());
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
        showId = findViewById(R.id.showIdEdtTxt);
        filmNameEdtTxt = findViewById(R.id.filmNameEdtTxt);
        availableSeats = findViewById(R.id.availableSeatEditTxt);
        noOfSeats = findViewById(R.id.noOfSeatsEdtTxt);
        submitBtn = findViewById(R.id.submitBtn);


////////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


        showId.setEnabled(false);
        filmNameEdtTxt.setEnabled(false);
        availableSeats.setEnabled(false);

    }
}
