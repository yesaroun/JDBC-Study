// DTO : GETTER, SETTER (속성만)
package com.test;


public class MemberDTOm	
{
	private static int sid;
	private static String name;
	private static String tel;

	public static int getSid()
	{
		return sid;
	}
	public static void setSid(int num)
	{
		MemberDTOm.sid = num;
	}
	public static String getName()
	{
		return name;
	}
	public static void setName(String name)
	{
		MemberDTOm.name = name;
	}
	public static String getTel()
	{
		return tel;
	}
	public static void setTel(String tel)
	{
		MemberDTOm.tel = tel;
	}
	
}
