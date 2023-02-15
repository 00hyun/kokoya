package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.MemberDAO;
import util.ScanUtil;
import util.View;

public class MemberService {

	MemberDAO dao = MemberDAO.getInstance();
	
	private static MemberService instance = null;
	private MemberService() {}
	public static MemberService getInstance() {
		if(instance == null) instance = new MemberService();
		return instance;
	}
	
	public int signUp() {
		
		System.out.println("=======================");
		System.out.println("회원가입 페이지 입니다.");
		System.out.println("=======================");
		System.out.println();
		System.out.println("아이디 입력 >> ");
		String id = ScanUtil.nextLine();
		System.out.println("비밀번호 입력 >> ");
		String pass = ScanUtil.nextLine();
		System.out.println("이름 입력 >> ");
		String name = ScanUtil.nextLine();
		System.out.println("동물이름 입력 >> ");
		String petname = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(pass);
		param.add(name);
		param.add(petname);
		
		int result = dao.signUp(param);

		if(result > 0 ) {
			System.out.println("회원가입 성공!");
		}else {
			System.out.println("회원가입 실패!");
		}
		
		return View.HOME;
	}
	public int login() {
		
		System.out.println("============로그인 페이지============");
		System.out.println(" 아이디 입력 >> ");
		String id = ScanUtil.nextLine();
		System.out.println(" 비밀번호 입력 >> ");
		String pass = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(pass);
		
		Map<String, Object> map = dao.login(param);
		System.out.println(map);
	
		if(map == null) {
			System.out.println("없는 회원 입니다.");
			return View.HOME;
		}else {
			Controller.loginInfo = dao.userInfo(id);
			Controller.login = true;
			System.out.println("로그인 성공!");
			return View.MAINPAGE;
		}
		
		
		
	}
	public int info() {
		
		info:
		while(true) {
			
			System.out.println("=================나의 정보=====================");
			System.out.println();
			for(Object item : Controller.loginInfo.keySet()) {
				System.out.println(item + " : " +  Controller.loginInfo.get(item));
				System.out.println();
			}
			
			System.out.println("1.  돌아가기    2. 수정");
			switch(ScanUtil.nextInt()) {
			case 1:
				break info;
			case 2:
				System.out.println("권한없음");
				break;
			default:
				System.out.println("잘못입력");
				break;

			}
			
		}
	
		
		return View.MYPAGE;
	}
	public int addMile() {
		System.out.println("=============마일리지 충전소================");
		System.out.println("얼마를 충전 하시겠습니까??");
		System.out.println("입력 >> ");
		
		List<Object> param = new ArrayList<>();
		param.add(ScanUtil.nextLine());
		param.add(Controller.loginInfo.get("MEM_ID"));
		
		int result = dao.addMile(param);
		
		if(result > 0) {
			System.out.println("충전 완료!");
		}else {
			System.out.println("충전 실패!");
		}
		
		
		return View.MYPAGE;
	}
	
	
	
}
