package org.conagua.common.view.swing;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class LabeledInput extends Component {
  private final JLabel label;
  private final JTextField input;

  public LabeledInput(String text) {
    super();
    this.label = new JLabel(text);
    this.input = new JTextField();

    setLayout(new BorderLayout()); // Establece el layout.
    add(label, BorderLayout.WEST);
    add(input, BorderLayout.EAST);
  }

  public LabeledInput() {
    this("New Label");
  }

  public void setLabelText(String text) {
    this.label.setText(text);
  }

  public String getInput() {
    return this.input.getText();
  }

  public String getInputTrimmed() {
    return getInput().trim();
  }

  public void setInput(String value) {
    this.input.setText(value);
  }

  public void setInputTrimmed(String value) {
    setInput(value.trim());
  }

  public JTextField getInputField() {
    return this.input;
  }

  public JLabel getLabelComponent() {
    return this.label;
  }
}
