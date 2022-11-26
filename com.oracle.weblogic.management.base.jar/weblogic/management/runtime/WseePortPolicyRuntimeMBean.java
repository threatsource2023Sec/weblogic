package weblogic.management.runtime;

public interface WseePortPolicyRuntimeMBean extends RuntimeMBean {
   int getTotalViolations();

   int getAuthenticationViolations();

   int getAuthorizationViolations();

   int getConfidentialityViolations();

   int getIntegrityViolations();

   int getAuthenticationSuccesses();

   int getAuthorizationSuccesses();

   int getConfidentialitySuccesses();

   int getIntegritySuccesses();

   int getPolicyFaults();

   /** @deprecated */
   @Deprecated
   int getTotalFaults();

   int getTotalSecurityFaults();
}
