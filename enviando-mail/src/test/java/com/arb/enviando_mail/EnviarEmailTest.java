package com.arb.enviando_mail;

import org.junit.Test;

public class EnviarEmailTest {

	@Test
	public void testEnvioEmailSemHtml() {
		EnviarEmail envEmail = new EnviarEmail();
		envEmail.setListaEmails(EnviarEmail.LISTAEMAILS, true);
		envEmail.setAssunto("JDT0001 - Definindo classe de envio - teste");
		envEmail.setNomeRemetente("Andre R.B. (JDT0001-teste)");
		envEmail.setConteudo("O Conteúdo do e-mail pode ser personalizado nesse método");
		envEmail.enviarEmail(false);
		//enviado com sucesso, demorou no teste (10,324 s)
	}

	@Test
	public void testEnvioEmailComHtml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Envio de e-mail no Java</h1>");
		sb.append("<h2>Configurando o conteúdo com html</h2>");
		sb.append("<p>JDT0001</p>");
		
		EnviarEmail envEmail = new EnviarEmail();
		envEmail.setListaEmails(EnviarEmail.LISTAEMAILS, true);
		envEmail.setAssunto("JDT0001 - Definindo classe de envio - teste");
		envEmail.setNomeRemetente("Andre R.B. (JDT0001-teste)");
		envEmail.setConteudo(sb.toString());
		envEmail.enviarEmail(true);
		//enviado com sucesso, demorou no teste (10,324 s)
	}
	
	@Test
	public void testEnvioEmailComHtmlComAnexoPDF() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Envio de e-mail no Java</h1>");
		sb.append("<h2>Configurando o conteúdo com html</h2>");
		sb.append("<p>JDT0001</p>");
		
		EnviarEmail envEmail = new EnviarEmail();
		envEmail.setListaEmails(EnviarEmail.LISTAEMAILS, true);
		envEmail.setAssunto("JDT0001 - Definindo classe de envio - teste");
		envEmail.setNomeRemetente("Andre R.B. (JDT0001-teste)");
		envEmail.setConteudo(sb.toString());
		envEmail.enviarEmailComAnexo(true);
		//enviado com sucesso, demorou no teste (10,324 s), com 4 anexos (12,981 s)
	}
}
