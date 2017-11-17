package com.xilingyuli.mypip;

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** The arguments to be used for Picture-in-Picture mode. */
    private final PictureInPictureParams.Builder mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();

    ImageView pic;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic = findViewById(R.id.pic);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rational aspectRatio = new Rational(1, 1);
                mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
                mPictureInPictureParamsBuilder.setSourceRectHint(new Rect(pic.getLeft(), pic.getTop(), pic.getRight(), pic.getBottom()));
                List<RemoteAction> actions = new ArrayList<>();
                actions.add(new RemoteAction(
                        Icon.createWithResource(MainActivity.this, R.drawable.ic_info_24dp),
                        "info",
                        "jump url",
                        PendingIntent.getActivity(MainActivity.this, 1,
                                new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.toutiao.com")), 0)));
                mPictureInPictureParamsBuilder.setActions(actions);
                enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
            }
        });
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            // Hide the controls in picture-in-picture mode.
            button.setVisibility(View.GONE);
        } else {
            // Restore the playback UI based on the playback status.
            button.setVisibility(View.VISIBLE);
        }
    }

}
