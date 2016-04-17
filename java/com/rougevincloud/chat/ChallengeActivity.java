package com.rougevincloud.chat;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.rougevincloud.chat.lists.ChallengeItem;
import com.rougevincloud.chat.lists.ListChallengeAdapter;
import com.rougevincloud.chat.lists.ListScoreAdapter;
import com.rougevincloud.chat.lists.ScoreItem;
import com.rougevincloud.chat.lists.UserItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChallengeActivity extends ListActivity {
    private String[] cols = new String[] {"name", "score"};
    private List<ScoreItem> scores;
    private ArrayList< HashMap<String, String> > scoresViewContent = new ArrayList<>();
    private ScoreItem ownScore = null;
    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int idChallenge = getIntent().getExtras().getInt(ListChallengeAdapter.EXTRA_ID);
        SharedPreferences prefs = getSharedPreferences("user_infos", MODE_PRIVATE);
        String pseudo = prefs.getString("pseudo", null);
        String passwd = prefs.getString("passwd", null);
        int idUser = prefs.getInt("id", 0);
        user = new UserItem(idUser, pseudo, passwd);
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

                Integer id = Server.setScore(idChallenge, user.getId(), Integer.parseInt(newScore));
                if (id != null) {
                    ChallengeItem challenge = Server.findChallengeById(idChallenge);
                    ScoreItem score = new ScoreItem(id, challenge, user, Integer.parseInt(newScore));
                    int index = scores.size()-1;
                    for (int i =  0; i < scores.size(); ++i) {
                        if (index == scores.size()-1 && scores.get(i).getScore() < score.getScore()) {
                            index = i;
                        }
                        if (scores.get(i).getUser().getId() == user.getId()) {
                            scores.remove(i);
                        }
                    }
                    scores.add(index, score);
                    getListView().invalidateViews();
                    scoreEdit.setText("");
                }
            }
        });

        scores = Server.findScoresByChallenge(idChallenge);
        if (scores == null)
            return;

        setListAdapter(new ListScoreAdapter(this, scores));
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
