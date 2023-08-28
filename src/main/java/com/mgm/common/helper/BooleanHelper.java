package com.mgm.common.helper;

import com.mgm.common.constant.BooleanConstant.BooleanOperator;

import java.util.ArrayList;
import java.util.List;

import static com.mgm.common.helper.GeneralHelper.*;

public class BooleanHelper {

    public static List<String> makeOrList(List<String> arr) {
        List<String> result = new ArrayList<>();
        result.add(BooleanOperator.OR.name());
        result.addAll(trimLowerStringList(arr));
        return result;
    }

    public static List<Object> makeAndOnlyList(List<Object> li) {
        List<Object> result = new ArrayList<>();
        result.add(BooleanOperator.AND.name());
        result.addAll(li);
        return result;
    }

    public static List<Object> makeNotList(List<Object> li) {
        List<Object> result = new ArrayList<>();
        result.add(BooleanOperator.NOT.name());
        result.addAll(li);
        return result;
    }

    public static List<Object> makeAndList(List<Object> cluster) {
        List<Object> result = new ArrayList<>();
        result.add(BooleanOperator.AND.name());
        for (Object li : cluster) {
            if (li instanceof List) {
                result.add(makeOrList((List<String>) li));
            }
        }
        return result;
    }

    public static String trimBySubString(String str, String subStr) {
        if (str.isEmpty() || subStr.isEmpty()) {
            return str;
        }
        String trimed = str;
        if (str.startsWith(subStr)) {
            trimed = trimed.replaceFirst(subStr, "");
        }
        if (str.endsWith(subStr)) {
            trimed = trimed.substring(0, trimed.lastIndexOf(subStr));
        }
        return trimed;
    }

    public static String trimEndBySubString(String str, String subStr) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        if (isNullOrEmpty(subStr)) {
            return str;
        }
        String trimed = str;
        if (str.endsWith(subStr)) {
            trimed = trimed.substring(0, trimed.lastIndexOf(subStr));
        }
        return trimed;
    }

    public static String searchArrayToBooleanString(Object searchArray) {
        String result = "";
        if (searchArray == null) {
            return result;
        }
        if (searchArray instanceof String) {
            return "\"" + searchArray + "\"";
        }
        if (searchArray instanceof List<?>) {
            List<?> formattedArray = (List) searchArray;
            if (formattedArray.size() == 0) {
                return result;
            }
            if (formattedArray.size() == 1) {
                return formattedArray.get(0).toString();
            }

            BooleanOperator operator = BooleanOperator.valueOf((String) formattedArray.get(0));
            List<?> remainingArray = formattedArray.subList(1, formattedArray.size());
            result += operator.name() + " ";
            switch (operator) {
                case NOT:
                    result += searchArrayToBooleanString(remainingArray);
                    break;
                case AND:
                case OR:
                    for (Object ele : remainingArray) {
                        if (ele instanceof String) {
                            result += "\"" + ele + "\"" + " " + operator + " ";
                        } else {
                            String subString = searchArrayToBooleanString(ele);
                            if (!subString.isEmpty()) {
                                result += subString + " " + operator + " ";
                            }
                        }
                    }
                    result = trimBySubString(result, operator + " ");
                    break;
                default:
                    return "";
            }
        }
        return "(" + result + ")";
    }
}
