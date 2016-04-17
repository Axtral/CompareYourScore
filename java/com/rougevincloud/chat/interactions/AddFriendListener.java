package com.rougevincloud.chat.interactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.rougevincloud.chat.AddFriendActivity;

public class AddFriendListener implements View.OnClickListener {
    private Activity activity;

    public AddFriendListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, AddFriendActivity.class);
        activity.startActivity(intent);
    }
}
