package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.management.runtime.ComponentRuntimeMBean;

public final class ComponentRuntimeStateFlow extends BaseFlow {
   public ComponentRuntimeStateFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   private void setDeploymentState(int state) {
      this.setDeploymentState(state, this.appCtx.getApplicationModules());
   }

   private void setDeploymentState(int state, Module[] m) {
      if (m != null) {
         for(int i = 0; i < m.length; ++i) {
            ComponentRuntimeMBean[] c = m[i].getComponentRuntimeMBeans();
            if (c != null) {
               for(int j = 0; j < c.length; ++j) {
                  if (c[j] != null) {
                     c[j].setDeploymentState(state);
                  }
               }
            }
         }

      }
   }

   public void prepare() {
      this.setDeploymentState(1);
   }

   public void activate() {
      this.setDeploymentState(2);
   }

   public void start(String[] uris) {
      this.setDeploymentState(2, this.appCtx.getStartingModules());
   }

   public void stop(String[] uris) {
      this.setDeploymentState(0, this.appCtx.getStoppingModules());
   }

   public void deactivate() {
      this.setDeploymentState(1);
   }

   public void unprepare() {
      this.setDeploymentState(0);
   }

   public void prepareUpdate(String[] uris) {
      this.setDeploymentState(4);
   }

   public void activateUpdate(String[] uris) {
      this.setDeploymentState(2);
   }

   public void rollbackUpdate(String[] uris) {
      this.setDeploymentState(2);
   }
}
