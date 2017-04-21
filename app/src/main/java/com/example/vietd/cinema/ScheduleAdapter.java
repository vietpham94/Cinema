package com.example.vietd.cinema;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Optimus on 4/7/2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private List<ScheduleInfo> scheduleInfos;
    private Context context;
    private MovieInfoForBooking movie;

    public ScheduleAdapter(List<ScheduleInfo> scheduleInfos, MovieInfoForBooking movie) {
        this.scheduleInfos = scheduleInfos;
        this.movie = movie;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.MyViewHolder holder, int position) {
        ScheduleInfo scheduleInfo = scheduleInfos.get(position);
        holder.time.setText(scheduleInfo.getTime());
    }

    @Override
    public int getItemCount() {
        return scheduleInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView time;

        public MyViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.txt_schedule);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent =  new Intent(context, BookingChairActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DATA", movie);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            final Intent intent;
            intent =  new Intent(context, BookingChairActivity.class);
            context.startActivity(intent);
        }
    }
}
