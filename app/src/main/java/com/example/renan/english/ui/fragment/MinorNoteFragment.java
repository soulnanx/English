package com.example.renan.english.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renan.english.R;

public class MinorNoteFragment extends Fragment {

    public static final int POSITION = 1;
    public static final String NAME_TAB = "Minor notes";
    private OnFragmentInteractionListener mListener;

    public static MinorNoteFragment newInstance() {
        MinorNoteFragment fragment = new MinorNoteFragment();
        return fragment;
    }

    public MinorNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_minor_notes, container, false);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
