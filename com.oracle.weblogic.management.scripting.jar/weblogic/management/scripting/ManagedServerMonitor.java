package weblogic.management.scripting;

import java.rmi.Remote;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.spi.EndPoint;

public class ManagedServerMonitor implements DisconnectListener {
   private EndPoint endPoint;
   private WLScriptContext ctx;
   private WLSTMsgTextFormatter txtFmt;

   public ManagedServerMonitor() {
   }

   public ManagedServerMonitor(String name, EndPoint ep, WLScriptContext ctx) {
      this.endPoint = ep;
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public void onDisconnect(DisconnectEvent event) {
      try {
         this.ctx.println(this.txtFmt.getLostConnection());
         this.endPoint.removeDisconnectListener((Remote)null, this);
         this.ctx.wlstHelper.disconnect();
         this.ctx.connected = "false";
         if (this.ctx.isEditSessionInProgress) {
            this.ctx.resetEditSession();
         }

         this.ctx.dc("true");
      } catch (Throwable var3) {
      }

   }

   public void initialize(String name, Object ep, WLScriptContext ctx) {
      try {
         this.endPoint = (EndPoint)ep;
         this.ctx = ctx;
         Remote rm = null;
         this.endPoint.addDisconnectListener((Remote)rm, this);
      } catch (Exception var5) {
      }

   }

   public void removeDisconnectListener() {
      try {
         this.endPoint.removeDisconnectListener((Remote)null, this);
      } catch (Exception var2) {
      }

   }
}
