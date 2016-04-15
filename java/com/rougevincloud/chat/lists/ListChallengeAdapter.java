package com.rougevincloud.chat.lists;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rougevincloud.chat.ChallengeActivity;
import com.rougevincloud.chat.MainActivity;
import com.rougevincloud.chat.R;
import com.rougevincloud.chat.lib.cache.ImageLoader;

import java.util.List;

public class ListChallengeAdapter extends BaseAdapter {
    private List<ChallengeItem> items;
    private Activity activity;
    private ImageLoader imageLoader;
    public final static String EXTRA_ID = "com.rougevincloud.chat.ID";
    public final static String EXTRA_IDUSER = "com.rougevincloud.chat.IDUSER";

    public ListChallengeAdapter(Activity activity, List<ChallengeItem> items) {
        this.items = items;
        this.activity = activity;
        imageLoader = new ImageLoader(activity);
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
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_challenge, viewGroup, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.desc = (TextView) view.findViewById(R.id.desc);
            holder.position = i;
            holder.onClick = new ChallengeClick(activity, items.get(i).getId());
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        imageLoader.displayImage(items.get(i).getImg(), holder.icon);
        holder.title.setText(items.get(i).getTitle());
        holder.desc.setText(items.get(i).getDesc());
        view.setOnClickListener(holder.onClick);

        return view;
    }

    private static class ViewHolder {
        TextView title;
        TextView desc;
        ImageView icon;
        int position;
        ChallengeClick onClick;
    }

    private class ChallengeClick implements View.OnClickListener {
        private Activity activity;
        private int id;

        public ChallengeClick(Activity activity, int id) {
            this.activity = activity;
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ChallengeActivity.class);
            intent.putExtra(EXTRA_ID, id);
            intent.putExtra(EXTRA_IDUSER, ((MainActivity) activity).getIdUser());
            activity.startActivity(intent);
        }
    }
}
