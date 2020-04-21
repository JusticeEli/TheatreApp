package com.justice.theatreapp1.user.user_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.user.user_first_page.UserFirstPageActivity;

public class UserSetupActivity extends AppCompatActivity {
    private EditText nameEdtTxt;
       private EditText mobileEdtTxt;
    private EditText addressEdtTxt;
    private EditText bestFriendEdtTxt;

    private Button submitBtn;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    private UserRegisterData userRegisterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);
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
        if (nameEdtTxt.getText().toString().isEmpty() || mobileEdtTxt.getText().toString().isEmpty() || addressEdtTxt.getText().toString().isEmpty() || bestFriendEdtTxt.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Fill All Fields !!", Toast.LENGTH_SHORT).show();
            return;
        }
        userRegisterData = new UserRegisterData(nameEdtTxt.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), mobileEdtTxt.getText().toString().trim(), addressEdtTxt.getText().toString().trim(), bestFriendEdtTxt.getText().toString().trim());

        showProgress(true);

        FirebaseFirestore.getInstance().collection("user_register_data").document(FirebaseAuth.getInstance().getUid()).set(userRegisterData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserSetupActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    createFeedBackForAllShows();

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(UserSetupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });


        ///////////////////BACKENDLESS//////////////////

        ///////////////////////////////////////////////
    }


    private void setActionListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
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

    private void createFeedBackForAllShows() {
        final Feedback feedback = new Feedback();

        FirebaseFirestore.getInstance().collection("showdata").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable final FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(UserSetupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        feedback.setEmail(firebaseAuth.getCurrentUser().getEmail());
                        feedback.setFilmName(doc.getDocument().getString("filmName"));
                        feedback.setName(userRegisterData.getName());

                        FirebaseFirestore.getInstance().collection("feedback").add(feedback).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserSetupActivity.this, "Feedback added", Toast.LENGTH_SHORT).show();

                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(UserSetupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
                Intent intent = new Intent(UserSetupActivity.this, UserFirstPageActivity.class);
                startActivity(intent);


            }
        });
    }

    private void initWidgets() {
        nameEdtTxt = findViewById(R.id.nameEdtTxt);
         mobileEdtTxt = findViewById(R.id.mobileEdtTxt);
        addressEdtTxt = findViewById(R.id.addressEdtTxt);
        bestFriendEdtTxt = findViewById(R.id.bestFriendEdtTxt);
        submitBtn = findViewById(R.id.submitBtn);

        //////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


    }
}
