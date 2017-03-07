package pw.androidthanatos.irouter.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lxf52 on 2017/3/6.
 */

public class ClassUtils {
    private static final String TAG = "ClassUtils";
    public static  List<Class> getClassList(@NotNull Context context,@NotNull String action){
        List<String> classList=new ArrayList<>();
        Intent intent = new Intent(action);
       // intent.setPackage(context.getPackageName());
        for (ResolveInfo activities : context.getPackageManager().queryIntentActivities(intent, 0)) {
            classList.add(activities.activityInfo.name);
        }
        for (ResolveInfo  services: context.getPackageManager().queryIntentServices(intent,0)) {
            classList.add(services.activityInfo.name);
        }
        for (ResolveInfo  recivers: context.getPackageManager().queryBroadcastReceivers(intent,0)) {
            classList.add(recivers.activityInfo.name);
        }
        if (classList.isEmpty()){
            return Collections.emptyList();
        }else {
            return string2Class(classList);
        }

    }

    /**
     * 将字符串转化为类对象
     * @param list 字符串class 集合
     * @return
     */
    private static List<Class> string2Class(List<String> list){
        List<Class> classList=new ArrayList<>();
        for (String s:list) {
            try {
             Class<?>   name = Class.forName(s );
             classList.add(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        if (classList.isEmpty()){
            return Collections.emptyList();
        }else {
            return classList;
        }
    }
}
