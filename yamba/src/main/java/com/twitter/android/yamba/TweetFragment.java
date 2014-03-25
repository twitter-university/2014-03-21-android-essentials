package com.twitter.android.yamba;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TweetFragment extends Fragment
        implements View.OnClickListener {
    private static final String TAG = "TweetFragment";

    EditText mEditMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_tweet, container, false);
        mEditMsg = (EditText) top.findViewById(R.id.edit_msg);
        Button buttonTweet = (Button) top.findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);
        return top;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_tweet:
                if (BuildConfig.DEBUG) Log.d(TAG, "Tweet button clicked");

                String msg = mEditMsg.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    Log.d(TAG, "User entered: " + msg);
                    mEditMsg.setText("");
                    // Start the service to post the tweet
                    Intent intent = new Intent(getActivity(), PostTweetService.class);
                    intent.putExtra(PostTweetService.EXTRA_TWEET_MSG, msg);
                    getActivity().startService(intent);
                    notifyOnTweetPostedListener();
                }
                break;
            default:
                // Unknown button clicked?
                Log.w(TAG, "What was that?");
        }
    }

    OnTweetPostedListener mOnTweetPostedListener;

    public void setOnTweetPostedListener(OnTweetPostedListener listener) {
        mOnTweetPostedListener = listener;
    }

    public void notifyOnTweetPostedListener() {
        if (null != mOnTweetPostedListener) {
            mOnTweetPostedListener.onTweetPosted();
        }
    }

    public interface OnTweetPostedListener {
        void onTweetPosted();
    }

}
