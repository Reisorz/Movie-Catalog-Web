package gm.Movie_catalog.servicio;

import java.util.List;

import gm.Movie_catalog.modelo.Pelicula;

public interface IPeliculaServicio {
    public List<Pelicula> listarPeliculas();

    public Pelicula buscarPeliculaPorId (Integer idPelicula);

    public void guardarPelicula (Pelicula pelicula);

    public void eliminarPelicula (Pelicula pelicula);

    public List<Pelicula> buscarPeliculaPorNombre(String nombre);
}
