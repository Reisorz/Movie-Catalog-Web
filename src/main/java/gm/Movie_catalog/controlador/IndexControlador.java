package gm.Movie_catalog.controlador;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.servicio.IPeliculaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador {

    @Autowired
    IPeliculaServicio peliculaServicio;
    private List<Pelicula> peliculas;
    private Pelicula peliculaSeleccionada;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        peliculas = peliculaServicio.listarPeliculas();
        peliculas.forEach(pelicula -> logger.info(pelicula.toString()));
    }

    public void agregarPelicula() {
        this.peliculaSeleccionada = new Pelicula();
    }

}
