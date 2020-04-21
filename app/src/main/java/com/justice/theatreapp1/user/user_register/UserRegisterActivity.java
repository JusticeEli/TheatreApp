package com.justice.theatreapp1.user.user_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.theatreapp1.R;

public class UserRegisterActivity extends AppCompatActivity {
    private EditText emailEdtTxt;
    private EditText passwordEdtTxt;
    private EditText confirmPasswordEdtTxt;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private Button registerBtn;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();

        setActionListeners();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }


        return true;
    }

    private void readDataFromEditTxt() {
        String email = emailEdtTxt.getText().toString().trim();
        String password = passwordEdtTxt.getText().toString().trim();
        String confirmPassword = confirmPasswordEdtTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password does not Match", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgress(true);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    displaySetupPage();
                    Toast.makeText(UserRegisterActivity.this, "User Register success", Toast.LENGTH_SHORT).show();
                    UserRegisterActivity.this.finish();


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(UserRegisterActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });


        ////////////////


        ///////////////////////////////////////////////
    }

    private void displaySetupPage() {
        startActivity(new Intent(this, UserSetupActivity.class));
    }



    private void setActionListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDataFromEditTxt();
            }
        });

    }

    ////////////////////PROGRESS_BAR////////////////////////////
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
        emailEdtTxt = findViewById(R.id.languageEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        confirmPasswordEdtTxt = findViewById(R.id.confirmPasswordEdtTxt);
        registerBtn = findViewById(R.id.registerBtn);

        //////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


    }
}
