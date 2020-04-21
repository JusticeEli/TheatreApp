package com.justice.theatreapp1.user.user_login;

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

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.user.user_first_page.UserFirstPageActivity;
import com.justice.theatreapp1.user.user_register.UserRegisterActivity;

public class UserLoginActivity extends AppCompatActivity {

    private EditText emailEdtTxt, passwordEdtTxt;
    private Button loginBtn;
    private TextView forgotPasswordTxtView,registerTxtView;

    ///////////PROGRESS lINEAR_LAYOUT/////////
    private LinearLayout load;
    private TextView loadTxtView;
    private ScrollView scrollView;

    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initWidgets();
        setListeners();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return true;
    }

    private void resetEditTxt() {

        emailEdtTxt.setText(null);
        passwordEdtTxt.setText(null);

    }

    private void setListeners() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmEmailAndPassword();

            }
        });

        forgotPasswordTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailEdtTxt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(UserLoginActivity.this, "Please Fill Email field!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    showProgress(true);

                    firebaseAuth.sendPasswordResetEmail(emailEdtTxt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserLoginActivity.this, "Reset Link send to Email Successfully!!", Toast.LENGTH_SHORT).show();

                            }else {
                                String error=task.getException().getMessage();
                                Toast.makeText(UserLoginActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                            }
                            showProgress(false);
                        }
                    });


                }

            }
        });

        registerTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
            }
        });


    }


    private void initWidgets() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEdtTxt = findViewById(R.id.languageEdtTxt);
        passwordEdtTxt = findViewById(R.id.passwordEdtTxt);
        loginBtn = findViewById(R.id.loginBtn);
        registerTxtView=findViewById(R.id.registerTxtView);
        forgotPasswordTxtView = findViewById(R.id.forgotPasswordTxtView);
        //////////////////PROGRESS_BAR//////////////////////
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


    private void confirmEmailAndPassword() {
        final String email = emailEdtTxt.getText().toString().trim();
        String password = passwordEdtTxt.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Fill All fields!!", Toast.LENGTH_SHORT).show();
            return;
        }
        /////////////BACKENDLESS////////////
        showProgress(true);

      firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  Toast.makeText(UserLoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(UserLoginActivity.this, UserFirstPageActivity.class);
                  resetEditTxt();
                  startActivity(intent);
                  finish();


              }else {
                  String error=task.getException().getMessage();
                  Toast.makeText(UserLoginActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
              }
              showProgress(false);
          }
      });

        ////////////////////////////////////////////////////
    }

    /**
     *   private void saveDataToFile() {
     *         String email = emailEdtTxt.getText().toString();
     *         String password = passwordEdtTxt.getText().toString();
     *
     *         UserLoginData data = new UserLoginData(email, password);
     *         try {
     *
     *             FileOutputStream fileOutputStream = openFileOutput("record.bin", MODE_PRIVATE);
     *             ObjectOutputStream objectOutputStream = new ObjectOutputStream((fileOutputStream));
     *             AllData.userLoginDataList.add(data);
     *             objectOutputStream.writeObject(AllData.userLoginDataList);
     *         } catch (Exception e) {
     *             e.printStackTrace();
     *         }
     *     }
     *
     *
     *        private void loadDataFromFile() {
     *         String data = null;
     *         try {
     *
     *             FileInputStream fileInputStream = openFileInput("record.bin");
     *             ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
     *             AllData.userLoginDataList = (List<UserLoginData>) objectInputStream.readObject();
     *             String names="";
     *             for(UserLoginData user:AllData.userLoginDataList){
     *                 names+=user.getEmail()+" "+user.getPassword()+" \n";
     *
     *             }
     *             Toast.makeText(this, names, Toast.LENGTH_LONG).show();
     *         } catch (Exception e) {
     *             e.printStackTrace();
     *         }
     */


}

