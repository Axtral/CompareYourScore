package com.rougevincloud.chat;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.rougevincloud.chat.data_managers.Server;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.lists.ScoreItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChallengeActivity extends ListActivity {
    private String[] cols = new String[] {"name", "score"};
    private List<ScoreItem> scores;
    private ArrayList< HashMap<String, String> > scoresViewContent = new ArrayList<>();
    private ScoreItem ownScore = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int idChallenge = getIntent().getExtras().getInt(ListChallengeAdapter.EXTRA_ID);
        final int idUser = getIntent().getExtras().getInt(ListChallengeAdapter.EXTRA_IDUSER);
        setContentView(R.layout.activity_challenge);

        Button btn = (Button) findViewById(R.id.update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText scoreEdit = (EditText) findViewById(R.id.scoreEdit);
                String newScore = scoreEdit.getText().toString();
                if (Objects.equals(newScore, "")) {
                    scoreEdit.setHintTextColor(getColor(R.color.error));
                    return;
                }

                Server.setScore(idChallenge, idUser, Integer.parseInt(newScore));

                if (ownScore != null) {
                    scoresViewContent.get(0).remove("score");
                    scoresViewContent.get(0).put("score", newScore);
                }
                else
                    addSportViewContent("You", Integer.parseInt(newScore), 0);
                getListView().invalidateViews();
            }
        });

        scores = Server.findScoresByChallenge(idChallenge);
        if (scores == null)
            return;
        scoresViewContent = new ArrayList<>();
        for (ScoreItem score : scores) {
            if (score.getUser().getId() == idUser) {
                ownScore = score;
                continue;
            }
            addSportViewContent(score.getUser().getPseudo(), score.getScore(), null);
        }
        if (ownScore != null) //own score first
            addSportViewContent("You", ownScore.getScore(), 0);

        ListAdapter adapter = new SimpleAdapter(this,
                scoresViewContent,
                android.R.layout.two_line_list_item,
                cols,
                new int[] {android.R.id.text1, android.R.id.text2});

        setListAdapter(adapter);
    }

    private void addSportViewContent(String name, int score, Integer index) {
        if (index != null)
            scoresViewContent.add(index, new HashMap<String, String>());
        else {
            scoresViewContent.add(new HashMap<String, String>());
            index = scoresViewContent.size()-1;
        }
        scoresViewContent.get(index).put("name", name);
        scoresViewContent.get(index).put("score", "Score : " + String.valueOf(score));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
