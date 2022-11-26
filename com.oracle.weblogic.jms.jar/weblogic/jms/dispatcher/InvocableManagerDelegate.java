package weblogic.jms.dispatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.management.ManagementException;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableManager;

public final class InvocableManagerDelegate {
   private static final String GLOBAL_PARTITION_ID = "0";
   public static final int DISPATCHER_MANAGER = 0;
   public static final int FE_MANAGER = 1;
   public static final int BE_MANAGER = 2;
   public static final int JMS_CONNECTION = 3;
   public static final int JMS_SESSION = 4;
   public static final int JMS_PRODUCER = 5;
   public static final int JMS_CONSUMER = 6;
   public static final int FE_CONNECTION = 7;
   public static final int FE_SESSION = 8;
   public static final int FE_PRODUCER = 9;
   public static final int FE_CONSUMER = 10;
   public static final int FE_BROWSER = 11;
   public static final int FE_ENUMERATION = 12;
   public static final int FE_SEQUENCER = 13;
   public static final int BE_SERVER = 14;
   public static final int BE_CONNECTION = 15;
   public static final int BE_SESSION = 16;
   public static final int BE_CONSUMER = 17;
   public static final int BE_BROWSER = 18;
   public static final int BE_ENUMERATION = 19;
   public static final int BE_DESTINATION = 20;
   public static final int JMS_LEADER = 21;
   public static final int JMS_BROWSER = 22;
   public static final int JMS_CDS = 23;
   public static final int MAXIMUM = 24;
   private static final Map[] SUPERSET_MAPS = createInternalMaps(true);
   private final Map[] INVOCABLE_MAPS = createInternalMaps(false);
   private static final String[] INVOCABLE_STRINGS = new String[]{"Manager", "Manager", "Manager", "Connection", "Session", "Producer", "Consumer", "Connection", "Session", "Producer", "Consumer", "Browser", "Enumeration", "Sequencer", "Back-End", "Connection", "Session", "Consumer", "Browser", "Enumeration", "Destination", "Leader", "Browser", "Manager"};
   private final int[] invocablesHighCount = new int[24];
   private final int[] invocablesTotalCount = new int[24];
   private final weblogic.messaging.dispatcher.Invocable[] managers = new weblogic.messaging.dispatcher.Invocable[24];
   private static final weblogic.messaging.dispatcher.Invocable[] singletonManagers = new weblogic.messaging.dispatcher.Invocable[24];
   private final MyInvocableManager invocableManager;
   private volatile boolean removedFromDelegateMap = false;
   private static final ConcurrentHashMap invocableManagerDelegateMap = new ConcurrentHashMap();
   public static final InvocableManagerDelegate delegate = new InvocableManagerDelegate();
   public static final int INVOCABLE_TYPE_MASK = 255;
   public static final int INVOCABLE_METHOD_MASK = 16776960;
   public static final int FE_BROWSER_CLOSE = 267;
   public static final int FE_BROWSER_CREATE = 520;
   public static final int FE_BROWSER_CREATE_61 = 519;
   public static final int FE_BROWSER_ENUMERATE = 779;
   public static final int FE_CONNECTION_CLOSE = 1031;
   public static final int FE_CONNECTION_CONSUMER_CLOSE = 1287;
   public static final int FE_CONNECTION_CONSUMER_CREATE = 1543;
   public static final int FE_CONNECTION_SET_CLIENT_ID = 1799;
   public static final int FE_CONNECTION_START = 2055;
   public static final int FE_CONNECTION_STOP = 2311;
   public static final int FE_CONSUMER_CLOSE = 2570;
   public static final int FE_CONSUMER_CREATE = 2824;
   public static final int FE_CONSUMER_INCREMENT_WINDOW = 3082;
   public static final int FE_CONSUMER_RECEIVE = 3338;
   public static final int FE_CONSUMER_SET_LISTENER = 3594;
   public static final int FE_DESTINATION_CREATE = 3841;
   public static final int FE_ENUMERATION_NEXT_ELEMENT = 4108;
   public static final int FE_PRODUCER_CLOSE = 4617;
   public static final int FE_PRODUCER_CREATE = 4872;
   public static final int FE_PRODUCER_SEND = 5129;
   public static final int FE_SUBSCRIPTION_REMOVE = 5377;
   public static final int FE_SERVER_SESSION_POOL_CLOSE = 5633;
   public static final int FE_SESSION_POOL_CREATE = 5895;
   public static final int FE_SESSION_ACKNOWLEDGE = 6152;
   public static final int FE_SESSION_CLOSE = 6408;
   public static final int FE_SESSION_CREATE = 6663;
   public static final int FE_SESSION_RECOVER = 6920;
   public static final int FE_SESSION_SET_REDELIVERY_DELAY = 7176;
   public static final int FE_TEMPORARY_DESTINATION_CLOSE = 7431;
   public static final int FE_TEMPORARY_DESTINATION_CREATE = 7687;
   public static final int FE_SERVER_PUSH_MESSAGE = 7937;
   public static final int BE_BROWSER_CLOSE = 8210;
   public static final int BE_BROWSER_CREATE_61 = 8450;
   public static final int BE_BROWSER_CREATE = 8464;
   public static final int BE_BROWSER_ENUMERATE = 8722;
   public static final int BE_CONNECTION_CONSUMER_CLOSE = 8975;
   public static final int BE_CONNECTION_CONSUMER_CREATE = 9218;
   public static final int BE_CONNECTION_START = 9487;
   public static final int BE_CONNECTION_STOP = 9743;
   public static final int BE_CONSUMER_CLOSE = 10001;
   public static final int BE_CONSUMER_CREATE = 10256;
   public static final int BE_CONSUMER_INCREMENT_WINDOW = 10513;
   public static final int BE_CONSUMER_IS_ACTIVE = 10769;
   public static final int BE_CONSUMER_RECEIVE = 11025;
   public static final int BE_CONSUMER_SET_LISTENER = 11281;
   public static final int BE_DESTINATION_CREATE = 11534;
   public static final int BE_ENUMERATION_NEXT_ELEMENT = 11795;
   public static final int BE_PRODUCER_SEND = 12052;
   public static final int BE_SERVER_SESSION_GET = 12302;
   public static final int BE_SERVER_SESSION_POOL_CLOSE = 12558;
   public static final int BE_SERVER_SESSION_POOL_CREATE = 12814;
   public static final int BE_SESSION_ACKNOWLEDGE = 13072;
   public static final int BE_SESSION_CLOSE = 13328;
   public static final int BE_SESSION_CREATE = 13570;
   public static final int BE_SESSION_RECOVER = 13840;
   public static final int BE_SESSION_SET_REDELIVERY_DELAY = 14096;
   public static final int BE_SESSION_START = 14352;
   public static final int BE_SESSION_STOP = 14608;
   public static final int BE_SUBSCRIPTION_REMOVE = 14850;
   public static final int BE_TEMPORARY_DESTINATION_CLOSE = 15118;
   public static final int JMS_PUSH_EXCEPTION = 15360;
   public static final int JMS_PUSH_MESSAGE = 15616;
   public static final int FE_BROWSER_PUSH_EXCEPTION = 15371;
   public static final int JMS_BROWSER_PUSH_EXCEPTION = 15382;
   public static final int FE_CONSUMER_PUSH_EXCEPTION = 15370;
   public static final int JMS_CONSUMER_PUSH_EXCEPTION = 15366;
   public static final int JMS_CONNECTION_PUSH_EXCEPTION = 15363;
   public static final int DSP_HANDSHAKE_HELLO = 15872;
   public static final int BE_DIST_RETRIEVE = 16130;
   public static final int LEADER_BIND_SINGULAR = 16405;
   public static final int LEADER_BIND_SINGULAR_FAILED = 16661;
   public static final int BE_DIST_PARENT_RETRIEVE = 16898;
   public static final int BE_DIST_CONSUMER = 17169;
   public static final int FE_CONSUMER_INCREMENT_WINDOW_ONE_WAY = 17418;
   public static final int BE_TRAN_FORWARD = 17684;
   public static final int BE_UOO_UPDATE = 17940;
   public static final int BE_UOO_UPDATE_PARENT = 18178;
   public static final int DD_MEMBERSHIP_REQUEST = 18455;
   public static final int DD_MEMBERSHIP_PUSH_REQUEST = 18711;
   public static final int DD_MEMBERSHIP_CANCEL_REQUEST = 18967;

