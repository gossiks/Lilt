package org.kazin.lilt.objects;

/**
 * Created by Alexey on 31.08.2015.
 */
public interface jCallback {
    void success(Object object);
    void fail(String error);
}
