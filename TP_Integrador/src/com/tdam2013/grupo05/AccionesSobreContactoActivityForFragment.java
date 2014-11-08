package com.tdam2013.grupo05;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Adaptador para mostrar fragmento en la una Activity
 * 
 * @author Horacio G. de Oro
 *
 */
public class AccionesSobreContactoActivityForFragment extends Activity {

	public static final String CONTACT_ID = "CONTACT_ID";
	public static final String DISPLAY_NAME = "DISPLAY_NAME";

	private Long contactId = null;
	private String displayName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		contactId = this.getIntent().getExtras().getLong(CONTACT_ID);
		displayName = this.getIntent().getExtras().getString(DISPLAY_NAME);

		AccionesSobreContactoFragment fragment = AccionesSobreContactoFragment
				.newInstance(contactId, displayName);

		getFragmentManager().beginTransaction()
				.add(android.R.id.content, fragment).commit();

	}

	public static Intent getActivityIntent(Context ctx, long contactId,
			String displayName) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(ctx,
				AccionesSobreContactoActivityForFragment.class
						.getCanonicalName()));
		intent.putExtra(CONTACT_ID, contactId);
		intent.putExtra(DISPLAY_NAME, displayName);
		return intent;
	}

}
