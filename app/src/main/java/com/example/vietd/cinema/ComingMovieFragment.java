package com.example.vietd.cinema;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

import static com.google.android.gms.internal.zzir.runOnUiThread;


public class ComingMovieFragment extends Fragment {
    public String[] covers;
    View myView;
    private JSONArray jsonarray;
    private ArrayList<MovieInfo> arrayList;
    private PagerContainer pagerContainer;
    private ViewPager viewPager;

    Config mConfig = new Config();
    Socket mSocket = mConfig.mSocket;
    private Emitter.Listener getData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        arrayList = new ArrayList<MovieInfo>();
                        jsonarray = data.getJSONArray("listmovie");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            MovieInfo movies = new MovieInfo(
                                    jsonarray.getJSONObject(i).getString("name"),
                                    jsonarray.getJSONObject(i).getString("startday"),
                                    jsonarray.getJSONObject(i).getDouble("imdb"),
                                    jsonarray.getJSONObject(i).getInt("duration"),
                                    "http://192.168.0.28:3000" + jsonarray.getJSONObject(i).getString("image"),
                                    jsonarray.getJSONObject(i).getInt("ages"),
                                    jsonarray.getJSONObject(i).getInt("format"),
                                    jsonarray.getJSONObject(i).getInt("status")
                            );
                            arrayList.add(movies);
                        }
                        covers = new String[arrayList.size()];
                        for(int i=0; i<arrayList.size(); i++){
                            covers[i] = String.valueOf(arrayList.get(i).getPosterurl());
                        }
                        pagerContainer = (PagerContainer) myView.findViewById(R.id.pager_container);
                        viewPager = pagerContainer.getViewPager();
                        viewPager.setAdapter(new MyPagerAdapter());
                        // viewPager.setClipChildren(false);
                        new CoverFlow.Builder()
                                .with(viewPager)
                                .scale(0.3f)
                                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                                .spaceSize(10f)
                                .build();
                        viewPager.setPageMargin(40);

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mSocket.connect();
        mSocket.emit("getComingMovie");
        mSocket.on("listComingMovie", getData);

        myView=inflater.inflate(R.layout.fragment_coming_movie,container,false);

        return myView;
    }



    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cover, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_movie_name);
            TextView tv_movie_release_date = (TextView) view.findViewById(R.id.tv_movie_release_date);
            TextView tv_movie_duration = (TextView) view.findViewById(R.id.tv_movie_duration);

            Picasso.with(getActivity().getBaseContext()).load(covers[position]).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            try {
                tv_name.setText(String.valueOf(jsonarray.getJSONObject(position).getString("name")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tv_movie_release_date.setText(String.valueOf(jsonarray.getJSONObject(position).getString("startday")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                tv_movie_duration.setText(String.valueOf(jsonarray.getJSONObject(position).getInt("duration") + " phút"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            container.addView(view);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity().getApplicationContext(),MovieDetail.class);
                    try {
                        i.putExtra("name", jsonarray.getJSONObject(position).getString("name"));
                        i.putExtra("duration", String.valueOf(jsonarray.getJSONObject(position).getInt("duration")));
                        i.putExtra("director", jsonarray.getJSONObject(position).getString("director"));
                        i.putExtra("actornactress", jsonarray.getJSONObject(position).getString("actornactress"));
                        i.putExtra("nation", jsonarray.getJSONObject(position).getString("nation"));
                        i.putExtra("language", jsonarray.getJSONObject(position).getString("language"));
                        i.putExtra("category", jsonarray.getJSONObject(position).getString("category"));
                        i.putExtra("startday", jsonarray.getJSONObject(position).getString("startday"));
                        i.putExtra("format", jsonarray.getJSONObject(position).getString("format"));
                        i.putExtra("imdb", String.valueOf(jsonarray.getJSONObject(position).getDouble("imdb")));
                        i.putExtra("urltrailer", jsonarray.getJSONObject(position).getString("urltrailer"));
                        i.putExtra("content", jsonarray.getJSONObject(position).getString("content"));
                        i.putExtra("poster", "http://192.168.0.28:3000" + jsonarray.getJSONObject(position).getString("image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                }
            });

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return covers.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
