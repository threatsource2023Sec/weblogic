package weblogic.management;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import weblogic.utils.NestedException;

public abstract class DistributedUpdateException extends NestedException {
   private ObjectName remoteName;
   private MBeanServer remoteMBeanServer;

   public DistributedUpdateException(ObjectName remoteNameArg, MBeanServer remoteMBeanServerArg, Exception exceptionArg) {
      super(exceptionArg);
      this.remoteName = remoteNameArg;
      this.remoteMBeanServer = remoteMBeanServerArg;
   }

   public ObjectName getRemoteName() {
      return this.remoteName;
   }

   public MBeanServer getRemoteMBeanServer() {
      return this.remoteMBeanServer;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream stream) {
      this.printStackTrace(new PrintWriter(stream));
   }

   public void printStackTrace(PrintWriter writer) {
      this.printExceptionInfo(writer);
      writer.println("Distributed update exception");
      writer.println("- remote object: " + this.getRemoteName());
      writer.println("- remote server: " + this.getRemoteMBeanServer());
      super.printStackTrace(writer);
   }

   public void printExceptionInfo(PrintWriter writer) {
      writer.println("Distributed update exception");
      writer.println("- remote object: " + this.getRemoteName());
      writer.println("- remote server: " + this.getRemoteMBeanServer());
   }
}
