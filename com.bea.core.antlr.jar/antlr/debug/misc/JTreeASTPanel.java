package antlr.debug.misc;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;

public class JTreeASTPanel extends JPanel {
   JTree tree;

   public JTreeASTPanel(TreeModel var1, TreeSelectionListener var2) {
      this.setLayout(new BorderLayout());
      this.tree = new JTree(var1);
      this.tree.putClientProperty("JTree.lineStyle", "Angled");
      if (var2 != null) {
         this.tree.addTreeSelectionListener(var2);
      }

      JScrollPane var3 = new JScrollPane();
      var3.getViewport().add(this.tree);
      this.add(var3, "Center");
   }
}
