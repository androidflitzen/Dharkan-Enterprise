package com.dharkanenquiry.Fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dharkanenquiry.vasudhaenquiry.R;

public class Enquiry_Fragment extends Fragment {

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enquiry_fragment, null);
        getActivity().setTitle("Enquiry List");

        context = getContext();



        return view;
    }
}
