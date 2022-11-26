package weblogic.application.ready;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

class ReadyLifecycleImpl implements ReadyLifecycle {
   private static final String APPLICATION_NAME_DEBUG_STRING = "Application Name = ";
   private static final String PARTITION_NAME_DEBUG_STRING = "Partition Name   = ";
   private static final String REGISTER_METHOD = "Register";
   private static final String UNREGISTER_METHOD = "Unregister";
   private static final String READY_METHOD = "Ready";
   private static final String NOT_READY_METHOD = "Not Ready";
   private static ReadyLifecycleImpl instance = null;
   private Map partitionMap = null;
   private static final String GLOBAL_PARTITION_NAME = "GLOBAL";
   private static boolean evaluateState = true;
   private Map evaluateStateMap = null;
   private Map stateMap = null;
   private int globalState = 0;
   protected static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugReadyApp");

   private ReadyLifecycleImpl() {
      this.partitionMap = Collections.synchronizedMap(new HashMap());
      this.stateMap = Collections.synchronizedMap(new HashMap());
      this.evaluateStateMap = Collections.synchronizedMap(new HashMap());
   }

   protected static synchronized ReadyLifecycle getInstance() {
      if (instance == null) {
         instance = new ReadyLifecycleImpl();
      }

      return instance;
   }

   public void register() {
      String applicationId = this.getAppData().getApplicationId();
      String partitionName = this.getPartitionNameFromApplicationId(applicationId);
      this.registerInternal(applicationId, partitionName);
   }

   public void register(String applicationId) {
      String partitionName = this.getPartitionNameFromApplicationId(applicationId);
      if (applicationId != null) {
         this.registerInternal(applicationId, partitionName);
      } else {
         if (LOGGER.isDebugEnabled()) {
            this.logContextDetails("Register", partitionName, applicationId);
         }

         throw new IllegalStateException("Invalid application id, application id is null");
      }
   }

   private void logContextDetails(String methodName, String partitionName, String applicationId) {
      StringBuilder sb = new StringBuilder();
      sb.append("\nError getting Application Name in " + methodName + " method\n");
      sb.append("Partition Name   = " + partitionName);
      sb.append("\n");
      sb.append("Application Name = " + applicationId);
      sb.append("\n");
      LOGGER.debug(sb.toString());
   }

   private void registerInternal(String appName, String partitionId) {
      String pId = partitionId;
      if (partitionId == null) {
         pId = "GLOBAL";
      }

      synchronized(this.partitionMap) {
         LOGGER.debug("Register called with application name: " + appName);
         LOGGER.debug("Register called with partition name  : " + partitionId);
         Map appMap = this.getAppMap(pId);
         if (appMap.containsKey(appName)) {
            throw new IllegalStateException("Invalid application id, duplicate application id found");
         } else {
            appMap.put(appName, 1);
            evaluateState = true;
            this.evaluateStateMap.put(pId, Boolean.TRUE);
         }
      }
   }

   public void unregister() {
      String applicationId = this.getAppData().getApplicationId();
      this.unregister(applicationId);
   }

   public void unregister(String applicationId) {
      String partitionName = this.getPartitionNameFromApplicationId(applicationId);
      if (applicationId != null) {
         this.unregisterInternal(applicationId, partitionName);
      } else {
         if (LOGGER.isDebugEnabled()) {
            this.logContextDetails("Unregister", partitionName, applicationId);
         }

         throw new IllegalStateException("Invalid application id, application id is null");
      }
   }

   private void unregisterInternal(String appName, String partitionId) {
      synchronized(this.partitionMap) {
         LOGGER.debug("Unregister called with application name: " + appName);
         LOGGER.debug("Unregister called with partition name  : " + partitionId);
         Map appMap = this.getAppMap(partitionId);
         if (appMap.containsKey(appName)) {
            appMap.remove(appName);
            this.evaluateStateMap.put(partitionId, Boolean.TRUE);
         } else {
            LOGGER.debug("Application " + appName + " is not registered with Ready App");
         }

      }
   }

   public void ready() {
      ComponentInvocationContext context = this.getAppData();
      String applicationId = context.getApplicationId();
      String partitionName = this.getPartitionNameFromApplicationId(applicationId);
      if (applicationId != null) {
         this.updateStateInternal(applicationId, partitionName, 0);
      } else {
         if (LOGGER.isDebugEnabled()) {
            this.logContextDetails("Ready", partitionName, applicationId);
         }

         throw new IllegalStateException("Invalid application id, application id is null");
      }
   }

   private void updateStateInternal(String appName, String partitionId, int state) {
      synchronized(this.partitionMap) {
         LOGGER.debug("Update State called with application name: " + appName);
         LOGGER.debug("Update State called with partition name  : " + partitionId);
         LOGGER.debug("Update State called with state           : " + state);
         Map appMap = this.getAppMap(partitionId);
         if (appMap.containsKey(appName)) {
            appMap.put(appName, state);
            evaluateState = true;
            if (this.evaluateStateMap.containsKey(partitionId)) {
               this.evaluateStateMap.put(partitionId, Boolean.TRUE);
            }

         } else {
            throw new IllegalStateException("ApplicationId " + appName + " not registered");
         }
      }
   }

