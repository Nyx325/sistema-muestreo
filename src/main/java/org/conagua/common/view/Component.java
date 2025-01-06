package org.conagua.common.adapter.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.FlowLayout; // O cualquier otro LayoutManager que desees usar

public class Component extends JComponent {
    public Component() {
        setLayout(new FlowLayout());
    }
}