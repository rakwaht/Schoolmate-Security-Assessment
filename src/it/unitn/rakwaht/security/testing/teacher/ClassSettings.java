package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassSettings {
	
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
		tester.setWorkingForm("classes");
        tester.setHiddenField("page","2'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.clickButtonWithText("Update");
        tester.assertMatch("Class Settings");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("classes");
        tester.setHiddenField("page2","1'><a href=www.unitn.it>malicious link</a><br ");
        tester.clickButtonWithText("Update");        
        tester.assertMatch("Class Settings");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass_MySqlError(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("classes");
        tester.setHiddenField("selectclass","1'><a href=www.unitn.it>malicious link</a><br ");
        tester.clickButtonWithText("Update");        
        tester.assertMatch("ClassSettings.php: Unable to update the grading scale");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void selectclass(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("classes");
        tester.setHiddenField("selectclass","1 -- ''><a href=www.unitn.it>malicious link</a> <br");//double quote to escape sql
        tester.clickButtonWithText("Update");        
        tester.assertMatch("Class Settings");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@After
	public void restore(){
	               
	}
}
