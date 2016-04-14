package com.rougevincloud.chat.lists;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rougevincloud.chat.R;
import com.rougevincloud.chat.lib.cache.ImageLoader;

import java.util.List;

public class ListChallengeAdapter extends BaseAdapter {
    private List<ChallengeItem> items;
    private Context context;
    private ImageLoader imageLoader;

    public ListChallengeAdapter(Context context, List<ChallengeItem> items) {
        this.items = items;
        this.context = context;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_challenge, viewGroup, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.desc = (TextView) view.findViewById(R.id.desc);
            holder.position = i;
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        imageLoader.displayImage(items.get(i).getImg(), holder.icon);
        holder.title.setText(items.get(i).getTitle());
        holder.desc.setText(items.get(i).getDesc());

        return view;
    }

    private static class ViewHolder {
        TextView title;
        TextView desc;
        ImageView icon;
        int position;
    }
}
