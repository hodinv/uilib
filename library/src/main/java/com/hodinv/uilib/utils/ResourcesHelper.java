package com.hodinv.uilib.utils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vhodin on 22.12.2015.
 */
public class ResourcesHelper {

    public static int[] loadResourcesIds(Class<?> clz, Filter filter) {
        final Field[] fields = clz.getDeclaredFields();
        List<Integer> ids = new LinkedList<>();
        for (Field field : fields) {
            try {
                int resId = field.getInt(clz);
                String resName = field.getName();
                if (filter.match(resName)) {
                    ids.add(resId);
                }
            } catch (Exception e) {

            }
        }
        int result[] = new int[ids.size()];
        int i = 0;
        for (Integer id : ids) {
            result[i] = id;
            i++;
        }
        return result;
    }


    public interface Filter {
        boolean match(String name);
    }
}
