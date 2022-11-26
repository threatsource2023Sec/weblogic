package kodo.profile.gui;

import com.solarmetric.ide.ui.treetable.AbstractTreeTableModel;

public class EmptyFetchDataTreeTableModel extends AbstractTreeTableModel {
   private String[] columnNames = new String[]{"Field Name", "Total", "Fetched (% Used)", "Unfetched (% Used)"};

   public EmptyFetchDataTreeTableModel() {
      super(new Object());
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(Object node, int col) {
      return "";
   }

   public Class getColumnClass(int col) {
      return String.class;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }

   public boolean isLeaf(Object node) {
      return true;
   }

   public Object getChild(Object parent, int index) {
      return null;
   }

   public int getChildCount(Object parent) {
      return 0;
   }

   public int getIndexOfChild(Object parent, Object child) {
      return -1;
   }
}
