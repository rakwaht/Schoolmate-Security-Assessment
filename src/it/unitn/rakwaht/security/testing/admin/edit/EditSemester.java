package it.unitn.rakwaht.security.testing.admin.edit;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditSemester {

	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Semesters");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Semesters");
		tester.setWorkingForm("semesters");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Semesters");
		tester.setWorkingForm("semesters");
        tester.setHiddenField("page2","13'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"semesters\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void id(){
		tester.assertMatch("Manage Semesters");
		tester.setWorkingForm("semesters");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1 -- '><a href=www.unitn.it>malicious link</a><br ");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void id_MysqlError(){
		tester.assertMatch("Manage Semesters");
		tester.setWorkingForm("semesters");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "<a href=www.unitn.it>malicious link</a>");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertMatch("EditSemester.php: Unable to retrieve");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void semestername() {
		tester.assertMatch("Manage Semesters");
		String key = "-1";
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			key = sacu.createSemesterName("''/><a href>j<a");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.setWorkingForm("semesters");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", key);
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore(){
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreSemesters();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}             
	}

}
