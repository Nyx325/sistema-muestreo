package org.conagua;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import org.conagua.common.adapter.gui.PagedTable;
import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.Search;
import org.conagua.user.extern.InMemoryUser;
import org.conagua.user.extern.UserController;

public class App {
    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Prueba");
        
        List<IEntity> users = new ArrayList<>();
        users.add(new InMemoryUser("C","D"));
        users.add(new InMemoryUser("C","D"));
        users.add(new InMemoryUser("C","D"));
        users.add(new InMemoryUser("C","D"));
        users.add(new InMemoryUser("C","D"));
        users.add(new InMemoryUser("C","D"));
        System.out.println(users.size());


        String[] headers = {"A", "b"};
        // Crear el componente
        PagedTable table = new PagedTable("null", headers, new UserController(), new Object());
        Search s = new Search(1, 1, new Object(), users);
        table.setLastSearch(s);
        table.refresh();
        // Agregar el componente al marco
        frame.add(table);
        
        // Hacer el marco visible
        frame.setSize(300, 200);  // Establecer un tamaño adecuado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar el programa al cerrar la ventana
        frame.setVisible(true);  // Hacer la ventana visible
    }
}