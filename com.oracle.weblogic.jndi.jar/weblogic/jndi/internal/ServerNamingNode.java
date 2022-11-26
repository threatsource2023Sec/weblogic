package weblogic.jndi.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import weblogic.cluster.ServiceAdvertiser;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.Alias;
import weblogic.jndi.CrossPartitionAware;
import weblogic.jndi.JNDILogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.JNDIResource;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StringUtils;

public class ServerNamingNode extends BasicNamingNode {
   private static final String DEFAULT_SEPARATORS_STRING = "./";
   private static final char[] DEFAULT_SEPARATORS_ARRAY = "./".toCharArray();
   private static boolean areStaticsInitialized = false;
   private static boolean isClustered = false;
   private static boolean isRemoteAnonymousEnabled = false;
   private static AuthorizationManager am = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private final AdminModeHandler adminHandler;
   private final VersionHandler versionHandler;
   private String partitionName;
   private boolean sharableAware;
   private final char[] separators;
   private static final boolean enableVisibilityControl = System.getProperty("weblogic.jndi.mt.enableVisibilityControl") == null ? true : Boolean.parseBoolean(System.getProperty("weblogic.jndi.mt.enableVisibilityControl"));
   private static final Set PrimitiveWrapperSets = getPrimitiveWrapperSets();

