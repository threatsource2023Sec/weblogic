package com.solarmetric.manage.jmx.gui;

import javax.management.MBeanInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.table.AbstractTableModel;

public class MOperationsTableModel extends AbstractTableModel {
   private MBeanInfo _mbInfo;
   private String[] columnNames = new String[]{"Name", "Description", "Impact", "Returns", "Parameters"};

   public MOperationsTableModel(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo) {
      this._mbInfo = mbInfo;
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this._mbInfo.getOperations().length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return this._mbInfo.getOperations()[row].getName();
         case 1:
            return this._mbInfo.getOperations()[row].getDescription();
         case 2:
            int impact = this._mbInfo.getOperations()[row].getImpact();
            switch (impact) {
               case 0:
                  return "INFO";
               case 1:
                  return "ACTION";
               case 2:
                  return "ACTION_INFO";
               case 3:
                  return "UNKNOWN";
               default:
                  return "";
            }
         case 3:
            return this._mbInfo.getOperations()[row].getReturnType();
         case 4:
            MBeanParameterInfo[] params = this._mbInfo.getOperations()[row].getSignature();
            if (params.length == 0) {
               return "none";
            }

            StringBuffer paramBuf = new StringBuffer();
            paramBuf.append(params[0].getType());
            paramBuf.append(" ");
            paramBuf.append(params[0].getName());

            for(int i = 1; i < params.length; ++i) {
               paramBuf.append(", ");
               paramBuf.append(params[i].getType());
               paramBuf.append(" ");
               paramBuf.append(params[i].getName());
            }

            return paramBuf.toString();
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
