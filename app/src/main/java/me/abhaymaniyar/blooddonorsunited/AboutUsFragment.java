package me.abhaymaniyar.blooddonorsunited;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abhay on 21/7/17.
 */

public class AboutUsFragment extends Fragment {
    public AboutUsFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater l = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = l.inflate(R.layout.tool_bar, null);
        TextView textView = (TextView) v.findViewById(R.id.custom_toolbar_title);
        textView.setText("About Me");
        v.findViewById(R.id.edit_image_view).setVisibility(View.GONE);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);

        TextView details = (TextView) getView().findViewById(R.id.details);
        details.setText("Hello there! \n" +
                "A little about myself, I am Abhay Maniyar and I am a Computer Science Engineer and like to solve problems using the power of technology.\n" +
                "This app is one of my ways to solve a problem and give back to the society and I hope you will help me with it by keeping this app installed on your phone. \n" +
                "Keeping this app installed will allow you to help even some random guy by a simple search for potential donors on this app.\n" +
                "And I am not integrating advertisments on this app so you don't get any useless notifications on your phone.\n" +
                "This app is like a dialer, you only use it when you or someone you know needs it.\n" +
                "I have open sourced this app, so if you want to improve this app or find any bug feel free to raise an issue on the GitHub repo linked below.\n" +
                "You can also contact me on my email by clicking below icon.");

        LinearLayout githubIcon = (LinearLayout) getView().findViewById(R.id.github_icon);
        githubIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.github.com/abhaymaniyar/Blood-Donation-App"));
                startActivity(i);
            }
        });

        ImageView email = (ImageView) githubIcon.findViewById(R.id.email_icon);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("mailto:abhaymaniyar@live.in"));
//                i.setType("plain/text");
//                i.putExtra(Intent.EXTRA_EMAIL, "");
                startActivity(i);
            }
        });
    }
}
