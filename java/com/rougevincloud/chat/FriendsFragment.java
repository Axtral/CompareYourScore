package com.rougevincloud.chat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.rougevincloud.chat.data_managers.DBOpenHelper;
import com.rougevincloud.chat.interactions.ChallengeClick;
import com.rougevincloud.chat.lists.ChallengeItem;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.data_managers.Server;

import java.util.ArrayList;
import java.util.List;

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
            String[] cols = {DBOpenHelper.COLUMN_IMG, DBOpenHelper.COLUMN_TITLE, DBOpenHelper.COLUMN_DESC};
            Cursor cursor = db.query(DBOpenHelper.DATABASE_TABLE_FRIENDS, cols, null, null, null, null, null);
            int titleIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_TITLE);
            int descIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_DESC);
            int imgIndex = cursor.getColumnIndex(DBOpenHelper.COLUMN_IMG);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String title = cursor.getString(titleIndex);
                String desc = cursor.getString(descIndex);
                String img = cursor.getString(imgIndex);
                challenges.add(new ChallengeItem(img, title, desc));
            }

            cursor.close();
        }
        else {
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
                values.put(DBOpenHelper.COLUMN_TITLE, item.getTitle());
                values.put(DBOpenHelper.COLUMN_DESC , item.getDesc());
                values.put(DBOpenHelper.COLUMN_IMG , item.getImg());
                db.insert(DBOpenHelper.DATABASE_TABLE_FRIENDS, null, values);
            }
            db.close();
        }

        setListAdapter(new ListChallengeAdapter(getContext(), challenges));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(new ChallengeClick(getActivity()));
    }
}
