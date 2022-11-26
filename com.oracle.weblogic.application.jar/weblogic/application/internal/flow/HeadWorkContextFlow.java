package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.management.DeploymentException;

public final class HeadWorkContextFlow extends BaseWorkContextFlow {
   public HeadWorkContextFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      this.setBindApplicationIdCtx("HeadWorkContextFlow.prepare");
   }

   public void activate() throws DeploymentException {
      this.setBindApplicationIdCtx("HeadWorkContextFlow.activate");
   }

   public void deactivate() throws DeploymentException {
      this.unsetBindApplicationIdCtx("HeadWorkContextFlow.deactivate");
   }

   public void unprepare() throws DeploymentException {
      this.unsetBindApplicationIdCtx("HeadWorkContextFlow.unprepare");
   }

   public void remove() throws DeploymentException {
      this.unsetBindApplicationIdCtx("HeadWorkContextFlow.remove");
   }

   public void start() throws DeploymentException {
      this.setBindApplicationIdCtx("HeadWorkContextFlow.start");
   }

   public void stop() throws DeploymentException {
      this.unsetBindApplicationIdCtx("HeadWorkContextFlow.stop");
   }
}
