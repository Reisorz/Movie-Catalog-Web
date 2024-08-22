package gm.Movie_catalog.GUI;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.servicio.IPeliculaServicio;
import gm.Movie_catalog.servicio.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class MovieCatalogForm extends JFrame {
    private JPanel panelPrincipal;
    private JTable peliculasTabla;
    private JLabel nombre;
    private JTextField nombreTexto;
    private JButton guardarButton;
    private JLabel year;
    private JTextField yearTexto;
    private JTextField puntuacionTexto;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    IPeliculaServicio peliculaServicio;
    private DefaultTableModel tablaModeloPeliculas;
    private Integer idPelicula;


    @Autowired
    public MovieCatalogForm(PeliculaServicio peliculaServicio) {
        this.peliculaServicio = peliculaServicio;
        iniciarForma();
        guardarButton.addActionListener(e ->  guardarPelicula());
        peliculasTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarPeliculaSeleccionada();
            }
        });
        limpiarButton.addActionListener( e -> limpiarTexto());
        eliminarButton.addActionListener(e -> eliminarPelicula());
        buscarButton.addActionListener(e -> buscarPelicula());
    }



    private void iniciarForma() {
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(900, 700);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloPeliculas = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String [] cabeceros = {"Id", "Nombre", "Año", "Puntuación"};
        this.tablaModeloPeliculas.setColumnIdentifiers(cabeceros);
        this.peliculasTabla = new JTable(this.tablaModeloPeliculas);
        this.peliculasTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Cargar listado de peliculas
        listarPeliculas();
    }

    private void listarPeliculas() {
        this.tablaModeloPeliculas.setRowCount(0);
        List<Pelicula> peliculas = this.peliculaServicio.listarPeliculas();
        peliculas.forEach(pelicula -> {
            Object[] renglonPelicula = {
                    pelicula.getIdPelicula(),
                    pelicula.getNombre(),
                    pelicula.getYear(),
                    pelicula.getPuntuacion()
            };
            this.tablaModeloPeliculas.addRow(renglonPelicula);
        });
    }

    private void guardarPelicula() {
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre.");
            nombreTexto.requestFocusInWindow();
            return;
        }

        String nombrePelicula = this.nombreTexto.getText();
        int yearPelicula = Integer.parseInt(this.yearTexto.getText());
        Double puntuacionPelicula = Double.parseDouble(this.puntuacionTexto.getText());
        Pelicula peliculaGuardar = new Pelicula(idPelicula, nombrePelicula, yearPelicula, puntuacionPelicula);
        peliculaServicio.guardarPelicula(peliculaGuardar);

        if (idPelicula == null) {
            mostrarMensaje("Película guardada con éxito.");
        } else {
            mostrarMensaje("Película actualizada con éxito");
        }
        limpiarTexto();
        listarPeliculas();
    }

    private void cargarPeliculaSeleccionada() {
        var renglonPelicula = peliculasTabla.getSelectedRow();
        if(renglonPelicula != -1){
            String id = peliculasTabla.getModel().getValueAt(renglonPelicula,0).toString();
            this.idPelicula = Integer.parseInt(id);
            String nombre = peliculasTabla.getModel().getValueAt(renglonPelicula,1).toString();
            nombreTexto.setText(nombre);
            String year = peliculasTabla.getModel().getValueAt(renglonPelicula,2).toString();
            yearTexto.setText(year);
            String puntuacion = peliculasTabla.getModel().getValueAt(renglonPelicula,3).toString();
            puntuacionTexto.setText(puntuacion);
        }
    }

    private void eliminarPelicula() {
        if(idPelicula == null){
            mostrarMensaje("Selecciona una película.");
            return;
        }
        String nombrePelicula = this.nombreTexto.getText();
        int yearPelicula = Integer.parseInt(this.yearTexto.getText());
        Double puntuacionPelicula = Double.parseDouble(this.puntuacionTexto.getText());
        Pelicula peliculaBorrar = new Pelicula(idPelicula, nombrePelicula, yearPelicula, puntuacionPelicula);
        peliculaServicio.eliminarPelicula(peliculaBorrar);
        mostrarMensaje("Película borrada con éxito.");
        limpiarTexto();
        listarPeliculas();
    }

    private void buscarPelicula() {
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre para buscar.");
            nombreTexto.requestFocusInWindow();
            return;
        }
        String nombrePelicula = this.nombreTexto.getText();
        List<Pelicula> peliculasBuscadas = peliculaServicio.buscarPeliculaPorNombre(nombrePelicula);
        this.tablaModeloPeliculas.setRowCount(0);
        peliculasBuscadas.forEach(pelicula -> {
            Object[] renglonPelicula = {
                    pelicula.getIdPelicula(),
                    pelicula.getNombre(),
                    pelicula.getYear(),
                    pelicula.getPuntuacion()
            };
            this.tablaModeloPeliculas.addRow(renglonPelicula);
        });
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarTexto() {
        this.nombreTexto.setText("");
        this.yearTexto.setText("");
        this.puntuacionTexto.setText("");
        this.idPelicula = null;
        this.peliculasTabla.getSelectionModel().clearSelection();
        listarPeliculas();
    }
}
