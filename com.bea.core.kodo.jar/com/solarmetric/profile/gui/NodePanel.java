package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.swing.XPanel;
import com.solarmetric.ide.ui.swing.XSplitPane;
import com.solarmetric.manage.SampleStatistic;
import com.solarmetric.profile.EventInfo;
import com.solarmetric.profile.Node;
import java.awt.GridLayout;
import javax.swing.JSplitPane;

public class NodePanel extends XPanel {
   private EventInfoPanel _defaultInfoPanel;
   private StatisticPanel _statPanel;
   private JSplitPane _splitPane;
   private Node _currentNode;
   private EventInfoPanel _currentViewer;

   public NodePanel() {
      this.setLayout(new GridLayout(1, 1));
      this._defaultInfoPanel = new EventInfoPanelImpl();
      this._statPanel = new StatisticPanel();
      this._splitPane = new XSplitPane(0, this._defaultInfoPanel, this._statPanel);
      this._splitPane.setDividerLocation(335);
      this._splitPane.setDividerSize(3);
      this.add(this._splitPane);
   }

   public NodePanel(Node node) {
      this();
      this.setNode(node);
   }

   public void setNode(Node node) {
      this._currentNode = node;
      if (node != null) {
         String viewerName = node.getInfo().getViewerClassName();
         if (viewerName == null) {
            this._currentViewer = this._defaultInfoPanel;
         } else {
            try {
               Class viewerClass = Class.forName(viewerName);
               this._currentViewer = (EventInfoPanel)viewerClass.newInstance();
            } catch (Exception var4) {
               this._currentViewer = this._defaultInfoPanel;
            }
         }

         if (this._splitPane.getTopComponent() != this._currentViewer) {
            int location = this._splitPane.getDividerLocation();
            this._splitPane.setTopComponent(this._currentViewer);
            this._splitPane.setDividerLocation(location);
         }

         this._currentViewer.setInfo(node.getInfo());
         this._statPanel.setStatistic(node.getStatistic());
      } else {
         this._defaultInfoPanel.setInfo((EventInfo)null);
         this._statPanel.setStatistic((SampleStatistic)null);
      }

   }

   public void update() {
      if (this._currentNode != null && this._currentViewer != null) {
         this._currentViewer.update();
         this._statPanel.update();
      }

   }
}
