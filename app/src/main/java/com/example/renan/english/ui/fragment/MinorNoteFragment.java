package com.example.renan.english.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.renan.english.R;
import com.example.renan.english.ui.activity.DrawerLayoutMain;
import com.gc.materialdesign.views.ButtonFloat;

public class MinorNoteFragment extends Fragment {

    public static final int POSITION = 1;
    public static final String NAME_TAB = "Minor notes";
    private View view;
    private UIHelper ui;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_minor_notes, container, false);

        init();
        return view;
    }

    private void init() {
        this.ui = new UIHelper();
        setEvents();
    }

    private void setEvents() {
//        ui.btnMenu.setOnClickListener(onClickBtnMenu());
    }

    private View.OnClickListener onClickBtnMenu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayoutMain)getActivity()).openDrawer();
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private class UIHelper{
        private ButtonFloat btnCreateNote;
        private ListView listViewMinorNotes;
        private Toolbar toolbar;

        private UIHelper() {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            btnCreateNote = (ButtonFloat) view.findViewById(R.id.create_new_note);
        }
    }

}
