package org.mozilla.javascript.tools.debugger;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class MyTreeTable extends JTreeTable {
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$tools$debugger$TreeTableModel;

   public MyTreeTable(TreeTableModel var1) {
      super(var1);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public JTree resetTree(TreeTableModel var1) {
      super.tree = new JTreeTable.TreeTableCellRenderer(var1);
      super.setModel(new TreeTableModelAdapter(var1, super.tree));
      JTreeTable.ListToTreeSelectionModelWrapper var2 = new JTreeTable.ListToTreeSelectionModelWrapper();
      super.tree.setSelectionModel(var2);
      this.setSelectionModel(var2.getListSelectionModel());
      if (super.tree.getRowHeight() < 1) {
         this.setRowHeight(18);
      }

      this.setDefaultRenderer(class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")), super.tree);
      this.setDefaultEditor(class$org$mozilla$javascript$tools$debugger$TreeTableModel != null ? class$org$mozilla$javascript$tools$debugger$TreeTableModel : (class$org$mozilla$javascript$tools$debugger$TreeTableModel = class$("org.mozilla.javascript.tools.debugger.TreeTableModel")), new JTreeTable.TreeTableCellEditor());
      this.setShowGrid(true);
      this.setIntercellSpacing(new Dimension(1, 1));
      super.tree.setRootVisible(false);
      super.tree.setShowsRootHandles(true);
      DefaultTreeCellRenderer var3 = (DefaultTreeCellRenderer)super.tree.getCellRenderer();
      var3.setOpenIcon((Icon)null);
      var3.setClosedIcon((Icon)null);
      var3.setLeafIcon((Icon)null);
      return super.tree;
   }
}
