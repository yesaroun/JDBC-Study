package com.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

public class T2
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Connection conn = DBConn.getConnection();
		
		if(conn != null)
		{
			System.out.println("연결성공");
		}
		
		DBConn.close();
	}
}
