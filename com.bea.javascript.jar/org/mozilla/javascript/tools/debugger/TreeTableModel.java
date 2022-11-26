package org.mozilla.javascript.tools.debugger;

import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel {
   Class getColumnClass(int var1);

   int getColumnCount();

   String getColumnName(int var1);

   Object getValueAt(Object var1, int var2);

   boolean isCellEditable(Object var1, int var2);

   void setValueAt(Object var1, Object var2, int var3);
}
