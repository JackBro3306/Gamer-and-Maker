package com.typedef.gam.utils;

public class WebUtils {
    public static String removeDOMById(String... hideDomIds) {
        StringBuilder builder = new StringBuilder();
        String getEleMethod = "getElementById";
        builder.append("javascript:(function() { ");
        for (String domId : hideDomIds) {
            builder.append(String.format("var item = document.getElementById('",getEleMethod)).append(domId).append("');");
            builder.append("item.parentNode.removeChild(item);");
        }
        // add javascript suffix
        builder.append("})()");
        return builder.toString();
    }

    public static String removeDOMByClassName(String... hideDomIds) {
        StringBuilder builder = new StringBuilder();
        String getEleMethod = "getElementById";
        builder.append("javascript:(function() { ");
        for (String domId : hideDomIds) {
            builder.append(String.format("var items = document.getElementsByClassName('",getEleMethod)).append(domId).append("');");
            builder.append("for(var i = 0;i<items.length;i++){");
            builder.append("var item = items[i];");
            builder.append("item.parentNode.removeChild(item);");
            builder.append("}");
        }
        // add javascript suffix
        builder.append("})()");
        return builder.toString();
    }
}
