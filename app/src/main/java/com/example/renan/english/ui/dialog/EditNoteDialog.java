package com.example.renan.english.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.english.R;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by renan on 12/15/14.
 */
public class EditNoteDialog extends DialogFragment {

    public static final int OK = 1;
    private static final int CANCEL = 2;
    private UIHelper uiHelper;
    private View view;
    private App app;
    private Note note;
    private int TYPE_NOTE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_note, container);
        init();
        return view;
    }

    private void init() {
        note = new Note();
        app = (App) this.getActivity().getApplication();
        uiHelper = new UIHelper();
        this.TYPE_NOTE = getArguments().getInt("type");
        setEvents();
        getDialog().setCanceledOnTouchOutside(false);
        setTitle();

    }

    private void setTitle() {
        switch (this.TYPE_NOTE){
            case Note.MAJORITY:
                getDialog().setTitle(R.string.title_create_dialog_majority_note);
                break;
            case Note.MINOR:
                getDialog().setTitle(R.string.title_create_dialog_minor_note);
                break;
        }
    }

    private void setEvents() {
        uiHelper.btnOK.setOnClickListener(onClickBtnOk());
        uiHelper.btnCancel.setOnClickListener(onClickBtnCancel());
    }

    private View.OnClickListener onClickBtnOk() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiHelper.validator.validate();
            }
        };
    }

    private View.OnClickListener onClickBtnCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNoteDialog.this.getTargetFragment().onActivityResult(getTargetRequestCode(), CANCEL, getActivity().getIntent());
                EditNoteDialog.this.dismiss();
            }
        };
    }

    private void saveNote() {
        note.setTitleEn(uiHelper.titleEn.getText().toString());
        note.setTitlePt(uiHelper.titlePt.getText().toString());
        note.setType(this.TYPE_NOTE);
        note.saveNote(getActivity(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                EditNoteDialog.this.getTargetFragment().onActivityResult(getTargetRequestCode(), OK, getActivity().getIntent());
                EditNoteDialog.this.dismiss();
            }
        });

    }


    private class UIHelper implements Validator.ValidationListener {
        final Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        EditText titleEn;

        @Required(order = 2, message = "it's mandatory =(")
        EditText titlePt;


        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.titleEn = (EditText) view.findViewById(R.id.edt_note_title_en);
            this.titlePt = (EditText) view.findViewById(R.id.edt_note_title_pt);
            this.btnOK = (LinearLayout) view.findViewById(R.id.note_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.note_create_dialog_btn_cancel);

            validator = new Validator(this);
            validator.setValidationListener(this);
        }

        public void onValidationSucceeded() {
            saveNote();
        }

        public void onValidationFailed(View failedView, Rule<?> failedRule) {
            String message = failedRule.getFailureMessage();

            if (failedView instanceof EditText) {
                failedView.requestFocus();
                ((EditText) failedView).setError(message);
            } else if (failedView instanceof TextView) {
                failedView.requestFocus();
                ((TextView) failedView).setError(message);
            } else {
                Toast.makeText(EditNoteDialog.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
