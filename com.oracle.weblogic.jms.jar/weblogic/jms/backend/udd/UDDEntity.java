package weblogic.jms.backend.udd;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BEDestinationRuntimeDelegate;
import weblogic.jms.backend.BETopicImpl;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.backend.DestinationEntityProvider;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.JMSTargetsListener;
import weblogic.jms.dd.UniformDistributedDestination;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.TargetListSave;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.kernel.Destination;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.common.PartitionNameUtils;

public class UDDEntity implements JMSModuleManagedEntity, JMSTargetsListener {
   public static final HashMap uDQueueBeanSignatures = new HashMap();
   public static final HashMap uDTopicBeanSignatures = new HashMap();
   public static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String name;
   private final boolean isQueue;
   private final ApplicationContextInternal appCtx;
   private final String moduleName;
   private final String earModuleName;
   private final Context namingContext;
   private final SyntheticJMSBean fakeJMSBean;
   private final UDDEntityHelper uddEntityHelper;
   private final boolean isJMSResourceDefinition;
   private final String partitionName;
   private UDDDests dests = new UDDDests();
   private List lotsOfListeners = new LinkedList();
   private UniformDistributedDestination udd;
   private UniformDistributedDestinationBean uDestBean;
   private SyntheticDDBean ddBean;
   private JMSBean wholeModule;
   private DomainMBean proposedDomain;
   UUID jmsuuid = null;
   UUID pptuid = null;
   Targeter targeter;

   public UDDEntity(String paramName, ApplicationContext appCtx, String moduleName, String earModuleName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean domain, boolean isJMSResourceDefinition) throws ModuleException {
      this.name = paramName;

      assert specificBean instanceof UniformDistributedDestinationBean;

      this.uDestBean = (UniformDistributedDestinationBean)specificBean;
      this.appCtx = (ApplicationContextInternal)appCtx;
      this.moduleName = moduleName;
      this.earModuleName = earModuleName;
      this.namingContext = namingContext;
      this.wholeModule = wholeModule;
      JMSService jmsService = JMSService.getJMSServiceWithModuleException();
      this.partitionName = jmsService.getPartitionName();
      this.uddEntityHelper = jmsService.getUddEntityHelper();
      this.isQueue = this.uDestBean instanceof UniformDistributedQueueBean;
      this.fakeJMSBean = new SyntheticJMSBean(this);
      if (localTargets != null && localTargets.size() == 0) {
         localTargets = null;
      } else if (localTargets != null && JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Constructor called for " + this.name + ": targets are: " + this.prTargets(localTargets));
      }

      this.debugme("initializing local Targets: " + localTargets);
      this.isJMSResourceDefinition = isJMSResourceDefinition;
      if (domain == null) {
         if (localTargets != null) {
            domain = JMSLegalHelper.getDomain((TargetMBean)localTargets.get(0));
         } else {
            domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         }
      }

      this.proposedDomain = domain;
      if (this.isQueue) {
         this.ddBean = new SyntheticDQBean(this);
      } else {
         this.ddBean = new SyntheticDTBean(this);
      }

      this.udd = new UniformDistributedDestination(this.name, this.fakeJMSBean, this.ddBean, earModuleName, moduleName, appCtx, namingContext, isJMSResourceDefinition);

      try {
         this.targeter = new Targeter();
         UUID uuid = UUID.randomUUID();
         this.targeter.createNewTargeting(uuid, localTargets, domain);
         this.targeter.create(uuid);
      } catch (Exception var13) {
         throw new ModuleException("Error Creating", var13);
      }
   }

   private void debugHere(String msg) {
      Exception e = new Exception(msg);
      e.fillInStackTrace();
      this.debugme(msg, e);
   }

