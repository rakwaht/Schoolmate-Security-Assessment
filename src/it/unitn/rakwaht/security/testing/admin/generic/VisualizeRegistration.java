package it.unitn.rakwaht.security.testing.admin.generic;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VisualizeRegistration {

	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Registration");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Registration");
		tester.setWorkingForm("registration");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Show in Grid");
        tester.assertMatch("Student1 Student1's Schedule");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Registration");
		tester.setWorkingForm("registration");
        tester.setHiddenField("page2","29'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"registration\"]",tester);
        tester.submit();
        tester.assertMatch("Student1 Student1's Schedule");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void semester_name() {
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		String key = "-1";
		try {
			key = sacu.createSemesterName("<a href=w>k</a>");
		} catch (SQLException e) {
			System.err.println(e + " creation of semester failed");
		}
		tester.clickLinkWithExactText("Registration");
		tester.assertMatch("Registration");
		tester.selectOptionByValue("semester", key);
		tester.clickButtonWithText("Show in Grid");
        tester.assertMatch("Student1 Student1's Schedule");
		tester.assertLinkNotPresentWithText("k");
	}
	
	
	@After
	public void restore() {
		tester.assertMatch("Registration");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreSemesters();
		} catch (SQLException e) {
			System.err.println(e + " restore of semester or courses failed");
		}
	}
}
