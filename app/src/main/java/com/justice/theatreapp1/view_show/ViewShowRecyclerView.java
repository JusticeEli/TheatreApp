package com.justice.theatreapp1.view_show;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.AllData;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.user.update_show.UpdateShowActivity;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;

import java.util.List;

public class ViewShowRecyclerView extends FirestoreRecyclerAdapter<ShowData, ViewShowRecyclerView.ViewHolder> {
    private Context context;
    private ViewShowActivity viewShowActivity;

    public ViewShowRecyclerView(Context context, @NonNull FirestoreRecyclerOptions<ShowData> options) {
        super(options);
        this.context = context;
        this.viewShowActivity = (ViewShowActivity) context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull ShowData model) {
        holder.filmNameEdtTxt.setText(model.getFilmName());
        holder.languageEdtTxt.setText(model.getLanguage());
        holder.categoryEdtTxt.setText(model.getCategory());
        holder.summaryEdtTxt.setText(model.getSummary());
        holder.castEdtTxt.setText(model.getCast());
        holder.showEdtTxt.setText(model.getSummary());
        holder.seatEdtTxt.setText("" + model.getSeat());
        holder.cityEdtTxt.setText(model.getCityAndLocality());


        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.documentSnapshot = getSnapshots().getSnapshot(position);
                Intent intent = new Intent(context, UpdateShowActivity.class);
                context.startActivity(intent);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage("Are you sure you want to delete?").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewShowActivity.showProgress(true);

                        getSnapshots().getSnapshot(position).getReference().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "removed..", Toast.LENGTH_SHORT).show();
                                    removeFilmFromFeedbackAndBookDataList(position);

                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                                }
                                viewShowActivity.showProgress(false);
                            }
                        });
                        ////////////////////////////////////////


                    }
                });
                builder.create().show();
            }
        });
    }

    private void removeFilmFromFeedbackAndBookDataList(int position) {
        String filmName = getSnapshots().getSnapshot(position).getString("filmName");

        viewShowActivity.showProgress(true);
        FirebaseFirestore.getInstance().collection("feedback").whereEqualTo("filmName", filmName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    snapshot.getReference().delete();
                    Toast.makeText(context, "Removing Feedback: ", Toast.LENGTH_SHORT).show();

                }
                viewShowActivity.showProgress(false);
            }
        });

/////////////////////////////////////////////////
        viewShowActivity.showProgress(true);
        FirebaseFirestore.getInstance().collection("bookdata").whereEqualTo("filmName", filmName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    snapshot.getReference().delete();
                    Toast.makeText(context, "Removing Bookdata ", Toast.LENGTH_SHORT).show();

                }
                viewShowActivity.showProgress(false);
            }
        });

        ////////////////////////////////////////////////////////////

        for (final BookData bookData : AllData.bookDataList) {
            if (bookData.getFilmName().equals(filmName)) {
                viewShowActivity.showProgress(true);
                Backendless.Persistence.of(BookData.class).remove(bookData, new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        AllData.bookDataList.remove(bookData);
                        viewShowActivity.showProgress(false);


                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        viewShowActivity.showProgress(false);
                        Toast.makeText(context, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView filmNameEdtTxt, languageEdtTxt, categoryEdtTxt, summaryEdtTxt, castEdtTxt, showEdtTxt, seatEdtTxt, cityEdtTxt;

        private Button updateBtn, deleteBtn;


        public ViewHolder(@NonNull View v) {
            super(v);

            filmNameEdtTxt = v.findViewById(R.id.filmNameTxtView);
            languageEdtTxt = v.findViewById(R.id.languageTxtView);
            categoryEdtTxt = v.findViewById(R.id.categoryDescriptionTxtView);
            summaryEdtTxt = v.findViewById(R.id.summaryTxtView);
            castEdtTxt = v.findViewById(R.id.castTxtView);
            showEdtTxt = v.findViewById(R.id.showTxtView);
            seatEdtTxt = v.findViewById(R.id.seatTxtView);
            cityEdtTxt = v.findViewById(R.id.cityLocalityTxtView);
            updateBtn = v.findViewById(R.id.updateBtn);
            deleteBtn = v.findViewById(R.id.deleteBtn);
        }
    }
}
