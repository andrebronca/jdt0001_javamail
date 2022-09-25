package com.arb.enviando_mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private String userName = "y8mvezhpr6pqb4@gmail.com";
	private String senha = "pylvajvvzcykckdy";	//token

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
					return new PasswordAuthentication(userName,senha);
				}
			});
			
			Address toUsers[] = InternetAddress.parse("bronca.andre@gmail.com,madoo.mop@gmail.com,"+ userName);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));	//quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUsers);	//e-mail de destino
			message.setSubject("jdt0001: Envio de e-mail - teste");	//assunto do e-mail
			message.setText("Formação Java Web Full Stack (jdt0001) - teste");	//conteúdo do e-mail
			
			Transport.send(message);
			//Envio realizado com sucesso: 25/set/2022 11:38
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
