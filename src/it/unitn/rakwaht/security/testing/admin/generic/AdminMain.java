package it.unitn.rakwaht.security.testing.admin.generic;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminMain {

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
		tester.setWorkingForm("admin");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickLinkWithExactText("Classes");
        tester.assertMatch("Manage Classes");
        tester.assertElementNotPresentByXPath("/html//form[@name=\"admin\"]/a[text()=\"malicious link\"]");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","0'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Classes");
        tester.assertElementNotPresentByXPath("/html//form[@name=\"admin\"]/a[text()=\"malicious link\"]");
	}
	
	@After
	public void restore(){
	               
	}
	
}
