package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.ide.ui.swing.XTextArea;
import com.solarmetric.profile.EventInfo;
import java.awt.GridLayout;
import javax.swing.JTextArea;

public class EventInfoPanelImpl extends EventInfoPanel {
   private JTextArea _infoArea;
   private HeaderFooterPanel _header;
   private EventInfo _currentInfo;

   public EventInfoPanelImpl() {
      this._currentInfo = null;
      this.setLayout(new GridLayout(1, 1));
      this._infoArea = new XTextArea();
      this._infoArea.setEditable(false);
      this._infoArea.setLineWrap(true);
      this._infoArea.setWrapStyleWord(true);
      this._header = new HeaderFooterPanel(new XScrollPane(this._infoArea), "Node: <not selected>", (String)null);
      this.add(this._header);
   }

   public EventInfoPanelImpl(EventInfo info) {
      this();
      this.setInfo(info);
   }

   public void setInfo(EventInfo info) {
      this._currentInfo = info;
      if (info != null) {
         this._header.setHeaderLabel("Node: " + info.getName());
         this._infoArea.setText(info.getDescription());
      } else {
         this._header.setHeaderLabel("Node: <not selected>");
         this._infoArea.setText("");
      }

   }

   public void update() {
      this.setInfo(this._currentInfo);
   }
}
