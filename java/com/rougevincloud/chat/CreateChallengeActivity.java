package com.rougevincloud.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rougevincloud.chat.data_managers.Server;

import java.util.Objects;

public class CreateChallengeActivity extends AppCompatActivity {
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
        EditText img = (EditText) findViewById(R.id.img);
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

                //Server.addChallenge();  //todo
            }
        });
    }
}
