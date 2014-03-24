package com.twitter.android.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class PostTweetService extends IntentService {
    private static final String TAG = "PostTweetService";

    public static final String EXTRA_TWEET_MSG = "EXTRA_TWEET_MSG";
    public static final String EXTRA_TWEET_POST_STATUS = "EXTRA_TWEET_POST_STATUS";
    public static final String ACTION_TWEET_POST_STATUS
            = "com.twitter.android.yamba.ACTION_TWEET_POST_STATUS";
    public static final String PERM_TWEET_POST_STATUS
            = "com.twitter.android.yamba.permission.TWEET_POST_STATUS";

    YambaClient mYambaClient;

    public PostTweetService() {
        // Identify the worker thread for debugging purposes
        super(TAG);

        // Initialize our YambaClient instance
        mYambaClient = new YambaClient("student", "password");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(EXTRA_TWEET_MSG);
        int result = R.string.post_tweet_fail;
        if (!TextUtils.isEmpty(msg)) {
            try {
                mYambaClient.postStatus(msg);
                result = R.string.post_tweet_success;
            } catch (YambaClientException e) {
                Log.e(TAG, "Failed to post status", e);
            }
        }
        // Notify observers of post attempt status
        Intent broadcast = new Intent(ACTION_TWEET_POST_STATUS);
        broadcast.putExtra(EXTRA_TWEET_POST_STATUS, result);
        sendBroadcast(broadcast, PERM_TWEET_POST_STATUS);
    }
}
