package com.bankFinal;

import java.util.Scanner;

public class BankMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankMain main = new BankMain();
		BankManager manager = new BankManager();
		Scanner sc = new Scanner(System.in);
		int menu;
		System.out.println("은행 시스템 시작");
		esc: do {
			main.menu();
			menu = sc.nextInt();
			sc.nextLine();
			switch (menu) {
			case 1:
				manager.login();
				break;
			default:
				break esc;
			}

		} while (true);
	}

	public void menu() {
		System.out.println("원하는 메뉴를 선택해주십시오.");
		System.out.println("=================");
		System.out.println("[1] 로그인");
		System.out.println("[2] 종료");
	}
}
