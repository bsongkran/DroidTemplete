package com.example.droid.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droid.R;

public class RealmDatabaseFragment extends Fragment {

    private Context context;
    private OnFragmentInteractionListener mListener;

    public RealmDatabaseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RealmDatabaseFragment newInstance(Context context) {
        RealmDatabaseFragment fragment = new RealmDatabaseFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_realm_database, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
