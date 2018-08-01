package com.digitaldna.supplier.ui.screens.settings;

public class LanguageItem {
    private String languageName;
    private int languageID;
    private String languageShortName;
    private boolean defualtLanguage;


    public LanguageItem(String languageName, int languageID, String languageShortName) {
        super();
        this.languageName = languageName;
        this.languageID = languageID;
        this.languageShortName = languageShortName;
    }

    @Override
    public String toString() {
        return languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public int getLanguageID() {
        return languageID;
    }

    public String getLanguageShortName() {
        return languageShortName;
    }
}