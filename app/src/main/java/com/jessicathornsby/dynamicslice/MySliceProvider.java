package com.jessicathornsby.dynamicslice;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.net.Uri;

import androidx.slice.builders.ListBuilder;
import androidx.slice.Slice;
import androidx.slice.builders.SliceAction;
import androidx.slice.SliceProvider;

import androidx.core.graphics.drawable.IconCompat;

import static com.jessicathornsby.dynamicslice.MyBroadcastReceiver.ACTION_CHANGE_COUNT;
import static com.jessicathornsby.dynamicslice.MyBroadcastReceiver.EXTRA_COUNT_VALUE;
import static com.jessicathornsby.dynamicslice.MainActivity.getClickString;
import static com.jessicathornsby.dynamicslice.MainActivity.clickCount;

public class MySliceProvider extends SliceProvider {
    private Context context;
    private static int count = 0;

    @Override
    public boolean onCreateSliceProvider() {
        context = getContext();
        return true;
    }

    @Override
    public Slice onBindSlice(Uri sliceUri) {
        final String path = sliceUri.getPath();
        switch (path) {
            case "/clickCount":
                return createClickSlice(sliceUri);
        }
        return null;
    }

    private Slice createClickSlice(Uri sliceUri) {
        SliceAction clickUp = new SliceAction(getChangeCountIntent(clickCount + 1),
                IconCompat.createWithResource(context, R.drawable.ic_count_up).toIcon(),
                "Increase count");
        SliceAction clickDown = new SliceAction(getChangeCountIntent(clickCount - 1),
                IconCompat.createWithResource(context, R.drawable.ic_count_down).toIcon(),
                "Decrease count");

        ListBuilder listBuilder = new ListBuilder(context, sliceUri);
        ListBuilder.RowBuilder clickRow = new ListBuilder.RowBuilder(listBuilder);

        clickRow.setTitle(getClickString(context));

        clickRow.addEndItem(clickDown);
        clickRow.addEndItem(clickUp);
        listBuilder.addRow(clickRow);
        return listBuilder.build();
    }

    private PendingIntent getChangeCountIntent(int value) {
        Intent intent = new Intent(ACTION_CHANGE_COUNT);
        intent.setClass(context, MyBroadcastReceiver.class);
        intent.putExtra(EXTRA_COUNT_VALUE, value);
        return PendingIntent.getBroadcast(getContext(), count++, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static Uri getUri(Context context, String path) {
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(context.getPackageName())
                .appendPath(path)
                .build();
    }
}