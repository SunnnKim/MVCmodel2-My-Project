package controller;

import java.util.List;

import dto.BbsDto;
import dto.MemberDto;
import service.MemberService;
import service.impl.MemberServiceImpl;
import view.BbsListView;
import view.JoinView;
import view.LoginView;

public class MemberController {
	
	MemberService memService = new MemberServiceImpl();
	
	
	// TODO: login view 보기
	public void loginView() {
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
	public boolean addMember(MemberDto dto) {
		return memService.addMember(dto);
	}
	
	//TODO: 로그인 아이디 확인하기 
	public MemberDto login(String id, String pwd) {
		return memService.login(id, pwd);
	}
	

	
}
