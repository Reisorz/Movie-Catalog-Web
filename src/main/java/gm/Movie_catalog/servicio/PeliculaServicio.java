package gm.Movie_catalog.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.repositorio.PeliculaRepositorio;

@Service
public class PeliculaServicio implements IPeliculaServicio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Override
    public Pelicula buscarPeliculaPorId(Integer idPelicula) {
        Pelicula pelicula = peliculaRepositorio.findById(idPelicula).orElse(null);
        return pelicula;
    }

    @Override
    public void eliminarPelicula(Pelicula pelicula) {
        peliculaRepositorio.delete(pelicula);
    }

    @Override
    public void guardarPelicula(Pelicula pelicula) {
        peliculaRepositorio.save(pelicula);
    }

    @Override
    public List<Pelicula> listarPeliculas() {
        List<Pelicula> peliculas = peliculaRepositorio.findAll();
        return peliculas;
    }

    @Override
    public List<Pelicula> buscarPeliculaPorNombre(String nombre) {
        List<Pelicula> peliculas = peliculaRepositorio.findByNombreLike(nombre);
        return peliculas;
    }
    
}
