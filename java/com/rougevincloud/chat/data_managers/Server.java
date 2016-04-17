package com.rougevincloud.chat.data_managers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.rougevincloud.chat.LoginActivity;
import com.rougevincloud.chat.lists.ChallengeItem;
import com.rougevincloud.chat.lists.ScoreItem;
import com.rougevincloud.chat.lists.UserItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Server {
    public static final String url = "http://darkicex3.alwaysdata.net/android_app/";

    private Server() {}

///////////////////////////////////////////////////////////////////////////////////////////////LOGIN
    @Nullable
    public static Boolean pseudoExists(String pseudo, LoginActivity activity) {
        JSONObject result;
        try {
            result = new JSONGetter(url + "pseudoExist.php?username=" + pseudo).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return result.getBoolean("response");
        } catch (Exception e) {
            activity.toast("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    } //SERVER OK

    @Nullable
    public static Integer connect(String pseudo, String passwd) {
        JSONObject result;
        try {
            result = new JSONGetter(url + "connectUser.php?username=" + pseudo + "&password=" +passwd)
                    .execute().get();

            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                //activity.toast(result.getString("message"));
                Log.d("message : ", result.getString("message"));
            return result.getInt("id");
        } catch (Exception e) {
            //activity.toast("Error : " + e.getMessage());
            Log.d("message : ", "Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    } //OK


    @Nullable
    public static Integer register(String pseudo, String passwd, LoginActivity activity) {
        JSONObject result;
        try {
            result = new JSONGetter(url + "insert/user.php?username=" + pseudo +"&password="+ passwd).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                activity.toast(result.getString("message"));
            return result.getInt("id");
        } catch (Exception e) {
            activity.toast("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    } //SERVER OK

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

///////////////////////////////////////////////////////////////////////////////////////////////USERS

    @Nullable
    public static List<UserItem> findAllFriends(int userId) {
        try {
            JSONObject result = new JSONGetter(url + "/show/friend.php?u_id="+userId).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseUsers(result, "friends");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static UserItem findUserById(int id) {
        try {
            JSONObject result = new JSONGetter(url + "show/user.php?id="+id).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseUsers(result, "users").get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static UserItem findUserByUsername(String username) {
        try {
            JSONObject result = new JSONGetter(url + "show/user.php?username="+username).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            List<UserItem> found = parseUsers(result, "users");
            if (found.size() < 0)
                return null;
            return parseUsers(result, "users").get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Boolean addFriend(int userId, int friendId) {
        try {
            JSONObject result = new JSONGetter(
                    url +"insert/friend.php?u_id="+ userId +
                            "&f_id="+ friendId).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                throw new Exception(result.getString("message"));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<UserItem> parseUsers(JSONObject result, String arrayName) throws Exception {
        List<UserItem> users = new ArrayList<>();

        JSONArray data = result.getJSONArray(arrayName);
        int i = 0;
        while (i < data.length()) {
            JSONObject item = data.getJSONObject(i++);
            int id = item.getInt("id");
            String username = item.getString("username");
            users.add(new UserItem(id, username, null));
        }
        return users;
    }

//////////////////////////////////////////////////////////////////////////////////////////CHALLENGES

    @Nullable
    public static Integer countChallenges() {
        try {
            JSONObject result = new JSONGetter(url + "show/challenge.php?action=count").execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return result.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Integer countFriendsChallenges(int id) {
        try {
            JSONObject result = new JSONGetter(url + "show/challenge.php?action=count&u_id="+id).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return result.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static ChallengeItem findChallengeById(int id) {
        try {
            JSONObject result = new JSONGetter(url + "show/challenge.php?id="+id).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseChallenges(result).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<ChallengeItem> findAllChallenges() {
        return findChallenges("", null);
    }

    public static List<ChallengeItem> findFriendsChallenges(int id) {
        return findChallenges("u_id", String.valueOf(id));
    }

    @Nullable
    private static List<ChallengeItem> findChallenges(String getField, String val) {
        try {
            String uri = "/show/challenge.php";
            if (val != null)
                uri += "?"+ getField +"="+ val;
            JSONObject result = new JSONGetter(url + uri).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseChallenges(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<ChallengeItem> parseChallenges(JSONObject result) throws Exception {
        List<ChallengeItem> list = new ArrayList<>();
        JSONArray data = result.getJSONArray("challenges");
        int i = 0;
        while (i < data.length()) {
            JSONObject item = data.getJSONObject(i++);
            int id = item.getInt("id");
            String iconUrl = item.getString("icon");
            String title = item.getString("title");
            String desc = item.getString("description");
            list.add(new ChallengeItem(id, iconUrl, title, desc));
        }

        return list;
    }

    @Nullable
    public static Integer addChallenge(ChallengeItem challenge) {
        try {
            JSONObject result = new JSONGetter(url +"insert/challenge.php?title="+ challenge.getTitle()
                    +"&description="+ challenge.getDesc()
                    +"&icon="+ challenge.getImg()).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            Boolean response = result.getBoolean("response");
            if (!response)
                throw new Exception(result.getString("message"));
            return result.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////SCORES

    public static List<ScoreItem> findScoresByChallenge(int id) {
        return findScores(id, "c_id");
    }

    public static List<ScoreItem> findScoresByUser(int id) {
        return findScores(id, "u_id");
    }

    @Nullable
    private static List<ScoreItem> findScores(int id, String getField) {
        try {
            JSONObject result = new JSONGetter(url +"/show/score.php?"+ getField +"=" + id).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return parseScores(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Integer setScore(int challengeId, int userId, int score) {
        try {
                    JSONObject result = new JSONGetter(
                            url +"update/score.php?challenge_id="+ challengeId +
                                    "&user_id="+ userId +
                                    "&score="+ score).execute().get();
            if (result == null)
                throw new ExecutionException("Unreachable server", new Error());
            return result.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static List<ScoreItem> parseScores(JSONObject result) throws Exception {
        List<ScoreItem> list = new ArrayList<>();
        JSONArray data = result.getJSONArray("scores");
        int i = 0;
        while (i < data.length()) {
            JSONObject item = data.getJSONObject(i++);
            int id = item.getInt("id");
            int challengeId = item.getInt("challenge_id");
            int userId = item.getInt("user_id");
            int score = item.getInt("score");
            ChallengeItem challenge = findChallengeById(challengeId);
            UserItem user = findUserById(userId);
            list.add(new ScoreItem(id,
                    challenge,
                    user,
                    score));
        }

        return list;
    }
}



