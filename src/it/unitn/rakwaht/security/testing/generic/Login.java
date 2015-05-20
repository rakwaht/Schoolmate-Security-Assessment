package it.unitn.rakwaht.security.testing.generic;

import it.unitn.rakwaht.security.testing.util.Manager;
import it.unitn.rakwaht.security.testing.util.StoredAttackSchoolinfoUtility;

import java.sql.SQLException;

import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Login {
	
	private WebTester tester;
	
	@Before
	public void setup() throws SQLException{
		tester = new WebTester();
		//set phone in db as a malicious link
		StoredAttackSchoolinfoUtility sacu = new StoredAttackSchoolinfoUtility();
		sacu.createSchoolText("<a href=ww.unitn.it>malicious link2</a>");
		sacu.createSchoolMessage("<a href=ww.unitn.it>malicious link1</a>");
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
	}
	
	@Test
	public void page(){
        tester.setHiddenField("page","0'><a href=www.unitn.it>malicious link</a><br  '");
        Manager.addNewSubmitButton("/html//form[@name=\"login\"]",tester);
        tester.clickButtonWithText("submit"); 
        tester.assertLinkNotPresentWithText("malicious link");
	}

	@Test
	public void sitemessage(){
        tester.assertLinkNotPresentWithText("malicious link1");
	}
	
	@Test
	public void sitetext() throws SQLException{
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
