package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BbsDao;
import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDaoImpl implements BbsDao {

	// 게시판 전체글 가져오기  
	@Override
	public List<BbsDto> getBbsList(int page) {
		String sql =  " SELECT SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT "
				+ " FROM  (SELECT ROWNUM r, SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT "
						+ " FROM  ( SELECT *  "
								+ " FROM BBS "
								+ " WHERE DEL <> 1  "
								+ " ORDER BY WDATE DESC ) ) "
				+ " WHERE ? < r AND r <= ? ";
			
	
	Connection conn = null;	// DB CONNECTION 
	PreparedStatement psmt = null;	// SQL
	ResultSet rs = null;			// RESULT
	
	System.out.println("sql : " + sql ); 	// SQL 확인		
	List<BbsDto> list = new ArrayList<BbsDto>();
	
	try {
		int rowNum = 10;
		conn = DBConnection.getConnection();
		psmt = conn.prepareStatement(sql);
		psmt.setInt(1, (page-1)*rowNum);
		psmt.setInt(2, page * rowNum);
		
		
		rs = psmt.executeQuery();	// select 는 executeQuery, 나머지는 executeUpdate
		// 마지막 페이지일때는 null리턴 
		while(rs.next()) {
			BbsDto dto = new BbsDto(rs.getInt(1),//seq, 
									rs.getString(2),//id,
									rs.getString(3),//title, 
									rs.getString(4),//contents, 
									rs.getString(5),//wdate, 
									rs.getInt(6),//del, 
									rs.getInt(7));//readcount
			list.add(dto);
		}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		DBClose.close(psmt, conn, rs);
	}
	
	return list;
	}

	@Override
	public boolean wirteBbs(String id, String title, String content) {
		/*
		 SEQ NUMBER(8) PRIMARY KEY,
		ID VARCHAR2(30) NOT NULL,
		TITLE VARCHAR2(200) NOT NULL,
		CONTENT VARCHAR2(4000) NOT NULL,
		WDATE DATE NOT NULL,
		DEL NUMBER(1) NOT NULL,
		READCOUNT NUMBER(8) NOT NULL
		 */
		boolean write = false;
		
		String sql =  " INSERT INTO BBS ( SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT ) "
					+ " VALUES ( SEQ_BBS.NEXTVAL, ?, ?, ?, SYSDATE, 0, 0 ) ";
		
		System.out.println("sql : " + sql);

		
		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int count = 0;
		
		
		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, title);
			psmt.setString(3, content);
			
			
			count = psmt.executeUpdate();
			
			if(count>0) {
				write = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		
		return write;
	}

	@Override
	public ArrayList<BbsDto> searchBbs(int choice, String str, int page) {
		// 1. 제목으로 찾기
		ArrayList<BbsDto> searchList = new ArrayList<BbsDto>();
		String sql = "";
		String searchStr = "%" + str.trim() +"%";
		
		/* 페이징되는 쿼리문
		 SELECT SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT
			FROM  (SELECT ROWNUM r, SEQ, ID,TITLE, CONTENT, WDATE, DEL, READCOUNT
					FROM   (SELECT * 
							FROM BBS 
							WHERE DEL <>1 AND CONTENT LIKE '%1%'
							ORDER BY WDATE DESC) ) 
		 
		 */
		sql =" SELECT SEQ, ID, TITLE, CONTENT, WDATE, DEL, READCOUNT "
				+" FROM   (SELECT ROWNUM r, SEQ, ID,TITLE, CONTENT, WDATE, DEL, READCOUNT "
						+" FROM   (SELECT *  "
							   + " FROM BBS ";
		
		if( choice == 1 ) {	// 제목으로 검색
			sql += " WHERE DEL <>1 AND TITLE LIKE ? ";
			
		}
		else if (choice == 2) {	// 내용으로 검색
			sql += " WHERE DEL <>1 AND CONTENT LIKE ? ";
		}
		else if (choice == 3) {	// 작성자로 검색
			sql += " WHERE DEL <>1 AND ID = ? ";
			searchStr = str.trim();
		}
		
		sql += " ORDER BY WDATE DESC) ) "
			 + " WHERE ? < r AND r <= ? ";
		
		System.out.println("sql : " + sql);
		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			// 바꿀부분
			int rowNum = 10;
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, searchStr);
			psmt.setInt(2, (page-1)*rowNum);
			psmt.setInt(3, page * rowNum);
			
			rs = psmt.executeQuery();
			
			// 리스트에 담기
			while(rs.next()) {
				BbsDto dto = new BbsDto();
				dto.setSeq(rs.getInt(1));
				dto.setId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContents(rs.getString(4));
				dto.setWdate(rs.getString(5));
				dto.setDel(rs.getInt(6));
				dto.setReadcount(rs.getInt(7));
				
				searchList.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		
		return searchList;
	}

	@Override
	public boolean readBbs(int sequenceNum) {
		String sql = " UPDATE BBS "
				+" SET READCOUNT = READCOUNT + 1 "
				+" WHERE SEQ = ? ";
	
		System.out.println("sql : " + sql);
		
		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean b = false;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1,sequenceNum);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				b = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		
		return b;
		
	}

	@Override
	public int getSequenceNum(BbsDto dto) {
		int sequence = -1;
		sequence = dto.getSeq();
		return sequence;
	}
	@Override
	public BbsDto getSelectedBbs(int sequenceNum) {
		
		BbsDto selectedDto = new BbsDto();
		String sql = " SELECT * "
					+" FROM BBS "
					+" WHERE SEQ = ? ";
		System.out.println("sql : " + sql);
		
		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, sequenceNum);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				selectedDto.setSeq(rs.getInt(1));
				selectedDto.setId(rs.getString(2)); 
				selectedDto.setTitle(rs.getString(3)); 
				selectedDto.setContents(rs.getString(4));
				selectedDto.setWdate(rs.getString(5));
				selectedDto.setDel(rs.getInt(6));
				selectedDto.setReadcount(rs.getInt(7));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		return selectedDto;
	}

	@Override
	public boolean update(String title, String content, int sequenceNum) {

		String sql = " UPDATE BBS"
				   + " SET TITLE = ? , CONTENT = ?, WDATE = SYSDATE "
				   + " WHERE SEQ = ? ";
		
		System.out.println("sql : " + sql);

		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean b = false;
		int count =0;
		
		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,title);
			psmt.setString(2,content);
			psmt.setInt(3, sequenceNum);
			
			count = psmt.executeUpdate();
			if(count > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		return b;
	}

	@Override
	public boolean deleteBbs(int sequenceNum) {

		String sql = " UPDATE BBS "
				    +" SET DEL = 1 "
					+" WHERE SEQ = ? ";
		System.out.println("sql : " + sql);

		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean b = false;
		int count =0;
		
		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, sequenceNum);
			
			count = psmt.executeUpdate();
			if(count > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		return b;
	}

	
	
	
}
