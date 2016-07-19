package com.challenge.spiderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/12/16.
 */
public class ResultsGridAdapter extends ArrayAdapter<Results> {

    List<Results> resultsesList;
    Context context;

    public ResultsGridAdapter(Context context, List<Results> resultsesList) {
        super(context, 0, resultsesList);
        this.context = context;
        this.resultsesList = resultsesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Results results = getItem(position);
        holder.textView.setText(String.valueOf(results.issue));

        Picasso.with(context)
                .load(context.getResources().getString(R.string.image_link, results.path))
                .placeholder(R.drawable.default_placeholder)
                .into(holder.imageView);

        return convertView;
    }

    @Override
    public int getCount() {
        return resultsesList.size();
    }

    public Results getItem(int position){
        return resultsesList.get(position);
    }


    public class ViewHolder {

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.issue)
        TextView textView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
