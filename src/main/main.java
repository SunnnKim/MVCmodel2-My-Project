package main;

import db.DBConnection;
import singleton.Singleton;

public class main {

	public static void main(String[] args) {
		
		
		// DB 연결
		DBConnection.initConnection();
		Singleton s = Singleton.getInstance();
		s.memCtrl.login();
		
	}

}
