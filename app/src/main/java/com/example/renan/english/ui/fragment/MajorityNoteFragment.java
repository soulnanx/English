package com.example.renan.english.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.english.R;
import com.example.renan.english.adapter.MajorityNoteAdapter;
import com.example.renan.english.app.App;
import com.example.renan.english.dao.MajorityNoteDAO;
import com.example.renan.english.dao.PhraseDAO;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.entity.PhraseOld;
import com.example.renan.english.ui.activity.DrawerLayoutMain;
import com.example.renan.english.ui.dialog.CreateNoteDialog;
import com.example.renan.english.ui.dialog.CreatePhraseDialog;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.List;


public class MajorityNoteFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "Majority notes";
    public static final int OK = 1;
    private static final int CANCEL = 2;
    private View view;
    private UIHelper ui;
    private App app;
    private static final int DIALOG_CREATE_NOTE = 1;
    private static final int DIALOG_CREATE_PHRASE = 2;
    private Context context;
    private ActionMode.Callback mCallback;
    private MajorityNote majorityNoteClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_majority_notes, container, false);

        init();

        showAndHideList();
        configureActionMode();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void showAndHideList(){
        if (MajorityNoteDAO.findAll(app.adapter).size() > 0){
            view.findViewById(R.id.text_empty).setVisibility(View.GONE);
            view.findViewById(R.id.list_majority_notes).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.list_majority_notes).setVisibility(View.GONE);
            view.findViewById(R.id.text_empty).setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        this.context = getActivity();
        ui = new UIHelper();
        app = (App) getActivity().getApplication();
        setList();
        setEvents();
    }

    public void setList() {
        ui.listViewMajorityNotes.setAdapter(
                new MajorityNoteAdapter(
                        MajorityNoteFragment.this.getActivity(),
                        R.layout.item_majority_note,
                        MajorityNoteDAO.findAll(app.adapter)));
    }

    private AdapterView.OnItemLongClickListener onLongItemClickList() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                majorityNoteClicked = (MajorityNote)parent.getAdapter().getItem(position);
                ui.toolbar.startActionMode(mCallback);
                return true;

            }
        } ;
    }
//    ((DrawerLayoutMain)getActivity()).openDrawer();

    private AdapterView.OnItemClickListener onItemClickList() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.phrases);

                if (viewGroup.getVisibility() == View.GONE){
                    viewGroup.setVisibility(View.VISIBLE);

                    addAll(
                            ((MajorityNote)parent.getAdapter().getItem(position)).getId(),
                            viewGroup);
                } else {
                    viewGroup.setVisibility(View.GONE);
                }

            }
        };
    }

    private void configureActionMode() {


        mCallback = new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("1 selected");
                getActivity().getMenuInflater().inflate(R.menu.context_menu_new, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_menu_delete:
                        deleteNote();
                        mode.finish();
                        showAndHideList();
                        break;
                    case R.id.item_menu_create:
                        createPhrase();
                        mode.finish();
                        showAndHideList();
                        break;
                }
                return false;
            }
        };
    }

    private void createPhrase() {
        openDialogCreatePhrase();
    }

    private void deleteNote() {
        MajorityNoteDAO.delete(app.adapter, majorityNoteClicked.getId());
        setList();
    }

    private void addAll(long id, ViewGroup viewGroup){
        List<PhraseOld> phraseOlds = PhraseDAO.findAll(app.adapter, id);
        viewGroup.removeAllViews();

        for (PhraseOld phraseOld : phraseOlds){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup lnPhrase = (ViewGroup) inflater.inflate( R.layout.item_phrase, null );
            TextView text = (TextView)lnPhrase.findViewById(R.id.text_phrase);
            text.setText(phraseOld.getTitle());
            viewGroup.addView(lnPhrase);
        }
    }

    private void setEvents() {
//        ui.btnMenu.setOnClickListener(onClickBtnMenu());
        ui.btnCreateNote.setOnClickListener(onClickBtnCreateNote());
        ui.listViewMajorityNotes.setOnItemClickListener(onItemClickList());
        ui.listViewMajorityNotes.setOnItemLongClickListener(onLongItemClickList());
    }

    private View.OnClickListener onClickBtnMenu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayoutMain)getActivity()).openDrawer();
            }
        };
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == DIALOG_CREATE_NOTE) {
            switch (resultCode) {
                case OK :
                    setList();
                    showAndHideList();
                    Toast.makeText(MajorityNoteFragment.this.getActivity(), R.string.msg_create_note, Toast.LENGTH_SHORT).show();
                    break;
                case CANCEL :
                    Toast.makeText(MajorityNoteFragment.this.getActivity(), R.string.msg_create_note_cancel, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == DIALOG_CREATE_PHRASE){
            switch (resultCode) {
                case OK :
                    setList();
                    showAndHideList();
                    Toast.makeText(MajorityNoteFragment.this.getActivity(), R.string.msg_create_phrase, Toast.LENGTH_SHORT).show();
                    break;
                case CANCEL :
                    Toast.makeText(MajorityNoteFragment.this.getActivity(), R.string.msg_create_phrase_cancel, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void openDialogCreateNote() {
        CreateNoteDialog createNoteDialog = new CreateNoteDialog();
        createNoteDialog.setRetainInstance(true);
        createNoteDialog.setTargetFragment(this, DIALOG_CREATE_NOTE);
        createNoteDialog.show(getFragmentManager(), "dialog");
    }

    private void openDialogCreatePhrase() {
        CreatePhraseDialog createPhraseDialog = new CreatePhraseDialog();
        createPhraseDialog.setRetainInstance(true);
        createPhraseDialog.setTargetFragment(this, DIALOG_CREATE_NOTE);
        Bundle b = new Bundle();
        b.putLong("idMajorityNote", majorityNoteClicked.getId());
        b.putString("titleEn", majorityNoteClicked.getTitleEn());
        createPhraseDialog.setArguments(b);
        createPhraseDialog.show(getFragmentManager(), "dialog");
    }

    private class UIHelper{
        private ButtonFloat btnCreateNote;
        private ListView listViewMajorityNotes;
        private Toolbar toolbar;

        private UIHelper() {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            btnCreateNote = (ButtonFloat) view.findViewById(R.id.create_new_note);
            listViewMajorityNotes = (ListView) view.findViewById(R.id.list_majority_notes);
        }
    }
}
