package com.mgm.common.casesensitive;


public @interface CaseSensitiveValue {

    CaseSensitiveType type() default CaseSensitiveType.ORIGINAL;

}
