package com.arb.enviando_mail;

import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testeEmail() {
		/* verificar as configurações do smtp 
		 * https://www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm
		 */
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
	}
}
