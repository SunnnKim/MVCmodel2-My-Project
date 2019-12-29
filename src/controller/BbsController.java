package controller;

import java.util.ArrayList;
import java.util.List;

import dto.BbsDto;
import service.BbsService;
import service.impl.BbsServiceImpl;
import view.BbsListView;
import view.BbsWriteView;
import view.ShowBbsView;
import view.UpdateView;

public class BbsController {
	BbsService bbsService = new BbsServiceImpl();
	
	//TODO: Bbs List뷰로 이동하기
		// Override : 처음 뷰 띄웠을때 화면 
		public void getBbsList() {
			List<BbsDto> list = bbsService.getBbsList(1);
			new BbsListView(list);
		}
		// Override : 페이지 돌릴때의 
		public List<BbsDto> getBbsList(int page) {
			return bbsService.getBbsList(page);
		}
		
	
	//TODO: 글쓰기
	public boolean writeBbs(String id, String title, String content) {
		return bbsService.wirteBbs(id, title, content);
	}
	//글쓰기 뷰 키기
	public void writeBbsView() {
		new BbsWriteView();
	}
	
	// 검색 결과 화면에 띄우기 
	public ArrayList<BbsDto> searchBbs(int choice, String str, int page) {
		return bbsService.searchBbs(choice, str, page);
	}
	
	// 조회수 올리기 
	public boolean readBbs(int sequenceNum) {
		return bbsService.readBbs(sequenceNum);
	}
	// 시퀀스 번호 찾기
	public int getSeqNum(BbsDto dto) {
		return bbsService.getSequenceNum(dto);
	}
	// 글 보기 
	public BbsDto getSelectedBbs(int sequenceNum) {
		return bbsService.getSelectedBbs(sequenceNum);
	}
	// 글보기 뷰 열기
	public void showBbs(int sequenceNum) {
		new ShowBbsView(sequenceNum);
	}
	// 글 업데이트 하기
	public boolean updateBbs(String title, String content, int sequenceNum) {
		return bbsService.update(title, content, sequenceNum);
	}
	// 업데이트 뷰 열기 
	public void showUpdateView(int sequenceNum) {
		new UpdateView(sequenceNum);
	}
	// DB에서 글 지우기 
	public boolean deleteBbs(int sequenceNum) {
		return bbsService.deleteBbs(sequenceNum);
	}
	
	
}