   public void notReady() {
      ComponentInvocationContext context = this.getAppData();
      String applicationId = context.getApplicationId();
      String partitionName = this.getPartitionNameFromApplicationId(applicationId);
      if (applicationId != null) {
         this.updateStateInternal(applicationId, partitionName, 1);
      } else {
         if (LOGGER.isDebugEnabled()) {
            this.logContextDetails("Not Ready", partitionName, applicationId);
         }

         throw new IllegalStateException("Invalid application id, application id is null");
      }
   }

   private Map getAppMap(String partitionId) {
      String pId = "GLOBAL";
      if (partitionId != null) {
         pId = partitionId;
      }

      Map appMap = null;
      if (this.partitionMap.containsKey(pId)) {
         appMap = (Map)this.partitionMap.get(pId);
      } else {
         appMap = new HashMap();
         this.partitionMap.put(pId, appMap);
      }

      return (Map)appMap;
   }

   public int getReadyStatus() {
      ComponentInvocationContext context = this.getAppData();
      String partitionName = this.getPartitionNameFromApplicationId(context.getApplicationId());
      LOGGER.debug("\ngetReadyStatus for partition " + partitionName);
      int currentState = 0;
      if (partitionName.equals("GLOBAL")) {
         if (evaluateState) {
            currentState = this.getGlobalReadyStatus(currentState);
            this.globalState = currentState;
         } else {
            currentState = this.globalState;
         }
      } else {
         int partitionState = 0;
         int gState = 0;
         partitionState = this.determinePartitionStatus(partitionName, partitionState);
         gState = this.determinePartitionStatus("GLOBAL", gState);
         if (partitionState != 0 || gState != 0) {
            currentState = 1;
         }
      }

      this.logMap("Get Ready Status");
      return currentState;
   }

   public int getGlobalRuntimeOnlyStatus() {
      Map appMap = (Map)this.partitionMap.get("GLOBAL");
      return this.getPartitionStatus(0, appMap);
   }

   public int getPartitionOnlyStatus() {
      ComponentInvocationContext context = this.getAppData();
      String partitionName = this.getPartitionNameFromApplicationId(context.getApplicationId());
      Map appMap = (Map)this.partitionMap.get(partitionName);
      return this.getPartitionStatus(0, appMap);
   }

   private int determinePartitionStatus(String partitionName, int currentState) {
      int cState = currentState;
      Boolean partitionEvaluateState = (Boolean)this.evaluateStateMap.get(partitionName);
      LOGGER.debug(partitionName + " evaluate state flag equals " + partitionEvaluateState);
      LOGGER.debug("Found state " + currentState + " for partition " + partitionName);
      if (partitionEvaluateState != null) {
         if (partitionEvaluateState) {
            Map appMap = (Map)this.partitionMap.get(partitionName);
            cState = this.getPartitionStatus(currentState, appMap);
            this.stateMap.put(partitionName, cState);
            LOGGER.debug("Updated state for partition " + partitionName + " to state " + cState);
            this.evaluateStateMap.put(partitionName, Boolean.FALSE);
         } else if (this.stateMap.containsKey(partitionName)) {
            cState = (Integer)this.stateMap.get(partitionName);
         } else {
            cState = 0;
         }
      }

      return cState;
   }

   private int getGlobalReadyStatus(int currentState) {
      int cState = currentState;
      Collection appMapCollection = this.partitionMap.values();

      Map appMap;
      for(Iterator appMapIterator = appMapCollection.iterator(); appMapIterator.hasNext(); cState = this.getPartitionStatus(cState, appMap)) {
         appMap = (Map)appMapIterator.next();
      }

      this.globalState = cState;
      evaluateState = false;
      return cState;
   }

   private int getPartitionStatus(int currentState, Map appMap) {
      int cState = currentState;
      if (appMap != null) {
         Collection values = appMap.values();

         for(Iterator valueIter = values.iterator(); valueIter.hasNext() && cState == 0; cState = (Integer)valueIter.next()) {
         }
      }

      return cState;
   }

   private void logMap(String operation) {
      StringBuilder sb = new StringBuilder();
      sb.append("\n**********************\nReady App Map - Operation: " + operation);
      Collection appMapCollection = this.partitionMap.values();
      Iterator appMapIterator = appMapCollection.iterator();
      Set partitionKeys = this.partitionMap.keySet();
      Iterator partitionKeysIterator = partitionKeys.iterator();

      while(appMapIterator.hasNext()) {
         sb.append("\nPartition Id " + (String)partitionKeysIterator.next());
         Map appMap = (Map)appMapIterator.next();
         Set keys = appMap.keySet();
         Collection values = appMap.values();
         Iterator keyIter = keys.iterator();
         Iterator valueIter = values.iterator();

         for(int i = 0; i < appMap.size(); ++i) {
            sb.append("\nItem " + i + " key: " + (String)keyIter.next() + " value: " + valueIter.next());
         }
      }

      sb.append("\n**********************\n");
      sb.append("Logger Info:\n");
      sb.append("Debug Logger Name         = ").append(LOGGER.getDebugLoggerName()).append("\n");
      sb.append("Is ReadyApp Debug enabled = ").append(LOGGER.isDebugEnabled()).append("\n");
      LOGGER.debug(sb.toString());
   }

   private ComponentInvocationContext getAppData() {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      return manager.getCurrentComponentInvocationContext();
   }

   private String getPartitionNameFromApplicationId(String applicationId) {
      String partitionName = ApplicationVersionUtils.getPartitionName(applicationId);
      if (partitionName == null || "DOMAIN".equals(partitionName)) {
         partitionName = "GLOBAL";
      }

      return partitionName;
   }
}
