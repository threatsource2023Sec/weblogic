package weblogic.servlet.ejb2jsp.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import weblogic.tools.ui.AWTUtils;

public class ArrayEditorDialog extends JDialog implements ActionListener {
   private String[] elements;
   private JTextField[] fields;
   private JTextField newField;
   private JButton[] buttons;
   private JButton addButton;
   private JButton okButton;
   private JButton cancelButton;
   private JPanel currentPanel;
   private JScrollPane scroll;
   private boolean trim;

   public ArrayEditorDialog(Frame owner, String title, boolean modal, String[] elements) {
      this(owner, title, modal, elements, true);
   }

   public ArrayEditorDialog(Frame owner, String title, boolean modal, String[] elements, boolean trim) {
      super(owner, title, modal);
      this.elements = elements;
      if (this.elements == null) {
         this.elements = new String[0];
      }

      this.trim = trim;
      this.scroll = new JScrollPane();
      this.getContentPane().add(this.scroll);
      this.makeComponents();
      this.myDoLayout();
   }

   private void makeComponents() {
      int len = this.elements.length;
      this.fields = new JTextField[len];
      this.buttons = new JButton[len];

      for(int i = 0; i < len; ++i) {
         this.fields[i] = new JTextField(this.elements[i]);
         this.buttons[i] = new JButton("Delete");
         this.buttons[i].addActionListener(this);
      }

      this.newField = new JTextField("");
      this.addButton = new JButton("Add New Element");
      this.okButton = new JButton("OK");
      this.cancelButton = new JButton("Cancel");
      this.addButton.addActionListener(this);
      this.okButton.addActionListener(this);
      this.cancelButton.addActionListener(this);
   }

   static void p(String s) {
   }

   private void myDoLayout() {
      if (this.currentPanel != null) {
         this.currentPanel.removeAll();
      }

      this.currentPanel = new JPanel();
      this.currentPanel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = -1;
      gbc.insets = new Insets(5, 5, 5, 5);

      for(int i = 0; i < this.fields.length; ++i) {
         ++gbc.gridy;
         gbc.gridx = 0;
         gbc.weightx = 1.0;
         gbc.gridwidth = -1;
         gbc.fill = 2;
         this.currentPanel.add(this.fields[i], gbc);
         ++gbc.gridx;
         gbc.anchor = 13;
         gbc.fill = 0;
         gbc.weightx = 0.0;
         gbc.gridwidth = 1;
         this.currentPanel.add(this.buttons[i], gbc);
      }

      ++gbc.gridy;
      gbc.gridx = 0;
      gbc.weightx = 1.0;
      gbc.gridwidth = -1;
      gbc.fill = 2;
      this.currentPanel.add(this.newField, gbc);
      ++gbc.gridx;
      gbc.anchor = 13;
      gbc.fill = 0;
      gbc.weightx = 0.0;
      gbc.gridwidth = 1;
      this.currentPanel.add(this.addButton, gbc);
      ++gbc.gridy;
      gbc.gridx = 0;
      this.currentPanel.add(this.okButton, gbc);
      ++gbc.gridx;
      gbc.anchor = 17;
      this.currentPanel.add(this.cancelButton, gbc);
      this.scroll.setViewportView(this.currentPanel);
   }

   public void actionPerformed(ActionEvent ev) {
      p("actionPerformed");
      Object src = ev.getSource();

      int i;
      for(i = 0; i < this.buttons.length; ++i) {
         if (src == this.buttons[i]) {
            p("button#" + i);
            JTextField[] tmpf = new JTextField[this.fields.length - 1];
            JButton[] tmpb = new JButton[this.fields.length - 1];
            System.arraycopy(this.fields, 0, tmpf, 0, i);
            System.arraycopy(this.fields, i + 1, tmpf, i, this.fields.length - i - 1);
            System.arraycopy(this.buttons, 0, tmpb, 0, i);
            System.arraycopy(this.buttons, i + 1, tmpb, i, this.buttons.length - i - 1);
            this.fields = tmpf;
            this.buttons = tmpb;
            this.myDoLayout();
            this.repaint();
            return;
         }
      }

      if (src == this.addButton) {
         JTextField[] tmpf = new JTextField[this.fields.length + 1];
         JButton[] tmpb = new JButton[this.fields.length + 1];
         System.arraycopy(this.fields, 0, tmpf, 0, this.fields.length);
         tmpf[this.fields.length] = new JTextField(this.newField.getText());
         System.arraycopy(this.buttons, 0, tmpb, 0, this.buttons.length);
         tmpb[this.buttons.length] = new JButton("Delete");
         tmpb[this.buttons.length].addActionListener(this);
         this.fields = tmpf;
         this.buttons = tmpb;
         this.myDoLayout();
         this.repaint();
         this.newField.setText("");
      } else if (src == this.okButton) {
         this.elements = new String[this.fields.length];

         for(i = 0; i < this.elements.length; ++i) {
            String s = this.fields[i].getText();
            if (this.trim) {
               s = s.trim();
            }

            this.elements[i] = s;
         }

         this.setVisible(false);
      } else if (src == this.cancelButton) {
         this.setVisible(false);
      }
   }

   public String[] getElements() {
      return this.elements;
   }

   public static void main(String[] a) throws Exception {
      AWTUtils.initLookAndFeel();
      JFrame f = new JFrame("array edit test");
      f.getContentPane().add(new JLabel("blah"));
      f.setSize(500, 500);
      f.setLocation(800, 300);
      f.setVisible(true);
      String[] s = new String[7];

      for(int i = 0; i < s.length; ++i) {
         s[i] = "this is a field, #" + (i + 1);
      }

      ArrayEditorDialog aed = new ArrayEditorDialog(f, "edit values", true, s);
      aed.pack();
      AWTUtils.centerOnWindow(aed, f);
      aed.show();
      s = aed.getElements();
      System.err.println("updated elements are: ");

      for(int i = 0; i < s.length; ++i) {
         System.err.println(" " + i + s[i]);
      }

   }
}