   private void debugme(String msg) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(this.name + ":" + msg);
      }

   }

   private void debugme(String msg, Throwable e) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(this.name + ":" + msg, e);
         e.printStackTrace();
      }

   }

   private String prTargets(List targets) {
      StringBuilder builder = new StringBuilder();

      for(Iterator iter = targets.listIterator(); iter.hasNext(); builder.append(((TargetMBean)iter.next()).getName())) {
         if (builder.length() != 0) {
            builder.append(", ");
         }
      }

      return builder.toString();
   }

   public JMSBean getJMSModuleBean() {
      return this.wholeModule;
   }

   boolean isQueue() {
      return this.isQueue;
   }

   public QueueBean[] getQueues() {
      Set beans = this.dests.getAllBeans();
      Iterator sb = beans.iterator();
      if (sb.hasNext() && !(sb.next() instanceof SyntheticQueueBean)) {
         sb.remove();
      }

      return (QueueBean[])beans.toArray(new QueueBean[beans.size()]);
   }

   public TopicBean[] getTopics() {
      Set beans = this.dests.getAllBeans();
      Iterator sb = beans.iterator();
      if (sb.hasNext() && !(sb.next() instanceof SyntheticTopicBean)) {
         sb.remove();
      }

      return (TopicBean[])beans.toArray(new TopicBean[beans.size()]);
   }

   public TopicBean lookupTopic(String name) {
      SyntheticDestinationBean me = this.dests.getBeanByUddName(name);
      if (!(me instanceof TopicBean)) {
         this.debugme("Could not get bean " + name + " in " + this.dests.getAllUddNames());
         return null;
      } else {
         return (TopicBean)me;
      }
   }

   public QueueBean lookupQueue(String name) {
      SyntheticDestinationBean me = this.dests.getBeanByUddName(name);
      if (!(me instanceof QueueBean)) {
         this.debugme("Could not get bean " + name + " in " + this.dests.getAllUddNames());
         return null;
      } else {
         return (QueueBean)me;
      }
   }

   public String getEntityName() {
      return this.uDestBean.getName();
   }

   public String getName() {
      return this.name;
   }

   UniformDistributedDestinationBean getUDestBean() {
      return this.uDestBean;
   }

   SyntheticDDBean getDDBean() {
      return this.ddBean;
   }

   boolean isJMSResourceDefinition() {
      return this.isJMSResourceDefinition;
   }

   public synchronized void activate(JMSBean paramWholeModule) throws ModuleException {
      this.wholeModule = paramWholeModule;
      if (this.uDestBean instanceof UniformDistributedQueueBean) {
         this.uDestBean = this.wholeModule.lookupUniformDistributedQueue(this.getEntityName());
      } else {
         this.uDestBean = this.wholeModule.lookupUniformDistributedTopic(this.getEntityName());
      }

      try {
         this.dests.setUDDState(UDDEntity.EntityState.ACTIVATE, UDDEntity.EntityState.PREPARE, true, false);
      } catch (Exception var3) {
         throw new ModuleException("Error Activating", var3);
      }

      if (this.udd != null) {
         this.udd.activate((JMSBean)null);
      }

      JMSService jmsService = JMSService.getJMSServiceWithModuleException(this.partitionName);
      this.unregisterBeanUpdateListeners(jmsService);
      this.registerBeanUpdateListeners(jmsService);
   }

   public synchronized void deactivate() throws ModuleException {
      JMSService jmsService = JMSService.getJMSServiceWithModuleException(this.partitionName);
      this.unregisterBeanUpdateListeners(jmsService);

      try {
         this.dests.setUDDState(UDDEntity.EntityState.DEACTIVATE, (EntityState)null, true, true);
         this.dests.setUDDState(UDDEntity.EntityState.UNPREPARE, (EntityState)null, true, true);
      } catch (Exception var3) {
         throw new ModuleException("Unexpected Error", var3);
      }

      if (this.udd != null) {
         this.udd.deactivate();
      }

   }

   public synchronized void destroy() throws ModuleException {
      if (this.udd != null) {
         this.udd.destroy();
      }

      try {
         this.dests.setUDDState(UDDEntity.EntityState.DESTROY, (EntityState)null, true, true);
      } catch (Exception var2) {
         throw new ModuleException("Unable to destroy", var2);
      }
   }

   private final void validateJNDIName(String jndiName) throws ModuleException {
      try {
         this.internalValidateJNDIName(jndiName);
      } catch (BeanUpdateRejectedException var3) {
         throw new ModuleException(var3.getMessage(), var3.getCause());
      }
   }

   private void internalValidateJNDIName(String jndiName) throws BeanUpdateRejectedException {
      String proposedJNDIName = JMSServerUtilities.transformJNDIName(jndiName);
      if (proposedJNDIName != null) {
         if (!this.udd.getDDHandler().isActive() || jndiName == null || !jndiName.equals(proposedJNDIName)) {
            Context replicatedContext = JMSService.getContextWithBeanUpdateRejectedException(true);
            Object boundObject = null;

            try {
               boundObject = replicatedContext.lookup(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", proposedJNDIName));
            } catch (NameNotFoundException var18) {
               return;
            } catch (NamingException var19) {
               throw new BeanUpdateRejectedException(var19.getMessage(), var19);
            }

            if (boundObject != null) {
               boundObject = null;
               ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
               String partitionName = cic == null ? null : cic.getPartitionName();
               JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
               if (jmsService != null) {
                  String subDeploymentName = this.getUDestBean() == null ? null : this.getUDestBean().getSubDeploymentName();
                  List uddTargets = new ArrayList();
                  boolean isDefaultTargeted = this.getUDestBean() == null ? false : this.getUDestBean().isDefaultTargetingEnabled();
                  boolean isRGTDeployment;
                  int i;
                  String currentUDDType;
                  if (isDefaultTargeted) {
                     isRGTDeployment = AppDeploymentHelper.isDeployedThroughRGT(this.appCtx.getBasicDeploymentMBean());
                     JMSServerMBean[] candidateJMSServers = JMSModuleHelper.getCandidateJMSServers(this.proposedDomain, JMSModuleHelper.getDeploymentScope(this.appCtx), "Distributed", isRGTDeployment);
                     JMSServerMBean[] var13 = candidateJMSServers;
                     int var14 = candidateJMSServers.length;

                     for(i = 0; i < var14; ++i) {
                        JMSServerMBean candidate = var13[i];
                        uddTargets.add(candidate.getName());
                     }
                  } else {
                     isRGTDeployment = subDeploymentName != null && subDeploymentName.length() != 0;
                     Map subTargets = !isRGTDeployment ? null : getSubDeploymentTargets(this.proposedDomain, subDeploymentName, partitionName);
                     if (subTargets == null) {
                        return;
                     }

                     boolean isTargetNotJMSServer = false;
                     Iterator var29 = subTargets.keySet().iterator();

                     while(var29.hasNext()) {
                        currentUDDType = (String)var29.next();
                        if (!(subTargets.get(currentUDDType) instanceof JMSServerMBean)) {
                           isTargetNotJMSServer = true;
                           break;
                        }
                     }

                     if (isTargetNotJMSServer) {
                        HashMap fillMe = new HashMap();
                        this.fillWithMyTargets(this.proposedDomain, fillMe, new ArrayList(subTargets.values()));
                        uddTargets = new ArrayList(fillMe.keySet());
                     } else {
                        uddTargets = new ArrayList(subTargets.keySet());
                     }
                  }

                  Iterator var23 = uddTargets.iterator();

                  while(var23.hasNext()) {
                     String jmsServerInstanceName = (String)var23.next();
                     BackEnd backEnd = jmsService.getBEDeployer().findBackEnd(jmsServerInstanceName);
                     if (backEnd != null) {
                        Context context = backEnd.getJmsService().getCtx(false);

                        for(i = 0; i < 40; ++i) {
                           try {
                              boundObject = context.lookup(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", JMSServerUtilities.transformJNDIName(proposedJNDIName)));
                           } catch (NameNotFoundException var21) {
                              break;
                           } catch (NamingException var22) {
                              throw new BeanUpdateRejectedException(var22.getMessage(), var22);
                           }

                           try {
                              Thread.sleep(500L);
                           } catch (InterruptedException var20) {
                              break;
                           }
                        }

                        if (boundObject != null) {
                           break;
                        }
                     }
                  }

                  if (boundObject != null) {
                     String exceptionMessage = null;
                     if (boundObject instanceof DistributedDestinationImpl) {
                        int type = this.uDestBean instanceof UniformDistributedQueueBean ? 0 : 1;
                        DistributedDestinationImpl ddImpl = (DistributedDestinationImpl)boundObject;
                        if (ddImpl.getName().equals(this.name) && ddImpl.getType() == this.convertToDestinationImplType(type)) {
                           return;
                        }

                        try {
                           Thread.sleep(500L);
                        } catch (InterruptedException var17) {
                        }

                        if (ddImpl.getName().equals(this.name) && ddImpl.getType() == this.convertToDestinationImplType(type)) {
                           return;
                        }

                        String jndiObjectType = ddImpl.getType() == this.convertToDestinationImplType(0) ? "Queue" : "Topic";
                        currentUDDType = type == 0 ? "Queue" : "Topic";
                        exceptionMessage = "JNDI lookup returned object DistributedDestinationImpl with name '" + ddImpl.getName() + "' and of type '" + jndiObjectType + "'. The current UniformDistributedDestination has name '" + this.name + "' and is of type '" + currentUDDType + "'";
                     }

                     if (!this.isJMSResourceDefinition) {
                        throw new BeanUpdateRejectedException(JMSExceptionLogger.logObjectAlreadyBoundLoggable(proposedJNDIName, this.name, boundObject.getClass().getName(), exceptionMessage == null ? "" : "[" + exceptionMessage + "]").getMessage());
                     }
                  }
               }
            }
         }
      }
   }

   public synchronized void prepare() throws ModuleException {
      this.uddEntityHelper.addUDDEntity(this);

      try {
         this.dests.setUDDState(UDDEntity.EntityState.PREPARE, (EntityState)null, false, false);
      } catch (Exception var5) {
         throw new ModuleException("Unable to prepare", var5);
      } finally {
         if (this.udd != null) {
            this.validateJNDIName(this.uDestBean.getJNDIName());
            this.udd.prepare();
         }

      }

   }

   private int convertToDestinationImplType(int destType) {
      return destType == 0 ? 1 : 2;
   }

   public synchronized void remove() throws ModuleException {
      try {
         this.dests.setUDDState(UDDEntity.EntityState.REMOVE, (EntityState)null, true, true);
      } catch (Exception var5) {
         throw new ModuleException("Unable to remove", var5);
      } finally {
         if (this.udd != null) {
            this.udd.remove();
         }

      }

   }

   public synchronized void unprepare() throws ModuleException {
      try {
         this.dests.setUDDState(UDDEntity.EntityState.UNPREPARE, (EntityState)null, true, true);
      } catch (Exception var5) {
         throw new ModuleException("Unable to Unprepare", var5);
      } finally {
         this.uddEntityHelper.removeUDDEntity(this);
         if (this.udd != null) {
            this.udd.unprepare();
         }

      }

   }

   private static final boolean safeEquals(Object a, Object b) {
      if (a == b) {
         return true;
      } else if (a == null) {
         return false;
      } else {
         return b == null ? false : a.equals(b);
      }
   }

   private static Map getSubDeploymentTargets(DomainMBean domain, String subName, String partitionName) {
      Map subdepTargets = new HashMap();
      BasicDeploymentMBean[] var4 = domain.getBasicDeployments();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BasicDeploymentMBean bde = var4[var6];
         String bdename = bde.getPartitionName() == null ? "DOMAIN" : bde.getPartitionName();
         if (safeEquals(partitionName, bdename)) {
            SubDeploymentMBean s = bde.lookupSubDeployment(subName);
            if (s != null) {
               TargetMBean[] var10 = s.getTargets();
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  TargetMBean t = var10[var12];
                  if (!(t instanceof VirtualTargetMBean)) {
                     subdepTargets.put(t.getName(), t);
                  }
               }
            }
         }
      }

      return subdepTargets;
   }

   public String getSubDeploymentName() {
      return this.getUDestBean() == null ? null : this.getUDestBean().getSubDeploymentName();
   }

   public synchronized void updateState(UUID uid, DomainMBean domain, String instanceName, EntityState newState, JMSServerMBean... servers) throws DeploymentException, BeanUpdateRejectedException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("updateState: [" + this.getName() + "," + newState + "]" + uid);
      }

      String sdeployName = this.getUDestBean() == null ? null : this.getUDestBean().getSubDeploymentName();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic == null ? null : cic.getPartitionName();
      boolean isSubDeployment = sdeployName != null && sdeployName.length() != 0;
      Map subTargets = !isSubDeployment ? null : getSubDeploymentTargets(domain, sdeployName, partitionName);
      boolean isDefaultTargeted = this.getUDestBean() == null ? false : this.getUDestBean().isDefaultTargetingEnabled();
      if (!isDefaultTargeted || subTargets != null && !((Map)subTargets).isEmpty()) {
         JMSServerMBean[] var28 = servers;
         int var30 = servers.length;

         for(int var31 = 0; var31 < var30; ++var31) {
            JMSServerMBean jmsServer = var28[var31];
            if (subTargets != null && ((Map)subTargets).get(jmsServer.getName()) != null || this.isJMSResourceDefinition && sdeployName != null && sdeployName.equals(jmsServer.getName())) {
               ((Map)subTargets).put(jmsServer.getName(), jmsServer);
            }
         }
      } else {
         if (subTargets == null) {
            subTargets = new HashMap();
         }

         boolean isRGTDeployment = AppDeploymentHelper.isDeployedThroughRGT(this.appCtx.getBasicDeploymentMBean());
         JMSServerMBean[] candidateJMSServers = JMSModuleHelper.getCandidateJMSServers(domain, JMSModuleHelper.getDeploymentScope(this.appCtx), "Distributed", isRGTDeployment);
         Set candidateJMSServerNames = new HashSet();
         JMSServerMBean[] var15 = candidateJMSServers;
         int var16 = candidateJMSServers.length;

         int var17;
         JMSServerMBean jmsServer;
         for(var17 = 0; var17 < var16; ++var17) {
            jmsServer = var15[var17];
            candidateJMSServerNames.add(jmsServer.getName());
         }

         var15 = servers;
         var16 = servers.length;

         for(var17 = 0; var17 < var16; ++var17) {
            jmsServer = var15[var17];
            if (candidateJMSServerNames.contains(jmsServer.getName())) {
               ((Map)subTargets).put(jmsServer.getName(), jmsServer);
            }
         }
      }

      List targetList = subTargets != null ? new ArrayList(((Map)subTargets).values()) : new ArrayList();

      try {
         this.targeter.createNewTargeting(uid, targetList, domain, !isSubDeployment, instanceName, newState == UDDEntity.EntityState.DEACTIVATE || newState == UDDEntity.EntityState.UNPREPARE);
         switch (newState) {
            case PREPARE:
               this.targeter.prepare(uid);
               break;
            case ACTIVATE:
               this.targeter.activate(uid);
               this.notifyUDDActivated();
               break;
            case DEACTIVATE:
               this.targeter.deactivate(uid);
               break;
            case UNPREPARE:
               this.targeter.unprepare(uid);
            case DESTROY:
            case REMOVE:
            case NEW:
            default:
               break;
            case CREATE:
               this.targeter.create(uid);
         }
      } catch (DeploymentException var24) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception in UpdateState: [" + this.getName() + "]" + uid, var24);
         }

         throw var24;
      } catch (BeanUpdateRejectedException var25) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception in UpdateState: [" + this.getName() + "]" + uid, var25);
         }

         throw var25;
      } catch (RuntimeException var26) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception in UpdateState: [" + this.getName() + "]" + uid, var26);
         }

         throw var26;
      } finally {
         this.completePendingTasks();
      }

   }

   public synchronized void rollBackState(UUID uid, boolean ignoreExceptions) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("rollBackState: [" + this.getName() + "]" + uid);
      }

      try {
         this.targeter.rollback(uid);
      } catch (Exception var7) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception Rolling Back " + this.getName(), var7);
         }
      } finally {
         this.completePendingTasks();
      }

   }

   public synchronized void complete(UUID uid) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("complete: [" + this.getName() + "]" + uid);
      }

      try {
         this.notifyUDDActivated();
      } catch (Throwable var6) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Exception Rolling Back " + this.getName(), var6);
         }
      } finally {
         this.targeter.removeTarget(uid);
         this.completePendingTasks();
      }

   }

   public synchronized void prepareUpdate(DomainMBean domain, TargetMBean targetMBean, int action, boolean migrationInProgress) throws BeanUpdateRejectedException {
      this.jmsuuid = UUID.randomUUID();

      try {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("rollbackUpdate: [" + this.getName() + "]" + this.jmsuuid);
         }

         this.targeter.createNewTargeting(this.jmsuuid, (List)null, domain, false);
         boolean hasOnlyRemoteJMS = this.targeter.hasOnlyNewRemoteServers(this.jmsuuid);
         if (!hasOnlyRemoteJMS) {
            this.targeter.removeTarget(this.jmsuuid);
            this.jmsuuid = null;
         } else {
            try {
               this.targeter.create(this.jmsuuid);
               this.targeter.prepare(this.jmsuuid);
            } catch (DeploymentException var10) {
               throw new AssertionError("Prepare Failed:", var10);
            }
         }
      } finally {
         this.completePendingTasks();
      }

   }

   public synchronized void rollbackUpdate() {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("rollbackUpdate: [" + this.getName() + "]" + this.jmsuuid);
      }

      if (this.jmsuuid != null) {
         try {
            this.targeter.rollback(this.jmsuuid);
         } catch (Exception var5) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Exception Rolling Back " + this.getName(), var5);
            }
         } finally {
            this.targeter.removeTarget(this.jmsuuid);
            this.jmsuuid = null;
            this.completePendingTasks();
         }
      }

   }

   public synchronized void activateUpdate() {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("activateChangeOfTargets: [" + this.getName() + "]" + this.jmsuuid);
      }

      if (this.jmsuuid != null) {
         try {
            this.targeter.activate(this.jmsuuid);
            this.targeter.cleanup(this.jmsuuid);
            this.notifyUDDActivated();
         } catch (Throwable var5) {
            throw new AssertionError("Activate Failed: " + this.getName(), var5);
         } finally {
            this.targeter.removeTarget(this.jmsuuid);
            this.jmsuuid = null;
            this.completePendingTasks();
         }
      }

   }

   public synchronized void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) throws ModuleException {
      this.pptuid = UUID.randomUUID();
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("prepareChangeOfTargets[" + this.getName() + "]:(" + this.pptuid + "):" + targets);
      }

      try {
         this.targeter.createNewTargeting(this.pptuid, targets, proposedDomain, false);
         this.targeter.create(this.pptuid);
         this.targeter.prepare(this.pptuid);
      } catch (IllegalArgumentException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new ModuleException("Rejecting Targeting Change[" + this.getName() + "]", var9);
      } finally {
         this.completePendingTasks();
      }

   }

   public synchronized void activateChangeOfTargets() throws ModuleException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("activateChangeOfTargets: [" + this.getName() + "]" + this.pptuid);
      }

      try {
         if (this.pptuid != null) {
            try {
               this.targeter.activate(this.pptuid);
               this.targeter.cleanupLocalDests(this.pptuid);
               this.notifyUDDActivated();
            } catch (Throwable var10) {
               throw new AssertionError("Activate Failed:" + this.getName(), var10);
            } finally {
               this.targeter.removeTarget(this.pptuid);
               this.pptuid = null;
            }
         }
      } finally {
         this.completePendingTasks();
      }

   }

   public synchronized void rollbackChangeOfTargets() {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("rollbackChangeOfTargets: [" + this.getName() + "]" + this.pptuid);
      }

      try {
         if (this.pptuid != null) {
            try {
               this.targeter.rollback(this.pptuid);
            } catch (Exception var10) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Exception Rolling Back " + this.getName(), var10);
               }
            } finally {
               this.targeter.removeTarget(this.pptuid);
               this.pptuid = null;
            }
         }
      } finally {
         this.completePendingTasks();
      }

   }

   private Set getLocalServers() {
      JMSService jmsService = JMSService.getJMSServiceWithIllegalStateException(this.partitionName);
      return jmsService.getJMSServerNames();
   }

   private final void registerBeanUpdateListeners(JMSService jmsService) {
      jmsService.addJMSServerListener(this);
      if (this.isQueue) {
         this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean, this, uDQueueBeanSignatures, (Map)null));
      } else {
         this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean, this, uDTopicBeanSignatures, (Map)null));
         this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)((UniformDistributedTopicBean)this.uDestBean).getMulticast(), this, JMSBeanHelper.multicastBeanSignatures, (Map)null));
      }

      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean.getThresholds(), this, JMSBeanHelper.thresholdBeanSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean.getDeliveryParamsOverrides(), this, JMSBeanHelper.deliveryOverridesSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean.getDeliveryFailureParams(), this, JMSBeanHelper.deliveryFailureSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean.getDeliveryFailureParams(), this, JMSBeanHelper.localDeliveryFailureSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean.getMessageLoggingParams(), this, JMSBeanHelper.messageLoggingSignatures, (Map)null));
      this.lotsOfListeners.add(new GenericBeanListener((DescriptorBean)this.uDestBean, this, JMSBeanHelper.localDestinationBeanSignatures, (Map)null));
   }

   private final void unregisterBeanUpdateListeners(JMSService jmsService) {
      jmsService.removeJMSServerListener(this);
      Iterator iter = this.lotsOfListeners.listIterator();

      while(iter.hasNext()) {
         ((GenericBeanListener)iter.next()).close();
      }

      this.lotsOfListeners.clear();
   }

   private void completePendingTasks() {
      this.dests.processPendingTasks();
   }

   private void notifyUDDActivated() {
      if (this.udd != null) {
         this.udd.activateFinished();
      }

   }

   public void setDefaultUnitOfOrder(final boolean defaultUnitOfOrderFlag) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setDefaultUnitOfOrder(" + defaultUnitOfOrderFlag + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setDefaultUnitOfOrder(defaultUnitOfOrderFlag);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setIncompleteWorkExpirationTime(final int incompleteWorkExpirationTime) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("defaultUnitOfOrderFlag(" + incompleteWorkExpirationTime + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setIncompleteWorkExpirationTime(incompleteWorkExpirationTime);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setUnitOfWorkHandlingPolicy(final String unitOfWorkHandlingPolicy) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setUnitOfWorkHandlingPolicy(" + unitOfWorkHandlingPolicy + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setUnitOfWorkHandlingPolicy(unitOfWorkHandlingPolicy);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setAttachSender(final String attachSenderString) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setAttachSender(" + attachSenderString + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setAttachSender(attachSenderString);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setConsumptionPausedAtStartup(final boolean consumptionPausedAtStartup) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setConsumptionPausedAtStartup(" + consumptionPausedAtStartup + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setConsumptionPausedAtStartup(consumptionPausedAtStartup);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setDestinationKeys(final String[] destinationKeyArray) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("consumptionPausedAtStartup(" + Arrays.toString(destinationKeyArray) + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setDestinationKeys(destinationKeyArray);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setJMSCreateDestinationIdentifier(String identifier) {
      final String newIdentifier = identifier == null ? null : JMSModuleHelper.uddMakeName(this.name, identifier);
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setJMSCreateDestinationIdentifier(" + newIdentifier + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setJMSCreateDestinationIdentifier(newIdentifier);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setInsertionPausedAtStartup(final boolean insertionPausedAtStartup) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setInsertionPausedAtStartup(" + insertionPausedAtStartup + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setInsertionPausedAtStartup(insertionPausedAtStartup);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setJNDIName(final String jndiName) {
      this.udd.setJNDIName(jndiName);
      final Set localServers = this.getLocalServers();
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setJNDIName(" + jndiName + ", " + localServers + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            if (!localServers.contains(jmsServer)) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Set JNDI Name: " + jndiName + " ignore " + jmsServer + " not local");
               }

            } else {
               String destJndiName = jndiName == null ? null : JMSModuleHelper.uddMakeName(jmsServer, jndiName);
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Set JNDI Name: " + jndiName + " setting name " + destJndiName + " on " + destination);
               }

               destination.setJNDIName(destJndiName);
            }
         }
      };
      this.dests.applyDestinationRequest(d, UDDEntity.EntityState.ACTIVATE);
   }

   public void setLocalJNDIName(final String localJndiName) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Set Local JNDI Name: " + localJndiName);
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            String destJndiName = localJndiName == null ? null : JMSModuleHelper.uddMakeName(jmsServer, localJndiName);
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Set JNDI Name: " + localJndiName + " setting name " + destJndiName + " on " + destination);
            }

            destination.setJNDIName(destJndiName);
         }
      };
      this.dests.applyDestinationRequest(d, UDDEntity.EntityState.ACTIVATE);
   }

   public void setMaximumMessageSize(final int maximumMessageSize) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMaximumMessageSize(" + maximumMessageSize + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMaximumMessageSize(maximumMessageSize);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMessagingPerformancePreference(final int throughputEmphasis) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMessagingPerformancePreference(" + throughputEmphasis + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMessagingPerformancePreference(throughputEmphasis);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setProductionPausedAtStartup(final boolean productionPausedAtStartup) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setProductionPausedAtStartup(" + productionPausedAtStartup + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setProductionPausedAtStartup(productionPausedAtStartup);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setQuota(final QuotaBean quota) throws BeanUpdateFailedException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setQuota(" + quota + ")");
      }

      ProcessDelegateRequest d = new ProcessDelegateRequest() {
         public void apply(BEDestinationRuntimeDelegate delegate, String jmsServer) throws BeanUpdateFailedException {
            delegate.setQuota(quota);
         }
      };
      this.dests.applyDelegateRequest(d);
   }

   public void setTemplate(String template) {
   }

   public void setUnitOfOrderRouting(String unitOfOrderRouting) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setUnitOfOrderRouting(" + unitOfOrderRouting + ")");
      }

      this.udd.setUnitOfOrderRouting(unitOfOrderRouting);
      this.notifyUDDActivated();
   }

   public void setForwardDelay(int forwardDelay) {
      this.udd.setForwardDelay(forwardDelay);
      this.notifyUDDActivated();
   }

   public void setResetDeliveryCountOnForward(boolean reset) {
      this.udd.setResetDeliveryCountOnForward(reset);
      this.notifyUDDActivated();
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      this.udd.setLoadBalancingPolicy(loadBalancingPolicy);
      this.notifyUDDActivated();
   }

   public String getForwardingPolicy() {
      return this.udd.getForwardingPolicy();
   }

   public void setForwardingPolicy(String forwardingPolicy) {
      this.udd.setForwardingPolicy(forwardingPolicy);
      this.notifyUDDActivated();
   }

   public void setBytesHigh(final long bytesHigh) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setBytesHigh(" + bytesHigh + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setBytesHigh(bytesHigh);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setBytesLow(final long bytesLow) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setBytesLow(" + bytesLow + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setBytesLow(bytesLow);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMessagesHigh(final long messagesHigh) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMessagesHigh(" + messagesHigh + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMessagesHigh(messagesHigh);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMessagesLow(final long messagesLow) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMessagesLow(" + messagesLow + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMessagesLow(messagesLow);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMessageLoggingEnabled(final boolean value) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMessageLoggingEnabled(" + value + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMessageLoggingEnabled(value);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMessageLoggingFormat(final String value) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMessageLoggingFormat(" + value + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setMessageLoggingFormat(value);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setDeliveryMode(final String deliveryMode) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setDeliveryMode(" + deliveryMode + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setDeliveryMode(deliveryMode);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setTimeToDeliver(final String timeToDeliver) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setTimeToDeliver(" + timeToDeliver + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setTimeToDeliver(timeToDeliver);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setTimeToLive(final long timeToLive) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setTimeToLive(" + timeToLive + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setTimeToLive(timeToLive);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setPriority(final int priority) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setPriority(" + priority + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setPriority(priority);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setRedeliveryDelay(final long redeliveryDelay) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setRedeliveryDelay(" + redeliveryDelay + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setRedeliveryDelay(redeliveryDelay);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setRedeliveryLimit(final int redeliveryLimit) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setRedeliveryLimit(" + redeliveryLimit + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setRedeliveryLimit(redeliveryLimit);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setExpirationPolicy(final String expirationPolicy) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setExpirationPolicy(" + expirationPolicy + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setExpirationPolicy(expirationPolicy);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setExpirationLoggingPolicy(final String expirationLoggingPolicy) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setExpirationLoggingPolicy(" + expirationLoggingPolicy + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setExpirationLoggingPolicy(expirationLoggingPolicy);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMulticastAddress(final String multicastAddress) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMulticastAddress(" + multicastAddress + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            if (destination instanceof BETopicImpl) {
               ((BETopicImpl)destination).setMulticastAddress(multicastAddress);
            }

         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMulticastPort(final int multicastPort) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMulticastPort(" + multicastPort + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            if (destination instanceof BETopicImpl) {
               ((BETopicImpl)destination).setMulticastPort(multicastPort);
            }

         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setMulticastTimeToLive(final int multicastTimeToLive) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setMulticastTimeToLive(" + multicastTimeToLive + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            if (destination instanceof BETopicImpl) {
               ((BETopicImpl)destination).setMulticastTimeToLive(multicastTimeToLive);
            }

         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setSAFExportPolicy(final String policy) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setSAFExportPolicy(" + policy + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            if (destination instanceof BETopicImpl) {
               ((BETopicImpl)destination).setSAFExportPolicy(policy);
            }

         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setDefaultTargetingEnabled(final boolean defaultTargetingEnabled) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setDefaultTargetingEnabled(" + defaultTargetingEnabled + ")");
      }

      ProcessDestinationRequest d = new ProcessDestinationRequest() {
         public void apply(BEDestinationImpl destination, String jmsServer) {
            destination.setDefaultTargetingEnabled(defaultTargetingEnabled);
         }
      };
      this.dests.applyDestinationRequest(d);
   }

   public void setErrorDestination(DestinationBean destination) {
      assert destination instanceof UniformDistributedDestinationBean;

      final Set localServer = this.getLocalServers();
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("setErrorDestination(" + destination + ", " + localServer + ")");
      }

      ProcessUDDInfoRequest d = new ProcessUDDInfoRequest() {
         public void apply(UDDInfo info) {
            String jmsInstanceName = info.getJMSInstanceName();
            if (localServer.contains(jmsInstanceName)) {
               if (info.getState() == UDDEntity.EntityState.ACTIVATE) {
                  BEDestinationRuntimeDelegate beDest = (BEDestinationRuntimeDelegate)info.getDestination();
                  SyntheticDestinationBean bean = info.getDestinationBean();
                  beDest.setErrorDestination(bean.getErrorDestination());
               }
            }
         }
      };
      this.dests.applyUDDInfoRequest(d);
   }

   public synchronized void setSubDeploymentName(String groupName) throws BeanUpdateRejectedException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      UUID uid = UUID.randomUUID();
      this.targeter.createNewTargeting(uid, (List)null, domain);

      try {
         this.targeter.create(uid);
         this.targeter.prepare(uid);
         this.targeter.activate(uid);
         this.notifyUDDActivated();
      } catch (DeploymentException var8) {
         throw new BeanUpdateRejectedException("Unable to set SubDeployment", var8);
      } finally {
         ;
      }

      this.completePendingTasks();
   }

   private Map getTargets(DomainMBean domain, List targets) {
      HashMap fillMe = new HashMap();
      this.fillWithMyTargets(domain, fillMe, targets);
      HashMap typeCastReturn = new HashMap();
      Iterator itr = fillMe.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry e = (Map.Entry)itr.next();
         typeCastReturn.put((String)e.getKey(), (String)e.getValue());
      }

      return typeCastReturn;
   }

   private void fillWithMyTargets(DomainMBean domain, HashMap fillMe, List targets) {
      if (targets == null) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("fillWithMyTargets called for " + this.name + ": targets is null");
         }

      } else {
         String uddType = "Uniform Distributed Topic";
         if (this.isQueue) {
            uddType = "Uniform Distributed Queue";
         }

         String entityName = this.name;
         if (this.isJMSResourceDefinition) {
            Iterator var6 = this.dests.getAllBeans().iterator();
            if (var6.hasNext()) {
               SyntheticDestinationBean destinationBean = (SyntheticDestinationBean)var6.next();
               entityName = destinationBean.getJNDIName();
            }
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("fillWithMyTargets called for " + this.name + ": targets are: " + this.prTargets(targets));
         }

         JMSModuleHelper.uddFillWithMyTargets(fillMe, domain, (TargetMBean[])((TargetMBean[])((TargetMBean[])targets.toArray(new TargetMBean[targets.size()]))), this.appCtx.getBasicDeploymentMBean(), uddType, entityName, this.isJMSResourceDefinition);
      }
   }

   static {
      Iterator iter = JMSBeanHelper.distributedTopicBeanSignatures.entrySet().iterator();

      Map.Entry entry;
      while(iter.hasNext()) {
         entry = (Map.Entry)iter.next();
         uDTopicBeanSignatures.put(entry.getKey(), entry.getValue());
      }

      iter = JMSBeanHelper.distributedQueueBeanSignatures.entrySet().iterator();

      while(iter.hasNext()) {
         entry = (Map.Entry)iter.next();
         uDQueueBeanSignatures.put(entry.getKey(), entry.getValue());
      }

      iter = JMSBeanHelper.destinationBeanSignatures.entrySet().iterator();

      while(iter.hasNext()) {
         entry = (Map.Entry)iter.next();
         uDQueueBeanSignatures.put(entry.getKey(), entry.getValue());
         uDTopicBeanSignatures.put(entry.getKey(), entry.getValue());
      }

   }

   class Targeter {
      private TargetListSave savedTargets;
      Map txnInfo = new ConcurrentHashMap();

      public boolean hasOnlyNewRemoteServers(UUID uid) {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         return ts == null ? false : ts.hasOnlyNewRemote();
      }

      public void createNewTargeting(UUID id, List newTargets, DomainMBean proposedDomain) {
         this.createNewTargeting(id, newTargets, proposedDomain, false, (String)null, false);
      }

      public void createNewTargeting(UUID id, List newTargets, DomainMBean proposedDomain, boolean addSaved) {
         this.createNewTargeting(id, newTargets, proposedDomain, addSaved, (String)null, false);
      }

      public void createNewTargeting(UUID id, List newTargets, DomainMBean proposedDomain, boolean addSaved, String instanceName, boolean overrideFillMe) {
         TargetingState ts = new TargetingState(id, newTargets, proposedDomain, addSaved, instanceName, overrideFillMe);
         this.txnInfo.put(id, ts);
      }

      public void removeTarget(UUID id) {
         this.txnInfo.remove(id);
      }

      public void create(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         ts.processSetOfTargets(ts.newTargetMap, UDDEntity.EntityState.CREATE, false);
      }

      public void prepare(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         ts.processSetOfTargets(ts.newTargetMap, UDDEntity.EntityState.PREPARE, false);
      }

      public void activate(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         ts.processSetOfTargets(ts.newTargetMap, UDDEntity.EntityState.ACTIVATE, false);
      }

      public void unprepare(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         ts.processSetOfTargets(ts.newTargetMap, UDDEntity.EntityState.UNPREPARE, true);
      }

      public void deactivate(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         ts.processSetOfTargets(ts.newTargetMap, UDDEntity.EntityState.DEACTIVATE, true);
      }

      public void cleanup(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         Set destroySet = ts.getDestroyTargets();
         if (destroySet.size() > 0) {
            ts.processSetOfTargetsIf(destroySet, UDDEntity.EntityState.DEACTIVATE, UDDEntity.EntityState.ACTIVATE, true);
            ts.processSetOfTargetsIf(destroySet, UDDEntity.EntityState.UNPREPARE, UDDEntity.EntityState.PREPARE, true);
         }

      }

      public void rollback(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         if (ts != null) {
            ts.rollback();
         }

      }

      public void cleanupLocalDests(UUID uid) throws BeanUpdateRejectedException, DeploymentException {
         TargetingState ts = (TargetingState)this.txnInfo.get(uid);
         Set requestedTargets = ts.newTargetMap.keySet();
         Set localDests = new HashSet();
         Iterator var5 = UDDEntity.this.dests.uddDestinations.keySet().iterator();

         while(var5.hasNext()) {
            String key = (String)var5.next();
            if (UDDEntity.this.dests.isLocal(key) && !requestedTargets.contains(key)) {
               localDests.add(key);
            }
         }

         if (localDests.size() > 0) {
            ts.processSetOfTargetsIf(localDests, UDDEntity.EntityState.DEACTIVATE, UDDEntity.EntityState.ACTIVATE, true);
            ts.processSetOfTargetsIf(localDests, UDDEntity.EntityState.UNPREPARE, UDDEntity.EntityState.DEACTIVATE, true);
         }

      }

      class TargetingState {
         UUID assignedID;
         String restrictedName;
         boolean rollback = false;
         DomainMBean proposedDomain;
         Map newTargetMap;
         Map processed = new HashMap();
         boolean onlyNewRemote = false;

         public TargetingState(UUID id, List newTargets, DomainMBean domain, boolean addTargetList, String instanceName, boolean override) {
            this.restrictedName = instanceName;
            this.proposedDomain = domain;
            List definedTargets = new ArrayList();
            List savedTargetList = Targeter.this.savedTargets == null ? new ArrayList() : Targeter.this.savedTargets.restoreTargets(domain);
            if (addTargetList) {
               definedTargets.addAll((Collection)savedTargetList);
            }

            if (newTargets != null) {
               definedTargets.addAll(newTargets);
            }

            this.assignedID = id;
            Set knownJMSInstances = UDDEntity.this.dests.getAllJMSInstanceNames();
            this.newTargetMap = UDDEntity.this.getTargets(domain, definedTargets);
            if (override && this.restrictedName != null) {
               String configName = UDDEntity.this.dests.getConfigName(this.restrictedName);
               if (configName != null) {
                  this.newTargetMap.put(this.restrictedName, configName);
               }
            }

            Targeter.this.savedTargets = new TargetListSave(definedTargets);
            Set s = new HashSet(this.newTargetMap.keySet());
            s.removeAll(knownJMSInstances);
            Iterator itr = s.iterator();
            this.onlyNewRemote = s.size() > 0;

            while(this.onlyNewRemote && itr.hasNext()) {
               if (UDDEntity.this.uddEntityHelper.isJMSServerLocal((String)itr.next())) {
                  this.onlyNewRemote = false;
               }
            }

         }

         public boolean hasOnlyNewRemote() {
            return this.onlyNewRemote;
         }

         public boolean isRolledBack() {
            return this.rollback;
         }

         public void rollback() throws BeanUpdateRejectedException, DeploymentException {
            this.rollback = true;
            this.revertProcessed(true);
         }

         private void putProcessed(String name, EntityState state) {
            this.processed.put(name, state);
         }

         private void processSetOfTargets(Map targets, EntityState state, boolean ignoreExceptions) throws BeanUpdateRejectedException, DeploymentException {
            try {
               if (targets != null) {
                  EntityState oldState = null;
                  Iterator itr = targets.entrySet().iterator();

                  while(itr.hasNext()) {
                     Map.Entry e = (Map.Entry)itr.next();
                     String instanceName = (String)e.getKey();
                     String configName = (String)e.getValue();
                     oldState = UDDEntity.this.dests.getEntityState(instanceName);
                     if (this.restrictedName == null || instanceName.equals(this.restrictedName)) {
                        this.putProcessed(instanceName, oldState);
                        UDDEntity.this.dests.processTarget(instanceName, configName, state, ignoreExceptions);
                     }
                  }

                  return;
               }
            } finally {
               UDDEntity.this.debugme("Completed Processing: " + targets + " -- " + state);
            }

         }

         private void processSetOfTargetsIf(Set targets, EntityState state, EntityState ifState, boolean ignoreExceptions) throws BeanUpdateRejectedException, DeploymentException {
            try {
               if (targets != null) {
                  EntityState oldState = null;
                  Iterator var6 = targets.iterator();

                  while(var6.hasNext()) {
                     String instanceName = (String)var6.next();
                     UDDEntity.this.dests.processTargetIf(instanceName, state, ifState, ignoreExceptions);
                  }

                  return;
               }
            } finally {
               UDDEntity.this.debugme("Completed Processing: " + targets + " -- " + state);
            }

         }

         private void revertProcessed(boolean ignoreExceptions) throws BeanUpdateRejectedException, DeploymentException {
            try {
               EntityState oldState = null;
               Iterator itr = this.processed.entrySet().iterator();

               while(itr.hasNext()) {
                  Map.Entry e = (Map.Entry)itr.next();
                  String instanceName = (String)e.getKey();
                  String configName = (String)this.newTargetMap.get(instanceName);
                  EntityState state = (EntityState)e.getValue();
                  UDDEntity.this.dests.processTarget(instanceName, configName, state, ignoreExceptions);
               }
            } finally {
               UDDEntity.this.debugme("Completed Processing: ");
            }

         }

         public Set getDestroyTargets() {
            Set targets = new HashSet();
            Iterator var2 = this.newTargetMap.keySet().iterator();

            while(var2.hasNext()) {
               String key = (String)var2.next();
               if (!UDDEntity.this.dests.isLocal(key)) {
                  targets.add(key);
               }
            }

            return new HashSet(targets);
         }

         public boolean hasTargets() {
            return !this.newTargetMap.isEmpty();
         }
      }
   }

   class UDDDests {
      private Map uddDestinations = new ConcurrentHashMap();
      EntityState uddState;

      UDDDests() {
         this.uddState = UDDEntity.EntityState.NEW;
      }

      public String toString() {
         return "UDDDests: uddState=" + this.uddState + " destinations=" + this.uddDestinations;
      }

      public void setUDDState(EntityState state, EntityState minState, boolean override, boolean ignoreExceptions) throws DeploymentException, BeanUpdateRejectedException {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("setUUDState: " + state + " minState=" + minState + " override=" + override);
         }

         this.uddState = state;
         if (override) {
            Iterator var5 = this.uddDestinations.values().iterator();

            while(var5.hasNext()) {
               UDDInfo info = (UDDInfo)var5.next();
               info.changeState(this.uddState, (EntityState)null, minState, ignoreExceptions);
            }
         } else {
            this.processPendingTasks();
         }

         if (state == UDDEntity.EntityState.ACTIVATE) {
            UDDEntity.this.notifyUDDActivated();
         }

      }

      public boolean contains(String jmsServerInstanceName) {
         return this.uddDestinations.containsKey(jmsServerInstanceName);
      }

      private UDDInfo findCreateUDDInfo(String jmsServerInstanceName, String jmsTemplateName) {
         UDDInfo info = (UDDInfo)this.uddDestinations.get(jmsServerInstanceName);
         if (info == null) {
            info = UDDEntity.this.new UDDInfo(jmsServerInstanceName, jmsTemplateName);
            this.uddDestinations.put(jmsServerInstanceName, info);
         }

         return info;
      }

      public void remove(String jmsServerInstanceName) {
         this.uddDestinations.remove(jmsServerInstanceName);
      }

      public Set getAllBeans() {
         Set s = new HashSet();
         SyntheticDestinationBean syntheticDestinationBean = null;
         Iterator var3 = this.uddDestinations.values().iterator();

         while(var3.hasNext()) {
            UDDInfo info = (UDDInfo)var3.next();
            syntheticDestinationBean = info.getDestinationBean();
            if (syntheticDestinationBean != null) {
               s.add(syntheticDestinationBean);
            }
         }

         return s;
      }

      public SyntheticDestinationBean getBean(String name) {
         UDDInfo info = (UDDInfo)this.uddDestinations.get(name);
         return info == null ? null : info.getDestinationBean();
      }

      public SyntheticDestinationBean getBeanByUddName(String name) {
         Iterator var2 = this.uddDestinations.values().iterator();

         UDDInfo info;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            info = (UDDInfo)var2.next();
         } while(!name.equals(info.getUddName()));

         return info.getDestinationBean();
      }

      public String getDebugState() {
         StringBuilder builder = new StringBuilder("[\n");
         Iterator var2 = this.uddDestinations.values().iterator();

         while(var2.hasNext()) {
            UDDInfo info = (UDDInfo)var2.next();
            builder.append("\t");
            builder.append(info);
            builder.append("\n");
         }

         builder.append("\n");
         return builder.toString();
      }

      public Set getAllJMSInstanceNames() {
         Set names = new HashSet();
         Iterator var2 = this.uddDestinations.values().iterator();

         while(var2.hasNext()) {
            UDDInfo info = (UDDInfo)var2.next();
            names.add(info.getJMSInstanceName());
         }

         return names;
      }

      public String getConfigName(String instanceName) {
         UDDInfo info = (UDDInfo)this.uddDestinations.get(instanceName);
         return info == null ? null : info.jmsServerConfigName;
      }

      public Set getAllUddNames() {
         Set names = new HashSet();
         Iterator var2 = this.uddDestinations.values().iterator();

         while(var2.hasNext()) {
            UDDInfo info = (UDDInfo)var2.next();
            names.add(info.getUddName());
         }

         return names;
      }

      public void applyDestinationRequest(ProcessDestinationRequest d) {
         this.applyDestinationRequest(d, (EntityState)null);
      }

      public void applyDelegateRequest(ProcessDelegateRequest d) throws BeanUpdateFailedException {
         Iterator var2 = this.uddDestinations.values().iterator();

         while(var2.hasNext()) {
            UDDInfo info = (UDDInfo)var2.next();
            BEDestinationRuntimeDelegate be = (BEDestinationRuntimeDelegate)info.getDestination();
            d.apply(be, info.getJMSInstanceName());
         }

      }

      public void applyUDDInfoRequest(ProcessUDDInfoRequest d) {
         Iterator var2 = this.uddDestinations.values().iterator();

         while(var2.hasNext()) {
            UDDInfo info = (UDDInfo)var2.next();
            d.apply(info);
         }

      }

      public void applyDestinationRequest(ProcessDestinationRequest d, EntityState state) {
         assert d != null;

         Iterator var3 = this.uddDestinations.values().iterator();

         while(true) {
            UDDInfo info;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               info = (UDDInfo)var3.next();
            } while(state != null && info.getState() != state);

            BEDestinationRuntimeDelegate dd = (BEDestinationRuntimeDelegate)info.getDestination();
            if (dd != null) {
               d.apply(dd.getManagedDestination(), info.getJMSInstanceName());
            }
         }
      }

      public void processPendingTasks() {
         Iterator var1 = this.uddDestinations.values().iterator();

         while(var1.hasNext()) {
            UDDInfo info = (UDDInfo)var1.next();
            info.processPending(this.uddState);
         }

      }

      public void processTarget(String instance, String config, EntityState state, boolean ignore) throws BeanUpdateRejectedException, DeploymentException {
         UDDInfo i = this.findCreateUDDInfo(instance, config);
         i.changeState(state, this.uddState, (EntityState)null, ignore);
      }

      public void processTargetIf(String instance, EntityState state, EntityState ifState, boolean ignore) throws BeanUpdateRejectedException, DeploymentException {
         UDDInfo i = (UDDInfo)this.uddDestinations.get(instance);
         if (i != null) {
            if (i.getState() == ifState) {
               i.changeState(state, this.uddState, (EntityState)null, ignore);
            }
         }
      }

      public EntityState getEntityState(String instanceName) {
         UDDInfo info = (UDDInfo)this.uddDestinations.get(instanceName);
         return info == null ? UDDEntity.EntityState.NEW : info.getState();
      }

      public boolean isLocal(String instanceName) {
         UDDInfo info = (UDDInfo)this.uddDestinations.get(instanceName);
         return info == null ? false : info.isLocal();
      }
   }

   class UDDInfo {
      final String jmsServerConfigName;
      final String jmsServerInstanceName;
      JMSModuleManagedEntity destination;
      SyntheticDestinationBean destinationBean;
      EntityState wantedState;
      EntityState state;

      public UDDInfo(String jmsInstanceName, String jmsServerConfigName) {
         this.wantedState = UDDEntity.EntityState.NEW;
         this.state = UDDEntity.EntityState.NEW;
         this.jmsServerConfigName = jmsServerConfigName;
         this.jmsServerInstanceName = jmsInstanceName;
      }

      public SyntheticDestinationBean getDestinationBean() {
         return this.destinationBean;
      }

      public String getUddName() {
         return JMSModuleHelper.uddMakeName(this.jmsServerInstanceName, UDDEntity.this.getEntityName());
      }

      public EntityState getState() {
         return this.state;
      }

      void setState(EntityState s) {
         this.state = s;
      }

      public String getJMSInstanceName() {
         return this.jmsServerInstanceName;
      }

      public JMSModuleManagedEntity getDestination() {
         return this.destination;
      }

      public String toString() {
         return this.jmsServerInstanceName + "[" + this.jmsServerConfigName + "]  state=" + this.getState() + ", isLocal = " + this.isLocal() + " udd=" + this.getUddName() + " destination=" + this.getDestination();
      }

      boolean isLocal() {
         return UDDEntity.this.uddEntityHelper.isJMSServerLocal(this.jmsServerInstanceName);
      }

      void processPending(EntityState uddState) {
         if (this.wantedState != null && this.wantedState != this.getState()) {
            EntityState[] states = this.getStatesForTarget(this.getState(), this.wantedState);

            try {
               this.applyStateChange(states, uddState, (EntityState)null, true);
            } catch (Exception var4) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Unexpected Exception", var4);
               }
            }

         }
      }

      public void changeState(EntityState target, EntityState uddState, EntityState minState, boolean ignoreExceptions) throws DeploymentException, BeanUpdateRejectedException {
         if (target == null) {
            target = this.wantedState;
         } else {
            this.wantedState = target;
         }

         EntityState[] states = this.getStatesForTarget(this.getState(), target);
         this.applyStateChange(states, uddState, minState, ignoreExceptions);
      }

      private EntityState[] getStatesForTarget(EntityState starting, EntityState ending) {
         return starting.getStatesToUndo(ending);
      }

      private boolean checkForFastFail(EntityState target) throws BeanUpdateRejectedException {
         if (target == UDDEntity.EntityState.CREATE) {
            return UDDEntity.this.uddEntityHelper.isJMSServerLocal(this.jmsServerInstanceName) && !this.isBackendStarted();
         } else if (!UDDEntity.this.uddEntityHelper.isJMSServerLocal(this.jmsServerInstanceName)) {
            return target == UDDEntity.EntityState.PREPARE || target == UDDEntity.EntityState.ACTIVATE;
         } else {
            switch (target) {
               case PREPARE:
                  if (this.getState() != UDDEntity.EntityState.CREATE) {
                     this.destination = null;
                  }

                  if (this.destination == null) {
                     try {
                        this.createDestination();
                     } catch (Exception var5) {
                        if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                           JMSDebug.JMSBackEnd.debug("Not Ready to Create Destination Yet " + UDDEntity.this.getName(), var5);
                        }

                        return true;
                     }
                  }

                  if (this.destination == null) {
                     return true;
                  }

                  if (!this.isBackendStarted()) {
                     return true;
                  }
                  break;
               case ACTIVATE:
                  BEDestinationRuntimeDelegate de = (BEDestinationRuntimeDelegate)this.destination;
                  BEDestinationImpl di = de == null ? null : de.getManagedDestination();
                  Destination kd = di == null ? null : di.getKernelDestination();
                  if (kd == null || !kd.isCreated()) {
                     return true;
                  }

                  if (this.destination == null) {
                     return true;
                  }

                  if (!this.isBackendStarted()) {
                     return true;
                  }
                  break;
               case DEACTIVATE:
               case UNPREPARE:
                  if (this.destination == null) {
                     this.setState(target);
                     UDDEntity.this.ddBean.removeMember(this.jmsServerInstanceName);
                     UDDEntity.this.dests.remove(this.jmsServerInstanceName);
                     return true;
                  }
                  break;
               case DESTROY:
               case REMOVE:
                  if (this.destination == null) {
                     this.setState(target);
                     return true;
                  }
            }

            return false;
         }
      }

      private void applyStateChange(EntityState[] states, EntityState maxState, EntityState minState, boolean ignoreExceptions) throws DeploymentException, BeanUpdateRejectedException {
         if (minState == null || this.getState() == minState || this.getState().applyGivenState(minState)) {
            EntityState[] var5 = states;
            int var6 = states.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               EntityState s = var5[var7];
               boolean changeState = true;

               try {
                  if (maxState == null || s == maxState || s.applyGivenState(maxState)) {
                     if (s == UDDEntity.EntityState.CREATE) {
                        this.createDestinationBean();
                     }

                     SyntheticMemberBean mybean = UDDEntity.this.ddBean.findMemberBean(this.jmsServerInstanceName);
                     if (this.checkForFastFail(s)) {
                        changeState = false;
                     } else {
                        switch (s) {
                           case PREPARE:
                              this.destination.prepare();
                              break;
                           case ACTIVATE:
                              this.destination.activate(UDDEntity.this.fakeJMSBean);
                              if (mybean != null) {
                                 if (UDDEntity.this.isQueue) {
                                    UDDEntity.this.udd.finishAddDistributedQueueMembers(mybean, true);
                                 } else {
                                    UDDEntity.this.udd.finishAddDistributedTopicMembers(mybean, true);
                                 }
                              }
                              break;
                           case DEACTIVATE:
                              if (mybean != null) {
                                 if (UDDEntity.this.isQueue) {
                                    UDDEntity.this.udd.finishRemoveDistributedQueueMembers(mybean, true);
                                 } else {
                                    UDDEntity.this.udd.finishRemoveDistributedTopicMembers(mybean, true);
                                 }
                              }

                              this.destination.deactivate();
                              break;
                           case UNPREPARE:
                              if (this.destination != null) {
                                 this.destination.unprepare();
                              }

                              if (mybean != null) {
                                 if (UDDEntity.this.isQueue) {
                                    UDDEntity.this.udd.finishRemoveDistributedQueueMembers(mybean, false);
                                 } else {
                                    UDDEntity.this.udd.finishRemoveDistributedTopicMembers(mybean, false);
                                 }
                              }

                              if (!this.isMigrationInProgress()) {
                                 UDDEntity.this.ddBean.removeMember(this.jmsServerInstanceName);
                                 UDDEntity.this.dests.remove(this.jmsServerInstanceName);
                              }

                              this.destination = null;
                              break;
                           case DESTROY:
                              if (this.destination != null) {
                                 this.destination.destroy();
                              }
                              break;
                           case REMOVE:
                              if (this.destination != null) {
                                 this.destination.remove();
                              }

                              this.destination = null;
                           case NEW:
                           default:
                              break;
                           case CREATE:
                              if (UDDEntity.this.uddEntityHelper.isJMSServerLocal(this.jmsServerInstanceName)) {
                                 this.createDestination();
                              }
                        }
                     }

                     if (changeState) {
                        this.setState(s);
                     }
                  }
               } catch (BeanUpdateRejectedException | DeploymentException var11) {
                  if (!ignoreExceptions) {
                     if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                        JMSDebug.JMSBackEnd.debug("Received Exception for " + this.jmsServerInstanceName + " When transitioning to state " + this.state, var11);
                     }

                     throw var11;
                  }

                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("Ignoring Exception for " + this.jmsServerInstanceName + " When transitioning to state " + this.state, var11);
                  }
               } catch (RuntimeException var12) {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug("Received Exception for " + this.jmsServerInstanceName + " When transitioning to state " + this.state, var12);
                  }

                  throw new RuntimeException(this + " When transitioning to state " + this.state + " Target states=" + Arrays.toString(states), var12);
               }
            }

         }
      }

      private void createDestinationBean() throws BeanUpdateRejectedException {
         if (this.destinationBean == null) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Creating the destination bean for " + this.jmsServerInstanceName + "with a config of " + this.jmsServerConfigName);
            }

            UDDEntity.this.ddBean.addMember(this.jmsServerInstanceName);
            if (UDDEntity.this.isQueue) {
               this.destinationBean = new SyntheticQueueBean(UDDEntity.this, this.jmsServerInstanceName, this.jmsServerConfigName);
            } else {
               this.destinationBean = new SyntheticTopicBean(UDDEntity.this, this.jmsServerInstanceName, this.jmsServerConfigName);
            }

         }
      }

      private void createDestination() throws BeanUpdateRejectedException {
         assert this.destinationBean != null;

         assert UDDEntity.this.fakeJMSBean != null;

         if (this.destination == null) {
            EntityName entityName = new EntityName(UDDEntity.this.appCtx.getApplicationId(), UDDEntity.this.earModuleName, this.destinationBean.getName());

            try {
               this.destination = (new DestinationEntityProvider()).createEntity(UDDEntity.this.appCtx, entityName, UDDEntity.this.namingContext, UDDEntity.this.fakeJMSBean, this.destinationBean, this.jmsServerInstanceName);

               assert this.destination != null;

            } catch (ModuleException var3) {
               throw new BeanUpdateRejectedException("Failed to create Destination", var3);
            }
         }
      }

      private boolean isBackendStarted() {
         JMSService jmsService = JMSService.getJMSServiceWithPartitionName(UDDEntity.this.partitionName);
         if (jmsService == null) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("UDDEntity.UDDInfo.isBackendStarted(): JMSService for partition " + UDDEntity.this.partitionName + " is not started or shutdown. UDDEntity " + UDDEntity.this.name);
            }

            return false;
         } else {
            BackEnd be = jmsService.getBEDeployer().findBackEnd(this.jmsServerInstanceName);
            return be == null ? false : be.isStart();
         }
      }

      private boolean isMigrationInProgress() {
         JMSService jmsService = JMSService.getJMSServiceWithPartitionName(UDDEntity.this.partitionName);
         if (jmsService != null) {
            BackEnd be = jmsService.getBEDeployer().findBackEnd(this.jmsServerInstanceName);
            return be == null ? false : be.isMigrationInProgress();
         } else {
            return false;
         }
      }
   }

   public static enum EntityState {
      NEW(1, 2, -1),
      CREATE(2, 3, -2),
      PREPARE(3, 4, -3),
      ACTIVATE(4, 0, -4),
      DEACTIVATE(-4, -3, 4),
      UNPREPARE(-3, -2, 3),
      DESTROY(-2, -1, 2),
      REMOVE(-1, 0, 1);

      private static final int DONE = 0;
      private static Map idMap = new HashMap();
      private final int id;
      private final int nextOp;
      private final int undoOp;

      private EntityState(int id, int nextOp, int undoOp) {
         this.id = id;
         this.nextOp = nextOp;
         this.undoOp = undoOp;
      }

      public boolean isGreater(EntityState s) {
         return this.ordinal() - s.ordinal() < 0;
      }

      public boolean applyGivenState(EntityState s) {
         if (s.id < 0 && this.id > 0) {
            return false;
         } else if (s.id > 0 && this.id < 0) {
            return true;
         } else if (s.id < 0) {
            return !this.isGreater(s);
         } else {
            return this.isGreater(s);
         }
      }

      public EntityState[] getStatesToUndo(EntityState s) {
         ArrayList list = new ArrayList();
         if (this.id == s.id) {
            return new EntityState[0];
         } else {
            int nextState = false;
            int finalState = false;
            int nextState;
            int finalState;
            if (this.id < 0 && s.id < 0) {
               if (this.id > s.id) {
                  return new EntityState[0];
               }

               finalState = s.id;
               nextState = this.nextOp;
            } else if (this.id > 0 && s.id > 0) {
               if (this.id > s.id) {
                  return new EntityState[0];
               }

               finalState = s.id;
               nextState = this.nextOp;
            } else {
               nextState = this.undoOp;
               if (nextState < 0 && nextState > s.id || nextState > 0 && nextState > s.id) {
                  return new EntityState[0];
               }

               finalState = s.id;
            }

            EntityState state;
            while(nextState != 0 && nextState != finalState) {
               state = (EntityState)idMap.get(nextState);
               list.add(state);
               nextState = state.nextOp;
            }

            if (nextState != 0) {
               state = (EntityState)idMap.get(nextState);
               list.add(state);
            }

            return (EntityState[])list.toArray(new EntityState[list.size()]);
         }
      }

      static {
         EntityState[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            EntityState s = var0[var2];
            idMap.put(s.id, s);
         }

      }
   }

   private interface ProcessDestinationRequest {
      void apply(BEDestinationImpl var1, String var2);
   }

   private interface ProcessUDDInfoRequest {
      void apply(UDDInfo var1);
   }

   private interface ProcessDelegateRequest {
      void apply(BEDestinationRuntimeDelegate var1, String var2) throws BeanUpdateFailedException;
   }
}
