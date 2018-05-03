package clwater.emojikeyboard;


import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import clwater.library.emojikeyboard.EmojiKeyboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editText = findViewById(R.id.editText);

        EmojiKeyboard emojiKeyboard = findViewById(R.id.emojiKeyboard);
        emojiKeyboard.setEditText(editText);


        List<Drawable> tips = new ArrayList<>();
//        tips.add(this.getDrawable(R.drawable.ic_launcher_background));
//        tips.add(this.getDrawable(R.drawable.ic_launcher_background));
//        tips.add(this.getDrawable(R.drawable.ic_launcher_background));
        tips.add(this.getDrawable(R.drawable.icon_emojikeyboard_emoji));
        tips.add(this.getDrawable(R.drawable.icon_emojikeyboard_emoji));
        tips.add(this.getDrawable(R.drawable.icon_emojikeyboard_emoji));

        emojiKeyboard.setTips(tips);
        emojiKeyboard.setMaxLines(3);
        emojiKeyboard.setMaxColumns(7);
//        emojiKeyboard.setIndicatorPadding(3);

        emojiKeyboard.init();

    }
}
