package gm.Movie_catalog;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.Movie_catalog.GUI.MovieCatalogForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class MovieCatalogSwing {
    public static void main(String[] args) {
        //Configuramos el modo oscuro
        FlatDarculaLaf.setup();
        //Instancia la fabrica de spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(MovieCatalogSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        //Crear un objeto de Swing
        SwingUtilities.invokeLater(() -> {
            MovieCatalogForm movieCatalogForm = contextoSpring.getBean(MovieCatalogForm.class);
            movieCatalogForm.setVisible(true);
        });
    }
}
