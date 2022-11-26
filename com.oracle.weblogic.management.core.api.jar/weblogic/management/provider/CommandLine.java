package weblogic.management.provider;

public class CommandLine {
   private static CommandLine singleton = new CommandLine();

   private CommandLine() {
   }

   public static CommandLine getCommandLine() {
      return singleton;
   }

   public String getSSLMinimumProtocolVersion() {
      return System.getProperty("weblogic.security.SSL.minimumProtocolVersion");
   }

   public String getSSLVersion() {
      return System.getProperty("weblogic.security.SSL.protocolVersion");
   }

   public String getSSLEnforcementConstraint() {
      return System.getProperty("weblogic.security.SSL.enforceConstraints");
   }

   public String getSSLTrustCA() {
      return System.getProperty("weblogic.security.SSL.trustedCAKeyStore");
   }

   public String getSSLTrustCAPassPhrase() {
      return System.getProperty("weblogic.security.SSL.trustedCAKeyStorePassPhrase");
   }

   public boolean isJavaEESecurityPermissionsDisabled() {
      return Boolean.getBoolean("weblogic.security.dd.javaEESecurityPermissionsDisabled");
   }

   public String getSubjectManagerClassPropertyName() {
      return "weblogic.security.SecurityServiceManagerDelegate";
   }

   public String getAnonymousAdminLookupEnabledString() {
      return System.getProperty("weblogic.management.anonymousAdminLookupEnabled");
   }

   public long getIdentityAssertionTTLMillis() {
      return Long.getLong("weblogic.security.identityAssertionTTL", 300L) * 1000L;
   }

   public String getKeyStoreFileName() {
      return System.getProperty("weblogic.security.SSL.trustedCAKeyStore");
   }

   public String getSecurityFWSubjectManagerClassNameProp() {
      return "weblogic.security.SubjectManager";
   }

   public String getAdminPKPasswordProp() {
      return "weblogic.management.pkpassword";
   }

   public String getAuditLogDir() {
      return System.getProperty("weblogic.security.audit.auditLogDir");
   }

   public boolean isDDSecurityPermissionDisabled() {
      return Boolean.getBoolean("weblogic.security.dd.permissionSpecDisabled");
   }
}
