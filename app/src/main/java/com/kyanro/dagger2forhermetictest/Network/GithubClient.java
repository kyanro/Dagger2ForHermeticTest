package com.kyanro.dagger2forhermetictest.Network;

import com.kyanro.dagger2forhermetictest.Models.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ppp on 2016/02/21.
 */
public interface GithubClient {
    @GET("/users/{user_name}")
    Observable<User> getUser(@Path("user_name") String user_name);
}
