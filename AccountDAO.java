package com.bankFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
	Conn gc;
	public int insertAccount(Account account){
		Connection conn = null;
		PreparedStatement pst = null;
		gc = new Conn();
		conn=gc.getConn();
		String sql = "INSERT INTO ACCOUNTS(\"ACCOUNTNUM\", \"PWD\", \"KIND\", \"LOCATIONS\",\"OWNER\") VALUES (?,?,?,?,?)";
		int iv=0;
		try {
			pst=conn.prepareStatement(sql);
			
			pst.setString(1, account.getAccountNum());
			pst.setString(2, account.getPwd());
			pst.setString(3, account.getKind());
			pst.setString(4, account.getLocations());
			pst.setString(5, account.getOwner());
			iv = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insert 중 실패 -sql오류 /"+e.getMessage());
		} finally{
			try {
				pst.close();
				conn.close();
				return iv;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("insert 후 실패 - ?? /"+e.getMessage());
			}
		}
		return 0;
	}
	
	public List<Account> selectAccount(){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		gc = new Conn();
		conn=gc.getConn();
		String sql = "SELECT * FROM ACCOUNTS";
		List<Account> list = null;
		Account account = null;
		try {
			pst=conn.prepareStatement(sql);
			rs =pst.executeQuery();
			list = new ArrayList<Account>();
			while(rs.next()){
				account = new Account();
				account.setAccountNum(rs.getString("accountNum"));
				account.setKind(rs.getString("kind"));
				account.setLocations(rs.getString("locations"));
				account.setOwner(rs.getString("owner"));
				account.setPwd(rs.getString("pwd"));
				account.setRegdate(rs.getString("regdate"));
				list.add(account);
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
	public Account selectAccount(String accountNum){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		gc = new Conn();
		conn=gc.getConn();
		String sql = "SELECT * FROM ACCOUNTS WHERE ACCOUNTNUM = ?";
		List<Account> list = null;
		Account account = null;
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, accountNum);
			rs =pst.executeQuery();
			if(rs.next()){
				account = new Account();
				account.setAccountNum(rs.getString("accountNum"));
				account.setKind(rs.getString("kind"));
				account.setLocations(rs.getString("locations"));
				account.setOwner(rs.getString("owner"));
				account.setPwd(rs.getString("pwd"));
				account.setRegdate(rs.getString("regdate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("select 중에 오류가 발생하였습니다 - sql 오류 /" + e.getMessage());
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				return account;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("select 종료 중에 오류가 발생하였습니다 - ??? /"+e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}
}
