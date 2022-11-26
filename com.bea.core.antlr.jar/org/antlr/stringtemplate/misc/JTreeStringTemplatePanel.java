package org.antlr.stringtemplate.misc;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;

public class JTreeStringTemplatePanel extends JPanel {
   JTree tree;

   public JTreeStringTemplatePanel(TreeModel tm, TreeSelectionListener listener) {
      this.setLayout(new BorderLayout());
      this.tree = new JTree(tm);
      this.tree.putClientProperty("JTree.lineStyle", "Angled");
      if (listener != null) {
         this.tree.addTreeSelectionListener(listener);
      }

      JScrollPane sp = new JScrollPane();
      sp.getViewport().add(this.tree);
      this.add(sp, "Center");
   }
}
