package com.bankFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
	Conn gc = new Conn();
	public Members selMember(String mid){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM MEMBERS WHERE UPPER(MID) = ?";
		conn=gc.getConn();
		Members m = null;
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, mid);
			rs=pst.executeQuery();
			if(rs.next()){
				m  = new Members();
				m.setMid(rs.getString("MID"));
				m.setPwd(rs.getString("PWD"));
				m.setName(rs.getString("NAME"));
				m.setGender(rs.getString("GENDER"));
				m.setAge(rs.getShort("AGE"));
				m.setBirthday(rs.getString("BIRTHDAY"));
				m.setPhone(rs.getString("PHONE"));
				m.setRegdate(rs.getString("REGDATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				return m;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
