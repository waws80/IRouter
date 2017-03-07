package pw.androidthanatos.irouter.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pw.androidthanatos.irouter.anotation.ModelType;
import pw.androidthanatos.irouter.utils.ClassUtils;

/**
 * Created by lxf52 on 2017/3/6.
 */

public class RouterLoader {

    private static final String TAG = "RouterLoader";
    private Context mContext;
    private String mHost;
    private Bundle mBundle;
    private Bundle mOptions;
    private String mAction;
    private int requestCode;
    private int responseCode;
    private Interrupter mInterrupter;
    public RouterLoader(@NotNull Context context, String mHost, Bundle mBundle, Bundle mOptions, Interrupter mInterrupter) {
        this.mContext=context;
        this.mBundle=mBundle;
        this.mOptions=mOptions;
        this.mInterrupter=mInterrupter;
        this.mHost=mHost;
    }

    public RouterLoader(@NotNull Context mContext, @NotNull String mAction, Bundle mBundle, Bundle mOptions, int requestCode, int responseCode, Interrupter mInterrupter) {
        this.mContext=mContext;
        this.mBundle=mBundle;
        this.mOptions=mOptions;
        this.mInterrupter=mInterrupter;
        this.mAction=mAction;
        this.responseCode=responseCode;
        this.requestCode=requestCode;
    }

    /**
     * android://activity/class/requestCode/responseCode
     * @param url
     */
    public void openByHref(@NotNull String url) {
        parseUrl(url);

    }

    private void parseUrl(@NotNull String url) {
        if (url.isEmpty()) throw new NullPointerException("the url can not null");
        if (!url.contains(mHost+"://")) throw new IllegalArgumentException("are you sure the host is"+mHost);
        String[] strings = url.split("://");
        String datas = strings[1];
        if (datas.isEmpty()) throw new IllegalArgumentException(" url error");
        String[] split = datas.split("/");
        String type=split[0];
        String targetClassName=split[1];
        if (targetClassName.isEmpty()) throw new IllegalArgumentException("target class error");

        try {
            Class<?> targetClass = Class.forName(targetClassName);
            Intent intent=new Intent(mContext,targetClass);
            intent.putExtra("bundle",mBundle);
            if (type.isEmpty()) throw new IllegalArgumentException("the url target error");
            switch (type){
                case "activity":
                    Activity activity= (Activity) mContext;
                    activity.startActivityForResult(intent,requestCode,mOptions);
                    break;
                case "service":
                    mContext.startService(intent);
                    break;
                default:
                    throw new IllegalArgumentException("the url target error");
            }
        } catch (ClassNotFoundException e) {
            Toast.makeText(mContext, "目标不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void openByName(@NotNull String name) {
        Class<?> targetClazz=null;
        List<Class> classList = ClassUtils.getClassList(mContext, mAction);
        if (classList.isEmpty()){
            Toast.makeText(mContext, "请确认Action设置是否正确或目标是否存在", Toast.LENGTH_SHORT).show();
        }
        for (Class<?> clazz:classList) {
            String value = bindType(clazz);
            if (value.equals(name)){
                targetClazz=clazz;
                break;
            }
        }
        if (targetClazz==null){
            Toast.makeText(mContext, "请确认Action设置是否正确或目标是否存在", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent=new Intent(mContext,targetClazz);
            intent.putExtra("bundle",mBundle);
            Activity activity= (Activity) mContext;
            activity.startActivityForResult(intent,requestCode,mOptions);
        }



    }

    private static String bindType(Class<?> clazz) {
        if (clazz.isAnnotationPresent(ModelType.class)){
            ModelType modelType = clazz.getAnnotation(ModelType.class);
            return modelType.value();
        }else {
            return "";
        }
    }
}
