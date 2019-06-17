package com.lqf.plugin.utils;

public class FilterUtil {

    public static boolean isMatchingClass(String[] interfaces) {
        return isMatchingInterfaces(interfaces, "android/view/View$OnClickListener");
    }



    private static boolean isMatchingInterfaces(String[] interfaces, String interfaceName) {
        boolean isMatch = false;
        for (String anInterface : interfaces) {
            if (anInterface.equals(interfaceName)) {
                isMatch = true;
            }
        }
        return isMatch;
    }



    public static boolean isMatchingMethod(String name, String desc) {
        if ((name.equals("onCreate") && desc.equals("(Landroid/os/Bundle;)V"))) {
            return true;
        }
        return false;
    }
}
