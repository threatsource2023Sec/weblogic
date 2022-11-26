package weblogic.cluster.leasing.databaseless;

import java.util.ArrayList;
import java.util.List;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.work.WorkManagerFactory;

public final class ClusterState implements HealthFeedback {
   static final String DISCOVERY = "discovery";
   static final String FORMATION = "formation";
   static final String FORMATION_LEADER = "formation_leader";
   static final String STABLE = "stable";
   public static final String STABLE_LEADER = "stable_leader";
   static final String FAILED = "failed";
   private String currentState;
   private final List listeners;
   private HealthState healthState;

   public synchronized boolean setState(String newState) {
      return this.setState(newState, (String)null);
   }

   public synchronized boolean setState(String newState, String reason) {
      String proposed = newState.intern();
      this.setHealthState(newState, reason);
      if (proposed != "discovery" && proposed != "failed") {
         if (this.currentState == null) {
            return proposed != "formation" && proposed != "stable" ? false : this.setInternalState(proposed);
         } else if (this.currentState == "discovery") {
            return proposed != "formation" && proposed != "stable" && proposed != "formation_leader" ? false : this.setInternalState(proposed);
         } else if (this.currentState == "formation_leader") {
            return proposed == "stable_leader" ? this.setInternalState(proposed) : false;
         } else if (this.currentState == "formation") {
            return proposed == "stable" ? this.setInternalState(proposed) : false;
         } else {
            throw new AssertionError("Invalid state transition from " + this.currentState + " to " + newState);
         }
      } else {
         return this.setInternalState(proposed);
      }
   }

   public HealthState getHealthState() {
      return this.healthState;
   }

   private ClusterState() {
      this.listeners = new ArrayList();
      this.healthState = new HealthState(0);
      HealthMonitorService.register("DatabaseLessLeasing", this, true);
   }

   private void setHealthState(String newState, String reason) {
      if (newState != null) {
         if (newState == "failed") {
            Symptom symptom = new Symptom(SymptomType.CLUSTER_ERROR, Severity.HIGH, (String)null, reason);
            this.healthState = new HealthState(3, symptom);
         } else {
            if (this.healthState.getState() == 3 && newState != "failed") {
               this.healthState = new HealthState(0);
            }

         }
      }
   }

   private boolean setInternalState(final String newState) {
      final String oldState = this.currentState;
      this.currentState = newState;
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            synchronized(ClusterState.this.listeners) {
               for(int count = 0; count < ClusterState.this.listeners.size(); ++count) {
                  ((ClusterStateChangeListener)ClusterState.this.listeners.get(count)).stateChanged(oldState, newState);
               }

            }
         }
      });
      return true;
   }

   void addStateChangeListener(ClusterStateChangeListener listener) {
      synchronized(this.listeners) {
         this.listeners.add(listener);
      }
   }

   void removeStateChangeListener(ClusterStateChangeListener listener) {
      synchronized(this.listeners) {
         this.listeners.remove(listener);
      }
   }

   public synchronized String getErrorMessage(String newState) {
      return "unable to transition from " + this.currentState + " to " + newState;
   }

   public static ClusterState getInstance() {
      return ClusterState.Factory.THE_ONE;
   }

   synchronized String getState() {
      return this.currentState;
   }

   // $FF: synthetic method
   ClusterState(Object x0) {
      this();
   }

   private static final class Factory {
      static final ClusterState THE_ONE = new ClusterState();
   }
}
