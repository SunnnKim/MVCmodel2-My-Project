package dao;

import java.util.ArrayList;
import java.util.List;

import dto.BbsDto;

public interface BbsDao {
	public List<BbsDto> getBbsList(int page);	// 전체 게시판 글 뿌리기 
	public boolean wirteBbs(String id, String title, String content); // 글쓰기 
	public ArrayList<BbsDto> searchBbs(int choice, String str, int page); // 검색하기 
	public boolean readBbs(int sequenceNum);	// 조회수 올리기 
	public int getSequenceNum(BbsDto dto);		// 시퀀스 번호 찾는 함수 
	public BbsDto getSelectedBbs(int sequenceNum);	// 클릭한 글 열기 
	public boolean update(String title, String content, int sequenceNum);
	public boolean deleteBbs(int sequenceNum);
}
