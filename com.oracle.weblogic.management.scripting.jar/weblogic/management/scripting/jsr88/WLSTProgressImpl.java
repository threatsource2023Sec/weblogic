package weblogic.management.scripting.jsr88;

import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.management.scripting.WLScriptContext;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class WLSTProgressImpl implements WLSTProgress {
   ProgressObject progressObject = null;
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;

   public WLSTProgressImpl(ProgressObject po, WLScriptContext ctx) {
      this.progressObject = po;
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public String getState() {
      return this.progressObject.getDeploymentStatus().getState().toString();
   }

   public boolean isRunning() {
      return this.progressObject.getDeploymentStatus().isRunning();
   }

   public boolean isFailed() {
      return this.progressObject.getDeploymentStatus().isFailed();
   }

   public boolean isCompleted() {
      return this.progressObject.getDeploymentStatus().isCompleted();
   }

   public String getMessage() {
      return this.progressObject.getDeploymentStatus().getMessage();
   }

   public String getCommandType() {
      return this.progressObject.getDeploymentStatus().getCommand().toString();
   }

   public void printStatus() {
      this.ctx.println(this.txtFmt.getCurrentStatus());
      this.ctx.println(this.txtFmt.getDeploymentCommandType(this.getCommandType()));
      this.ctx.println(this.txtFmt.getDeploymentState(this.getState()));
      if (this.getMessage() == null) {
         this.ctx.println(this.txtFmt.getDeploymentNoMsg());
      } else {
         this.ctx.println(this.txtFmt.getDeploymentMsg("" + this.getMessage()));
      }

   }

   public ProgressObject getProgressObject() {
      return this.progressObject;
   }
}
