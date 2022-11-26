package com.solarmetric.profile.gui;

import com.solarmetric.ide.ui.HeaderFooterPanel;
import com.solarmetric.ide.ui.swing.XScrollPane;
import com.solarmetric.profile.ProfilingAgentImpl;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingPanel extends HeaderFooterPanel {
   private static final Localizer s_loc = Localizer.forPackage(ProfilingPanel.class);
   private ProfilingTree _tree;

   public ProfilingPanel(ProfilingAgentImpl agent, ProfilingInterfaceImpl iface) {
      this._tree = new ProfilingTree(new ProfilingTreeModel(agent), agent, iface);
      JScrollPane scrollPane = new XScrollPane(this._tree);
      scrollPane.setPreferredSize(new Dimension(200, 200));
      this.initialize(scrollPane, s_loc.get("profiling-panel-name").getMessage(), (String)null);
   }

   public ProfilingTree getTree() {
      return this._tree;
   }
}
