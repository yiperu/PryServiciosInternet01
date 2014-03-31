package com.apps4s.pryserviciosinternet01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	EditText mEditText = null;
	Button miButon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		mEditText = (EditText)findViewById(R.id.editText);
		miButon = (Button)findViewById(R.id.button);
		
		miButon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String stringUrl = mEditText.getText().toString();
				if (isNetworkAvailable()) {
					new DownloadURL().execute(stringUrl);
				} else{
					System.out.println("No hay Conexion Disponible");
				}
				
			}
		});
		
		

		
	}

	public boolean isNetworkAvailable(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	public boolean isWifiConnection(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifi = networkInfo.isConnected();
		return isWifi;
	}
	
	public boolean isMobileConnection(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobile = networkInfo.isConnected();
		return isMobile;
	}
	
	private void readStream(InputStream in){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DownloadURL extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			try {
				URL myurl = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
				readStream(con.getInputStream());			
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}

}
