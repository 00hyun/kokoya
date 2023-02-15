package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import dao.MarketDAO;
import util.ScanUtil;
import util.View;

public class MarketService {

	private static MarketService instance = null;

	private MarketService() {
	};

	public static MarketService getInstance() {
		if (instance == null) {
			instance = new MarketService();
		}
		return instance;
	}

//	Controller con = Controller.getInstance
	MarketDAO dao = MarketDAO.getInstance();
	int buyNum = 0; // 구매할 상품 번호
	int buyNum2 = 0; // 상품 정보 보고 나서 바로 구매할 시 들어가는 상품 번호
	int buyQty = 0; // 구매할 상품 수량
	int input = 0; // 어떤 동물 용품 살건지 입력받는 변수
	int myMile = 0; // 내 마일리지
	int money = 0; // 장바구니 결제금액
	int paidMoney = 0; // 구매리스트 결제 확정 금액
	int paidMoney2 = 0; // 구매리스트 결제 확정 금액
	int choiceMoney = 0; // 선택 구매 결제 확정 금액
	List<Object> selectCart = new ArrayList<>();

	static public void makeinstance() {

	}

	public int choiceMenu() {
		System.out.println(".A__A    ✨🎂✨    A__A");
		System.out.println("( •⩊•)   _______   (•⩊• )");
		System.out.println("(>🍰>)   |           |   (<🔪<)");
		System.out.println("-------상품 코너에 오신것을 환영합니다-----");

		for (int i = 0; i < 1; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("1.상품 보기");
		System.out.println("2.내 정보 보기");
		System.out.println("3.메인 페이지 돌아가기");
		System.out.println("-------------------------------");
		System.out.println("입력>>");
		switch (ScanUtil.nextInt()) {
		case 1:
			clearScreen();
			return View.MARKET_PRODTYPE;
		case 2:
			clearScreen();
			return View.MARKET_MYINFO;
		case 3:
		default:
			clearScreen();
			return View.HOME;
		}
	}

	public int showList() {

		System.out.println("----------용품구분------------");
		System.out.println("1.강아지 용품");
		System.out.println("2.고양이 용품");
		System.out.println("3.기타 동물 용품");
		System.out.println("------------------------------");
		System.out.println("입력>>");
		input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			clearScreen();
			System.out.println("-------------------------------------------------");
			System.out.println("번호\t분류\t\t\t상품명\t\t\t가격");
			System.out.println("-------------------------------------------------");
			List<Map<String, Object>> dogList = dao.dogList();
			for (Map<String, Object> item : dogList) {
				System.out.printf("%s", item.get("PROD_NUM"));
				System.out.printf("\t%6s", item.get("PROD_TYPE"));
				System.out.printf("\t\t%-15s", item.get("PROD_NAME"));
				System.out.printf("\t\t%10s",
						String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
//			System.out.print("\t" + item.get("PROD_INFO"));
				System.out.println();
			}
			break;
		case 2:
			clearScreen();
			System.out.println("-------------------------------------------------");
			System.out.println("번호\t분류\t\t\t상품명\t\t\t가격");
			System.out.println("-------------------------------------------------");
			List<Map<String, Object>> catList = dao.catList();
			for (Map<String, Object> item : catList) {
				System.out.print(item.get("PROD_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.printf("\t\t%-7s", item.get("PROD_NAME"));
				System.out.print(
						"\t\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
//			System.out.print("\t" + item.get("PROD_INFO"));
				System.out.println();
			}
			break;
		case 3:
			clearScreen();
			System.out.println("-------------------------------------------------");
			System.out.println("번호\t분류\t\t\t상품명\t\t\t가격");
			System.out.println("-------------------------------------------------");
			List<Map<String, Object>> etcList = dao.etcList();
			for (Map<String, Object> item : etcList) {
				System.out.print(item.get("PROD_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.print("\t\t" + item.get("PROD_NAME"));
				System.out.print(
						"\t\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
//			System.out.print("\t" + item.get("PROD_INFO"));
				System.out.println();
			}
			break;
		}
//		for(int i = 0; i<list.size(); i++) {
//			Map<String, Object> item = list.get(i);
//			System.out.print(i+1);
//			System.out.print("\t" + item.get("PROD_TYPE"));
//			System.out.print("\t" +item.get("PROD_NAME"));
//			System.out.print("\t" + item.get("PROD_PRICE"));
////			System.out.print("\t" + item.get("PROD_INFO"));
//			
//			System.out.println();
//			
//		}

		System.out.println("-------------------------------------------------");
		System.out.println("1.상품정보 확인  2.구매하기  3.내 정보  4. 돌아가기");
		System.out.println("입력>>");
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.MARKET_PRODINFO;
		case 2:
			clearScreen();
			return View.MARKET_BUY;
		case 3:
			clearScreen();
			return View.MARKET_MYINFO;
		case 4:
		default:
			clearScreen();
			return View.MARKET;
		}

	}

	// 상품정보/리뷰 보기
	public int showProdInfo() {
		if (input == 1) {
			System.out.println("몇번상품을 보시겠습니까..");
			System.out.println("입력>>");
			buyNum2 = ScanUtil.nextInt();
			List<Map<String, Object>> dogInfo = dao.dogInfo(buyNum2);
			for (Map<String, Object> item : dogInfo) {
				System.out.println("-------------------------------------------------");
				System.out.println("상품설명 : " + item.get("PROD_INFO"));
				List<Map<String, Object>> review = dao.review(item.get("PROD_ID").toString());
				if (review == null) {
					System.out.println("등록된 리뷰가 없습니다.");
					System.out.println("걍 사세요!");
					System.out.println("-------------------------------------------------");
					System.out.println("구매하시겠습니까? 1.구매  2.상품 더보기 ");
					System.out.println("입력 >>");
					switch (ScanUtil.nextInt()) {
					case 1:
						return View.MARKET_CARTINSERT2;
					case 2:
						clearScreen();
						return View.MARKET_PRODTYPE;
					}
				} else {
					System.out.println();
					System.out.println("----------------------리뷰-------------------------");
					for (int i = 0; i < review.size(); i++) {
						Map<String, Object> item2 = review.get(i);
						System.out.print(item2.get("REVIEW_NUM") + ".");
						System.out.print(item2.get("REVIEW_TITLE"));
						System.out.print("\t\t     작성자: ");
						System.out.println(item2.get("MEM_ID"));
						System.out.println("내용: " + item2.get("REVIEW_CONTENT"));
						System.out.print("작성일시: " + item2.get("REVIEW_DATE"));
						System.out.println();
						System.out.println("-------------------------------------------------");
					}
				}

				System.out.println("구매하시겠습니까? 1.구매  2.상품 더보기 ");
				System.out.println("입력 >>");
				switch (ScanUtil.nextInt()) {
				case 1:
					return View.MARKET_CARTINSERT2;
				case 2:
					clearScreen();
					return View.MARKET_PRODTYPE;
				}
			}
		} else if (input == 2) {
			System.out.println("몇번상품을 보시겠습니까..");
			System.out.println("입력>>");
			buyNum2 = ScanUtil.nextInt();
			List<Map<String, Object>> catInfo = dao.catInfo(buyNum2);
			for (Map<String, Object> item : catInfo) {
				System.out.println("-------------------------------------------------");
				System.out.println("상품설명 : " + item.get("PROD_INFO"));
				List<Map<String, Object>> review = dao.review(item.get("PROD_ID").toString());
				if (review == null) {
					System.out.println("등록된 리뷰가 없습니다.");
					System.out.println("걍 사세요!");
					System.out.println("-------------------------------------------------");
					System.out.println("구매하시겠습니까? 1.구매  2.상품 더보기 ");
					System.out.println("입력 >>");
					switch (ScanUtil.nextInt()) {
					case 1:
						return View.MARKET_CARTINSERT2;
					case 2:
						clearScreen();
						return View.MARKET_PRODTYPE;
					}
				} else {
					for (int i = 0; i < review.size(); i++) {
						Map<String, Object> item2 = review.get(i);
						System.out.println("\t\t\t리뷰");
						System.out.print(item2.get("REVIEW_NUM") + ".");
						System.out.print(item2.get("REVIEW_TITLE"));
						System.out.print(item2.get("REVIEW_CONTENT"));
						System.out.print(item2.get("REVIEW_DATE"));
						System.out.println();
						System.out.println("-------------------------------------------------");
						System.out.println("구매하시겠습니까? 1.구매  2.상품 더보기 ");
						System.out.println("입력 >>");
						switch (ScanUtil.nextInt()) {
						case 1:
							return View.MARKET_CARTINSERT2;
						case 2:
							clearScreen();
							return View.MARKET_PRODTYPE;
						}
					}
				}
			}
		} else {
			System.out.println("몇번상품을 보시겠습니까..");
			System.out.println("입력>>");
			buyNum2 = ScanUtil.nextInt();
			List<Map<String, Object>> etcInfo = dao.etcInfo(buyNum2);
			for (Map<String, Object> item : etcInfo) {
				System.out.println("-------------------------------------------------");
				System.out.println("상품설명 : " + item.get("PROD_INFO"));
				List<Map<String, Object>> review = dao.review(item.get("PROD_ID").toString());
				if (review == null) {
					System.out.println("등록된 리뷰가 없습니다.");
					System.out.println("걍 사세요!");
					System.out.println("-------------------------------------------------");
					System.out.println("구매하시겠습니까? 1.구매  2.상품 더보기 ");
					System.out.println("입력 >>");
					switch (ScanUtil.nextInt()) {
					case 1:
						return View.MARKET_CARTINSERT2;
					case 2:
						clearScreen();
						return View.MARKET_PRODTYPE;
					}
				} else {
					for (int i = 0; i < review.size(); i++) {
						Map<String, Object> item2 = review.get(i);
						System.out.println("\t\t\t리뷰");
						System.out.print(item2.get("REVIEW_NUM") + ".");
						System.out.print(item2.get("REVIEW_TITLE"));
						System.out.print(item2.get("REVIEW_CONTENT"));
						System.out.print(item2.get("REVIEW_DATE"));
						System.out.println();
						System.out.println("-------------------------------------------------");
						System.out.println("구매하시겠습니까ㅣ 1.구매  2.상품 더보기 ");
						System.out.println("입력 >>");
						switch (ScanUtil.nextInt()) {
						case 1:
							return View.MARKET_CARTINSERT2;
						case 2:
							clearScreen();
							return View.MARKET_PRODTYPE;
						}
					}
				}
			}
		}
		return View.MARKET;
	}

	public int buyProcess() {
		clearScreen();
		if (input == 1) {
			System.out.println("-------------------------------------------------");
			System.out.println("번호\t분류\t\t상품명\t\t가격");
			System.out.println("-------------------------------------------------");
			List<Map<String, Object>> dogList = dao.dogList();
			for (Map<String, Object> item : dogList) {
				System.out.print(item.get("PROD_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.print("\t\t" + item.get("PROD_NAME"));
				System.out.print(
						"\t\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
				System.out.println();
			}
			System.out.println("-------------------------------------------------");
			System.out.println("1.장바구니 담기  2.장바구니 가기  3.마켓 메인으로 돌아가기");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.MARKET_CARTINSERT;
			case 2:
				clearScreen();
				return View.MARKET_CARTLIST;
			case 3:
				clearScreen();
				return View.MARKET;
			}
		} else if (input == 2) {
			System.out.println("--------------------------------------");
			System.out.println("번호\t분류\t\t상품명\t가격");
			System.out.println("--------------------------------------");
			List<Map<String, Object>> catList = dao.catList();
			for (Map<String, Object> item : catList) {
				System.out.print(item.get("PROD_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.print("\t\t" + item.get("PROD_NAME"));
				System.out
						.print("\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
				System.out.println();
			}
			System.out.println("---------------------------------------");
			System.out.println("1.장바구니 담기  2.장바구니 가기  3.돌아가기");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.MARKET_CARTINSERT;
			case 2:
				clearScreen();
				return View.MARKET_CARTLIST;
			case 3:
				clearScreen();
				return View.MARKET;
			}
		} else {
			System.out.println("--------------------------------------");
			System.out.println("번호\t분류\t\t상품명\t가격");
			System.out.println("--------------------------------------");
			List<Map<String, Object>> etcList = dao.etcList();
			for (Map<String, Object> item : etcList) {
				System.out.print(item.get("PROD_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.print("\t\t" + item.get("PROD_NAME"));
				System.out
						.print("\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
				System.out.println();
			}
			System.out.println("---------------------------------------");
			System.out.println("1.장바구니 담기  2.장바구니 가기  3.돌아가기");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.MARKET_CARTINSERT;
			case 2:
				clearScreen();
				return View.MARKET_CARTLIST;
			case 3:
				clearScreen();
				return View.MARKET;
			}
		}
		clearScreen();
		return View.MARKET;
	}

	public int cartInsert() {
		System.out.println("---------------------------------------");
		System.out.println("몇번 상품을 구입하시겠습니까?");
		System.out.println("입력>>");
		buyNum = ScanUtil.nextInt();
		System.out.println("구입할 수량을 입력해주세요.");
		System.out.println("입력>>");
		buyQty = ScanUtil.nextInt();

		int cartInsert = dao.cartInsert(buyNum, buyQty, (String) Controller.loginInfo.get("MEM_ID"));// 여기 아이디 바꿔~~
		if (cartInsert > 0) {
			System.out.println("해당 상품을 장바구니에  담았습니다.");
			System.out.println("장바구니로 이동하시겠습니까? 1.이동  2.상품 더보기");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				clearScreen();
				return View.MARKET_CARTLIST;
			case 2:
			default:
				clearScreen();
				return View.MARKET_BUY;
			}
		} else {
			System.out.println("해당 상품을 장바구니에 담지 못하였습니다.");
			System.out.println("상품 번호를 확인해주세요.");
			buyProcess();
		}

		clearScreen();
		return View.MARKET;
	}

	public int cartInsert2() {
		System.out.println("구입할 수량을 입력해주세요.");
		System.out.println("입력>>");
		buyQty = ScanUtil.nextInt();
		int cartInsert = dao.cartInsert(buyNum2, buyQty, (String) Controller.loginInfo.get("MEM_ID"));
		if (cartInsert > 0) {
			System.out.println("해당 상품을 장바구니에  담았습니다.");
			System.out.println("장바구니로 이동하시겠습니까? 1.이동  2.상품 더보기");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				clearScreen();
				return View.MARKET_CARTLIST;
			case 2:
			default:
				clearScreen();
				return View.MARKET_BUY;
			}
		} else {
			System.out.println("해당 상품을 장바구니에 담지 못하였습니다.");
			System.out.println("상품 번호를 확인해주세요.");
			buyProcess();
		}
		clearScreen();
		return View.MARKET;

	}

	public int cartList() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("---------------장바구니 목록------------------");
		System.out.println("번호\t분류\t\t상품명\t가격\t수량");
		System.out.println("-----------------------------------------");
		List<Map<String, Object>> cartList = dao.cartList((String) (Controller.loginInfo.get("MEM_ID"))); // 여기 아이디 넣어야함
		if (cartList == null) {
			System.out.println("장바구니가 비어있습니다!");
			if (input == 1) {
				System.out.println("댕댕이를 위한 선물을 사보는게 어때요?");
			} else if (input == 2) {
				System.out.println("고양이를 위한 선물을 사보는게 어때요?");
			} else if (input == 3) {
				System.out.println("햄찌를 위한 선물을 사보는게 어때요?");
			}
			System.out.println("-----------------------------------------");
			for (int i = 0; i < 1; i++) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return View.MARKET_MYINFO;
		} else {
			for (Map<String, Object> item : cartList) {
				System.out.print(item.get("ORDER_NUM"));
				System.out.print("\t" + item.get("PROD_TYPE"));
				System.out.print("\t\t" + item.get("PROD_NAME"));
				System.out
						.print("\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
				System.out.print("\t" + item.get("ORDER_QTY"));
				System.out.println();
			}
		}
		System.out.println("--------------------------------------------");

		for (int i = 0; i < 1; i++) {
			int money = 0;
			for (Map<String, Object> item : cartList) {
				money += Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))
						* Integer.parseInt(String.valueOf(item.get("ORDER_QTY")));
//			System.out.println(String.format("%,d", Integer.parseInt(String.valueOf(item.get("총 결제 금액")))));
			}
			System.out.printf("총 결제 금액: %,6d", money);
			paidMoney = money;
		}
//		System.out.println();
//		System.out.println("--------------------------------------------");
//		System.out.println("1.결제하기  2.삭제하기  3.전체삭제  4.정보 돌아가기");
//		System.out.println("입력>>");
//		switch(ScanUtil.nextInt()) {
//		case 1: clearScreen(); return View.MARKET_CARTPURCHASE;
//		case 2: return View.MARKET_CARTDELETE;
//		case 3: clearScreen(); return View.MARKET_CARTDELETE2;
//		case 4: clearScreen(); default: return View.MARKET_MYINFO;
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println("1.전체 결제하기  2.선택 구매  3.삭제하기  4.전체삭제  4.정보 돌아가기");
		System.out.println("입력>>");
		switch (ScanUtil.nextInt()) {
		case 1:
			clearScreen();
			return View.MARKET_CARTPURCHASE;
		case 2:
			return View.MARKET_CARTPURCHASECHOICE;
		case 3:
			return View.MARKET_CARTDELETE;
		case 4:
			clearScreen();
			return View.MARKET_CARTDELETE2;
		case 5:
			clearScreen();
		default:
			return View.MARKET_MYINFO;
		}
	}

	public int cartDelete() {
		System.out.println("---------------------------------------");
		System.out.println("삭제할 목록을 선택해주세요.");
		System.out.println("입력>>");
		int cartDelete = dao.cartDelete(ScanUtil.nextInt());
		if (cartDelete > 0) {
			System.out.println("삭제 완료!!");
		} else {
			System.out.println("번호를 잘못입력 하셨습니다..삭제 실패");
		}
		return View.MARKET_CARTLIST;
	}

	public int cartDelete2() {
		int cartDelete2 = dao.cartDelete2((String) Controller.loginInfo.get("MEM_ID"));
		if (cartDelete2 > 0) {
			System.out.println("삭제 완료!!");
			return View.MARKET_CARTLIST;
		} else {
			System.out.println("뭔가 잘못 되었습니다.삭제 실패");
			return View.MARKET_CARTLIST;
		}
	}

	public int cartPurchase() {

		List<Map<String, Object>> showMyInfo = dao.showMyInfo((String) Controller.loginInfo.get("MEM_ID"));
		Map<String, Object> mem_mile = showMyInfo.get(0); // 얘는 되고
//		selectCart = new ArrayList<Integer>();
		
		try {
			if (Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) >= paidMoney) {
				myMile = Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) - paidMoney;
				int cartPurchase = dao.cartPurchase(myMile, (String) Controller.loginInfo.get("MEM_ID"), paidMoney);
				if (cartPurchase > 0) {
					System.out.println("\t 결제중");
					for (int i = 0; i < 3; i++) {
						try {
							System.out.println("\t   .");
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					clearScreen();
					System.out.println("-------------------------------------------------");
					System.out.println("구매 완료!");
					System.out.println();
					System.out.println("결제전 마일리지: "
							+ String.format("%,d", Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE"))))));
					System.out.println("차감 마일리지: " + String.format("%,d", paidMoney));
					System.out.println("결제후 마일리지: " + String.format("%,d", myMile));
					Controller.loginInfo.replace("MEM_MILE", myMile);
					System.out.println("-------------------------------------------------");
					System.out.println("구매 이력 조회로 이동하시겠습니까? 1.이동  2.상품 더보기");
					System.out.println("입력>>");
					switch (ScanUtil.nextInt()) {
					case 1:
						clearScreen();
						return View.MARKET_BUYLIST;
					case 2:
					default:
						clearScreen();
						return View.MARKET_BUY;
					}
				}
			} else {
				System.out.println("\t 결제중");
				for (int i = 0; i < 3; i++) {
					try {
						System.out.println("\t   .");
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				clearScreen();
				System.out.println("-------------------------------------------------");
				System.out.println("마일리지가 부족합니다.");
				System.out.println("마일리지를 충전해주세요.");
				System.out.println("-------------------------------------------------");
				System.out.println("1. 충전소    2. 마켓메인");
				System.out.println("입력 >>");
				System.out.println("-------------------------------------------------");
				switch (ScanUtil.nextInt()) {
				case 1:
					return View.MEM_MILE;
				case 2:
					return View.MARKET;

				}
			}
		} catch (Exception e) {
			if (Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) < paidMoney)
//			e.printStackTrace();
				System.out.println("마일리지가 부족합니다.");
			System.out.println("마일리지를 충전해주세요.");
			System.out.println("오류가 발생했습니다.");
			System.out.println("1. 충전소    2. 마켓메인");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.MEM_MILE;
			case 2:
				return View.MARKET;

			}
			clearScreen();
			return View.MARKET;
		}
		clearScreen();
		return View.HOME;
	}
	
	
	public int cartPurchase2() {

		List<Map<String, Object>> showMyInfo = dao.showMyInfo((String) Controller.loginInfo.get("MEM_ID"));
		Map<String, Object> mem_mile = showMyInfo.get(0); // 얘는 되고
		
		System.out.println(selectCart);
		List<Map<String, Object>> choiceTotal = dao.choiceMoney(selectCart,String.valueOf(Controller.loginInfo.get("MEM_ID")));
		System.out.println(choiceTotal);
		int total = 0 ;
		for(Map<String, Object> item : choiceTotal) {
			total += Integer.parseInt(String.valueOf(item.get("결제금액"))) ;
		}
		
		try {
			if (Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) >= total) {
				myMile = Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) - total;
				int cartPurchase = dao.cartPurchase2(myMile, (String) Controller.loginInfo.get("MEM_ID"), total,selectCart);
				if (cartPurchase > 0) {
					System.out.println("\t 결제중");
					for (int i = 0; i < 3; i++) {
						try {
							System.out.println("\t   .");
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					clearScreen();
					selectCart = new ArrayList<>();
					System.out.println("-------------------------------------------------");
					System.out.println("구매 완료!");
					System.out.println();
					System.out.println("결제전 마일리지: "
							+ String.format("%,d", Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE"))))));
					System.out.println("차감 마일리지: " + String.format("%,d", total));
					System.out.println("결제후 마일리지: " + String.format("%,d", myMile));
					Controller.loginInfo.replace("MEM_MILE", myMile);
					System.out.println("-------------------------------------------------");
					System.out.println("구매 이력 조회로 이동하시겠습니까? 1.이동  2.상품 더보기");
					System.out.println("입력>>");
					switch (ScanUtil.nextInt()) {
					case 1:
						clearScreen();
						
						return View.MARKET_BUYLIST;
					case 2:
					default:
						clearScreen();
						return View.MARKET_BUY;
					}
				}
			} else {
				System.out.println("\t 결제중");
				for (int i = 0; i < 3; i++) {
					try {
						System.out.println("\t   .");
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				clearScreen();
				System.out.println("-------------------------------------------------");
				System.out.println("마일리지가 부족합니다.");
				System.out.println("마일리지를 충전해주세요.");
				System.out.println("-------------------------------------------------");
				System.out.println("1. 충전소    2. 마켓메인");
				System.out.println("입력 >>");
				System.out.println("-------------------------------------------------");
				switch (ScanUtil.nextInt()) {
				case 1:
					return View.MEM_MILE;
				case 2:
					return View.MARKET;

				}
			}
		} catch (Exception e) {
			if (Integer.parseInt(String.valueOf((mem_mile.get("MEM_MILE")))) < paidMoney)
//			e.printStackTrace();
				System.out.println("마일리지가 부족합니다.");
			System.out.println("마일리지를 충전해주세요.");
			System.out.println("오류가 발생했습니다.");
			System.out.println("1. 충전소    2. 마켓메인");
			System.out.println("입력>>");
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.MEM_MILE;
			case 2:
				return View.MARKET;

			}
			clearScreen();
			return View.MARKET;
		}
		clearScreen();
		return View.HOME;
	}
	

	public int cartPurchaseChoice() {// 
		List<Map<String, Object>> showMyInfo = dao.showMyInfo((String)Controller.loginInfo.get("MEM_ID"));
		List<Map<String, Object>> cartList = dao.cartList((String)(Controller.loginInfo.get("MEM_ID")));
		Map<String, Object> mem_mile = showMyInfo.get(0);
		
		clearScreen();
		while(true) {
			System.out.println("---------------장바구니 목록------------------");
			System.out.println("번호\t분류\t\t상품명\t가격\t수량\t선택체크 박스");
			System.out.println("-----------------------------------------");
			for(Map<String, Object> item : cartList) {
				
				System.out.print(item.get("ORDER_NUM"));
				System.out.print( "\t"+item.get("PROD_TYPE"));
				System.out.print("\t\t" +item.get("PROD_NAME"));
				System.out.print("\t" + String.format("%,6d", Integer.parseInt(String.valueOf(item.get("PROD_PRICE")))));
				System.out.print("\t" + item.get("ORDER_QTY"));
				if(selectCart.contains(Integer.parseInt(item.get("ORDER_NUM").toString()))) {
					System.out.print("\t♥");
				}
				System.out.println();
			}		
			System.out.println("---------------------------------------");
			System.out.println("구매할 상품 번호를 입력해주세요");
			System.out.println("입력>>");
			int num = ScanUtil.nextInt();
			if(selectCart.contains(num)) {
				System.out.println("이미 선택한 상품입니다.");
				if(cartList.size() == selectCart.size()) {
					System.out.println("더 이상 선택할 상품이 없습니다.");
					System.out.println("구매를 진행해 주세요.");
					return View.MARKET_CARTPURCHASE2;
				}else {
					continue;
				}
			}else {
				selectCart.add(num);
				System.out.println("1.구매할 상품을 더 입력 하실래요?  2.선택한 목록만 구매하기");
				switch(ScanUtil.nextInt()) {
				case 1: clearScreen(); break;		
				case 2: return View.MARKET_CARTPURCHASE2;
			}
			
			/* 중요 주석
			 * MarketService.getInstance().selectCart = new ArrayList<Integer>(); 
			 * 리스트 비워주는 중요함을 기억
			 * */ 
			}
		}
		
	}

	public int myInfo() {
//		List<Map<String, Object>> showMyInfo = dao.showMyInfo(Controller.loginInfo.get("MEM_ID"));
//		List<Map<String,Object>> totalPayment = dao.totalPayment((String)Controller.loginInfo.get("MEM_ID"));
//		List<Map<String,Object>> Payment = dao.Payment(Controller.loginInfo.get("MEM_ID"));
		clearScreen();

		System.out.println("--------------------------MY PAGE---------------------------------");
		System.out.println("아이디 : " + Controller.loginInfo.get("MEM_ID"));
		System.out.println("내 배송지: " + Controller.loginInfo.get("MEM_ADD"));
		try {
			System.out.println("보유 마일리지: "
					+ String.format("%,6d", Integer.parseInt(String.valueOf(Controller.loginInfo.get("MEM_MILE")))));
		} catch (Exception e) {
			System.out.println("보유 마일리지가 없습니다.");
		}
		System.out.println("------------------------------------------------------------------");
		System.out.println("1.장바구니 보기  2.구매이력 조회  3.마일리지 충전하기  4.돌아가기");
		System.out.println("입력>>");
		switch (ScanUtil.nextInt()) {
		case 1:
			clearScreen();
			return View.MARKET_CARTLIST;
		case 2:
			clearScreen();
			return View.MARKET_BUYLIST;
		case 3:
			clearScreen();
			return View.MEM_MILE;
		case 4:
		default:
			clearScreen();
			return View.MARKET;
		}
	}

	public int buyList() {
		System.out.println("===================================구매 목록=============================================");

		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("번호\t구매코드\t\t\t구매일시\t\t구매내역\t결제금액");
//		System.out.println("번호\t분류\t\t상품명\t\t가격\t수량");

		System.out.println("-----------------------------------------------------------------------------------------");
		List<Map<String, Object>> buyList = dao.buytList((String) Controller.loginInfo.get("MEM_ID")); // 여기 아이디 넣어야함
		List<Map<String, Object>> buyCount = dao.buyCount((String) Controller.loginInfo.get("MEM_ID")); // 여기 아이디 넣어야함
		if (buyList == null) {
			System.out.println("구매목록이 비어있습니다!");
			System.out.println("당신은 검소한 사람!");

			System.out.println(
					"-----------------------------------------------------------------------------------------");
			for (int i = 0; i < 1; i++) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return View.MARKET_MYINFO;
		} else {
			for (int i = 0; i < buyList.size(); i++) {
				Map<String, Object> item2 = buyCount.get(i);
				Map<String, Object> item = buyList.get(i);
				System.out.print(item.get("BUY_NUM"));
				System.out.print("\t" + item.get("BUY_CODE"));
				System.out.print("\t\t" + item.get("BUY_DATE"));
				System.out.print("\t\t" + "총 " + item2.get("COUNT") + "건");
				System.out.print(
						"\t\t" + String.format("%,8d", Integer.parseInt(String.valueOf(item.get("BUY_AMOUNT")))) + "원");
				System.out.println();

				System.out.println(
						"-----------------------------------------------------------------------------------------");
			}
		}
		System.out.println();

		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("1.상세 내역 조회  2.내 정보로 돌아가기");
		System.out.println("입력>>");
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.MARKET_WRITEREVIEW;
		case 2:
		default:
			clearScreen();
			return View.MARKET_MYINFO;
		}
	}

	public int review() {
		System.out.println("번호를 입력하세요>>");
		List<Map<String, Object>> list = dao.list4review(ScanUtil.nextLine());
		clearScreen();
		System.out.println("===================================구매 목록====================================");
		System.out.println("순번\t상품번호\t상품명\t상품가격");
		for (Map<String, Object> item : list) {
			System.out.println(item.get("ORDER_NUM") + "\t" + item.get("PROD_ID") + "\t" + item.get("PROD_NAME") + "\t"
					+ item.get("PROD_PRICE"));
		}
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("1.리뷰 작성  2.돌아가기");
		System.out.print("번호 >>");
		switch (ScanUtil.nextInt()) {
		case 1:
			List<Object> param = new ArrayList<>();
			param.add(Controller.loginInfo.get("MEM_ID"));
			System.out.println("어떤 상품 리뷰하시겠습니까");
			System.out.print("순번 입력>>");
			param.add(ScanUtil.nextInt());
			System.out.print("제목 >> ");
			param.add(ScanUtil.nextLine());
			System.out.println("내용 >> ");
			param.add(ScanUtil.nextLine());
			int result = dao.insertReview(param);
			if (result > 0) {
				System.out.println("리뷰 등록 성공!");
			} else {
				System.out.println("리뷰 등록 실패!");
			}
			return View.MARKET_BUYLIST;
		case 2:
		default:
			return View.MARKET_BUYLIST;
		}

	}

	public static void clearScreen() {
		for (int i = 0; i < 45; i++) {
			System.out.println();
		}
	}

}
