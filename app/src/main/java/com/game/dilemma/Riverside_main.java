package com.game.dilemma;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.transition.Fade;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EventListener;

import static java.lang.Thread.sleep;import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;import android.media.AudioManager;import android.media.SoundPool;

public class Riverside_main extends AppCompatActivity {

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private final Handler mHideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideSystemUI();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeMessages(0);
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    //????????? ??????????????? ?????? ??????
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        if (hasFocus) {
            delayedHide(300);
        } else {
            mHideHandler.removeMessages(0);
        }

        Log.d("Focus debug", "Focus changed !");

        if(!hasFocus) {
            Log.d("Focus debug", "Lost focus !");

            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    //?????? ??????
    View touchArea;
    boolean touchActive = false;
    Button kill_btn;
    ScrollView scrollView1;
    TextView textView1;
    ImageView firstThought;
    SoundPool a1_sound;
    int a1_ID;
    SoundPool a2_sound;
    int a2_ID;
    SoundPool a3_sound;
    int a3_ID;
    SoundPool a6_sound;
    int a4_ID;
    AudioManager am;
    GestureDetector detector; //?????? ???????????? ????????? ??????
    Button ji_letter_btn;
    Button jung_letter_btn;
    Button bell_btn;
    boolean is_ji_prssd = false;
    boolean is_jung_prssd = false;

    private View decorView;
    private int uiOption;
    float raindrop_start;
    boolean isStraight = false;

    boolean get_isStraight() {
        return isStraight;
    }

    void set_isStraight(boolean val) {
        isStraight = val;
    }

    boolean videoStart = false;
    boolean is_first_video_start_request = true;
    boolean is_first_a1_audio_request = true;
    boolean is_first_a2_audio_request = true;
    boolean is_first_a3_audio_request = true;
    boolean is_first_a6_audio_request = true;

    int touch_cnt = 0;

    boolean[] narr_end = {false, false, false, false, false};
    int narr_1_alpha = 0;
    boolean narr_1_flag = true;
    int narr_1_time = 0;
    boolean threadOnly1 = true;
    void narr_1_fadeInOut() {
        if(threadOnly1) {
            ImageView firstThought = (ImageView) findViewById(R.id.firstThought);
            Handler handler_fading = new Handler();
            if (narr_1_time == 0) {
                firstThought.setImageAlpha(0);
                MySoundPlayer.play(MySoundPlayer.A1_SOUND);
            }
            firstThought.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (narr_1_flag) {
                        try {
//                            Log.d("nar", Integer.toString(narr_1_alpha));
                            handler_fading.post(new Runnable() {
                                @Override
                                public void run() {
                                    narr_1_time++;
                                    if (narr_1_time > 200) {
                                        narr_1_alpha -= 2;
                                        if (narr_1_alpha < 0) {
                                            narr_end[0] = true;
                                            narr_1_alpha = 0;
                                        }
                                    } else {
                                        narr_1_alpha += 2;
                                        if (narr_1_alpha > 255) {
                                            narr_1_alpha = 255;
                                        }
                                    }
                                    firstThought.setImageAlpha(narr_1_alpha);
                                }
                            });
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            threadOnly1 = false;
        }
    }

    int narr_2_alpha = 0;
    boolean narr_2_flag = true;
    int narr_2_time = 0;
    boolean isThreadOnly2 = true;
    void narr_2_fadeInOut() {
        if(isThreadOnly2) {
            ImageView secondThought = (ImageView) findViewById(R.id.secondThought);
            Handler handler_fading2 = new Handler();
            if (narr_2_time == 0) {
                secondThought.setImageAlpha(0);
                MySoundPlayer.play(MySoundPlayer.A2_SOUND);
            }
            secondThought.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (narr_2_flag) {
                        try {
                            handler_fading2.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (is_ji_prssd && is_jung_prssd && narr_end[1]) {
                                        narr_end[2] = true;
                                    }
                                    narr_2_time++;
                                    if (narr_2_time > 200) {
                                        narr_2_alpha -= 4;
                                        if (narr_2_alpha < 0) {
                                            narr_2_alpha = 0;
                                            narr_end[1] = true;
                                        }
                                    } else {
                                        narr_2_alpha += 2;
                                        if (narr_2_alpha > 255) {
                                            narr_2_alpha = 255;
                                        }
                                    }
                                    secondThought.setImageAlpha(narr_2_alpha);
                                }
                            });
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            isThreadOnly2 = false;
        }
    }

    int narr_3_alpha = 0;
    boolean narr_3_flag = true;
    int narr_3_time = 0;
    boolean raindropAnim_flag = true;
    boolean isThreadOnly3 = true;
    //????????? A4 '???' '???' ???????????? ?????????
    //5. narr_end[2]??? ??????????????? raindrop ??????
    void narr_3_fadeInOut() {
        if(isThreadOnly3) {
            ImageView thirdThought = (ImageView) findViewById(R.id.thirdThought);
            Handler handler_fading3 = new Handler();
            if (narr_3_time == 0) {
                thirdThought.setImageAlpha(0);
                MySoundPlayer.play(MySoundPlayer.A3_SOUND);
            }
            thirdThought.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (narr_3_flag) {
                        try {
                            handler_fading3.post(new Runnable() {
                                @Override
                                public void run() {
                                    narr_3_time++;

                                    //??????????????? ????????? ??? ????????? ????????? ????????? false ??????
                                    is_jung_prssd = false;
                                    is_ji_prssd = false;

                                    if (narr_end[2] && raindropAnim_flag) {
                                        raindrop_anim();
                                        raindropAnim_flag = false;
                                    }

                                    if (narr_end[2]) {
                                        narr_3_alpha -= 2;
                                        if (narr_3_alpha < 0) {
                                            narr_3_alpha = 0;
//                                            narr_3_flag = false;
                                            narr_end[2] = true;
                                        }
                                    } else {
                                        narr_3_alpha += 2;
                                        if (narr_3_alpha > 255) {
                                            narr_3_alpha = 255;
                                        }
                                    }
                                    thirdThought.setImageAlpha(narr_3_alpha);
                                }
                            });
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            isThreadOnly3 = false;
        }
    }

    int raindropY = 10;
    int raindropAlpha = 255;
    boolean rainFirstVisit = true;
    boolean raindrop_flag = true;
    boolean isThreadOnly4 = true;
    void raindrop_anim(){
        if(isThreadOnly4) {
            ImageView raindrop = (ImageView) findViewById(R.id.raindrop);
            Handler handler_rain = new Handler();
            if (narr_end[2] && rainFirstVisit) {
                raindrop.setVisibility(View.VISIBLE);
                rainFirstVisit = false;
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (raindrop_flag) {
                        try {
                            handler_rain.post(new Runnable() {
                                @Override
                                public void run() {
                                    raindropY += 3;
//                                Log.d("rainY", Integer.toString(raindropY));
                                    if (raindropY > 2200) {
                                        raindropY = -20;
                                    }
                                    raindrop.setY(raindropY);
                                    if (narr_end[3]) {
                                        raindropAlpha -= 4;
                                        if (raindropAlpha < 0) {
                                            raindrop.setVisibility(View.INVISIBLE);
                                            raindrop_flag = false;
                                        } else {
                                            raindrop.setImageAlpha(raindropAlpha);
                                        }
                                    }
                                }
                            });
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            isThreadOnly4 = false;
        }
    }

    boolean controlpanel_anim_flag = true;
    boolean bell_on = false;
    boolean bell_on_flag = true;
    float controlpanelY = -1900;
    float controlpanel_bellY = -80;
    int interval_between_upcoming_suicide_video = 0;
    boolean isThreadOnly5 = true;
    //(1)??????????????? ???????????? ??????????????? / ??? ?????? ?????? ?????????, (2)????????? ?????? (3)controlpanel_bell??? visibility on
    void controlpanel_anim(){
        if(isThreadOnly5) {
            ImageView controlpanel = (ImageView) findViewById(R.id.controlpanel);
            ImageView controlpanel_bell = (ImageView) findViewById(R.id.controlpanel_bell);
            Handler controlpanel_handler = new Handler();
            if (controlpanel_anim_flag) {
                controlpanel.setY(controlpanelY);
                controlpanel.setVisibility(View.VISIBLE);
                controlpanel_anim_flag = false;
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (narr_3_flag) {
                        try {
                            controlpanel_handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (bell_on_flag) {
                                        if (controlpanelY < -80) {
                                            controlpanelY += 1 + (-1 * (controlpanelY / 40));
                                        } else {
                                            controlpanelY = -80;
                                        }
                                        controlpanel.setY(controlpanelY);
                                        if (bell_on) {
                                            controlpanel_bell.setY(controlpanel_bellY);
                                            controlpanel_bell.setVisibility(View.VISIBLE);
                                            controlpanel.setVisibility(View.INVISIBLE);
                                            MySoundPlayer.play(MySoundPlayer.A6_SOUND);
                                            final VideoView videoView2 = (VideoView) findViewById(R.id.videoView2);
                                            videoView2.setVisibility(View.VISIBLE);
                                            Uri uri = Uri.parse("android.resource://com.game.dilemma/raw/suicide");
                                            videoView2.setVideoURI(uri);
                                            videoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                @Override
                                                public void onPrepared(MediaPlayer mp) {
                                                    // ?????? ???????????? ????????? ??????
                                                    mp.start();
                                                    mp.setLooping(true);
                                                }
                                            });
                                            bell_on_flag = false;
                                        }
                                    }
                                    if (bell_on) {
                                        interval_between_upcoming_suicide_video++;
                                        if (interval_between_upcoming_suicide_video > 50) {
                                            controlpanel_bellY -= 12;
                                            controlpanel_bell.setY(controlpanel_bellY);
                                            if (controlpanel_bellY < -1900) narr_3_flag = false;
                                        }
                                    }
                                }
                            });
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            isThreadOnly5 = false;
        }
    }

    int kill_cnt = 0;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riverside_main);
        this.startLockTask();

        //status bar??? hide
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        MySoundPlayer.initSounds(getApplicationContext());

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);

        kill_btn = findViewById(R.id.kill_btn);
        kill_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                kill_cnt++;
                Log.d("cnt",Integer.toString(kill_cnt));
                if(kill_cnt > 300){
                    stopLockTask();
                    finish();
                }
                return true;
            }
        });

