package singleton;

import controller.BbsController;
import controller.MemberController;
import dto.MemberDto;
import view.LoginView;

public class Singleton {
	private static Singleton s = null;
	public MemberController memCtrl = null;
	public BbsController bbsCtrl = null;
	
	public String loginId;	// 현재로그인한 아이디정보를 가지고 있음
	private MemberDto loginDto;
	
	
	public MemberDto getLoginDto() {
		return loginDto;
	}
	public void setLoginDto(MemberDto loginDto) {
		this.loginDto = loginDto;
		this.loginId = loginDto.getId();
	}
	
	
	
	
	private Singleton() {
		memCtrl = new MemberController();
		bbsCtrl = new BbsController();
	}
	public static Singleton getInstance() {
		if(s == null ) {
			s = new Singleton();
		}
		return s;
	}
	
	
	
	
}
