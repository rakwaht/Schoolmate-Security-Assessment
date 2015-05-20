package it.unitn.rakwaht.security.testing.admin.manage;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackUsersUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManageParents {
	
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
		tester.clickLinkWithExactText("Parents");
		tester.assertMatch("Manage Parents");
		tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void page2(){
		tester.assertMatch("Manage Classes");
		tester.setWorkingForm("admin");
        tester.setHiddenField("page2","22'><a href=www.unitn.it>malicious link</a> <br ");
        Manager.addNewSubmitButton("/html//form[@name=\"admin\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Parents");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void onpage(){
		tester.assertMatch("Manage Classes");
		tester.clickLinkWithExactText("Parents");
		tester.assertMatch("Manage Parents");
		tester.setHiddenField("onpage","1'><a href=www.unitn.it>malicious link</a> <br ");
		tester.setWorkingForm("parents");
        Manager.addNewSubmitButton("/html//form[@name=\"parents\"]",tester);
        tester.submit();     
        tester.assertMatch("Manage Parents");
        tester.assertLinkNotPresentWithText("malicious link");
	}
	
	@Test
	public void parentname() {
		tester.assertMatch("Manage Classes");
		StoredAttackUsersUtility sacu = new StoredAttackUsersUtility();
		try {
			sacu.createParentname("<a href=w>j<a","<a href=w>k<a");
		} catch (SQLException e) {
			System.err.println(e + " class creation faild");
		}
		tester.clickLinkWithExactText("Parents");
		tester.assertMatch("Manage Parents");
	    tester.assertLinkNotPresentWithText("k");
	    tester.assertLinkNotPresentWithText("j");
	}
	
	@After
	public void restore(){
		StoredAttackUsersUtility sacu = new StoredAttackUsersUtility();
		try {
			sacu.restoreParent();
		} catch (SQLException e) {
			System.err.println(e + " restore faild");
		}             
	}
}
