package pw.androidthanatos.irouter.control;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pw.androidthanatos.irouter.anotation.BindLayout;

/**
 * Created by lxf52 on 2017/3/5.
 */

public class Bind {

    public static void init(Context context){
                bindLayout(context);

    }

    /**
     * 初始化布局
     * @param context 上下文
     */
    private static void bindLayout(Context context) {
        Class<?> clazz=context.getClass();
        if (clazz.isAnnotationPresent(BindLayout.class)){
            BindLayout annotation = clazz.getAnnotation(BindLayout.class);
            int resId = annotation.value();
            try {
                Method setContentView = clazz.getMethod("setContentView", int.class);
                setContentView.invoke(context,resId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