   private static void initializeStatics() {
      if (!areStaticsInitialized) {
         Class var0 = ServerNamingNode.class;
         synchronized(ServerNamingNode.class) {
            if (!areStaticsInitialized) {
               if (ManagementService.getRuntimeAccess(kernelId).getServer().getCluster() != null) {
                  isClustered = true;
               }

               isRemoteAnonymousEnabled = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().isRemoteAnonymousJNDIEnabled();
               String realmName = "weblogicDEFAULT";
               am = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHORIZE);
               if (am == null) {
                  throw new RuntimeException("Security Services Unavailable");
               }

               areStaticsInitialized = true;
            }
         }
      }

   }

   ServerNamingNode() {
      this(DEFAULT_SEPARATORS_ARRAY, (ServerNamingNode)null, "", new CopyOnWriteArrayList(), (String)null, false);
   }

   ServerNamingNode(boolean partitionAware, String partitionName) {
      this(DEFAULT_SEPARATORS_ARRAY, (ServerNamingNode)null, "", new CopyOnWriteArrayList(), partitionName, false);
   }

   public ServerNamingNode(String separators) {
      this(separators.toCharArray(), (ServerNamingNode)null, "", new CopyOnWriteArrayList(), (String)null, false);
   }

   ServerNamingNode(boolean sharableAware) {
      this(DEFAULT_SEPARATORS_ARRAY, (ServerNamingNode)null, "", new CopyOnWriteArrayList(), (String)null, sharableAware);
   }

   ServerNamingNode(String separators, boolean sharableAware) {
      this(separators.toCharArray(), (ServerNamingNode)null, "", new CopyOnWriteArrayList(), (String)null, sharableAware);
   }

   private ServerNamingNode(char[] separators, ServerNamingNode parent, String relativeName, CopyOnWriteArrayList subtreeScopeNameListenerList, String partitionName, boolean sharableAware) {
      super(separators, parent, relativeName, subtreeScopeNameListenerList);
      this.adminHandler = new AdminModeHandler(this);
      this.versionHandler = new VersionHandler(this);
      initializeStatics();
      this.separators = separators;
      this.partitionName = partitionName;
      this.sharableAware = sharableAware;
   }

   protected BasicNamingNode newSubnode(String relativeName) {
      return new ServerNamingNode(this.separators, this, relativeName, this.subtreeScopeNameListenerList, this.partitionName, this.sharableAware);
   }

   public Context getContext(Hashtable env) {
      try {
         String fullName = this.getNameInNamespace();
         ServerHelper.exportObject(this, fullName);
         return new WLEventContextImpl(env, this);
      } catch (RemoteException var3) {
         throw new AssertionError("Failed to create stub for " + this.getClass().getName(), var3);
      }
   }

   protected Context getContextForException(Hashtable env) {
      return super.getContext(env);
   }

   protected void bindHere(String name, Object object, Hashtable env, boolean enableVersion, Object original) throws NoPermissionException, NamingException {
      String fullNameAsString = this.getNameInNamespace(name);
      this.checkModify(fullNameAsString, env);
      Name fullName = this.nameParser.parse(fullNameAsString);
      Object objectToBind = WLNamingManager.getStateToBind(object, fullName, (Context)null, env);
      boolean isBindVersioned = this.versionHandler.isBindVersioned();
      if (enableVersion && isBindVersioned) {
         this.versionHandler.bindHere(name, objectToBind, env);
         if (this.replicateBindings(env)) {
            this.advertiseBinding(fullNameAsString, objectToBind);
         }
      } else {
         this.adminHandler.checkBind(name, isBindVersioned);
         super.bindHere(name, objectToBind, env, false, object);
         if (enableVersion && this.replicateBindings(env)) {
            this.advertiseBinding(fullNameAsString, objectToBind);
         }
      }

   }

   private void advertiseBinding(String fullNameAsString, Object objectToBind) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ advertise bind(" + fullNameAsString + ", " + objectToBind.getClass().getName() + ")");
      }

      if (!(objectToBind instanceof Serializable) && !(objectToBind instanceof Remote)) {
         JNDILogger.logCannotReplicateObjectInCluster(fullNameAsString);
      } else {
         if (this.sharableAware) {
            fullNameAsString = "sharable:" + fullNameAsString;
         } else if (this.partitionName != null) {
            fullNameAsString = "partition:" + this.partitionName + "/" + fullNameAsString;
         } else {
            fullNameAsString = "domain:" + fullNameAsString;
         }

         ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
         this.getServiceAdvertiser().offerService(fullNameAsString, avus.getBindApplicationId(), objectToBind);
      }

   }

   protected Collection listThis(Hashtable env) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace();
      this.checkList(fullName, env);
      return this.isVersioned() ? this.versionHandler.getAccessibleBindings(super.listThis(env)) : this.adminHandler.getAccessibleBindings(super.listThis(env));
   }

   protected Object lookupHere(String name, Hashtable env, String restOfName) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace(name);
      this.checkLookup(fullName, env);
      this.adminHandler.checkLookup(name, restOfName, env);
      Object object = super.lookupHere(name, env, restOfName);
      if (restOfName.length() > 0) {
         return object;
      } else {
         if (object instanceof ServerNamingNode) {
            object = this.versionHandler.getCurrentVersion((ServerNamingNode)object, env);
            if (!(object instanceof ServerNamingNode)) {
               object = this.getPartitionVisibleObject(name, object, env);
            }
         } else if (!this.isVersioned()) {
            this.versionHandler.checkGlobalResource(object, env);
         }

         return object;
      }
   }

   Object superLookupHere(String name, Hashtable env, String restOfName) throws NoPermissionException, NamingException {
      return super.lookupHere(name, env, restOfName);
   }

   protected void rebindHere(String name, Object object, Hashtable env, boolean enableVersion, Object original) throws NoPermissionException, NamingException {
      String fullNameAsString = this.getNameInNamespace(name);
      this.checkModify(fullNameAsString, env);
      Name fullName = this.nameParser.parse(fullNameAsString);
      Object objectToBind = WLNamingManager.getStateToBind(object, fullName, (Context)null, env);
      boolean isBindVersioned = this.versionHandler.isBindVersioned();
      if (enableVersion && isBindVersioned) {
         this.versionHandler.rebindHere(name, objectToBind, env);
         if (this.replicateBindings(env)) {
            this.advertiseRebinding(fullNameAsString, (Object)null, objectToBind);
         }
      } else {
         this.adminHandler.checkBind(name, isBindVersioned);
         super.rebindHere(name, objectToBind, env, false, object);
         if (enableVersion && this.replicateBindings(env)) {
            this.advertiseRebinding(fullNameAsString, (Object)null, objectToBind);
         }
      }

   }

   protected void rebindHere(String name, Object oldObject, Object newObject, Hashtable env, boolean enableVersion) throws NoPermissionException, NamingException {
      String fullNameAsString = this.getNameInNamespace(name);
      this.checkModify(fullNameAsString, env);
      Name fullName = this.nameParser.parse(fullNameAsString);
      Object objectToBind = WLNamingManager.getStateToBind(newObject, fullName, (Context)null, env);
      boolean isBindVersioned = this.versionHandler.isBindVersioned();
      Object objectToReplace;
      if (enableVersion && isBindVersioned) {
         this.versionHandler.rebindHere(name, objectToBind, env);
         if (this.replicateBindings(env)) {
            objectToReplace = WLNamingManager.getStateToBind(oldObject, fullName, (Context)null, env);
            this.advertiseRebinding(fullNameAsString, objectToReplace, objectToBind);
         }
      } else {
         this.adminHandler.checkBind(name, isBindVersioned);
         super.rebindHere(name, newObject, objectToBind, env, false);
         if (enableVersion && this.replicateBindings(env)) {
            objectToReplace = WLNamingManager.getStateToBind(oldObject, fullName, (Context)null, env);
            this.advertiseRebinding(fullNameAsString, objectToReplace, objectToBind);
         }
      }

   }

   private void advertiseRebinding(String fullNameAsString, Object objectToReplace, Object objectToBind) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ advertise rebind(" + fullNameAsString + ", " + objectToBind.getClass().getName() + ")");
      }

      if (this.sharableAware) {
         fullNameAsString = "sharable:" + fullNameAsString;
      } else if (this.partitionName != null) {
         fullNameAsString = "partition:" + this.partitionName + "/" + fullNameAsString;
      } else {
         fullNameAsString = "domain:" + fullNameAsString;
      }

      ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
      this.getServiceAdvertiser().replaceService(fullNameAsString, avus.getBindApplicationId(), objectToReplace, objectToBind);
   }

   protected NamingNode createSubnodeHere(String name, Hashtable env) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace(name);
      this.checkModify(fullName, env);
      NamingNode node = super.createSubnodeHere(name, env);
      if (this.replicateBindings(env)) {
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ advertise createSubContext(" + fullName + ")");
         }

         this.getServiceAdvertiser().createSubcontext(fullName);
      }

      return node;
   }

   protected void destroySubnodeHere(String name, Hashtable env) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace(name);
      this.checkModify(fullName, env);
      super.destroySubnodeHere(name, env);
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ destroySubContext(" + fullName + ")");
      }

      if (this.replicateBindings(env)) {
         if (this.sharableAware) {
            fullName = "sharable:" + fullName;
         } else if (this.partitionName != null) {
            fullName = "partition:" + this.partitionName + "/" + fullName;
         } else {
            fullName = "domain:" + fullName;
         }

         this.getServiceAdvertiser().retractService(fullName, (String)null, (Object)null);
      }

   }

   public Object unbindHere(String name, Object object, Hashtable env, boolean enableVersion) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace(name);
      this.checkModify(fullName, env);
      boolean isBindVersioned = this.versionHandler.isBindVersioned();
      if (enableVersion && isBindVersioned) {
         this.versionHandler.unbindHere(name, object, env);
         if (this.replicateBindings(env)) {
            this.advertiseUnbinding(fullName, object);
         }

         return null;
      } else {
         this.adminHandler.checkUnbind(name, isBindVersioned);
         Object bound = super.unbindHere(name, object, env, false);
         if (enableVersion && this.replicateBindings(env)) {
            this.advertiseUnbinding(fullName, object);
         }

         this.versionHandler.checkUnbind(name, env, object, bound);
         return bound;
      }
   }

   private void advertiseUnbinding(String fullName, Object object) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         if (object == null) {
            NamingDebugLogger.debug("+++ advertise unbind(" + fullName + ")");
         } else {
            NamingDebugLogger.debug("+++ advertise unbind(" + fullName + ", " + object.getClass().getName() + ")");
         }
      }

      if (this.sharableAware) {
         fullName = "sharable:" + fullName;
      } else if (this.partitionName != null) {
         fullName = "partition:" + this.partitionName + "/" + fullName;
      } else {
         fullName = "domain:" + fullName;
      }

      ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
      this.getServiceAdvertiser().retractService(fullName, avus.getBindApplicationId(), object);
   }

   public void rename(String oldName, String newName, Hashtable env) throws OperationNotSupportedException, NamingException, RemoteException {
      if (this.replicateBindings(env)) {
         throw new OperationNotSupportedException("replicated rename not supported");
      } else {
         super.rename(oldName, newName, env);
      }
   }

   final boolean replicateBindings(Hashtable env) {
      return isClustered && (env == null || !"false".equals(this.getProperty(env, "weblogic.jndi.replicateBindings")));
   }

   private void checkList(String ctxName, Hashtable env) throws NoPermissionException {
      this.checkPermission(ctxName, "list", env);
   }

   private void checkLookup(String objName, Hashtable env) throws NoPermissionException {
      this.checkPermission(objName, "lookup", env);
   }

   private void checkModify(String objName, Hashtable env) throws NoPermissionException {
      this.checkPermission(objName, "modify", env);
   }

   private void checkPermission(String objectName, String action, Hashtable env) throws NoPermissionException {
      ManagedInvocationContext ignoredMic = PartitionHandler.setCIC(this.partitionName, env);
      Throwable var5 = null;

      try {
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         String[] myPath = StringUtils.splitCompletely(objectName, "./");
         this.checkIfRemoteUserHasPermission(subject, action, env);
         Resource resource = new JNDIResource((String)null, myPath, action);
         String pname = this.partitionName;
         if (pname == null) {
            pname = PartitionHandler.getPartitionName(env);
         }

         ResourceIDDContextWrapper resWrapper = new ResourceIDDContextWrapper((ContextHandler)null, false);
         resWrapper.setResourcePartition(pname);
         if (!am.isAccessAllowed(subject, resource, resWrapper)) {
            throw new NoPermissionException("User " + SubjectUtils.getUsername(subject) + " does not have permission on " + objectName + " to perform " + action + " operation.");
         }
      } catch (Throwable var18) {
         var5 = var18;
         throw var18;
      } finally {
         if (ignoredMic != null) {
            if (var5 != null) {
               try {
                  ignoredMic.close();
               } catch (Throwable var17) {
                  var5.addSuppressed(var17);
               }
            } else {
               ignoredMic.close();
            }
         }

      }

   }

   protected boolean isVersioned() {
      return this.versionHandler.isVersioned();
   }

   final VersionHandler getVersionHandler() {
      return this.versionHandler;
   }

   private String getFullJNDIName(String relativeName) {
      if (relativeName != null) {
         if (this.getNameInNamespace().isEmpty()) {
            return relativeName;
         } else {
            return this.separators.length == 0 ? this.getNameInNamespace() + DEFAULT_SEPARATORS_ARRAY[0] + relativeName : this.getNameInNamespace() + this.separators[0] + relativeName;
         }
      } else {
         return null;
      }
   }

   public Object lookup(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "lookup");
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "SERVER_NAMING_NODE", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var6 = null;

         Object var7;
         try {
            var7 = super.lookup(name, env);
         } catch (Throwable var19) {
            var6 = var19;
            throw var19;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var18) {
                     var6.addSuppressed(var18);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var7;
      } catch (NameNotFoundException var21) {
         NamingDebugLogger.debugIfEnable(var21.getMessage());

         try {
            return PartitionHandler.lookupSharable(this.getFullJNDIName(name), env);
         } catch (NameNotFoundException var17) {
            NamingDebugLogger.debugIfEnable(var17.getMessage());
            throw var21;
         }
      }
   }

   public void bind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "bind");
      if (this.sharableAware && !Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createUnderSharable"))) {
         NamingNode targetNode = PartitionHandler.findTargetRootNode(env);
         boolean isIntermediateEnable = false;
         if (!Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createIntermediateContexts"))) {
            env.put("weblogic.jndi.createIntermediateContexts", "true");
            isIntermediateEnable = true;
         }

         try {
            if (targetNode == null) {
               PartitionHandler.getRootNode().bind(this.getFullJNDIName(name), newObject, env);
            } else {
               targetNode.bind(this.getFullJNDIName(name), newObject, env);
            }
         } finally {
            if (isIntermediateEnable) {
               env.remove("weblogic.jndi.createIntermediateContexts");
            }

         }
      } else {
         super.bind(name, newObject, env);
      }

   }

   public void rebind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "rebind");
      if (this.sharableAware && !Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createUnderSharable"))) {
         NamingNode targetNode = PartitionHandler.findTargetRootNode(env);
         boolean isIntermediateEnable = false;
         if (!Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createIntermediateContexts"))) {
            env.put("weblogic.jndi.createIntermediateContexts", "true");
            isIntermediateEnable = true;
         }

         try {
            if (targetNode == null) {
               PartitionHandler.getRootNode().rebind(this.getFullJNDIName(name), newObject, env);
            } else {
               targetNode.rebind(this.getFullJNDIName(name), newObject, env);
            }
         } finally {
            if (isIntermediateEnable) {
               env.remove("weblogic.jndi.createIntermediateContexts");
            }

         }
      } else {
         super.rebind(name, newObject, env);
      }

   }

   public void rebind(String name, Object oldObject, Object newObject, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "rebind");
      if (this.sharableAware && !Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createUnderSharable"))) {
         NamingNode targetNode = PartitionHandler.findTargetRootNode(env);
         boolean isIntermediateEnable = false;
         if (!Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createIntermediateContexts"))) {
            env.put("weblogic.jndi.createIntermediateContexts", "true");
            isIntermediateEnable = true;
         }

         try {
            if (targetNode == null) {
               PartitionHandler.getRootNode().rebind(this.getFullJNDIName(name), oldObject, newObject, env);
            } else {
               targetNode.rebind(this.getFullJNDIName(name), oldObject, newObject, env);
            }
         } finally {
            if (isIntermediateEnable) {
               env.remove("weblogic.jndi.createIntermediateContexts");
            }

         }
      } else {
         super.rebind(name, oldObject, newObject, env);
      }

   }

   public void unbind(String name, Object object, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "unbind");
      super.unbind(name, object, env);
   }

   public Context createSubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "createSubcontext");
      if (this.sharableAware && !Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createUnderSharable"))) {
         NamingNode targetNode = PartitionHandler.findTargetRootNode(env);
         boolean isIntermediateEnable = false;
         if (!Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createIntermediateContexts"))) {
            env.put("weblogic.jndi.createIntermediateContexts", "true");
            isIntermediateEnable = true;
         }

         Context var5;
         try {
            if (targetNode == null) {
               var5 = PartitionHandler.getRootNode().createSubcontext(this.getFullJNDIName(name), env);
               return var5;
            }

            var5 = targetNode.createSubcontext(this.getFullJNDIName(name), env);
         } finally {
            if (isIntermediateEnable) {
               env.remove("weblogic.jndi.createIntermediateContexts");
            }

         }

         return var5;
      } else {
         return super.createSubcontext(name, env);
      }
   }

   public void destroySubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "destroySubcontext");
      super.destroySubcontext(name, env);
   }

   public Object lookupLink(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "lookupLink");

      try {
         return super.lookupLink(name, env);
      } catch (NameNotFoundException var6) {
         NamingDebugLogger.debugIfEnable(var6.getMessage());

         try {
            return PartitionHandler.lookupLinkSharable(this.getFullJNDIName(name), env);
         } catch (NameNotFoundException var5) {
            NamingDebugLogger.debugIfEnable(var5.getMessage());
            throw var6;
         }
      }
   }

   public NamingEnumeration list(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "list");
      NamingException cne = null;
      NamingException sne = null;
      NameClassPairEnumeration ncpe = null;
      NameClassPairEnumeration sncpe = null;

      try {
         ncpe = (NameClassPairEnumeration)super.list(name, env);
      } catch (NamingException var9) {
         cne = var9;
      }

      try {
         sncpe = (NameClassPairEnumeration)PartitionHandler.listSharable(this.getFullJNDIName(name), env);
      } catch (NamingException var8) {
         sne = var8;
      }

      if (sne != null && cne != null) {
         if (cne instanceof NameNotFoundException) {
            throw sne;
         } else {
            throw cne;
         }
      } else if (ncpe == null) {
         return sncpe;
      } else {
         return sncpe == null ? ncpe : new NameClassPairEnumeration(this.merge(ncpe, sncpe));
      }
   }

   public NamingEnumeration listBindings(String name, Hashtable env) throws NamingException, RemoteException {
      this.debug(name, env, "listBindings");
      NamingException cne = null;
      NamingException sne = null;
      BindingEnumeration be = null;
      BindingEnumeration sbe = null;

      try {
         be = (BindingEnumeration)super.listBindings(name, env);
      } catch (NamingException var9) {
         cne = var9;
      }

      try {
         sbe = (BindingEnumeration)PartitionHandler.listSharableBindings(this.getFullJNDIName(name), env);
      } catch (NamingException var8) {
         sne = var8;
      }

      if (sne != null && cne != null) {
         if (cne instanceof NameNotFoundException) {
            throw sne;
         } else {
            throw cne;
         }
      } else if (be == null) {
         return sbe;
      } else {
         return sbe == null ? be : new BindingEnumeration(this.merge(be, sbe));
      }
   }

   private NameClassPair[] merge(NameClassPairEnumeration pairs, NameClassPairEnumeration sharablePairs) {
      HashMap mergedPairs = new HashMap();
      NameClassPair[] var4 = sharablePairs.list;
      int var5 = var4.length;

      int var6;
      NameClassPair pair;
      for(var6 = 0; var6 < var5; ++var6) {
         pair = var4[var6];
         mergedPairs.put(pair.getName(), pair);
      }

      var4 = pairs.list;
      var5 = var4.length;

      for(var6 = 0; var6 < var5; ++var6) {
         pair = var4[var6];
         mergedPairs.put(pair.getName(), pair);
      }

      return (NameClassPair[])((NameClassPair[])mergedPairs.values().toArray(new NameClassPair[0]));
   }

   private Binding[] merge(BindingEnumeration pairs, BindingEnumeration sharablePairs) {
      HashMap mergedPairs = new HashMap();
      NameClassPair[] var4 = sharablePairs.list;
      int var5 = var4.length;

      int var6;
      NameClassPair pair;
      for(var6 = 0; var6 < var5; ++var6) {
         pair = var4[var6];
         mergedPairs.put(pair.getName(), (Binding)pair);
      }

      var4 = pairs.list;
      var5 = var4.length;

      for(var6 = 0; var6 < var5; ++var6) {
         pair = var4[var6];
         mergedPairs.put(pair.getName(), (Binding)pair);
      }

      return (Binding[])((Binding[])mergedPairs.values().toArray(new Binding[0]));
   }

   private void debug(String name, Hashtable env, String operation) {
      if (NamingDebugLogger.isDebugEnabled()) {
         String partitionNameInCIC = PartitionHandler.getPartitionName(env);
         if (!partitionNameInCIC.equals("DOMAIN") && !partitionNameInCIC.equals(this.partitionName) || !partitionNameInCIC.equals("DOMAIN") && !this.partitionName.equals(partitionNameInCIC)) {
            NamingDebugLogger.debug("cross name space happens when " + operation + " " + name + " occurs, CIC : [" + partitionNameInCIC + "], context refers to [" + this.partitionName + "]");
         }
      }

   }

   private ServiceAdvertiser getServiceAdvertiser() {
      return (ServiceAdvertiser)GlobalServiceLocator.getServiceLocator().getService(ServiceAdvertiser.class, new Annotation[0]);
   }

   protected BasicNamingNode newSubnode(String relativeName, String s) {
      return new ServerNamingNode(s.toCharArray(), (ServerNamingNode)null, relativeName, this.subtreeScopeNameListenerList, this.partitionName, this.sharableAware);
   }

   private boolean isCrossPartitionAccess(Object obj, Hashtable env) {
      if (!enableVisibilityControl) {
         return false;
      } else {
         String envPartition = null;
         if (env != null) {
            envPartition = (String)env.get("weblogic.jndi.partitionInformation");
         }

         return envPartition != null && !envPartition.equals(this.getCurrentPartitonName());
      }
   }

   private void doWarningLogForCrossPartitionAccess(Hashtable env, String name) {
      String currentPartition = this.getCurrentPartitonName();
      String envPartition = (String)env.get("weblogic.jndi.partitionInformation");
      String jndiName = this.getFullJNDIName(name);
      JNDILogger.logIsCrossPartitionAccessWarning(jndiName, currentPartition, envPartition);
   }

   private static Set getPrimitiveWrapperSets() {
      Set ret = new HashSet();
      ret.add(Boolean.class);
      ret.add(Character.class);
      ret.add(Byte.class);
      ret.add(Short.class);
      ret.add(Integer.class);
      ret.add(Long.class);
      ret.add(Float.class);
      ret.add(Double.class);
      return ret;
   }

   private boolean isInCPAWhiteList(Object obj) {
      return obj instanceof String || PrimitiveWrapperSets.contains(obj.getClass()) || obj instanceof Alias;
   }

   private NamingException newNotAllowCrossPartitionNamingException(Object c, Hashtable env) {
      String envPartition = (String)env.get("weblogic.jndi.partitionInformation");
      return new NamingException("Do not allow cross partition access for " + c + "(" + envPartition + "), from partition " + this.getCurrentPartitonName());
   }

   protected PartitionVisibleRef.CPAwareSource isCrossPartitionAware(Object obj, Hashtable env) {
      if (env != null && env.containsKey("weblogic.jndi.crossPartitionAware")) {
         return Boolean.parseBoolean((String)env.get("weblogic.jndi.crossPartitionAware")) ? PartitionVisibleRef.CPAwareSource.METHOD_TRUE : PartitionVisibleRef.CPAwareSource.METHOD_FALSE;
      } else if (obj instanceof CrossPartitionAware) {
         return PartitionVisibleRef.CPAwareSource.INTERFACE;
      } else {
         return this.isAnnotatedCrossPartitionAware(obj) ? PartitionVisibleRef.CPAwareSource.ANNOTATION : PartitionVisibleRef.CPAwareSource.NONE;
      }
   }

   protected Object getPartitionVisibleObject(String name, Object bound, Hashtable env) throws NamingException {
      if (bound != null) {
         PartitionVisibleRef ref = null;
         if (bound instanceof PartitionVisibleRef) {
            ref = (PartitionVisibleRef)bound;
            bound = ref.getReferent();
         }

         if (this.isCrossPartitionAccess(bound, env)) {
            if (ref == null) {
               if (!this.isInCPAWhiteList(bound)) {
                  throw this.newNotAllowCrossPartitionNamingException(bound, env);
               }
            } else {
               if (PartitionVisibleRef.CPAwareSource.METHOD_FALSE == ref.getSource()) {
                  throw this.newNotAllowCrossPartitionNamingException(bound, env);
               }

               if (PartitionVisibleRef.CPAwareSource.INTERFACE == ref.getSource()) {
                  boolean isAllowed = false;

                  try {
                     isAllowed = ((CrossPartitionAware)ref.getOriginal()).isAccessAllowed();
                  } catch (Exception var7) {
                  }

                  if (!isAllowed) {
                     throw this.newNotAllowCrossPartitionNamingException(bound, env);
                  }
               }
            }
         }
      }

      return bound;
   }

   private boolean isAnnotatedCrossPartitionAware(Object obj) {
      return obj.getClass().isAnnotationPresent(weblogic.jndi.annotation.CrossPartitionAware.class);
   }

   private String getCurrentPartitonName() {
      return ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
   }

   private void checkIfRemoteUserHasPermission(AuthenticatedSubject subject, String action, Hashtable env) throws NoPermissionException {
      if (!isRemoteAnonymousEnabled && env != null && env.get("java.naming.provider.url") != null) {
         if (SubjectUtils.isUserAnonymous(subject)) {
            if (!"lookup".equals(action)) {
               throw new NoPermissionException("A remote anonymous user does not have permission to perform this JNDI operation.");
            }
         }
      }
   }
}
