package gm.Movie_catalog.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gm.Movie_catalog.modelo.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer>{
    
    @Query ( value = "SELECT * FROM pelicula WHERE nombre LIKE %:nombre%", nativeQuery = true)
    List<Pelicula> findByNombreLike(@Param("nombre") String nombre);
    
}
