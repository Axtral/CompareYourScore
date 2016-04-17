package com.rougevincloud.chat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rougevincloud.chat.data_managers.DBOpenHelper;
import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.interactions.AddFriendListener;
import com.rougevincloud.chat.lists.ListUserAdapter;
import com.rougevincloud.chat.lists.UserItem;

import java.util.ArrayList;
import java.util.List;

public class FindFriendsFragment extends ListFragment {
    private List<UserItem> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_find_friends, container, false);

        DBOpenHelper helper = DBOpenHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        if(!DBOpenHelper.isJustCreated()) {
            //read from db
            friends = new ArrayList<>();
            String[] cols = {DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_USERNAME};
            Cursor cursor = db.query(DBOpenHelper.DATABASE_TABLE_ALL_FRIENDS, cols, null, null, null, null, null);
            int idIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_ID);
            int usernameIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_USERNAME);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String username = cursor.getString(usernameIndex);
                friends.add(new UserItem(id, username, ""));
            }
            cursor.close();
        }
        else {
            //get friends from web
            friends = Server.findAllFriends(((MainActivity) getActivity()).getIdUser());

            //set friends in cache
            db = DBOpenHelper.getInstance(getContext()).getWritableDatabase();
            if (friends == null) {
                Toast.makeText(getContext(), "Unable to get friends", Toast.LENGTH_LONG).show();
                return rootView;
            }
            for (UserItem item : friends) {
                ContentValues values = new ContentValues();
                values.put(DBOpenHelper.COLUMN_ID, item.getId());
                values.put(DBOpenHelper.COLUMN_USERNAME, item.getPseudo());
                db.insert(DBOpenHelper.DATABASE_TABLE_ALL_FRIENDS, null, values);
            }
            db.close();
        }

        if (friends != null)
            setListAdapter(new ListUserAdapter(getActivity(), friends));

        rootView.findViewById(R.id.add_friend).setOnClickListener(new AddFriendListener(getActivity()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras == null)
            return;
        String newFriendPseudo = extras.getString(AddFriendActivity.EXTRA_NEWFRIEND_PSEUDO);
        if (newFriendPseudo == null)
            return;
        int newFriendId = getActivity().getIntent().getExtras().getInt(AddFriendActivity.EXTRA_NEWFRIEND_ID);
        UserItem newFriend = new UserItem(newFriendId, newFriendPseudo, null);
        friends.add(newFriend);
        getListView().invalidateViews();
        getActivity().getIntent().removeExtra(AddFriendActivity.EXTRA_NEWFRIEND_ID);
        getActivity().getIntent().removeExtra(AddFriendActivity.EXTRA_NEWFRIEND_PSEUDO);

        DBOpenHelper helper = DBOpenHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_ID, newFriend.getId());
        values.put(DBOpenHelper.COLUMN_USERNAME, newFriend.getPseudo());
        db.insert(DBOpenHelper.DATABASE_TABLE_ALL_FRIENDS, null, values);
        db.close();
    }
}
