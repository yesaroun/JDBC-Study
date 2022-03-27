package com.test;

public class Process
{
	private MemberDAO dao;
	
	public Process()
	{
		dao = new MemberDAO();
	}
	
	public void memeberInsert()
	{
		dao.connection();
		
		
	}
	
	
	public void memeberList()
	{
		System.out.println();
	}
}
