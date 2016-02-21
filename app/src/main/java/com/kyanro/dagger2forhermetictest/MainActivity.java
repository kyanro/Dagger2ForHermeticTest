package com.kyanro.dagger2forhermetictest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyanro.dagger2forhermetictest.Network.GithubClient;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    GithubClient mGithubClient;

    @Inject
    Picasso mPicasso;

    @Bind(R.id.name_text)
    TextView mNameText;
    @Bind(R.id.profile_image)
    ImageView mProfileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((MyApp) getApplication()).mAppComponent.inject(this);

        mGithubClient.getUser("kanro")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    mNameText.setText(user.login);
                    mPicasso.load(user.avatar_url)
                            .into(mProfileImageView);
                    Log.d("mylog", "success");
                }, e -> {
                    Log.d("mylog", "error:" + e.getMessage());
                });
    }
}
