// DAO : // 액션 처리하는 아이들만
package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class MemberDAOm
{
	static void input() throws SQLException, ClassNotFoundException 
	{
		Scanner sc = new Scanner(System.in);
		
		Connection conn = DBConn.getConnection();
		
		Statement stmt = conn.createStatement();
			
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		ResultSet rs = stmt.executeQuery(sql);
		
		int res = 0;
		
		while(rs.next())
		{
			res = rs.getInt("COUNT");
			
		}
		
			do
			{	
				++res;
				MemberDTOm.setSid(res);
			
				System.out.printf("이름 전화번호 입력(%d) : ", MemberDTOm.getSid());
				MemberDTOm.setName(sc.next());
				
				if(MemberDTOm.getName().equals("."))
					break;
				
				MemberDTOm.setTel(sc.next());
				
				if(conn != null) 
				{
					System.out.println("O");
					
					try
					{						
						String sql2 = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(%d, '%s', '%s')"
								, MemberDTOm.getSid(), MemberDTOm.getName(), MemberDTOm.getTel());
						
						int result = stmt.executeUpdate(sql2);
						
						if(result > 0)
							System.out.println("회원 정보 입력 완료~!!");
						
					} catch (Exception e)
					{
						System.out.println(e.toString());
					}
				}
				else 
				{
					System.out.println("X");
				}
				
			} while(true);

		
		sc.close();
		DBConn.close();
	}
	
	public static void output() throws ClassNotFoundException, SQLException
	{
		Connection conn = DBConn.getConnection();
		
		if(conn != null)
		{
			System.out.println("O");
			
			try
			{
				Statement stmt = conn.createStatement();
				
				String sql = "SELECT SID, NAME, TEL  FROM TBL_MEMBER ORDER BY SID";
				
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next())
				{
					String sid = rs.getString("SID");
					String name = rs.getString("NAME");
					String tel = rs.getString("TEL");
					
					String str = String.format("%3s %8s %12s", sid, name, tel);
					
					System.out.println(str);
				}
				
				rs.close();
				stmt.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		else
		{
			System.out.println("X");
		}
		
		DBConn.close();
		
	}
}
