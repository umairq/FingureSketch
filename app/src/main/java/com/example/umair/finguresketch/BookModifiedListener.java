package com.example.umair.finguresketch;

import name.vbraun.view.write.Page;

public interface BookModifiedListener {
	public void onPageInsertListener(Page page, int position);
	public void onPageDeleteListener(Page page, int position);
}
