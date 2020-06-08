package com.justice.theatreapp1.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.async.callback.AsyncCallback;

import com.backendless.persistence.DataQueryBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.theatre.theatre_login.TheatreLoginActivity;
import com.justice.theatreapp1.theatre.theatre_register.TheatreRegisterActivity;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;
import com.justice.theatreapp1.user.user_login.UserLoginActivity;
import com.justice.theatreapp1.user.user_register.UserRegisterActivity;

import java.util.List;

import static com.justice.theatreapp1.R.id.userLoginBtn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_WRITE_REQUEST_CODE = 1;
    private Button userLoginbtn, userRegisterBtn, theatreLoginBtn, theatreRegisterBtn;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnRequestPermissions();
        initWidgets();
        loadAllDataFromDatabase();
        onClickListeners();
    }

    private void setOnRequestPermissions() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))) {
            ActivityCompat.requestPermissions(this, permissions, READ_WRITE_REQUEST_CODE);
        }

    }


    private void onClickListeners() {
        userLoginbtn.setOnClickListener(this);
        userRegisterBtn.setOnClickListener(this);
        theatreLoginBtn.setOnClickListener(this);
        theatreRegisterBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case userLoginBtn:

                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.userRegistrationBtn:

                Intent intent2 = new Intent(this, UserRegisterActivity.class);
                startActivity(intent2);
                break;
            case R.id.theatreLoginBtn:

                Intent intent3 = new Intent(this, TheatreLoginActivity.class);
                startActivity(intent3);
                break;
            case R.id.theatreRegisterBtn:

                Intent intent4 = new Intent(this, TheatreRegisterActivity.class);
                startActivity(intent4);
                break;

        }
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
        userLoginbtn = findViewById(userLoginBtn);
        userRegisterBtn = findViewById(R.id.userRegistrationBtn);
        theatreLoginBtn = findViewById(R.id.theatreLoginBtn);
        theatreRegisterBtn = findViewById(R.id.theatreRegisterBtn);
        ////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);
    }

    ///////////////BACKENDLESS///////////////////////////////////
    private void loadAllDataFromDatabase() {

        FirebaseFirestore.getInstance().collection("showdata").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        AllData.showsDataList.add(documentSnapshot.toObject(ShowData.class));
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });




    }
}
