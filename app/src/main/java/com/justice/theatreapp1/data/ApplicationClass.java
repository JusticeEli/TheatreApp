package com.justice.theatreapp1.data;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;

import java.util.List;

public class ApplicationClass extends Application {

    public static DocumentSnapshot documentSnapshot;

    ////////////////ADAPTER LISTS////////////
    public static String[] languageAdapter;
    public static String[] categoryAdapter;
    public static String[] cityLocalityAdapter;

    /////////////////////////////////////////




    ///////////BACKENDLESS////////////////////

    @Override
    public void onCreate() {
        super.onCreate();
        languageAdapter=new String[]{"English","Spanish","Kiswahili","French"};
        categoryAdapter=new String[]{"Action","Horror","Fantacy","Fiction"};
        cityLocalityAdapter=new String[]{"Nairobi","Mombase","Nakuru","Eldoret"};


        /////BACKENDLESS//////////////////

     
       // loadAllDataFromDatabase();
        ////////////////////////////////
/**
 *    AllData.initializeDirectoryAndFile();
 *         AllData.readAllDataFromFiles();
 *         if(AllData.theatreLoginDataList.isEmpty()){
 *             AllData.putDummyValues();
 *         }
 */

    }

    ///////////////BACKENDLESS///////////////////////////////////
    private void loadAllDataFromDatabase() {
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();

        Backendless.Persistence.of(ShowData.class).find(dataQueryBuilder, new AsyncCallback<List<ShowData>>() {
            @Override
            public void handleResponse(List<ShowData> response) {
                AllData.showsDataList = response;
                Toast.makeText(ApplicationClass.this, "Show data list Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Backendless.Persistence.of(BookData.class).find(dataQueryBuilder, new AsyncCallback<List<BookData>>() {
            @Override
            public void handleResponse(List<BookData> response) {
                AllData.bookDataList = response;
                Toast.makeText(ApplicationClass.this, "Book data listSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Backendless.Persistence.of(Feedback.class).find(dataQueryBuilder, new AsyncCallback<List<Feedback>>() {
            @Override
            public void handleResponse(List<Feedback> response) {
                AllData.feedbackList = response;
                Toast.makeText(ApplicationClass.this, "Feed back listSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(ApplicationClass.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AllData.writeAllDataToFiles();
    }
}
