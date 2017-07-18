package com.example.abhay.blooddonationapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 18/7/17.
 */

public class FaqActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);

        List<Faq> faqList = new ArrayList<Faq>();
        faqList.add(new Faq("Who can donate blood?", "> A person of 17 years of age and older.\n> A person in good physical health\n" +
                "> A person who weighs over 50 kilograms."));
        faqList.add(new Faq("Is it safe?", "Blood donation is 100% safe, and does not transmit HIV or other infectious diseases. The donation kit is used only once and disposed of right after its usage."));
        faqList.add(new Faq("Is it painful?", "The donor will feel a small pinch for a quick second and then it will immediately go away."));
        faqList.add(new Faq("How much blood is retrived?", "Between 350 and 450 ml of blood is retrieved from the blood donation process.The human body contains roughly 5 to 6 liters of blood, and is capable of recovering the donated blood within a month."));
        faqList.add(new Faq("Does it cause any weakness?", "Most donors do not complain of any side effects when they donate blood. However, here are some steps to consider after your blood donation:\n" +
                "> It is recommended to have some juice and rest for 10-15 minutes before resuming daily activities.\n" +
                "> In case the donor is a smoker, it is better to refrain from smoking for at least an hour.\n" +
                "> The donor must refrain from excessive sports and activities for the next 24 hours.\n" +
                "> Extra amounts of water and fluids throughout the day will make for a quick recovery."));
        faqList.add(new Faq("How many times one can donate in a year?", "> The plasma from the extracted blood will be replaced within 24 hours. Red cells need about 4 to 6 weeks.\n" +
                "> It is preferable for a person to donate Up to 4 times a year, however with more than 56 days in between.\n" +
                "> When donating platelets, donation can be done every 7 days, with no more than 24 times a year."));
        faqList.add(new Faq("How the blood is distributed to the patients?", "> Red blood cells could be used in accidents involving trauma or anemia but also for patients in need of surgeries.\n" +
                "> Plasma could also be used for patients with clotting problems, burns, or liver disease.\n" +
                "> Platelets could often be used to treat cancer patients or patients in need of transplants."));
        faqList.add(new Faq("How long the donated blood is valid?", "> Red blood cells may be stored under refrigeration for a maximum of 42 days or frozen for up to 10 years.\n" +
                "> Platelets may be kept at room temperature for up to 5 days only.\n" +
                "> Plasma can be frozen for a period of one year.\n" +
                "> Cryoprecipitate AHF may be stored in a frozen state for one year.\n" +
                "> White blood cells should be transported to the patient within 24 hours.\n"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.faq_recyclerview);
        FaqAdapter faqAdapter = new FaqAdapter(faqList);

        recyclerView.setAdapter(faqAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);
    }
}
