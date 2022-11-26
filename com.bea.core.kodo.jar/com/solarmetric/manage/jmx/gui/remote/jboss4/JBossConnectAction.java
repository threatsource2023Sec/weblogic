package com.solarmetric.manage.jmx.gui.remote.jboss4;

import com.solarmetric.ide.util.IconManager;
import com.solarmetric.manage.jmx.gui.JMXTree;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class JBossConnectAction extends AbstractAction {
   private static Localizer s_loc = Localizer.forPackage(JBossConnectAction.class);
   private IconManager _iconManager = IconManager.forClass(this.getClass());
   private JMXTree _jmxTree;
   private Log _log;

   public JBossConnectAction(JMXTree jmxTree, Log log) {
      super(s_loc.get("jboss-connect-label").getMessage());
      this.putValue("ShortDescription", s_loc.get("jboss-connect-label").getMessage());
      ImageIcon icon = this._iconManager.getIcon("jboss-connect.gif");
      this.putValue("SmallIcon", icon);
      this._jmxTree = jmxTree;
      this._log = log;
   }

   public void actionPerformed(ActionEvent e) {
      JDialog dialog = new JBossConnectDialog(s_loc.get("jboss-connect-label").getMessage(), true, this._jmxTree, this._log);
      dialog.pack();
      dialog.setVisible(true);
   }
}