   private static final Map[] createInternalMaps(boolean concurrent) {
      return new Map[]{null, null, null, createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), createMap(concurrent), null, createMap(concurrent), null};
   }

   private static Map createMap(boolean concurrent) {
      return (Map)(concurrent ? new ConcurrentHashMap() : new HashMap());
   }

   private InvocableManagerDelegate() {
      this.invocableManager = new MyInvocableManager(this.INVOCABLE_MAPS, SUPERSET_MAPS, INVOCABLE_STRINGS, this.invocablesHighCount, this.invocablesTotalCount, this.managers, singletonManagers);
   }

   public static InvocableManagerDelegate getDelegate(String partitionId) throws JMSException {
      if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("InvocableManagerDelegate.getDelegate(" + partitionId + ")");
      }

      if (partitionId != null && !"0".equals(partitionId)) {
         InvocableManagerDelegate d = (InvocableManagerDelegate)invocableManagerDelegateMap.get(partitionId);
         checkValid(partitionId, d);
         return d;
      } else {
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("InvocableManagerDelegate.getDelegate: returning DOMAIN delegate: " + delegate);
         }

         return delegate;
      }
   }

   public static void checkValid(String debugPartitionId, InvocableManagerDelegate d) throws JMSException {
      if (d == null || d.removedFromDelegateMap) {
         String emsg = "InvocableManager for partition id " + debugPartitionId + " has not been initialized or has shutdown";
         throw new weblogic.jms.common.JMSException(emsg);
      }
   }

   public static InvocableManagerDelegate createDelegate(String partitionId) throws ManagementException {
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManagerDelegate.createDelegate: partitionId: " + partitionId);
      }

      if (partitionId != null && !"0".equals(partitionId)) {
         InvocableManagerDelegate d = null;
         synchronized(invocableManagerDelegateMap) {
            d = (InvocableManagerDelegate)invocableManagerDelegateMap.get(partitionId);
            if (d != null) {
               String emsg = "InvocableManager[@" + d.hashCode() + "] already exists for partition id " + partitionId;
               throw new ManagementException(emsg);
            }

            d = new InvocableManagerDelegate();
            invocableManagerDelegateMap.put(partitionId, d);
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("Created InvocableManager[@" + d.hashCode() + "] for partition id " + partitionId);
         }

         return d;
      } else {
         if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
            JMSDebug.JMSInvocableVerbose.debug("InvocableManagerDelegate.createDelegate: returning DOMAIN delegate: " + delegate);
         }

         return delegate;
      }
   }

   public static void removeDelegate(String partitionId) {
      if (partitionId != null && !"0".equals(partitionId)) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("InvocableManagerDelegate.removeDelegate(" + partitionId + ")");
         }

         InvocableManagerDelegate d = (InvocableManagerDelegate)invocableManagerDelegateMap.remove(partitionId);
         if (d != null) {
            d.removedFromDelegateMap = true;
         }

      }
   }

   public static boolean isBEMethod(int methodID) {
      return methodID >= 8210 && methodID <= 15118;
   }

   public InvocableManager getInvocableManager() {
      return this.invocableManager;
   }

   public void invocableAdd(int invocableType, weblogic.messaging.dispatcher.Invocable invocable) throws JMSException {
      try {
         this.invocableManager.invocableAdd(invocableType, invocable);
      } catch (Exception var4) {
         throw new weblogic.jms.common.JMSException(var4.getMessage(), var4);
      }
   }

   public weblogic.messaging.dispatcher.Invocable invocableFind(int invocableType, ID invocableId) throws JMSException {
      try {
         if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
            JMSDebug.JMSInvocableVerbose.debug("InvocableManagerDelegate.invocableFind: " + this.toString() + " hashCode: " + this.hashCode() + " invocableManager: " + this.invocableManager.toString() + " hashCode: " + this.invocableManager.hashCode());
         }

         return this.invocableManager.invocableFind(invocableType, invocableId);
      } catch (Exception var4) {
         throw new weblogic.jms.common.JMSException(var4.getMessage(), var4);
      }
   }

   public static Map mapForAllParitions(int invocableType) {
      return SUPERSET_MAPS[invocableType];
   }

   public weblogic.messaging.dispatcher.Invocable invocableRemove(int invocableType, ID invocableId) {
      return this.invocableManager.invocableRemove(invocableType, invocableId);
   }

   public Map getInvocableMap(int invocableType) {
      return this.invocableManager.getInvocableMap(invocableType);
   }

   public int getInvocablesCurrentCount(int invocableType) {
      return this.invocableManager.getInvocablesCurrentCount(invocableType);
   }

   public int getInvocablesHighCount(int invocableType) {
      return this.invocableManager.getInvocablesHighCount(invocableType);
   }

   public int getInvocablesTotalCount(int invocableType) {
      return this.invocableManager.getInvocablesTotalCount(invocableType);
   }

   protected boolean isManager(int invocableType) {
      return this.invocableManager.isManager(invocableType);
   }

   static boolean isPushToClient(int invocableType) {
      return ((MyInvocableManager)delegate.getInvocableManager()).isPushToClient(invocableType);
   }

   static int getInvocableTypeMask() {
      delegate.getInvocableManager();
      return 255;
   }

   public void addManager(int invocableType, weblogic.messaging.dispatcher.Invocable invocable) {
      this.invocableManager.addManager(invocableType, invocable);
   }

   public void addSingletonManager(int invocableType, weblogic.messaging.dispatcher.Invocable invocable) {
      this.invocableManager.addSingletonManager(invocableType, invocable);
   }

   static {
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManagerDelegate static initializer: create deletate for partition id 0");
      }

      invocableManagerDelegateMap.put("0", delegate);
   }

   private class MyInvocableManager extends InvocableManager {
      public MyInvocableManager(Map[] INVOCABLE_MAPS, Map[] SUPERSET_MAPS, String[] INVOCABLE_STRINGS, int[] invocablesHighCount, int[] invocablesTotalCount, weblogic.messaging.dispatcher.Invocable[] managers, weblogic.messaging.dispatcher.Invocable[] singletonManagers) {
         super(INVOCABLE_MAPS, SUPERSET_MAPS, INVOCABLE_STRINGS, invocablesHighCount, invocablesTotalCount, managers, singletonManagers);
      }

      public weblogic.messaging.dispatcher.Invocable invocableFind(int invocableType, ID invocableId) throws Exception {
         try {
            return super.invocableFind(invocableType, invocableId);
         } catch (Exception var5) {
            switch (invocableType) {
               case 20:
                  InvalidDestinationException ide = new InvalidDestinationException(var5.getMessage());
                  ide.setLinkedException(var5);
                  throw ide;
               default:
                  throw var5;
            }
         }
      }

      protected boolean isManager(int invocableType) {
         InvocableManagerDelegate var10001 = InvocableManagerDelegate.this;
         return invocableType == 0 | invocableType == 1 | invocableType == 2;
      }

      protected boolean isSingletonManager(int invocableType) {
         InvocableManagerDelegate var10001 = InvocableManagerDelegate.this;
         return invocableType == 0 | invocableType == 21 | invocableType == 23;
      }

      protected boolean isPushToClient(int invocableType) {
         return invocableType == 3 | invocableType == 4 | invocableType == 5 | invocableType == 6 | invocableType == 22;
      }
   }
}
