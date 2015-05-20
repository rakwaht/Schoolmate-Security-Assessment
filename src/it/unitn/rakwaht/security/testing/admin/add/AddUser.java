package it.unitn.rakwaht.security.testing.admin.add;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddUser {

	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Users");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Users");
		tester.setWorkingForm("users");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Add");
        tester.assertMatch("Add New User");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Users");
		tester.setWorkingForm("users");
        tester.setHiddenField("page2","14'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"users\"]",tester);
        tester.submit();     
        tester.assertMatch("Add New User");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
	
}
