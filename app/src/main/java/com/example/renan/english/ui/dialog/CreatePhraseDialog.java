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
import com.example.renan.english.dao.MajorityNoteDAO;
import com.example.renan.english.dao.PhraseDAO;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.entity.Phrase;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * Created by renan on 12/15/14.
 */
public class CreatePhraseDialog extends DialogFragment {

    public static final int OK = 1;
    private static final int CANCEL = 2;
    private UIHelper uiHelper;
    private View view;
    private App app;
    private Phrase phrase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_create_phrase, container);
        init();
        return view;
    }

    private void init() {
        phrase = new Phrase();
        app = (App) this.getActivity().getApplication();
        uiHelper = new UIHelper();
        setEvents();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setTitle(R.string.title_create_dialog_majority_note);
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
                CreatePhraseDialog.this.getTargetFragment().onActivityResult(getTargetRequestCode(), CANCEL, getActivity().getIntent());
                CreatePhraseDialog.this.dismiss();
            }
        };
    }

    private void saveNote() {
        Bundle bundle = getArguments();
        phrase.setIdMajorityNote(bundle.getLong("idMajorityNote"));
        phrase.setTitle(uiHelper.title.getText().toString());
        PhraseDAO.save(app.adapter, phrase);
        this.getTargetFragment().onActivityResult(getTargetRequestCode(), OK, getActivity().getIntent());

        CreatePhraseDialog.this.dismiss();
    }

    private class UIHelper implements Validator.ValidationListener {
        final Validator validator;

        @Required(order = 1, message = "it's mandatory =(")
        EditText title;

        LinearLayout btnOK;
        LinearLayout btnCancel;

        public UIHelper() {
            this.title = (EditText) view.findViewById(R.id.edt_phrase);
            this.btnOK = (LinearLayout) view.findViewById(R.id.phrase_create_dialog_btn_ok);
            this.btnCancel = (LinearLayout) view.findViewById(R.id.phrase_create_dialog_btn_cancel);

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
                Toast.makeText(CreatePhraseDialog.this.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
