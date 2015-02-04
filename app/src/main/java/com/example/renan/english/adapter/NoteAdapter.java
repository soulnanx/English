package com.example.renan.english.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renan.english.R;
import com.example.renan.english.app.App;
import com.example.renan.english.entity.Note;
import com.example.renan.english.entity.Phrase;
import com.example.renan.english.util.NotesUtil;

import java.util.List;


public class NoteAdapter extends ArrayAdapter<Note> {

    private int resource;

    public NoteAdapter(Context context, int resource, List<Note> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (note != null) {
            itemHolder.titlePt.setText(note.getTitlePt());
            itemHolder.titleEn.setText(note.getTitleEn());

            if (note.getType() == Note.MAJORITY){
                itemHolder.qtdPhrases.setVisibility(View.VISIBLE);
                itemHolder.qtdPhrases.setText(NotesUtil.qtdPhrasesInNote(getContext(),note) + "");
                itemHolder.phrases.setVisibility(View.GONE);
            } else {
                itemHolder.box.setVisibility(View.GONE);
                itemHolder.phrases.setVisibility(View.VISIBLE);
                addAll(((App)getContext().getApplicationContext()).cacheList.get(note), itemHolder.phrases);
            }
        }
        notifyDataSetChanged();
        return convertView;
    }

    public class ItemHolder {
        LinearLayout box;
        TextView titlePt;
        TextView titleEn;
        TextView qtdPhrases;
        LinearLayout phrases;

        ItemHolder(View view) {
            this.titleEn = (TextView) view.findViewById(R.id.item_note_title_en);
            this.titlePt = (TextView) view.findViewById(R.id.item_note_title_pt);
            this.qtdPhrases = (TextView) view.findViewById(R.id.item_note_qtd_phrases);
            this.phrases = (LinearLayout) view.findViewById(R.id.phrases);
            this.box = (LinearLayout) view.findViewById(R.id.box_qtd_phrases);

        }
    }

    private void addAll(List<Phrase> phrases, ViewGroup viewGroup) {
        viewGroup.removeAllViews();

        for (Phrase phrase : phrases) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup lnPhrase = (ViewGroup) inflater.inflate(R.layout.item_phrase, null);
            TextView text = (TextView) lnPhrase.findViewById(R.id.text_phrase);
            text.setText(phrase.getTitle());
            viewGroup.addView(lnPhrase);
        }
    }

}
