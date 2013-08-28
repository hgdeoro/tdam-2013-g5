package ar.com.hgdeoro.tdam.holamundo;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

/**
 * Base Activity with random stuff.
 * 
 * @author Horacio G. de Oro
 * 
 */
public class MyBaseActivity extends Activity {

	private boolean doLog = false;

	protected void doLog() {
		this.doLog = true;
	}

	protected void doNotLog() {
		this.doLog = false;
	}

	protected boolean isLogging() {
		return this.doLog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (doLog)
			Log.i(this.getClass().toString(), "onCreate()");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (doLog)
			Log.i(this.getClass().toString(), "onCreateOptionsMenu()");
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (doLog)
			Log.i(this.getClass().toString(), "onPostCreate()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (doLog)
			Log.i(this.getClass().toString(), "onStart()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (doLog)
			Log.i(this.getClass().toString(), "onRestart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (doLog)
			Log.i(this.getClass().toString(), "onResume()");
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (doLog)
			Log.i(this.getClass().toString(), "onPostResume()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (doLog)
			Log.i(this.getClass().toString(), "onSaveInstanceState()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (doLog)
			Log.i(this.getClass().toString(), "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (doLog)
			Log.i(this.getClass().toString(), "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (doLog)
			Log.i(this.getClass().toString(), "onDestroy()");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if (doLog)
			Log.i(this.getClass().toString(), "onLowMemory()");
	}

}
