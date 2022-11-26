package weblogic.jms.dotnet.t3.server;

import weblogic.rjvm.RJVMManager;

public final class CSharpServicesImpl {
   public static void initialize() {
      RJVMManager.getLocalRJVM().getFinder().put(41, JMSCSharp.getInstance());
   }
}
