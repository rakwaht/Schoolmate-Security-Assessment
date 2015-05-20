package it.unitn.rakwaht.security.testing.admin.manage;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageClasses {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("School");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("admin");
		tester.setHiddenField("page", "1'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Manage Classes");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","0'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Classes");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void onpage(){
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Manage Classes");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
		tester.setWorkingForm("classes");
        Manager.addNewSubmitButton("/html//form[@name=\"classes\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Classes");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void coursename() {
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createCoursename("''/><a href=ww>j</a>");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Manage Classes");
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
