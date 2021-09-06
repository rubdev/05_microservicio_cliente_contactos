package dev.ruben._microservicio_cliente_contactos.controller;

import dev.ruben._microservicio_cliente_contactos.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class PersonaController {

    @Autowired
    RestTemplate restTemplate;

    private static String URL_BASE = "http://localhost:8082";

    @GetMapping(value = "/personas/{nombre}/{email}/{edad}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Persona> darAltaPersona(
            @PathVariable("nombre") String nombre,
            @PathVariable("email") String email,
            @PathVariable("edad") int edad
    ) {
        // Guardado llamando al microservicio de contactos
        if (nombre != null && email != null && edad > 0) {
            Persona persona = new Persona(nombre, email, edad);
            restTemplate.postForLocation(URL_BASE + "/contactos", persona);
        }
        // Obtención y devolución de todos los contactos llamando al microservicio de contactos
        Persona[] personas = restTemplate.getForObject( URL_BASE + "/contactos", Persona[].class);
        return Arrays.asList(personas);
    }

}
