package com.acrinrete.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;

import com.acrinrete.R;
import com.acrinrete.core.GlobalState;

public class DialogBuilder {

	public static Dialog createDialog(Activity context, int layout, boolean cancelable) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		builder.setCancelable(cancelable);
		View v = inflater.inflate(layout,
				(ViewGroup) context.findViewById(R.id.dialogLayout));
		builder.setView(v);
		Dialog d = builder.create();
		return d;
	}

	public static Dialog createExitDialog(Activity context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Sei sicuro di voler uscire?");
		MyListener listener = new MyListener(context);
		listener.setPositive(true);
		builder.setPositiveButton("OK", listener);
		MyListener listener2= new MyListener(context);
		listener2.setPositive(false);
		builder.setNegativeButton("Annulla", listener2);
		return builder.create();
	}
	
	static class MyListener implements DialogInterface.OnClickListener{
		private boolean positive;
		private Activity context;
		
		public MyListener(Activity context) {
			this.context = context;
			positive = true;
		}
		
		public void setPositive(boolean positive) {
			this.positive = positive;
		}

		public void onClick(DialogInterface arg0, int arg1) {
			if(positive){
				Intent intent = new Intent();
				context.setResult(Activity.RESULT_OK, intent);
				context.finish();
				GlobalState gs = (GlobalState) context.getApplication();
				gs.reset();
				
			}
			else{
				arg0.dismiss();
			}
			
		}
		
	}
	
	

}


