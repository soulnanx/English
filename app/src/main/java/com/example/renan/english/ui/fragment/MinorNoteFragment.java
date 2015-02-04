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
import android.widget.ListView;

import com.example.renan.english.R;
import com.example.renan.english.adapter.NoteAdapter;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.example.renan.english.ui.activity.DrawerLayoutMain;
import com.example.renan.english.ui.dialog.CreateNoteDialog;
import com.example.renan.english.ui.dialog.CreatePhraseDialog;
import com.example.renan.english.util.FilterNotesUtil;
import com.example.renan.english.util.NotesUtil;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.parse.DeleteCallback;
import com.parse.ParseException;

public class MinorNoteFragment extends Fragment {

    public static final int POSITION = 1;
    public static final String NAME_TAB = "Minor notes";
    public static final int OK = 1;
    private static final int CANCEL = 2;
    private View view;
    private UIHelper ui;
    private App app;
    private static final int DIALOG_CREATE_NOTE = 1;
    private static final int DIALOG_CREATE_PHRASE = 2;
    private Context context;
    private ActionMode.Callback mCallback;
    private Note minorNoteClicked;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_minor_notes, container, false);

        init();
        configureActionMode();
        return view;
    }

    private void init() {
        this.context = getActivity();
        this.ui = new UIHelper();
        app = (App) getActivity().getApplication();
        setList();
        setEvents();
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
                mode.setTitle(minorNoteClicked.getTitleEn());
                getActivity().getMenuInflater().inflate(R.menu.context_menu_new, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_menu_delete:
                        showDialog();
                        break;
                    case R.id.item_menu_create:
                        createPhrase();
                        break;
                }
                mode.finish();
                setList();
                return false;
            }
        };
    }

    private void createPhrase() {
        if (NotesUtil.qtdPhrasesInNote(getActivity(), minorNoteClicked) >= 1){
            showLimitDialog();
        } else {
            openDialogCreatePhrase();
        }
    }

    private void showDialog(){
        dialog = new Dialog(getActivity(), getResources().getString(R.string.dialog_delete_phrase), getResources().getString(R.string.dialog_body));
        dialog.setOnAcceptButtonClickListener(dialogAcceptEvent());
        dialog.setOnCancelButtonClickListener(dialogCancelEvent());
        dialog.show();

    }

    private void showLimitDialog(){
        dialog = new Dialog(getActivity(), getResources().getString(R.string.dialog_limit_title) , getResources().getString(R.string.dialog_limit_body_minor));

        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getButtonCancel().setVisibility(View.GONE);

    }

    private View.OnClickListener dialogCancelEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }

    private View.OnClickListener dialogAcceptEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        };
    }

    private void deleteNote() {

        minorNoteClicked.deleteNote(getActivity(), new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                setList();
                new SnackBar(MinorNoteFragment.this.getActivity(), minorNoteClicked.getTitleEn(), "", null).show();
            }
        });
    }

    private void setEvents() {
        ui.btnCreateNote.setOnClickListener(onClickBtnCreateNote());
        ui.listViewMinorNotes.setOnItemLongClickListener(onLongItemClickList());
    }

    private View.OnClickListener onClickBtnCreateNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreateNote();
            }
        };
    }

    private AdapterView.OnItemLongClickListener onLongItemClickList() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                minorNoteClicked = (Note) parent.getAdapter().getItem(position);
                ui.toolbar.startActionMode(mCallback);
                return true;

            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_CREATE_NOTE) {
            switch (resultCode) {
                case OK:
                    setList();
                    new SnackBar(MinorNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_note), null, null).show();
                    break;
                case CANCEL:
                    new SnackBar(MinorNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_note_cancel), null, null).show();
                    break;
            }
        } else if (requestCode == DIALOG_CREATE_PHRASE) {
            switch (resultCode) {
                case OK:
                    setList();
                    new SnackBar(MinorNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_phrase), null, null).show();
                    break;
                case CANCEL:
                    new SnackBar(MinorNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_phrase_cancel), null, null).show();
                    break;
            }
        }
    }

    public void setList() {

        if (FilterNotesUtil.filterByType(app.cacheList, Note.MINOR).size() > 0) {
            view.findViewById(R.id.text_empty).setVisibility(View.GONE);
            view.findViewById(R.id.list_minor_notes).setVisibility(View.VISIBLE);

            ui.listViewMinorNotes.setAdapter(
                    new NoteAdapter(
                            MinorNoteFragment.this.getActivity(),
                            R.layout.item_note,
                            FilterNotesUtil.filterByType(app.cacheList, Note.MINOR)));

        } else {
            view.findViewById(R.id.list_minor_notes).setVisibility(View.GONE);
            view.findViewById(R.id.text_empty).setVisibility(View.VISIBLE);
        }
    }

    private void openDialogCreateNote() {
        CreateNoteDialog createNoteDialog = new CreateNoteDialog();
        createNoteDialog.setRetainInstance(true);
        createNoteDialog.setTargetFragment(this, DIALOG_CREATE_NOTE);
        Bundle b = new Bundle();
        b.putInt("type", Note.MINOR);
        createNoteDialog.setArguments(b);
        createNoteDialog.show(getFragmentManager(), "dialog");
    }

    private void openDialogCreatePhrase() {
        CreatePhraseDialog createPhraseDialog = new CreatePhraseDialog();
        createPhraseDialog.setRetainInstance(true);
        createPhraseDialog.setTargetFragment(this, DIALOG_CREATE_PHRASE);
        Bundle b = new Bundle();
        b.putSerializable("note", minorNoteClicked);
        createPhraseDialog.setArguments(b);
        createPhraseDialog.show(getFragmentManager(), "dialog");
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

    private class UIHelper{
        private ButtonFloat btnCreateNote;
        private ListView listViewMinorNotes;
        private Toolbar toolbar;

        private UIHelper() {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            listViewMinorNotes = (ListView) view.findViewById(R.id.list_minor_notes);
            btnCreateNote = (ButtonFloat) view.findViewById(R.id.create_new_note);
        }
    }

}
