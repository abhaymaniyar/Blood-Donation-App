package me.abhaymaniyar.blooddonorsunited;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhay on 20/7/17.
 */

public class MainFragment extends Fragment {
    ActionBar customToobar;
    View view1;
    ImageView editImageView;
    ImageView finishEditingImageView;

    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("registrationStatus", Context.MODE_PRIVATE);
        customToobar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        view1 = customToobar.getCustomView();
        editImageView = view1.findViewById(R.id.edit_image_view);
        finishEditingImageView = (ImageView) view1.findViewById(R.id.finish_editing_image_view);
        editImageView.setVisibility(View.GONE);
        TextView t = view1.findViewById(R.id.custom_toolbar_title);
        t.setText("Search Donors");
        final Spinner spinner = (Spinner) getView().findViewById(R.id.search_spinner);
        final List<String> bloodGroups = new ArrayList<String>();
        bloodGroups.add("Select");
        bloodGroups.add("O+");
        bloodGroups.add("O-");
        bloodGroups.add("A+");
        bloodGroups.add("A-");
        bloodGroups.add("B+");
        bloodGroups.add("B-");
        bloodGroups.add("AB-");
        bloodGroups.add("AB+");
        //        set Adapter to Spinner items
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bloodGroups);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
        spinner.setPrompt(bloodGroups.get(0));
//        select a bloodGroup and show it on spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                spinner.setPrompt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) getView().findViewById(R.id.search_city);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
//                String[] strArray = description.split(",");
                autocompleteView.setText(description);
//                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

//        onClick behavior of the search button
//        Button searchDonorBtn = (Button) getView().findViewById(R.id.search_button);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.button);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getPrompt().equals("Select")) {
                    builder.setTitle("");
                    builder.setMessage("Please select a blood group.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    final Bundle bundle = new Bundle();
                    EditText cityEditText = (EditText) getView().findViewById(R.id.search_city);
                    String city = cityEditText.getText().toString();
                    Spinner spnr = (Spinner) getView().findViewById(R.id.search_spinner);
                    String bGroup = spnr.getPrompt().toString();
                    Fragment fragment = new ResultsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    bundle.putString("city", city);
                    bundle.putString("bGroup", bGroup);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_frame, fragment, "Other Fragment").commit();
                }
            }
        });
    }


    public boolean checkInternetConnectivity() {
        try {
            InetAddress inetAddress = InetAddress.getByName("google.com");
            return !inetAddress.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
