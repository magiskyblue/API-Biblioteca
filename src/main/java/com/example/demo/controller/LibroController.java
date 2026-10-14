package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> listarTodos() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        return libroService.obtenerPorIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> actualizar(@PathVariable String isbn, @RequestBody Libro detalles) {
        return libroService.obtenerPorIsbn(isbn).map(libro -> {
            libro.setTitulo(detalles.getTitulo());
            libro.setAutor(detalles.getAutor());
            libro.setAnioPublicacion(detalles.getAnioPublicacion());
            libro.setTotalEjemplares(detalles.getTotalEjemplares());
            return ResponseEntity.ok(libroService.guardar(libro));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> eliminar(@PathVariable String isbn) {
        libroService.eliminar(isbn);
        return ResponseEntity.noContent().build();
    }
}