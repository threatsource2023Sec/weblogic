package weblogic.deploy.service.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Version;

public final class DomainVersion implements Externalizable {
   private static final long serialVersionUID = 8660184188777148469L;
   private static final String ADDITIONS = "ADDITIONS";
   private static final String DELETIONS = "DELETIONS";
   private static final String CHANGES = "CHANGES";
   private Map deploymentsVersionMap;

   public DomainVersion() {
      this.deploymentsVersionMap = null;
      this.deploymentsVersionMap = Collections.synchronizedMap(new HashMap());
   }

   public DomainVersion(Map givenVersionMap) {
      this();
      this.deploymentsVersionMap.putAll(givenVersionMap);
   }

   public final Map getDeploymentsVersionMap() {
      return this.deploymentsVersionMap;
   }

   public final void addOrUpdateDeploymentVersion(String identity, Version version) {
      Version old = (Version)this.deploymentsVersionMap.put(identity, version);
      if (this.isDebugEnabled()) {
         String dbgMsg = null;
         if (old != null) {
            dbgMsg = super.toString() + ".addOrUpdateDeploymentVersion(): Changed version on - '" + identity + "' from: '" + old + "' to: '" + version + "'";
         } else {
            dbgMsg = super.toString() + ".addOrUpdateDeploymentVersion(): Added version for - '" + identity + "' : '" + version + "'";
         }

         this.debug(dbgMsg);
      }

   }

   public final void removeDeploymentVersion(String componentIdentity) {
      this.deploymentsVersionMap.remove(componentIdentity);
   }

   public final Version getDeploymentVersion(String componentIdentity) {
      return (Version)this.deploymentsVersionMap.get(componentIdentity);
   }

   public final boolean equals(Object inObj) {
      if (this == inObj) {
         return true;
      } else if (inObj instanceof DomainVersion) {
         DomainVersion inV = (DomainVersion)inObj;
         return this.deploymentsVersionMap.equals(inV.getDeploymentsVersionMap());
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.deploymentsVersionMap.hashCode();
   }

   public final DomainVersion getCopy() {
      return new DomainVersion(this.deploymentsVersionMap);
   }

   public final Map getDifferences(DomainVersion version) {
      Map result = new HashMap();
      if (version == null) {
         result.put("ADDITIONS", this.deploymentsVersionMap);
      } else {
         Map additions = new HashMap();
         Map deletions = new HashMap();
         Map changes = new HashMap();
         Map components = version.getDeploymentsVersionMap();
         Iterator iterator;
         String key;
         if (components.size() > this.deploymentsVersionMap.size()) {
            iterator = components.keySet().iterator();

            while(iterator.hasNext()) {
               key = (String)iterator.next();
               if (this.deploymentsVersionMap.containsKey(key)) {
                  if (!components.get(key).equals(this.deploymentsVersionMap.get(key))) {
                     changes.put(key, this.deploymentsVersionMap.get(key));
                  }
               } else {
                  deletions.put(key, components.get(key));
               }
            }
         } else {
            iterator = this.deploymentsVersionMap.keySet().iterator();

            while(iterator.hasNext()) {
               key = (String)iterator.next();
               if (components.containsKey(key)) {
                  if (!this.deploymentsVersionMap.get(key).equals(components.get(key))) {
                     changes.put(key, this.deploymentsVersionMap.get(key));
                  }
               } else {
                  additions.put(key, this.deploymentsVersionMap.get(key));
               }
            }
         }

         result.put("ADDITIONS", additions);
         result.put("DELETIONS", deletions);
         result.put("CHANGES", changes);
      }

      return result;
   }

   public DomainVersion getFilteredVersion(Set handlers) {
      DomainVersion dv = this;
      if (handlers != null && !handlers.equals(this.deploymentsVersionMap.keySet())) {
         dv = this.getCopy();
         dv.getDeploymentsVersionMap().keySet().retainAll(handlers);
      }

      return dv;
   }

   public final String toString() {
      StringBuffer stringBuf = new StringBuffer();
      stringBuf.append(super.toString()).append(": [");
      Iterator iterator = this.deploymentsVersionMap.keySet().iterator();

      while(iterator.hasNext()) {
         String key = (String)iterator.next();
         stringBuf.append(" { Deployment: ");
         stringBuf.append("'");
         stringBuf.append(key);
         stringBuf.append("'");
         stringBuf.append(" : v = '");
         stringBuf.append(this.deploymentsVersionMap.get(key));
         stringBuf.append("' }, ");
      }

      stringBuf.append("]");
      return stringBuf.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.deploymentsVersionMap);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.deploymentsVersionMap.putAll((Map)in.readObject());
   }

   private boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }

   private void debug(String msg) {
      Debug.serviceDebug(msg);
   }
}
