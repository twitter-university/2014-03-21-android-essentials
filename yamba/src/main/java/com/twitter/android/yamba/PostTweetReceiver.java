package com.twitter.android.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PostTweetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int result = intent.getIntExtra(PostTweetService.EXTRA_TWEET_POST_STATUS, R.string.post_tweet_fail);
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
