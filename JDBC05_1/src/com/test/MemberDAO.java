package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import com.util.DBConn;

public class MemberDAO
{
	private Connection conn;
	
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;	
	}
	
	// 전체 갯수
	public int count() throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) AS COUNT  FROM TBL_EMP";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			result = rs.getInt("COUNT");
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	//-- 입력
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String  sql = String.format("INSERT INTO TBL_EMP(EMP_ID, EMP_NAME, SSN, IBSADATE"
				                  + ", CITY_ID, TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
				                  + " VALUES(EMPSEQ.NEXTVAL, '%s', '%s', '%s'"
				                  + ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s')"
				                  + ", '%s'"
				                  + ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
				                  + ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
				                  + ", %d, %d)", dto.getName(), dto.getSsn(), dto.getIbsaDate(), dto.getCity()
				                  , dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicPay(), dto.getSudang());
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		return result;
	}
	
	//-- 출력
	//(이거 switch문으로 받은다음 숫자 바꾸기)
	public ArrayList<MemberDTO> order(int num) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
								 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, NVL(E.SUDANG, 0) AS SUDANG"
								 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
								 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
								 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
								 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID ORDER BY %d", num);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// desc 함수 하나 추가하기
	public ArrayList<MemberDTO> orderDESC(int num) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
								 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY, NVL(E.SUDANG, 0) AS SUDANG"
								 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
								 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
								 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
								 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID ORDER BY %d DESC", num);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		rs.close();
		stmt.close();
		
		return result;
	}	
	
	// 검색(사번, 이름)
	public ArrayList<MemberDTO> search(int num) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
				                 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY"
				                 + ", NVL(E.SUDANG, 0) AS SUDANG"
				                 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
				                 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
				                 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
				                 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
				                 + " WHERE E.EMP_ID = %d ORDER BY 1", num);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	public ArrayList<MemberDTO> search(String name) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
				                 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY"
				                 + ", NVL(E.SUDANG, 0) AS SUDANG"
				                 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
				                 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
				                 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
				                 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
				                 + " E.EMP_NAME = '%s' ORDER BY 1", name);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 검색 (부서) (수정해야함)
	public ArrayList<MemberDTO> searchBuseo(String buseo) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
				                 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY"
				                 + ", NVL(E.SUDANG, 0) AS SUDANG"
				                 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
				                 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
				                 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
				                 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
				                 + " WHERE B.BUSEO_NAME = '%s' ORDER BY 1", buseo);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 검색 (직위)
	public ArrayList<MemberDTO> searchJikwi(String jikwi) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT E.EMP_ID, E.EMP_NAME, E.SSN, E.IBSADATE, C.CITY_NAME"
				                 + ", E.TEL, B.BUSEO_NAME, J.JIKWI_NAME, E.BASICPAY"
				                 + ", NVL(E.SUDANG, 0) AS SUDANG"
				                 + ", (E.BASICPAY+NVL(E.SUDANG, 0)) AS PAY"
				                 + " FROM TBL_EMP E LEFT JOIN TBL_CITY C ON E.CITY_ID = C.CITY_ID"
				                 + " LEFT JOIN TBL_BUSEO B ON E.BUSEO_ID = B.BUSEO_ID"
				                 + " LEFT JOIN TBL_JIKWI J ON E.JIKWI_ID = J.JIKWI_ID"
				                 + " WHERE J.JIKWI_NAME = '%s' ORDER BY 1", jikwi);
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setId(rs.getString("E.EMP_ID"));
			dto.setName(rs.getString("E.EMP_NAME"));
			dto.setSsn(rs.getString("E.SSN"));
			dto.setIbsaDate(rs.getString("E.IBSADATE"));
			dto.setCity(rs.getString("C.CITY_NAME"));
			dto.setTel(rs.getString("E.TEL"));
			dto.setBuseo(rs.getString("B.BUSEO_NAME"));
			dto.setJikwi(rs.getString("J.JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("E.BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 수정  (날짜 데이터 생각해보기!)
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		String sql = String.format("UPDATE TBL_EMP SET EMP_NAME = '%s', SSN = '%s', IBSADATE = '%s'"
				                 + ", CITY_ID = (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s'), TEL = '%s'"
				                 + ", BUSEO_ID = (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
				                 + ", JIKWI_ID = (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
				                 + ", BASICPAY = %d, SUDANG = 3000000 WHERE EMP_ID = %s", dto.getId()
				                    , dto.getSsn(), dto.getIbsaDate(), dto.getCity(), dto.getTel()
				                    , dto.getBuseo(), dto.getJikwi(), dto.getBasicPay(), dto.getSudang(), dto.getId());
		result = stmt.executeUpdate(sql);
		
		stmt.close();	
		return result;
	}
	
	public int remove(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID = %s", dto.getId());
		result = stmt.executeUpdate(sql);
		
		stmt.close();
		return result;
	}
}



















