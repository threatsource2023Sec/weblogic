package weblogic.security;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class SecurityMessagesTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public SecurityMessagesTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.security.SecurityMessagesTextLocalizer", SecurityMessagesTextFormatter.class.getClassLoader());
   }

   public SecurityMessagesTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.security.SecurityMessagesTextLocalizer", SecurityMessagesTextFormatter.class.getClassLoader());
   }

   public static SecurityMessagesTextFormatter getInstance() {
      return new SecurityMessagesTextFormatter();
   }

   public static SecurityMessagesTextFormatter getInstance(Locale l) {
      return new SecurityMessagesTextFormatter(l);
   }

   public String getPasswordPromptMessage() {
      String id = "PasswordPromptMessage";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordPromptMessageRenter() {
      String id = "PasswordPromptMessageRenter";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernamePromptMessage() {
      String id = "UsernamePromptMessage";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallbackWarningMessage() {
      String id = "CallbackWarningMessage";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallbackErrorMessage() {
      String id = "CallbackErrorMessage";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNewAdministrativeUserPassCreate() {
      String id = "getNewAdminstrativeUserPassCreated";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminUserTooShort() {
      String id = "getAdminUserTooShort";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminPassTooShort() {
      String id = "getAdminPassTooShort";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordsNoMatch() {
      String id = "PassNoMatch";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordsNoMatchBoom() {
      String id = "PassNoMatchBoom";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTryAgainMessage() {
      String id = "TryAgainPassNoMatchBoom";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedCreateAdminUser() {
      String id = "failCreateAdminUser";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSSLClientTrustKeyStoreConfigError() {
      String id = "SSLClientTrustKeyStoreConfigError";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSSLClientTrustKeyStoreSyntax() {
      String id = "SSLClientTrustKeyStoreSyntax";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCATrustPasswordPromptMessage(String arg0) {
      String id = "CATrustPasswordPromptMessage";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreatePrompt(String arg0, String arg1) {
      String id = "UserKeyConfigCreatePrompt";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreateAffirmative() {
      String id = "UserKeyConfigCreateAffirmative";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreateNegative() {
      String id = "UserKeyConfigCreateNegative";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreateConfig(String arg0, String arg1) {
      String id = "UserKeyConfigCreateConfirm";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreateFailure() {
      String id = "UserKeyConfigCreateFailure";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsingExistingKeyFile() {
      String id = "UsingExistingKeyFile";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserKeyConfigCreateNoPrompt() {
      String id = "UserKeyConfigCreateNoPrompt";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeNotPossible(String arg0, String arg1) {
      String id = "SecurityPre90UpgradeNotPossible";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeNameSolution() {
      String id = "SecurityPre90UpgradeNameSolution";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMissingObjectNameProblem() {
      String id = "SecurityPre90UpgradeMissingObjectNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeDuplicateObjectNameProblem(String arg0) {
      String id = "SecurityPre90UpgradeDuplicateObjectNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMissingRealmChildProblem(String arg0, String arg1) {
      String id = "SecurityPre90UpgradeMissingRealmChildProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeRealmChildRefersToAnotherRealmProblem(String arg0, String arg1, String arg2) {
      String id = "SecurityPre90UpgradeRealmChildRefersToAnotherRealmProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeRealmChildRefersToNoRealmProblem(String arg0, String arg1) {
      String id = "SecurityPre90UpgradeRealmChildRefersToNoRealmProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeUnreferencedRealmChildProblemProblem(String arg0) {
      String id = "SecurityPre90UpgradeUnreferencedRealmChildProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeNonStandardRealmChildObjectNameProblem(String arg0, String arg1, String arg2, String arg3) {
      String id = "SecurityPre90UpgradeNonStandardRealmChildObjectNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeNonStandardRealmObjectNameProblem(String arg0, String arg1, String arg2) {
      String id = "SecurityPre90UpgradeNonStandardRealmObjectNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeDuplicateRealmChildDisplayNameProblem(String arg0, String arg1, String arg2) {
      String id = "SecurityPre90UpgradeDuplicateRealmChildDisplayNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeDuplicateRealmChildReferencesProblem(String arg0, String arg1, String arg2) {
      String id = "SecurityPre90UpgradeDuplicateRealmChildReferencesProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeDuplicateRealmDisplayNameProblem(String arg0) {
      String id = "SecurityPre90UpgradeDuplicateRealmDisplayNameProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeRealmCertPathBuilderNotChildProblem(String arg0, String arg1) {
      String id = "SecurityPre90UpgradeRealmCertPathBuilderNotChildProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeRealmCertPathBuilderNotChildSolution() {
      String id = "SecurityPre90UpgradeRealmCertPathBuilderNotChildSolution";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMultipleDefaultRealmsProblem(String arg0, String arg1) {
      String id = "SecurityPre90UpgradeMultipleDefaultRealmsProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMultipleDefaultRealmsSolution() {
      String id = "SecurityPre90UpgradeMultipleDefaultRealmsSolution";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMissingUserLockoutManagerProblem(String arg0) {
      String id = "SecurityPre90UpgradeMissingUserLockoutManagerProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeMissingUserLockoutManagerSolution() {
      String id = "SecurityPre90UpgradeMissingUserLockoutManagerSolution";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeUseDeprecatedWebResourceProblem(String arg0) {
      String id = "SecurityPre90UpgradeUseDeprecatedWebResourceProblem";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityPre90UpgradeUseDeprecatedWebResourceSolution() {
      String id = "SecurityPre90UpgradeUseDeprecatedWebResourceSolution";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownSecurityProviderTypeError(String arg0, String arg1, String arg2) {
      String id = "UnknownSecurityProviderTypeError";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmptyActiveTypeError() {
      String id = "EmptyActiveTypeError";
      String subsystem = "Security Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSelfSignedCertificateInChainError(String arg0) {
      String id = "SelfSignedCertificateInChainError";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCertificateNotSignedByIssuerError(String arg0, String arg1) {
      String id = "CertificateNotSignedByIssuerError";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIssuerDNMismatchError(String arg0, String arg1) {
      String id = "IssuerDNMismatchError";
      String subsystem = "Security Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
