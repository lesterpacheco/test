package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLCalls_fixed {

	// Test SQL Injection
	public static Map<String, String> fireSQLQuery(String queryParam) {
		Map<String, String> employeeDetails = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			// MySQL Connection URL
			String myUrl = "jdbc:mysql://localhost:4406/test";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, "root", "root");
			String query = "select * from employee where name=?";
			System.out.println("SQL Query : " + query);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, queryParam);
			rs = stmt.executeQuery();
			employeeDetails = new HashMap<String, String>();
			while (rs.next()) {
				String emp_id = rs.getString("id");
				String emp_name = rs.getString("name");
				String emp_email = rs.getString("email");
				String emp_dob = rs.getString("dob");
				String emp_address = rs.getString("address");
				System.out.println("Employee Id:" + emp_id + ", Employee Name:" + emp_name);
				employeeDetails.put(emp_id, "Name : " + emp_name + " Email :" + emp_email + " DOB :" + emp_dob
						+ " Address :" + emp_address);
			}
		} catch (Exception e) {
			System.err.println("Got an exception!!!!");
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return employeeDetails;
	}

	public static void main(String args[]) throws Exception {

		String queryParam = "Scott";
		// SQL Injection attack
		// String queryParam = "John' or '1'='1";
		// String queryParam = "' or '1'='1";
		fireSQLQuery(queryParam);

	}

}
