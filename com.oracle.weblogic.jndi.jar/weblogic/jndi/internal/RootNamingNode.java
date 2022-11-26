package weblogic.jndi.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.event.NamingListener;
import weblogic.management.provider.ManagementService;
import weblogic.rjvm.PartitionNotFoundException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class RootNamingNode extends ServerNamingNode {
   public static final String STUB_NAME = ServerHelper.getStubClassName(ServerNamingNode.class.getName());
   private static RootNamingNode singleton;
   private static volatile ServerNamingNode sharableRootNode;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final transient ConcurrentHashMap partitionRootNamingNodes = new ConcurrentHashMap();

   public static RootNamingNode getSingleton() {
      if (singleton == null) {
         Class var0 = RootNamingNode.class;
         synchronized(RootNamingNode.class) {
            if (singleton == null) {
               singleton = new RootNamingNode();
            }
         }
      }

      return singleton;
   }

   private RootNamingNode() {
   }

   static void initialize() {
   }

   public Object lookup(String name, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      if (!partitionName.equals("DOMAIN")) {
         NamingNode targetNode = this.getPartitionRootNode(partitionName);
         return name.equalsIgnoreCase("weblogic.partitionName") ? partitionName : targetNode.lookup(name, env);
      } else {
         return super.lookup(name, env);
      }
   }

   public boolean isBindable(String name, Object boundObject, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      return !partitionName.equals("DOMAIN") ? this.getPartitionRootNode(partitionName).isBindable(name, boundObject, env) : super.isBindable(name, boundObject, env);
   }

   public boolean isBindable(String name, boolean isAggregatable, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      return !partitionName.equals("DOMAIN") ? this.getPartitionRootNode(partitionName).isBindable(name, isAggregatable, env) : super.isBindable(name, isAggregatable, env);
   }

   public void bind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      if (targetNamingNode != null) {
         targetNamingNode.bind(name, newObject, env);
      } else {
         super.bind(name, newObject, env);
      }

   }

   public void rebind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      if (targetNamingNode != null) {
         targetNamingNode.rebind(name, newObject, env);
      } else {
         super.rebind(name, newObject, env);
      }

   }

   public void rebind(String name, Object oldObject, Object newObject, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      if (targetNamingNode != null) {
         targetNamingNode.rebind(name, oldObject, newObject, env);
      } else {
         super.rebind(name, oldObject, newObject, env);
      }

   }

   public void unbind(String name, Object object, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      if (targetNamingNode != null) {
         targetNamingNode.unbind(name, object, env);
      } else {
         super.unbind(name, object, env);
      }

   }

   public Context createSubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      return targetNamingNode != null ? targetNamingNode.createSubcontext(name, env) : super.createSubcontext(name, env);
   }

   public void destroySubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      NamingNode targetNamingNode = PartitionHandler.findTargetRootNode(env);
      if (targetNamingNode != null) {
         targetNamingNode.destroySubcontext(name, env);
      } else {
         super.destroySubcontext(name, env);
      }

   }

   public Object lookupLink(String name, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      if (!partitionName.equals("DOMAIN")) {
         NamingNode targetNode = this.getPartitionRootNode(partitionName);
         return name.equalsIgnoreCase("weblogic.partitionName") ? partitionName : targetNode.lookupLink(name, env);
      } else {
         return super.lookupLink(name, env);
      }
   }

   public NamingEnumeration list(String name, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      return !partitionName.equals("DOMAIN") ? this.getPartitionRootNode(partitionName).list(name, env) : super.list(name, env);
   }

   public NamingEnumeration listBindings(String name, Hashtable env) throws NamingException, RemoteException {
      String partitionName = PartitionHandler.getPartitionName(env);
      return !partitionName.equals("DOMAIN") ? this.getPartitionRootNode(partitionName).listBindings(name, env) : super.listBindings(name, env);
   }

   public void addNamingListener(String name, int scope, NamingListener l, Hashtable env) throws NamingException {
      String partitionName = PartitionHandler.getPartitionName(env);
      if (!partitionName.equals("DOMAIN")) {
         this.getPartitionRootNode(partitionName).addNamingListener(name, scope, l, env);
      } else {
         super.addNamingListener(name, scope, l, env);
      }

   }

   public void removeNamingListener(NamingListener l, Hashtable env) throws NamingException {
      String partitionName = PartitionHandler.getPartitionName(env);
      if (!partitionName.equals("DOMAIN")) {
         this.getPartitionRootNode(partitionName).removeNamingListener(l, env);
      } else {
         super.removeNamingListener(l, env);
      }

   }

   protected ServerNamingNode getPartitionRootNode(String partitionName) throws NamingException {
      ServerNamingNode node = (ServerNamingNode)partitionRootNamingNodes.get(partitionName);
      if (node == null) {
         synchronized(partitionRootNamingNodes) {
            node = (ServerNamingNode)partitionRootNamingNodes.get(partitionName);
            if (node == null) {
               if (!ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
                  PartitionNotFoundException pnfe = new PartitionNotFoundException("Partition " + partitionName + " does not exist, or is not running.");
                  NamingException nex = new NamingException(pnfe.getMessage());
                  nex.setRootCause(pnfe);
                  throw nex;
               }

               PartitionHandler.checkPartition(partitionName);
               node = this.getSharableRootNode();
            }
         }
      }

      return node;
   }

   protected void addPartitionRootNode(String partitionName) {
      if (partitionName != null) {
         partitionRootNamingNodes.put(partitionName, new ServerNamingNode(true, partitionName));
      }
   }

   protected ServerNamingNode removePartitionRootNode(String partitionName) {
      return partitionName == null ? null : (ServerNamingNode)partitionRootNamingNodes.remove(partitionName);
   }

   protected ServerNamingNode getSharableRootNode() {
      if (sharableRootNode == null) {
         Class var1 = RootNamingNode.class;
         synchronized(RootNamingNode.class) {
            if (sharableRootNode == null) {
               sharableRootNode = new ServerNamingNode(true);
            }
         }
      }

      return sharableRootNode;
   }
}
