package com.example.umair.finguresketch.bookshelf;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.umair.finguresketch.ActivityBase;
import com.example.umair.finguresketch.FingerWriterActivity;
import com.example.umair.finguresketch.R;
import com.example.umair.finguresketch.data.Book.BookIOException;
import com.example.umair.finguresketch.data.Bookshelf;

/**
 * This activity is called if the user picks a .finger_sketch file with a file manager
 * @author vbraun
 *
 */
public class ImportBackupActivity extends ActivityBase {
	@SuppressWarnings("unused")
	private final static String TAG = "ImportBackupActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_backup);
	}
	
	private Handler handler = new Handler();
	private File file = null;
	
	@Override
	protected void onStart() {
		super.onStart();
	    final Intent intent = getIntent ();
	    if (intent == null) return;
	    Uri uri = intent.getData();
	    if (uri == null) return;
        final String filename = uri.getEncodedPath();
        file = new File(filename);
        Log.e(TAG, "importing backup file "+file.getAbsolutePath());
	}
	
	private Runnable importRunnable = new Runnable() {
		@Override
		public void run() {
    		Bookshelf bookshelf = Bookshelf.getBookshelf();
    		try {
    			bookshelf.importBook(file);
    		} catch (BookIOException e) {
    			Log.e(TAG, "Error loading the backup file.");
    			Toast.makeText(ImportBackupActivity.this, 
    					R.string.preferences_err_loading_backup, Toast.LENGTH_LONG).show();
    			return;
    		}
    		startQuill();
		}
	};
	
	/**
	 * Start the mail Quill activity
	 * used after import is finished
	 */
	private void startQuill() {
		Intent intent = new Intent(this, FingerWriterActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (file == null) 
			finish();
		else
			handler.postDelayed(importRunnable, 1000);
	}
}
