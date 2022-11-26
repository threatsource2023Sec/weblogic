package weblogic.connector.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.connector.compliance.RAComplianceTextFormatter;

public interface ValidationMessage {
   RAComplianceTextFormatter RAComplianceTextMsg = RAComplianceTextFormatter.getInstance();
   String SUB_COMPONENT_ADAPTER = "Adapter Bean";
   String SUB_COMPONENT_POOL = "Connection Pool";
   String SUB_COMPONENT_POOL_RUNTIME = "Connection Pool in Runtime";
   String SUB_COMPONENT_POOLS = "Connection Pools Group";
   String SUB_COMPONENT_AO = "Admin Object";
   String SUB_COMPONENT_AO_RUNTIME = "Admin Object in Runtime";
   String SUB_COMPONENT_AOS = "Admin Objects Group";
   String SUB_COMPONENT_GENERAL = "General";
   List CRITICAL_SUB_COMPONENTS = Collections.unmodifiableList(new LinkedList() {
      private static final long serialVersionUID = 1L;

      {
         this.add("Adapter Bean");
         this.add("Admin Object");
         this.add("Admin Objects Group");
         this.add("General");
      }
   });
   Map SUB_COMPONENTS_CHILD2PARENT_MAP = Collections.unmodifiableMap(new HashMap() {
      private static final long serialVersionUID = 12312364561L;

      {
         this.put("Connection Pool", new String[]{"Connection Pools Group", "Connection Pool in Runtime"});
         this.put("Admin Object", new String[]{"Admin Objects Group", "Admin Object in Runtime"});
      }
   });
   String KEY_GENERAL = "General";
   String KEY_SEPERATOR = ";;;;";

   List getWarnings();

   List getErrors();

   void error(String var1, String var2, String var3, int var4);

   List getCriticalErrors();

   boolean hasCriticalError();

   List getNonCriticalErrors();

   List getErrorsOfMessageKey(SubComponentAndKey var1);

   void clearErrorsOfMessageKey(SubComponentAndKey var1);

   public static class SubComponentAndKey implements Comparable {
      public String subComponent;
      public String key;

      public SubComponentAndKey(String subComponent, String key) {
         this.subComponent = subComponent;
         this.key = key;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + this.subComponent.hashCode();
         result = 31 * result + this.key.hashCode();
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
            SubComponentAndKey other = (SubComponentAndKey)obj;
            return this.key != null && this.key.length() > 0 && this.equals(other.subComponent, other.key);
         }
      }

      public boolean equals(String theSubComponent, String theKey) {
         return !this.subComponent.equals(theSubComponent) ? false : this.key.equals(theKey);
      }

      public String toString() {
         return "(" + this.subComponent + "-" + this.key + ")";
      }

      public int compareTo(SubComponentAndKey me) {
         return !this.subComponent.equals(me.subComponent) ? this.subComponent.compareTo(me.subComponent) : this.key.compareTo(me.key);
      }
   }
}
