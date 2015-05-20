package it.unitn.rakwaht.security.testing.admin.manage;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackGenericUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageSemester {
	
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
		tester.clickLinkWithExactText("Semesters");
		tester.assertMatch("Manage Semesters");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","5'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Semesters");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void onpage(){
		tester.assertMatch("Manage Classes");
		tester.clickLinkWithExactText("Semesters");
		tester.assertMatch("Manage Semesters");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
		tester.setWorkingForm("semesters");
        Manager.addNewSubmitButton("/html//form[@name=\"semesters\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Semesters");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void semestername() {
		tester.assertMatch("Manage Classes");
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.createSemesterName("<a href=w>j<a");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.clickLinkWithExactText("Semesters");
		tester.assertMatch("Manage Semesters");
	    tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore() throws SQLException{
		StoredAttackGenericUtility sacu = new StoredAttackGenericUtility();
		try {
			sacu.restoreTerms();
			sacu.restoreSemesters();
		} catch (SQLException e) {
			System.err.println(e + " restore of terms failed");
		}         
	}
}
