package br.edu.unirn.turma08.rest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.webservice.WebService;


public class UsuarioREST extends WebService {
	private static String PATH = "/usuario/";

	public static Usuario logar(String login, String senha) {

		String urlTemplate = getUrl() + PATH + "logar?login=%s&senha=%s";
		String url = String.format(urlTemplate, login, senha);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		return restTemplate.getForObject(url, Usuario.class);
	}
	
	public static Usuario cadastrar(Usuario usuario) {
		String url = getUrl() + PATH + "cadastro";
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate.postForObject(url, usuario, Usuario.class);
				
	}

	public static Usuario recuperar(String login, String telefone) {
		String urlTemplate = getUrl() + PATH + "recuperarsenha";
		String url = String.format(urlTemplate, login, telefone);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate.getForObject(url, Usuario.class);
	}

	public static Usuario alterarSenha(String login, String senhaAtual, String senhaNova) {

		String urlTemplate = getUrl() + PATH + "alterarsenha?login=%s&senha=%s&novasenha=%s";
		String url = String.format(urlTemplate, login, senhaAtual, senhaNova);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		return restTemplate.getForObject(url, Usuario.class);
	}

}
