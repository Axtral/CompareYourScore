package com.rougevincloud.chat;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.lists.ListUserAdapter;
import com.rougevincloud.chat.lists.UserItem;

import java.util.List;

public class FindFriendsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_find_friends, container, false);

        List<UserItem> friends = Server.findAllFriends(((MainActivity) getActivity()).getIdUser());
        if (friends != null)
            setListAdapter(new ListUserAdapter(getActivity(), friends));

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
