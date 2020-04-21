package com.justice.theatreapp1.theatre.theatre_first_page.view_feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.justice.theatreapp1.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackRecyclerAdapter extends FirestoreRecyclerAdapter<Feedback, ViewFeedbackRecyclerAdapter.ViewHolder> {

    private Context context;


    public ViewFeedbackRecyclerAdapter(Context context, @NonNull FirestoreRecyclerOptions<Feedback> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Feedback model) {
        holder.filmNameTxtView.setText(model.getFilmName());
        holder.nameTxtView.setText(model.getName());
        holder.emailTxtView.setText(model.getEmail());
        switch (model.getFeedback()) {
            case 1:
                holder.feedbackTxtView.setText("Excellent");

                break;
            case 2:
                holder.feedbackTxtView.setText("Good");

                break;
            case 3:
                holder.feedbackTxtView.setText("Medium");

                break;

            case 4:
                holder.feedbackTxtView.setText("Bad");

                break;

            case 5:
                holder.feedbackTxtView.setText("Unacceptable");

                break;
            default:
                break;


        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        ViewFeedbackRecyclerAdapter.ViewHolder holder = new ViewFeedbackRecyclerAdapter.ViewHolder(view);
        return holder;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView filmNameTxtView;
        private TextView nameTxtView;
        private TextView feedbackTxtView;
        private TextView emailTxtView;


        public ViewHolder(@NonNull View v) {
            super(v);
            filmNameTxtView = v.findViewById(R.id.filmNameTxtView);
            nameTxtView = v.findViewById(R.id.nameTxtView);
            feedbackTxtView = v.findViewById(R.id.feedbackTxtView);
            emailTxtView = v.findViewById(R.id.emailTxtView);
        }
    }

}
