package com.techelevator.menu;
/**********************************************************************
 * This will demonstrate how to use the Menu process given to
 * students in the Tech Elevator Java bootcamp
 *********************************************************************/ 

import com.techelevator.view.Menu;  // Menu processing class

public class MenuCLI {  // Class representing the menu to be used
	/**********************************************************************
	 * Define String constants for the menu option text/choices
	 * named constants are a good idea s they make the code easier
	 * to follow and use
	 *********************************************************************/ 

	private static final String   MAIN_MENU_OPTION_1    = "Option 1 Description";
	private static final String   MAIN_MENU_OPTION_2    = "Option 2 Description";
	private static final String   MAIN_MENU_OPTION_3    = "Option 3 Description";
	private static final String   MAIN_MENU_OPTION_EXIT = "Exit";
	
	/**********************************************************************
	 * Define array of String constants representing the menu option 
	 * text/choices to be displayed to the user.
	 **********************************************************************/ 

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_1,
														MAIN_MENU_OPTION_2,
														MAIN_MENU_OPTION_3,
													    MAIN_MENU_OPTION_EXIT};
	/**********************************************************************
	 * Define array of String constants representing the menu option 
	 * text/choices to be displayed to the user.
	 * 
	 * Note: named constants need not be used, but may make use of the
	 *       menu more difficult.
	 **********************************************************************/ 

	private static final String[] INSTRUCTOR_OPTIONS = { "Frank",
														 "Joe",
														 "Josh",
														 "Craig",
													     "End Process"};
	
	
	private Menu menu;   // Define an instance variable for Menu object
	                     // This will be set by ctor using menu passed in by user
	
	public MenuCLI(Menu menu) { // ctor to set menu object to be used for this instance
		this.menu = menu;
	}
	/**********************************************************************
	 * Main processing loop invoked from main()
	 **********************************************************************/ 	

	public void run() {  
		
		boolean shouldLoop = true;  // Loop control variable

		while(shouldLoop) {         // Loop while loop control variable is true
			/**********************************************************************
			 * Define a variable to hold user menu choice & display menu to get it
			 * 
			 * getChoiceFromOptions will:
			 * 
			 *  1. Display the elements of  the menu option array passed to it
			 *  2. Ask the user to pick one
			 *  3. return the constant defined in the menu option array passed to it
			 *     for the option the user picked
			 **********************************************************************/ 
			
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			/**********************************************************************
			 * Examine choice made and process accordingly
			 **********************************************************************/ 
			
			if(choice.equals(MAIN_MENU_OPTION_1)) {
				// process for option 1 choice - good place for a method call
			} else if(choice.equals(MAIN_MENU_OPTION_2)) {
				// process for option 2 choice - good place for a method call
			} else if(choice.equals(MAIN_MENU_OPTION_3)) {
				// process for option 3 choice - good place for a method call
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// do any end of loop processing
			       shouldLoop = false;	  
			  }		  
		}
	}

/**********************************************************************
 * main() method invokes main processing method run()
 **********************************************************************/ 			
	
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);  // Define Menu object with input and output files
		MenuCLI mainMenu = new MenuCLI(menu);		  // Define a MenuClI object to handle menus
		mainMenu.run();     						  // Process using MenuCLI control logic
	}
}
