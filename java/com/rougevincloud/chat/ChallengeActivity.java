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
import com.rougevincloud.chat.lists.ListScoreAdapter;
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

                //todo set view
                getListView().invalidateViews();
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
