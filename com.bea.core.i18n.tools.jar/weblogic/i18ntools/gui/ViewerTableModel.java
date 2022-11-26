package weblogic.i18ntools.gui;

import javax.swing.table.AbstractTableModel;

class ViewerTableModel extends AbstractTableModel {
   MessageViewer viewer;
   String[] titles;

   public ViewerTableModel(MessageViewer vwr, String[] tts) {
      this.viewer = vwr;
      this.titles = tts;
   }

   public int getColumnCount() {
      return this.titles != null ? this.titles.length : 0;
   }

   public int getRowCount() {
      return this.viewer.myData != null ? this.viewer.myData.length : 0;
   }

   public Object getValueAt(int row, int col) {
      return this.viewer.myData[row][col];
   }

   public String getColumnName(int column) {
      return this.titles[column];
   }

   public Class getColumnClass(int col) {
      Object value = this.getValueAt(0, col);
      return value != null ? value.getClass() : null;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }
}
