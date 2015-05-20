package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageGrades {
	
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
	}
	
	@Test
	public void page(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
		tester.setHiddenField("page", "2'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Grades");
		tester.assertMatch("Grades");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
        tester.setHiddenField("page2","3'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"teacher\"]",tester);
        tester.submit();     
        tester.assertMatch("Grades");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("teacher");
		tester.setHiddenField("selectclass", "1' -- '> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Grades");
		tester.assertMatch("Grades");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	}
}
