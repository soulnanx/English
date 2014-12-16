package com.example.renan.english.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.english.R;
import com.example.renan.english.ui.dialog.CreateNoteDialog;

import java.util.List;


public class MajorityNoteFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "Majority notes";
    private View view;
    private UIHelper ui;

    public static MajorityNoteFragment newInstance() {
        MajorityNoteFragment fragment = new MajorityNoteFragment();
        return fragment;
    }

    public MajorityNoteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_majority_notes, container, false);

        init();
        return view;
    }

    private void init() {
        ui = new UIHelper();

        setEvents();
    }

    private void setEvents() {
        ui.btnCreateNote.setOnClickListener(onClickBtnCreateNote());
    }

    private View.OnClickListener onClickBtnCreateNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreateNote();
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void openDialogCreateNote() {
        CreateNoteDialog createNoteDialog = new CreateNoteDialog();
        createNoteDialog.setRetainInstance(true);
        createNoteDialog.show(getFragmentManager(), "dialog");
    }

    private class UIHelper{
        private View btnCreateNote;
        private ListView lvNotes;

        private UIHelper() {
            btnCreateNote = (View) view.findViewById(R.id.create_new_note);
        }
    }



}
