package org.kazin.lilt.objects;

/**
 * Created by Alexey on 09.10.2015.
 */
public interface jCallbackRingtone {
    void onEvent(LiltRingtone2 ringtone);
    void onError(String error);
}
