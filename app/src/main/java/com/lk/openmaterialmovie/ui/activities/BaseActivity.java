/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.activities;

import android.app.Activity;
import android.app.KeyguardManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.navigator.Navigator;
import com.lk.openmaterialmovie.ui.preloader.Progress;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class BaseActivity extends AppCompatActivity {

    @Getter
    @Setter
    protected Progress progress;

    @Inject
    public Navigator navigator;

    public Toolbar toolbar;

    @Getter
    @Setter
    private @IdRes
    int fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.setActivity(this);
    }

    public void progressShow() {
        progress.show();
    }

    public void progressHide() {
        progress.hide();
    }

    public void showBack() {
        // noinspection ConstantConditions
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void hideBack() {
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    // TODO: 2019-04-29 Add own safe abstract layer
    public void setTitleText(int stringId) {
        getSupportActionBar().setTitle(stringId);
    }

    // TODO: 2019-04-29 Add own safe abstract layer
    public void setTitleText(String string) {
        getSupportActionBar().setTitle(string);
    }

    public static void riseAndShine(Activity activity) {
        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(KEYGUARD_SERVICE);
        assert keyguardManager != null;
        //noinspection deprecation,deprecation
        final KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("Unlock!");
        keyguardLock.disableKeyguard();

        activity.getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);
        //noinspection deprecation
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, "Wakeup!");
        wakeLock.acquire();
        wakeLock.release();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
