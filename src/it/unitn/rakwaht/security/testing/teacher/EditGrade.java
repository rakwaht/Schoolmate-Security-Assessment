package it.unitn.rakwaht.security.testing.teacher;

import it.unitn.rakwaht.security.testing.util.Manager;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditGrade {

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
		tester.assertMatch("Class Settings");
		tester.clickLinkWithExactText("Grades");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
        tester.setHiddenField("page","2'> <a href=www.unitn.it>malicious link</a> <br  '");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
        tester.setHiddenField("page2","7'><a href=www.unitn.it>malicious link</a><br ");
        Manager.addNewSubmitButton("/html//form[@name=\"grades\"]",tester);
        tester.checkCheckbox("delete[]","1");
        tester.submit();     
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void id(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
		tester.getElementByXPath("//input[@type='checkbox' and @value='1']").setAttribute("value", "1' -- '><a href=www.unitn.it>malicious link</a><br ");
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
	    tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void assignment(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
		tester.getElementByXPath("//select/option[@value='1']").setAttribute("value", "1' -- '><a href=www.unitn.it>malicious link</a><br ");//without closing the sql param
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void assignment_MysqlError(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
		tester.getElementByXPath("//select/option[@value='1']").setAttribute("value", "1'><a href=www.unitn.it>malicious link</a><br ");//without closing the sql param
		tester.checkCheckbox("delete[]");
		tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	
	
	@Test
	public void selectclass(){
		tester.assertMatch("Grades");
		tester.setWorkingForm("grades");
        tester.setHiddenField("selectclass","1'><a href=www.unitn.it>malicious link</a><br ");
        tester.checkCheckbox("delete[]","1");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("malicious link");
	}	

	@After
	public void restore(){    
	}
	
}
