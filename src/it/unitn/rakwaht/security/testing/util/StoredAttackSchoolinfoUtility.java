package it.unitn.rakwaht.security.testing.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import net.sourceforge.jwebunit.junit.WebTester;

public class StoredAttackSchoolinfoUtility {
	
	private WebTester tester;
	
	public void createSchoolAddress(String address){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("School");
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("info");
		tester.setTextField("schooladdress", address);
		tester.clickButtonWithText(" Update ");
	}
	
	public void createSchoolMessage(String message){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("School");
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("info");
		tester.setTextField("sitemessage", message);
		tester.clickButtonWithText(" Update ");
	}
	
	public void createSchoolPhone(String phone){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("School");
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("info");
		tester.setTextField("schoolphone", phone);
		tester.clickButtonWithText(" Update ");
	}
	
	public void createSchoolText(String test){
		tester = new WebTester();
		tester.setBaseUrl(Manager.getBaseUrl());
		tester.beginAt("index.php");
		tester.setTextField("username", "schoolmate");
		tester.setTextField("password", "schoolmate");
		tester.submit();
		tester.clickLinkWithExactText("School");
		tester.assertMatch("Manage School Information");
		tester.setWorkingForm("info");
		tester.setTextField("sitetext", test);
		tester.clickButtonWithText(" Update ");
	}
	
	public void restoreSchoolInfo() throws SQLException{
		Connection con = Manager.getConnection();
		Statement stmt = null;
		stmt = con.createStatement();
		if (stmt != null) {
			//there is only one course in my db and have id 1
			stmt.execute("delete from schoolinfo where schoolname='School Name'");
			stmt.execute("INSERT INTO schoolinfo VALUES ('School Name','1,Street','52365895','Ciao','This is the Message of the day:-\r\n\r\nWe think our fathers fools, so wise do we grow,no doubt our wisest sons would think usso.',NULL,0,0,0.000,0.000,0.000,0.000,0.000);");
			stmt.close();
		}  
	}
}
