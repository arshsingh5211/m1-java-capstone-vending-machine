package com.techelevator.menu;
/**********************************************************************
 * This will demonstrate how to use the Menu process given to
 * students in the Tech Elevator Java bootcamp
 *********************************************************************/ 

import com.techelevator.view.Menu;  // Menu processing class

public class MenuCLI {  // Class representing the menu to be used
	
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

		/**********************************************************************
		 * Another example of using the MenuCLI class menus
		 **********************************************************************/ 
		
		shouldLoop = true;      // Loop control variable

		while(shouldLoop) {     // Loop while loop control variable is true
			
			String choice = (String)menu.getChoiceFromOptions(INSTRUCTOR_OPTIONS);
			
			if(choice.equals("Joe")) {
				// process for Joe processing - good place for a method call
			} else if(choice.equals("Frank")) {
				// process for Frank processing - good place for a method call
			} else if(choice.equals("Craig")) {
				// process for Craig processing - good place for a method call
			} else if(choice.equals("Josh")) {
				// process for Josh processing - good place for a method call 
			} else if(choice.equals("End Process")) {
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
