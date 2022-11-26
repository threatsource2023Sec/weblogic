package weblogic.management;

import java.io.PrintWriter;
import java.util.Arrays;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/** @deprecated */
@Deprecated
public final class DistributedOperationUpdateException extends DistributedUpdateException {
   private static final long serialVersionUID = -1592802979693240641L;
   private String actionName;
   private Object[] params;
   private String[] signature;

   public DistributedOperationUpdateException(ObjectName remoteNameArg, MBeanServer remoteMBeanServerArg, Exception exceptionArg, String actionNameArg, Object[] paramsArg, String[] signatureArg) {
      super(remoteNameArg, remoteMBeanServerArg, exceptionArg);
      this.actionName = actionNameArg;
      this.params = paramsArg;
      this.signature = signatureArg;
   }

   public String getActionName() {
      return this.actionName;
   }

   public Object[] getParams() {
      return this.params;
   }

   public String[] getSignature() {
      return this.signature;
   }

   public void printExceptionInfo(PrintWriter writer) {
      super.printExceptionInfo(writer);
      writer.println("- actionName: " + this.getActionName());
      writer.println("- params: " + Arrays.toString(this.getParams()));
      writer.println("- signature: " + Arrays.toString(this.getSignature()));
   }
}
