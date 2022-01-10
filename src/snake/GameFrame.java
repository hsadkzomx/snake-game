package snake;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	//constructor
	GameFrame(){
				
		this.add(new GamePanel());
		this.setTitle("Snake"); // add a title
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // if we add components to a JFrame, 
					 //this pack() function is going to  take over JFrame 
					 //and fit it surroundly around all the components that we add to the frame
		this.setVisible(true);
		this.setLocationRelativeTo(null); // the window to be appear in the middle of the screen
		
	}
}
