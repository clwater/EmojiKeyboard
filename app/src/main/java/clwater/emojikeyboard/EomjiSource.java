package clwater.emojikeyboard;

import java.util.ArrayList;
import java.util.List;

//可以通过https://apps.timwhitlock.info/emoji/tables/unicode 查看相关内容

public class EomjiSource {
    public static List<List<String>> getLists(){
        List<List<String>> lists = new ArrayList<>();

        lists.add(getEmoticons());
        lists.add(getDingbats());
        lists.add(getTransportAndMapSymbols());
        lists.add(getEnclosedCharacters());
        //范围太杂...
//        lists.add(getUncategorized());
        lists.add(getAdditionalEmoticons());
        lists.add(getAdditionalTransportAndMapSymbols());
        //27的库有些未更新
//        lists.add(getOtherAdditionalSymbols());

        return lists;
    }

    /**
     * @param unicode
     * @return
     * 将unicode转换为String方法
     */
    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    /**
     * @return
     * 1. Emoticons ( 1F601 - 1F64F )
     */
    private static List<String> getEmoticons(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x1F601 ; unicode < 0x1F64F ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }

    /**
     * @return
     * 2. Dingbats ( 2702 - 27B0 )
     */
    private static List<String> getDingbats(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x2702 ; unicode < 0x27B0 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }

    /**
     * @return
     * 3. Transport and map symbols ( 1F680 - 1F6C0 )
     */
    private static List<String> getTransportAndMapSymbols(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x1F680 ; unicode < 0x1F6C0 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }

    /**
     * @return
     * 4. Enclosed characters ( 24C2 - 1F251 )
     * 有点多... 展示未优化
     */
    private static List<String> getEnclosedCharacters(){
        List<String> list = new ArrayList<>();

//        for (int unicode = 0x24C2 ; unicode < 0x1F251 ; unicode++) {
        for (int unicode = 0x24C2 ; unicode < 0x2500 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }


    /**
     * @return
     * 5. Uncategorized
     * 没啥规律...
     */
    private static List<String> getUncategorized(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0 ; unicode < 0 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }

    /**
     * @return
     * 6a. Additional emoticons ( 1F600 - 1F636 )
     */
    private static List<String> getAdditionalEmoticons(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x1F600 ; unicode < 0x1F636 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }

    /**
     * @return
     * 6b. Additional transport and map symbols ( 1F681 - 1F6C5 )
     */
    private static List<String> getAdditionalTransportAndMapSymbols(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x1F681 ; unicode < 0x1F6C5 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }
    /**
     * @return
     * 6c. Other additional symbols ( 1F30D - 1F567 )
     */
    private static List<String> getOtherAdditionalSymbols(){
        List<String> list = new ArrayList<>();

        for (int unicode = 0x1F30D ; unicode < 0x1F567 ; unicode++) {
            list.add(getEmojiByUnicode(unicode));
        }
        return list;
    }




}
