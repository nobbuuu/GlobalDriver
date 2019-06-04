package com.sy.globaltake_driver.utils;


import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.MyApplication;

import org.xutils.http.RequestParams;

import java.util.Locale;

/**
 * Created by sunnetdesign3 on 2017/3/8.
 */
public class LanguageUtil {
    public static String getResText(int resText){
        String text = MyApplication.getInstance().getResources().getString(resText);
        return text;
    }

    public static boolean isZh() {
        Locale locale = MyApplication.getInstance().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    public static void addLanguage(RequestParams params){
        if (LanguageUtil.isZh()){
            params.addBodyParameter(Const.Language, Const.zh);
        }else {
            params.addBodyParameter(Const.Language, Const.en);
        }
    }
}
