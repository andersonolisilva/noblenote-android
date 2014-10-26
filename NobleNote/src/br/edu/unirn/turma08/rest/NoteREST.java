package br.edu.unirn.turma08.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.unirn.turma08.modelo.Note;
import br.edu.unirn.turma08.webservice.WebService;

public class NoteREST extends WebService {

	private static String PATH = "/note/";

	public static List<Note> listar() {

		String urlTemplate = getUrl() + PATH;
		String url = String.format(urlTemplate);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		Note[] arrNotas = restTemplate.getForObject(url, Note[].class);

		List<Note> notas = new ArrayList<Note>();
        
        if(arrNotas != null){
        	notas = Arrays.asList(arrNotas);
        }
        
		return notas; 
	}

}
