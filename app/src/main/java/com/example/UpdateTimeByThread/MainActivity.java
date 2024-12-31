package com.example.UpdateTimeByThread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.UpdateTimeByThread.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("lib_native");
    }

    private ActivityMainBinding binding;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tv = binding.sampleText;
        TextView native_tv = binding.nativeText;
        native_tv.setText(stringFromJNI()+"\n"+GetDateTime()+"\n"+GetDateTimeByChrono());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread t = getThread(tv);
        t.start();
        System.out.println("线程已启动===>线程名："+t.getName()+" 线程ID: "+t.getId()+
                " 线程状态: "+t.getState()+ " Alive: "+
                t.isAlive()+" Daemon: "+t.isDaemon());
    }

    @NonNull
    private Thread getThread(TextView tv) {
        Thread t = new Thread(() -> {
            while (true){
                try {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    tv.setText(LocalDateTime.now().format(fmt));
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("===>thread catch error: "+e.getMessage());
                    break;
                }
            }
        });
        t.setDaemon(true);
        t.setName("_thread_update_time_to_ui");
        return t;
    }
    public native String stringFromJNI();

    public native String GetDateTime();

    public native String GetDateTimeByChrono();
}