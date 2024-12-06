package org.conagua;

import javax.swing.*;
import java.awt.*;

import org.conagua.common.adapter.gui.PagedTable;
import org.conagua.user.extern.UserController;

public class App {
  public static void main(String[] args) {
    // Crear el marco principal
    JFrame frame = new JFrame("Prueba");

    // Definir los encabezados de la tabla
    String[] headers = { "A" };
    
    // Crear el PagedTable
    PagedTable table = new PagedTable("Prueba", headers, new UserController(), new Object());

    // Establecer tamaño preferido para el PagedTable
    table.setPreferredSize(new Dimension(600, 400));  // Ajusta el tamaño según tus necesidades

    // Hacer la tabla visible
    table.setVisible(true);

    // Agregar el PagedTable al frame
    frame.setLayout(new BorderLayout());
    frame.add(table, BorderLayout.CENTER);

    // Configurar el tamaño del frame y la operación de cierre
    frame.setSize(800, 600);  // Ajusta el tamaño del frame según tus necesidades
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Hacer visible el frame
    frame.setVisible(true);
  }
}
