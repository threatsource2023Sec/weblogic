package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XTable;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JPanel;
import javax.swing.JTable;

public class NotificationPanelFactory {
   public static HashMap createNotificationPanels(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher) {
      HashMap notifPanels = new HashMap();
      MBeanNotificationInfo[] notifs = mbInfo.getNotifications();

      for(int i = 0; i < notifs.length; ++i) {
         MBeanNotificationInfo notif = notifs[i];
         String notifType = notif.getDescription();
         if (!notifType.startsWith("Statistic:")) {
            NotificationsTableModel tableModel = new NotificationsTableModel();
            JTable table = new XTable(tableModel);
            JPanel notifPanel = new XPanel();
            notifPanel.setLayout(new GridLayout(1, 1));
            notifPanel.add(new XScrollPane(table));
            dispatcher.addListener(notif, tableModel);
            notifPanels.put(notif.getDescription(), notifPanel);
         }
      }

      return notifPanels;
   }
}
