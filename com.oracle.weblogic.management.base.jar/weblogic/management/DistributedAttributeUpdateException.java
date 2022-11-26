package weblogic.management;

import java.io.PrintWriter;
import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/** @deprecated */
@Deprecated
public final class DistributedAttributeUpdateException extends DistributedUpdateException {
   private static final long serialVersionUID = -3780351746236507273L;
   private static final String NONE = "none specified";
   private Attribute attribute;

   public DistributedAttributeUpdateException(ObjectName remoteNameArg, MBeanServer remoteMBeanServerArg, Exception exceptionArg, Attribute attributeArg) {
      super(remoteNameArg, remoteMBeanServerArg, exceptionArg);
      this.attribute = attributeArg;
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public void printExceptionInfo(PrintWriter writer) {
      super.printExceptionInfo(writer);
      String attribute = "none specified";
      if (this.getAttribute() != null) {
         attribute = this.getAttribute().getName();
      }

      writer.println("- attribute: " + attribute);
   }
}
