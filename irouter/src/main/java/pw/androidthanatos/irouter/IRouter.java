package pw.androidthanatos.irouter;

import android.content.Context;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import pw.androidthanatos.irouter.control.Interrupter;
import pw.androidthanatos.irouter.control.RouterLoader;

/**
 * Created by lxf52 on 2017/3/5.
 * android://android/
 */

public class IRouter {
    private static WeakReference<Context> contextWeakReference;
    private static String mAction;
    private RouterLoader mLoader;
    private Bundle mBundle;
    private Bundle mOptions;
    private int requestCode=-1;
    private int responseCode=-1;
    private Interrupter mInterrupter;
    private Context mContext;
    private static String mHost="android";

    private IRouter(){
       mContext=contextWeakReference.get();
    }

    public static class Builder{

        public static IRouter build(@NotNull Context context){
            contextWeakReference=new WeakReference<>(context);
            return new IRouter();
        }
    }
    public static void register(@NotNull String action){
        mAction=action;
    }
    public static void register(@NotNull String action ,String host){
        mAction=action;
        mHost=host;
    }

    /**
     *
     * @param url android://activity/class/requestCode/responseCode
     * @return
     */
    public IRouter openHref(@NotNull String url){
        mLoader=new RouterLoader(mContext,mHost,mBundle,mOptions,mInterrupter);
        mLoader.openByHref(url);
        return this;
    }



    public IRouter openName(@NotNull String name){
        mLoader=new RouterLoader(mContext,mAction,mBundle,mOptions,requestCode,responseCode,mInterrupter);
        mLoader.openByName(name);
        return this;
    }



    public IRouter addBundle(@NotNull Bundle bundle){
       this.mBundle=bundle;
        return this;
    }



    public IRouter addOption(@NotNull Bundle option){
       this.mOptions=option;
        return this;
    }


    public IRouter addRequestCode(@NotNull int requestCode){
       this.requestCode=requestCode;
        return this;
    }



    public IRouter addResponseCode(@NotNull int responseCode){
      this.responseCode=responseCode;
        return this;
    }

    public IRouter addInterrupter(Interrupter interrupter){
        this.mInterrupter=interrupter;
        return this;
    }



    public static void unregister(){
        if (contextWeakReference!=null){
            if (contextWeakReference.get()!=null){
                contextWeakReference.clear();
            }
            contextWeakReference=null;
        }
    }

}
