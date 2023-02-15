package service;

import util.ScanUtil;
import util.View;

public class AdminService {

	
	private static AdminService instance = null;
	private AdminService() {}
	public static AdminService getInstance() {
		if(instance == null) instance = new AdminService();
		
		return instance;
	}
	public int adminPage() {
		
		while(true) {
			System.out.println(" =====================관리자 페이지===================== ");
			
			System.out.println(" 1. 상품 관리");
			System.out.println(" 2. 클래스 관리");
			System.out.println(" 3. 게시글 관리");
			System.out.println(" 4. 로그아웃 ");
			
			switch(ScanUtil.nextInt()) {
			
			case 1:
				break;
			case 2:
				return View.ADMIN_ANIGRAM;
			case 3:
				break;
			case 4:
				return View.HOME;
			default:
				System.out.println("잘못 입력");
				break;
			}
			
		}
		
	}
	public int adminAnimalgram() {
		
		System.out.println("================클래스 관리창=================");
		System.out.println("");
		
		
		
		
		return 0;
	}
}
