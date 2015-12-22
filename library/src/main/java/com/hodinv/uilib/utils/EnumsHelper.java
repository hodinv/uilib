package com.hodinv.uilib.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vhodin on 22.12.2015.
 */
public class EnumsHelper {
    private static EnumsHelper mInstance;
    private Context mContext;
    private Map<Class<? extends Enum>, Map<String, String>> mEnums = new HashMap<>();


    private EnumsHelper(Context context) {

        mContext = context;
    }

    public static void init(Application context) {
        mInstance = new EnumsHelper(context);
    }

    public static EnumsHelper getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("EnumsHelper must be initialized before usage");
        }
        return mInstance;
    }

    /**
     * Loads from string resourece. Looks for ids like
     * %enum_simple_name_lowercase%_%enum_name_lowercase% (if prefix is null and suffix is null)
     * or
     * %prefix_lowercase%_%enum_simple_name_lowercase%_%enum_name_lowercase% (if prefix not null and suffix is null)
     * or
     * %prefix_lowercase%_%enum_simple_name_lowercase%_%enum_name_lowercase%_%suffix% (if suffix not null and prefix not null)
     * or
     * %enum_simple_name_lowercase%_%enum_name_lowercase%_%suffix% (if prefix is null and suffix is not null)
     *
     * @param clazz  enum class to load
     * @param prefix prefix for string resource
     * @param suffix sffix for string resource
     * @param <T>
     */
    public <T extends Enum> void loadEnum(@NonNull Class<T> clazz, @Nullable String prefix, @Nullable String suffix) {
        if (mEnums.containsKey(clazz)) {
            throw new RuntimeException("Such enum already loaded");
        }
        String startsWith = (prefix != null ? (prefix.toLowerCase()) + "_" : "") + clazz.getSimpleName().toLowerCase();
        String endsWith = suffix != null ? ("_" + suffix.toLowerCase()) : "";
        Map<String, String> values = new HashMap<>();
        for (T item : clazz.getEnumConstants()) {
            String name = startsWith + "_" + item.name().toLowerCase() + endsWith;
            int titleId = mContext.getResources().getIdentifier(name, "string", mContext.getPackageName());
            if (titleId == 0) {
                throw new RuntimeException("cant find string resource " + name + " for enum " + clazz.getCanonicalName() + " value " + item.name());
            }
            String value = mContext.getString(titleId);
            values.put(item.name(), value);
        }
        mEnums.put(clazz, values);
    }


    /**
     * Loads from string resourece. Looks for ids like
     * %enum_simple_name_lowercase%_%enum_name_lowercase% (
     *
     * @param clazz enum class to load
     * @param <T>
     */
    public <T extends Enum> void loadEnum(@NonNull Class<T> clazz) {
        loadEnum(clazz, null, null);
    }

    /**
     * Get title for loaded enum
     *
     * @param e enum to get title for
     * @return title for enum
     */
    public String getTitle(Enum e) {
        Map<String, String> values = mEnums.get(e.getClass());
        if (values == null) {
            throw new RuntimeException("Titles for enum " + e.getClass().getCanonicalName() + " not loaded");
        }
        return values.get(e.name());
    }

}
