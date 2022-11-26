package com.solarmetric.manage.jmx.gui;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.table.AbstractTableModel;

public class MNotificationsTableModel extends AbstractTableModel {
   private MBeanInfo _mbInfo;
   private String[] columnNames = new String[]{"Name", "Description", "Types"};

   public MNotificationsTableModel(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo) {
      this._mbInfo = mbInfo;
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this._mbInfo.getNotifications().length;
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return this._mbInfo.getNotifications()[row].getName();
         case 1:
            return this._mbInfo.getNotifications()[row].getDescription();
         case 2:
            String[] types = this._mbInfo.getNotifications()[row].getNotifTypes();
            if (types.length == 0) {
               return "<none>";
            }

            StringBuffer typesBuf = new StringBuffer();
            typesBuf.append(types[0]);

            for(int i = 1; i < types.length; ++i) {
               typesBuf.append(", ");
               typesBuf.append(types[i]);
            }

            return typesBuf.toString();
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
