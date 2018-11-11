package hello_console;

import javax.swing.*;     
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hello_World_Console {

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static JTextField myName;
    static JLabel label;
    static IOClass cr10Printer;

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Java IO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

        //Add the ubiquitous controls.
        label = new JLabel("Command");
	myName = new JTextField("",10);
	JButton btn=new JButton("Send");
	JButton btnRead=new JButton("Read");    
	//b.setBounds(100,100,140, 40);   

        frame.getContentPane().add(label);
 	frame.getContentPane().add(myName);
	frame.getContentPane().add(btn);
	frame.getContentPane().add(btnRead);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	
	//lets add some listeners
	btn.addActionListener(new ActionListener() {
        	@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "Send Pressed " + myName.getText() );				
			JOptionPane.showMessageDialog(null,"Returned " + cr10Printer.send(myName.getText()));
		}          
      	});
	btnRead.addActionListener(new ActionListener() {
        	@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, cr10Printer.read());				
		}          
      	});	
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
		cr10Printer = new IOClass();
		try
            	{
			cr10Printer.connect();
		}
		catch ( Exception e )
            	{
                	e.printStackTrace();
            	}   
            }
        });
    }
}
