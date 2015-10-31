package com.example.grehelper;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class WordsDialog extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		Builder builder=new Builder(getActivity());
		builder.setTitle("Attention");
		builder.setMessage("Are you sure you want to delete this word?");
		builder.setNegativeButton("Cancel", new OnClickListener()
		{	
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Toast.makeText(getActivity(), "negative", Toast.LENGTH_LONG).show();
			}
		});
		builder.setPositiveButton("Ok", new OnClickListener()
		{	
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Toast.makeText(getActivity(), "Positive", Toast.LENGTH_LONG).show();
			}
		});
		Dialog dialog=builder.create();
		
		return dialog;
	}


}
