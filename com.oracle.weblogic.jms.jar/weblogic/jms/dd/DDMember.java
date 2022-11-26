package weblogic.jms.dd;

import java.security.AccessController;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.module.JMSDeploymentHelper;
import weblogic.jms.server.DestinationStatus;
import weblogic.jms.server.DestinationStatusListener;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DDMember implements DestinationStatusListener, DestinationStatus, Cloneable, Runnable {
   static final long serialVersionUID = -7244423966789145785L;
   private String name;
   private boolean isProductionPaused;
   private boolean isInsertionPaused;
   private boolean isConsumptionPaused;
   private boolean hasConsumers;
   private boolean isUp = false;
   private boolean isForwardingUp = false;
   private boolean isDestinationUp = false;
   private boolean isLocal = false;
   private boolean isPersistent;
   private int weight = 1;
   private BEDestinationImpl destination = null;
   private List statusListeners;
   private DistributedDestinationImpl ddImpl = null;
   private boolean ddImplOutOfDate = true;
   private String jmsServerInstanceName;
   private String jmsServerConfigName;
   private String persistentStoreName;
   private String wlsServerName;
   private String migratableTargetName;
   private String domainName = null;
   private JMSServerId backEndId;
   private JMSID destinationId;
   private DispatcherId dispatcherId;
   private short events = 0;
   public static final short NO_CHANGE = 0;
   public static final short HAS_CONSUMERS_CHANGE = 1;
   public static final short INSERTION_PAUSED_CHANGE = 2;
   public static final short PRODUCTION_PAUSED_CHANGE = 4;
   public static final short CONSUMPTION_PAUSED_CHANGE = 8;
   public static final short UP_CHANGE = 16;
   public static final short WEIGHT_CHANGE = 32;
   public static final short ONBIND_CHANGE = 64;
   public static final short PATH_SERVICE_JNDI_CHANGE = 128;
   private String globalJNDIName = null;
   private String pathServiceJndiName = null;
   private String localJNDIName = null;
   private DDHandler ddHandler = null;
   private MemberStatusListener currentStatusListener = null;
   private boolean scheduled = false;
   public static final int SECURITY_MODE_REMOTE_SIGNED = 11;
   public static final int SECURITY_MODE_REMOTE_UNSIGNED = 12;
   public static final int SECURITY_MODE_REMOTE_SIGNEDFULL = 13;
   public static final int SECURITY_MODE_REMOTE_KERNELID = 14;
   public static final int SECURITY_MODE_LOCAL_KERNELID = 15;
   public static final int SECURITY_MODE_UNKNOWN = 16;
   private int remoteSecurityMode = 16;
   private static final int SECURITY_MODE_FOR_WIRE = getSecurityModeForWire();
   private int deploymentMemberType = 4;
   private int distributionPolicy = -1;

   public DDMember(String name) {
      this.name = name;
      this.isInsertionPaused = false;
      this.isConsumptionPaused = false;
      this.isProductionPaused = false;
      this.hasConsumers = false;
      this.pathServiceJndiName = null;
      this.isPersistent = false;
      this.jmsServerInstanceName = null;
      this.jmsServerConfigName = null;
      this.wlsServerName = null;
      this.migratableTargetName = null;
      this.domainName = null;
      this.backEndId = null;
      this.destinationId = null;
      this.dispatcherId = null;
      this.remoteSecurityMode = 16;
      this.setDeploymentMemberType(4);
   }

   public boolean equals(Object o) {
      return o instanceof DDMember && this.name.equals(((DDMember)o).name);
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   private void determineMigratableTargetName() {
      BackEnd backend = this.destination.getBackEnd();
      if (backend != null) {
         this.migratableTargetName = JMSDeploymentHelper.getMigratableTargetName(backend.getConfigName());
      }
   }

   private void determinePersistentStoreName() {
      if (this.destination != null) {
         BackEnd backend = this.destination.getBackEnd();
         if (backend != null) {
            if (backend.getPersistentStore() != null) {
               this.persistentStoreName = backend.getPersistentStore().getName();
            }

         }
      }
   }

   public synchronized void setDestination(BEDestinationImpl destination) {
      if (this.destination == null) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("I have not seen this destination before: " + destination.getName());
         }

         this.destination = destination;
         this.isLocal = true;
         this.remoteSecurityMode = SECURITY_MODE_FOR_WIRE;
         short events = 0;
         events |= this.setIsInsertionPausedWithoutEvent(destination.isInsertionPaused());
         events |= this.setIsConsumptionPausedWithoutEvent(destination.isConsumptionPaused());
         events |= this.setIsProductionPausedWithoutEvent(destination.isProductionPaused());
         events |= this.setHasConsumersWithoutEvent(destination.hasConsumers());
         events |= this.setPathServiceJndiNameWithoutEvent(destination.getPathServiceJndiName());
         this.isPersistent = destination.isPersistent();
         this.globalJNDIName = destination.getJNDIName();
         this.localJNDIName = destination.getLocalJNDIName();
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.wlsServerName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
         this.jmsServerInstanceName = destination.getDestinationImpl().getServerName();
         this.jmsServerConfigName = destination.getDestinationImpl().getJMSServerConfigName();
         this.determinePersistentStoreName();
         this.determineMigratableTargetName();
         this.determineDeploymentMemberType();
         this.domainName = JMSDeploymentHelper.getDomainName();
         this.backEndId = destination.getDestinationImpl().getBackEndId();
         this.destinationId = destination.getDestinationImpl().getId();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("destinationId is now " + this.destinationId);
         }

         this.dispatcherId = destination.getDestinationImpl().getDispatcherId();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("dispatcherId is now " + this.dispatcherId);
         }

         destination.addStatusListener(this);
         this.isDestinationUp = false;
         this.isUp = false;
         this.isForwardingUp = false;
         events |= this.setIsDestinationUpWithoutEvent(destination.isUp());
         if (this.currentStatusListener == null) {
            new DDMemberStatusSharer(this);
         }

         this.fireEvent(events);
      }
   }

   public DDMember() {
   }

   public synchronized void notAMember() {
      if (this.destination != null) {
         this.destination.removeStatusListener(this);
      }

      this.setIsUp(false);
      this.ddHandler = null;
   }

   synchronized void update(DDMember newMemberStatus) {
      if (!this.isLocal) {
         this.name = newMemberStatus.name;
         short events = 0;
         events |= this.setIsInsertionPausedWithoutEvent(newMemberStatus.isInsertionPaused);
         events |= this.setIsConsumptionPausedWithoutEvent(newMemberStatus.isConsumptionPaused);
         events |= this.setIsProductionPausedWithoutEvent(newMemberStatus.isProductionPaused);
         events |= this.setHasConsumersWithoutEvent(newMemberStatus.hasConsumers);
         events |= this.setPathServiceJndiNameWithoutEvent(newMemberStatus.pathServiceJndiName);
         this.isPersistent = newMemberStatus.isPersistent;
         this.wlsServerName = newMemberStatus.wlsServerName;
         this.jmsServerInstanceName = newMemberStatus.jmsServerInstanceName;
         this.jmsServerConfigName = newMemberStatus.jmsServerConfigName;
         this.persistentStoreName = newMemberStatus.persistentStoreName;
         this.migratableTargetName = newMemberStatus.migratableTargetName;
         this.domainName = newMemberStatus.domainName;
         this.globalJNDIName = newMemberStatus.globalJNDIName;
         this.localJNDIName = newMemberStatus.localJNDIName;
         this.backEndId = newMemberStatus.backEndId;
         this.destinationId = newMemberStatus.destinationId;
         this.dispatcherId = newMemberStatus.dispatcherId;
         this.remoteSecurityMode = newMemberStatus.remoteSecurityMode;
         this.setDeploymentMemberType(newMemberStatus.deploymentMemberType);
         events |= this.setIsUpWithoutEvent(newMemberStatus.isUp);
         if (events != 0) {
            events = (short)(events | 64);
            DDScheduler.schedule(new EventProcessor(events, this.statusListeners));
         }

      }
   }

   public synchronized void addStatusListener(MemberStatusListener listener) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDMember.addStatusListener(MemberStatusListener@" + listener.hashCode() + "[" + listener + "]) on " + this.name + "[" + this + "]");
      }

      if (this.statusListeners == null) {
         this.statusListeners = new LinkedList();
      }

      this.statusListeners.add(listener);
      if (this.events != 0 && !this.scheduled) {
         DDScheduler.schedule(this);
         this.scheduled = true;
      }

   }

   public synchronized void removeStatusListener(MemberStatusListener listener) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Removing listener: " + listener + " on " + this.name + ", this is " + this);
      }

      if (this.statusListeners != null) {
         this.statusListeners.remove(listener);
         if (this.statusListeners.isEmpty()) {
            this.statusListeners = null;
         }

         if (this.currentStatusListener == listener) {
            this.currentStatusListener = null;
         }
      }

   }

   boolean isUOWDestination() {
      return this.destination == null ? true : this.destination.isUOWDestination();
   }

   public void onProductionPauseChange(DestinationStatus dest) {
      this.setIsProductionPaused(dest.isProductionPaused());
   }

   public void onConsumptionPauseChange(DestinationStatus dest) {
      this.setIsConsumptionPaused(dest.isConsumptionPaused());
   }

   public void onInsertionPauseChange(DestinationStatus dest) {
      this.setIsInsertionPaused(dest.isInsertionPaused());
   }

   public void onHasConsumersStatusChange(DestinationStatus dest) {
      this.setHasConsumers(dest.hasConsumers());
   }

   public void onUpStatusChange(DestinationStatus dest) {
      this.setIsDestinationUp(dest.isUp());
   }

   public void run() {
      this.callListeners();
   }

   private void callListeners() {
      short stableEvents;
      LinkedList stableListeners;
      DDMember stableThis;
      synchronized(this) {
         this.scheduled = false;
         if (this.events == 0) {
            return;
         }

         stableEvents = this.events;
         this.events = 0;
         if (this.statusListeners == null) {
            return;
         }

         stableListeners = new LinkedList(this.statusListeners);

         try {
            stableThis = (DDMember)this.clone();
         } catch (CloneNotSupportedException var7) {
            throw new AssertionError("Clone is supported, no matter what the followoing says: " + var7);
         }
      }

      this.callListeners(stableListeners, stableEvents, stableThis);
   }

   private void callListeners(List listeners, short events, DDMember member) {
      Iterator iter = listeners.listIterator();

      while(iter.hasNext()) {
         MemberStatusListener callMe = (MemberStatusListener)iter.next();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Calling out to " + callMe + " events is " + eventsPrint(events));
         }

         callMe.memberStatusChange(member, events);
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Back from " + callMe + " events is " + eventsPrint(events));
         }
      }

   }

   private static String append(String base, String add) {
      return base == null ? add : base + "|" + add;
   }

   private static String eventsPrint(short events) {
      String buf = null;
      if ((events & 1) != 0) {
         buf = append(buf, "HAS_CONSUMERS_CHANGE");
      }

      if ((events & 2) != 0) {
         buf = append(buf, "INSERTION_PAUSED_CHANGE");
      }

      if ((events & 4) != 0) {
         buf = append(buf, "PRODUCTION_PAUSED_CHANGE");
      }

      if ((events & 8) != 0) {
         buf = append(buf, "CONSUMPTION_PAUSED_CHANGE");
      }

      if ((events & 16) != 0) {
         buf = append(buf, "UP_CHANGE");
      }

      if ((events & 32) != 0) {
         buf = append(buf, "WEIGHT_CHANGE");
      }

      if ((events & 64) != 0) {
         buf = append(buf, "ONBIND_CHANGE");
      }

      if ((events & 128) != 0) {
         buf = append(buf, "PATH_SERVICE_JNDI_CHANGE");
      }

      return buf;
   }

   private void addEvent(short event) {
      this.events |= event;
      if (this.statusListeners != null) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("DDMember.addEvent(" + eventsPrint(event) + ") on " + this.name);
         }

         if (!this.scheduled) {
            DDScheduler.schedule(this);
            this.scheduled = true;
         }

      }
   }

   private void fireEvent(short events) {
      if (events != 0) {
         this.addEvent(events);
      }

   }

   public synchronized void setOutOfDate(boolean bOutOfDate) {
      this.ddImplOutOfDate = bOutOfDate;
   }

   public synchronized boolean getOutOfDate() {
      return this.ddImplOutOfDate;
   }

   public synchronized void setHasConsumers(boolean hasConsumers) {
      this.fireEvent(this.setHasConsumersWithoutEvent(hasConsumers));
   }

   public synchronized short setHasConsumersWithoutEvent(boolean hasConsumers) {
      if (hasConsumers == this.hasConsumers) {
         return 0;
      } else {
         if (this.statusListeners != null && JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Got a HAS_CONSUMERS_CHANGE on " + this.name + ", it is now " + hasConsumers);
         }

         this.ddImplOutOfDate = true;
         this.hasConsumers = hasConsumers;
         return 1;
      }
   }

   private static boolean equalString(String one, String two) {
      if (one == null) {
         return two == null;
      } else {
         return one.equals(two);
      }
   }

   public synchronized void setPathServiceJndiName(String pathServiceJndiName) {
      this.fireEvent(this.setPathServiceJndiNameWithoutEvent(pathServiceJndiName));
   }

   public synchronized short setPathServiceJndiNameWithoutEvent(String pathServiceJndiName) {
      DDHandler ddHandler;
      short event;
      synchronized(this) {
         ddHandler = this.ddHandler;
         if (!equalString(this.pathServiceJndiName, pathServiceJndiName) && pathServiceJndiName != null) {
            if (this.statusListeners != null && JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Got a PATH_SERVICE_JNDI_CHANGE on " + this.name + ", it is now " + pathServiceJndiName);
            }

            this.ddImplOutOfDate = true;
            this.pathServiceJndiName = pathServiceJndiName;
            event = 128;
         } else {
            event = 0;
         }
      }

      if (ddHandler != null) {
         ddHandler.improvePathServiceJndiName(this);
      }

      return event;
   }

   public void setIsPersistent(boolean isPersistent) {
      this.isPersistent = isPersistent;
   }

   public synchronized void setIsInsertionPaused(boolean isInsertionPaused) {
      this.fireEvent(this.setIsInsertionPausedWithoutEvent(isInsertionPaused));
   }

   public synchronized short setIsInsertionPausedWithoutEvent(boolean isInsertionPaused) {
      if (isInsertionPaused == this.isInsertionPaused) {
         return 0;
      } else {
         this.ddImplOutOfDate = true;
         this.isInsertionPaused = isInsertionPaused;
         return 2;
      }
   }

   public synchronized void setIsProductionPaused(boolean isProductionPaused) {
      this.fireEvent(this.setIsProductionPausedWithoutEvent(isProductionPaused));
   }

   public synchronized short setIsProductionPausedWithoutEvent(boolean isProductionPaused) {
      if (isProductionPaused == this.isProductionPaused) {
         return 0;
      } else {
         this.ddImplOutOfDate = true;
         this.isProductionPaused = isProductionPaused;
         return 4;
      }
   }

   public synchronized void setIsConsumptionPaused(boolean isConsumptionPaused) {
      this.fireEvent(this.setIsConsumptionPausedWithoutEvent(isConsumptionPaused));
   }

   public synchronized short setIsConsumptionPausedWithoutEvent(boolean isConsumptionPaused) {
      if (isConsumptionPaused == this.isConsumptionPaused) {
         return 0;
      } else {
         this.ddImplOutOfDate = true;
         this.isConsumptionPaused = isConsumptionPaused;
         return 8;
      }
   }

   void setIsUp(boolean isUp) {
      this.fireEvent(this.setIsUpWithoutEvent(isUp));
   }

   short setIsUpWithoutEvent(boolean isUp) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDMember.setIsUpWithoutEvent(isUp=" + isUp + ")this.isUp=" + this.isUp + " for " + this.name + ", statusListeners=" + this.statusListeners);
      }

      if (isUp == this.isUp) {
         return 0;
      } else {
         this.ddImplOutOfDate = true;
         this.isUp = isUp;
         return 16;
      }
   }

   public synchronized boolean isDestinationUp() {
      return this.isDestinationUp;
   }

   private synchronized void setIsDestinationUp(boolean isDestinationUp) {
      this.fireEvent(this.setIsDestinationUpWithoutEvent(isDestinationUp));
   }

   private synchronized short setIsDestinationUpWithoutEvent(boolean isDestinationUp) {
      this.improveDDHandlerPathServiceJndiName(this.ddHandler);
      synchronized(this) {
         if (isDestinationUp == this.isDestinationUp) {
            return 0;
         } else {
            if (this.statusListeners != null && JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Got a DESTINATION_UP_CHANGE on " + this.name + ", it is now " + isDestinationUp);
            }

            if (!isDestinationUp) {
               if (this.destination != null) {
                  this.destination.removeStatusListener(this);
                  this.destination = null;
               }

               this.isLocal = false;
            }

            this.ddImplOutOfDate = true;
            this.isDestinationUp = isDestinationUp;
            return this.setIsUpWithoutEvent(this.isForwardingUp && isDestinationUp);
         }
      }
   }

   public synchronized void setIsForwardingUp(boolean isForwardingUp) {
      if (isForwardingUp != this.isForwardingUp) {
         if (this.statusListeners != null && JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Got a FORWARDING_UP_CHANGE on " + this.name + ", it is now " + isForwardingUp);
         }

         this.isForwardingUp = isForwardingUp;
         this.setIsUp(isForwardingUp && this.isDestinationUp);
      }
   }

   public synchronized void setWeight(int weight) {
      this.fireEvent(this.setWeightWithoutEvent(weight));
   }

   public synchronized short setWeightWithoutEvent(int weight) {
      if (weight == this.weight) {
         return 0;
      } else {
         this.ddImplOutOfDate = true;
         this.weight = weight;
         return 32;
      }
   }

   public synchronized BEDestinationImpl getDestination() {
      return this.destination;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   void setRemoteSecurityMode(int sm) {
      switch (sm) {
         case 11:
         case 12:
         case 13:
         case 14:
            this.remoteSecurityMode = sm;
            return;
         default:
            throw new AssertionError();
      }
   }

   public int getRemoteSecurityMode() {
      return this.remoteSecurityMode;
   }

   void setDeploymentMemberType(int type) {
      this.deploymentMemberType = type;
   }

   public int getDeploymentMemberType() {
      return this.deploymentMemberType;
   }

   public int getMemberType1212() {
      return this.getDeploymentMemberType() & 7;
   }

   private void determineDeploymentMemberType() {
      if (this.destination != null) {
         BackEnd backend = this.destination.getBackEnd();
         if (backend != null) {
            this.setDeploymentMemberType(backend.determineDeploymentMemberType());
         }
      }
   }

   public DispatcherId getDispatcherId() {
      return this.dispatcherId;
   }

   public void setDispatcherId(DispatcherId dispatcherId) {
      this.dispatcherId = dispatcherId;
   }

   public JMSID getDestinationId() {
      return this.destinationId;
   }

   public void setDestinationId(JMSID destinationId) {
      this.destinationId = destinationId;
   }

   public JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public void setBackEndId(JMSServerId backEndId) {
      this.backEndId = backEndId;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public void setDomainName(String domainName) {
      this.domainName = domainName;
   }

   public String getMigratableTargetName() {
      return this.migratableTargetName;
   }

   public void setMigratableTargetName(String migratableTargetName) {
      this.migratableTargetName = migratableTargetName;
   }

   public String getWLSServerName() {
      return this.wlsServerName;
   }

   public void setWLSServerName(String wlsServerName) {
      this.wlsServerName = wlsServerName;
   }

   public String getJMSServerInstanceName() {
      return this.jmsServerInstanceName;
   }

   public void setJMSServerInstanceName(String jmsServerInstanceName) {
      this.jmsServerInstanceName = jmsServerInstanceName;
   }

   public String getJMSServerConfigName() {
      return this.jmsServerConfigName;
   }

   public void setJMSServerConfigName(String jmsServerConfigName) {
      this.jmsServerConfigName = jmsServerConfigName;
   }

   public String getPersistentStoreName() {
      this.determinePersistentStoreName();
      return this.persistentStoreName;
   }

   public void setPersistentStoreName(String persistentStoreName) {
      this.persistentStoreName = persistentStoreName;
   }

   public String getGlobalJNDIName() {
      return this.globalJNDIName;
   }

   public void setGlobalJNDIName(String globalJNDIName) {
      this.globalJNDIName = globalJNDIName;
   }

   public String getLocalJNDIName() {
      return this.localJNDIName;
   }

   public void setLocalJNDIName(String localJNDIName) {
      this.localJNDIName = localJNDIName;
   }

   public synchronized boolean isLocal() {
      return this.isLocal;
   }

   public synchronized boolean isInsertionPaused() {
      return this.isInsertionPaused;
   }

   public synchronized boolean isConsumptionPaused() {
      return this.isConsumptionPaused;
   }

   public synchronized boolean isProductionPaused() {
      return this.isProductionPaused;
   }

   public synchronized boolean hasConsumers() {
      return this.hasConsumers;
   }

   public String getPathServiceJndiName() {
      return this.getImprovedPathServiceJndiName();
   }

   String getImprovedPathServiceJndiName() {
      synchronized(this) {
         if (this.pathServiceJndiName != null) {
            return this.pathServiceJndiName;
         }
      }

      BEDestinationImpl localDest = this.destination;
      if (localDest == null) {
         return null;
      } else {
         String beDestPathJndiName;
         if ((beDestPathJndiName = localDest.getPathServiceJndiName()) != null) {
            DDHandler ddHandler;
            synchronized(this) {
               ddHandler = this.ddHandler;
               this.pathServiceJndiName = beDestPathJndiName;
            }

            this.improveDDHandlerPathServiceJndiName(ddHandler);
         }

         return beDestPathJndiName;
      }
   }

   private void improveDDHandlerPathServiceJndiName(DDHandler ddHandler) {
      if (this.pathServiceJndiName != null && ddHandler != null && ddHandler.retrievePathServiceJndiName() == null) {
         ddHandler.improvePathServiceJndiName(this);
      }

   }

   public synchronized boolean isUp() {
      return this.isUp;
   }

   public boolean isPersistent() {
      return this.isPersistent;
   }

   public int getWeight() {
      return this.weight;
   }

   public void setDDHandler(DDHandler ddHandler) {
      synchronized(this) {
         this.ddHandler = ddHandler;
      }

      this.getDDImpl();
   }

   public DistributedDestinationImpl getDDImpl() {
      DDHandler ddHandler = null;
      synchronized(this) {
         if (this.ddHandler == null) {
            return this.ddImpl;
         }

         ddHandler = this.ddHandler;
      }

      synchronized(ddHandler) {
         DistributedDestinationImpl var10000;
         synchronized(this) {
            if (this.ddImplOutOfDate && ddHandler != null) {
               this.ddImpl = new DistributedDestinationImpl(ddHandler.isQueue() ? 1 : 2, this.jmsServerInstanceName, this.jmsServerConfigName, ddHandler.getName(), ddHandler.getApplicationName(), ddHandler.getEARModuleName(), ddHandler.getLoadBalancingPolicyAsInt(), ddHandler.getForwardingPolicy(), this.getName(), ddHandler.getJNDIName(), this.backEndId, this.destinationId, this.dispatcherId, this.isPersistent(), this.persistentStoreName, ddHandler.getSAFExportPolicy(), this.isLocal, ddHandler.getPartitionName());
               this.ddImpl.setNonSystemSubscriberConsumers(this.hasConsumers ? 1 : 0);
               this.ddImpl.setIsProductionPaused(this.isProductionPaused());
               this.ddImpl.setIsConsumptionPaused(this.isConsumptionPaused());
               this.ddImpl.setIsInsertionPaused(this.isInsertionPaused());
               this.ddImpl.setWeight(this.weight);
               this.ddImpl.setWLSServerName(this.wlsServerName);
               boolean ctargeted = 0 != (this.deploymentMemberType & 48);
               this.ddImpl.setClusterTargeted(ctargeted);
               if (ctargeted) {
                  this.ddImpl.setDistributionPolicy(this.distributionPolicy);
                  if (this.distributionPolicy == 0 && this.jmsServerInstanceName != null) {
                     this.ddImpl.setOnDynamicNonUPS(!this.jmsServerInstanceName.endsWith("@" + this.wlsServerName));
                  }
               }

               this.ddImplOutOfDate = false;
            }

            var10000 = this.ddImpl;
         }

         return var10000;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return (DDMember)super.clone();
   }

   public String toString() {
      return "DDMember: " + this.name + ", hash: " + this.hashCode() + ", dispId: " + this.dispatcherId + ", backEndId: " + this.backEndId + ", destinationId: " + this.destinationId + ", deploymentMemberType: " + this.getDeploymentMemberType() + ", remoteSecurityMode: " + this.remoteSecurityMode;
   }

   private static int getSecurityModeForWire() {
      String PROP_NAME = "weblogic.jms.DDMemberPolicy";
      String p = System.getProperty(PROP_NAME, "");
      p = p.toLowerCase(Locale.ENGLISH).trim();
      if (p.equals("default")) {
         return 11;
      } else if (p.equals("perf")) {
         return 12;
      } else if (p.equals("full")) {
         return 13;
      } else if (p.equals("kid")) {
         return 14;
      } else {
         if (p.length() > 0) {
            (new Exception("Unexpected value for " + PROP_NAME)).printStackTrace();
         }

         return 11;
      }
   }

   class EventProcessor implements Runnable {
      private final short events;
      private final List statusListeners;

      public EventProcessor(short events, List statusListeners) {
         this.events = events;
         this.statusListeners = new LinkedList(statusListeners);
      }

      public void run() {
         DDMember.this.callListeners(this.statusListeners, this.events, DDMember.this);
      }
   }
}
