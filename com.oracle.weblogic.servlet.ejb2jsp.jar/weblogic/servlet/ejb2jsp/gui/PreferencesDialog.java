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

public class PreferencesDialog extends JDialog implements ActionListener {
   private Prefs p;
   private JTextField compiler;
   private JTextField webapp;
   private JTextField src;
   private JButton okButton;
   private JButton cancelButton;
   private JPanel currentPanel;
   private JScrollPane scroll;

   public PreferencesDialog(Frame owner, String title, boolean modal, Prefs p) {
      super(owner, title, modal);
      this.p = p;
      JPanel pan = new JPanel();
      pan.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = -1;
      gbc.insets = new Insets(5, 5, 5, 5);
      ++gbc.gridy;
      gbc.gridx = 0;
      gbc.weightx = 0.0;
      gbc.gridwidth = -1;
      gbc.fill = 0;
      JLabel l = new JLabel("Java Compiler");
      pan.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 13;
      gbc.gridwidth = 0;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.compiler = new JTextField(p.compiler);
      pan.add(this.compiler, gbc);
      ++gbc.gridy;
      gbc.gridx = 0;
      gbc.weightx = 0.0;
      gbc.gridwidth = -1;
      gbc.fill = 0;
      l = new JLabel("Webapp Directory");
      pan.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 13;
      gbc.gridwidth = 0;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.webapp = new JTextField(p.webapp);
      pan.add(this.webapp, gbc);
      ++gbc.gridy;
      gbc.gridx = 0;
      gbc.weightx = 0.0;
      gbc.gridwidth = -1;
      gbc.fill = 0;
      l = new JLabel("Source Directory");
      pan.add(l, gbc);
      ++gbc.gridx;
      gbc.anchor = 13;
      gbc.gridwidth = 0;
      gbc.fill = 2;
      gbc.weightx = 1.0;
      this.src = new JTextField(p.sourceDir);
      pan.add(this.src, gbc);
      ++gbc.gridy;
      gbc.gridx = 0;
      gbc.weightx = 0.0;
      gbc.gridwidth = -1;
      gbc.fill = 0;
      this.okButton = new JButton("OK");
      this.okButton.addActionListener(this);
      pan.add(this.okButton, gbc);
      ++gbc.gridx;
      this.cancelButton = new JButton("Cancel");
      this.cancelButton.addActionListener(this);
      pan.add(this.cancelButton, gbc);
      this.getContentPane().add(pan);
   }

   public void actionPerformed(ActionEvent ev) {
      if (ev.getSource() == this.okButton) {
         this.fields2Prefs();
      }

      this.setVisible(false);
   }

   private void fields2Prefs() {
      Prefs np = new Prefs();
      np.compiler = this.compiler.getText().trim();
      np.sourceDir = this.src.getText().trim();
      np.webapp = this.webapp.getText().trim();
      this.p = np;
   }

   public Prefs getPrefs() {
      return this.p;
   }

   public static void main(String[] a) throws Exception {
      AWTUtils.initLookAndFeel();
      JFrame f = new JFrame("array edit test");
      f.getContentPane().add(new JLabel("blah"));
      f.setSize(500, 500);
      f.setLocation(800, 300);
      f.setVisible(true);
      Prefs p = new Prefs();
      p.compiler = "javac";
      p.webapp = "C:/tmp/webapp";
      p.sourceDir = "C:/weblogic/dev/src_ag/samples";
      PreferencesDialog pd = new PreferencesDialog(f, "prefs", true, p);
      pd.pack();
      AWTUtils.centerOnWindow(pd, f);
      pd.show();
      System.err.println("prefs are: " + pd.getPrefs());
      System.exit(0);
   }
}
