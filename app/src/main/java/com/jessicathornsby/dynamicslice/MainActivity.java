package com.jessicathornsby.dynamicslice;

import android.os.Bundle;
import android.content.Context;
import android.widget.TextView;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int clickCount = 0;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.click_count);

        findViewById(R.id.increase).setOnClickListener(this);
        findViewById(R.id.decrease).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.increase:
                updateClickCount(getApplicationContext(), clickCount + 1);
                break;
            case R.id.decrease:
                updateClickCount(getApplicationContext(), clickCount - 1);
                break;
        }
        mTextView.setText(getClickString(getApplicationContext()));
    }

    public static String getClickString(@NonNull Context context) {
        return context.getString(R.string.click_string, clickCount);
    }

    public static void updateClickCount(Context context, int newValue) {

        if (newValue != clickCount) {
            clickCount = newValue;
            Uri uri = MySliceProvider.getUri(context, "clickCount");
            context.getContentResolver().notifyChange(uri, null);
        }
    }
}
