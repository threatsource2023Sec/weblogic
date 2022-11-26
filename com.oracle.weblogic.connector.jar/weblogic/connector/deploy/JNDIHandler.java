package weblogic.connector.deploy;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import javax.resource.Referenceable;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.ResourceAdapter;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.exception.RACommonException;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.outbound.RAOutboundManager;
import weblogic.connector.utils.PartitionUtils;
import weblogic.connector.utils.RuntimeAccessUtils;
import weblogic.jndi.Environment;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JNDIHandler implements ObjectFactory {
   private static final String PARTITION = "Partition";
   private static final String RJVM_ADDRESS = "RJVMAddress";
   private static final String SERVER_ADDRESS = "ServerName";
   private static final String KEY_NAME = "keyName";
   private static Hashtable keyNameToOutboundManagerMap = new Hashtable();
   protected static Hashtable keyNameToAdminObj = new Hashtable();
   protected static Hashtable keyNameToRA = new Hashtable();
   public static final String THIS_FACTORY_NAME = JNDIHandler.class.getName();

   public static boolean verifyJNDIName(String jndiName) throws RAException {
      if (jndiName != null && !jndiName.equals("")) {
         return isJndiNameBound(jndiName);
      } else {
         String exMsg = Debug.getExceptionJndiNameNull();
         throw new RAException(exMsg);
      }
   }

   public static void bindConnectionFactory(OutboundInfo outboundInfo, RAOutboundManager outboundManager, Object connFactory) throws DeploymentException {
      Debug.enter("JNDIHandler.jndiBind(...) ");
      if (outboundInfo == null) {
         Debug.throwAssertionError("OutboundInfo is null");
      }

      if (outboundManager == null) {
         Debug.throwAssertionError("RAOutboundManager is null");
      }

      String jndiName = outboundInfo.getJndiName();
      String versionId = outboundManager.getRA().getVersionId();
      UniversalResourceKey key = new UniversalResourceKey(jndiName, versionId);

      try {
         String exMsg;
         try {
            Debug.println("Binding to the JNDI tree : " + jndiName);
            boolean jndiAlreadyNameBound = isJndiNameBound(jndiName);
            exMsg = Debug.getExceptionAlreadyDeployed(jndiName);
            assertDeployment(jndiAlreadyNameBound, exMsg);
            Reference ref = createReference(outboundInfo.getCFImpl(), key);
            exportObject(connFactory, key.getJndi());
            setReference(ref, connFactory);
            Debug.println("Adding to the keyNameToOutboundManagerMap : " + key);
            keyNameToOutboundManagerMap.put(key, outboundManager);
            getInitialContext().bind(new CompositeName(jndiName), ref);
         } catch (InvalidNameException var14) {
            exMsg = Debug.getExceptionBindingFailed(jndiName, var14.toString());
            throw new DeploymentException(exMsg, var14);
         } catch (NamingException var15) {
            exMsg = Debug.getExceptionBindingFailed(jndiName, var15.toString());
            throw new DeploymentException(exMsg, var15);
         } catch (Throwable var16) {
            exMsg = Debug.getExceptionBindingFailed(jndiName, var16.toString());
            throw new DeploymentException(exMsg, var16);
         }
      } finally {
         Debug.exit("JNDIHandler.jndiBind(...) ");
      }

   }

   public static void assertDeployment(boolean error, String message) throws DeploymentException {
      if (error) {
         throw new DeploymentException(message);
      }
   }

   public static void unbindConnectionFactory(OutboundInfo outboundInfo, RAOutboundManager raOutboundManager, Object connFactory) throws UndeploymentException {
      Debug.enter("JNDIHandler.unbindConnectionFactory(...) ");
      if (outboundInfo == null) {
         Debug.throwAssertionError("OutboundInfo is null");
      }

      try {
         String jndiName = outboundInfo.getJndiName();
         UniversalResourceKey key = new UniversalResourceKey(jndiName, raOutboundManager.getRA().getVersionId());
         if (jndiName != null && jndiName.length() != 0) {
            Context ctx = getInitialContext();
            Debug.println("Unbind JNDI name : " + jndiName);
            ctx.unbind(new CompositeName(jndiName));
            Debug.println("Remove from keyNameToOutboundManagerMap : " + key);
            keyNameToOutboundManagerMap.remove(key);
            unexportObject(connFactory, jndiName);
         }
      } catch (NamingException var9) {
         String exMsg = Debug.getExceptionNoInitialContextForUnbind(var9.toString());
         throw new UndeploymentException(exMsg, var9);
      } finally {
         Debug.exit("JNDIHandler.unbindConnectionFactory(...) ");
      }

   }

   public static void bindRA(String jndiName, ResourceAdapter ra, String versionId) throws RACommonException {
      if (ra == null) {
         Debug.throwAssertionError("ResourceAdapter is null");
      }

      if (jndiName != null && !jndiName.equals("")) {
         try {
            UniversalResourceKey key = new UniversalResourceKey(jndiName, versionId);
            boolean jndiAlreadyNameBound = isJndiNameBound(jndiName);
            String exMsg = Debug.getExceptionJndiNameAlreadyBound(jndiName);
            assertDeployment(jndiAlreadyNameBound, exMsg);
            keyNameToRA.put(key, ra);
            Reference ref = null;
            if (ra instanceof Remote) {
               ref = doCreateReference(ra.getClass().toString(), key, TransportableJNDIHandler.class.getName());
               Remote exportedRA;
               if (ServerHelper.isClusterable((Remote)ra)) {
                  exportedRA = ServerHelper.exportObject((Remote)ra, jndiName);
               } else {
                  exportedRA = ServerHelper.exportObject((Remote)ra);
               }

               if (Debug.isDeploymentEnabled()) {
                  Debug.deployment("Bind Remote RA " + exportedRA + " with JNDI name = " + jndiName);
               }
            } else {
               ref = doCreateReference(ra.getClass().toString(), key, JNDIHandler.class.getName());
               if (Debug.isDeploymentEnabled()) {
                  Debug.deployment("Bind non-Remote RA ref" + ref + " with JNDI name = " + jndiName);
               }
            }

            getInitialContext().bind(new CompositeName(jndiName), ref);
         } catch (Exception var8) {
            String exMsg = Debug.getExceptionBindingFailed(jndiName, "");
            throw new RACommonException(exMsg, var8);
         }
      }

   }

   public static void unbindRA(String jndiName, ResourceAdapter ra, String versionId) throws NamingException {
      if (jndiName != null && !jndiName.equals("")) {
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Unbind RA JNDI name '" + jndiName + "'");
         }

         getInitialContext().unbind(new CompositeName(jndiName));
         UniversalResourceKey key = new UniversalResourceKey(jndiName, versionId);
         keyNameToRA.remove(key);

         try {
            ServerHelper.unexportObject(ra, true, true);
         } catch (NoSuchObjectException var5) {
         }
      }

   }

   public static void removeAdminObj(UniversalResourceKey key) {
      if (keyNameToAdminObj.remove(key) == null) {
         Debug.throwAssertionError("AdminObj does not exist with key: " + key.toString());
      }

   }

   public static void storeAdminObj(Object adminObj, UniversalResourceKey key) {
      keyNameToAdminObj.put(key, adminObj);
   }

   public static void removeRAOutboundManager(UniversalResourceKey key) {
      if (keyNameToOutboundManagerMap.remove(key) == null) {
         Debug.throwAssertionError("RAOutboundManager does not exist with key: " + key.toString());
      }

   }

   public static void storeRAOutboundManager(UniversalResourceKey key, RAOutboundManager outboundManager) {
      keyNameToOutboundManagerMap.put(key, outboundManager);
   }

   public static void unexportObject(Object obj, String jndiName) {
      if (obj instanceof Remote) {
         try {
            ServerHelper.unexportObject(obj, true, true);
         } catch (NoSuchObjectException var3) {
            ConnectorLogger.logNoSuchObjectException(obj.getClass().getName(), jndiName);
         }
      }

   }

   public static Reference createReference(String className, UniversalResourceKey key) {
      return doCreateReference(className, key, JNDIHandler.class.getName());
   }

   private static Reference doCreateReference(String className, UniversalResourceKey key, String jndiHandle) {
      Reference reference = new Reference(className, new StringRefAddr("keyName", key.toString()), jndiHandle, (String)null);
      reference.add(new StringRefAddr("RJVMAddress", RuntimeAccessUtils.getRJVMID()));
      reference.add(new StringRefAddr("ServerName", RuntimeAccessUtils.getDomainAndServerName()));
      reference.add(new StringRefAddr("Partition", PartitionUtils.getPartitionName()));
      return reference;
   }

   public static void exportObject(Object obj, String jndiName) throws RemoteException {
      if (obj instanceof Remote) {
         if (ServerHelper.isClusterable((Remote)obj)) {
            ServerHelper.exportObject((Remote)obj, jndiName);
         } else {
            ServerHelper.exportObject((Remote)obj);
         }
      }

   }

   public static void setReference(Reference ref, Object obj) {
      if (obj instanceof Referenceable) {
         ((Referenceable)obj).setReference(ref);
      }

   }

   public static void bindAdminObj(Object adminObj, String jndiName, String versionId, RAInstanceManager raIM) throws RACommonException {
      if (jndiName == null || jndiName.trim().equals("")) {
         Debug.throwAssertionError("JNDI name null or blank");
      }

      if (adminObj == null) {
         Debug.throwAssertionError("Administered object is null");
      }

      String exMsg;
      try {
         boolean jndiAlreadyNameBound = isJndiNameBound(jndiName);
         exMsg = Debug.getExceptionJndiNameAlreadyBound(jndiName);
         assertDeployment(jndiAlreadyNameBound, exMsg);
         UniversalResourceKey key = new UniversalResourceKey(jndiName, versionId);
         keyNameToAdminObj.put(key, adminObj);
         Reference ref = createReference(adminObj.getClass().toString(), key);
         exportObject(adminObj, key.getJndi());
         setReference(ref, adminObj);
         getInitialContext().bind(new CompositeName(jndiName), ref);
         if (Debug.isDeploymentEnabled()) {
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            Debug.deployment("Binding admin object '" + raIM.getAdapterLayer().toString(adminObj, kernelId) + "' to JNDI name '" + jndiName + "'.");
         }

      } catch (Exception var9) {
         exMsg = Debug.getExceptionBindingFailed(jndiName, "");
         throw new RACommonException(exMsg, var9);
      }
   }

   public static void unbindAdminObj(AdminObjInfo adminObjInfo, RAInstanceManager raIM) throws UndeploymentException {
      String jndiName = adminObjInfo.getJndiName();

      try {
         UniversalResourceKey key = new UniversalResourceKey(jndiName, raIM.getVersionId());
         if (jndiName != null && !jndiName.equals("")) {
            Debug.deployment("Unbind Admin Object, jndi-name : " + jndiName);
            getInitialContext().unbind(new CompositeName(jndiName));
            Debug.println("Remove from keyNameToAdminObj : " + key);
            keyNameToAdminObj.remove(key);
         }

      } catch (NamingException var5) {
         String exMsg = Debug.getExceptionUnbindAdminObjectFailed(var5.toString());
         throw new UndeploymentException(exMsg, var5);
      }
   }

   public Object getObjectInstance(Object refObj, Name name, Context ctx, Hashtable env) throws Exception {
      Reference ref = null;
      String classFactory = null;
      if (!(refObj instanceof Reference)) {
         return null;
      } else {
         ref = (Reference)refObj;
         classFactory = ref.getFactoryClassName();
         if (!classFactory.equals(THIS_FACTORY_NAME)) {
            return null;
         } else {
            RefAddr serverRefAddr = ref.get("ServerName");
            RefAddr rjvmRefAddr = ref.get("RJVMAddress");
            RefAddr partitionRefAddr = ref.get("Partition");
            if (serverRefAddr != null && partitionRefAddr != null && rjvmRefAddr != null) {
               if (!KernelStatus.isServer()) {
                  throw new NamingException(ConnectorLogger.getJndiAccessOutsideServer(serverRefAddr.getContent().toString(), "remote client"));
               } else {
                  String currentRJVM = RuntimeAccessUtils.getRJVMID();
                  String resourceRJVM = rjvmRefAddr.getContent().toString();
                  if (!resourceRJVM.equals(currentRJVM)) {
                     throw new NamingException(ConnectorLogger.getJndiAccessOutsideServer(serverRefAddr.getContent().toString(), RuntimeAccessUtils.getDomainAndServerName()));
                  } else {
                     String resourcePartition = partitionRefAddr.getContent().toString();
                     String currentPartition = PartitionUtils.getPartitionName();
                     if (!resourcePartition.equals(currentPartition)) {
                        throw new NamingException(ConnectorLogger.getAccessOutsidePartition(resourcePartition, currentPartition));
                     } else {
                        return this.lookupObject(refObj, name, ctx, env, ref, classFactory);
                     }
                  }
               }
            } else {
               return null;
            }
         }
      }
   }

   protected Object lookupObject(Object refObj, Name name, Context ctx, Hashtable env, Reference ref, String classFactory) throws ResourceException, ApplicationServerInternalException, NoPermissionException, NameNotFoundException {
      Object returnedObj = null;

      Object var12;
      try {
         Debug.enter(this, "getObjectInstance( " + refObj + ", " + name + ", " + ctx + ", " + env + " )");
         String className = ref.getClassName();
         RefAddr keyRef = ref.get("keyName");
         String keyName = null;
         if (keyRef != null) {
            keyName = (String)keyRef.getContent();
         }

         Debug.println((Object)this, (String)(".getObjectInstance() FactoryClassName = " + classFactory + ", ClassName = " + className + ", Ref = " + keyRef + ", Key name = " + keyName));
         UniversalResourceKey key;
         if (keyName == null) {
            key = null;
            return key;
         }

         key = UniversalResourceKey.fromString(keyName);
         returnedObj = this.getConnectionFactory(key);
         if (returnedObj == null) {
            returnedObj = keyNameToAdminObj.get(key);
            if (returnedObj == null) {
               returnedObj = keyNameToRA.get(key);
            }
         }

         if (returnedObj == null) {
            if (key.isDefinedInRA()) {
               throw new NameNotFoundException("No Object found:  " + keyName);
            }

            throw new NameNotFoundException(ConnectorLogger.getExceptionAppDefinedObjNotExist(key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi()));
         }

         var12 = returnedObj;
      } finally {
         Debug.exit(this, "getObjectInstance(...) returning: " + returnedObj);
      }

      return var12;
   }

   private static Context getInitialContext() throws NamingException {
      Environment env = new Environment();
      env.setCreateIntermediateContexts(true);
      env.setReplicateBindings(false);
      return env.getInitialContext();
   }

   public static boolean isJndiNameBound(String jndiName) {
      Debug.enter("JNDIHandler.isJndiNameBound( " + jndiName + " )");
      if (jndiName == null || jndiName.trim().equals("")) {
         Debug.throwAssertionError("JNDI Name is null");
      }

      Context ctx = null;
      Object boundObject = null;

      boolean isBound;
      try {
         ctx = getInitialContext();
         boundObject = ctx.lookup(jndiName);
      } catch (NameNotFoundException var9) {
      } catch (Throwable var10) {
         Debug.throwAssertionError("No Initial Context for Jndi:  " + var10, var10);
      } finally {
         isBound = boundObject != null;
         Debug.exit("JNDIHandler.isJndiNameBound( " + jndiName + " ) returning " + isBound);
      }

      return isBound;
   }

   protected Object getConnectionFactory(UniversalResourceKey key) throws ApplicationServerInternalException, NoPermissionException, ResourceException {
      Debug.enter(this, "getConnectionFactory( keyName = " + key + " ) ");
      Object returnObj = null;

      Object var4;
      try {
         RAOutboundManager outboundManager = (RAOutboundManager)keyNameToOutboundManagerMap.get(key);
         Debug.println((Object)this, (String)(".getConnectionFactory(): Got the outbound manager associated with the Key name : " + key + " : " + outboundManager));
         if (outboundManager != null) {
            Debug.println("Get the connection factory from the outbound manager");
            returnObj = outboundManager.getConnectionFactory(key);
         }

         var4 = returnObj;
      } finally {
         Debug.exit(this, "getConnectionFactory( " + key + " ) returning: " + returnObj);
      }

      return var4;
   }
}
