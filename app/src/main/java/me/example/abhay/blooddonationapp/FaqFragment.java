package me.example.abhay.blooddonationapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 20/7/17.
 */

public class FaqFragment extends Fragment {
    public FaqFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.faq_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Faq> faqList = new ArrayList<Faq>();
        faqList.add(new Faq("Who can donate blood?", "1. A person of 17 years of age and older.\n2. A person in good physical health\n" +
                "3. A person who weighs over 50 kilograms."));
        faqList.add(new Faq("Is it safe?", "Blood donation is 100% safe, and does not transmit HIV or other infectious diseases. The donation kit is used only once and disposed of right after its usage."));
        faqList.add(new Faq("Is it painful?", "The donor will feel a small pinch for a quick second and then it will immediately go away."));
        faqList.add(new Faq("How much blood is retrived?", "Between 350 and 450 ml of blood is retrieved from the blood donation process.The human body contains roughly 5 to 6 liters of blood, and is capable of recovering the donated blood within a month."));
        faqList.add(new Faq("Does it cause any weakness?", "Most donors do not complain of any side effects when they donate blood. However, here are some steps to consider after your blood donation:\n" +
                "1. It is recommended to have some juice and rest for 10-15 minutes before resuming daily activities.\n" +
                "2. In case the donor is a smoker, it is better to refrain from smoking for at least an hour.\n" +
                "3. The donor must refrain from excessive sports and activities for the next 24 hours.\n" +
                "4 Extra amounts of water and fluids throughout the day will make for a quick recovery."));
        faqList.add(new Faq("How many times one can donate in a year?", "1. The plasma from the extracted blood will be replaced within 24 hours. Red cells need about 4 to 6 weeks.\n" +
                "2. It is preferable for a person to donate Up to 4 times a year, however with more than 56 days in between.\n" +
                "3. When donating platelets, donation can be done every 7 days, with no more than 24 times a year."));
        faqList.add(new Faq("How the blood is distributed to the patients?", "1. Red blood cells could be used in accidents involving trauma or anemia but also for patients in need of surgeries.\n" +
                "2. Plasma could also be used for patients with clotting problems, burns, or liver disease.\n" +
                "3. Platelets could often be used to treat cancer patients or patients in need of transplants."));
        faqList.add(new Faq("How long the donated blood is valid?", "1. Red blood cells may be stored under refrigeration for a maximum of 42 days or frozen for up to 10 years.\n" +
                "2. Platelets may be kept at room temperature for up to 5 days only.\n" +
                "3. Plasma can be frozen for a period of one year.\n" +
                "4. Cryoprecipitate AHF may be stored in a frozen state for one year.\n" +
                "5. White blood cells should be transported to the patient within 24 hours.\n"));

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.faq_recyclerview);
        FaqAdapter faqAdapter = new FaqAdapter(faqList);

        recyclerView.setAdapter(faqAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);
    }
}
