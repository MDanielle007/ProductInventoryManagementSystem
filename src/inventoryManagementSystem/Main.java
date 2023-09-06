package inventoryManagementSystem;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		main.startPage();
		System.out.println("\nThank You For Your Patronage!");
	}
	
	public void startPage() {
		Scanner scanner = new Scanner(System.in);
		boolean choiceValid = true;
		do {
			System.out.println("=========================================================================");
			System.out.println("   Product Inventory Management System");
			System.out.println("=========================================================================\n");
			System.out.println("\t[1] Login\n"
					+ "\t[2] Exit\n");
			System.out.print("Select Option: ");
			try {
				int choice = scanner.nextInt();
				
				switch (choice) {
				case 1:
					LoginMain login = new LoginMain(new SessionUser());
					login.loginForm();
					break;
				case 2:
					break;
				default:
					choiceValid = false;
					System.out.println("Invalid Input");	
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (choiceValid == false);
	}
}
