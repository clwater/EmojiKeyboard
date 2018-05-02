package clwater.emojikeyboard;


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

        emojiKeyboard.setMaxLines(5);
        emojiKeyboard.setMaxColumns(5);
        emojiKeyboard.setIndicatorPadding(3);

        emojiKeyboard.init();

    }
}
