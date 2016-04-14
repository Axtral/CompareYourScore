package com.rougevincloud.chat;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ChallengeActivity extends ListActivity {
    ArrayList< HashMap<String, String> > scores = new ArrayList<>();
    private String[] cols = new String[] {"nom", "score"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        for (int i = 0; i < 10; ++i) {
            scores.add(new HashMap<String, String>());
            scores.get(i).put("nom", "Louis");
            scores.get(i).put("score", String.valueOf(i*100));
        }
        ListAdapter adapter = new SimpleAdapter(this,
                scores,
                android.R.layout.two_line_list_item,
                cols,
                new int[] {android.R.id.text1, android.R.id.text2});

        setListAdapter(adapter);
    }
}
