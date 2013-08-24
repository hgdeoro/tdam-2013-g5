package ar.com.hgdeoro.tdam.holamundo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(this.getClass().toString(), "onCreate()");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.i(this.getClass().toString(), "onPostCreate()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(this.getClass().toString(), "onStart()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(this.getClass().toString(), "onRestart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(this.getClass().toString(), "onResume()");
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		Log.i(this.getClass().toString(), "onPostResume()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(this.getClass().toString(), "onSaveInstanceState()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(this.getClass().toString(), "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(this.getClass().toString(), "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(this.getClass().toString(), "onDestroy()");
	}

	@Override
	public void onLowMemory() {
		Log.i(this.getClass().toString(), "onLowMemory()");
		super.onLowMemory();
	}

}
