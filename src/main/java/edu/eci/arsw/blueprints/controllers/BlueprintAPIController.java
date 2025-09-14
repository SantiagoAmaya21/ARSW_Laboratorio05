package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {

    private static final Logger logger = Logger.getLogger(BlueprintAPIController.class.getName());

    @Autowired
    private BlueprintsServices blueprintServices;

    /**
     * GET /blueprints
     * Devuelve todos los planos.
     */
    @GetMapping
    public ResponseEntity<Set<Blueprint>> getAllBlueprints() {
        Set<Blueprint> all = blueprintServices.getAllBlueprints();
        return new ResponseEntity<>(all, HttpStatus.ACCEPTED);
    }

    /**
     * GET /blueprints/{author}
     * Devuelve todos los planos de un autor.
     */
    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> bps = blueprintServices.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(bps, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            logger.log(Level.WARNING, "Autor no encontrado: " + author, ex);
            return new ResponseEntity<>(
                    "Autor no encontrado: " + author,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * GET /blueprints/{author}/{bpname}
     * Devuelve un plano específico por autor y nombre.
     */
    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprintByAuthorAndName(
            @PathVariable("author") String author,
            @PathVariable("bpname") String bpname) {
        try {
            Blueprint bp = blueprintServices.getBlueprint(author, bpname);
            return new ResponseEntity<>(bp, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            logger.log(Level.WARNING,
                    "Plano no encontrado: " + author + "/" + bpname, ex);
            return new ResponseEntity<>(
                    "Plano no encontrado: " + author + "/" + bpname,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}