package br.edu.unirn.turma08.rest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.unirn.turma08.modelo.Usuario;
import br.edu.unirn.turma08.webservice.WebService;

public class UsuarioREST extends WebService {
	private static String PATH = "/usuario/";

	public static Usuario logar(String login, String senha) {

		String urlTemplate = getUrl()+ PATH + "logar?login=%s&senha=%s";
		String url = String.format(urlTemplate, login, senha);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		Usuario usuario = restTemplate.getForObject(url, Usuario.class);

		return usuario;
	}
}
