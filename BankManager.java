package com.bankFinal;

import java.util.List;
import java.util.Scanner;

public class BankManager {

	public void login() {
		MemberDAO dao = new MemberDAO();
		Members m = new Members();
		Scanner sc = new Scanner(System.in);

		String pwd;
		System.out.println("아이디를 입력해주십시오.");
		String mid = sc.nextLine();
		System.out.println("비밀번호를 입력해주십시오.");
		pwd = sc.nextLine();
		m = dao.selMember(mid.toUpperCase());
		if (m == null || !pwd.equals(m.getPwd())) {
			System.out.println("아이디와 비밀번호를 확인해주십시오.");
		} else {
			System.out.println("로그인이 완료되었습니다.");
			System.out.println("=================");
			menu(m);
		}
	}

	public void createAccount(Members m) {
		// 계좌 생성
		AccountDAO dao = new AccountDAO();
		Scanner sc = new Scanner(System.in);
		System.out.println("계좌를 생성합니다");
		System.out.println("=================");
		Account account = new Account();
		account.setOwner(m.getMid());
		System.out.println("계좌번호를 입력하세요.");
		account.setAccountNum(sc.nextLine());
		System.out.println("비밀번호를 입력하세요.");
		account.setPwd(sc.nextLine());
		System.out.println("통장 종류를 입력하세요.");
		account.setKind(sc.nextLine());
		System.out.println("개설지점을 입력하세요.");
		account.setLocations(sc.nextLine());

		int iv = dao.insertAccount(account);
		if (iv == 0) {
			System.out.println("계좌 생성에 실패하셨습니다.");
		} else {
			System.out.println("계좌 생성에 성공하셨습니다.");
		}
	}

	public void checkAccount(Members m) {
		// 계좌 조회
		AccountDAO dao = new AccountDAO();
		Account account = new Account();
		String accountNum;
		Scanner sc = new Scanner(System.in);
		System.out.println("계좌를 조회합니다");
		System.out.println("=================");
		System.out.println("계좌번호를 입력해주세요");
		accountNum = sc.nextLine();
		account = dao.selectAccount(accountNum);
		if (account == null) {
			System.out.println("계좌가 존재하지 않습니다.");
		} else {
			System.out.println("계좌번호 : " + account.getAccountNum());
			System.out.println("비밀번호 : " + account.getPwd());
			System.out.println("통장종류 : " + account.getKind());
			System.out.println("개설지점 : " + account.getLocations());
			System.out.println("개설일 : " + account.getRegdate());
			System.out.println("소유주 : " + account.getOwner());
			esc: do {
				System.out.println("=================");
				System.out.println("메뉴를 선택해주세요.");
				System.out.println("=================");
				System.out.println("[1]거래내역 조회");
				System.out.println("[2]입금");
				System.out.println("[3]출금");
				System.out.println("[4]이체");
				System.out.println("[5]되돌아가기");
				int menu = sc.nextInt();
				switch (menu) {
				case 1:
					dealList(account);
					break;
				case 2:
					deposit(account);
					break;
				case 3:
					withdraw(account);
					break;
				case 4:
					transfer(account);
					break;
				case 5:
					break esc;
				default:
					break;
				}
			} while (true);
		}
	}

	public void dealList(Account account) {
		DealDAO dao = new DealDAO();
		List<Deal> list = dao.selectDeal(account);
		if (list.size() == 0) {
			System.out.println("목록이 존재하지 않습니다.");
		} else {
			for (Deal deal : list) {
				System.out.print("거래 번호 : " + deal.getDealnum() + "\t");
				System.out.print("거래 날짜 : " + deal.getDealdate() + "\t");
				System.out.print("거래 종류 : " + deal.getKind() + "\t");
				System.out.print("거래 내용 : " + deal.getContent() + "\t");
				System.out.print("거래 금액 : " + deal.getAmount() + "\t");
				System.out.print("잔고       : " + deal.getBalance() + "\t");
				System.out.println("계좌 번호 : " + deal.getFaccountnum() + "\t");
				System.out.println("=================");
			}
		}
	}

	public void deposit(Account a) {
		// 입금
		DealDAO dao = new DealDAO();
		Deal d = dao.selectDeal(a.getAccountNum());
		if (d == null) {
			d = new Deal();
			d.setBalance(0);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("=================");
		System.out.println("입금할 금액을 입력해주세요");
		d.setAmount(sc.nextInt());
		d.setBalance(d.getBalance() + d.getAmount());
		d.setFaccountnum(a.getAccountNum());
		int iv = dao.deDeal(d);
		if (iv == 0) {
			System.out.println("입금에 실패하셨습니다.");
		} else {
			System.out.println("입금에 성공하셨습니다.");
		}
	}

	public void withdraw(Account a) {
		// 출금
		DealDAO dao = new DealDAO();
		Deal d = dao.selectDeal(a.getAccountNum());
		if (d == null) {
			d = new Deal();
			d.setBalance(0);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("=================");
		System.out.println("출금할 금액을 입력해주세요");
		d.setAmount(sc.nextInt());
		d.setBalance(d.getBalance() - d.getAmount());
		d.setFaccountnum(a.getAccountNum());
		int iv = 0;
		if (d.getBalance() >= 0) {
			iv = dao.wiDeal(d);
		}else{
			System.out.print("잔액이 부족합니다. ");
		}
		if (iv == 0) {
			System.out.println("출금에 실패하셨습니다.");
		} else {
			System.out.println("출금에 성공하셨습니다.");
		}
	}

	public void transfer(Account a) {
		// 이체 (출금 & 입금)
		DealDAO dao = new DealDAO();
		//d는 이체 출금할 계좌
		Deal d = dao.selectDeal(a.getAccountNum());

		if (d == null) {
			d = new Deal();
			d.setBalance(0);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("=================");
		System.out.println("이체할 계좌를 입력해주세요.");
		String tfAccountNum = sc.nextLine();
		System.out.println("이체할 금액을 입력해주세요.");
		d.setAmount(sc.nextInt());
		
		//d2는 이체 입금할 계좌
		Deal d2 = dao.selectDeal(tfAccountNum);
		if (d2 == null) {
			d2 = new Deal();
			d2.setBalance(0);
		}
		d.setBalance(d.getBalance() - d.getAmount());
		d.setFaccountnum(a.getAccountNum());

		d2.setAmount(d.getAmount());
		d2.setBalance(d2.getBalance() + d2.getAmount());
		int iv = 0;
		
		//금액이 - 일때는 패스.
		if (d.getBalance() >= 0) {
			iv = dao.tfDeal(d, d2);
		}else{
			System.out.print("잔액이 부족합니다. ");
		}
		if (iv == 0) {
			System.out.println("이체에 실패하셨습니다.");
		} else {
			System.out.println("이체에 성공하셨습니다.");
		}
	}

	public void menu(Members m) {
		Scanner sc = new Scanner(System.in);
		int menu;
		esc: do {
			System.out.println("메뉴를 선택해주세요.");
			System.out.println("=================");
			System.out.println("[1]계좌생성");
			System.out.println("[2]계좌조회");
			System.out.println("[3]메뉴로 가기");
			menu = sc.nextInt();
			sc.nextLine();
			switch (menu) {
			case 1:
				// 계좌 생성
				createAccount(m);
				break;
			case 2:
				// 계좌 조회
				checkAccount(m);
				break;
			case 3:
				break esc;
			default:

				break;
			}
		} while (true);
	}
}
