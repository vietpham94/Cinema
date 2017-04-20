package com.example.vietd.cinema;

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

    public ScheduleAdapter2(List<ScheduleInfo2> scheduleInfos) {
        this.scheduleInfos = scheduleInfos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter2.MyViewHolder holder, int position) {
        ScheduleInfo2 scheduleInfo = scheduleInfos.get(position);
        holder.time.setText(scheduleInfo.getTime());
    }

    @Override
    public int getItemCount() {
        return scheduleInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;

        public MyViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.txt_schedule);
        }
    }
}
