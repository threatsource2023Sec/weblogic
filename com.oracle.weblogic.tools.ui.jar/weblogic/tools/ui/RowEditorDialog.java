package weblogic.tools.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class RowEditorDialog extends JDialog implements ActionListener {
   private static final MarathonTextFormatter fmt = new MarathonTextFormatter();
   JButton ok;
   JButton cancel;
   boolean okclicked = false;
   private IBeanRowEditor editor;
   Frame owner;
   private boolean m_wasCancelled = false;
   private static final int MAX_HEIGHT = 600;
   private static final int MAX_WIDTH = 500;

   public RowEditorDialog(Frame owner, String title, boolean modal, IBeanRowEditor bre) {
      super(owner, title, modal);
      this.owner = owner;
      this.editor = bre;
      JPanel pan = new JPanel();
      pan.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.weightx = gbc.weighty = 1.0;
      gbc.fill = 1;
      JScrollPane jsp = new JScrollPane(this.editor.getComponent());
      pan.add(jsp, gbc);
      ++gbc.gridy;
      gbc.weighty = 0.0;
      gbc.weightx = 1.0;
      JPanel buttonPanel = this.makeButtonPane();
      pan.add(buttonPanel, gbc);
      this.getContentPane().add(pan);
      this.getRootPane().setDefaultButton(this.ok);
      this.pack();
   }

   public void pack() {
      super.pack();
      Dimension d = this.editor.getComponent().getSize();
      d.width = (int)Math.max(d.getWidth(), 500.0);
      d.height = (int)Math.max(d.getHeight(), 600.0);
      this.setSize(d);
   }

   public IBeanRowEditor getBeanRowEditor() {
      return this.editor;
   }

   public final void editObject(Object o) {
      this.setAutoCommit(false);
      this.editor.setEditingBean(o);
      this.okclicked = false;
      this.pack();
      AWTUtils.centerOnWindow(this, this.owner);
      JComponent foc = this.editor.getFirstFocusComponent();
      if (foc != null) {
         foc.requestFocus();
      }

      this.setVisible(true);
   }

   public boolean wasCancelled() {
      return this.m_wasCancelled;
   }

   public void onEscPressed() {
      this.m_wasCancelled = true;
   }

   public final Object addObject() {
      Object o = this.editor.createNewBean();
      this.editObject(o);
      return this.okclicked ? o : null;
   }

   private JPanel makeButtonPane() {
      JPanel ret = new JPanel();
      this.ok = new JButton("OK");
      this.cancel = new JButton("Cancel");
      ret.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1.0;
      gbc.anchor = 13;
      ret.add(this.ok, gbc);
      ++gbc.gridx;
      gbc.weightx = 0.0;
      gbc.insets.left = 5;
      ret.add(this.cancel, gbc);
      this.ok.addActionListener(this);
      this.cancel.addActionListener(this);
      return ret;
   }

   public void actionPerformed(ActionEvent ev) {
      if (ev.getSource() == this.ok) {
         this.m_wasCancelled = false;
         IValidationFeedback bbd = this.editor.getFeedback();
         if (bbd != null) {
            this.showError(bbd);
            return;
         }

         this.editor.uiToModel();
         this.okclicked = true;
      } else if (ev.getSource() == this.cancel) {
         this.m_wasCancelled = true;
      }

      this.setAutoCommit(true);
      this.setVisible(false);
   }

   private void showError(IValidationFeedback bbd) {
      Component comp = bbd.getFocusComponent();
      JOptionPane.showMessageDialog(this, bbd.getMessage(), fmt.getIncompleteSettings(), 0);
      if (comp != null) {
         comp.requestFocus();
      }

   }

   private void setAutoCommit(boolean b) {
      this.editor.setAutoCommit(b);
   }

   public void processKeyEvent(KeyEvent ev) {
      int keyCode = ev.getKeyCode();
      if (keyCode == 112) {
         Component c = SwingUtilities.findFocusOwner(this);
         String helpanchor = null;
      }
   }

   private static void ppp(String s) {
      System.out.println("[RowEditorDialog] " + s);
   }
}
