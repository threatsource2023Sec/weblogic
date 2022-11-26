package weblogic.jndi.internal;

import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionHandler {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private PartitionHandler() {
   }

   protected static RootNamingNode getRootNode() {
      return RootNamingNode.getSingleton();
   }

   static Object lookupSharable(String name, Hashtable env) throws NamingException {
      try {
         return getSharableRootNode().lookupSharable(name, env);
      } catch (RemoteException var3) {
         throw new NamingException(var3.getMessage());
      }
   }

   static Object lookupLinkSharable(String name, Hashtable env) throws NamingException {
      try {
         return getSharableRootNode().lookupLinkSharable(name, env);
      } catch (RemoteException var3) {
         throw new NamingException(var3.getMessage());
      }
   }

   static NamingEnumeration listSharableBindings(String name, Hashtable env) throws RemoteException, NamingException {
      return getSharableRootNode().listBindingsSharable(name, env);
   }

   static NamingEnumeration listSharable(String name, Hashtable env) throws RemoteException, NamingException {
      return getSharableRootNode().listSharable(name, env);
   }

   public static String getPartitionName(Hashtable env) {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      String partitionName = null;
      if (env != null && env.containsKey("weblogic.jndi.partitionInformation")) {
         partitionName = (String)env.get("weblogic.jndi.partitionInformation");
      } else {
         partitionName = cic.getPartitionName();
      }

      return partitionName;
   }

   public static String getPartitionName(Context ctx) throws NamingException {
      String partitionName = null;
      String providerURL = (String)ctx.getEnvironment().get("java.naming.provider.url");
      if (providerURL != null) {
         try {
            partitionName = PartitionTable.getInstance().lookup(providerURL).getPartitionName();
         } catch (URISyntaxException var4) {
         }
      }

      if (null == partitionName) {
         partitionName = (String)ctx.getEnvironment().get("weblogic.jndi.partitionInformation");
         if (null == partitionName) {
            partitionName = "DOMAIN";
         }
      }

      return partitionName;
   }

   static void addPartitionRootNode(String partitionName) {
      getRootNode().addPartitionRootNode(partitionName);
   }

   static ServerNamingNode getPartitionRootNode(String partitionName) throws NamingException {
      return getRootNode().getPartitionRootNode(partitionName);
   }

   static ServerNamingNode removePartitionRootNode(String partitionName) {
      return getRootNode().removePartitionRootNode(partitionName);
   }

   static ServerNamingNode getSharableRootNode() {
      return getRootNode().getSharableRootNode();
   }

   static NamingNode findTargetRootNode(Hashtable env) throws NamingException {
      if (env != null && env.containsKey("weblogic.jndi.createUnderSharable") && Boolean.parseBoolean((String)env.get("weblogic.jndi.createUnderSharable"))) {
         return getRootNode().getSharableRootNode();
      } else {
         String partitionName = getPartitionName(env);
         if (!partitionName.equals("DOMAIN")) {
            NamingNode targetNode = getRootNode().getPartitionRootNode(partitionName);
            if (targetNode.equals(getRootNode().getSharableRootNode())) {
               throw new NamingException("Partition " + partitionName + " does not exist, or is not running. Or it does not support to modification JNDI operation.");
            } else {
               return targetNode;
            }
         } else {
            return null;
         }
      }
   }

   public static void checkPartition(String partitionName) throws NamingException {
      PartitionMBean partitionMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      if (partitionMBean == null) {
         throw new NamingException("Partition " + partitionName + " does not exist, or is not running.");
      }
   }

   static ManagedInvocationContext setCIC(String partitionName, Hashtable env) {
      if (partitionName == null) {
         partitionName = getPartitionName(env);
      }

      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      String currentPartitionName = cicm.getCurrentComponentInvocationContext().getPartitionName();
      if (partitionName.equals(currentPartitionName)) {
         return null;
      } else {
         ComponentInvocationContext cic = cicm.createComponentInvocationContext(partitionName);
         return cicm.setCurrentComponentInvocationContext(cic);
      }
   }
}
