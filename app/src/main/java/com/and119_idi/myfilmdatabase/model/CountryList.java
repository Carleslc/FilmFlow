package com.and119_idi.myfilmdatabase.model;

import java.util.Locale;

/**
 * Created by albert on 06/12/16.
 * Refactored by Carlos on 07/01/17.
 */
public class CountryList {

    private static final String[] ISO_COUNTRY_CODES = Locale.getISOCountries();

    private static CountryList sInstance;

    private String[] mCountryList;
    private String mLanguageCode;

    private CountryList(String languageCode) {
        mLanguageCode = languageCode;
        mCountryList = new String[ISO_COUNTRY_CODES.length];

        for (int i = 0; i < ISO_COUNTRY_CODES.length; i++) {
            mCountryList[i] = new Locale(languageCode, ISO_COUNTRY_CODES[i]).getDisplayCountry();
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
