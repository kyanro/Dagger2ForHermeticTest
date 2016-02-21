package com.kyanro.dagger2forhermetictest;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kyanro.dagger2forhermetictest.Models.User;
import com.kyanro.dagger2forhermetictest.Modules.AppModule;
import com.kyanro.dagger2forhermetictest.MyApp.AppComponent;
import com.kyanro.dagger2forhermetictest.Network.GithubClient;
import com.kyanro.dagger2forhermetictest.TestModules.MockNetworkModule;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;

/**
 * Created by ppp on 2016/02/21.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Singleton
    @Component(modules = {MockNetworkModule.class, AppModule.class})
    interface TestComponent extends AppComponent {
        void inject(MainActivityTest testClass);
    }

    @Inject
    GithubClient githubClient;

    @Before
    public void setUp() throws Exception {
        MyApp app = (MyApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
        TestComponent testComponent = DaggerMainActivityTest_TestComponent.builder()
                .appModule(new AppModule(app))
                .build();
        testComponent.inject(this);
        app.mAppComponent = testComponent;
    }

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void mockTest() {
        User mockUser = new User();
        mockUser.login = "mock kyanro";

        Mockito.when(githubClient.getUser("kanro")).thenReturn(Observable.just(mockUser));

        User user = githubClient.getUser("kanro").toBlocking().single();

        Assert.assertEquals(user.login, mockUser.login);
    }
}