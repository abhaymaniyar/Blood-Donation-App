package me.abhaymaniyar.blooddonorsunited;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

/**
 * Created by abhay on 21/8/17.
 */

public class IntroActivity extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addSlide(AppIntro2Fragment.newInstance("Blood", "description is very long here", R.drawable.ss, Color.parseColor("#ffffff")));
//        addSlide(AppIntro2Fragment.newInstance("Blood", "description is very long here", R.drawable.if_love_37578, Color.parseColor("#ffffff")));
        addSlide(IntroFragment.newInstance(R.layout.intro1));
        addSlide(IntroFragment.newInstance(R.layout.intro2));
        addSlide(IntroFragment.newInstance(R.layout.intro3));
//        setDepthAnimation();
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
