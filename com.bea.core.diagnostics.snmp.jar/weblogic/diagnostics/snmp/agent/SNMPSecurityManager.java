package weblogic.diagnostics.snmp.agent;

public interface SNMPSecurityManager {
   int getFailedAuthenticationCount();

   int getFailedAuthorizationCount();

   int getFailedEncryptionCount();

   void invalidateLocalizedKeyCache(String var1);
}
