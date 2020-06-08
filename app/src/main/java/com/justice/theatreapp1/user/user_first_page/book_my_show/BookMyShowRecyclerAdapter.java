package com.justice.theatreapp1.user.user_first_page.book_my_show;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.justice.theatreapp1.FeedbackActivity;
import com.justice.theatreapp1.R;
import com.justice.theatreapp1.data.ApplicationClass;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;

import java.util.List;

public class BookMyShowRecyclerAdapter extends FirestoreRecyclerAdapter<ShowData, BookMyShowRecyclerAdapter.ViewHolder> implements Filterable {
    private Context context;


    public BookMyShowRecyclerAdapter(Context context, @NonNull FirestoreRecyclerOptions<ShowData> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull final ShowData model) {
        holder.filmNameTxtView.setText(model.getFilmName());
        holder.showTimeTxtView.setText(model.getShowData_time());
        holder.languageTxtView.setText(model.getLanguage());
        holder.categoryTxtView.setText(model.getCategory());
        holder.castTxtView.setText(model.getCast());
        holder.seatTxtView.setText("" + model.getSeat());
        holder.cityTxtView.setText(model.getCityAndLocality());


        ////////LISTENERS////////////////////////////
        holder.bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.documentSnapshot = getSnapshots().getSnapshot(position);
                Intent intent = new Intent(context, BookNowActivity.class);
                context.startActivity(intent);
            }
        });
        holder.feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.documentSnapshot = getSnapshots().getSnapshot(position);
                Intent intent = new Intent(context, FeedbackActivity.class);
                context.startActivity(intent);

            }
        });

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_my_show_layout_item, parent, false);
        BookMyShowRecyclerAdapter.ViewHolder holder = new BookMyShowRecyclerAdapter.ViewHolder(view);
        return holder;

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint.toString().isEmpty()){

            }


            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView filmNameTxtView, showTimeTxtView, languageTxtView, categoryTxtView, castTxtView, seatTxtView, cityTxtView;

        private Button bookNowBtn, feedbackBtn;


        public ViewHolder(@NonNull View v) {
            super(v);
            filmNameTxtView = v.findViewById(R.id.filmNameTxtView);
            showTimeTxtView = v.findViewById(R.id.showTimeTxtView);

            languageTxtView = v.findViewById(R.id.languageTxtView);
            categoryTxtView = v.findViewById(R.id.categoryDescriptionTxtView);
            castTxtView = v.findViewById(R.id.castTxtView);
            seatTxtView = v.findViewById(R.id.availableSeatTxtView);
            cityTxtView = v.findViewById(R.id.cityLocalityTxtView);
            bookNowBtn = v.findViewById(R.id.bookNowBtn);
            feedbackBtn = v.findViewById(R.id.feedbackBtn);

        }

    }

}
