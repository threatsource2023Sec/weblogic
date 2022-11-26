package weblogic.corba.cos.codebase;

import org.omg.SendingContext._RunTimeImplBase;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;

public final class RunTimeImpl extends _RunTimeImplBase {
   public static final String TYPE_ID = "IDL:omg.org/SendingContext/RunTime:1.0";
   private static final IOR ior = ServerIORFactory.createWellKnownIor("IDL:omg.org/SendingContext/RunTime:1.0", 11);
   private static final RunTimeImpl runtime = new RunTimeImpl();

   private RunTimeImpl() {
   }

   public static RunTimeImpl getRunTime() {
      return runtime;
   }

   public IOR getIOR() {
      return ior;
   }
}
