package weblogic.jms.dd;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import weblogic.application.ModuleException;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BEUOOQueueState;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.backend.QueueForwardingManager;
import weblogic.jms.backend.TopicForwardingManager;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.deployer.BEDeployer;
import weblogic.jms.frontend.FEDDHandler;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManager;

public final class DDHandler implements MemberStatusListener, Runnable {
   public static final int NO_EVENT = 0;
   public static final int MEMBERSHIP_CHANGE = 1;
   public static final int MEMBER_STATUS_CHANGE = 2;
   public static final int DD_PARAM_CHANGE = 4;
   public static final int ACTIVATE = 8;
   public static final int DEACTIVATE = 16;
   public static final int ANY_CHANGE = 255;
   public static final int NO_JNDI_EVENT_FLAG = 256;
   public static final int DEPLOY_DYNAMIC_CLUSTER = 16;
   public static final int DEPLOY_STATIC_CLUSTER = 32;
   public static final int DEPLOY_NO_CLUSTER = 64;
   public static final int DEPLOY_NON_DD = 128;
   public static final int DEPLOY_SAF_AGENT = 256;
   public static final int DEPLOY_RESOURCE_GROUP = 512;
   public static final int DEPLOY_RESOURCE_GROUP_TEMPLATE = 1024;
   private List listeners = new LinkedList();
   private static List generalListeners;
   private static Object generalLock = new Object();
   private Map members = new ConcurrentHashMap();
   private Map qfmMap = new ConcurrentHashMap();
   private Map tfmMap = new ConcurrentHashMap();
   private List memberList = new ArrayList();
   private FEDDHandler feDDHandler;
   private DDConfig ddConfig = null;
   private DistributedDestinationImpl ddImpl = null;
   private DDStatusListener currentStatusListener = null;
   private Context namingContext = null;
   private int events = 0;
   private String name = null;
   private String safExportPolicy = null;
   private String unitOfOrderRouting = null;
   private String jndiName = null;
   private int loadBalancingPolicyAsInt;
   private String applicationName = null;
   private String EARModuleName = null;
   private String referenceName = null;
   private int queueForwardDelay;
   private boolean resetDeliveryCount = true;
   private boolean active = false;
   private boolean local = false;
   private boolean isQueue;
   private boolean setupForwarding;
   private boolean isUOWDestination = false;
   private boolean remoteUpdatePending = false;
   private int forwardingPolicy = 1;
   private boolean defaultUnitOfOrder = false;
   private boolean scheduled = false;
   private boolean isJMSResourceDefinition = false;
   private String wlsServerName = ManagementService.getRuntimeAccess((AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction())).getServer().getName();
   private String pathServiceJndiName = null;
   private String partitionName = null;

   public DDHandler() {
      this.partitionName = JMSService.getSafePartitionNameFromThread();
   }

   public DDHandler(DDConfig ddConfig, boolean setupForwarding, Context namingContext, boolean isJMSResourceDefinition) throws ModuleException {
      this.name = ddConfig.getName();
      this.ddConfig = ddConfig;
      this.isQueue = ddConfig.getType() == 0;
      this.safExportPolicy = ddConfig.getSAFExportPolicy();
      this.unitOfOrderRouting = ddConfig.getUnitOfOrderRouting();
      this.jndiName = ddConfig.getJNDIName();
      this.loadBalancingPolicyAsInt = ddConfig.getLoadBalancingPolicyAsInt();
      this.applicationName = ddConfig.getApplicationName();
      this.EARModuleName = ddConfig.getEARModuleName();
      this.referenceName = ddConfig.getReferenceName();
      this.queueForwardDelay = ddConfig.getForwardDelay();
      this.resetDeliveryCount = ddConfig.getResetDeliveryCountOnForward();
      this.setupForwarding = setupForwarding;
      this.local = true;
      this.defaultUnitOfOrder = ddConfig.isDefaultUnitOfOrder();
      this.namingContext = namingContext;
      this.isJMSResourceDefinition = isJMSResourceDefinition;
      BEDeployer beDeployer = JMSService.getJMSServiceWithModuleException().getBEDeployer();
      this.pathServiceJndiName = this.beDeployerPathServiceJndiName(beDeployer);
      this.partitionName = JMSService.getSafePartitionNameFromThread();
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public Context getNamingContext() {
      return this.namingContext;
   }

   public int getForwardingPolicy() {
      return this.forwardingPolicy;
   }

   private String beDeployerPathServiceJndiName(BEDeployer beDeployer) {
      BackEnd[] backEnds = beDeployer.getBackEnds();
      BackEnd[] var3 = backEnds;
      int var4 = backEnds.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BackEnd backend = var3[var5];
         String psjn = backend.getPathServiceJndiName();
         if (psjn != null) {
            return psjn;
         }
      }

      return null;
   }

