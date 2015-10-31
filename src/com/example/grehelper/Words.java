package com.example.grehelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Words extends Fragment   
{
	Context context;
	// List view
    private ListView lv;
     
    // Listview Adapter
    ArrayAdapter<String> adapter;
    
    List<String>products =new ArrayList<String>();
    List<String>meanings =new ArrayList<String>();
    List<String>synonims =new ArrayList<String>();
    List<String>antonims =new ArrayList<String>();
    List<Integer>keys =new ArrayList<Integer>();
    
    DatabaseHandler db;
     
    // Search EditText
    EditText inputSearch;
  
    MyReceiver r;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View words = inflater.inflate(R.layout.words, container, false);
           
        context=container.getContext();
     // Listview Data
        
        db = new DatabaseHandler(container.getContext());
        
        List<Contact> contacts = db.getAllContacts();
		 
        for (Contact cn : contacts) 
        {
        	products.add(cn.get_word());
        }
         
        lv = (ListView)words.findViewById(R.id.list_view);
        inputSearch = (EditText)words.findViewById(R.id.inputSearch);
        
        
        // Adding items to listview
        adapter = new ArrayAdapter<String>(container.getContext(), R.layout.list_item, R.id.product_name, products);      
        lv.setAdapter(adapter);
        
        
        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() 
        {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) 
            {
                // When user changed the Text
                Words.this.adapter.getFilter().filter(cs);  
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) 
            {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) 
            {
                // TODO Auto-generated method stub                         
            }
        });
        
        
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
        	
        	String word,meaning,antonim,synonim;
        	int key;
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) 
			{
				List<Contact> contacts = db.getAllContacts();
		        meanings.clear();
				synonims.clear();
				antonims.clear();
				keys.clear();
				 
		        for (Contact cn : contacts) 
		        {
		        	meanings.add(cn.get_meaning());
		        	synonims.add(cn.get_synonim());
		        	antonims.add(cn.get_antonim());
		        	keys.add(cn.get_id());
		        }
				
				
				word= (String) lv.getItemAtPosition(position);
				
				int index = products.indexOf(word);
				
				meaning = meanings.get(index);
				synonim = synonims.get(index);
				antonim = antonims.get(index);
				key=keys.get(index);
				
				final Dialog dialog=new Dialog(context);
				dialog.setContentView(R.layout.memorising_test);
				dialog.setTitle("Remember "+"\'"+word+"\'?");
				dialog.setCanceledOnTouchOutside(false);
				final EditText meaniEditText=(EditText) dialog.findViewById(R.id.test_meaning_et);
				final EditText antonimEditText=(EditText) dialog.findViewById(R.id.test_antonim_et);
				final EditText synonimEditText=(EditText) dialog.findViewById(R.id.test_synonim_et);
				
				final ImageView mnImageView=(ImageView) dialog.findViewById(R.id.test_mn_iv);
				final ImageView anImageView=(ImageView) dialog.findViewById(R.id.test_an_iv);
				final ImageView syImageView=(ImageView) dialog.findViewById(R.id.test_sy_iv);
				
				final Button testButton=(Button) dialog.findViewById(R.id.test_btn);
				final Button skipButton=(Button) dialog.findViewById(R.id.test_skip_btn);
				
				mnImageView.setVisibility(view.INVISIBLE);
				anImageView.setVisibility(view.INVISIBLE);
				syImageView.setVisibility(view.INVISIBLE);
				
				testButton.setOnClickListener(new OnClickListener()
				{	
					@Override
					public void onClick(View arg0) 
					{
						String newMeaning=meaniEditText.getText().toString();
						String newAntonim=antonimEditText.getText().toString();
						String newSynonim=synonimEditText.getText().toString();
						
						mnImageView.setVisibility(view.VISIBLE);
						anImageView.setVisibility(view.VISIBLE);
						syImageView.setVisibility(view.VISIBLE);
						
						if ((newMeaning.toLowerCase()).equals(meaning.toLowerCase()))
						{
							mnImageView.setImageResource(R.drawable.right);
						}
						else 
						{
							mnImageView.setImageResource(R.drawable.wrong);
						}
						
						if ((newAntonim.toLowerCase()).equals(antonim.toLowerCase()))
						{
							anImageView.setImageResource(R.drawable.right);
						}
						else 
						{
							anImageView.setImageResource(R.drawable.wrong);
						}
						if ((newSynonim.toLowerCase()).equals(synonim.toLowerCase()))
						{
							syImageView.setImageResource(R.drawable.right);
						}
						else 
						{
							syImageView.setImageResource(R.drawable.wrong);
						}
					}
				});
				
				skipButton.setOnClickListener(new OnClickListener()
				{	
					@Override
					public void onClick(View arg0) 
					{
						Intent intent =new Intent(getActivity(),Word_Details.class);
						
						intent.putExtra("word", word);
						intent.putExtra("meaning", meaning);
						intent.putExtra("synonim", synonim);
						intent.putExtra("antonim", antonim);
						intent.putExtra("key", key);
						
						startActivity(intent);
						
						dialog.cancel();
					}
				});
				dialog.show();
				
			}
		});
        
        lv.setOnItemLongClickListener(new OnItemLongClickListener()
        {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) 
			{
				//WordsDialog warnDialog= new WordsDialog();
				//warnDialog.show(getFragmentManager(), "Warn Dialog");
				return false;
			}
		});
        
        return words;       
	}
	

	//function for refreshing listview data dynamically after adding words
	
	public void refresh() 
	{
	    //yout code in refresh
	    List<Contact> contacts = db.getAllContacts();  
	    products.clear();
	    
		 
        for (Contact cn : contacts) 
        {
        	products.add(cn.get_word());
        }
        
        adapter.notifyDataSetChanged();   
     }

	public void onPause() 
	{
	    super.onPause();
	    LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(r);
	    
	}

	@Override
	public void onResume() 
	{
	    super.onResume();
	    r = new MyReceiver();
	    LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(r,new IntentFilter("TAG_REFRESH"));
	    DatabaseHandler databaseHandler=new DatabaseHandler(context);
	    List<Contact> contacts =databaseHandler.getAllContacts();
	    products.clear();
	    for (Contact cn : contacts) 
        {
        	products.add(cn.get_word());
        }
	    adapter=new ArrayAdapter<String>(context, R.layout.list_item, R.id.product_name, products);
	    lv.setAdapter(adapter);
	}

	
	//broadcast receiver for listening word adding event 
	
	private class MyReceiver extends BroadcastReceiver 
	{
	    @Override
	    public void onReceive(Context context, Intent intent) 
	    {
	        Words.this.refresh();
	    }
	}
}