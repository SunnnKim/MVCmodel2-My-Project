package service.impl;

import dao.MemberDao;
import dao.impl.MemberDaoImpl;
import service.MemberService;

public class MemberServiceImpl implements MemberService {
	MemberDao mdao = new MemberDaoImpl();

	
	
	@Override
	public boolean getId(String id) {
		boolean b = mdao.getId(id);
		return b;
	}
	
	

}
