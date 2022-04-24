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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	public int add(MemberDTO dto) throws SQLException
	{
		// 반환할 결과값을 담아낼 변수(적용된 행의 갯수)
		int result = 0;
		
		// 작업 객체생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비(insert)
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
								 + " VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')", dto.getName(), dto.getTel());
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		result = stmt.executeUpdate(sql);
		
		// 사용한 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환
		return result;
	}//end add()
	
	//메소드 정의 → 전체 인원 수 확인하는 기능 → select
	public int count() throws SQLException
	{
		// 결과값으로 반환하게 될 변수 선언 및 초기화
		int result = 0;
		
		// 작업 객체생성
		Statement stmt = conn.createStatement();
		
		//쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT   FROM TBL_MEMBER";	//(연산들어가 있으면 꼭 별칭주기)
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select → executeQuery() → ResultSet 반환 → 일반적으로 반복문 구성을 통한 ResultSet 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성 → 결과값 수신
		while (rs.next())					// if(rs.next) (이 상황은 if 사용해도 상관없다)
		{
			result = rs.getInt("COUNT");	// rs.getInt(1); (이렇게도 가능/ 다만 여기서 명시하는 숫자는 이 테이블의 컬럼 인덱스가 아니라 select 하는 컬럼 인덱스다)
											// ※ 컬럼 인덱스는 1부터...
		}
		// 리소스 반납
		rs.close();
		stmt.close(); //(반납순서는 오픈을 a,b,c 했으면 닫는걸 c,b,a 이렇게 해야함)
		
		// 최종 결과값 반환
		return result;
		
	} // end count()

	// 메소드 정의 → 전체 리스트를 조회하는 기능 → select
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		// 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		//작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select
		String sql = "SELECT SID, NAME, TEL  FROM TBL_MEMBER ORDER BY SID";
		
		// 생성된 작업 객체를 활용하여 퀄문 실행 → select → executeQuery() → ResultSet 반환 → 일반적으로 반복 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 일반적 반복문 활용
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setTel(rs.getString("TEL"));
			
			result.add(dto);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	} // end lists()
	
	//-- (여기서는 싱글톤이여서 만들어도 되고 안만들어도 된다.)
	public void close() throws SQLException
	{
		// 주의 check~!!!
		// conn.close(); (이거하면 안됨)
		
		DBConn.close();
	}
	
	
}// end class MemberDAO()

