package org.conagua.common.adapter.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.conagua.common.domain.IController;
import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.Search;

public class PagedTable extends Component implements IRefreshable {
  private final IController controller;
  private Search lastSearch;
  private JTable table;
  private String[] headers;
  private Pager pager;

  public PagedTable(String title, String[] headers, IController controller, Object criteria) {
    super();
    setLayout(new BorderLayout()); // Usar BorderLayout

    if (controller == null)
      throw new RuntimeException("controller can not be null");
    this.controller = controller;

    if (criteria == null)
      throw new RuntimeException("criteria can not be null");
    this.lastSearch = new Search(1, 1, criteria);

    if (headers == null)
      throw new RuntimeException("headers can not be null");
    this.headers = headers;

    this.pager = new Pager(this);

    // Crear la tabla con un modelo vacío al inicio
    this.table = new JTable(new String[0][headers.length], headers);
    this.table.setPreferredSize(new Dimension(600, 400)); // Ajusta el tamaño de la tabla

    // Agregar la tabla envuelta en un JScrollPane en el centro
    add(new JScrollPane(this.table), BorderLayout.CENTER);

    // Agregar el paginador en la parte inferior
    add(pager, BorderLayout.SOUTH);

    refresh();
  }

  public Search getLastSearch() {
    return lastSearch;
  }

  public void setLastSearch(Search lastSearch) {
    if (lastSearch == null)
      throw new RuntimeException("Last search can not be null");

    this.lastSearch = lastSearch;
  }

  @Override
  public void refresh() {
    try {
      // Obtener la última búsqueda
      Search currentSearch = this.lastSearch;

      // Obtener los datos actualizados desde el controlador
      Search updatedSearch = this.controller.getBy(currentSearch.getCriteria(), currentSearch.getCurrentPage());

      // Actualizar la búsqueda
      this.lastSearch = updatedSearch;

      // Obtener los resultados para la tabla
      List<IEntity> results = updatedSearch.getResult();
      String[][] tableData = new String[results.size()][headers.length];

      for (int i = 0; i < results.size(); i++) {
        IEntity entity = results.get(i);
        tableData[i] = entity.attributesToArray();
      }

      // Actualizar el modelo de la tabla
      if (this.table == null) {
        this.table = new JTable(tableData, headers);
        add(new JScrollPane(table)); // Agregar la tabla con scroll al componente
      } else {
        this.table.setModel(new DefaultTableModel(tableData, headers));
      }

      // Eliminar los componentes anteriores y agregar la nueva tabla
      removeAll();
      add(new JScrollPane(this.table), BorderLayout.CENTER);
      add(pager, BorderLayout.SOUTH);

      revalidate();
      repaint();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error while refreshing the table: " + e.getMessage());
    }
  }
}
