package it.unitn.rakwaht.security.testing.admin.add;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddClass {

	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("Classes");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Add");
        tester.assertMatch("Add New Class");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page2","9'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"classes\"]",tester);
        tester.submit();     
        tester.assertMatch("Add New Class");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void page_FullYear(){
		tester.assertMatch("Manage Classes");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Class");
		tester.setWorkingForm("addclass");
        tester.setHiddenField("page","1'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Full Year");
        tester.assertMatch("Add New Class");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2_FullYear(){
		tester.assertMatch("Manage Classes");
		tester.clickButtonWithText("Add");
		tester.assertMatch("Add New Class");
		tester.setWorkingForm("addclass");
        tester.setHiddenField("page2","9'><a href=www.unitn.it>malicious link</a> <br ");
        tester.setHiddenField("fullyear","1");
        Manager.addNewSubmitButton("/html//form[@name=\"addclass\"]",tester);
        tester.submit();     
        tester.assertMatch("Add New Class");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
	
}
