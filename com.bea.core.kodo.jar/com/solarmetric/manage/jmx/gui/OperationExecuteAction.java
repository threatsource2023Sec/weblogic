package com.solarmetric.manage.jmx.gui;

import java.awt.event.ActionEvent;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class OperationExecuteAction extends AbstractAction {
   private static Localizer s_loc = Localizer.forPackage(OperationExecuteAction.class);
   private MBeanServer _server;
   private ObjectInstance _instance;
   private MBeanOperationInfo _operInfo;
   private Log _log;

   public OperationExecuteAction(MBeanServer server, ObjectInstance instance, MBeanOperationInfo operInfo, Log log) {
      super(s_loc.get("opexec-label").getMessage());
      this.putValue("ShortDescription", s_loc.get("opexec-short-desc").getMessage());
      this._server = server;
      this._instance = instance;
      this._operInfo = operInfo;
      this._log = log;
   }

   public void actionPerformed(ActionEvent e) {
      JDialog dialog = new OperationExecuteDialog(s_loc.get("opexec-label").getMessage(), true, this._server, this._instance, this._operInfo, this._log);
      dialog.pack();
      dialog.setVisible(true);
   }
}
