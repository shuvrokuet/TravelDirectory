package com.example.kuet.traveldirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by shuvro on 12/27/2015.
 */
public class StartPage extends Activity {

    User user=new User();

    @Override
    protected void onCreate(Bundle Shuvro) {
        super.onCreate(Shuvro);
        setContentView(R.layout.startpage);

        //Log.d("name:: ",user.name);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent start = new Intent(StartPage.this, UserInterface.class);

                    startActivity(start);
                   // startActivity(start);
                }

            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}