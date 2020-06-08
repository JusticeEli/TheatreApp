package com.justice.theatreapp1.user.user_first_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.ShowProgress;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.user.user_register.UserRegisterData;
import com.justice.theatreapp1.user.user_register.UserSetupActivity;

public class MyProfileActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;

    private EditText mobile;
    private EditText address;

    private Button updateBtn;
    private UserRegisterData userRegisterData;

    private ProgressDialog progressDialog;
    public String originalName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initWidgets();

        setDefaultValues();

        setListeners();


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

    private void setDefaultValues() {
        FirebaseFirestore.getInstance().collection("user_register_data").document(FirebaseAuth.getInstance().getUid()).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(MyProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                userRegisterData = documentSnapshot.toObject(UserRegisterData.class);
                originalName = userRegisterData.getName();
                name.setText(userRegisterData.getName());
                email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                mobile.setText(userRegisterData.getMobile());
                address.setText(userRegisterData.getAddress());
            }
        });


    }


    private void setListeners() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserData();

            }
        });

    }

    private void updateUserData() {
        String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString().trim();

        String mobile1 = mobile.getText().toString().trim();
        String address1 = address.getText().toString().trim();
        if (name1.isEmpty()  || mobile1.isEmpty() || address1.isEmpty()) {
            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return;
        }


        userRegisterData.setName(name1);
        userRegisterData.setMobile(mobile1);
        userRegisterData.setAddress(address1);
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("user_register_data").document(FirebaseAuth.getInstance().getUid()).set(userRegisterData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MyProfileActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    updateFeedbacks();

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(MyProfileActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
////////////////////////////////////////////////////////////////

    }

    private void updateFeedbacks() {
        FirebaseFirestore.getInstance().collection("feedback").whereEqualTo("name", originalName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Feedback feedback = snapshot.toObject(Feedback.class);
                    feedback.setName(userRegisterData.getName());
                    snapshot.getReference().set(feedback);
                    Toast.makeText(MyProfileActivity.this, "Updated feedback", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        name = findViewById(R.id.nameEdtTxt);
        email = findViewById(R.id.emailEdtTxt);
        mobile = findViewById(R.id.mobileEdtTxt);
        address = findViewById(R.id.addressEdtTxt);
        updateBtn = findViewById(R.id.updateBtn);
        progressDialog = new ProgressDialog(this);


        email.setEnabled(false);
    }
}
