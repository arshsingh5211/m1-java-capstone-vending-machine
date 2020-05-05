package com.techelevator;

import java.io.IOException;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String   MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String   MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String   MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													    MAIN_MENU_OPTION_PURCHASE,
													    MAIN_MENU_OPTION_EXIT};
	
	private static final String   PURCHASE_MENU_FEED_MONEY = "Feed Money";
	private static final String   PURCHASE_MENU_SELECT_ITEM = "Select Item";
	private static final String   PURCHASE_MENU_EXIT = "Exit to Main Menu";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_FEED_MONEY,
															PURCHASE_MENU_SELECT_ITEM,
															PURCHASE_MENU_EXIT };
	
	private Menu menu;
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	public void run() throws IOException {
		VendingMachine vm1 = new VendingMachine();
		try {
			vm1.stockInventory();
		} catch (IOException e) {
				e.printStackTrace();
		}
		boolean shouldLoop = true;
		while(shouldLoop) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				vm1.displayInventory();
			} 
			else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				boolean purchaseMenuLoop = true;
				while (purchaseMenuLoop) {
					String purchaseMenuChoice = (String)menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					
					if (purchaseMenuChoice.equals(PURCHASE_MENU_FEED_MONEY)) {
						vm1.feedMoney();
					}
					else if (purchaseMenuChoice.equals(PURCHASE_MENU_SELECT_ITEM)) {
						vm1.selectProduct();
					}
					else if (purchaseMenuChoice.equals(PURCHASE_MENU_EXIT)) {
						purchaseMenuLoop = false;
					}
				}
			}
			else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				vm1.getChange();
				vm1.consumeItems();
				vm1.printSalesReport();
		        shouldLoop=false;	  
			  }
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
