package org.mozilla.javascript.tools.debugger;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

public class TreeTableModelAdapter extends AbstractTableModel {
   JTree tree;
   TreeTableModel treeTableModel;

   public TreeTableModelAdapter(TreeTableModel var1, JTree var2) {
      this.tree = var2;
      this.treeTableModel = var1;
      var2.addTreeExpansionListener(new TreeExpansionListener() {
         public void treeCollapsed(TreeExpansionEvent var1) {
            TreeTableModelAdapter.this.fireTableDataChanged();
         }

         public void treeExpanded(TreeExpansionEvent var1) {
            TreeTableModelAdapter.this.fireTableDataChanged();
         }
      });
      var1.addTreeModelListener(new TreeModelListener() {
         public void treeNodesChanged(TreeModelEvent var1) {
            TreeTableModelAdapter.this.delayedFireTableDataChanged();
         }

         public void treeNodesInserted(TreeModelEvent var1) {
            TreeTableModelAdapter.this.delayedFireTableDataChanged();
         }

         public void treeNodesRemoved(TreeModelEvent var1) {
            TreeTableModelAdapter.this.delayedFireTableDataChanged();
         }

         public void treeStructureChanged(TreeModelEvent var1) {
            TreeTableModelAdapter.this.delayedFireTableDataChanged();
         }
      });
   }

   protected void delayedFireTableDataChanged() {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            TreeTableModelAdapter.this.fireTableDataChanged();
         }
      });
   }

   public Class getColumnClass(int var1) {
      return this.treeTableModel.getColumnClass(var1);
   }

   public int getColumnCount() {
      return this.treeTableModel.getColumnCount();
   }

   public String getColumnName(int var1) {
      return this.treeTableModel.getColumnName(var1);
   }

   public int getRowCount() {
      return this.tree.getRowCount();
   }

   public Object getValueAt(int var1, int var2) {
      return this.treeTableModel.getValueAt(this.nodeForRow(var1), var2);
   }

   public boolean isCellEditable(int var1, int var2) {
      return this.treeTableModel.isCellEditable(this.nodeForRow(var1), var2);
   }

   protected Object nodeForRow(int var1) {
      TreePath var2 = this.tree.getPathForRow(var1);
      return var2.getLastPathComponent();
   }

   public void setValueAt(Object var1, int var2, int var3) {
      this.treeTableModel.setValueAt(var1, this.nodeForRow(var2), var3);
   }
}
