package com.rougevincloud.chat.interactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.rougevincloud.chat.CreateChallengeActivity;

public class CreateChallengeListener implements View.OnClickListener {
    private Activity activity;

    public CreateChallengeListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, CreateChallengeActivity.class);
        activity.startActivity(intent);
    }
}
