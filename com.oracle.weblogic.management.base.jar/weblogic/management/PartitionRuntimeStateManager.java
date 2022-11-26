package weblogic.management;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.server.ServerService;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;

@Contract
@Service
@Singleton
@Named
public class PartitionRuntimeStateManager implements PartitionRuntimeStateManagerContract {
   @Inject
   @Named("DefaultStoreService")
   private ServerService dependencyOnDefaultStoreService;
   private Map partitionStates = new HashMap();

   public synchronized void setPartitionState(String partitionName, String state, String... serverNames) throws ManagementException {
   }

   public synchronized void setResourceGroupState(String partitionName, String resourceGroupName, String state, String... serverNames) throws ManagementException {
   }

   public synchronized String getPartitionState(String partitionName) {
      return "SHUTDOWN";
   }

   public synchronized String getPartitionState(String partitionName, String serverName) {
      return "SHUTDOWN";
   }

   public synchronized String getResourceGroupState(String partitionName, String resourceGroupName, boolean isAdmin) {
      return "SHUTDOWN";
   }

   public synchronized String getResourceGroupState(String partitionName, String resourceGroupName, String serverName, boolean isAdmin) {
      return "SHUTDOWN";
   }

   public synchronized void load(Map statesToUse) throws ManagementException {
   }

   public Map getStates() {
      return this.partitionStates;
   }

   public String getDefaultPartitionState() {
      return "SHUTDOWN";
   }

   public String getDefaultResourceGroupState() {
      return "SHUTDOWN";
   }

   public synchronized void prune(DomainMBean domain) throws ManagementException {
   }

   void setPersistentStore(PersistentStore store) {
   }

   void clear() throws PersistentStoreException {
   }

   @PostConstruct
   protected void init() throws ManagementException {
   }

   void init(PersistentStore pStore) throws ManagementException {
   }

   static String minState(String... stateNames) {
      return null;
   }

   static String calculateState(StateAssignment... stateAssignments) {
      return "SHUTDOWN";
   }

   static class StateAssignment implements Serializable {
      private long time;
      private String state;

      StateAssignment(String state) {
      }

      StateAssignment(String state, long time) {
      }

      void set(String state) {
      }

      String get() {
         return "SHUTDOWN";
      }
   }

   public static class PartitionRunnableState extends RunnableState {
      private PartitionRunnableState(String defaultState) {
         super(defaultState, null);
      }

      private PartitionRunnableState() {
         super((<undefinedtype>)null);
      }

      protected RunnableState getOrCreate(String resourceGroupName) {
         return new RunnableState();
      }

      protected StateAssignment getResourceGroupState(String resourceGroupName) {
         return null;
      }

      protected StateAssignment getResourceGroupState(String resourceGroupName, String serverName) {
         return null;
      }

      private RunnableState get(String resourceGroupName) {
         return null;
      }
   }

   public static class RunnableState implements Serializable {
      private final StateAssignment currentState;
      private Map serverStates;

      private RunnableState(String currentState) {
         this.serverStates = new HashMap();
         this.currentState = new StateAssignment(currentState);
      }

      private RunnableState() {
         this.serverStates = new HashMap();
         this.currentState = new StateAssignment("SHUTDOWN");
      }

      protected synchronized void setState(String state, String... serverNames) throws ManagementException {
      }

      protected synchronized StateAssignment getServerState(String serverName) {
         return this.currentState;
      }

      protected synchronized void setServerState(String serverName, String state) {
      }

      protected synchronized StateAssignment getState() {
         return this.currentState;
      }

      protected Map serverStates() {
         return this.serverStates;
      }

      // $FF: synthetic method
      RunnableState(String x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      RunnableState(Object x0) {
         this();
      }
   }
}
