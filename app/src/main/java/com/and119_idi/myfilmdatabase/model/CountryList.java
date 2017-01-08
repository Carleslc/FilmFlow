package com.and119_idi.myfilmdatabase.model;

import java.util.Locale;

public class CountryList {

    private static final String[] ISOCountryCodes = Locale.getISOCountries();

    private static CountryList sInstance;

    private String[] mCountryList;
    private String mLanguageCode;

    private CountryList(String languageCode) {
        mLanguageCode = languageCode;
        mCountryList = new String[ISOCountryCodes.length];

        for (int i = 0; i < ISOCountryCodes.length; i++) {
            mCountryList[i] = new Locale(languageCode, ISOCountryCodes[i]).getDisplayCountry();
        }
    }

    public static String[] get() {
        return getInstance(Locale.getDefault().getLanguage()).getCountryList();
    }

    public static String[] get(String languageCode) {
        return getInstance(languageCode).getCountryList();
    }

    private static CountryList getInstance(String languageCode) {
        if (sInstance == null || !sInstance.getLanguageCode().equals(languageCode)) {
            sInstance = new CountryList(languageCode);
        }
        return sInstance;
    }

    private String getLanguageCode() {
        return mLanguageCode;
    }

    private String[] getCountryList() {
        return mCountryList;
    }
}
