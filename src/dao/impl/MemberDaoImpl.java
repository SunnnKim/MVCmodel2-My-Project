package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.MemberDao;
import db.DBClose;
import db.DBConnection;

public class MemberDaoImpl implements MemberDao {

	
	
	@Override
	public boolean getId(String id) {
		
		String sql = " SELECT COUNT(*) "
				+ " FROM MEMBER "
				+ " WHERE ID = ? ";
	
		Connection conn = null;	// DB CONNECTION 
		PreparedStatement psmt = null;	// SQL
		ResultSet rs = null;			// RESULT
		
		boolean findid = false;
		System.out.println("sql : " + sql ); 	// SQL 확인		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			// 아이디가 있으면 true 반환
			int count = 0;
			if(rs.next()) count = rs.getInt("COUNT(*)");
			System.out.println("count: " + count);
			System.out.println("findid: "+findid);
			if(count > 0) findid = true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
			
		}
		
		return findid;
		
	}

}
