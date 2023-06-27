package com.example.controlador;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.entidad.Pais;
import com.example.entidad.Sede;

@RestController
@RequestMapping("/rest/general")
@CrossOrigin(origins = "http://localhost:3000")
public class SedeController {
	// PostgreSQL
	String URL_PAIS = "http://localhost:8091/rest/pais";

	// MongoDB
	String URL_SEDE = "http://localhost:8093/rest/sede";

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/registrar-sede")
	public ResponseEntity<?> registrarSede(@RequestBody Sede sede) {
		HashMap<String, Object> salida = new HashMap<>();

		// Obtener el país correspondiente a la sede
		ResponseEntity<List<Pais>> paises = restTemplate.exchange(URL_PAIS, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Pais>>() {
				});
		List<Pais> lstCategoria = paises.getBody();

		Pais pais = lstCategoria.stream().filter(p -> p.getIdPais() == sede.getIdPais()).findFirst().orElse(null);

		if (pais == null) {
			salida.put("mensaje", "No se encontró el país correspondiente a la sede");
			return new ResponseEntity<>(salida, HttpStatus.BAD_REQUEST);
		}

		// Establecer el país en la sede
		sede.setPais(pais);
		sede.setFechaRegistro(LocalDateTime.now());
		// Realizar la solicitud POST para registrar la sede
		HttpEntity<Sede> requestEntity = new HttpEntity<>(sede);
		restTemplate.postForObject(URL_SEDE, requestEntity, Sede.class);

		salida.put("mensaje", "Se registro con exito la sede!");
		salida.put("sede", sede);
		return new ResponseEntity<>(salida, HttpStatus.CREATED);
	}
}
