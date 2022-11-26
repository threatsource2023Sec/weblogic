package weblogic.deploy.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Version;

public final class AggregateDeploymentVersion implements Version {
   private static final long serialVersionUID = 7559709643035775633L;
   private static final String IDENTITY = "Deployments";
   private Map versionComponentsMap = null;

   public AggregateDeploymentVersion() {
      this.versionComponentsMap = new HashMap();
   }

   public static AggregateDeploymentVersion createAggregateDeploymentVersion() {
      return new AggregateDeploymentVersion();
   }

   public static AggregateDeploymentVersion createAggregateDeploymentVersion(Map componentsMap) {
      AggregateDeploymentVersion version = new AggregateDeploymentVersion();
      if (componentsMap != null) {
         Iterator iterator = componentsMap.entrySet().iterator();

         while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            version.addOrUpdateDeploymentVersion((String)entry.getKey(), (DeploymentVersion)entry.getValue());
         }
      }

      return version;
   }

   public String getIdentity() {
      return "Deployments";
   }

   public Map getVersionComponents() {
      synchronized(this.versionComponentsMap) {
         return new HashMap(this.versionComponentsMap);
      }
   }

   public void addOrUpdateDeploymentVersion(String deploymentId, DeploymentVersion version) {
      Version old = null;
      synchronized(this.versionComponentsMap) {
         old = (Version)this.versionComponentsMap.put(deploymentId, version);
      }

      if (Debug.isDeploymentDebugEnabled()) {
         String dbgMsg = null;
         if (old != null) {
            dbgMsg = "AggregateDeploymentVersion: Updated version for id '" + deploymentId + "' from: '" + old + "' to: '" + version + "', '" + this.toString() + "'";
         } else {
            dbgMsg = "AggregateDeploymentVersion: Updated version for id '" + deploymentId + "' from: '" + old + "' to: '" + version + "', '" + this.toString() + "'";
         }

         Debug.deploymentDebug(dbgMsg);
      }

   }

   public void removeDeploymentVersionFor(String deploymentId) {
      synchronized(this.versionComponentsMap) {
         this.versionComponentsMap.remove(deploymentId);
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("AggregateDeploymentVersion: removed id '" + deploymentId + ", " + this.toString() + "'");
      }

   }

   public boolean equals(Object inObj) {
      if (inObj == null) {
         return false;
      } else if (this == inObj) {
         return true;
      } else if (inObj instanceof AggregateDeploymentVersion) {
         Map thisCompMap = this.getVersionComponents();
         Map inCompMap = ((AggregateDeploymentVersion)inObj).getVersionComponents();
         return thisCompMap.equals(inCompMap);
      } else {
         return false;
      }
   }

   public int hashCode() {
      synchronized(this.versionComponentsMap) {
         return this.versionComponentsMap.hashCode();
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (!Debug.isDeploymentDebugConciseEnabled()) {
         sb.append(super.toString());
      }

      sb.append("Aggregated Deployment Version: (");
      sb.append("id: ").append(this.getIdentity());
      sb.append(" - versionComponentsMap{");
      synchronized(this.versionComponentsMap) {
         Iterator iterator = this.versionComponentsMap.entrySet().iterator();

         while(true) {
            if (!iterator.hasNext()) {
               break;
            }

            Map.Entry each = (Map.Entry)iterator.next();
            String id = (String)each.getKey();
            sb.append(" [id: ");
            sb.append(id);
            sb.append(", version: ");
            sb.append(each.getValue().toString());
            sb.append("]");
         }
      }

      sb.append("}").append(")");
      return sb.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      synchronized(this.versionComponentsMap) {
         out.writeObject(this.versionComponentsMap);
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      synchronized(this.versionComponentsMap) {
         this.versionComponentsMap.putAll((Map)in.readObject());
      }
   }
}
