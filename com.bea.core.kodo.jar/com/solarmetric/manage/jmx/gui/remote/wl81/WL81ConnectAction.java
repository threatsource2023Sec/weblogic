package com.solarmetric.manage.jmx.gui.remote.wl81;

import com.solarmetric.ide.util.IconManager;
import com.solarmetric.manage.jmx.gui.JMXTree;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class WL81ConnectAction extends AbstractAction {
   private static Localizer s_loc = Localizer.forPackage(WL81ConnectAction.class);
   private IconManager _iconManager = IconManager.forClass(this.getClass());
   private JMXTree _jmxTree;
   private Log _log;

   public WL81ConnectAction(JMXTree jmxTree, Log log) {
      super(s_loc.get("wl81-connect-label").getMessage());
      this.putValue("ShortDescription", s_loc.get("wl81-connect-label").getMessage());
      ImageIcon icon = this._iconManager.getIcon("wl81-connect.gif");
      this.putValue("SmallIcon", icon);
      this._jmxTree = jmxTree;
      this._log = log;
   }

   public void actionPerformed(ActionEvent e) {
      JDialog dialog = new WL81ConnectDialog(s_loc.get("wl81-connect-label").getMessage(), true, this._jmxTree, this._log);
      dialog.pack();
      dialog.setVisible(true);
   }
}
