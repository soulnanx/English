package com.example.renan.english.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.renan.english.R;
import com.example.renan.english.app.App;
import com.example.renan.english.dao.PhraseDAO;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.entity.Phrase;

import java.util.List;


public class MajorityNoteAdapter extends ArrayAdapter<MajorityNote> {

    private int resource;

    public MajorityNoteAdapter(Context context, int resource, List<MajorityNote> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        MajorityNote majorityNote = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (majorityNote != null) {
            itemHolder.titlePt.setText(majorityNote.getTitlePt());
            itemHolder.titleEn.setText(majorityNote.getTitleEn());
            itemHolder.qtdPhrases.setText(getQtdPhrases(majorityNote.getId()) + "");
        }
        notifyDataSetChanged();
        return convertView;
    }

    public class ItemHolder {
        TextView titlePt;
        TextView titleEn;
        TextView qtdPhrases;

        ItemHolder(View view) {
            this.titleEn = (TextView) view.findViewById(R.id.item_majority_note_title_en);
            this.titlePt = (TextView) view.findViewById(R.id.item_majority_note_title_pt);
            this.qtdPhrases = (TextView) view.findViewById(R.id.item_majority_note_qtd_phrases);
        }
    }

    private int getQtdPhrases(long idMajorityNote){
        App app = (App) getContext().getApplicationContext();
        return PhraseDAO.findQtdItem(app.adapter, idMajorityNote);
    }
}
