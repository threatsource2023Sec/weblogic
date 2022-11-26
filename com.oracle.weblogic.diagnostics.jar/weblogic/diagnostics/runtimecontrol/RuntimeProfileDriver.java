package weblogic.diagnostics.runtimecontrol;

import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.runtimecontrol.internal.RuntimeProfileDriverImpl;
import weblogic.management.ManagementException;

public abstract class RuntimeProfileDriver {
   public static RuntimeProfileDriver getInstance() {
      return RuntimeProfileDriverImpl.getSingleton();
   }

   public abstract boolean isEnabled(WLDFResourceBean var1);

   public abstract void deploy(WLDFResourceBean var1) throws ManagementException;

   public abstract void undeploy(WLDFResourceBean var1) throws ManagementException;

   public abstract void enable(WLDFResourceBean var1, boolean var2) throws ManagementException;

   public abstract boolean isEnabled(String var1);
}
