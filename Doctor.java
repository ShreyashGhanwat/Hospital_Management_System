package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Doctor {

	private Connection connection;
	

	public Doctor(Connection connection) {
		this.connection=connection;
		
	}
	public void viewDoctors() {
		String query="select * from doctors";
		
		try {
		
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultset=preparedStatement.executeQuery();
			System.out.println("Doctors: ");
            System.out.println("+------------+-------------------+--------------------+");
            System.out.println("| Doctor Id  | Name              | specialization     |");
            System.out.println("+------------+-------------------+--------------------+");
			while(resultset.next()) {
				int id=resultset.getInt("id");
				String name=resultset.getString("name");
				String specialization=resultset.getString("specialization");
				
				System.out.printf("| %-10s | %-17s | %-18s |\n",id,name,specialization);
	            System.out.println("+------------+-------------------+--------------------+");

				
		//		System.out.println("Patients id: "+id);
		//		System.out.println("Patients name: "+name);
		//		System.out.println("Patients age: "+age);
		//		System.out.println("Patients gender: "+gender);
				

			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean getDoctorbyID(int id) {
		try {
		String query="select * from doctors where id=?";
		
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
