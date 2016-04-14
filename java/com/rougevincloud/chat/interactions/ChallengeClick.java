package com.rougevincloud.chat.interactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.rougevincloud.chat.ChallengeActivity;


public class ChallengeClick implements AdapterView.OnItemClickListener {
    private Activity activity;

    public ChallengeClick(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity, ChallengeActivity.class);
        activity.startActivity(intent);
    }
}
