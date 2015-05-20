package it.unitn.rakwaht.security.testing.admin.edit;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditClass {

	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page2","11'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"classes\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void id(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1 -- '><a href=www.unitn.it>malicious link</a><br ");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void id_MysqlError(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1><a href=www.unitn.it>malicious link</a><br ");//without closing the sql param
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertMatch("EditClass.php: Unable to retrieve");
        tester.assertLinkNotPresentWithText("malicious link");
	}	

	@Test
	public void coursename() {
		tester.assertMatch("Manage Classes");
		String key = "-1";
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			key = sacu.createCoursename("''/><a href=ww>j</a>");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
		tester.checkCheckbox("delete[]", key);
		tester.clickButtonWithText("Edit");
		tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore() {
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreCourses();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}         
	}
	
}
