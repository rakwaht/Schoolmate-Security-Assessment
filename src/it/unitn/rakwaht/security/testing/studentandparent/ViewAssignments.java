package it.unitn.rakwaht.security.testing.studentandparent;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewAssignments {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "parent");
		tester.setTextField("password", "parent");
		tester.submit();
		tester.clickLinkWithExactText("Student1 Student1");
		tester.clickLinkWithExactText("Class");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("page", "5'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("View Assignments");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("page2","2'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"student\"]",tester);
        tester.submit();     
        tester.assertMatch("View Assignments");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void onpage(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("View Assignments");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"assignments\"]",tester);
        tester.submit();     
        tester.assertMatch("View Assignments");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("selectclass", "1 -- '> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Assignments");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass_MySqlError(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
        tester.setHiddenField("selectclass","'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickLinkWithExactText("Assignments");
        //fixed string in ViewAssignment.php that said ManageAssignments.php: Unable to get the course name..
		tester.assertMatch("ManageAssignments.php: Unable to get the course name");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void coursename() {
		tester.assertMatch("Class Settings");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		String key = "-1";
		try {
			key = sacu.createCoursename("</h2><a href=w>j</a>");
		} catch (SQLException e) {
			System.err.println(e + " creation of course failed");
		}
		tester.setWorkingForm("student");
		tester.setHiddenField("selectclass", key);
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("View Assignments");
		tester.assertLinkNotPresentWithText("j");
	}
	
	@Test
	public void assignment(){
		tester.assertMatch("Class Settings");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createAssignment("<a href=w>j</a", "<a href=www.malicious.com>malicious link</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("View Assignments");
        tester.assertLinkNotPresentWithText("j");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore() {
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreCourses();
			sacu.restoreAssignments();
		} catch (SQLException e) {
			System.err.println(e + " restore of course failed");
		}  
	}
	
}