   public void setForwardingPolicy(int forwardingPolicy) {
      if (forwardingPolicy != 1 && forwardingPolicy != 0) {
         throw new RuntimeException("Invalid forwarding policy value (" + forwardingPolicy + ") found. Valid forwarding policies are the constants from DDConstants(DDConstants.FORWARDING_POLICY_PARTITIONED(value=" + 0 + ") or DDConstants.FORWARDING_POLICY_REPLICATED(value=" + 1 + ")");
      } else {
         this.forwardingPolicy = forwardingPolicy;
      }
   }

   public synchronized void activate() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("activating " + this.name);
      }

      this.feDDHandler = new FEDDHandler(this);
      if (this.currentStatusListener == null) {
         this.currentStatusListener = new DDStatusSharer(this);
      }

      this.active = true;
      this.addEvent(8);
   }

   public synchronized void deactivate() {
      this.deactivate(true);
   }

   public synchronized void deactivate(boolean withEvent) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Deactivating " + this.name);
      }

      if (withEvent) {
         this.updateMembers(new LinkedList());
      } else {
         this.updateMembersWithNoJndiEvent(new LinkedList(), true);
      }

      this.addEvent(16);
      this.shutdownForwarders();
      DDManager.deactivate(this);
      this.active = false;
      this.feDDHandler = null;
   }

   synchronized void updateMembers(List updateMembers) {
      this.fireEvent(this.updateMembersWithoutEvent(updateMembers, true));
   }

   synchronized void updateMembersWithNoJndiEvent(List updateMembers, boolean deleteLocalMember) {
      this.fireEvent(this.updateMembersWithoutEvent(updateMembers, deleteLocalMember) | 256);
   }

   private synchronized int updateMembersWithoutEvent(List updateMembers, boolean deleteLocalMember) {
      if (updateMembers == null) {
         return 0;
      } else {
         String betterPathServiceJndiName = null;
         boolean needPathServiceJndi = this.pathServiceJndiName == null;
         int eventsPathService = 0;
         int myevents = 0;
         synchronized(this.members) {
            Iterator iter = this.memberCloneIterator();

            while(iter.hasNext()) {
               DDMember member = (DDMember)iter.next();
               if (needPathServiceJndi && member.getPathServiceJndiName() != null && betterPathServiceJndiName == null) {
                  betterPathServiceJndiName = member.getPathServiceJndiName();
               }

               if (!updateMembers.contains(member.getName()) && (deleteLocalMember || !this.wlsServerName.equals(member.getWLSServerName())) || deleteLocalMember && updateMembers.contains(member.getName()) && this.wlsServerName.equals(member.getWLSServerName())) {
                  myevents |= this.removeMemberWithoutEvent(member.getName());
               }
            }

            String memberName;
            for(Iterator iter = updateMembers.listIterator(); iter.hasNext(); myevents |= this.addMemberWithoutEvent(memberName)) {
               memberName = (String)iter.next();
            }

            if (betterPathServiceJndiName != null) {
               int var10000 = eventsPathService | this.setPathServiceJndiNameWithoutEvent(betterPathServiceJndiName);
            }

            return myevents;
         }
      }
   }

   synchronized void update(DDHandler update) {
      if (this.local && !update.local) {
         this.setRemoteUpdatePending(true);
      }

      if (update.local) {
         this.local = true;
         this.ddConfig = update.ddConfig;
      }

      if (update.local) {
         this.namingContext = update.namingContext;
         this.isJMSResourceDefinition = update.isJMSResourceDefinition;
      }

      int events = 0;
      events |= this.setSAFExportPolicyWithoutEvent(update.safExportPolicy);
      events |= this.setUnitOfOrderRoutingWithoutEvent(update.unitOfOrderRouting);
      events |= this.setDefaultUnitOfOrderWithoutEvent(update.defaultUnitOfOrder);
      events |= this.setJNDINameWithoutEvent(update.jndiName);
      events |= this.setLoadBalancingPolicyAsIntWithoutEvent(update.loadBalancingPolicyAsInt);
      this.setForwardingPolicy(update.forwardingPolicy);
      events |= this.setForwardDelayWithoutEvent(update.queueForwardDelay);
      events |= this.setResetDeliveryCountOnForwardWithoutEvent(update.resetDeliveryCount);
      events |= this.setPathServiceJndiNameWithoutEvent(update.pathServiceJndiName);
      this.setApplicationName(update.applicationName);
      this.setEARModuleName(update.EARModuleName);
      this.setReferenceName(update.referenceName);
      this.setupForwarding |= update.setupForwarding;
      this.fireEvent(events);

      ListenerObject lo;
      for(Iterator var3 = update.listeners.iterator(); var3.hasNext(); lo.newDDHandler = this) {
         lo = (ListenerObject)var3.next();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Transfer DDStatusListener@" + lo.listener.hashCode() + "[" + lo.listener + "] from ddHandler@" + update.hashCode() + "[" + update + "] to DDHandler this@" + this.hashCode() + "[" + this + "]");
         }

         this.addStatusListener(lo.listener, lo.toWhat);
      }

   }

   public synchronized boolean isActive() {
      return this.active;
   }

   public synchronized boolean isLocal() {
      return this.local;
   }

   public boolean isQueue() {
      return this.isQueue;
   }

   public void setIsQueue(boolean isQueue) {
      this.isQueue = isQueue;
   }

   public void setEARModuleName(String EARModuleName) {
      this.EARModuleName = EARModuleName;
   }

   public void setReferenceName(String referenceName) {
      this.referenceName = referenceName;
   }

   public synchronized FEDDHandler getFEDDHandler() {
      return this.feDDHandler;
   }

   public String getEARModuleName() {
      return this.EARModuleName;
   }

   public String getReferenceName() {
      return this.referenceName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public boolean isJMSResourceDefinition() {
      return this.isJMSResourceDefinition;
   }

   public void setJMSResourceDefinition(boolean isJMSResourceDefinition) {
      this.isJMSResourceDefinition = isJMSResourceDefinition;
   }

   public void addMemberStatusListener(String memberName, MemberStatusListener listener) {
      DDMember member = null;
      synchronized(this.members) {
         member = (DDMember)this.members.get(memberName);

         assert member != null;

         member.addStatusListener(listener);
      }
   }

   public static void addGeneralStatusListener(DDStatusListener listener, int toWhat) {
      synchronized(generalLock) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Adding general listener: " + listener + ", listening for " + toWhat + " on everything");
         }

         LinkedList newGeneralListeners = new LinkedList();
         if (generalListeners != null) {
            Iterator iter = generalListeners.listIterator();

            while(iter.hasNext()) {
               newGeneralListeners.add(iter.next());
            }
         }

         newGeneralListeners.add(new ListenerObject(listener, toWhat));
         generalListeners = newGeneralListeners;
      }
   }

   public synchronized void addStatusListener(DDStatusListener listener, int toWhat) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDHandler.addStatusListener(DDStatusListener@" + listener.hashCode() + "[" + listener + "], listening for events " + toWhat + " on " + this.name + "[" + this + "]");
      }

      this.listeners.add(new ListenerObject(listener, toWhat));
   }

   public synchronized void removeStatusListener(DDStatusListener listener) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDHandler.removeStatusListener(DDStatusListener@" + listener.hashCode() + "[" + listener + "] for " + this.name + "[" + this + "]");
      }

      List newddhandlers = new ArrayList();
      Iterator iter = this.listeners.listIterator();

      while(iter.hasNext()) {
         ListenerObject obj = (ListenerObject)iter.next();
         if (obj.listener == listener) {
            iter.remove();
            if (obj.newDDHandler != null) {
               newddhandlers.add(obj.newDDHandler);
            }
         }
      }

      Iterator var6 = newddhandlers.iterator();

      while(var6.hasNext()) {
         DDHandler ddh = (DDHandler)var6.next();
         ddh.removeStatusListener(listener);
      }

   }

   public void run() {
      this.callListeners();
   }

   private static String append(String base, String add) {
      return base == null ? add : base + "|" + add;
   }

   private static String eventsPrint(int events) {
      String buf = null;
      if ((events & 1) != 0) {
         buf = append(buf, "MEMBERSHIP_CHANGE");
      }

      if ((events & 2) != 0) {
         buf = append(buf, "MEMBER_STATUS_CHANGE");
      }

      if ((events & 4) != 0) {
         buf = append(buf, "DD_PARAM_CHANGE");
      }

      if ((events & 8) != 0) {
         buf = append(buf, "ACTIVATE");
      }

      if ((events & 16) != 0) {
         buf = append(buf, "DEACTIVATE");
      }

      return buf;
   }

   private void callListener(ListenerObject obj, int events) {
      DDStatusListener listener = obj.listener;
      if ((obj.toWhat & events) != 0) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Calling out to " + listener);
         }

         try {
            listener.statusChangeNotification(this, events);
         } catch (Throwable var5) {
            var5.printStackTrace();
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Back from " + listener);
         }
      }

   }

   private void callListeners() {
      int stableEvents;
      LinkedList stableListeners;
      synchronized(this) {
         this.scheduled = false;
         if (this.events == 0) {
            return;
         }

         stableEvents = this.events;
         this.events = 0;
         stableListeners = new LinkedList(this.listeners);
      }

      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("DDHandler.callListeners(): Events is " + eventsPrint(stableEvents) + " for " + this.name + ", this is " + this);
      }

      Iterator iter = stableListeners.listIterator();

      while(iter.hasNext()) {
         this.callListener((ListenerObject)iter.next(), stableEvents);
      }

      List callList = new LinkedList();
      synchronized(generalLock) {
         if (generalListeners != null) {
            iter = generalListeners.listIterator();

            while(iter.hasNext()) {
               callList.add(iter.next());
            }
         }
      }

      if (callList != null) {
         iter = callList.listIterator();

         while(iter.hasNext()) {
            this.callListener((ListenerObject)iter.next(), stableEvents);
         }
      }

   }

   private void addEvent(int event) {
      if (this.active) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("DDHandler.addEvent(" + eventsPrint(event) + ") to " + this.name + "[" + this + "]");
         }

         this.events |= event;
         if (!this.scheduled) {
            DDScheduler.schedule(this);
            this.scheduled = true;
         }

      }
   }

   private void fireEvent(int events) {
      if (events != 0) {
         this.addEvent(events);
      }

   }

   public synchronized void memberStatusChange(DDMember member, int events) {
      this.addEvent(2);
   }

   private boolean equal(Object object1, Object object2) {
      if (object1 == null) {
         return object2 == null;
      } else {
         return object1.equals(object2);
      }
   }

   public String getSAFExportPolicy() {
      return this.safExportPolicy;
   }

   public synchronized void setSAFExportPolicy(String safExportPolicy) {
      this.fireEvent(this.setSAFExportPolicyWithoutEvent(safExportPolicy));
   }

   public synchronized int setSAFExportPolicyWithoutEvent(String safExportPolicy) {
      if (this.equal(safExportPolicy, this.safExportPolicy)) {
         return 0;
      } else {
         this.safExportPolicy = safExportPolicy;
         return 4;
      }
   }

   public boolean isUOWDestination() {
      return this.isUOWDestination;
   }

   public synchronized void setIsUOWDestination(boolean isUOWDestination) {
      this.fireEvent(this.setIsUOWDestinationWithoutEvent(isUOWDestination));
   }

   public synchronized int setIsUOWDestinationWithoutEvent(boolean isUOWDestination) {
      if (isUOWDestination == this.isUOWDestination) {
         return 0;
      } else {
         this.isUOWDestination = isUOWDestination;
         return 4;
      }
   }

   public String getUnitOfOrderRouting() {
      return this.unitOfOrderRouting;
   }

   public synchronized void setUnitOfOrderRouting(String unitOfOrderRouting) {
      this.fireEvent(this.setUnitOfOrderRoutingWithoutEvent(unitOfOrderRouting));
   }

   public synchronized int setUnitOfOrderRoutingWithoutEvent(String unitOfOrderRouting) {
      if (this.equal(this.unitOfOrderRouting, unitOfOrderRouting)) {
         return 0;
      } else {
         this.unitOfOrderRouting = unitOfOrderRouting;
         return 4;
      }
   }

   public boolean isDefaultUnitOfOrder() {
      return this.defaultUnitOfOrder;
   }

   public synchronized void setDefaultUnitOfOrder(boolean isDefaultUnitOfOrder) {
      this.fireEvent(this.setDefaultUnitOfOrderWithoutEvent(isDefaultUnitOfOrder));
   }

   public synchronized int setDefaultUnitOfOrderWithoutEvent(boolean isDefaultUnitOfOrder) {
      if (this.defaultUnitOfOrder == isDefaultUnitOfOrder) {
         return 0;
      } else {
         this.defaultUnitOfOrder = isDefaultUnitOfOrder;
         return 4;
      }
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public synchronized void setJNDIName(String jndiName) {
      this.fireEvent(this.setJNDINameWithoutEvent(jndiName));
   }

   public synchronized int setJNDINameWithoutEvent(String jndiName) {
      if (this.equal(this.jndiName, jndiName)) {
         return 0;
      } else {
         this.jndiName = jndiName;
         return 4;
      }
   }

   public String getPathServiceJndiName() {
      return this.getImprovedPathServiceJndiName();
   }

   String getImprovedPathServiceJndiName() {
      if (this.pathServiceJndiName != null) {
         return this.pathServiceJndiName;
      } else {
         Iterator iterator;
         synchronized(this.members) {
            iterator = (new HashMap(this.members)).values().iterator();
         }

         String value;
         do {
            if (!iterator.hasNext()) {
               return this.pathServiceJndiName;
            }

            value = this.improvePathServiceJndiName((DDMember)iterator.next());
         } while(value == null);

         return this.pathServiceJndiName;
      }
   }

   String retrievePathServiceJndiName() {
      return this.pathServiceJndiName;
   }

   String improvePathServiceJndiName(DDMember ddMember) {
      if (this.pathServiceJndiName != null) {
         return this.pathServiceJndiName;
      } else {
         String refresh = ddMember.getImprovedPathServiceJndiName();
         if (refresh != null) {
            this.pathServiceJndiName = refresh;
         }

         return this.pathServiceJndiName;
      }
   }

   public synchronized void setPathServiceJndiName(String pathServiceJndiName) {
      this.fireEvent(this.setPathServiceJndiNameWithoutEvent(pathServiceJndiName));
   }

   public synchronized int setPathServiceJndiNameWithoutEvent(String pathServiceJndiName) {
      if (!this.equal(this.pathServiceJndiName, pathServiceJndiName) && pathServiceJndiName != null) {
         this.pathServiceJndiName = pathServiceJndiName;
         return 4;
      } else {
         return 0;
      }
   }

   public int getLoadBalancingPolicyAsInt() {
      return this.loadBalancingPolicyAsInt;
   }

   public synchronized void setLoadBalancingPolicyAsInt(int loadBalancingPolicyAsInt) {
      this.fireEvent(this.setLoadBalancingPolicyAsIntWithoutEvent(loadBalancingPolicyAsInt));
   }

   public synchronized int setLoadBalancingPolicyAsIntWithoutEvent(int loadBalancingPolicyAsInt) {
      if (this.loadBalancingPolicyAsInt == loadBalancingPolicyAsInt) {
         return 0;
      } else {
         this.loadBalancingPolicyAsInt = loadBalancingPolicyAsInt;
         return 4;
      }
   }

   public int getForwardDelay() {
      return this.queueForwardDelay;
   }

   public synchronized void setForwardDelay(int queueForwardDelay) {
      this.fireEvent(this.setForwardDelayWithoutEvent(queueForwardDelay));
   }

   public synchronized int setForwardDelayWithoutEvent(int queueForwardDelay) {
      if (this.queueForwardDelay == queueForwardDelay) {
         return 0;
      } else {
         this.queueForwardDelay = queueForwardDelay;
         return 4;
      }
   }

   public boolean getResetDeliveryCountOnForward() {
      return this.resetDeliveryCount;
   }

   public synchronized void setResetDeliveryCountOnForward(boolean reset) {
      this.fireEvent(this.setResetDeliveryCountOnForwardWithoutEvent(reset));
   }

   public synchronized int setResetDeliveryCountOnForwardWithoutEvent(boolean reset) {
      if (this.resetDeliveryCount == reset) {
         return 0;
      } else {
         this.resetDeliveryCount = reset;
         return 4;
      }
   }

   synchronized void memberUpdate(DDMember member) {
      this.memberUpdateInternal(member);
   }

   private void memberUpdateInternal(DDMember member) {
      DDMember oldMember;
      synchronized(this.members) {
         oldMember = (DDMember)this.members.get(member.getName());
      }

      if (this.pathServiceJndiName == null) {
         this.pathServiceJndiName = this.improvePathServiceJndiName(member);
      }

      if (oldMember != null) {
         if (this.pathServiceJndiName == null) {
            this.pathServiceJndiName = this.improvePathServiceJndiName(oldMember);
         }

         oldMember.update(member);
      }
   }

   public synchronized void addMembers(String[] memberNames, BEDestinationImpl[] destinations) {
      if (memberNames != null && memberNames.length != 0) {
         int myevents = 0;

         try {
            for(int i = 0; i < memberNames.length; ++i) {
               myevents |= this.addMemberWithoutEvent(memberNames[i], destinations[i]);
            }
         } finally {
            if (myevents != 0) {
               this.addEvent(myevents);
            }

         }

      }
   }

   private int addMemberWithoutEvent(String memberName) {
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(this.getPartitionName());
      BEDeployer localBeDeployer;
      return jmsService != null && (localBeDeployer = jmsService.getBEDeployer()) != null ? this.addMemberWithoutEvent(memberName, localBeDeployer.findBEDestination(memberName)) : 0;
   }

   private int addMemberWithoutEvent(String memberName, BEDestinationImpl destination) {
      DDMember member = this.findMemberByName(memberName);
      if (member != null) {
         if (this.local && destination != null) {
            this.setDestination(member, destination);
            return 2;
         } else {
            return 0;
         }
      } else {
         member = DDManager.removeDeferredMember(memberName);
         if (member == null) {
            member = new DDMember(memberName);
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("DDHandler.addMemberWithoutEvent(" + memberName + ") created new to " + this.name);
            }
         } else if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("DDHandler.addMemberWithoutEvent(" + memberName + ") using deferred member " + member + " to " + this.name);
         }

         synchronized(this.members) {
            this.members.put(memberName, member);
            this.memberList.add(member);
            if (this.local && destination != null) {
               this.setDestination(member, destination);
            }

            if (this.pathServiceJndiName == null) {
               this.improvePathServiceJndiName(member);
            }

            member.setDDHandler(this);
            member.addStatusListener(this);
            DDManager.addDDHandlerMember(memberName, this);
         }

         member = DDManager.removeDeferredMember(memberName);
         if (member != null) {
            this.memberUpdateInternal(member);
         }

         return 1;
      }
   }

   public void addMember(String memberName) {
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(this.getPartitionName());
      BEDeployer localBeDeployer;
      if (jmsService != null && (localBeDeployer = jmsService.getBEDeployer()) != null) {
         this.addMember(memberName, localBeDeployer.findBEDestination(memberName));
      }

   }

   public synchronized void addMember(String memberName, BEDestinationImpl destination) {
      int myevents = this.addMemberWithoutEvent(memberName, destination);
      if (myevents != 0) {
         this.addEvent(myevents);
      }

   }

   public void setMemberWeight(String memberName, int weight) {
      DDMember member = this.findMemberByName(memberName);
      member.setWeight(weight);
   }

   private void setDestination(DDMember member, BEDestinationImpl destination) {
      if (this.pathServiceJndiName == null) {
         this.improvePathServiceJndiName(member);
      }

      if (member.getDestination() == null) {
         member.setDestination(destination);
         this.setIsUOWDestination(member.isUOWDestination());
         if (this.setupForwarding) {
            if (this.isQueue) {
               this.createOrUpdateQueueForwarder(member);
            } else if (this.forwardingPolicy == 1) {
               this.createOrUpdateTopicForwarder(member, destination);
            } else {
               member.setIsForwardingUp(true);
            }
         } else {
            member.setIsForwardingUp(true);
         }

         if ("PathService".equals(this.getUnitOfOrderRouting())) {
            destination.setExtension(new BEUOOQueueState(destination, this));
         }

      }
   }

   public synchronized void shutdownForwarders() {
      try {
         Iterator iter = this.qfmMap.values().iterator();

         while(iter.hasNext()) {
            QueueForwardingManager qfm = (QueueForwardingManager)iter.next();
            qfm.shutdown();
         }

         iter = this.tfmMap.values().iterator();

         while(iter.hasNext()) {
            TopicForwardingManager tfm = (TopicForwardingManager)iter.next();
            tfm.shutdown();
         }
      } catch (Throwable var6) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug(this + ": Unexpected exception when shutting down forwarders for, " + var6);
         }
      } finally {
         this.qfmMap.clear();
         this.tfmMap.clear();
      }

   }

   private void createOrUpdateQueueForwarder(DDMember member) {
      QueueForwardingManager qfm = (QueueForwardingManager)this.qfmMap.get(member.getName());
      if (qfm != null) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug(this + ": Closing old queue forwarder " + qfm + "for " + member.getName());
         }

         qfm.shutdown();
      }

      qfm = new QueueForwardingManager(this, member);
      this.qfmMap.put(member.getName(), qfm);
   }

   private void createOrUpdateTopicForwarder(DDMember member, BEDestinationImpl destination) {
      WorkManager fwm = null;
      if (destination.getBackEnd() != null) {
         fwm = destination.getBackEnd().getForwarderWorkManager();
      }

      TopicForwardingManager tfm = (TopicForwardingManager)this.tfmMap.get(member.getName());
      if (tfm != null) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug(this + ": Closing old topic forwarder " + tfm + " for " + member.getName());
         }

         tfm.shutdown();
      }

      tfm = new TopicForwardingManager(this, member, destination, fwm);
      this.tfmMap.put(member.getName(), tfm);
   }

   public synchronized void removeMember(String memberName) {
      this.addEvent(this.removeMemberWithoutEvent(memberName));
   }

   private int removeMemberWithoutEvent(String memberName) {
      DDManager.removeDDHandlerMember(memberName);
      synchronized(this.members) {
         DDMember member = (DDMember)this.members.remove(memberName);
         if (member != null) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Removing member " + memberName + " from " + this.name);
            }

            this.memberList.remove(member);
            member.removeStatusListener(this);
            member.notAMember();
         }

         return 1;
      }
   }

   public synchronized DistributedDestinationImpl getDDImpl() {
      if (this.ddImpl != null) {
         return this.ddImpl;
      } else {
         String memberName = null;
         JMSServerId backEndId = null;
         JMSID destinationId = null;
         synchronized(this.members) {
            if (!this.members.isEmpty()) {
               DDMember firstMember = (DDMember)this.members.values().iterator().next();
               if (firstMember != null) {
                  synchronized(firstMember) {
                     memberName = firstMember.getName();
                     backEndId = firstMember.getBackEndId();
                     destinationId = firstMember.getDestinationId();
                  }
               }
            }
         }

         DispatcherId dispatcherId = JMSEnvironment.getJMSEnvironment().getLocalDispatcherId();
         this.ddImpl = new DistributedDestinationImpl(this.isQueue ? 1 : 2, "", "", this.name, this.getApplicationName(), this.getEARModuleName(), this.getLoadBalancingPolicyAsInt(), this.getForwardingPolicy(), memberName, this.jndiName, backEndId, destinationId, dispatcherId, false, "", this.safExportPolicy, false, this.getPartitionName());
         return this.ddImpl;
      }
   }

   public DistributedDestinationImpl getDDIByMemberName(String memberName) {
      DDMember member = this.findMemberByName(memberName);

      assert member != null;

      return member.getDDImpl();
   }

   synchronized DDMember findMemberByNameWithDDHandlerLock(String destinationName) {
      return this.findMemberByName(destinationName);
   }

   public DDMember findMemberByName(String destinationName) {
      synchronized(this.members) {
         return (DDMember)this.members.get(destinationName);
      }
   }

   public TopicForwardingManager findTFMByName(String memberName) {
      return (TopicForwardingManager)this.tfmMap.get(memberName);
   }

   public String debugKeys() {
      Set keys = this.members.keySet();
      if (keys == null) {
         return "DDHandler keys are null";
      } else {
         Iterator iterator = this.members.values().iterator();
         String values = "\nvalues:";
         if (iterator.hasNext()) {
            while(iterator.hasNext()) {
               DDMember m = (DDMember)iterator.next();
               values = values + "\n(" + m + ")";
            }
         } else {
            values = "\n no values";
         }

         return keys.toString() + values;
      }
   }

   boolean memberHasSecurityMode(int sm) {
      synchronized(this.members) {
         Iterator iter = this.members.values().iterator();

         DDMember member;
         do {
            if (!iter.hasNext()) {
               return false;
            }

            member = (DDMember)iter.next();
         } while(member.getRemoteSecurityMode() != sm);

         return true;
      }
   }

   public List memberCloneList() {
      List ret = new LinkedList();
      synchronized(this.members) {
         Iterator iter = this.members.values().iterator();

         while(iter.hasNext()) {
            DDMember member = (DDMember)iter.next();

            try {
               ret.add(member.clone());
            } catch (CloneNotSupportedException var7) {
               throw new AssertionError("I can't clone a member");
            }
         }

         return ret;
      }
   }

   public Iterator memberCloneIterator() {
      return this.memberCloneList().listIterator();
   }

   public String toString() {
      return "[DDHandler@" + this.hashCode() + ", name=" + this.name + ", isLocal=" + this.local + ", dd: " + this.ddImpl + " with Members:" + this.debugKeys() + "]";
   }

   public int getNumberOfMembers() {
      synchronized(this.members) {
         return this.members.size();
      }
   }

   public DDMember getMemberByIndex(int index) {
      synchronized(this.members) {
         return (DDMember)this.memberList.get(index);
      }
   }

   public void newDestination(BEDestinationImpl dest) {
      if (this.isLocal()) {
         synchronized(this) {
            DDMember member = this.findMemberByName(dest.getName());
            if (member != null) {
               this.setDestination(member, dest);
            }
         }
      }
   }

   boolean isRemoteUpdatePending() {
      return this.remoteUpdatePending;
   }

   public void setRemoteUpdatePending(boolean remoteUpdatePending) {
      this.remoteUpdatePending = remoteUpdatePending;
   }

   public boolean hashingThrowsOrderException() {
      synchronized(this.members) {
         Iterator iter = this.members.values().iterator();

         int myDeploymentMemberType;
         do {
            if (!iter.hasNext()) {
               return false;
            }

            DDMember member = (DDMember)iter.next();
            myDeploymentMemberType = member.getDeploymentMemberType();
            if ((myDeploymentMemberType & 1536) != 0) {
               return true;
            }

            if ((myDeploymentMemberType & 256) != 0) {
               return false;
            }
         } while((myDeploymentMemberType & 48) == 0);

         return true;
      }
   }

   private static class ListenerObject {
      int toWhat;
      DDStatusListener listener;
      DDHandler newDDHandler = null;

      ListenerObject(DDStatusListener listener, int toWhat) {
         this.toWhat = toWhat;
         this.listener = listener;
      }
   }
}
