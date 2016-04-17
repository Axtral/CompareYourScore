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
import com.rougevincloud.chat.interactions.CreateChallengeListener;
import com.rougevincloud.chat.lists.ChallengeItem;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.lists.UserItem;

import java.util.ArrayList;
import java.util.List;

public class WorldFragment extends ListFragment {
    private List<ChallengeItem> challenges;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list_challenges, container, false);

        DBOpenHelper helper = DBOpenHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_IMG, DBOpenHelper.COLUMN_TITLE, DBOpenHelper.COLUMN_DESC};
        Cursor cursor = db.query(DBOpenHelper.DATABASE_TABLE_ALL, cols, null, null, null, null, null);
        //getContext().getDatabasePath(DBOpenHelper.DATABASE_NAME).delete();
        Integer nbChallengesOnline = Server.countChallenges();
        int nbChallengesLocal = cursor.getCount();
        if (!DBOpenHelper.isJustCreated() && nbChallengesOnline==null && nbChallengesLocal==nbChallengesOnline) {
            //read from db
            challenges = new ArrayList<>();
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
        }
        else {
            //get challenges from web
            challenges = Server.findAllChallenges();

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
                db.insert(DBOpenHelper.DATABASE_TABLE_ALL, null, values);
            }
            db.close();
        }

        rootView.findViewById(R.id.add_challenge).setOnClickListener(new CreateChallengeListener(getActivity()));

        setListAdapter(new ListChallengeAdapter(getActivity(), challenges));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras == null)
            return;
        String newChallengeTitle = extras.getString(CreateChallengeActivity.EXTRA_NEWCHALLENGE_TITLE);
        if (newChallengeTitle == null)
            return;
        String newChallengeDesc = extras.getString(CreateChallengeActivity.EXTRA_NEWCHALLENGE_DESC);
        int newChallengeId = getActivity().getIntent().getExtras().getInt(CreateChallengeActivity.EXTRA_NEWCHALLENGE_ID);
        String newChallengeImg = extras.getString(CreateChallengeActivity.EXTRA_NEWCHALLENGE_IMG);
        ChallengeItem newChallenge = new ChallengeItem(newChallengeId, newChallengeImg, newChallengeTitle, newChallengeDesc);
        challenges.add(newChallenge);
        getListView().invalidateViews();

        DBOpenHelper helper = DBOpenHelper.getInstance(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_ID, newChallenge.getId());
        values.put(DBOpenHelper.COLUMN_TITLE, newChallenge.getTitle());
        values.put(DBOpenHelper.COLUMN_DESC, newChallenge.getDesc());
        values.put(DBOpenHelper.COLUMN_IMG, newChallenge.getImg());
        db.insert(DBOpenHelper.DATABASE_TABLE_ALL, null, values);
        db.close();
    }
}
