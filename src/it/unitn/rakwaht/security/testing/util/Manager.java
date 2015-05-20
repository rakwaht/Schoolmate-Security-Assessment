package it.unitn.rakwaht.security.testing.util;

import java.sql.*;

import org.xml.sax.helpers.AttributesImpl;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.InputElementFactory;

import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.htmlunit.HtmlUnitElementImpl;
import net.sourceforge.jwebunit.junit.WebTester;

public class Manager {
	
	static Connection con = null;
	
	public static String getBaseUrl(){
		return "http://localhost:5000/schoolmatefixed";
	}
	
	public static String getDataBaseUrl(){
		return "jdbc:mysql://localhost:5001/schoolmate";
	}

	public static String getDataBaseUser() {
		return "schoolmate";
	}

	public static String getDataBasePsw() {
		return "schoolmate";
	}
	
	public static Connection getConnection(){
		if(con == null){
	        try{
	            Class.forName("com.mysql.jdbc.Driver");
	            System.out.println("Driver loaded");
	            con =  DriverManager.getConnection(Manager.getDataBaseUrl(),Manager.getDataBaseUser(), Manager.getDataBasePsw());
	            System.out.println("Connection created");
	        }
	        catch(ClassNotFoundException e){
	        	System.out.println("Errore coid driver " + e);
	        } catch (SQLException e) {
	        	System.out.println("Errore a creare la con" + e);
			}
		}
        return con;
	}
	
    public static void addNewSubmitButton(String form_xpath,WebTester tester){
        IElement e = tester.getElementByXPath(form_xpath);
        DomElement form = ((HtmlUnitElementImpl) e).getHtmlElement();
        InputElementFactory factory = InputElementFactory.instance;
        AttributesImpl attributes = new AttributesImpl();
        attributes.addAttribute("", "", "type", "", "submit");
        attributes.addAttribute("", "", "value", "", "submit");
        HtmlElement submit = factory.createElement(form.getPage(), "input", attributes);
        form.appendChild(submit);
}
	
}
