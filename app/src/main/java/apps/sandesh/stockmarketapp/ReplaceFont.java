package apps.sandesh.stockmarketapp;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by Sandesh on 1/20/2017.
 */

public class ReplaceFont {

    public static void replaceDefaultFont(Context context, String oldFont, String fontFile){
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), fontFile);
        replaceFont(oldFont, customFont);
    }

    private static void replaceFont(String oldFont, Typeface customFont) {
        try {
            Field field = Typeface.class.getDeclaredField(oldFont);
            field.setAccessible(true);
            field.set(null, customFont);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
