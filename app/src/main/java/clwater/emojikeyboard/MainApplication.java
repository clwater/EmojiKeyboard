package clwater.emojikeyboard;

import android.app.Application;

import clwater.library.emojikeyboard.EmojiApplicationInit;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EmojiApplicationInit.init(this);
    }
}