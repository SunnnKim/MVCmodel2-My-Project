package service.impl;

import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import dto.MemberDto;
import service.MemberService;

public class MemberServiceImpl implements MemberService {
	MemberDao mdao = new MemberDaoImpl();

	
	
	@Override
	public boolean getId(String id) {
		boolean b = mdao.getId(id);
		return b;
	}



	@Override
	public boolean addMember(MemberDto dto) {
		return mdao.addMember(dto);
	}

	@Override
	public MemberDto login(String id, String pwd) {
		return mdao.login(id, pwd);
	}
	
	
	

}
