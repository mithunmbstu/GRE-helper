package com.example.grehelper;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Word_Details extends Activity implements TextToSpeech.OnInitListener
{
	final Context context=this;
	TextView meaning,antonim,synonim,word;
	private TextToSpeech tts;
	private ImageView btnSpeak;
	ImageView editImgView,deleteImgView;
	private String recieve_word,recieve_meaning,recieve_synonim,recieve_antonim;
	private int recieve_key;
	DatabaseHandler databaseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_word__details);
		
		Intent intent = getIntent();
		
		//recieve intent values
		recieve_word = intent.getStringExtra("word");
		recieve_meaning = intent.getStringExtra("meaning");
		recieve_synonim = intent.getStringExtra("synonim");
		recieve_antonim = intent.getStringExtra("antonim");
		recieve_key=intent.getIntExtra("key", 0);
		
		
		//set text of textview
		word = (TextView) findViewById(R.id.show_word);
		meaning = (TextView) findViewById(R.id.show_meaning);
		antonim = (TextView) findViewById(R.id.show_antonim);
		synonim = (TextView) findViewById(R.id.show_synonim);
		
		editImgView=(ImageView) findViewById(R.id.edit_iv);
		deleteImgView=(ImageView) findViewById(R.id.delete_iv);
		
		word.setText(recieve_word);
		meaning.setText(recieve_meaning);
		synonim.setText(recieve_synonim);
		antonim.setText(recieve_antonim);
		
		editImgView.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View view) 
			{
				final Dialog dialog=new Dialog(context);
				dialog.setContentView(R.layout.edit_word_details);
				dialog.setTitle("Edit Word");
				dialog.setCanceledOnTouchOutside(false);
				final EditText wordEditText=(EditText) dialog.findViewById(R.id.edit_word);
				final EditText meaniEditText=(EditText) dialog.findViewById(R.id.edit_meaning);
				final EditText antonimEditText=(EditText) dialog.findViewById(R.id.edit_antonim);
				final EditText synonimEditText=(EditText) dialog.findViewById(R.id.edit_synonim);
				final Button cancelButton=(Button) dialog.findViewById(R.id.edit_cancel);
				final Button saveButton=(Button) dialog.findViewById(R.id.edit_save);
				
				wordEditText.setText(recieve_word);
				meaniEditText.setText(recieve_meaning);
				antonimEditText.setText(recieve_antonim);
				synonimEditText.setText(recieve_synonim);
				
				cancelButton.setOnClickListener(new OnClickListener()
				{	
					@Override
					public void onClick(View arg0) 
					{
						dialog.cancel();
					}
				});
				
				saveButton.setOnClickListener(new OnClickListener()
				{	
					@Override
					public void onClick(View arg0) 
					{
						String newWord=wordEditText.getText().toString();
						String newMeaning=meaniEditText.getText().toString();
						String newAntonim=antonimEditText.getText().toString();
						String newSynonim=synonimEditText.getText().toString();
						if (recieve_word.equals(newWord) && recieve_meaning.equals(newMeaning) 
								&& recieve_antonim.equals(newAntonim) && recieve_synonim.equals(newSynonim)) 
						{
							Toast.makeText(context, "Nothing Changed", Toast.LENGTH_LONG).show();
						}
						else 
						{
							word.setText(newWord);
							meaning.setText(newMeaning);
							antonim.setText(newAntonim);
							synonim.setText(newSynonim);
							databaseHandler=new DatabaseHandler(context);
							Contact contact=new Contact(recieve_key, newWord, newMeaning, newAntonim, newSynonim);
							databaseHandler.updateContact(contact);
							dialog.cancel();
						}
					}
				});
				dialog.show();
			}
		});
		
		deleteImgView.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View view) 
			{
				Builder alertBuilder=new Builder(context);
				alertBuilder.setTitle("Attention");
				alertBuilder.setMessage("Are you sure you want to delete this word?")
				.setCancelable(false)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id) 
					{
						dialog.cancel();
					}
				})
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
				{	
					@Override
					public void onClick(DialogInterface dialog, int id) 
					{
						databaseHandler=new DatabaseHandler(context);
						Contact contact=new Contact(recieve_key, recieve_word, recieve_meaning, recieve_antonim, recieve_synonim);
						
						databaseHandler.deleteContact(contact);
						
		                Word_Details.this.finish();
		                


					}
				});
				AlertDialog alertDialog=alertBuilder.create();
				alertDialog.show();
			}
		});
		
		tts = new TextToSpeech(this, this);

		btnSpeak = (ImageView) findViewById(R.id.speak);
		
		
		// button on click event
		btnSpeak.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View arg0) 
			{
				speakOut();
			}

		});
	}
	
	
	@Override
	public void onDestroy() 
	{
		// Don't forget to shutdown!
		if (tts != null) 
		{
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) 
	{
		if (status == TextToSpeech.SUCCESS) 
		{

			int result = tts.setLanguage(Locale.US);

			//tts.setPitch(5); // set pitch level

			tts.setSpeechRate(0.5f); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA|| result == TextToSpeech.LANG_NOT_SUPPORTED) 
			{
				Log.e("TTS", "Language is not supported");
			} 
			
			else 
			{
				
			}

		} 
		else 
		{
			Log.e("TTS", "Initilization Failed");
		}
		
	}
	
	
	private void speakOut() 
	{

		//String text = "cat";
		String speakString="word "+word.getText().toString()+" meaning "+meaning.getText().toString()+" antonim "+antonim.getText().toString()+" synonim "+synonim.getText().toString();
		tts.speak(speakString, TextToSpeech.QUEUE_FLUSH, null);
	}
}
