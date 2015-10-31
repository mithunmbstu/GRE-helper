package com.example.grehelper;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

public class Swipe_Tab_Main extends FragmentActivity 
{
	ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//hide title bar :D 
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		setContentView(R.layout.activity_swipe__tab__main);
		
		
		TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                      actionBar = getActionBar();
                      actionBar.setSelectedNavigationItem(position);                    }
                });
        
        Tab.setAdapter(TabAdapter);
        actionBar = getActionBar();
        
      //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener()
        {
        	@Override
        	public void onTabReselected(android.app.ActionBar.Tab tab,FragmentTransaction ft) 
        	{
        		
        	}
      
        	@Override
        	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) 
        	{
              Tab.setCurrentItem(tab.getPosition());

                  LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
                  Intent i = new Intent("TAG_REFRESH");
                  lbm.sendBroadcast(i);
              
        	}
      
        	@Override
        	public void onTabUnselected(android.app.ActionBar.Tab tab,FragmentTransaction ft) 
        	{
        // TODO Auto-generated method stub
        	}
        };
      //Add New Tab
      actionBar.addTab(actionBar.newTab().setText("Words").setTabListener(tabListener));
      actionBar.addTab(actionBar.newTab().setText("Add Word").setTabListener(tabListener));
      actionBar.addTab(actionBar.newTab().setText("About").setTabListener(tabListener));
	}
	
	
}
