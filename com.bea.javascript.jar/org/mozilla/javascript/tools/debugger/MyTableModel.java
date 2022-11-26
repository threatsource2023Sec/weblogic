package org.mozilla.javascript.tools.debugger;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class MyTableModel extends AbstractTableModel {
   Main db;
   Vector expressions;
   Vector values;

   MyTableModel(Main var1) {
      this.db = var1;
      this.expressions = new Vector();
      this.values = new Vector();
      this.expressions.addElement("");
      this.values.addElement("");
   }

   public int getColumnCount() {
      return 2;
   }

   public String getColumnName(int var1) {
      switch (var1) {
         case 0:
            return "Expression";
         case 1:
            return "Value";
         default:
            return null;
      }
   }

   public int getRowCount() {
      return this.expressions.size();
   }

   public Object getValueAt(int var1, int var2) {
      switch (var2) {
         case 0:
            return this.expressions.elementAt(var1);
         case 1:
            return this.values.elementAt(var1);
         default:
            return "";
      }
   }

   public boolean isCellEditable(int var1, int var2) {
      return true;
   }

   public void setValueAt(Object var1, int var2, int var3) {
      switch (var3) {
         case 0:
            String var4 = var1.toString();
            this.expressions.setElementAt(var4, var2);
            String var5 = "";
            if (var4.length() > 0) {
               var5 = this.db.eval(var4);
               if (var5 == null) {
                  var5 = "";
               }
            }

            this.values.setElementAt(var5, var2);
            this.updateModel();
            if (var2 + 1 == this.expressions.size()) {
               this.expressions.addElement("");
               this.values.addElement("");
               this.fireTableRowsInserted(var2 + 1, var2 + 1);
            }
            break;
         case 1:
            this.fireTableDataChanged();
      }

   }

   void updateModel() {
      for(int var1 = 0; var1 < this.expressions.size(); ++var1) {
         Object var2 = this.expressions.elementAt(var1);
         String var3 = var2.toString();
         String var4 = "";
         if (var3.length() > 0) {
            var4 = this.db.eval(var3);
            if (var4 == null) {
               var4 = "";
            }
         } else {
            var4 = "";
         }

         var4 = var4.replace('\n', ' ');
         this.values.setElementAt(var4, var1);
      }

      this.fireTableDataChanged();
   }
}
