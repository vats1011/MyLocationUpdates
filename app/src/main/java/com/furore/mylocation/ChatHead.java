package com.furore.mylocation;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import android.app.Service;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.furore.intern.R;

public class ChatHead extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    Point p;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // return super.onStartCommand(intent, flags, startId);

        return Service.START_STICKY;
    }

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.serlocimg2);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        final Context context=getApplicationContext();
        windowManager.addView(chatHead, params);

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        DBLocHistory db = new DBLocHistory(getApplicationContext());
                        db.open();
                        LocPojo lp =db.getLastLoc();
                        db.close();

                        Toast.makeText(getApplicationContext(),lp.latitude+"\n"+lp.longitude+"\n"+lp.address,Toast.LENGTH_SHORT).show();

                        //showPopup(v,p);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHead, params);
                        return true;
                }
                return false;
            }
        });

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showPopup(new Activity(),p);
            }
        });
    }

    // The method that displays the popup.
    private void showPopup(View v, Point p) {
        int popupWidth = 200;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        /*LinearLayout viewGroup = (LinearLayout) v.findViewById(R.id.llpop);
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.popup, null);*/

        LinearLayout viewGroup = (LinearLayout) v.findViewById(R.id.llpop);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow();
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}