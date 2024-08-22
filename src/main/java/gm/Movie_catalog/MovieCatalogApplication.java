package gm.Movie_catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.servicio.IPeliculaServicio;

//@SpringBootApplication
public class MovieCatalogApplication implements CommandLineRunner{

	@Autowired
	private IPeliculaServicio peliculaServicio;

	private static final Logger logger = LoggerFactory.getLogger(MovieCatalogApplication.class);

	String nl = System.lineSeparator();
	String nl2 = System.lineSeparator() + System.lineSeparator();
	

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");
		SpringApplication.run(MovieCatalogApplication.class, args);
		logger.info("Aplicacion finalizada!");
	}

	@Override
	public void run(String... args) throws Exception {
		movieCatalogApp();
	}


	private void movieCatalogApp(){
		logger.info(nl2 + "*** Aplicacion Movie Catalog ***" + nl);
		int opcion = 0;

		while (opcion !=6) {
			opcion = elegirOpcion();
			realizarAccion(opcion);
		}
		
	}

	private int elegirOpcion() {
		Scanner scan = new Scanner(System.in);
		
		logger.info(nl + "Elija una opción: " + nl + 
		"1 - Listar películas." + nl + 
		"2 - Añadir película." + nl + 
		"3 - Modificar película." + nl +
		"4 - Buscar película." + nl +
		"5 - Borrar película." + nl +
		"6 - Cerrar aplicación." + nl + 
		"Opcion: ");
		boolean isValid = false;
		int opcion = 0;
		while (isValid == false){
			try {
				opcion = Integer.parseInt(scan.nextLine());
				isValid = true;
				if(opcion < 1 || opcion > 6) {
					isValid = false;
					logger.info("Elija una opción válida.");
				}
			} catch (NumberFormatException e) {
				logger.info("Elija una opción válida.");
				isValid = false;
			}
		}
		return opcion;
	}

	private void realizarAccion (Integer opcion) {
		Scanner scan = new Scanner(System.in);
		switch (opcion) {
            case 1:
				List<Pelicula> peliculas = peliculaServicio.listarPeliculas();
				logger.info(nl + "*** Lista de películas ***" + nl);
				for (Pelicula pelicula : peliculas) {
					logger.info(pelicula.toString());
				}
                break;
            
            case 2:
				
				Pelicula pelicula = new Pelicula();
                logger.info(nl + "*** Añadir película ***" + nl);
				logger.info("Escriba el nombre de la película a añadir: ");
				pelicula.setNombre(scan.nextLine());
				logger.info("Escriba el año de la película a añadir: ");
				pelicula.setYear(Integer.parseInt(scan.nextLine()));
				logger.info("Escriba la puntuación de la película a añadir: ");
				pelicula.setPuntuacion(Double.parseDouble(scan.nextLine()));
				peliculaServicio.guardarPelicula(pelicula);
				logger.info("Película añadida: " + pelicula.toString());
                break;

            case 3:
				logger.info(nl + "*** Modificar película ***" + nl);
				Pelicula peliculaModificar = new Pelicula();
				logger.info("Escriga el id de la película a modificar: ");
				peliculaModificar.setIdPelicula(Integer.parseInt(scan.nextLine()));
				logger.info("Escriba el nuevo nombre de la película: ");
				peliculaModificar.setNombre(scan.nextLine());
				logger.info("Escriba el nuevo año de la película: ");
				peliculaModificar.setYear(Integer.parseInt(scan.nextLine()));
				logger.info("Escriba la nueva puntuación de la película: ");
				peliculaModificar.setPuntuacion(Double.parseDouble(scan.nextLine()));
				peliculaServicio.guardarPelicula(peliculaModificar);
				logger.info("Película modificada correctamente: " + peliculaModificar.toString());
                break;

            case 4:
				logger.info(nl + "*** Buscar película ***" + nl);
				logger.info("Escriba el nombre de la película que busca: ");
				String nombrePeliculaBuscada = scan.nextLine();
				List<Pelicula> peliculas2 = peliculaServicio.buscarPeliculaPorNombre(nombrePeliculaBuscada);
				for (Pelicula pelicula2 : peliculas2) {
					logger.info(pelicula2.toString());
				}

                break;

            case 5:
				Pelicula peliculaBorrar = new Pelicula();
				int idBorrar = 0;
                logger.info(nl + "*** Borrar película ***" + nl);
				logger.info("Escriba el id de la película a borrar: ");
				idBorrar = Integer.parseInt(scan.nextLine());
				peliculaBorrar = peliculaServicio.buscarPeliculaPorId(idBorrar);
				peliculaServicio.eliminarPelicula(peliculaBorrar);
				logger.info("Película borrada: " + peliculaBorrar.toString());
                break;

            case 6:
                
                System.out.println(nl + "Fin del programa." + nl);
                break;
        
            default:

                System.out.println("Elija una opción válidad.");
                elegirOpcion();
                break;
        }

	}



}
