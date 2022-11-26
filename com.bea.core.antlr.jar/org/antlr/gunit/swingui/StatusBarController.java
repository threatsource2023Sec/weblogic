package org.antlr.gunit.swingui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusBarController implements IController {
   private final JPanel panel = new JPanel();
   private final JLabel labelText = new JLabel("Ready");
   private final JLabel labelRuleName = new JLabel("");
   private final JProgressBar progress = new JProgressBar();

   public StatusBarController() {
      this.initComponents();
   }

   private void initComponents() {
      this.labelText.setPreferredSize(new Dimension(300, 20));
      this.labelText.setHorizontalTextPosition(2);
      this.progress.setPreferredSize(new Dimension(100, 15));
      JLabel labRuleHint = new JLabel("Rule: ");
      FlowLayout layout = new FlowLayout();
      layout.setAlignment(0);
      this.panel.setLayout(layout);
      this.panel.add(this.labelText);
      this.panel.add(this.progress);
      this.panel.add(labRuleHint);
      this.panel.add(this.labelRuleName);
      this.panel.setOpaque(false);
      this.panel.setBorder(BorderFactory.createEmptyBorder());
   }

   public void setText(String text) {
      this.labelText.setText(text);
   }

   public void setRule(String name) {
      this.labelRuleName.setText(name);
   }

   public Object getModel() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public JPanel getView() {
      return this.panel;
   }

   public void setProgressIndetermined(boolean value) {
      this.progress.setIndeterminate(value);
   }

   public void setProgress(int value) {
      this.progress.setIndeterminate(false);
      this.progress.setValue(value);
   }
}
