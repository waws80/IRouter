package pw.androidthanatos.irouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pw.androidthanatos.irouter.control.Bind;

/**
 * Created by lxf52 on 2017/3/5.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bind.init(this);

    }
}
