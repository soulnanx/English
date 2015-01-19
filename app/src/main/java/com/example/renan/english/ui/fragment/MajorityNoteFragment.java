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
import android.widget.TextView;

import com.example.renan.english.R;
import com.example.renan.english.adapter.NoteAdapter;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.ui.dialog.CreateNoteDialog;
import com.example.renan.english.ui.dialog.CreatePhraseDialog;
import com.example.renan.english.util.FilterNotesUtil;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.parse.DeleteCallback;
import com.parse.ParseException;

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
    private Note majorityNoteClicked;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_majority_notes, container, false);
        init();
        configureActionMode();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void init() {
        this.context = getActivity();
        ui = new UIHelper();
        app = (App) getActivity().getApplication();
        setList();
        setEvents();
    }

    public void setList() {

        if (FilterNotesUtil.filterByType(app.cacheList, Note.MAJORITY).size() > 0) {
            view.findViewById(R.id.text_empty).setVisibility(View.GONE);
            view.findViewById(R.id.list_majority_notes).setVisibility(View.VISIBLE);

            ui.listViewMajorityNotes.setAdapter(
                    new NoteAdapter(
                            MajorityNoteFragment.this.getActivity(),
                            R.layout.item_note,
                            FilterNotesUtil.filterByType(app.cacheList, Note.MAJORITY)));

        } else {
            view.findViewById(R.id.list_majority_notes).setVisibility(View.GONE);
            view.findViewById(R.id.text_empty).setVisibility(View.VISIBLE);
        }
    }

    private AdapterView.OnItemLongClickListener onLongItemClickList() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                majorityNoteClicked = (Note) parent.getAdapter().getItem(position);
                ui.toolbar.startActionMode(mCallback);
                return true;

            }
        };
    }

    private AdapterView.OnItemClickListener onItemClickList() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.phrases);

                if (viewGroup.getVisibility() == View.GONE) {
                    viewGroup.setVisibility(View.VISIBLE);
                    addAll(((App)getActivity().getApplication()).cacheList.get((Note) parent.getAdapter().getItem(position)), viewGroup);
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
                mode.setTitle(majorityNoteClicked.getTitleEn());
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
        openDialogCreatePhrase();
    }

    private void deleteNote() {

        majorityNoteClicked.deleteNote(getActivity(), new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                setList();
                new SnackBar(MajorityNoteFragment.this.getActivity(), majorityNoteClicked.getTitleEn(), "", null).show();
            }
        });
    }

    private void addAll(List<Phrase> phrases, ViewGroup viewGroup) {
        viewGroup.removeAllViews();

        for (Phrase phrase : phrases) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup lnPhrase = (ViewGroup) inflater.inflate(R.layout.item_phrase, null);
            TextView text = (TextView) lnPhrase.findViewById(R.id.text_phrase);
            text.setText(phrase.getTitle());
            viewGroup.addView(lnPhrase);
        }
    }

    private void showDialog(){
        dialog = new Dialog(getActivity(), getResources().getString(R.string.dialog_delete_phrase), getResources().getString(R.string.dialog_body));
        dialog.setOnAcceptButtonClickListener(dialogAcceptEvent());
        dialog.setOnCancelButtonClickListener(dialogCancelEvent());
//        dialog.getButtonAccept().setText(getResources().getString(R.string.dialog_delete_button));
        dialog.show();

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

    private void setEvents() {
        ui.btnCreateNote.setOnClickListener(onClickBtnCreateNote());
        ui.listViewMajorityNotes.setOnItemClickListener(onItemClickList());
        ui.listViewMajorityNotes.setOnItemLongClickListener(onLongItemClickList());
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

        if (requestCode == DIALOG_CREATE_NOTE) {
            switch (resultCode) {
                case OK:
                    setList();
                    new SnackBar(MajorityNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_note), null, null).show();
                    break;
                case CANCEL:
                    new SnackBar(MajorityNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_note_cancel), null, null).show();
                    break;
            }
        } else if (requestCode == DIALOG_CREATE_PHRASE) {
            switch (resultCode) {
                case OK:
                    setList();
                    new SnackBar(MajorityNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_phrase), null, null).show();
                    break;
                case CANCEL:
                    new SnackBar(MajorityNoteFragment.this.getActivity(), getResources().getString(R.string.msg_create_phrase_cancel), null, null).show();
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
        Bundle b = new Bundle();
        b.putInt("type", Note.MAJORITY);
        createNoteDialog.setArguments(b);
        createNoteDialog.show(getFragmentManager(), "dialog");
    }

    private void openDialogCreatePhrase() {
        CreatePhraseDialog createPhraseDialog = new CreatePhraseDialog();
        createPhraseDialog.setRetainInstance(true);
        createPhraseDialog.setTargetFragment(this, DIALOG_CREATE_PHRASE);
        Bundle b = new Bundle();
        b.putSerializable("note", majorityNoteClicked);
        createPhraseDialog.setArguments(b);
        createPhraseDialog.show(getFragmentManager(), "dialog");
    }

    private class UIHelper {
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
