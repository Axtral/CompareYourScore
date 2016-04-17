package com.rougevincloud.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.lists.ChallengeItem;

import java.util.Objects;

public class CreateChallengeActivity extends AppCompatActivity {
    public final static String EXTRA_NEWCHALLENGE_ID = "com.rougevincloud.chat.NEWCHALLENGE_ID";
    public final static String EXTRA_NEWCHALLENGE_TITLE = "com.rougevincloud.chat.NEWCHALLENGE_TITLE";
    public final static String EXTRA_NEWCHALLENGE_IMG = "com.rougevincloud.chat.NEWCHALLENGE_IMG";
    public final static String EXTRA_NEWCHALLENGE_DESC = "com.rougevincloud.chat.NEWCHALLENGE_DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        Button submit = (Button) findViewById(R.id.submit);
        if (submit == null)
            throw new NullPointerException();
        final EditText title = (EditText) findViewById(R.id.title);
        if (title == null)
            throw new NullPointerException();
        final EditText desc = (EditText) findViewById(R.id.desc);
        if (desc == null)
            throw new NullPointerException();
        final EditText img = (EditText) findViewById(R.id.img);
        if (img == null)
            throw new NullPointerException();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(title.getText().toString(), "")) {
                    title.setHintTextColor(getColor(R.color.error));
                    return;
                }
                if (Objects.equals(desc.getText().toString(), "")) {
                    desc.setHintTextColor(getColor(R.color.error));
                    return;
                }

                ChallengeItem newChallenge = new ChallengeItem(0, img.getText().toString(), title.getText().toString(), desc.getText().toString());

                Log.d("img", img.getText().toString());
                Log.d("title", title.getText().toString());
                Log.d("desc", desc.getText().toString());
                Integer id = Server.addChallenge(newChallenge);
                if (id != null) {
                    Intent intent = new Intent(CreateChallengeActivity.this, MainActivity.class);
                    intent.putExtra(EXTRA_NEWCHALLENGE_ID, id);
                    intent.putExtra(EXTRA_NEWCHALLENGE_TITLE, newChallenge.getTitle());
                    intent.putExtra(EXTRA_NEWCHALLENGE_DESC, newChallenge.getDesc());
                    intent.putExtra(EXTRA_NEWCHALLENGE_IMG, newChallenge.getImg());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
