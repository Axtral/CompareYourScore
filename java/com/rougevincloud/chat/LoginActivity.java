package com.rougevincloud.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rougevincloud.chat.data_managers.Server;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private boolean lookingForPseudo = false;
    private Boolean pseudoExists = false;
    private EditText pseudo = null;
    private TextView invite = null;
    private String hashPasswd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pseudo = (EditText) findViewById(R.id.pseudo);
        if (pseudo == null)
            throw new NullPointerException("id pseudo doesn't exists");
        invite = (TextView) findViewById(R.id.invite);
        if (invite == null)
            throw new NullPointerException("id invite doesn't exists");
        Button b = (Button) findViewById(R.id.form_ok);
        if (b == null)
            throw new NullPointerException("id form_ok doesn't exists");
        final EditText passwd = (EditText) findViewById(R.id.passwd);
        if (passwd == null)
            throw new NullPointerException("id passwd doesn't exists");

        pseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!lookingForPseudo) {
                    lookingForPseudo = true;
                    invite.setText(R.string.invite_loading);
                    new Timer().schedule(new PseudoLooker(), 500);
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(pseudo.getText().toString(), ""))
                    pseudo.setHintTextColor(getColor(R.color.error));
                if (Objects.equals(passwd.getText().toString(), ""))
                    passwd.setHintTextColor(getColor(R.color.error));

                //server work
                Boolean ok;
                Integer id;
                if (pseudoExists) { //connection
                    id = Server.connect(pseudo.getText().toString(), passwd.getText().toString(), LoginActivity.this);
                    ok = id != null;
                }
                else {  //register
                    id = Server.register(pseudo.getText().toString(), passwd.getText().toString(), LoginActivity.this);
                    ok = id != null;
                }

                //app reaction
                if (ok && hashPasswd != null) {
                        SharedPreferences prefs = getSharedPreferences("user_infos", MODE_PRIVATE);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putInt("id", id);
                        edit.putString("pseudo", pseudo.getText().toString());
                        edit.putString("passwd", hashPasswd);
                        edit.apply();

                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(main);
                        finish();
                }
            }//OnClick()
        });
    }

    public void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setHashPasswd(String hashPasswd) {
        this.hashPasswd = hashPasswd;
    }

    private class PseudoLooker extends TimerTask {
        private int text;

        @Override
        public void run() {
            text = R.string.invite;
            if (!Objects.equals(pseudo.getText().toString(), "")) {
                pseudoExists = Server.pseudoExists(pseudo.getText().toString(), LoginActivity.this);
                if (pseudoExists != null)
                    if ( pseudoExists )
                        text = R.string.invite_connection;
                    else
                        text = R.string.invite_inscription;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    invite.setText(text);
                }
            });
            lookingForPseudo = false;
        }
    }
}
