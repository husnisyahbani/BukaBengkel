package com.multimediainformatika.bukabengkel;

import android.app.Application;

import com.multimediainformatika.bukabengkel.database.Database;

public class AppMain extends Application {
	private Database database;

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		database = new Database(this);
		database.open();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		database.close();
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}



}
