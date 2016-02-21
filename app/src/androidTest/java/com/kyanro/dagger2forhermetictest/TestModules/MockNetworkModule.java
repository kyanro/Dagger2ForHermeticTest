package com.kyanro.dagger2forhermetictest.TestModules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.kyanro.dagger2forhermetictest.Network.GithubClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import org.mockito.Mockito;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ppp on 2016/02/21.
 */
@Module
public class MockNetworkModule {
    @Singleton
    @Provides
    public GithubClient provideGithubClient() {
        return Mockito.mock(GithubClient.class);
    }

    @Singleton
    @Provides
    public Picasso providePicasso(Context context) {
        Bitmap bmp = createDummyBitmap();
        return new Picasso.Builder(context).addRequestHandler(new RequestHandler() {
            @Override
            public boolean canHandleRequest(Request data) {
                return true;
            }

            @Override
            public Result load(Request request, int networkPolicy) throws IOException {
                return new Result(bmp, LoadedFrom.MEMORY);
            }
        }).build();
    }

    private Bitmap createDummyBitmap() {
        Bitmap bmp = Bitmap.createBitmap(10, 10, Config.RGB_565);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, 10, 10, paint);
        return bmp;
    }

}
