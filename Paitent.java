package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Paitent {
	
	private Connection connection;
	private Scanner scanner;
	
	public Paitent(Connection connection,Scanner scanner) {
		 this.connection=connection;
		 this.scanner=scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter Paitent Name: ");
		String name=scanner.next();
		System.out.println("Enter Paitent Age: ");
		int age = scanner.nextInt();
		System.out.println("Enter Paitent Gender: ");
		String gender=scanner.next();
		
		try {
			String query="insert into paitents(name,age,gender) values (?,?,?)";
			
			PreparedStatement preparedstatement= connection.prepareStatement(query);
			preparedstatement.setString(1, name);
			preparedstatement.setInt(2,age);
			preparedstatement.setString(3,gender);
			
			int affectedRows=preparedstatement.executeUpdate();
			
			if(affectedRows>0) {
				System.out.println("Patient Added Successfully!!");
			}
			else {
				System.out.println("Failed to add Patient!!");
			}
			
					
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void viewPatients() {
		String query="select * from paitents";
		
		try {
		
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultset=preparedStatement.executeQuery();
			System.out.println("Patients: ");
            System.out.println("+------------+-------------------+----------+------------+");
            System.out.println("| Patient Id | Name              | Age      | Gender     |");
            System.out.println("+------------+-------------------+----------+------------+");
			while(resultset.next()) {
				int id=resultset.getInt("id");
				String name=resultset.getString("name");
				int age=resultset.getInt("age");
				String gender=resultset.getString("gender");
				
				System.out.printf("| %-10s | %-17s | %-8s | %-10s |\n",id,name,age,gender);
	            System.out.println("+------------+-------------------+----------+------------+");

				
		//		System.out.println("Patients id: "+id);
		//		System.out.println("Patients name: "+name);
		//		System.out.println("Patients age: "+age);
		//		System.out.println("Patients gender: "+gender);
				

			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean getPaitentbyID(int id) {
		try {
		String query="select * from paitents where id=?";
		
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet resultset=preparedStatement.executeQuery();
		if(resultset.next()) {
			return true;
		}
		else {
			return false;
		}
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
