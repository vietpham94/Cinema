package com.example.vietd.cinema;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Optimus on 4/7/2017.
 */

public class ScheduleAdapter2 extends RecyclerView.Adapter<ScheduleAdapter2.MyViewHolder> {

    private List<ScheduleInfo2> scheduleInfos;
    private Context context;
    private MovieInfoForBooking movie;

    public ScheduleAdapter2(List<ScheduleInfo2> scheduleInfos, MovieInfoForBooking movie) {
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
    public void onBindViewHolder(ScheduleAdapter2.MyViewHolder holder, int position) {
        ScheduleInfo2 scheduleInfo = scheduleInfos.get(position);
        holder.time.setText(scheduleInfo.getTime());
        holder.id_schedule.setText(String.valueOf(scheduleInfo.getId()));
        holder.date.setText(scheduleInfo.getDate());
        holder.room.setText(String.valueOf(scheduleInfo.getRoom()));
    }

    @Override
    public int getItemCount() {
        return scheduleInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView time, date, id_schedule, room;

        public MyViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.txt_schedule);
            date = (TextView) v.findViewById(R.id.tv_date_schedule);
            id_schedule = (TextView) v.findViewById(R.id.tv_id_schedule);
            room = (TextView) v.findViewById(R.id.tv_id_room);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent = new Intent(context, BookingChairActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DATA", movie);
                    bundle.putInt("id_time", Integer.parseInt(id_schedule.getText().toString()));
                    bundle.putString("date", date.getText().toString());
                    bundle.putInt("room", Integer.parseInt(room.getText().toString()));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            final Intent intent;
            intent = new Intent(context, BookingChairActivity.class);
            context.startActivity(intent);
        }
    }
}
