package com.example.droid.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.droid.R;
import com.example.droid.databinding.FragmentGalleryBinding;
import com.example.droid.model.user.Pretty;
import com.example.droid.service.api.IRestApiClient;
import com.example.droid.view.adapter.GalleryRecycleAdapter;
import com.example.droid.viewmodel.gallery.GalleryViewModel;

import javax.inject.Inject;


public class GalleryFragment extends BaseFragment implements GalleryViewModel.IDataChangedListener {

    private OnFragmentInteractionListener mListener;
    private Context context;
    private FragmentGalleryBinding binding;
    private GalleryViewModel galleryViewModel;

    @Inject
    IRestApiClient restApiClient;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContextBase();

        getApplicationComponent().inject(this);

        galleryViewModel = new GalleryViewModel(context, this, this.restApiClient);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getActivity().getApplicationContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        binding.setViewModel(galleryViewModel);

        setupRecycleView();

        return binding.getRoot();
    }

    private void setupRecycleView() {
        binding.progressLoadGallery.setVisibility(View.VISIBLE);
        GalleryRecycleAdapter adapter = new GalleryRecycleAdapter(context);
        binding.recycleGallery.setAdapter(adapter);
        binding.recycleGallery.setLayoutManager(new GridLayoutManager(context, 2));
    }

    @Override
    public void onDataChanged(Pretty pretty) {
        binding.progressLoadGallery.setVisibility(View.GONE);
        GalleryRecycleAdapter adapter = (GalleryRecycleAdapter) binding.recycleGallery.getAdapter();
        adapter.setPretty(pretty);
        adapter.notifyDataSetChanged();
    }


}
