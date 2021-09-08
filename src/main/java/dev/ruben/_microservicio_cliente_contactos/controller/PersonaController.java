package dev.ruben._microservicio_cliente_contactos.controller;

import dev.ruben._microservicio_cliente_contactos.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonaController {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL_BASE = "http://localhost:8082";

    @GetMapping(value = "/personas/{nombre}/{email}/{edad}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Persona>> darAltaPersona(
            @PathVariable("nombre") String nombre,
            @PathVariable("email") String email,
            @PathVariable("edad") int edad
    ) {
        try {
            // Guardado llamando al microservicio de contactos
            if (nombre != null && email != null && edad > 0) {
                Persona persona = new Persona(nombre, email, edad);
                restTemplate.postForLocation(URL_BASE + "/contactos", persona);
            }
            // Obtención y devolución de todos los contactos llamando al microservicio de contactos
            Persona[] personas = restTemplate.getForObject( URL_BASE + "/contactos", Persona[].class);
            return new ResponseEntity<>(Arrays.asList(personas), HttpStatus.OK);
        } catch (HttpStatusCodeException e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Error", e.getResponseBodyAsString());
            return new ResponseEntity<>(new ArrayList<>(), httpHeaders, e.getStatusCode());
        }

    }

}
