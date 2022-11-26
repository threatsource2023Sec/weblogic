package weblogic.management.runtime;

import java.util.Map;

public interface WseePolicyRuntimeMBean extends RuntimeMBean {
   String[] getAvailablePolicies();

   void addPolicies(Map var1);

   void addPolicy(String var1);

   void removePolicy(String var1);
}
