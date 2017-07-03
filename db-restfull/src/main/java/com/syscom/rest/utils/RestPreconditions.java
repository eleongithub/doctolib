package com.syscom.rest.utils;

/**
 * Created by ansible on 02/07/17.
 */
public class RestPreconditions {


    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Null Object is rejected !");
        }
        return resource;
    }
}
