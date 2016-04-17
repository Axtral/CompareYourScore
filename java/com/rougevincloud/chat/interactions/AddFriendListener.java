package com.rougevincloud.chat.interactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.rougevincloud.chat.AddFriendActivity;
import com.rougevincloud.chat.MainActivity;

public class AddFriendListener implements View.OnClickListener {
    public final static String EXTRA_IDUSER = "com.rougevincloud.chat.IDUSER";
    private Activity activity;

    public AddFriendListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, AddFriendActivity.class);
        intent.putExtra(EXTRA_IDUSER, ((MainActivity) activity).getIdUser());
        activity.startActivity(intent);
    }
}
