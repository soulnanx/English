package com.example.renan.english.ui.dialog;

import android.content.Intent;
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
import com.example.renan.english.dao.MajorityNoteDAO;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.ui.activity.MainActivity;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * Created by renan on 12/15/14.
 */
public class CreateNoteDialog extends DialogFragment implements View.OnClickListener{

        private UIHelper uiHelper;
        private View view;
        private App application;
        private MajorityNote note;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.dialog_create_note, container);
            init();
            return view;
        }

    private void init() {
        note = new MajorityNote();
        application = (App) this.getActivity().getApplication();
        uiHelper = new UIHelper();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setTitle(R.string.title_create_dialog_majority_note);
    }

    private View.OnClickListener eventOK() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiHelper.validator.validate();
            }
        };
    }

    private void saveNote() {
        note.setTitleEn(uiHelper.titleEn.getText().toString());
        note.setTitlePt(uiHelper.titlePt.getText().toString());
        MajorityNoteDAO.save(application.adapter, note);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_create_dialog_btn_ok :
                uiHelper.validator.validate();
                break;
            case R.id.note_create_dialog_btn_cancel :
                CreateNoteDialog.this.dismiss();
                break;
        }

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
                Toast.makeText(CreateNoteDialog.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
