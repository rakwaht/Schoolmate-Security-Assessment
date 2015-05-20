package it.unitn.rakwaht.security.testing.admin.add;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddAttendance {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.assertMatch("Manage Classes");
		tester.clickLinkWithExactText("Attendance");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Attendance");
        tester.setWorkingForm("registration");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Add");
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Attendance");
        tester.setWorkingForm("registration");
        tester.setHiddenField("page2","31'><a href=www.unitn.it>malicious link</a> <br ");
        tester.setHiddenField("addattend","1'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"registration\"]",tester);
        tester.submit();     
        tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	
	@Test
	public void semester(){
		tester.assertMatch("Attendance");
        tester.setWorkingForm("registration");
        tester.getElementByXPath("//form[@name='registration']/select[@name='semester']/option[@value=1]").setAttribute("value", "1'><a href=www.unitn.it>malicious link</a><br '");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void student(){
		tester.assertMatch("Attendance");
        tester.setWorkingForm("registration");
        tester.getElementByXPath("//form[@name='registration']/select[@name='student']/option[@value=1]").setAttribute("value", "1'><a href=www.unitn.it>malicious link</a><br '");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Attendance Record");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
	
}
