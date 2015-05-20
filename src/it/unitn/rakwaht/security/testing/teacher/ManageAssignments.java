package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageAssignments {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "teacher");
		tester.setTextField("password", "teacher");
		tester.submit();
		tester.clickLinkWithExactText("Class");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
		tester.setHiddenField("page", "2'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
        tester.setHiddenField("page2","2'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"teacher\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Assignments");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void onpage(){
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
		tester.setWorkingForm("assignments");
        Manager.addNewSubmitButton("/html//form[@name=\"assignments\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Assignments");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
		tester.setHiddenField("selectclass", "1' -- '> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass_MySqlError(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
        tester.setHiddenField("selectclass","1 -- '> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("ManageAssignments.php: Unable to get the course name");
		tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void coursename() throws SQLException{
		tester.assertMatch("Class Settings");
		String key = "-1";
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			key = sacu.createCoursename("</h2><a href=w>j</a>");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.setWorkingForm("teacher");
		tester.setHiddenField("selectclass", key);
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
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
		tester.assertMatch("Manage Assignments");
        tester.assertLinkNotPresentWithText("j");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore() throws SQLException{
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreCourses();
			sacu.restoreAssignments();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}         
	}
}
