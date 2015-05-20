package it.unitn.rakwaht.security.testing.admin.edit;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackUsersUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditParent {
private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Parents");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Parents");
		tester.setWorkingForm("parents");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Parents");
		tester.setWorkingForm("parents");
        tester.setHiddenField("page2","24'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"parents\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void id(){
		tester.assertMatch("Manage Parents");
		tester.setWorkingForm("parents");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1 /*'><a href=www.unitn.it>malicious link</a><br */");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void id_MysqlError(){
		tester.assertMatch("Manage Parents");
		tester.setWorkingForm("parents");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "<a href=www.unitn.it>malicious link</a>");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertMatch("EditParent.php: Unable to retrieve");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void student(){
		tester.assertMatch("Manage Parents");
		tester.setWorkingForm("parents");
		tester.setHiddenField("page2","24'><a href=www.unitn.it>malicious link</a><br ");
        tester.setHiddenField("studentid","1 --'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"parents\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void parentname() {
		tester.assertMatch("Manage Parents");
		String key = "-1";
		StoredAttackUsersUtility sacu = new StoredAttackUsersUtility();
		try {
			key = sacu.createParentname("''/><a href>j<a","''/><a href>k<a");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.setWorkingForm("parents");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", key);
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("k");
	    tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore(){
		StoredAttackUsersUtility sacu = new StoredAttackUsersUtility();
		try {
			sacu.restoreParent();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}             
	}
}
