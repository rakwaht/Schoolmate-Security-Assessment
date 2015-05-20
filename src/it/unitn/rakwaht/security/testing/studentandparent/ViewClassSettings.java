package it.unitn.rakwaht.security.testing.studentandparent;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewClassSettings {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "student");
		tester.setTextField("password", "student");
		tester.submit();
		tester.clickLinkWithExactText("Class");
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Assignments");
		tester.assertMatch("View Assignments");
	}
	
	@Test
	public void page(){
		tester.setWorkingForm("student");
		tester.setHiddenField("page", "4'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Settings");
		tester.assertMatch("Class Settings");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.setWorkingForm("student");
        tester.setHiddenField("page2","1'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"student\"]",tester);
        tester.submit();     
        tester.assertMatch("Class Settings");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass_MySqlError(){
		tester.setWorkingForm("student");
        tester.setHiddenField("selectclass","<a href=www.unitn.it>malicious link</a><br ");
        tester.clickLinkWithExactText("Settings");
		tester.assertMatch("ClassInfo.php: Unable to get the class information");
		tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void selectclass(){
		tester.setWorkingForm("student");
        tester.setHiddenField("selectclass","1 -- ''><a href=www.unitn.it>malicious link</a> <br");//double quote to escape sql
        tester.clickLinkWithExactText("Settings");
		tester.assertMatch("Class Settings");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
}
