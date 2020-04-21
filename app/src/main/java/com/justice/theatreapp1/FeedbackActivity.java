package com.justice.theatreapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;

public class FeedbackActivity extends AppCompatActivity {
    private RadioButton radioExcellent;
    private RadioButton radioGood;
    private RadioButton radioMedium;
    private RadioButton radioBad;
    private RadioButton radioUnacceptable;

    private RadioGroup radioGroup;
    private Button submitBtn;


    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private LinearLayout scrollView;

    private ShowData showData;
    private Feedback feedback;

    private DocumentSnapshot feedbackDocumentSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initWidgets();
        showData = ApplicationClass.documentSnapshot.toObject(ShowData.class);
        getFeedback();
        setListeners();
    }

    private void getFeedback() {
        showProgress(true);
        FirebaseFirestore.getInstance().collection("feedback").whereEqualTo("filmName", showData.getFilmName()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                showProgress(false);
                feedbackDocumentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                feedback = feedbackDocumentSnapshot.toObject(Feedback.class);

                setDefaultValues();
                Toast.makeText(FeedbackActivity.this, "Loaded Feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFeedBack();
            }
        });

    }

    private void updateFeedBack() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioExcellent:
                feedback.setFeedback(1);
                break;
            case R.id.radioGood:
                feedback.setFeedback(2);
                break;
            case R.id.radioMedium:
                feedback.setFeedback(3);
                break;
            case R.id.radioBad:
                feedback.setFeedback(4);
                break;
            case R.id.radioUnacceptable:
                feedback.setFeedback(5);
                break;

        }
        showProgress(true);
        feedbackDocumentSnapshot.getReference().set(feedback).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FeedbackActivity.this, "Feedback Updated", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(FeedbackActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
                showProgress(false);
            }
        });
    }

    private void setDefaultValues() {
        switch (feedback.getFeedback()) {
            case 1:
                radioExcellent.setChecked(true);
                break;
            case 2:
                radioGood.setChecked(true);
                break;
            case 3:
                radioMedium.setChecked(true);
                break;
            case 4:
                radioBad.setChecked(true);
                break;
            case 5:
                radioUnacceptable.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void initWidgets() {
        radioGroup = findViewById(R.id.radioGroup);
        submitBtn = findViewById(R.id.submitBtn);

        radioExcellent = findViewById(R.id.radioExcellent);
        radioGood = findViewById(R.id.radioGood);
        radioMedium = findViewById(R.id.radioMedium);
        radioBad = findViewById(R.id.radioBad);
        radioUnacceptable = findViewById(R.id.radioUnacceptable);

        ////////////////PROGRESS_BAR//////////////////////
        load = findViewById(R.id.loadingLinearLayout);
        loadTxtView = findViewById(R.id.loadTxtView);
        scrollView = findViewById(R.id.scrollView);


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

}
