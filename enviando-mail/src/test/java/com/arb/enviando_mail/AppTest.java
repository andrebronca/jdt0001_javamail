package com.arb.enviando_mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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
		
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");	//autorização
			properties.put("mail.smtp.starttls.enable", "true");	//autenticação
			properties.put("mail.smtp.host", "smtp.gmail.com");	//servidor do gmail
			properties.put("mail.smtp.port", "465");	//465 ou 587
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return PasswordAuthentication()
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
