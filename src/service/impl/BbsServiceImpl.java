package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.BbsDao;
import dao.impl.BbsDaoImpl;
import dto.BbsDto;
import service.BbsService;

public class BbsServiceImpl implements BbsService {
	BbsDao dao = new BbsDaoImpl();

	@Override
	public List<BbsDto> getBbsList(int page) {
		return dao.getBbsList(page);
	}

	@Override
	public boolean wirteBbs(String id, String title, String content) {
		return dao.wirteBbs(id, title, content);
	}

	@Override
	public int getSequenceNum(BbsDto dto) {
		return dao.getSequenceNum(dto);
	}
	@Override
	public ArrayList<BbsDto> searchBbs(int choice, String str, int page) {
		return dao.searchBbs(choice, str, page);
	}

	@Override
	public boolean readBbs(int sequenceNum) {
		return dao.readBbs(sequenceNum);
	}
	@Override
	public BbsDto getSelectedBbs(int sequenceNum) {
		return dao.getSelectedBbs(sequenceNum);
	}

	@Override
	public boolean update(String title, String content, int sequenceNum) {
		return dao.update(title, content, sequenceNum);
	}

	@Override
	public boolean deleteBbs(int sequenceNum) {
		return dao.deleteBbs(sequenceNum);
	}
	
	
}
