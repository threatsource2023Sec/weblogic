package com.solarmetric.manage.jmx.gui;

import java.lang.reflect.Constructor;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.swing.table.AbstractTableModel;

public class OperationExecuteTableModel extends AbstractTableModel {
   private MBeanOperationInfo _opInfo;
   private Object[] _args;
   private String[] columnNames = new String[]{"Name", "Description", "Type", "Value"};

   public OperationExecuteTableModel(MBeanOperationInfo opInfo) {
      this._opInfo = opInfo;
      this._args = new Object[this._opInfo.getSignature().length];
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this._opInfo.getSignature().length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return this._opInfo.getSignature()[row].getName();
         case 1:
            return this._opInfo.getSignature()[row].getDescription();
         case 2:
            return this._opInfo.getSignature()[row].getType();
         case 3:
            if (this._args[row] == null) {
               return "<null>";
            }

            return this._args[row].toString();
         default:
            return "";
      }
   }

   public Class getColumnClass(int col) {
      return String.class;
   }

   public boolean isCellEditable(int row, int col) {
      return col >= 3;
   }

   public Object[] getArgs() {
      return this._args;
   }

   public void setValueAt(Object value, int row, int col) {
      MBeanParameterInfo paramInfo = this._opInfo.getSignature()[row];
      String typeName = paramInfo.getType();

      try {
         if (typeName.equals("int")) {
            this._args[row] = new Integer((String)value);
         } else if (typeName.equals("double")) {
            this._args[row] = new Double((String)value);
         } else if (typeName.equals("boolean")) {
            this._args[row] = Boolean.valueOf((String)value);
         } else if (typeName.equals("java.lang.Object")) {
            this._args[row] = value;
         } else {
            Class valClass = Class.forName(typeName);
            Constructor c = valClass.getConstructor(String.class);
            this._args[row] = c.newInstance(value);
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }
}
