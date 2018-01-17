package com.example.magic.taskgithubrepos.utilities;

/**
 * Created by magic on 12/26/2017.
 * repository utils
 */

public final class ReposUtils {

    public static int getBackgroundColor(boolean fork) {
        if (fork) {
            return android.R.color.holo_blue_light;
        } else {
            return android.R.color.holo_orange_light;
        }
    }

}