        //A4 ????????? '???', '???' ??? ??????
        //A4 '???' '???' ???????????? ??????
        ji_letter_btn = (Button)findViewById(R.id.ji_letter_btn);
        jung_letter_btn = (Button)findViewById(R.id.jung_letter_btn);
        bell_btn = (Button)findViewById(R.id.bell_btn);
        ji_letter_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
//                Log.d("ji_btn", "true");
                //A4 '???' '???' ???????????? ??????

                if(narr_end[1]) {
                    return is_ji_prssd = true;
                }
                return true;
            }
        });
        jung_letter_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
//                Log.d("jung_btn", "true");
                if(narr_end[1]) {
                    return is_jung_prssd = true;
                }
                return true;
            }
        });
        bell_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
//                Log.d("ji_btn", "true");
                //A4 '???' '???' ???????????? ??????
                if(controlpanelY == -80){
                    bell_on = true;
                }
                return true;
            }
        });

        //??????????????? ?????? :
        //1. ????????? ????????? ??????
        //2. ??? ??? ????????? ????????? ????????? ?????? ??????
        touchArea = findViewById(R.id.touchArea);
        touchArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //????????? ???????????? ????????? ?????????
                detector.onTouchEvent(event);

                //????????? ?????????
                int action = event.getAction();
                float curX = event.getX();  //?????? ?????? X??????
                float curY = event.getY();  //?????? ?????? Y??????
                if (action == event.ACTION_DOWN) {   //?????? ????????? ???
                    if (curX > 390 && curX < 605) {
                        raindrop_start = curY;
//                        Log.d("Prsd_CurY : ", Float.toString(curY));
//                        Log.d("Prsd_CurX : ", Float.toString(curX));
//                        Log.d("Correct Area", "");
                        isStraight = true;
                    } else {
                        raindrop_start = 9999;
                    }
                    //printString("????????? ?????? : " + curX + ", " + curY);
                } else if (action == event.ACTION_MOVE) {    //????????? ???????????? ???
                    //printString("????????? ????????? : " + curX + ", " + curY);
                    if (curX < 390 || curX > 605) {
                        isStraight = false;
                    }
                } else if (action == event.ACTION_UP) {    //????????? ?????? ???
                    //printString("????????? ??? : " + curX + ", " + curY);
                    boolean isLong = curY - raindrop_start > 100;
                    if (isLong && isStraight && narr_end[2]) {
                        narr_end[3] = true;
                        controlpanel_anim();
                    } else {
                        if (raindrop_start == 9999) {
//                            Log.d("not Area", ".");
                        } else {
                            if (!isLong) {
//                                Log.d("not Long", ".");
                            }
//                            Log.d("not Straight", Boolean.toString(isStraight));
                        }
                        isStraight = true;
                        raindrop_start = 9999;
                    }
                }
                return true;
            }
        });
        Button disp_btn = (Button)findViewById(R.id.display_btn);
        Button disp_btn2 = (Button)findViewById(R.id.display_btn2);
        disp_btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("as", Boolean.toString(touchActive));
                if (is_first_video_start_request) {
                    //???????????? ????????? ?????????
                    TextView initializeText = (TextView) findViewById(R.id.InitializeText);
                    initializeText.setVisibility(View.INVISIBLE);

                    //???????????? ????????? ????????????(flag. ??? ?????? ???????????? ?????????)
                    final VideoView videoView = (VideoView) findViewById(R.id.videoView);
                    videoView.setVisibility(View.VISIBLE);
                    Uri uri = Uri.parse("android.resource://com.game.dilemma/raw/a0_video");
                    videoView.setVideoURI(uri);
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // ?????? ???????????? ????????? ??????
                            mp.start();
                            mp.setLooping(true);
                        }
                    });
                    is_first_video_start_request = false;
                }
                disp_btn2.setVisibility(View.INVISIBLE);
                touchActive = true;
            }
        });
        disp_btn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent m) {
                if(touchActive) {
                    touch_cnt++;
                }
                if(touch_cnt > 0){
                    narr_1_fadeInOut();
                    if(narr_end[0]){
                        narr_2_fadeInOut();
                    }
                    if(narr_end[1]){
                        disp_btn.setVisibility(View.INVISIBLE);
                        narr_3_fadeInOut();
                    }
                }
                return true;
            }
        });

        //????????? ????????? ?????? :
        //1. x
        //2. ???????????? ????????? ?????????
        //3. ???????????? ????????? ????????????
        //4. ????????? ????????????
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            //????????? ????????? ???
            @Override
            public boolean onDown(MotionEvent e) {

                return true;
            }

            //????????? ????????? ???????????? ??????
            @Override
            public void onShowPress(MotionEvent e) {
                touchActive = true;
            }

            //????????? ??? ??????????????? ????????? ???????????? ??????
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                touchActive = true;
                return true;
            }

            //????????? ????????? ????????? ????????? ???????????? ???????????? ???????????? ??????
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                touchActive = true;
                return false;
            }

            //????????? ??????????????? ???????????? ????????? ??????
            @Override
            public void onLongPress(MotionEvent e) {
                touchActive = true;
            }

            //????????? ????????? ???????????? ???????????? ???????????? ???????????? ??????
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                touchActive = true;
                return true;
            }
        });
    }
}
