package clwater.emojikeyboard;

import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import clwater.library.emojikeyboard.EmojiKeyboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editText = findViewById(R.id.editText);

        EmojiKeyboard emojiKeyboard = findViewById(R.id.emojiKeyboard);
        emojiKeyboard.setEditText(editText);
//
//        CustomTextView emojiTextview = findViewById(R.id.emojiTextview);
//        emojiTextview.setText("\uD83D\uDE01");
    }
}
