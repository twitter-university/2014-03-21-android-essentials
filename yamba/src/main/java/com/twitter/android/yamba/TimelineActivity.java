package com.twitter.android.yamba;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;


public class TimelineActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int TIMELINE_LOADER = 42;
    private static final String[] FROM = {
      YambaContract.Timeline.Columns.HANDLE,
      YambaContract.Timeline.Columns.TIMESTAMP,
      YambaContract.Timeline.Columns.TWEET,
    };
    private static final int[] TO = {
      R.id.text_tweet_user,
      R.id.text_tweet_date,
      R.id.text_tweet_msg,
    };
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mAdapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0
        );
        mAdapter.setViewBinder( new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int id = view.getId();
                switch (id) {
                    case R.id.text_tweet_date:
                        long timestamp = cursor.getLong(columnIndex);
                        CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
                        ((TextView) view).setText(relTime);
                        return true;
                    default:
                        return false;
                }
            }
        });
        ListView list = (ListView) findViewById(R.id.list_timeline);
        list.setAdapter(mAdapter);
        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getApplicationContext(),
                YambaContract.Timeline.URI,
                null,
                null,
                null,
                YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_tweet) {
            // Display the TweetActivity
            Intent intent = new Intent(this, TweetActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
