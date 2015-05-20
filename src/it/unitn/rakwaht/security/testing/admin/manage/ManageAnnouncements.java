package it.unitn.rakwaht.security.testing.admin.manage;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageAnnouncements {
	
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
		tester.setHiddenField("page", "1'> <a href=www.unitn.it>malicious link</a> <br  '");
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("Manage Announcements");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","4'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Announcements");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void onpage(){
		tester.assertMatch("Manage Classes");
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("Manage Announcements");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
		tester.setWorkingForm("announcements");
        Manager.addNewSubmitButton("/html//form[@name=\"announcements\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Announcements");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void announcements(){
		tester.assertMatch("Manage Classes");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createAnnouncements("<a href=w>j</a", "<a href=www.malicious.com>malicious link</a>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tester.clickLinkWithExactText("Announcements");
		tester.assertMatch("Manage Announcements");
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
