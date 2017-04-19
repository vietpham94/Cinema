package com.example.vietd.cinema;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Admin on 4/13/2017.
 */

public class TextTabhostFragment extends Fragment {
    private TextView tv_showing, tv_feature, tv_comming;
    View myView;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.content_main,container,false);


        Fragment featureMovieFragment = new FeatureMovieFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame_text_tabhost, featureMovieFragment).addToBackStack(null).commit();

        tv_showing = (TextView)myView.findViewById(R.id.tv_showing_movie);
        tv_showing.setTextColor(Color.GRAY);
        tv_comming = (TextView)myView.findViewById(R.id.tv_coming_movie);
        tv_comming.setTextColor(Color.GRAY);
        tv_feature = (TextView)myView.findViewById(R.id.tv_feature_movie);
        tv_feature.setTextColor(Color.WHITE);

        tv_showing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_feature.setTextColor(Color.GRAY);
                tv_comming.setTextColor(Color.GRAY);
                tv_showing.setTextColor(Color.WHITE);
                Fragment showingMovieFragment = new ShowingMovieFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame_text_tabhost, showingMovieFragment).addToBackStack(null).commit();
            }
        });
        tv_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_showing.setTextColor(Color.GRAY);
                tv_comming.setTextColor(Color.GRAY);
                tv_feature.setTextColor(Color.WHITE);
                Fragment featureMovieFragment = new FeatureMovieFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame_text_tabhost, featureMovieFragment).addToBackStack(null).commit();
            }
        });
        tv_comming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_showing.setTextColor(Color.GRAY);
                tv_comming.setTextColor(Color.WHITE);
                tv_feature.setTextColor(Color.GRAY);
                Fragment comingMovieFragment = new ComingMovieFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame_text_tabhost, comingMovieFragment).addToBackStack(null).commit();
            }
        });
        return myView;
    }


}

