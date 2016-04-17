package com.rougevincloud.chat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rougevincloud.chat.data_managers.DBOpenHelper;
import com.rougevincloud.chat.lists.ChallengeItem;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.data_managers.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FriendsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_friends, container, false);

        List<ChallengeItem> challenges;


        DBOpenHelper helper = DBOpenHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        //getContext().getDatabasePath(DBOpenHelper.DATABASE_NAME).delete();
        if (!DBOpenHelper.isJustCreated()) {
            //read from db
            challenges = new ArrayList<>();
            String[] cols = {DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_IMG, DBOpenHelper.COLUMN_TITLE, DBOpenHelper.COLUMN_DESC};
            Cursor cursor = db.query(DBOpenHelper.DATABASE_TABLE_FRIENDS, cols, null, null, null, null, null);
            int idIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_TITLE);
            int descIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_DESC);
            int imgIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_IMG);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String desc = cursor.getString(descIndex);
                String img = cursor.getString(imgIndex);
                challenges.add(new ChallengeItem(id, img, title, desc));
            }

            cursor.close();
        } else {
            //get challenges from web
            challenges = Server.findFriendsChallenges(((MainActivity) getActivity()).getPseudo());

            //set challenges in cache
            db = DBOpenHelper.getInstance(getContext()).getWritableDatabase();
            if (challenges == null) {
                Toast.makeText(getContext(), "Unable to get challenges", Toast.LENGTH_LONG).show();
                return rootView;
            }
            for (ChallengeItem item : challenges) {
                ContentValues values = new ContentValues();
                values.put(DBOpenHelper.COLUMN_ID, item.getId());
                values.put(DBOpenHelper.COLUMN_TITLE, item.getTitle());
                values.put(DBOpenHelper.COLUMN_DESC, item.getDesc());
                values.put(DBOpenHelper.COLUMN_IMG, item.getImg());
                db.insert(DBOpenHelper.DATABASE_TABLE_FRIENDS, null, values);
            }
            db.close();
        }

        setListAdapter(new ListChallengeAdapter(getActivity(), challenges));

        return rootView;

//        ////////////////GET VALUES
//        pseudo = (EditText) findViewById(R.id.pseudo);
//        if (pseudo == null)
//            throw new NullPointerException("id pseudo doesn't exists");
//        invite = (TextView) findViewById(R.id.invite);
//        if (invite == null)
//            throw new NullPointerException("id invite doesn't exists");
//        Button b = (Button) findViewById(R.id.form_ok);
//        if (b == null)
//            throw new NullPointerException("id form_ok doesn't exists");
//        final EditText passwd = (EditText) findViewById(R.id.passwd);
//        if (passwd == null)
//            throw new NullPointerException("id passwd doesn't exists");
//
//        //////////////SET LISTENERS ON AFTER TEXT CHANGED TEXT BOX ADD FRIENDS
//        pseudo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!lookingForPseudo) {
//                    lookingForPseudo = true;
//                    invite.setText(R.string.invite_loading);
//                    new Timer().schedule(new FriendLooker(), 500);
//                }
//            }
//        });
//
//        ////////////WHEN CLICK ON ADD FRIEND
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Objects.equals(pseudo.getText().toString(), ""))
//                    pseudo.setHintTextColor(getColor(R.color.error));
//                if (Objects.equals(passwd.getText().toString(), ""))
//                    passwd.setHintTextColor(getColor(R.color.error));
//
//                //server work
//                Boolean ok;
//                Integer id;
//                if (pseudoExists) { //connection
//                    id = Server.connect(pseudo.getText().toString(), passwd.getText().toString(), LoginActivity.this);
//                    ok = id != null;
//                } else {  //register
//                    id = Server.register(pseudo.getText().toString(), passwd.getText().toString(), LoginActivity.this);
//                    ok = id != null;
//                }
//
//                //app reaction
//                if (ok && hashPasswd != null) {
//                    SharedPreferences prefs = getSharedPreferences("user_infos", MODE_PRIVATE);
//                    SharedPreferences.Editor edit = prefs.edit();
//                    edit.putInt("id", id);
//                    edit.putString("pseudo", pseudo.getText().toString());
//                    edit.putString("passwd", hashPasswd);
//                    edit.apply();
//
//                    Intent main = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(main);
//                    finish();
//                }
//            }//OnClick()
//        });
//    }
//
//    //////////WHEN ENTER NAME OF FRIEND
//    private class FriendLooker extends TimerTask {
//        private int text;
//
//        @Override
//        public void run() {
//            text = R.string.invite;
//            if (!Objects.equals(pseudo.getText().toString(), "")) {
//                pseudoExists = Server.pseudoExists(pseudo.getText().toString(), LoginActivity.this);
//                if (pseudoExists != null)
//                    if (pseudoExists)
//                        text = R.string.invite_connection;
//                    else
//                        text = R.string.invite_inscription;
//            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    invite.setText(text);
//                }
//            });
//            lookingForPseudo = false;
//        }
    }
}
