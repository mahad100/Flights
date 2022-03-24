import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Filtered_Flights extends JFrame {

	String file = "Flights.csv";

	SimpleDateFormat current_day_format = new SimpleDateFormat("dd/MM/YYYY");
	String today = current_day_format.format(Calendar.getInstance().getTime());

	Object[] d_row = new Object[8];
	JTable d_table;
	DefaultTableModel d_model;

	Object[] a_row = new Object[8];
	JTable a_table;
	DefaultTableModel a_model;

	Filtered_Flights(String from, String to, String depart_date, String return_date) {
					setTitle("Filtered Flights");

		JButton selectseats = new JButton("next");
		selectseats.setForeground(Color.white);
		selectseats.setBackground(Color.blue);
		selectseats.setFocusable(false);
		selectseats.setBounds(768,550,70,30);

		JLabel available_flights = new JLabel("Available Flights");
		available_flights.setBounds(85,35,270,30);
		available_flights.setFont(new Font("Verdana",Font.BOLD,21));
		available_flights.setForeground(new Color(0x2A9296));

		JLabel departures_label = new JLabel("Departures");
		departures_label.setBounds(85,85,100,30);
		departures_label.setFont(new Font("Verdana",Font.BOLD,15));
		departures_label.setForeground(new Color(0x2A9296));

		JLabel arrivals_label = new JLabel("Arrivals");
		arrivals_label.setBounds(85,350,100,30);
		arrivals_label.setFont(new Font("Verdana",Font.BOLD,15));
		arrivals_label.setForeground(new Color(0x2A9296)); 

		//JButton seating_plan = new Jbutton("View seats"); 
		//seating_plan.setBounds(85,200,200,200); 
		//seating_plan.setFont(new Font("Verdana",Font.BOLD,15)); 
		//arrivals_label.setForeground(new Color(0x2A9296));
		//this.add(seating_plan); 

		Object[] d_cols = {"Departing From", "Airport", "Arriving To", "Airport", "Departure Time", "Arrival Time","Flight Number", "Flight Duration"};
		d_table = new JTable();
		d_model = new DefaultTableModel();
		d_model.setColumnIdentifiers(d_cols);
		d_table.setModel(d_model);
		d_table.setBackground(Color.WHITE);
		d_table.setBounds(85,125,680,200);
		d_table.setRowHeight(30);

		JScrollPane d_scroll = new JScrollPane(d_table);
		d_scroll.setBounds(85,125,680,200); 



		final String DepartureFlightTimeString = add_departure_values(from,to,depart_date);

		Object[] a_cols = {"Departing From", "Airport", "Arriving To", "Airport", "Departure Time", "Arrival Time","Flight Number", "Flight Duration"};
		a_table = new JTable();
		a_model = new DefaultTableModel();
		a_model.setColumnIdentifiers(a_cols);
		a_table.setModel(a_model);
		a_table.setBackground(Color.WHITE);
		a_table.setBounds(85,125,680,200);
		a_table.setRowHeight(30);

		JScrollPane a_scroll = new JScrollPane(a_table);
		a_scroll.setBounds(85,395,680,200);

		final String ReturnFlightTimeString = add_arrival_values(from,to,return_date);

		add(available_flights);
		add(departures_label);

		add(d_scroll);
		add(arrivals_label);
		add(a_scroll);

		add(selectseats);

		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(850,650);
		setResizable(false);
		setVisible(true);  
		selectseats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println(DepartureFlightTimeString);
				System.out.println(ReturnFlightTimeString);
				System.out.println("hi");


			}

		});
	}



	//INSERTS VALID VALUES INTO DEPARTURES TABLE	
	public String add_departure_values(String from, String to, String depart_date) {

		String line = "";
		int counter = 0; 
		String DepartureFlightTimeString = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));


			while((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if((values[0].equals(depart_date)) && values[7].equals(from) && values[9].equals(to)) {
					d_row[0] = values[7];
					d_row[1] = values[6];
					d_row[2] = values[9];
					d_row[3] = values[8];
					d_row[4] = values[1];
					d_row[5] = values[2];
					d_row[6] = values[10];
					d_row[7] = values[3]; 



					d_model.addRow(d_row);  
					DepartureFlightTimeString = values[3];


				} 
			}br.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DepartureFlightTimeString;
	}	


	//INSERTS VALID VALUES INTO ARRIVALS TABLE
	public String add_arrival_values(String from, String to, String return_date) {

		String line = "";
		int counter = 0; 
		String ReturnFlightTimeString = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			while((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if((values[0].equals(return_date)) && values[7].equals(to) && values[9].equals(from)) {
					a_row[0] = values[7];
					a_row[1] = values[6];
					a_row[2] = values[9];
					a_row[3] = values[8];
					a_row[4] = values[1];
					a_row[5] = values[2];
					a_row[6] = values[10];
					a_row[7] = values[3];


					a_model.addRow(a_row); 
					ReturnFlightTimeString = values[3];

				}
			} br.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ReturnFlightTimeString;
	}

}
//on my last screen I need a button, "view seating plan" so that when it is pressed i need listeners so that it grabs the flight number 