package com.example.droid.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.droid.R;
import com.example.droid.model.user.Pretty;

import java.util.Collections;
import java.util.List;

/**
 * Created by ss on 7/14/2016.
 */
public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private List<Pretty.ResultsEntity> resultsEntities;

    public GalleryAdapter(Context context){
        this.context = context;
        this.resultsEntities = Collections.emptyList();
    }

    public void setPretty(Pretty pretty){
        this.resultsEntities = pretty.getResults();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(resultsEntities == null){
            return 0;
        }
        return resultsEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.txtViewName.setText(resultsEntities.get(position).getWho());
        Glide.with(context)
                .load(resultsEntities.get(position).getUrl())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .into(mHolder.imgViewPhoto);

        return convertView;
    }

    public  static class ViewHolder {
        TextView txtViewName;
        ImageView imgViewPhoto;
        ViewHolder(View view) {
            txtViewName  = (TextView) view.findViewById(R.id.tv_name);
            imgViewPhoto = (ImageView) view.findViewById(R.id.img_cover);

        }
    }
}
