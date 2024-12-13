package org.conagua.common.adapter.gui;

import java.util.List;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;

import org.conagua.common.domain.Search;

public class Pager extends Component implements IRefreshable {
  private final PagedTable table;
  private long maxBtns;

  // Buttons
  private JButton prev;
  private JButton next;
  private JButton first;
  private JButton last;

  public Pager(PagedTable table, long maxBtns) {
    super();
    this.table = table;
    this.maxBtns = maxBtns;
    setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar los botones
    this.initDefaultBtns();
  }

  public Pager(PagedTable table) {
    this(table, 5);
  }

  private void initDefaultBtns() {
    this.prev = new JButton("←");
    this.next = new JButton("→");
    this.first = new JButton("1");
    this.last = new JButton();

    prev.addActionListener(e -> updatePage(this.table.getLastSearch().getCurrentPage() - 1));
    next.addActionListener(e -> updatePage(this.table.getLastSearch().getCurrentPage() + 1));
    first.addActionListener(e -> updatePage(1));
    last.addActionListener(e -> updatePage(this.table.getLastSearch().getTotalPages()));
  }

  public List<Long> calculatePageNumbers(long total, long current) {
    long max = Math.min(this.maxBtns, total); // No exceder el total de páginas
    long half = (long) Math.floor(max / 2.0);
    long start = Math.max(1, current - half); // Evitar valores negativos
    long end = start + max - 1;

    if (end > total) {
      end = total;
      start = Math.max(1, end - max + 1);
    }

    List<Long> result = new ArrayList<>();
    for (long i = start; i <= end; i++) {
      result.add(i);
    }

    return result;
  }

  @Override
  public void refresh() {
    Long totalP = this.table.getLastSearch().getTotalPages();
    Long page = this.table.getLastSearch().getCurrentPage();

    removeAll();

    // Actualizar el estado de los botones de navegación
    prev.setEnabled(page > 1);
    first.setEnabled(page > 1);
    next.setEnabled(page < totalP);
    last.setEnabled(page < totalP);

    // Actualizar el texto del botón 'last'
    this.last.setText(totalP.toString());

    // Agregar componentes
    add(first);
    add(prev);

    List<Long> pages = this.calculatePageNumbers(totalP, page);
    for (Long pageNumber : pages) {
      JButton btn = createPageButton(pageNumber);
      btn.setEnabled(pageNumber != page);
      add(btn);
    }

    add(next);
    add(last);

    revalidate();
    repaint();
  }

  private void updatePage(long newPage) {
    Search s = this.table.getLastSearch();
    s.setCurrentPage(newPage);
    this.table.setLastSearch(s);
    this.table.refresh();
  }

  private JButton createPageButton(long pageNumber) {
    JButton btn = new JButton(String.valueOf(pageNumber));
    btn.addActionListener(e -> updatePage(pageNumber));
    return btn;
  }
}
