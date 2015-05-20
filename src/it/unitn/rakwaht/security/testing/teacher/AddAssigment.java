package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddAssigment {

private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "teacher");
		tester.setTextField("password", "teacher");
		tester.submit();
	}
	
	@Test
	public void page(){
		tester.assertMatch("teacher teacher's Classes");
		tester.clickLinkWithExactText("Class");
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
        tester.setWorkingForm("assignments");
        tester.setHiddenField("page","2'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Add");
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("teacher teacher's Classes");
		tester.clickLinkWithExactText("Class");
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
        tester.setWorkingForm("assignments");
        tester.setHiddenField("page2","4'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"assignments\"]",tester);
        tester.submit();     
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	
	@Test
	public void selectclass(){
		tester.clickLinkWithExactText("Class");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("Manage Assignments");
		tester.setWorkingForm("assignments");
		tester.setHiddenField("selectclass", "2'><a href=www.unitn.it>malicious link</a><br  '");
		tester.clickButtonWithText("Add");
        tester.assertMatch("Add New Assignment");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
	
}
