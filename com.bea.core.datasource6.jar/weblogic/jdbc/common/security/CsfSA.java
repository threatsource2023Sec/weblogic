package weblogic.jdbc.common.security;

import java.security.Policy;
import java.util.Properties;
import oracle.security.jps.internal.policystore.JavaPolicyProvider;

public class CsfSA {
   static Throwable th = null;

   public static void getCredential(Properties props) throws Exception {
      if (th != null) {
         throw new Exception("Failed to initialize JPS policy provider", th);
      } else {
         Csf.getCredential(props);
      }
   }

   static {
      try {
         Policy.setPolicy(new JavaPolicyProvider());
      } catch (Throwable var1) {
         th = var1;
      }

   }
}
