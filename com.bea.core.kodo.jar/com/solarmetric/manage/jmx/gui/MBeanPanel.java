package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XSplitPane;
import com.solarmetric.ide.ui.swing.XTabbedPane;
import com.solarmetric.ide.ui.swing.XTable;
import com.solarmetric.manage.jmx.NotificationDispatchListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import org.apache.openjpa.lib.conf.Configuration;

public class MBeanPanel extends XPanel {
   public MBeanPanel(MBeanServer server, ObjectInstance instance, MBeanInfo mbInfo, NotificationDispatchListener dispatcher, Configuration conf) {
      this.setLayout(new GridLayout(1, 1));
      JSplitPane splitPane = new XSplitPane(0);
      this.add(new HeaderFooterPanel(splitPane, instance.getObjectName().toString() + " (" + mbInfo.getDescription() + ")", (String)null));
      JPanel notifPanel = new XPanel();
      notifPanel.setLayout(new GridLayout(1, 1));
      HashMap chartPanels = StatisticPanelFactory.createStatisticPanels(server, instance, mbInfo, dispatcher, conf);
      HashMap notifPanels = NotificationPanelFactory.createNotificationPanels(server, instance, mbInfo, dispatcher);
      if (chartPanels.size() <= 0 && notifPanels.size() <= 0) {
         JLabel label = new XLabel("<No Notifications>");
         label.setHorizontalAlignment(0);
         notifPanel.add(label);
      } else {
         JTabbedPane tabbedPane = new XTabbedPane();
         notifPanel.add(tabbedPane);
         notifPanel.setPreferredSize(new Dimension(300, 200));
         notifPanel.setMinimumSize(new Dimension(200, 200));
         Iterator i = chartPanels.entrySet().iterator();

         Map.Entry entry;
         while(i.hasNext()) {
            entry = (Map.Entry)i.next();
            tabbedPane.add((String)entry.getKey(), (JComponent)entry.getValue());
         }

         i = notifPanels.entrySet().iterator();

         while(i.hasNext()) {
            entry = (Map.Entry)i.next();
            tabbedPane.add((String)entry.getKey(), (JComponent)entry.getValue());
         }
      }

      JPanel infoPanel = new XPanel();
      infoPanel.setLayout(new GridLayout(1, 1));
      JTabbedPane infoTabbedPane = new XTabbedPane();
      infoPanel.add(infoTabbedPane);
      JTable attrTable = new XTable(new MAttributesTableModel(server, instance, mbInfo));
      infoTabbedPane.add("Attributes", new XScrollPane(attrTable));
      JTable operTable = new XTable(new MOperationsTableModel(server, instance, mbInfo));
      infoTabbedPane.add("Operations", new XScrollPane(operTable));
      JTable notifTable = new XTable(new MNotificationsTableModel(server, instance, mbInfo));
      infoTabbedPane.add("Notifications", new XScrollPane(notifTable));
      splitPane.setBottomComponent(infoPanel);
      splitPane.setTopComponent(new XScrollPane(notifPanel));
      splitPane.setDividerLocation((int)notifPanel.getMinimumSize().getHeight() + 5);
      splitPane.setDividerSize(5);
   }
}
