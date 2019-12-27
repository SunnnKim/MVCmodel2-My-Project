package singleton;

import memberController.MemberController;
import view.LoginView;

public class Singleton {
	private static Singleton s = null;
	public MemberController memCtrl = null;
	
	
	private Singleton() {
		memCtrl = new MemberController();
	}
	public static Singleton getInstance() {
		if(s == null ) {
			s = new Singleton();
		}
		return s;
	}
	
	
	
	
}
