package org.kazin.lilt.objects;

import org.kazin.lilt.backend.ContactAA;

/**
 * Created by Alexey on 16.10.2015.
 */
public interface SyncStateListener {
    void call(ContactAA contact);
}
