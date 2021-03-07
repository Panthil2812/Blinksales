package com.rku.blinksales;

import android.content.Context;
import android.view.View;
interface DoubleTapCallback {

    public void onDoubleClick(View v);
}
public class DoubleTapListener  implements View.OnClickListener{

    private boolean isRunning= false;
    private int resetInTime =500;
    private int counter=0;

    private DoubleTapCallback listener;

    public DoubleTapListener(Context context)
    {
        listener = (DoubleTapCallback)context;
    }

    @Override
    public void onClick(View v) {

        if(isRunning)
        {
            if(counter==1) //<-- makes sure that the callback is triggered on double click
                listener.onDoubleClick(v);
        }

        counter++;

        if(!isRunning)
        {
            isRunning=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter=0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}