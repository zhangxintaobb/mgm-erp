package com.mgm.common.helper;

public class FileHelper {

    private static String resumeKeySplitor = "__\\{\\{@@@}}__";

    public static String parseResumeNameFromUrl(String resumeKey) {
        try {
            String[] splitedArr = resumeKey.split(resumeKeySplitor);
            if (splitedArr.length > 0) {
                return splitedArr[splitedArr.length - 1];
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
