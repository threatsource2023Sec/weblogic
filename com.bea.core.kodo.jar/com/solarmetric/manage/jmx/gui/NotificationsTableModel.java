package com.solarmetric.manage.jmx.gui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.swing.table.AbstractTableModel;

public class NotificationsTableModel extends AbstractTableModel implements NotificationListener {
   private ArrayList notifs = new ArrayList();
   private DateFormat formatter = DateFormat.getDateTimeInstance();
   private String[] columnNames = new String[]{"Sequence", "Timestamp", "Source", "Type", "Message", "User Data"};

   public void addNotification(Notification notif) {
      this.notifs.add(notif);
      this.fireTableRowsInserted(this.notifs.size() - 1, this.notifs.size() - 1);
   }

   public void handleNotification(Notification notif, Object handback) {
      this.addNotification(notif);
   }

   public int getColumnCount() {
      return this.columnNames.length;
   }

   public int getRowCount() {
      return this.notifs.size();
   }

   public String getColumnName(int col) {
      return this.columnNames[col];
   }

   public Object getValueAt(int row, int col) {
      switch (col) {
         case 0:
            return new Long(((Notification)this.notifs.get(row)).getSequenceNumber());
         case 1:
            return this.formatter.format(new Date(((Notification)this.notifs.get(row)).getTimeStamp()));
         case 2:
            return ((Notification)this.notifs.get(row)).getSource().toString();
         case 3:
            return ((Notification)this.notifs.get(row)).getType();
         case 4:
            return ((Notification)this.notifs.get(row)).getMessage();
         case 5:
            Object userdata = ((Notification)this.notifs.get(row)).getUserData();
            if (userdata != null) {
               return ((Notification)this.notifs.get(row)).getUserData().toString();
            }

            return "";
         default:
            return "";
      }
   }

   public Class getColumnClass(int col) {
      switch (col) {
         case 0:
            return Long.class;
         case 1:
            return String.class;
         case 2:
            return String.class;
         case 3:
            return String.class;
         case 4:
            return String.class;
         case 5:
            return String.class;
         default:
            return Object.class;
      }
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }
}
