package weblogic.deploy.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Version;
import weblogic.utils.PlatformConstants;

public class DeploymentVersion implements Version {
   private static final long serialVersionUID = 7424059632370370194L;
   private static final String EOL;
   public static final String ARCHIVE_TIMESTAMP = "ARCHIVE_TIMESTAMP";
   public static final String PLAN_TIMESTAMP = "PLAN_TIMESTAMP";
   private String identity;
   private Map versionMap;

   public DeploymentVersion() {
      this.identity = null;
      this.versionMap = null;
      this.versionMap = new LinkedHashMap();
   }

   public DeploymentVersion(String identity, long archiveTimeStamp, long planTimeStamp) {
      this();
      this.identity = identity;
      this.versionMap.put("ARCHIVE_TIMESTAMP", new Long(archiveTimeStamp));
      this.versionMap.put("PLAN_TIMESTAMP", new Long(planTimeStamp));
   }

   public String getIdentity() {
      return this.identity;
   }

   public Map getVersionComponents() {
      synchronized(this.versionMap) {
         return this.versionMap;
      }
   }

   public long getArchiveTimeStamp() {
      return this.getValue("ARCHIVE_TIMESTAMP");
   }

   public long getPlanTimeStamp() {
      return this.getValue("PLAN_TIMESTAMP");
   }

   public static long createTimeStamp() {
      long timeStamp = System.currentTimeMillis();
      timeStamp -= timeStamp % 1000L;
      return timeStamp;
   }

   public void update(boolean configChanged, boolean isControlOperation) {
      long timeStamp = createTimeStamp();
      if (configChanged) {
         this.resetArchiveTimeStamp(timeStamp);
         this.resetPlanTimeStamp(timeStamp);
      } else if (!isControlOperation) {
         this.resetPlanTimeStamp(timeStamp);
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.identity);
      synchronized(this.versionMap) {
         out.writeObject(this.versionMap);
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.identity = (String)in.readObject();
      synchronized(this.versionMap) {
         this.versionMap.putAll((Map)in.readObject());
      }
   }

   private void resetArchiveTimeStamp(long newTimeStamp) {
      synchronized(this.versionMap) {
         this.versionMap.put("ARCHIVE_TIMESTAMP", new Long(newTimeStamp));
      }
   }

   private void resetPlanTimeStamp(long newTimeStamp) {
      synchronized(this.versionMap) {
         this.versionMap.put("PLAN_TIMESTAMP", new Long(newTimeStamp));
      }
   }

   private long getValue(String key) {
      synchronized(this.versionMap) {
         return (Long)this.versionMap.get(key);
      }
   }

   public int hashCode() {
      synchronized(this.versionMap) {
         return this.versionMap.hashCode();
      }
   }

   public boolean equals(Object inObj) {
      if (inObj == null) {
         return false;
      } else if (this == inObj) {
         return true;
      } else if (inObj instanceof DeploymentVersion) {
         Map thisCompMap = this.getVersionComponents();
         Map otherCompMap = ((Version)inObj).getVersionComponents();
         return thisCompMap.equals(otherCompMap);
      } else {
         return false;
      }
   }

   public void pretty(StringBuffer sb) {
      sb.append("Deployment version for \"" + this.identity + "\"" + EOL);
      synchronized(this.versionMap) {
         int lcv = 0;
         Iterator var4 = this.versionMap.entrySet().iterator();

         while(true) {
            if (!var4.hasNext()) {
               break;
            }

            Map.Entry entry = (Map.Entry)var4.next();
            sb.append(lcv + 1 + ". " + (String)entry.getKey() + "=" + entry.getValue() + EOL);
            ++lcv;
         }
      }

      sb.append(EOL);
   }

   public String toString() {
      StringBuffer result = new StringBuffer();
      if (!Debug.isDeploymentDebugConciseEnabled()) {
         result.append(super.toString());
      }

      result.append("(id: ");
      result.append(this.identity);
      result.append(" - versionMap{");
      synchronized(this.versionMap) {
         Iterator var3 = this.versionMap.entrySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               break;
            }

            Map.Entry each = (Map.Entry)var3.next();
            String key = (String)each.getKey();
            result.append(" [Component: ");
            result.append(key);
            result.append(" - version: ");
            result.append(((Long)each.getValue()).toString());
            result.append("]");
         }
      }

      result.append("}").append(")");
      return result.toString();
   }

   static {
      EOL = PlatformConstants.EOL;
   }
}
