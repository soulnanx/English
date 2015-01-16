package com.example.renan.english.pojo;

import com.example.renan.english.R;
import com.example.renan.english.entity.MajorityNote;
import com.example.renan.english.ui.fragment.MajorityNoteFragment;
import com.example.renan.english.ui.fragment.MinorNoteFragment;

import java.util.ArrayList;


public class MenuItemPojo {

    private String fragmentName;
    private int idStringResource;
    private int idDrawableResource;

    public MenuItemPojo() {
    }

    private MenuItemPojo(String fragmentName, int idStringResource, int idDrawableResource) {
        this.fragmentName = fragmentName;
        this.idStringResource = idStringResource;
        this.idDrawableResource = idDrawableResource;
    }

    public ArrayList<MenuItemPojo> getItemsMenu(){
        ArrayList<MenuItemPojo> list = new ArrayList<MenuItemPojo>();
        list.add(new MenuItemPojo(MajorityNoteFragment.class.getName(), R.string.majority_note_label, android.R.drawable.ic_menu_myplaces));
        list.add(new MenuItemPojo(MinorNoteFragment.class.getName(), R.string.minor_note_label, android.R.drawable.ic_menu_edit));
        list.add(new MenuItemPojo(MajorityNoteFragment.class.getName(), R.string.dialog_creator_label, android.R.drawable.ic_menu_my_calendar));
        list.add(new MenuItemPojo(MajorityNoteFragment.class.getName(), R.string.about_label, android.R.drawable.ic_menu_send));
        list.add(new MenuItemPojo(MajorityNoteFragment.class.getName(), R.string.settings_label, android.R.drawable.ic_menu_agenda));
        list.add(new MenuItemPojo(MajorityNoteFragment.class.getName(), R.string.logout, android.R.drawable.ic_menu_compass));

        return list;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public int getIdStringResource() {
        return idStringResource;
    }

    public int getIdDrawableResource() {
        return idDrawableResource;
    }


}
