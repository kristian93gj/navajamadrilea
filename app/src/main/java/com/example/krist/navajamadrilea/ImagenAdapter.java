package com.example.krist.navajamadrilea;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagenAdapter extends PagerAdapter {
    private Context mContext;
    private int [] mimagen=new int [] {R.drawable.ocaso2,R.drawable.ocaso3};
    ImagenAdapter(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return mimagen.length;
    }

    @Override
    public boolean isViewFromObject( View view,  Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView= new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mimagen[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
