/*==================================
 	MemberProcess.java
 	- 콘솔 기반 서브 
==================================*/
package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	// 주요 속성 구성
	private MemberDAO dao;
	
	// 생성자 정의
	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	// 직원 정보 입력 메소드 정의
	public void memberInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			//데이터베이스 연결
			dao.connection();
			
			// 지역 리스트 구성
			ArrayList<String> citys = dao.searchCity();
			StringBuilder cityStr = new StringBuilder();
			for (String city : citys)
				cityStr.append(city + "/");
			
			
			// 부서 리스트 구성
			ArrayList<String> buseos = dao.searchBuseo();
			StringBuilder buseoStr = new StringBuilder();
			for (String buseo : buseos)
				buseoStr.append(buseo + "/");
			
			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			StringBuilder jikwiStr = new StringBuilder();
			for (String jikwi : jikwis)
				jikwiStr.append(jikwi + "/");
			
			// 사용자에게 보여지는 화면 처리
			/*
			직원 정보 입력-----------------------------------------------
			이름 : 김정용
			주민등록번호(yymmdd-nnnnnnn) : 960608-2234567
			입사일(yyyy-mm-dd) : 2019-06-08
			지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북) : 경기
			전화번호 : 010-2731-3153
			부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부) : 개발부
			직위(사장/전무/상무/이사/부장/차장/과장/대리/사원) : 대리
			기본급(최소 1800000 이상) : 5000000
			수당 : 2000000
			*/
			System.out.println();
			System.out.println("직원 정보 입력-----------------------------------------------");
			System.out.print("이름 : ");
			String empName = sc.next();
			
			System.out.print("주민등록번호(yymmdd-nnnnnnn): ");
			String ssn = sc.next();
			
			System.out.print("입사일(yyyy-mm-dd): ");
			String ibsaDate = sc.next();
			
			System.out.printf("지역(%s) : ", cityStr.toString());
			String cityName = sc.next();

			System.out.print("전화번호 : ");
			String tel = sc.next();

			System.out.printf("부서(%s) : ", buseoStr.toString());
			String buseoName = sc.next();		
			
			System.out.printf("직위(%s) : ", jikwiStr.toString());
			String jikwiName = sc.next();

			System.out.printf("기본급(최소 %d원 이상) : ", dao.searchBasicPay(jikwiName));
			int basicPay = sc.nextInt();
			
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			System.out.println();
			
			// MemberDTO 구성
			MemberDTO dto = new MemberDTO();
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityName(cityName);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicPay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			if (result > 0)
			{
				System.out.println("직원 정보 입력 완료~!!!");				
			}
			System.out.println("-----------------------------------------------직원 정보 입력");

		} 
		catch (Exception e)
		{
			System.out.println(e.toString());;
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}// end memberInsert()
	
	// 직원 전체 출력 메소드 정의
	public void membeerSists()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력
		System.out.println();
		System.out.println("1. 사번 정렬");				// EMP_ID
		System.out.println("2. 이름 정렬");				// EMP_NAME
		System.out.println("3. 부서 정렬");				// BUSEO_NAME
		System.out.println("4. 직위 정렬");				// JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬");	// PAY DESC
		System.out.print(">> 선택(1~5, -1 종료): ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			if(menu == -1)
				return;
			
			String key = "";
			switch (menu)
			{
			case 1:
				key="EMP_ID";
				break;
			case 2:
				key="EMP_NAME";
				break;
			case 3:
				key="BUSEO_NAME";
				break;
			case 4:
				key="JIKWI_NAME";
				break;
			case 5:
				key="PAY DESC";
				break;
			}
			
			// 데이터베이스 연결
			dao.connection();
			
			// 직원 리스트 출력
			System.out.println();
			System.out.printf("전체 인원: %d명\n", dao.memberCount());
			System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
			ArrayList<MemberDTO> memList = dao.lists(key);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\n"
						, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
						, dto.getBasicPay(), dto.getSudang(), dto.getPay());
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
				
			}
			
		}
	}//end memberLists();
	
	// 직원 검색 출력 메소드 정의
	public void memberSearch()
	{
		// 인스턴스 생성
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 구성 → 1:사번, 2:이름, 3:부서, 4:직위
		System.out.println();
		System.out.println("1. 사번 검색");
		System.out.println("2. 이름 검색");
		System.out.println("3. 부서 검색");
		System.out.println("4. 직위 검색");
		System.out.print(">> 선택(1~4, -1 종료): ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr); // (여기서 문자가 올바르지 않으면 예외 잡을 것이다)
			if (menu == -1)
				return;
			
			// 사용자로부터 카와 값 입력 받기
			String key = "";
			String value = "";
			
			switch (menu)
			{
			case 1:
				System.out.println("사번을 입력하세요 : ");
				key="EMP_ID";
				break;
			case 2:
				System.out.println("이름을 입력하세요 : ");
				key="EMP_NAME";
				break;
			case 3:
				System.out.println("부서를 입력하세요 : ");
				key="BUSEO_NAME";
				break;
			case 4:
				System.out.println("직위를 입력하세요 : ");
				key="JIKWI_NAME";
				break;
			}
			value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			// 검색 결과 출력
			System.out.println();
			System.out.printf("검색 인원 : %d\n", dao.memberCount(key, value));
			
			System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
			ArrayList<MemberDTO> memList = dao.searchLists(key, value);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\\n"
							     , dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							     , dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
							     , dto.getBasicPay(), dto.getSudang(), dto.getPay());
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}//end memberSearch()
	
	// 직원 정보 수정 메소드 정의
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			//수정할 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력: ");
			String value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			ArrayList<MemberDTO> memList = dao.searchLists("EMP_ID", value);
			
			if(memList.size() > 0)
			{
				// 수정 대상을 찾은경우
				
				// 지역 리스트 구성
				ArrayList<String> citys = dao.searchCity();
				StringBuilder cityStr = new StringBuilder();
				for (String city : citys)
					cityStr.append(city + "/");
				
				
				// 부서 리스트 구성
				ArrayList<String> buseos = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
					buseoStr.append(buseo + "/");
				
				// 직위 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
					jikwiStr.append(jikwi + "/");
				
				// 사용자에게 보여지는 화면 처리
				/*
				직원 정보 수정-----------------------------------------------
				기존 이름 : 김정용
				수정 이름 : -
				기존 주민등록번호(yymmdd-nnnnnnn) : 960608-2234567
				수정 주민등록번호(yymmdd-nnnnnnn) :
				기존 입사일(yyyy-mm-dd) : 2019-06-08
				수정 입사일(yyyy-mm-dd) :
				기존 지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북) : 경기
				수정 지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북) : 
				기존 전화번호 : 010-2731-3153
				수정 전화번호 : 
				기존 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부) : 개발부
				수정 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부) :
				기존 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원) : 대리
				수정 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원) : 
				기존 기본급(최소 1800000 이상) : 5000000
				수정 기본급(최소 1800000 이상) :
				기존 수당 : 2000000
				수정 수당 : 
				
				직원 정보 수정 완료
				-----------------------------------------------직원 정보 수정
				*/
				
				MemberDTO mList = memList.get(0);
				int mEmpId = mList.getEmpId();
				String mEmpName = mList.getEmpName();
				String mSsn = mList.getSsn();
				String mIbsaDate = mList.getIbsaDate();
				String mCityName = mList.getCityName();
				String mTel = mList.getTel();
				String mBuseoName = mList.getBuseoName();
				String mJikwiName = mList.getJikwiName();
				int mBasicPay = mList.getBasicPay();
				int mSudang = mList.getSudang();
				
				System.out.println();
				System.out.println("직원 정보 수정-----------------------------------------------");
				System.out.printf("기존 이름 : %s", mEmpName);
				System.out.println("수정 이름 : ");
				String empName = sc.next();
				if(empName.equals("-"))
					empName = mEmpName;
				
				
				System.out.printf("기존 주민등록번호(yymmdd-nnnnnnn) : %s", mSsn);
				System.out.println("수정 주민등록번호(yymmdd-nnnnnnn) :");
				String ssn = sc.next();
				if(ssn.equals("-"))
					ssn = mSsn;
				
				System.out.printf("기존 입사일(yyyy-mm-dd) : %s", mIbsaDate);
				System.out.println("수정 입사일(yyyy-mm-dd) :");
				String ibsaDate = sc.next();
				if(ibsaDate.equals("-"))
					ibsaDate = mIbsaDate;
				
				System.out.printf("기존 지역(%s) : %s", cityStr.toString(), mCityName);
				System.out.printf("수정 지역(%s) : ", cityStr.toString());
				String cityName = sc.next();
				if(cityName.equals("-"))
					cityName = mCityName;
				
				System.out.printf("기존 전화번호 : %s", mTel);
				System.out.println("수정 전화번호 : ");
				String tel = sc.next();
				if(tel.equals("-"))
					tel = mTel;
				
				System.out.printf("기존 부서(%s) : %s", buseoStr.toString(), mBuseoName);
				System.out.printf("수정 부서(%s) :", buseoStr);
				String buseoName = sc.next();
				if(buseoName.equals("-"))
					buseoName = mBuseoName;
				
				System.out.printf("기존 직위(%s) : %s", jikwiStr.toString(), mJikwiName);
				System.out.printf("수정 직위(%s) : ", jikwiStr.toString());
				String jikwiName = sc.next();
				if(jikwiName.equals("-"))
					jikwiName = mJikwiName;
				
				
				System.out.printf("기존 기본급(최소 1800000 이상) : %d", mBasicPay);
				System.out.printf("수정 기본급(최소 %d 이상) :", dao.searchBasicPay(jikwiName)); //(직위가 수정되어서 다시 조회하는거임)
				String basicPayStr = sc.next();
				int basicPay;
				if(basicPayStr.equals("-"))
					basicPay = mBasicPay;
				else
					basicPay = Integer.parseInt(basicPayStr);
				
				System.out.printf("기존 수당 : %d", mSudang);
				System.out.println("수정 수당 : ");
				String sudangStr = sc.next();
				int sudang;
				if(sudangStr.equals("-"))
					sudang = mSudang;
				else
					sudang = Integer.parseInt(sudangStr);
				
				// MemberDTO 구성
				MemberDTO dto = new MemberDTO();
				dto.setEmpId(mEmpId);
				dto.setEmpName(empName);
				dto.setSsn(ssn);
				dto.setIbsaDate(ibsaDate);
				dto.setCityName(cityName);
				dto.setTel(tel);
				dto.setBuseoName(buseoName);
				dto.setJikwiName(jikwiName);
				dto.setBasicPay(basicPay);
				dto.setSudang(sudang);		
				
				// 회원 정보 수정 메소드 실행
				int result = dao.modify(dto);
				
				System.out.println("");
				if (result > 0)
					System.out.println("직원 정보 수정 완료");
				System.out.println("-----------------------------------------------직원 정보 수정");

				
			}
			else
			{
				System.out.println("수정 대상을 검색하지 못했습니다.");
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}//end memberUpdate()
	
	// 직원 정보 삭제 메소드 정의 
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("삭제할 직원의 사원번호 입력: ");
			String value = sc.next();			// (실무에서는 이렇게 문자형을 받고 이를 숫자로 바꾸는 경우가 더 많다. 왜냐하면 실수로 받았는 적합하지 않은 경우가 많아서)
			
			// 직원 정보 확인 후 삭제 여부 결정
			dao.connection();
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				System.out.println();
				System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
				
				for(MemberDTO dto : members)
				{
					System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\n"
							, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
							, dto.getBasicPay(), dto.getSudang(), dto.getPay());
				}
				System.out.println("\n정말 삭제하시겠습니까?(Y/N) : ");
				String response = sc.next();
				if(response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
						System.out.println("직원 정보가 정상적으로 삭제되었습니다.");
				}
			} else
			{
				System.out.println("삭제 대상을 찾지 못했습니다.");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}// end memberDelete()
}
