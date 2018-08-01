package com.digitaldna.supplier.ui.screens.settings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.digitaldna.supplier.R;

import java.util.List;
import java.util.Locale;

/**
 * Adapter for LanguageActivity.
 */
public class LanguageAdapter extends BaseAdapter {
    public int language;
    Integer selectedPosition = -5;
    private LayoutInflater layoutInflater;
    private List<LanguageItem> languageItems;

    public LanguageAdapter(Context context, List<LanguageItem> languageItems) {
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.languageItems = languageItems;
    }
    @Override
    public int getCount() {
        return languageItems.size();
    }
    @Override
    public LanguageItem getItem(int position) {
        return languageItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View columnView;
        columnView = layoutInflater.inflate(R.layout.item_language, null);
        final TextView textView =
                (TextView) columnView.findViewById(R.id.textView90);
        final RadioButton radioButton = (RadioButton) columnView.findViewById(R.id.radioButton9);
        final LanguageItem languageItem = languageItems.get(position);
        textView.setText(languageItem.getLanguageName());

        if (selectedPosition == -5) {
            final LanguageItem ıtem = languageItems.get(position);
            radioButton.setChecked(languageItem.getLanguageShortName().equals(Locale.getDefault().getLanguage().toUpperCase()));
            ChangeLanguageActivity.chosenLangId= languageItem.getLanguageID();
            ChangeLanguageActivity.chosenLangShortName = languageItem.getLanguageShortName();
        } else radioButton.setChecked(position == selectedPosition);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedPosition = position;
                    ChangeLanguageActivity.chosenLangId= languageItem.getLanguageID();
                    ChangeLanguageActivity.chosenLangShortName = languageItem.getLanguageShortName();
                  //  LanguageActivity.languageShortName = languageItem.getLanguageShortName();
              //      language = languageItem.getLanguageID();
                   // LanguageActivity.langId = languageItem.getLanguageID();
                   // HttpPost.language = languageItem.getLanguageShortName();
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
            }
        });
        return columnView;
    }

    private int selectedItem;
    public void setSelectedItem(int position) {
        this.selectedItem = position;
        this.selectedPosition = position;
        Log.i("PPPP", "position" + position);
        Log.i("PPPP", "selectedPosition" + selectedPosition);
    }
}