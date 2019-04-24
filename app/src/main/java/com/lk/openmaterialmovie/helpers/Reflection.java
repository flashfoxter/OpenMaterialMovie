/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.helpers;

import android.content.Context;

import com.lk.openmaterialmovie.Strings;

import java.util.StringTokenizer;

public class Reflection {

    //We can use it for cross project service management, just cache results to prevent search every time
    public static Class<?> findClassBySimpleName(String classname, String[] searchPackages) {
        for (String searchPackage : searchPackages) {
            try {
                return Class.forName(searchPackage + "." + classname);
            } catch (ClassNotFoundException e) {
                //not in this package, try another
            }
        }
        //nothing found: return null or throw ClassNotFoundException
        return null;
    }

    public static String getLayoutNameFor(Class<?> viewHolderClass) {
        String viewHolderName = viewHolderClass.getSimpleName();
        viewHolderName = viewHolderName.substring(0, viewHolderName.indexOf("ViewHolder"));
        return "cell_" + viewHolderName.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }

    public static Class<?> getBindingClassForResId(Context context, int resId) {
        String resourceName = context.getResources().getResourceEntryName(resId);
        StringBuilder bindingName = new StringBuilder(PackageName.DATA_BINDING);
        StringTokenizer tokenizer = new StringTokenizer(resourceName);
        while (tokenizer.hasMoreTokens()) {
            bindingName.append(Strings.capitalize(tokenizer.nextToken("_")));
        }
        try {
            return Class.forName(bindingName.append("Binding").toString());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
