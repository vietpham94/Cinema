package com.example.vietd.cinema;

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

import com.squareup.picasso.Picasso;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class ShowingMovieFragment extends Fragment {


    public static String[] covers = {"http://192.168.0.32:3000/public/images/nhoc.jpg", "http://192.168.0.32:3000/public/images/f8.jpg", "http://192.168.0.32:3000/public/images/pr2017.png"};
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.showing_movie_fragment,container,false);

        PagerContainer pagerContainer = (PagerContainer) myView.findViewById(R.id.pager_container);
        ViewPager viewPager = pagerContainer.getViewPager();
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setClipChildren(false);

        new CoverFlow.Builder()
                .with(viewPager)
                .scale(0.3f)
                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                .spaceSize(10f)
                .build();
        viewPager.setPageMargin(40);

        return myView;
    }


    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cover, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            Picasso.with(getActivity().getBaseContext()).load(covers[position]).into(imageView);
            //imageView.setImageDrawable(getResources().getDrawable(covers[position]));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(view);

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
