package com.justice.theatreapp1.theatre.theatre_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.theatreapp1.R;

import com.justice.theatreapp1.data.*;
import com.justice.theatreapp1.theatre.TheatreSetupActivity;
import com.justice.theatreapp1.theatre.theatre_first_page.TheatreFirstPage;


public class TheatreRegisterActivity extends AppCompatActivity {
    private EditText emailEdtTxt, passwordEdtTxt, confirmPasswordEdtTxt;
    private Button registerBtn;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre_register);
        setTitle("Theatre Register");
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
        String email = emailEdtTxt.getText().toString().trim();
        String password = passwordEdtTxt.getText().toString().trim();
        String confirmPassword = confirmPasswordEdtTxt.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TheatreRegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TheatreRegisterActivity.this, TheatreSetupActivity.class));

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(TheatreRegisterActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });

        resetEditText();
    }

    private void resetEditText() {
        emailEdtTxt.setText(null);
        passwordEdtTxt.setText(null);
        confirmPasswordEdtTxt.setText(null);

    }


    private void initWidgets() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailEdtTxt = findViewById(R.id.emailEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        confirmPasswordEdtTxt = findViewById(R.id.confirmPasswordEdtTxt);
        registerBtn = findViewById(R.id.registerBtn);
        progressDialog = new ProgressDialog(this);


    }
}
