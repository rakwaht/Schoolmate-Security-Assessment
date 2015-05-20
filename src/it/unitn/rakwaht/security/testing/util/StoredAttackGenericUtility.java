package it.unitn.rakwaht.security.testing.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.jwebunit.junit.WebTester;

public class StoredAttackGenericUtility {

	private WebTester tester;
	
	public String createCoursename(String coursenamecontent) throws SQLException{
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Classes");
		tester.clickButtonWithText("Add");
        tester.assertMatch("Add New Class");
        tester.setTextField("title", coursenamecontent);
        tester.setTextField("sectionnum", "1");
        tester.setTextField("roomnum", "1");
        tester.setTextField("periodnum", "1");
        tester.checkCheckbox("Days[]", "M");
        tester.clickButtonWithText("Add Class");
        int key = - 1;
        Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select courseid from courses where courseid in (select MAX(courseid) from courses)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("courseid");
			    stmt.execute("INSERT INTO registrations(courseid, studentid, semesterid, termid) "
					+ "VALUES('"+ key +"', '1', '1', '1')");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}
	
	public String createSemesterName(String semesternamecontent) throws SQLException{
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Semesters");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Semester");
		tester.setTextField("title", semesternamecontent);
        tester.setTextField("startdate", "14/10/2014");
        tester.setTextField("middate", "14/11/2014");
        tester.setTextField("enddate", "14/12/2014");
        tester.clickButtonWithText("Add Semester");
        int key = - 1;
        Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select semesterid from semesters where semesterid in (select MAX(semesterid) from semesters)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("semesterid");
			}
			stmt.close(); 
		}
		return Integer.toString(key);
	}
	
	public String createTermName(String termname) throws SQLException{
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Terms");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Term");
		tester.setTextField("title", termname);
        tester.setTextField("startdate", "14/10/2014");
        tester.setTextField("enddate", "14/11/2014");
        tester.clickButtonWithText("Add Term");
        int key = - 1;
        Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select termid from terms where termid in (select MAX(termid) from terms)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("termid");
			}
			stmt.close(); 
		}
		return Integer.toString(key);
	}
	
	public String createAnnouncements(String title, String message) throws SQLException{
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("Manage Announcements");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Announcement");
		tester.setWorkingForm("addannouncement");
		tester.setTextField("title", title);
		tester.setTextField("message", message);
		tester.clickButtonWithText("Add announcement");
		int key = - 1;
        Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select sbulletinid from schoolbulletins where sbulletinid in (select MAX(sbulletinid) from schoolbulletins)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("sbulletinid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}
	
	public String createAssignment(String title, String task) throws SQLException{
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "teacher");
		tester.setTextField("password", "teacher");
		tester.submit();
		tester.clickLinkWithExactText("Class");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Assignment");
		tester.setWorkingForm("addassignment");
		tester.setTextField("title", title);
		tester.setTextField("task", task);
		tester.setTextField("total", "10");
		tester.setTextField("assigneddate", "1");
		tester.setTextField("duedate", "1");
		tester.clickButtonWithText("Add Assignment");
		int key = - 1;
        Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			ResultSet rs = stmt.executeQuery("select assignmentid from assignments where assignmentid in (select MAX(assignmentid) from assignments)");
			if (rs != null && rs.next()) {
			    key = rs.getInt("assignmentid");
			}
			stmt.close(); 
		}
        return Integer.toString(key);
	}
	
	public void restoreCourses() throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from courses where courseid>1");
			stmt.execute("delete from registrations where courseid>1");
			stmt.close();
		}               
	}
	
	public void restoreSemesters() throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from semesters where semesterid>1");
			stmt.close();
		}               
	}
	
	public void restoreTerms() throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from terms where termid>1");
			stmt.close();
		}               
	}

	public void restoreAnnouncements() throws SQLException {
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from schoolbulletins where sbulletinid>1");
			stmt.close();
		}  
	}
	
	public void restoreAssignments() throws SQLException {
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from assignments where assignmentid>1");
			stmt.close();
		}  
	}

}
