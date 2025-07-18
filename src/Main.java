package TaskScheduler;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			System.out.println("Could not set look and feel. ");
		}
		
		SwingUtilities.invokeLater(() -> {
			TaskSchedularUI ui = new TaskSchedularUI();
			ui.setVisible(true);
		});
	}

}
