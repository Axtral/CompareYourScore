package com.rougevincloud.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.rougevincloud.chat.custom_views.CustomPageTransformer;
import com.rougevincloud.chat.data_managers.Server;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Fragment[] frags = new Fragment[3];
    private String pseudo;
    private Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set user
        SharedPreferences prefs = getSharedPreferences("user_infos", MODE_PRIVATE);
        pseudo = prefs.getString("pseudo", null);
        String passwd = prefs.getString("passwd", null);
        idUser = prefs.getInt("id", 0);
        if (pseudo == null || passwd == null || idUser == 0 || !Objects.equals(Server.connect(pseudo, passwd), idUser)) {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();
        }


        setContentView(R.layout.activity_main);
        TextView hello = (TextView) findViewById(R.id.welcome);
        if (hello == null)
            throw new NullPointerException("id welcome doesn't exists");

        hello.setText( String.format(getResources().getString(R.string.hello), pseudo) );

        //Set fragments
        frags[0] = new FindFriendsFragment();
        frags[1] = new FriendsChallengesFragment();
        frags[2] = new WorldFragment();

        //Set Pager
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        if (vp == null)
            throw new NullPointerException("id vp doesn't exists");
        PagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setPageTransformer(false, new CustomPageTransformer());
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getIdUser() {
        return idUser;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public final String[] fragmentNames = {"Find Friends", "Friends Challenges", "World Challenges"};

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames[position];
        }
    }

}
