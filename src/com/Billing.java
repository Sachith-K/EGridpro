package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Billing {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electripower?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
			"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public String insertBilling(String UserID, String UserEmail, String Date, String UnitsConsumed, String PricePerUnit) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into billsys(`BillID`,`UserID`,`UserEmail`,`Date`,`UnitsConsumed`,`PricePerUnit`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, UserID);
			preparedStmt.setString(3, UserEmail);
			preparedStmt.setString(4, Date);
			preparedStmt.setString(5, UnitsConsumed);
			preparedStmt.setString(6, PricePerUnit);
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Inserted successfully";
			
			String newBill = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
		} catch (Exception e) {
			//output = "Error while inserting the billing.";
			output = "{\"status\":\"error\", \"data\": \"Error while entering the bill.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBilling() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database.";
			}
			// Prepare the output
			output = "<table border=\"1\"><tr><th>User ID</th><th>User Email</th><th>Date</th><th>Units Consumed</th><th>Price Per Unit</th></tr>";
			String query = "select * from billsys";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String BillID = Integer.toString(rs.getInt("BillID"));
				String UserID = rs.getString("UserID");
				String UserEmail = rs.getString("UserEmail");
				String Date = rs.getString("Date");
				String UnitsConsumed = rs.getString("UnitsConsumed");
				String PricePerUnit = rs.getString("PricePerUnit");

				// Add into the html table
				output += "<tr><td><input id=\'hidBillIDUpdate\' name=\'hidBillIDUpdate\' type=\'hidden\' value=\'" + BillID + "'>" 
							+ UserID + "</td>";
				//output += "<tr><td>" + BillID + "</td>";
				//output += "<td>" + UserID + "</td>";
				output += "<td>" + UserEmail + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + UnitsConsumed + "</td>";
				output += "<td>" + PricePerUnit + "</td>";
				
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + BillID + "'>" + "</td></tr>"; 
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBilling(String BillID, String UserID, String UserEmail, String Date, String UnitsConsumed, String PricePerUnit) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE billsys SET UserID=?,UserEmail=?,Date=?,UnitsConsumed=?,PricePerUnit=?" + "WHERE BillID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, UserID);
			preparedStmt.setString(2, UserEmail);
			preparedStmt.setString(3, Date);
			preparedStmt.setString(4, UnitsConsumed);
			preparedStmt.setString(5, PricePerUnit);
			preparedStmt.setInt(6, Integer.parseInt(BillID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			//output = "Updated successfully";
			
			String newBill = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while updating.\"}";
			//output = "Error while updating the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteBilling(String BillID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from billsys where BillID =?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(BillID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			//output = "Deleted successfully";
			
			String newBill = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
			
		} catch (Exception e) {
			output = "Error while deleting the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
