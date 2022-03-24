// import the packages that allow me to build my GUI
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

import com.toedter.calendar.JDateChooser;

import java.awt.*;

public class Flights implements ActionListener {  //action listeners define what should be done when an user performs certain operation

	//A global variable is one declared at the start of the code and is accessible to all parts of the program.
	JButton search; // declared outside the constructor to have global scope
	
	JComboBox departures_dropdown;  // declared outside the constructor to have global scope
	JComboBox return_dropdown;   // declared outside the constructor to have global scope
	
	JDateChooser depart_date;   // declared outside the constructor to have global scope 
	JDateChooser return_date;  // declared outside the constructor to have global scope
	
	JFrame frame;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY"); 	  //  formats the current date in the format:
	String today = format.format(Calendar.getInstance().getTime());	  //  day,month,year   eg: 02/03/2022 or 22/10/2022
	
	Flights() {
		
		frame = new JFrame();
		
		
		JLabel bcl = new JLabel("BCL Airport");
		bcl.setForeground(Color.blue);
		bcl.setBounds(20,20,100,30);
		
		JLabel from = new JLabel("from");
		from.setForeground(Color.blue);
		from.setBounds(130,80,100,30);
		
		JLabel to = new JLabel("to");
		to.setForeground(Color.blue);
		to.setBounds(470,80,100,30);
		
		JLabel depart_label = new JLabel("depart");
		depart_label.setForeground(Color.blue);
		depart_label.setBounds(130,200,100,30);
		
		JLabel return_label = new JLabel("return");
		return_label.setForeground(Color.blue);
		return_label.setBounds(470,200,100,30);
		
		search = new JButton("Search");
		search.setForeground(Color.white);
		search.setBackground(Color.blue);
		search.setFocusable(false);
		search.setBounds(305,300,100,30);
		
		String depart_cities[] = {"Amsterdam","Athens","Bangkok","Cairo","Delhi","Dubai","Dublin","Hong Kong",
									"Johannesburg","Lagos","Las Vegas", "Lisbon","London", "Marid", "Marrakesh", "Mexico",
										"Moscow","New York", "Paris", "Rome","San Paolo", "Sweden", "Sydney", "Texas", "Tokyo",
											"Toronto"};
		departures_dropdown = new JComboBox(depart_cities);
		departures_dropdown.setBounds(190,80,150,30);
		
		String return_cities[] = {"Amsterdam","Athens","Bangkok","Cairo","Delhi","Dubai","Dublin","Hong Kong",
				"Johannesburg","Lagos","Las Vegas", "Lisbon","London", "Marid", "Marrakesh", "Mexico",
					"Moscow","New York", "Paris", "Rome","San Paolo", "Sweden", "Sydney", "Texas", "Tokyo",
						"Toronto",};
		return_dropdown = new JComboBox(return_cities);
		return_dropdown.setBounds(520,80,150,30);
		
		depart_date = new JDateChooser();
		depart_date.setBounds(190,200,150,30);
		
		return_date = new JDateChooser();
		return_date.setBounds(520,200,150,30);
		
		search.addActionListener(this);
		
		frame.add(bcl);
		frame.add(from);
		frame.add(to);
		frame.add(depart_label);
		frame.add(return_label);
		frame.add(search);
		frame.add(departures_dropdown);
		frame.add(return_dropdown);
		frame.add(depart_date);
		frame.add(return_date);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,400);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
	
	// d1 = first date that user inputs eg: date1
	// d2 = second date that user inputs eg: date2
	
	//CALCULATES THE DIFFERENCE IN THE NUMBER OF DAYS BETWEEN 2 DATES (DEPARTURE + ARRIVAL USER INPUT)	
		public int date_difference(String d1,String d2) {
			String[] d1_values = d1.split("/");
			String[] d2_values = d2.split("/");
			
			LocalDate d_1 = LocalDate.of(Integer.valueOf(d1_values[2]),Integer.valueOf(d1_values[1]),Integer.valueOf(d1_values[0]));
			LocalDate d_2 = LocalDate.of(Integer.valueOf(d2_values[2]),Integer.valueOf(d2_values[1]),Integer.valueOf(d2_values[0]));
			
			int days_difference = Period.between(d_1, d_2).getDays();
			
			return days_difference;
		}


	@Override //error handling
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==search) {
			

			if(depart_date.getDate()==null || return_date.getDate()==null) {
				JOptionPane.showMessageDialog(frame, "Departure and return date must not be empty");
			}
			else {
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
				
				String departDate = sdf.format(depart_date.getDate());
				String returnDate = sdf.format(return_date.getDate());
				
				String last_date = "29/04/2022";
				
				if(date_difference(departDate,today)>0 && date_difference(returnDate,today)>0) {
					JOptionPane.showMessageDialog(frame, "Departure and return date must not have already passed.");
				}
			
				else if(date_difference(departDate,today)>0) {
					JOptionPane.showMessageDialog(frame, "Departure date must not have already passed.");
				}
				
				else if(date_difference(returnDate,today)>0) {
					JOptionPane.showMessageDialog(frame, "Return date must not have already passed.");
				}
				
				else if(date_difference(departDate,returnDate)<0) {
					JOptionPane.showMessageDialog(frame, "Departure date must be before return date.");
				}
				
				else if(date_difference(departDate,returnDate)==0) {
					JOptionPane.showMessageDialog(frame, "Departure and return date must not be the same.");
				}
				
				else if(date_difference(departDate,last_date)<0 || date_difference(returnDate,last_date)<0) {
					JOptionPane.showMessageDialog(frame, "Departure and return date must be before 29/04/2022.");
				}

				else {
					
					String from = (String) departures_dropdown.getSelectedItem();
					String to = (String) return_dropdown.getSelectedItem();
					
					if(from.equals(to)) {
						JOptionPane.showMessageDialog(frame, "Departure and return city must not be the same.");
					}
					else {
						//makes an object of filtered flights class and runs the code in its constructor
						Filtered_Flights f = new Filtered_Flights(from,to,departDate,returnDate);
						
						//positions the window of the filtered flights gui in the middle of the screen
						f.setLocationRelativeTo(frame);
					}
				}
			}
			
		}
	}
	
		
}
	

