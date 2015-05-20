package it.unitn.rakwaht.security.testing.admin.report;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GradeReport {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Students");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Students");
		tester.setWorkingForm("students");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.selectOption("report", "Grade Report");
        tester.assertMatch("Grade Report");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Students");
		tester.setWorkingForm("students");
        tester.setHiddenField("page2","28'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"students\"]",tester);
        tester.submit();
        tester.assertMatch("Grade Report");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void semester_name(){
		tester.assertMatch("Manage Students");
		tester.setWorkingForm("students");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createSemesterName("<a href=w>k</a>");
		} catch (SQLException e) {
			System.err.println(e + " creation of course failed");
		}
		tester.selectOption("report", "Grade Report");
        tester.assertMatch("Grade Report");
		tester.assertLinkNotPresentWithText("k");
	}
	
	
	@After
	public void restore(){
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreSemesters();
		} catch (SQLException e) {
			System.err.println(e + " restore of semester failed");
		}             
	}
}
