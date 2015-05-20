package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditAssignment {

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
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Assignments");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
        tester.setHiddenField("page","2'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
        tester.setHiddenField("page2","5'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"assignments\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass(){
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
        tester.setHiddenField("selectclass","1'><a href=www.unitn.it>malicious link</a><br ");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void id(){
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1 -- '><a href=www.unitn.it>malicious link</a><br ");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void id_MysqlError(){
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "<a href=www.unitn.it>malicious link</a>");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void assignment(){
		tester.assertMatch("Manage Assignments");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		String key = "-1";
		try {
			key = sacu.createAssignment("''><a href>j</a", "</textarea><a href=www.malicious.com>malicious link</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tester.setWorkingForm("assignments");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", key);
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore(){
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreAssignments();
		} catch (SQLException e) {
			System.err.println(e + " restore of course failed");
		}         
	}
	
}
