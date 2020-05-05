package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class VendingMachine {
	
	private BigDecimal balance = new BigDecimal ("0.00");
	private Map<String, Stack<Products>> slots;
	private List<Products> purchasedItems = new ArrayList<Products>();
	
	public void stockInventory () throws IOException {
		File file = new File ("vendingmachine.csv"); // represents the file
		Scanner fileInput = new Scanner(file); 					// to read the file
		Map<String, Stack<Products>> slots = new HashMap<String, Stack<Products>>();
		while (fileInput.hasNextLine()) { 						// while loop for when file has a next line
			String line = fileInput.nextLine(); 				// scans file and takes next line and puts in string
			String[] products = line.split("\\|"); 				// splits into delimiters per instructions
			if (products[0].startsWith("A")) {
				Stack<Products> items = generateStack(new Chips(products[1], new BigDecimal(products[2])));
				slots.put(products[0], items);
			}
			if (products[0].startsWith("B")) {
				Stack<Products> items = generateStack(new Candy(products[1], new BigDecimal(products[2])));
				slots.put(products[0], items);
			}
			if (products[0].startsWith("C")) {
				Stack<Products> items = generateStack(new Beverage(products[1], new BigDecimal(products[2])));
				slots.put(products[0], items);
			}
			if (products[0].startsWith("D")) {
				Stack<Products> items = generateStack(new Gum(products[1], new BigDecimal(products[2])));
				slots.put(products[0], items);
			}
		}
		this.setSlots(slots);
	}
	
	public void displayInventory() {
		Map<String,Stack<Products>> slots = this.getSlots();
		
		System.out.println("\t\t\tVendo-Matic 500");
		System.out.println();
		System.out.println();
		
		String[] keys = new String[] { "A1", "A2", "A3", "A4",
				"B1", "B2", "B3", "B4",
				"C1", "C2", "C3", "C4",
				"D1", "D2", "D3", "D4" };
		
		String[][] allProductDetails = getProductDetails(keys);
		
		for (int i = 0 ; i < allProductDetails.length ; i+=4) {
			System.out.printf("%18s %8s %18s %8s %18s %8s %18s %8s%n",
				allProductDetails[i][0], allProductDetails[i][1],
				allProductDetails[i+1][0], allProductDetails[i+1][1],
				allProductDetails[i+2][0], allProductDetails[i+2][1],
				allProductDetails[i+3][0], allProductDetails[i+3][1]);
			System.out.printf("%18s %8s %18s %8s %18s %8s %18s %8s%n",
				allProductDetails[i][2], allProductDetails[i][3],
				allProductDetails[i+1][2], allProductDetails[i+1][3],
				allProductDetails[i+2][2], allProductDetails[i+2][3],
				allProductDetails[i+3][2], allProductDetails[i+3][3]);
			System.out.println();
		}
		System.out.println("Current Balance: " + this.getBalance());
	}
	
	// Helper function for displayInveotory
	private String[][] getProductDetails(String[] keys) {
		
		String[][] productDetails = new String[16][4];
		for (int i = 0 ; i < productDetails.length ; i++) {
			
			productDetails[i][2] = keys[i];
			
			if ((slots.get(keys[i]).size() == 0)) {	// If stack is empty, will generate EmptyStackException, this accounts for that
				productDetails[i][1] = "";
				productDetails[i][0] = "SOLD OUT";
				productDetails[i][3] = "";
			}
			else {
				Products item = slots.get(keys[i]).peek();
				productDetails[i][1] = "(Qty: " + slots.get(keys[i]).size() + ")";
				productDetails[i][0] = item.getName();
				productDetails[i][3] = item.getPrice().toString();
			}
		}
		
		return productDetails;
	}

	// Helper function for stockInventory
	private Stack<Products> generateStack (Products product) {
			Stack<Products>items = new Stack<Products>(); // creating a new stack to go into slots
			for (int i = 0; i < 5; i++) { // for loop to add 5 times to stack
				items.push(product);
			}
			return items;
	}
	
	public void feedMoney() throws IOException {
		System.out.println("Please select dollar amount in the form of $1, $2, $5, or $10: ");
		Scanner userInput = new Scanner (System.in);
			String choice = userInput.nextLine(); // choice is whatever user enters in next line
			String[] options = choice.split("\\.");
			options[0] = options[0].replace("$", "");
			if (options[0].equals("1") || options[0].equals("2") || options[0].equals("5") || options[0].equals("10")) {
				BigDecimal amountTendered = new BigDecimal (options[0] + ".00");
				logFeedMoney(amountTendered);
				this.balance = this.balance.add(amountTendered);
			} else {
				System.out.println("Invalid dollar amount entered. Pleas enter $1, $2, $5, or $10.");
			}
		
		}
	
	public void selectProduct() throws IOException {
		System.out.println("Select your product.");
		Scanner userInput = new Scanner (System.in);
		String selection = userInput.nextLine().toUpperCase();
		if (this.slots.get(selection) == null) {
			System.out.println("Please select a valid product.");
			return ;
		} if (slots.get(selection).size() == 0) {
			System.out.println("Product is sold out. Please select another product.");
			return ;
		}
		BigDecimal priceOfItem = slots.get(selection).peek().getPrice();
			if (this.balance.compareTo(priceOfItem) < 0) {
				System.out.println("Insufficient funds. Please insert money.");
				return ;
			}
			logPurchase(slots.get(selection).peek());
			this.balance = this.balance.subtract(priceOfItem);
			purchasedItems.add(slots.get(selection).pop());
	}
	
	public void getChange() throws IOException {
		String sBalance = "" + (this.balance.multiply(new BigDecimal("100")).setScale(0, RoundingMode.DOWN));
		int balance = Integer.parseInt(sBalance);
		int quarters;
		int dimes;
		int nickels;
		quarters = balance / 25;
		balance = balance % 25;
		dimes = balance / 10;
		balance = balance % 10;
		nickels = balance / 5;
		
		System.out.println("Your change is " + quarters + " quarter(s), " + dimes + " dime(s), " + nickels + " nickel(s).");
		logChangeProffered(this.balance);
		this.balance = new BigDecimal ("0.00");
	}
	public void consumeItems() {
		for (Products product : purchasedItems) {
			System.out.println(product.getSound());
		}
	}
	
	private void logFeedMoney(BigDecimal amount) throws IOException {
		File logFile = new File("vending_machine.log");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		FileWriter fileOut = new FileWriter(logFile,true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		fileOut.write(dtf.format(now) + " FEED MONEY " + amount + " " + this.balance.add(amount) + "\n");
		fileOut.flush();
	}
	
	private void logPurchase(Products product) throws IOException {
		File logFile = new File("vending_machine.log");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		FileWriter fileOut = new FileWriter(logFile,true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		fileOut.write( dtf.format(now) + " " + product.getName() + " " + product.getPrice() + " " + this.balance.subtract(product.getPrice()) + "\n");
		fileOut.flush();
	}
	
	private void logChangeProffered(BigDecimal totalChange) throws IOException {
		File logFile = new File("vending_machine.log");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		FileWriter fileOut = new FileWriter(logFile,true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		fileOut.write( dtf.format(now) + " CHANGE PROFFERED " + totalChange + " " + this.balance.subtract(totalChange) + "\n");
		fileOut.flush();
	}
	
	public void printSalesReport() throws IOException {
		File salesReport = new File("sales_report.txt");
		if (!salesReport.exists()) {
			salesReport.createNewFile();
		}
		PrintWriter fileOut = new PrintWriter(new FileOutputStream(salesReport), true);
		String[] allProducts = new String[] { "Potato Crisps", "Stackers", "Grain Waves", "Cloud Popcorn",
				"Moonpie", "Cowtales", "Wonka Bar", "Crunchie",
				"Cola", "Dr. Salt", "Mountain Melter", "Heavy",
				"U-Chews", "Little League Chew", "Chiclets", "Triplemint" };
		
		for (String item : allProducts) {
			fileOut.println(item + "|" + salesHelper(item));
		}
		BigDecimal total = new BigDecimal("0.00");
		for (Products product : this.purchasedItems) {
			total = total.add(product.getPrice());
		}
		fileOut.println();
		fileOut.println("**TOTAL SALES** $" + total);
		fileOut.close();
	}
		
	private int salesHelper (String productName) {
		int counter = 0;
		for (Products product : this.purchasedItems) {
			if (product.getName().equals(productName)) {
				counter++;
			}
		}
		return counter;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Map<String, Stack<Products>> getSlots() {
		return slots;
	}

	public void setSlots(Map<String, Stack<Products>> slots) {
		this.slots = slots;
	}
}
