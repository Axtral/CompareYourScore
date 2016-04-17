package com.rougevincloud.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.interactions.AddFriendListener;
import com.rougevincloud.chat.lists.UserItem;

import java.util.Objects;

public class AddFriendActivity extends AppCompatActivity {
    public final static String EXTRA_NEWFRIEND_PSEUDO = "com.rougevincloud.chat.NEWFRIEND_PSEUDO";
    public final static String EXTRA_NEWFRIEND_ID = "com.rougevincloud.chat.NEWFRIEND_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        final int idUser = getIntent().getExtras().getInt(AddFriendListener.EXTRA_IDUSER);

        Button submit = (Button) findViewById(R.id.submitFriend);
        if (submit == null)
            throw new NullPointerException();
        final EditText username = (EditText) findViewById(R.id.usernameFriend);
        if (username == null)
            throw new NullPointerException();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(username.getText().toString(), "")) {
                    username.setHintTextColor(getColor(R.color.error));
                    return;
                }

                UserItem friendToAdd = Server.findUserByUsername(username.getText().toString());
                if (friendToAdd != null && Server.addFriend(idUser, friendToAdd.getId()) != null) {
                    Intent intent = new Intent(AddFriendActivity.this, MainActivity.class);
                    intent.putExtra(EXTRA_NEWFRIEND_PSEUDO, friendToAdd.getPseudo());
                    intent.putExtra(EXTRA_NEWFRIEND_ID, friendToAdd.getId());
                    startActivity(intent);
                }
            }
        });
    }
}
