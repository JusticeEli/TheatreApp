package com.justice.theatreapp1.theatre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.TheatreFirstPage;
import com.justice.theatreapp1.theatre.theatre_register.TheatreRegisterActivity;
import com.justice.theatreapp1.theatre.theatre_register.TheatreRegisterData;

public class TheatreSetupActivity extends AppCompatActivity {

    private EditText nameEdtTxt, mobileEdtTxt, cityEdtTxt, addressEdtTxt, aadharEdtTxt;
    private Button registerBtn;
    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    private TheatreRegisterData theatreRegisterData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_setup);

        initWidgets();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtnTapped();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

    private void registerBtnTapped() {
        String name = nameEdtTxt.getText().toString().trim();
        String mobile = nameEdtTxt.getText().toString().trim();
        String city = nameEdtTxt.getText().toString().trim();
        String address = nameEdtTxt.getText().toString().trim();
        String aadhar = nameEdtTxt.getText().toString().trim();

        if (name.isEmpty() || mobile.isEmpty() || city.isEmpty() || address.isEmpty() || aadhar.isEmpty()) {
            Toast.makeText(this, "Please Fill All Filleds!!", Toast.LENGTH_SHORT).show();
            return;
        }


        theatreRegisterData = new TheatreRegisterData(name, mobile, city, address, aadhar);
        showProgress(true);
        FirebaseFirestore.getInstance().collection("theatre_register_data").document(FirebaseAuth.getInstance().getUid()).set(theatreRegisterData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TheatreSetupActivity.this, "Setup Completed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TheatreSetupActivity.this, TheatreFirstPage.class));

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(TheatreSetupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);

            }
        });


        resetEditText();


    }

    private void resetEditText() {
        nameEdtTxt.setText("");
        mobileEdtTxt.setText("");
        cityEdtTxt.setText("");
        addressEdtTxt.setText("");
        aadharEdtTxt.setText("");
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
        nameEdtTxt = findViewById(R.id.filmNameEdtTxt);
        mobileEdtTxt = findViewById(R.id.summaryEdtTxt);
        cityEdtTxt = findViewById(R.id.city);
        addressEdtTxt = findViewById(R.id.castEdtTxt);
        aadharEdtTxt = findViewById(R.id.aadharEdtTxt);

        registerBtn = findViewById(R.id.registerBtn);

        ////////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);
    }


}
