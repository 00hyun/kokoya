package service;

import controller.Controller;
import util.ScanUtil;
import util.View;

public class MypageService {
	
	private static MypageService instance = null;
	private  MypageService() {}
	public static MypageService getInstance() {
		if(instance == null) instance = new MypageService();
		return instance;
	}
	public int mypageHome() {
		
		Controller.mypage = true;
		
		mypage:
		while(true) {
			System.out.println("==================마이페이지==================");
			System.out.println("1. 나의 정보 보기         5. 마일리지 충전");
			System.out.println("2. 예약정보보기");
			System.out.println("3. 장바구니 보기");
			System.out.println("4. 나의 게시글 보기");
			System.out.println("0. 돌아가기");
			
			switch(ScanUtil.nextInt()) {
			case 1:
				return View.MEMBERINFO;
			case 2:
				return View.CLASS_RSVLIST;
			case 3:
				return View.MARKET_CARTLIST;
			case 4:
				return View.ANI_MYANI;
			case 5:
				return View.MEM_MILE;
			case 0:
				Controller.mypage=false;
				break mypage;
			default:
				System.out.println("잘못입력");
			}
			
		}
	
		return View.MAINPAGE;
	}
			
	
	
	

}
