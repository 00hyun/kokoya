package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.ClassDAO;
import util.ScanUtil;
import util.View;

public class ClassService { 
	public static ClassService instance = null;
	private ClassService() {}
	public static ClassService getInstance() {
		if(instance == null) instance = new ClassService();
		return instance;
	}  //싱글톤
	
	ClassDAO dao = ClassDAO.getInstance();
	
	
	//Class 테이블 컬럼명: CLASS_CODE, CALSS_TITLE, CLASS_TRAINER, PET_TYPE, CLASS_CONTENT, CLASS_DATE
	
	

	
	
	
	
	
	public int list() {
		clearScreen();
		System.out.println("==================================== 클래스 =======================================");
		System.out.println("순번\t강의코드\t강의명\t\t\t강사\t\t동물종류\t수강일");
		System.out.println("------------------------------------------------------------------------------------");
		List<Map<String, Object>> list = dao.list();
		for(Map<String, Object>item : list) {

			System.out.print(item.get("CLASS_NUM"));
			System.out.print("\t"+item.get("CLASS_CODE"));
			System.out.print("\t\t"+item.get("CLASS_TITLE"));
			System.out.print("\t"+item.get("CLASS_TRAINER"));
			System.out.print("\t\t"+item.get("PET_TYPE"));
			System.out.print("\t\t"+item.get("CLASS_DATE"));
			System.out.println();	
		}
		System.out.println("====================================================================================");
		while(true) {
			System.out.println("1. 상세정보   2. 예약하기   3. 예약내역 확인   0. 이전화면");
			System.out.println("선택 >> ");
			switch(ScanUtil.nextInt()) {
				case 1: return View.CLASS_DETAIL;
				case 2: return View.CLASS_RSV;
				case 3: return View.CLASS_RSVLIST;
				case 0: return View.MAINPAGE;
				default: System.out.println("잘못된 입력입니다"); 
			}
		}
		

	}
	public int detail() {
		System.out.println("순번입력 >>");
		List<Object> param = new ArrayList();
		param.add(ScanUtil.nextInt());
		Map<String, Object> detail = dao.detail(param);
		clearScreen();
		System.out.println("==============================================");
		System.out.println("강의명 : " + detail.get("CLASS_TITLE"));
		System.out.println("강사 : " + detail.get("CLASS_TRAINER"));
		System.out.println("동물종류 : " + detail.get("PET_TYPE"));
		System.out.println("강의내용 : " + detail.get("CLASS_CONTENT"));
		System.out.println("수강일 : " + detail.get("CLASS_DATE"));
		System.out.println("==============================================");
		while(true) {
			System.out.println("1. 예약하기      2. 이전화면");
			switch(ScanUtil.nextInt()) {
				case 1: 
						param.add(Controller.loginInfo.get("MEM_ID"));
						List<Map<String, Object>> check = dao.check(param);
						if(check == null) {
							int result =  dao.rsv(param);
							if(result == 0) {
								System.out.println("\n\t예약 실패\t\n");
								return View.CLASS_LIST;
							}else {
								System.out.println("\n\t"+Controller.loginInfo.get("MEM_ID") + " 님! " + detail.get("CLASS_TITLE") + " 예약이 완료되었습니다!\t\n");
								return View.CLASS_LIST;
							}
						}else {
							System.out.println();
							System.out.println("이미 신청된 강의입니다");
							System.out.println();
							return View.CLASS_LIST;
						}
					
				case 2: return View.CLASS_LIST;
				default: System.out.println("잘못된 입력입니다");
				}
			}
	}
	public int rsv() {
		System.out.println("순번입력 >>");
		List<Object> param = new ArrayList();
		param.add(ScanUtil.nextInt());
		param.add(Controller.loginInfo.get("MEM_ID"));
		List<Map<String, Object>> check = dao.check(param);
		if(check == null) {
			int result = dao.rsv(param);
			if(result > 0 ) {
				System.out.println("\n\t"+Controller.loginInfo.get("MEM_ID") + " 님! 예약이 완료되었습니다!\t\n");
				return View.CLASS_LIST;
			}else {
				System.out.println("\n\t예약실패\t\n");
				return View.CLASS_LIST;
			}
		}else {
			System.out.println();
			System.out.println("이미 신청된 강의입니다");
			System.out.println();
			return View.CLASS_LIST;
		}
	}
	
	public int rsvlist() {
		clearScreen();
		System.out.println("============================= "+ Controller.loginInfo.get("MEM_ID") +" 님 예약 내역 ==================================");
		System.out.println("순번\t강의코드\t강의명\t\t\t강사\t\t동물종류\t수강일");
		System.out.println("---------------------------------------------------------------------------------");
		List<Object> param = new ArrayList();
		param.add(Controller.loginInfo.get("MEM_ID"));
		List<Map<String, Object>> rsvlist = dao.rsvlist(param);
		if(rsvlist == null) {
			System.out.println();
			System.out.println("예약 내역이 없습니다");
			System.out.println();
			return View.CLASS_LIST;
		}else {
		for(Map<String, Object>item : rsvlist) {
			System.out.print(item.get("CLASS_NUM"));
			System.out.print("\t"+item.get("CLASS_CODE"));
			System.out.print("\t\t"+item.get("CLASS_TITLE"));
			System.out.print("\t"+item.get("CLASS_TRAINER"));
			System.out.print("\t\t"+item.get("PET_TYPE"));
			System.out.print("\t\t"+item.get("CLASS_DATE"));
			System.out.println();	
		}
		System.out.println("=================================================================================");
				while(true) {
					System.out.println("1. 예약취소   0. 이전화면");
					switch(ScanUtil.nextInt()) {
					case 1: 
						System.out.print("순번입력 >> ");
						param.add(ScanUtil.nextLine());
						int result = dao.delete(param);
						if(result > 0) {
							System.out.println("\n 삭제되었습니다 \n");
						}else {
							System.out.println("\n 삭제 실패 \n");
						}
	
					case 0: return View.CLASS_LIST;
					}
				}
			}
		}
				
	public static void clearScreen() {
		for(int i = 0 ; i < 45; i++) {
			System.out.println();
		}
	}
	
}
