package weblogic.cluster.migration;

import java.io.Serializable;
import java.util.Map;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;

public interface DynamicLoadbalancer {
   String getName();

   Map adjustServices(Map var1, ClusterMBean var2, ServerMBean var3, Map var4);

   Map onFailure(Map var1, Map var2, Map var3, Map var4);

   void onSuccess();

   public static enum Action {
      MIGRATE,
      ACTIVATE,
      DEACTIVATE,
      NO_OP;
   }

   public static class ActionItem implements Serializable {
      private static final long serialVersionUID = -2041318985699438387L;
      private final Action action;
      private final String targetServer;

      public ActionItem(Action action, String targetServer) {
         this.action = action;
         this.targetServer = targetServer;
      }

      public Action getAction() {
         return this.action;
      }

      public String getTargetServer() {
         return this.targetServer;
      }

      public String toString() {
         return "ActionItem[" + this.action + " - " + this.targetServer + "]";
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.action == null ? 0 : this.action.hashCode());
         result = 31 * result + (this.targetServer == null ? 0 : this.targetServer.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ActionItem other = (ActionItem)obj;
            if (this.action != other.action) {
               return false;
            } else {
               if (this.targetServer == null) {
                  if (other.targetServer != null) {
                     return false;
                  }
               } else if (!this.targetServer.equals(other.targetServer)) {
                  return false;
               }

               return true;
            }
         }
      }
   }

   public static enum State {
      ACTIVE,
      INACTIVE_FAILED_CRITICAL,
      INACTIVE_FAILED_UNHEALTHY,
      INACTIVE_SERVER_SHUTDOWN,
      INACTIVE_MANAGED,
      INACTIVE_OTHERS;
   }

   public static enum CRITICAL {
      YES,
      NO,
      NOTREADY;
   }

   public static class ServiceStatus implements Serializable {
      private static final long serialVersionUID = 1259344963807555357L;
      private final State state;
      private final String server;

      public ServiceStatus(State state, String server) {
         this.state = state;
         this.server = server;
      }

      public State getState() {
         return this.state;
      }

      public String getHostingServer() {
         return this.server;
      }

      public String toString() {
         return "ServiceStatus[" + this.state + " - " + this.server + "]";
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.server == null ? 0 : this.server.hashCode());
         result = 31 * result + (this.state == null ? 0 : this.state.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ServiceStatus other = (ServiceStatus)obj;
            if (this.server == null) {
               if (other.server != null) {
                  return false;
               }
            } else if (!this.server.equals(other.server)) {
               return false;
            }

            return this.state == other.state;
         }
      }
   }
}
