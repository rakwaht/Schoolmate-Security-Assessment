package it.unitn.rakwaht.security.testing.admin.manage;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackSchoolinfoUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageSchoolInfo {
	
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
		tester.clickLinkWithExactText("School");
		tester.assertMatch("Manage School Information");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","1'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void phone() throws SQLException{
		tester.assertMatch("Manage Classes");
		//set phone in db as a malicious link
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		sacu.createSchoolPhone("''><a>link1</a>");
		tester.clickLinkWithExactText("School");
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("link1");
	}
	
	@Test
	public void address(){
		tester.assertMatch("Manage Classes");
		//set address in db as a malicious link
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		//sacu.createSchoolAddress("''><a>link2</a>'"); \'\'><a>link2</a>\'
		sacu.createSchoolAddress("''><a href=w>link2</a>"); 
		tester.clickLinkWithExactText("School");
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("link2");
	}
	
	@Test
	public void sitemessage() {
		tester.assertMatch("Manage Classes");
		//set phone in db as a malicious link
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		sacu.createSchoolMessage("</textarea><a href=ww.unitn.it>malicious link1</a>");
		tester.clickLinkWithExactText("School");
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("malicious link1");
	}
	
	@Test
	public void sitetext() {
		tester.assertMatch("Manage Classes");
		//set phone in db as a malicious link
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		sacu.createSchoolText("</textarea><a href=ww.unitn.it>malicious link2</a>");
		tester.clickLinkWithExactText("School");
        tester.assertMatch("Manage School Information");
        tester.assertLinkNotPresentWithText("malicious link2");
	}
	
	@After
	public void restore() throws SQLException{
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		try {
			sacu.restoreSchoolInfo();
		} catch (SQLException e) {
			System.err.println(e + " restore of schoolinfo failed");
		} 
	}
}
