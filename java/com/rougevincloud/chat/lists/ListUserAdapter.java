package com.rougevincloud.chat.lists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rougevincloud.chat.AddFriendActivity;
import com.rougevincloud.chat.ChallengeActivity;
import com.rougevincloud.chat.CreateChallengeActivity;
import com.rougevincloud.chat.MainActivity;
import com.rougevincloud.chat.R;
import com.rougevincloud.chat.lib.cache.ImageLoader;

import java.util.List;

public class ListUserAdapter extends BaseAdapter {
    private List<UserItem> items;
    private Activity activity;
    private ImageLoader imageLoader;
    public final static String EXTRA_ID = "com.rougevincloud.chat.ID";
    public final static String EXTRA_IDUSER = "com.rougevincloud.chat.IDUSER";

    public ListUserAdapter(Activity activity, List<UserItem> items) {
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
            view = inflater.inflate(R.layout.item_user, viewGroup, false);

            holder = new ViewHolder();
            holder.friendName = (TextView) view.findViewById(R.id.friendName);
            holder.position = i;
            holder.onClick = new UserClick(activity, items.get(i).getId());
            view.setTag(holder);
        }
        else
            holder = (ViewHolder) view.getTag();

        holder.friendName.setText(items.get(i).getPseudo());
        view.setOnClickListener(holder.onClick);

        return view;
    }

    private static class ViewHolder {
        TextView friendName;
        ImageView img;  //todo implement user images
        int position;
        UserClick onClick;
    }

    private class UserClick implements View.OnClickListener {
        private Activity activity;
        private int id;

        public UserClick(Activity activity, int id) {
            this.activity = activity;
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, AddFriendActivity.class);
            intent.putExtra(EXTRA_IDUSER, ((MainActivity) activity).getIdUser());
            activity.startActivity(intent);
        }
    }
}
