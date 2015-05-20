package it.unitn.rakwaht.security.testing.admin.report;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeficiencyReport {
	
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
        tester.selectOption("report", "Deficiency Report");
        tester.assertMatch("Deficiency Report");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Students");
		tester.setWorkingForm("students");
        tester.setHiddenField("page2","27'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"students\"]",tester);
        tester.submit();
        tester.assertMatch("Deficiency Report");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	
	@After
	public void restore(){
	               
	}
}
