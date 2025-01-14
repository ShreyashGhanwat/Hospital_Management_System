package Hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String[] args) {
		
		try {
			Scanner scanner = new Scanner(System.in);
			Connection connection=DriverManager.getConnection(url, username, password);
			Paitent paitent=new Paitent(connection,scanner);
			Doctor doctor = new Doctor(connection);
			
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				
				System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                
                int choice =scanner.nextInt();
                
                switch(choice) {
                case 1 :
                	// Add Paitent
                	paitent.addPatient();
                	System.out.println();
                	break;
                	
                case  2 :
                	// View Paitenets
                	paitent.viewPatients();
                	System.out.println();
                	break;


                case 3 :
                	// View Doctors
                	doctor.viewDoctors();
                	System.out.println();
                	break;


                	
                case 4 :
                	// Book Appointmenet
                	bookAppointment(paitent, doctor, connection, scanner);
                	System.out.println();
                	break;


                	
                case 5 :
                	//Exist
                	System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                	return ;
                	
                default :
                	System.out.println("Enter Invalid Choice!!!");
                }
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
				
	}
	
	public static void bookAppointment(Paitent paitent,Doctor doctor,Connection connection,Scanner scanner) {
		System.out.println("Enter Paitent ID: ");
		int paitientID=scanner.nextInt();
		
		System.out.println("Enter Doctor ID: ");
		int doctorID=scanner.nextInt();
		
		System.out.println("Enter Appointment Date(YYYY-MM-DD)");
		String appointmentDate=scanner.next();
		
		if(paitent.getPaitentbyID(paitientID) && doctor.getDoctorbyID(doctorID)) {
			if(checkDoctorAvailability(doctorID, appointmentDate, connection)) {
				 String appointmentQuery = "INSERT INTO appointments(paitent_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
				 try {
					 PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
					 preparedStatement.setInt(1,paitientID);
					 preparedStatement.setInt(2,doctorID);
					 preparedStatement.setString(3, appointmentDate);
					 
					 int affectedRows=preparedStatement.executeUpdate();
					 
					 if(affectedRows>0) {
						 System.out.println("Appointment Booked!");
					 }
					 else {
						 System.out.println("Failed to Book Appointment!");
					 }
				 }
				 catch(Exception e) {
					 System.out.println(e.getMessage());
				 }
			}
			else {
				System.out.println("Doctor not available on this date!!");
			}
		}
		else {
			System.out.println("Either Doctor or Paitent does not exist");
		}
	}

	private static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection) {
		
		String query="select * from appointments  WHERE doctor_id = ? AND appointment_date = ?";

		try {
			
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorID);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultset=preparedStatement.executeQuery();
			
			if(resultset.next()) {
				return false;
			}
			else {
				return true;
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
