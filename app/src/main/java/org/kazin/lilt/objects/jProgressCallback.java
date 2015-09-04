package org.kazin.lilt.objects;

/**
 * Created by Alexey on 04.09.2015.
 */
public interface jProgressCallback  {
    void progress(Object progress);
    void failItem(String error);
}
