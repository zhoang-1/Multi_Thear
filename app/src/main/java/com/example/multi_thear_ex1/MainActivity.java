package com.example.multi_thear_ex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.multi_thear_ex1.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Random random =new Random();
    // main thread
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int parent = msg.arg1;
            int randomb =(int) msg.obj;
            binding.tvTienDo.setText(parent + "%");
            binding.probTienDo.setProgress(parent);

            //  update UI
            ImageView imageView = new ImageView(MainActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);

            imageView.setLayoutParams(params);
            if (randomb %2 ==0){
                imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));

            }else {
                imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
            }
            binding.containerLayout.addView(imageView);


            if (parent ==100){
                binding.tvTienDo.setText("DONE!");
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        addEvend();
    }

    private void addEvend() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBackgrow();
            }
        });
    }

    private void drawBackgrow() {
        binding.containerLayout.removeAllViews();
        int numberView = Integer.parseInt(binding.edtEnterText.getText().toString());
        // background thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =1 ; i<= numberView; i++){
                    Message message =  new Message(); // new obtainMessage
                    message.arg1 = i+ 10/numberView;
                    message.obj = random.nextInt(100); //radndom
                    handler.sendMessage(message);
                    SystemClock.sleep(100);

                }
            }
        });
    }
}