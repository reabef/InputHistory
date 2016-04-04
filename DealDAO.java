package com.bankFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealDAO {

	public List<Deal> selectDeal(Account account) {
		// TODO Auto-generated method stub
		Conn gc = new Conn();
		Deal d = null;
		List<Deal> list = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		conn=gc.getConn();
		String sql = "SELECT * FROM DEALS WHERE FACCOUNTNUM=? ORDER BY TO_NUMBER(DEALNUM)";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, account.getAccountNum());
			rs =pst.executeQuery();
			list = new ArrayList<Deal>();
			while(rs.next()){
				d = new Deal();
				d.setAmount(rs.getInt("amount"));
				d.setBalance(rs.getInt("balance"));
				d.setContent(rs.getString("content"));
				d.setDealdate(rs.getString("dealdate"));
				d.setDealnum(rs.getString("dealnum"));
				d.setFaccountnum(rs.getString("faccountnum"));
				d.setKind(rs.getString("kind"));
				list.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("select 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("select 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Deal selectDeal(String accountNum) {
		//마지막 거래 조회
		// TODO Auto-generated method stub
		Conn gc = new Conn();
		Deal d = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		conn=gc.getConn();
		String sql = "SELECT * FROM (SELECT * FROM DEALS WHERE FACCOUNTNUM=? ORDER BY TO_NUMBER(DEALNUM) DESC) WHERE ROWNUM = 1";
		Account account = null;
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, accountNum);
			rs =pst.executeQuery();
			if(rs.next()){
				d = new Deal();
				d.setAmount(rs.getInt("amount"));
				d.setBalance(rs.getInt("balance"));
				d.setContent(rs.getString("content"));
				d.setDealdate(rs.getString("dealdate"));
				d.setDealnum(rs.getString("dealnum"));
				d.setFaccountnum(rs.getString("faccountnum"));
				d.setKind(rs.getString("kind"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("select 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				return d;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("select 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	public int deDeal(Deal d) {
		// TODO Auto-generated method stub
		Conn gc = new Conn();
		Connection conn = null;
		PreparedStatement pst = null;
		conn=gc.getConn();
		int iv = 0;
		String sql = "INSERT INTO DEALS(dealnum, kind, \"CONTENT\", amount, balance, dealdate, faccountnum)"
				+ "VALUES (LPAD((SELECT DECODE (COUNT(DEALNUM), 0, 1, MAX(DEALNUM)+1) FROM DEALS), 8, 0),"
				+ "'입금','입금',?,?,SYSDATE,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, d.getAmount());
			pst.setInt(2, d.getBalance());
			pst.setString(3, d.getFaccountnum());
			
			iv = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insert 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
		} finally {
			try {
				pst.close();
				conn.close();
				return iv;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("insert 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	public int wiDeal(Deal d) {
		// TODO Auto-generated method stub
		Conn gc = new Conn();
		Connection conn = null;
		PreparedStatement pst = null;
		conn=gc.getConn();
		int iv = 0;
		String sql = "INSERT INTO DEALS(dealnum, kind, \"CONTENT\", amount, balance, dealdate, faccountnum)"
				+ " VALUES (LPAD((SELECT DECODE (COUNT(DEALNUM), 0, 1, MAX(DEALNUM)+1) FROM DEALS), 8, 0),"
				+ "'출금','출금',?,?,SYSDATE,?)";
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, d.getAmount());
			pst.setInt(2, d.getBalance());
			pst.setString(3, d.getFaccountnum());
			
			iv = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insert 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
		} finally {
			try {
				pst.close();
				conn.close();
				return iv;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("insert 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int tfDeal(Deal d, Deal d2) {
		Conn gc = new Conn();
		Connection conn = null;
		PreparedStatement pst = null;
		conn=gc.getConn();
		int iv = 0;
		String sql = "INSERT INTO DEALS(dealnum, kind, \"CONTENT\", amount, balance, dealdate, faccountnum)"
				+ " VALUES (LPAD((SELECT DECODE (COUNT(DEALNUM), 0, 1, MAX(DEALNUM)+1) FROM DEALS), 8, 0),"
				+ "?,?,?,?,SYSDATE,?)";
		try {
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			pst.setString(1, "출금");
			pst.setString(2, "계좌이체 출금");
			pst.setInt(3, d.getAmount());
			pst.setInt(4, d.getBalance());
			pst.setString(5, d.getFaccountnum());
			iv = iv + pst.executeUpdate();
			
			pst.setString(1, "입금");
			pst.setString(2, "계좌이체 입금");
			pst.setInt(3, d2.getAmount());
			pst.setInt(4, d2.getBalance());
			pst.setString(5, d2.getFaccountnum());
			
			iv = iv + pst.executeUpdate();
			if(iv == 2){
				conn.commit();
			}else{
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insert 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				pst.close();
				conn.close();
				return iv;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("insert 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return 0;
	}
}
