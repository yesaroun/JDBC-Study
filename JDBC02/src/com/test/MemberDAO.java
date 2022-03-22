/*========================
 	MemberDAO.java
========================*/

// 데이터베이스에 엑세스 하는 기능
// → DBConn 활용(전담 계층)

// 데이터를 입력하는 기능 → insert

// 인원 수를 확인하는 기능
// 즉, 대상 테이블(TBL_MEMBER)의 레코드를 카운팅 기능 → select

// 전체 리스트를 조회하는 기능
// 즉, 대상 테이블(TBL_MEMEBER)의 데이터를 조회하는 기능 → select


package com.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO
{
	// 주요 속성 구성 → DB 연결 객체
	private Connection conn;	//-- getter, setter 필요 없음
	
	// 생성자 정의(사용자 정의 생성자)
	public MemberDAO() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
	}
	
	//메소드 정의 → 데이터를 입력하는 기능 → insert
	public add(MemberDTO dto)
	{
		// 반환할 결과값을 담아낼 변수(적용된 행의 갯수)
		
		// 작업 객체생성
		
		// 쿼리문 준비
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		
		// 사용한 리소스 반납
		
		// 최종 결과값 반환
	}
	
	
}

