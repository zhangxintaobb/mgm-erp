package com.mgm.common.helper;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class Uniqid {

    /***
     *  Copy of uniqid in php http://php.net/manual/fr/function.uniqid.php
     * @param prefix
     * @param moreEntropy
     * @return
     */
    public String uniqid(String prefix,boolean moreEntropy) {
        long time = System.currentTimeMillis();
        String uniqid = "";
        String format = String.format("%s%08x%05x", prefix, time / 1000, time & 0xFFFFFF);
        if(!moreEntropy)
        {
            uniqid = format;
        }else {
            SecureRandom sec = new SecureRandom();
            byte[] sbuf = sec.generateSeed(8);
            ByteBuffer bb = ByteBuffer.wrap(sbuf);

            uniqid = format;
            uniqid += "." + String.format("%.8s", "" + Math.abs(bb.getLong()));
        }
        return uniqid;
    }
}