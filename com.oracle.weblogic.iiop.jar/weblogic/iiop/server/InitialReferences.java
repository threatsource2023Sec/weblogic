package weblogic.iiop.server;

import java.io.IOException;
import java.rmi.Remote;
import java.util.HashMap;
import weblogic.corba.cos.codebase.CodeBaseImpl;
import weblogic.corba.cos.codebase.RunTimeImpl;
import weblogic.corba.cos.naming.NamingContextImpl;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.corba.cos.transactions.RecoveryCoordinatorImpl;
import weblogic.corba.cos.transactions.RecoveryFactory;
import weblogic.corba.cos.transactions.ResourceFactory;
import weblogic.corba.cos.transactions.ResourceImpl;
import weblogic.corba.cos.transactions.TransactionFactoryImpl;
import weblogic.corba.idl.IDLHelper;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.HeartbeatHelperImpl;
import weblogic.rmi.internal.InitialReferenceConstants;

public final class InitialReferences implements InitialReferenceConstants {
   public static final String COS_NAMING_SERVICE = "NameService";
   public static final String COS_NAMING_PATH = "weblogic.cosnaming.NameService";
   public static final String COS_TRANSACTION_FACTORY_SERVICE = "TransactionFactory";
   public static final String COS_CODEBASE_SERVICE = "CodeBase";
   public static final String HEARTBEAT_HELPER_SERVICE = "HeartbeatHelper";
   static final HashMap refMap = new HashMap();
   static final HashMap refObjectMap = new HashMap();
   public static final int MAX_INITIAL_REF_LENGTH = 128;
   private static final IOR NULL_OBJECT;
   private static final String[] services;
   private static final String HEARTBEATHELPER_REPID = "RMI:weblogic.rmi.extensions.server.HeartbeatHelper:0000000000000000";

   public static void initializeServerInitialReferences() throws IOException {
      exportNameServiceRoot();
      ServerHelper.exportObject(ResourceImpl.class, ResourceFactory.getActivator());
      ServerHelper.exportObject(RecoveryCoordinatorImpl.class, RecoveryFactory.getActivator());
      exportTransactionFactory();
      exportHeartbeatHelper();
   }

   private static void exportTransactionFactory() throws IOException {
      TransactionFactoryImpl transactionFactory = TransactionFactoryImpl.getTransactionFactory();
      IDLHelper.exportObject(transactionFactory);
      defineReference("TransactionFactory", (IOR)IIOPReplacer.getReplacer().replaceObject(transactionFactory), transactionFactory);
   }

   private static void exportHeartbeatHelper() {
      defineReference("HeartbeatHelper", ServerIORFactory.createWellKnownIor("RMI:weblogic.rmi.extensions.server.HeartbeatHelper:0000000000000000", 21), HeartbeatHelperImpl.getHeartbeatHelper());
   }

   private static void exportNameServiceRoot() throws IOException {
      NamingContextImpl nameServiceRoot = RootNamingContextImpl.getInitialReference();
      IDLHelper.exportObject(nameServiceRoot, "weblogic.cosnaming.NameService");
      defineReference("NameService", nameServiceRoot.getIOR(), nameServiceRoot);
   }

   private static void defineReference(String serviceName, IOR ior, Object implementation) {
      refMap.put(serviceName, ior);
      refObjectMap.put(serviceName, implementation);
   }

   public static void initializeClientInitialReferences() throws IOException {
      IDLHelper.exportObject(RunTimeImpl.getRunTime());
      IDLHelper.exportObject(CodeBaseImpl.getCodeBase());
      refMap.put("CodeBase", (IOR)IIOPReplacer.getReplacer().replaceObject(CodeBaseImpl.getCodeBase()));
      refObjectMap.put("CodeBase", CodeBaseImpl.getCodeBase());
   }

   public static IOR getInitialReference(String name) {
      IOR ior = (IOR)refMap.get(name);
      if (ior == null && name.startsWith("weblogic.")) {
         try {
            Class c = Class.forName(name);
            Remote o = ServerHelper.getLocalInitialReference(c);
            ior = IIOPReplacer.getIIOPReplacer().replaceRemote(o);
            refMap.put(name, ior);
         } catch (Exception var4) {
            refMap.put(name, NULL_OBJECT);
         }
      } else if (ior == NULL_OBJECT) {
         return null;
      }

      return ior;
   }

   public static String[] getServiceList() {
      return services;
   }

   public static org.omg.CORBA.Object getInitialReferenceObject(String name) {
      return (org.omg.CORBA.Object)refObjectMap.get(name);
   }

   static {
      NULL_OBJECT = IOR.NULL;
      services = new String[]{"NameService", "CodeBase", "TransactionFactory", "HeartbeatHelper"};
   }
}
