package com.mgm.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

public class UploadedFileConstant {
    @Getter
    @RequiredArgsConstructor
    public enum UploadedFileStatus {
        NORMAL((byte) 0),
        DELETED((byte) 1);

        final Byte value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum UploadedFileType {

        /**
         * Other file
         */
        OTHERS((byte) 0),
        /**
         * Resume File
         */
        RESUME((byte) 1);
        final Byte value;

        public static UploadedFileType withValue(Byte value) {
            return Arrays.stream(values()).filter(v -> v.getValue().equals(value)).findFirst().orElse(OTHERS);
        }
    }
}
