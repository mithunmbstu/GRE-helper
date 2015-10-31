package com.example.grehelper;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Words extends Fragment 
{
	
	EditText word,meaning,antonims,synonims;
	Button saveButton;
	
	List<String>same =new ArrayList<String>();
	
	DatabaseHandler db;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
        View add_words = inflater.inflate(R.layout.add_words, container, false);

        db = new DatabaseHandler(container.getContext());
        
        word = (EditText) add_words.findViewById(R.id.word);
        meaning = (EditText) add_words.findViewById(R.id.meaning);
        antonims = (EditText) add_words.findViewById(R.id.antonims);
        synonims = (EditText) add_words.findViewById(R.id.synonims);
        
        saveButton = (Button) add_words.findViewById(R.id.save_btn);
 
        
        //code for save button
        
        saveButton.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View view) 
			{
				/**
		         * CRUD Operations
		         * */

				same.clear();
		
				List<Contact> contacts = db.getAllContacts();     
				if(word.getText().toString().length()>0 && meaning.getText().toString().length()>0 && synonims.getText().toString().length()>0 && antonims.getText().toString().length()>0)
				{
 
			        for (Contact cn : contacts) 
			        {
			        	same.add(cn.get_word());
			        }
					
			        if(same.contains(word.getText().toString()))
					{	
						Toast.makeText(getActivity().getApplicationContext(),"Word ALready Exists", Toast.LENGTH_LONG).show();
					}
					
					else
					{
						// Inserting Contacts
						Log.d("Insert: ", "Inserting ..");
						db.addContact(new Contact(word.getText().toString(),meaning.getText().toString(),antonims.getText().toString(),synonims.getText().toString()));
						Toast.makeText(getActivity().getApplicationContext(),"Word Inserted", Toast.LENGTH_LONG).show();
						clearall();
						
						LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
		                Intent i = new Intent("TAG_REFRESH");
		                lbm.sendBroadcast(i);
					}
				}
				
				else
					Toast.makeText(getActivity().getApplicationContext(),"Fill all the Fields", Toast.LENGTH_LONG).show();
					
		    }

			private void clearall() 
			{
			
				word.setText("");
				meaning.setText("");
				synonims.setText("");
				antonims.setText("");
			} 
			
		});
        
        return add_words;
	}
}