package com.asus.cloudmusic.Util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * Created by asus on 2018/1/22.
 */

public class Text_Util {
    private static StringBuffer stringBuffer = new StringBuffer();

    /**
     * 获取汉字字符串的首字母，英文字符不变
     * 例如：阿飞→af
     */
    public static String getPinYinHeadChar(String chines) {
        stringBuffer.setLength(0);
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aChar : chars) {
            if (aChar > 128) {
                try {
                    stringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(aChar, defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                stringBuffer.append(aChar);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 获取汉字字符串的第一个字母
     */
    public static String getPinYinFirstLetter(String str) {
        stringBuffer.setLength(0);
        char c = str.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyinArray != null) {
            stringBuffer.append(pinyinArray[0].charAt(0));
        } else {
            stringBuffer.append(c);
        }
        String result = stringBuffer.toString().toUpperCase();
        if (getChsAscii(result) > 90 || getChsAscii(result) < 65) {
            return "#";
        }
        return result;
    }

    private static int getChsAscii(String chs) {
        int asc = 0;
        try {
            byte[] bytes = chs.getBytes("gb2312");
            if (bytes.length > 2 || bytes.length <= 0) {
                throw new RuntimeException("illegal resource string");
            }
            if (bytes.length == 1) {
                asc = bytes[0];
            }
            if (bytes.length == 2) {
                int hightByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                asc = (256 * hightByte + lowByte) - 256 * 256;
            }
        } catch (Exception e) {
            System.out.println("ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
        }
        return asc;
    }

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYin(String chines) {
        stringBuffer.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aNameChar : nameChar) {
            if (aNameChar > 128) {
                try {
                    stringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(aNameChar, defaultFormat)[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                stringBuffer.append(aNameChar);
            }
        }
        return stringBuffer.toString();
    }

    public static String getTime(int t) {
        String string;
        int min, sec;
        t = t / 1000;
        min = t / 60;
        sec = t % 60;
        if (min>10&&sec>10){
            string = ("" + min + ":" + "" + sec);
        }else if(min<10&&sec<10){
            string = ("0" + min + ":" + "0" + sec);
        }else if (min>10&&sec<10){
            string = ("" + min + ":" + "0" + sec);
        }else{
            string = ("0" + min + ":" + "" + sec);
        }
        return string;
    }
}
