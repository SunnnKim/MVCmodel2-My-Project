package memberController;

import service.MemberService;
import service.impl.MemberServiceImpl;
import view.JoinView;
import view.LoginView;

public class MemberController {
	MemberService memService = new MemberServiceImpl();
	

	
	
	// TODO: login view 보기
	public void login() {
		new LoginView();
	}
	
	// TODO: join view 보기
	public void join() {
		new JoinView();
	}
	
	// TODO: 아이디 찾기
	public boolean idCheck(String id) {
		return memService.getId(id);
	}
	
	//TODO: 회원가입 완료하기
	
	
}
