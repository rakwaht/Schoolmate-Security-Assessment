package it.unitn.rakwaht.security.testing.studentandparent;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ViewAnnouncements {
	
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
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("View Announcements");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("page2","4'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"student\"]",tester);
        tester.submit();     
        tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void onpage(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("View Announcements");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"announcements\"]",tester);
        tester.submit();     
        tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void selectclass(){
		tester.assertMatch("Class Settings");
		tester.setWorkingForm("student");
		tester.setHiddenField("selectclass", "1 -- '> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Announcements");
		tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void announcements(){
		tester.assertMatch("Class Settings");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createAnnouncements("<a href=w>j</a", "<a href=www.malicious.com>malicious link</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("View Announcements");
        tester.assertLinkNotPresentWithText("malicious link");
        tester.assertLinkNotPresentWithText("j");
	}
	

	@After
	public void restore(){
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreAnnouncements();
		} catch (SQLException e) {
			System.err.println(e + " restore of course failed");
		}            
	}
	
}
