package com.solarmetric.manage.jmx.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XLabel;
import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.management.MBeanServer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import org.apache.openjpa.lib.conf.Configuration;

public class DashboardPanel extends XPanel {
   public DashboardPanel(MBeanServer server, DashboardMetaData dashboard, JMXTree tree, Configuration conf) {
      this.setLayout(new GridLayout(1, 1));
      JPanel notifPanel = new ScrollablePanel();
      List chartPanelsList = new ArrayList();
      int panelCount = 0;
      List chartPanelList = dashboard.getChartPanels();

      Iterator i;
      HashMap chartPanels;
      for(i = chartPanelList.iterator(); i.hasNext(); panelCount += chartPanels.size()) {
         DashboardChartPanelMetaData chartPanel = (DashboardChartPanelMetaData)i.next();
         chartPanels = StatisticPanelFactory.createStatisticPanels(server, chartPanel, new NotificationDispatchManager(tree), conf);
         chartPanelsList.add(chartPanels);
      }

      if (panelCount > 0) {
         notifPanel.setLayout(new GridLayout((panelCount + 1) / 2, 2));
         i = chartPanelsList.iterator();

         while(i.hasNext()) {
            HashMap chartPanels = (HashMap)i.next();
            Iterator j = chartPanels.entrySet().iterator();

            while(j.hasNext()) {
               Map.Entry entry = (Map.Entry)j.next();
               notifPanel.add((String)entry.getKey(), (JComponent)entry.getValue());
            }
         }
      } else {
         notifPanel.setLayout(new GridLayout(1, 1));
         JLabel label = new XLabel("<No Notifications>");
         label.setHorizontalAlignment(0);
         notifPanel.add(label);
      }

      this.add(new HeaderFooterPanel(new XScrollPane(notifPanel, 20, 31), dashboard.getName() + " (" + dashboard.getDescription() + ")", (String)null));
   }

   public static class ScrollablePanel extends XPanel implements Scrollable {
      public Dimension getPreferredScrollableViewportSize() {
         return this.getPreferredSize();
      }

      public int getScrollableBlockIncrement(Rectangle visRect, int orientation, int direction) {
         return 50;
      }

      public int getScrollableUnitIncrement(Rectangle visRect, int orientation, int direction) {
         return 10;
      }

      public boolean getScrollableTracksViewportHeight() {
         return false;
      }

      public boolean getScrollableTracksViewportWidth() {
         return true;
      }
   }
}
