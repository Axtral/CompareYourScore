package com.rougevincloud.chat.data_managers;

import com.rougevincloud.chat.LoginActivity;
import com.rougevincloud.chat.lists.ChallengeItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Server {
    public static final String url = "http://rougevincloud.com/android/";

    private Server() {}

    public static Boolean pseudoExists(String pseudo, LoginActivity activity) {
        JSONObject result;
        try {
            result = new JSONGetter(url + "pseudo.php?pseudo=" + pseudo).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return result.getBoolean("response");
        } catch (Exception e) {
            activity.toast("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean connect(String pseudo, String passwd, LoginActivity activity) {
        JSONObject result;
        try {
            passwd = hashPasswd(passwd);
            activity.setHashPasswd(passwd);
            result = new JSONGetter(url + "pseudo.php?pseudo=" + pseudo+"&passwd="+passwd).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                activity.toast(result.getString("message"));
            return response;
        } catch (Exception e) {
            activity.toast("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean register(String pseudo, String passwd, LoginActivity activity) {
        JSONObject result;
        try {
            passwd = hashPasswd(passwd);
            activity.setHashPasswd(passwd);
            result = new JSONGetter(/*url + */"http://darkicex3.alwaysdata.net/android_app/insert/insertUser.php?username=" + pseudo +"&password="+ passwd).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                activity.toast(result.getString("message"));
            return response;
        } catch (Exception e) {
            activity.toast("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static List<ChallengeItem> findAllChallenges() {
        try {
            JSONObject result = new JSONGetter(url + "aze.json").execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseChallenges(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ChallengeItem> findFriendsChallenges(String pseudo) {
        try {
            JSONObject result = new JSONGetter(url + "aze.json?pseudo=" + pseudo).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseChallenges(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hashPasswd(String passwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hash =  md.digest((passwd + "azeqsdwxc").getBytes("UTF-8"));

        //hash to hex
        StringBuilder sb = new StringBuilder();
        for (byte hashByte : hash) {
            sb.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static List<ChallengeItem> parseChallenges(JSONObject result) throws Exception {
        List<ChallengeItem> list = new ArrayList<>();
        JSONArray data = result.getJSONArray("data");
        int i = 0;
        while (i < data.length()) {
            JSONObject item = data.getJSONObject(i++);
            int id = item.getInt("id");
            String iconUrl = item.getString("icon");
            String title = item.getString("title");
            String desc = item.getString("desc");
            list.add(new ChallengeItem(id, iconUrl, title, desc));
        }

        return list;
    }
}
