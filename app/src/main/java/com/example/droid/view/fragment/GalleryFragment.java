package com.example.droid.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droid.R;
import com.example.droid.databinding.FragmentGalleryBinding;


public class GalleryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context context;
    private FragmentGalleryBinding binding;

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        View view = binding.getRoot();
        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
