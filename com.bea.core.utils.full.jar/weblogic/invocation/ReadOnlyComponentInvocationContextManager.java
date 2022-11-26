package weblogic.invocation;

import java.security.Principal;

class ReadOnlyComponentInvocationContextManager extends ComponentInvocationContextManager {
   private ComponentInvocationContextManager cicm;
   private SecurityException se;

   ReadOnlyComponentInvocationContextManager(ComponentInvocationContextManager cicm) {
      this.cicm = cicm;
      this.se = new SecurityException("No subject provided during ComponentInvocationContextManager creation");
   }

   ReadOnlyComponentInvocationContextManager(ComponentInvocationContextManager cicm, SecurityException se) {
      this.cicm = cicm;
      this.se = se;
   }

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      return this.cicm.getCurrentComponentInvocationContext();
   }

   public ComponentInvocationContext createComponentInvocationContext(String applicationId, String moduleName, String componentName) {
      return this.cicm.createComponentInvocationContext(applicationId, moduleName, componentName);
   }

   public ComponentInvocationContext createComponentInvocationContext(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
      return this.cicm.createComponentInvocationContext(partitionName, applicationName, applicationVersion, moduleName, componentName);
   }

   public void addInvocationContextChangeListener(ComponentInvocationContextChangeListener ic) {
      this.cicm.addInvocationContextChangeListener(ic);
   }

   public void removeInvocationContextChangeListener(ComponentInvocationContextChangeListener ic) {
      this.cicm.removeInvocationContextChangeListener(ic);
   }

   protected void checkIfKernel(Principal sub) {
      assert false;

   }

   public void pushComponentInvocationContext(ComponentInvocationContext ci) {
      warnUnsecureCallers();
      this.cicm.pushComponentInvocationContext(ci);
   }

   public void popComponentInvocationContext() {
      warnUnsecureCallers();
      this.cicm.popComponentInvocationContext();
   }

   public ManagedInvocationContext setCurrentComponentInvocationContext(ComponentInvocationContext cic) {
      warnUnsecureCallers();
      return this.cicm.setCurrentComponentInvocationContext(cic);
   }
}
