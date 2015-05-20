package it.unitn.rakwaht.security.testing.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.jwebunit.junit.WebTester;

public class StoredAttackUsersUtility {
	
	private WebTester tester;
	
	public String createStudent(String firstname, String lastname) throws SQLException {
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		this.newTempUser("Student");
		tester.clickLinkWithExactText("Students");
		tester.assertMatch("Manage Students");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Student");
		tester.setWorkingForm("addstudent");
		tester.setTextField("fname", firstname);
		tester.setTextField("lname", lastname);
		tester.setTextField("mi", "M");
		tester.clickButtonWithText("Add Student");
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		int key = - 1;
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select studentid from students where studentid in (select MAX(studentid) from students)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("studentid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}
	
	private void newTempUser(String role) throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			stmt.execute("INSERT INTO users (username, password, type) VALUES "
					+ "('temporary','098f6bcd4621d373cade4e832627b4f6','"+ role + "');");
			stmt.close(); 
		}
		System.err.println("User created");
	}
	
	private void deleteTempUser() throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			stmt.execute("delete from users where username='temporary'");
			stmt.close(); 
		}
	}
	
	public void restoreStudent() throws SQLException{
		this.deleteTempUser();
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			int key = -1;
			ResultSet rs = stmt.executeQuery("select studentid from students where studentid in (select MAX(studentid) from students)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("studentid");
			}
			if(key >1){
				stmt.execute("delete from students where studentid=" + key);
				stmt.execute("delete from parent_student_match where mathcid>1");
			}
			stmt.close(); 
		}
	}
	
	public void restoreParent() throws SQLException{
		this.deleteTempUser();
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			int key = -1;
			ResultSet rs = stmt.executeQuery("select parentid from parents where parentid in (select MAX(parentid) from parents)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("parentid");
			}
			if(key >1){
				stmt.execute("delete from parents where parentid=" + key);
			}
			stmt.close(); 
		}
	}

	public String createParentname(String firstname, String lastname) throws SQLException {
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Parents");
		tester.assertMatch("Manage Parents");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Parent");
		tester.setWorkingForm("addparent");
		tester.setTextField("fname", firstname);
		tester.setTextField("lname", lastname);
		tester.clickButtonWithText("Add Parent");
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		int key = - 1;
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select parentid from parents where parentid in (select MAX(parentid) from parents)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("parentid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}

	public String createTeacher(String firstname, String lastname) throws SQLException {
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		this.newTempUser("Teacher");
		tester.clickLinkWithExactText("Teachers");
		tester.assertMatch("Manage Teacher");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Teacher");
		tester.setWorkingForm("addteacher");
		tester.setTextField("fname", firstname);
		tester.setTextField("lname", lastname);
		tester.clickButtonWithText("Add Teacher");
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		int key = - 1;
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select teacherid from teachers where teacherid in (select MAX(teacherid) from teachers)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("teacherid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}
	
	public String createUser(String username) throws SQLException {
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Users");
		tester.assertMatch("Manage Users");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New User");
		tester.setWorkingForm("adduser");
		tester.setTextField("username", username);
		tester.setTextField("password", "password");
		tester.setTextField("password2", "password");
		tester.clickButtonWithText("Add User");
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		int key = - 1;
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select userid from users where userid in (select MAX(userid) from users)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("userid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}

	public void restoreUser() throws SQLException {
		this.deleteTempUser();
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			int key = -1;
			ResultSet rs = stmt.executeQuery("select userid from users where userid in (select MAX(userid) from users)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("userid");
			}
			if(key > 6){ //first six users are real
				stmt.execute("delete from users where userid=" + key);
			}
			stmt.close(); 
		}
	}
	
	public void restoreTeacher() throws SQLException {
		this.deleteTempUser();
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			int key = -1;
			ResultSet rs = stmt.executeQuery("select teacherid from teachers where teacherid in (select MAX(teacherid) from teachers)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("teacherid");
			}
			if(key >1){
				stmt.execute("delete from teachers where teacherid=" + key);
			}
			stmt.close(); 
		}
	}
}
