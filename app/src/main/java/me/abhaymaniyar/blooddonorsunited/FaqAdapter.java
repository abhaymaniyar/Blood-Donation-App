package me.abhaymaniyar.blooddonorsunited;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhay on 18/7/17.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private List<Faq> faqList;

    public FaqAdapter(List<Faq> faqList) {
        super();
        this.faqList = faqList;
    }

    @Override
    public FaqAdapter.FaqViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_row, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaqAdapter.FaqViewHolder holder, int position) {
        Faq faq = faqList.get(position);
        holder.faqHeading.setText(faq.getHeading());
        holder.faqDetails.setText(faq.getDetails());
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView faqHeading, faqDetails;
        public FaqViewHolder(View itemView) {
            super(itemView);
            faqHeading = (TextView)itemView.findViewById(R.id.faq_heading);
            faqDetails = (TextView)itemView.findViewById(R.id.faq_details);
        }
    }
}
