package com.android.zone.retrofit;


import java.util.List;


public class LanguageItem  {


    private List<LanguageBean> configuration;

    public List<LanguageBean> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<LanguageBean> configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "LanguageItem{" +
                "configuration=" + configuration +
                '}';
    }
}
