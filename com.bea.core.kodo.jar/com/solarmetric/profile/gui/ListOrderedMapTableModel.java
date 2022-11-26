package com.solarmetric.profile.gui;

import javax.swing.table.AbstractTableModel;
import org.apache.commons.collections.map.ListOrderedMap;

public class ListOrderedMapTableModel extends AbstractTableModel {
   private ListOrderedMap _map;
   private String[] columnNames = new String[]{"Key", "Value"};

   public ListOrderedMapTableModel(ListOrderedMap map) {
      this._map = map;
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this._map.size();
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return this._map.get(row).toString();
         case 1:
            return this._map.getValue(row).toString();
         default:
            return "";
      }
   }

   public Class getColumnClass(int col) {
      return String.class;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }
}
