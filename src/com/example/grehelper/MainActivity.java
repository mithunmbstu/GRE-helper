package com.example.grehelper;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity 
{
	private Context context=this;
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    //DatabaseHandler databaseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		 
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//setContentView(R.layout.activity_main);
		Intent i = new Intent(MainActivity.this, Swipe_Tab_Main.class);
        startActivity(i);
        finish();
		
//		new Handler().postDelayed(new Runnable() 
//		{
//			 
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
// 
//            @Override
//            public void run() 
//            {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(MainActivity.this, Swipe_Tab_Main.class);
//                startActivity(i);
// 
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
	}
}
