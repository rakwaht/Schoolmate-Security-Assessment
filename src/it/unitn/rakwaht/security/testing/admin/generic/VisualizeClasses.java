package it.unitn.rakwaht.security.testing.admin.generic;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VisualizeClasses {
	
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
        tester.clickButtonWithText("Show in Grid");
        tester.assertMatch("School Class Schedule");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page2","25'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"classes\"]",tester);
        tester.submit();
        tester.assertMatch("School Class Schedule");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void semester_name(){
		tester.assertMatch("Manage Classes");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		String key = "";
		try {
			key = sacu.createSemesterName("<a href=w>k</a>");
		} catch (SQLException e) {
			System.err.println(e + " semester creation faild");
		}
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Manage Classes");
		tester.selectOptionByValue("semester", key);
		tester.clickButtonWithText("Show in Grid");
        tester.assertMatch("School Class Schedule");
		tester.assertLinkNotPresentWithText("k");
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
