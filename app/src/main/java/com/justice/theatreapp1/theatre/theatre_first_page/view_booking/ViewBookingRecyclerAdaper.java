package com.justice.theatreapp1.theatre.theatre_first_page.view_booking;

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
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;

import java.util.List;

public class ViewBookingRecyclerAdaper extends FirestoreRecyclerAdapter<BookData, ViewBookingRecyclerAdaper.ViewHolder> {
    private Context context;

    public ViewBookingRecyclerAdaper(Context context, @NonNull FirestoreRecyclerOptions<BookData> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull BookData model) {
        holder.email.setText(model.getEmail());
        holder.filmName.setText(model.getFilmName());
        holder.seat.setText("" + model.getNoOfSeats());


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_booking_layout, parent, false);
        ViewBookingRecyclerAdaper.ViewHolder holder = new ViewBookingRecyclerAdaper.ViewHolder(view);
        return holder;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView email, filmName, seat;

        public ViewHolder(@NonNull View v) {
            super(v);
            email = v.findViewById(R.id.email);
            filmName = v.findViewById(R.id.filmName);
            seat = v.findViewById(R.id.seatTxtView);
        }
    }
}
