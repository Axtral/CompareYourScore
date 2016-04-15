package com.rougevincloud.chat;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.lists.ScoreItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChallengeActivity extends ListActivity {
    private String[] cols = new String[] {"nom", "score"};
    private List<ScoreItem> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("id", String.valueOf(getIntent().getExtras().getInt(ListChallengeAdapter.EXTRA_ID)));
        int idChallenge = getIntent().getExtras().getInt(ListChallengeAdapter.EXTRA_ID);
        setContentView(R.layout.activity_challenge);

        scores = Server.findScoresByChallenge(idChallenge);
        if (scores == null)
            return;
        List< HashMap<String, String> > scoresViewContent = new ArrayList<>();
        for (ScoreItem score : scores) {
            scoresViewContent.add(new HashMap<String, String>());
            int index = scoresViewContent.size()-1;
            scoresViewContent.get(index).put("nom", String.valueOf(score.getUser()));
            scoresViewContent.get(index).put("score", "Score : " + String.valueOf(score.getScore()));
        }

        ListAdapter adapter = new SimpleAdapter(this,
                scoresViewContent,
                android.R.layout.two_line_list_item,
                cols,
                new int[] {android.R.id.text1, android.R.id.text2});

        setListAdapter(adapter);
    }
}
