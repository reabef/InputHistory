package com.bankFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	public Connection getConn() {
		// TODO Auto-generated constructor stub
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String pwd = "951236";
		Connection conn = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("드라이버 로드에 실패하셨습니다.");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("연결에 실패하였습니다. - 주소와 아이디, 비밀번호를 확인해주세요");
			e.printStackTrace();
		}
		return conn;
	}
}
