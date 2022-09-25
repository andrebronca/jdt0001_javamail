package com.arb.enviando_mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 25-set-2022
 * @author andrebronca
 *
 */
public class EnviarEmail {
	//essa parte fixa é ideal que seja inserida e obtida de BD
	private String userName = "y8mvezhpr6pqb4@gmail.com";	//remetente
	private String senha = "pylvajvvzcykckdy";				//token
	private String listaEmails;		//lista de destinatários
	private String nomeRemetente;	//descrição de quem enviou, diferente do e-mail
	private String assunto;			//titulo do e-mail
	private String conteudo; 		//conteúdo do e-mail
	
	public EnviarEmail(String listaEmails, String nomeRemetente, String assunto, String conteudo) {
		this.listaEmails = listaEmails;
		this.nomeRemetente = nomeRemetente;
		this.assunto = assunto;
		this.conteudo = conteudo;
	}
	
	public EnviarEmail() {
	}

	/**
	 * String da lista de emails separados por ',' (vírgula)
	 * @param lista_emails
	 * @param para_remetente, se true o remetente recebe uma cópia.
	 */
	public void setListaEmails(String lista_emails, boolean para_remetente) {
		if (para_remetente) {
			this.listaEmails = lista_emails +","+ userName;
		} else {
			this.listaEmails = lista_emails;
		}
	} 
	
	private String getListaEmails() {
		return listaEmails;
	}
	
	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}
	
	private String getNomeRemetente() {
		return nomeRemetente;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	private String getAssunto() {
		return assunto;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	private String getConteudo() {
		return conteudo;
	}
	
	public void enviarEmail(boolean isHtml) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");	//autorização
		properties.put("mail.smtp.starttls.enable", "true");	//autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");	//servidor do gmail
		properties.put("mail.smtp.port", "587");	//465 ou 587 (ambas funcionaram)
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.trust", "*");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		try {
			Address toUsers[] = InternetAddress.parse( getListaEmails() );
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, getNomeRemetente() ));	//quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUsers);	//e-mail de destino
			message.setSubject( getAssunto() );	//assunto do e-mail
			
			if (isHtml) {
				message.setContent(getConteudo(), "text/html; charset=\"utf-8\"");
			} else {
				message.setText( getConteudo() );	//conteúdo do e-mail
			}
			
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Simula um arquivo PDF ou qualquer outro formato que possa ser enviado como anexo no e-mail
	 * O arquivo pode vir do BD base64, byte[], Stream
	 */
	private FileInputStream simuladorPDF() {
		FileInputStream fis = null;
		Document document = new Document();
		File file = new File("jdt0001_simulado.pdf");
		try {
			file.createNewFile();
			PdfWriter.getInstance(document,  new FileOutputStream(file));
			document.open();
			document.add(new Paragraph("Conteúdo do PDF"));
			document.close();
			fis = new FileInputStream(file);
			return fis;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return fis;
	}
	
	/**
	 * Isso em uma aplicação final tem que refatorar retirando o código duplicado.
	 * @param isHtml
	 */
	public void enviarEmailComAnexo(boolean isHtml) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");	//autorização
		properties.put("mail.smtp.starttls.enable", "true");	//autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");	//servidor do gmail
		properties.put("mail.smtp.port", "587");	//465 ou 587 (ambas funcionaram)
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.trust", "*");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		try {
			Address toUsers[] = InternetAddress.parse( getListaEmails() );
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, getNomeRemetente() ));	//quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUsers);	//e-mail de destino
			message.setSubject( getAssunto() );	//assunto do e-mail
			
			//como terá anexo, aqui terá a mudança
			//mudando o tipo do conteúdo
			MimeBodyPart corpoEmail = new MimeBodyPart();
			
			if (isHtml) {
				corpoEmail.setContent(getConteudo(), "text/html; charset=\"utf-8\"");
			} else {
				corpoEmail.setText( getConteudo() );	//conteúdo do e-mail
			}
			
			//simulando uma lista de anexos
			List<FileInputStream> listaAnexos = new ArrayList<>();
			listaAnexos.add(simuladorPDF());
			listaAnexos.add(simuladorPDF());
			listaAnexos.add(simuladorPDF());
			listaAnexos.add(simuladorPDF());
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			
			int seqArquivo = 1;
			for(FileInputStream f : listaAnexos) {
				//anexo
				MimeBodyPart anexoEmail = new MimeBodyPart();
				
				anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource( f, "application/pdf")));
				anexoEmail.setFileName("jdt0001_simulado"+ seqArquivo++ +".pdf");
				
				multipart.addBodyPart(anexoEmail);
			}
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
