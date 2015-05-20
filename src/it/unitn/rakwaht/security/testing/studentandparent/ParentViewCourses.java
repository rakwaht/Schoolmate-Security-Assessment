package it.unitn.rakwaht.security.testing.studentandparent;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParentViewCourses {
	
	private WebTester tester;
	
	@Before
	public void setup(){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "parent");
		tester.setTextField("password", "parent");
		tester.submit();
		tester.clickLinkWithExactText("Student1 Student1");
		tester.clickLinkWithExactText("Class");
	}
	
	@Test
	public void page(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("page", "5'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Student1 Student1's Classes");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("page2","5'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"student\"]",tester);
        tester.submit();     
        tester.assertMatch("Student1 Student1's Classes");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void student(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("student","1 -- '><a href=www.unitn.it>malicious link</a> <br ");
		tester.clickLinkWithExactText("Classes");
        tester.assertMatch("Student1 Student1's Classes");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void student_SqlError(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("student","<a href=www.unitn.it>malicious link</a> <br ");
		tester.clickLinkWithExactText("Classes");  
        tester.assertMatch("ParentViewCourses.php: Unable to get the studentid ");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void coursename(){
		tester.assertMatch("Class Settings");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createCoursename("</a><a href=www>link");
		} catch (SQLException e) {
			System.err.println(e + " creation of course failed");
		}
		tester.clickLinkWithExactText("Classes");
		tester.assertMatch("Student1 Student1's Classes");
		tester.assertLinkNotPresentWithText("link");
	}
	
	@After
	public void restore(){
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreCourses();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}    
	}
}
