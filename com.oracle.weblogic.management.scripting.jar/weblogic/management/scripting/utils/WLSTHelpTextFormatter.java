package weblogic.management.scripting.utils;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class WLSTHelpTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public WLSTHelpTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.scripting.utils.WLSTHelpTextLocalizer", WLSTHelpTextFormatter.class.getClassLoader());
   }

   public WLSTHelpTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.scripting.utils.WLSTHelpTextLocalizer", WLSTHelpTextFormatter.class.getClassLoader());
   }

   public static WLSTHelpTextFormatter getInstance() {
      return new WLSTHelpTextFormatter();
   }

   public static WLSTHelpTextFormatter getInstance(Locale l) {
      return new WLSTHelpTextFormatter(l);
   }

   public String getMe() {
      String id = "getMe";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMOShortDescription() {
      String id = "cmoShortDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainNameShortDescription() {
      String id = "DomainNameShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameShortDescription() {
      String id = "ServerNameShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedShortDescription() {
      String id = "ConnectedShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserNameShortDescription() {
      String id = "UserNameShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getisAdminServerShortDescription() {
      String id = "isAdminServerShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionShortDescription() {
      String id = "VersionShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingShortDescription() {
      String id = "RecordingShortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExitonerror() {
      String id = "Exitonerror";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCMOSyntax() {
      String id = "cmoSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCmoDescription() {
      String id = "cmoDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCmoExample() {
      String id = "cmoExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDNSyntax() {
      String id = "dnSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDNDescription() {
      String id = "dnDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDNExample() {
      String id = "dnExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSNSyntax() {
      String id = "snSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSNDescription() {
      String id = "snDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSNExample() {
      String id = "snExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUNSyntax() {
      String id = "unSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUNDescription() {
      String id = "unDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUNExample() {
      String id = "unExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIsAdminServerSyntax() {
      String id = "isAsSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String isAdminServerDescription() {
      String id = "isAsDescr";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIsAdminServerExample() {
      String id = "isASExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionSyntax() {
      String id = "versionSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionDescription() {
      String id = "vDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionExample() {
      String id = "vEx";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingSyntax() {
      String id = "recordingSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingDescription() {
      String id = "rDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingExample() {
      String id = "rExample";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEOESyntax() {
      String id = "eoeSyntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEOEDescription() {
      String id = "eoeDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEOEExample() {
      String id = "eoeex";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTMainDescription() {
      String id = "wlstDesc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpAll() {
      String id = "helpAll";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHall() {
      String id = "hall";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpBrowse() {
      String id = "hbrowse";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpBrowsee() {
      String id = "helpBrowsee";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpControl() {
      String id = "hControl";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpDeploy() {
      String id = "hdeploy";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpDiagnostic() {
      String id = "hdiagnostic";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpEdit() {
      String id = "hedit";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpInfo() {
      String id = "hinfo";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpLC() {
      String id = "hlc";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpVar() {
      String id = "hvar";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHControl() {
      String id = "hcontrol";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHLifeCycle() {
      String id = "hLifeCycle";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHbr() {
      String id = "hbr";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHCont() {
      String id = "hcont";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHinf() {
      String id = "hinf";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHdep() {
      String id = "hdep";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHAl() {
      String id = "hal";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHV() {
      String id = "hv";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cd_shortDescription() {
      String id = "cd_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cd_description() {
      String id = "cd_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cd_syntax() {
      String id = "cd_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_currentTree_shortDescription() {
      String id = "currentTree_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_currentTree_description() {
      String id = "currentTree_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_currentTree_syntax() {
      String id = "currentTree_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_prompt_shortDescription() {
      String id = "prompt_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_prompt_description() {
      String id = "prompt_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_prompt_syntax() {
      String id = "prompt_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pwd_shortDescription() {
      String id = "pwd_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pwd_description() {
      String id = "pwd_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pwd_syntax() {
      String id = "pwd_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommandGroup_shortDescription() {
      String id = "addHelpCommandGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommandGroup_description() {
      String id = "addHelpCommandGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommandGroup_syntax() {
      String id = "addHelpCommandGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommand_shortDescription() {
      String id = "addHelpCommand_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommand_description() {
      String id = "addHelpCommand_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addHelpCommand_syntax() {
      String id = "addHelpCommand_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addTemplate_shortDescription() {
      String id = "addTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addTemplate_description() {
      String id = "addTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addTemplate_syntax() {
      String id = "addTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeDomain_shortDescription() {
      String id = "closeDomain_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeDomain_description() {
      String id = "closeDomain_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeDomain_syntax() {
      String id = "closeDomain_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeTemplate_shortDescription() {
      String id = "closeTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeTemplate_description() {
      String id = "closeTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closeTemplate_syntax() {
      String id = "closeTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createDomain_shortDescription() {
      String id = "createDomain_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createDomain_description() {
      String id = "createDomain_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createDomain_syntax() {
      String id = "createDomain_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validateConfig_shortDescription() {
      String id = "validateConfig_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validateConfig_description() {
      String id = "validateConfig_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validateConfig_syntax() {
      String id = "validateConfig_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setDistDestType_shortDescription() {
      String id = "setDistDestType_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setDistDestType_description() {
      String id = "setDistDestType_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setDistDestType_syntax() {
      String id = "setDistDestType_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setSharedSecretStoreWithPassword_shortDescription() {
      String id = "setSharedSecretStoreWithPassword_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setSharedSecretStoreWithPassword_description() {
      String id = "setSharedSecretStoreWithPassword_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setSharedSecretStoreWithPassword_syntax() {
      String id = "setSharedSecretStoreWithPassword_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_clone_shortDescription() {
      String id = "clone_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_clone_description() {
      String id = "clone_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_clone_syntax() {
      String id = "clone_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerHome_shortDescription() {
      String id = "getNodeManagerHome_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerHome_description() {
      String id = "getNodeManagerHome_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerHome_syntax() {
      String id = "getNodeManagerHome_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getOldNodeManagerHome_shortDescription() {
      String id = "getOldNodeManagerHome_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getOldNodeManagerHome_description() {
      String id = "getOldNodeManagerHome_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getOldNodeManagerHome_syntax() {
      String id = "getOldNodeManagerHome_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerType_shortDescription() {
      String id = "getNodeManagerType_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerType_description() {
      String id = "getNodeManagerType_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerType_syntax() {
      String id = "getNodeManagerType_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeType_shortDescription() {
      String id = "getNodeManagerUpgradeType_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeType_description() {
      String id = "getNodeManagerUpgradeType_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeType_syntax() {
      String id = "getNodeManagerUpgradeType_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeOverwriteDefault_shortDescription() {
      String id = "getNodeManagerUpgradeOverwriteDefault_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeOverwriteDefault_description() {
      String id = "getNodeManagerUpgradeOverwriteDefault_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getNodeManagerUpgradeOverwriteDefault_syntax() {
      String id = "getNodeManagerUpgradeOverwriteDefault_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String storeKeyStorePassword_shortDescription() {
      String id = "storeKeyStorePassword_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeKeyStorePassword_description() {
      String id = "storeKeyStorePassword_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeKeyStorePassword_syntax() {
      String id = "storeKeyStorePassword_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_connect_shortDescription() {
      String id = "connect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_connect_description() {
      String id = "connect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_connect_syntax() {
      String id = "connect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disconnect_shortDescription() {
      String id = "disconnect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disconnect_description() {
      String id = "disconnect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disconnect_syntax() {
      String id = "disconnect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exit_shortDescription() {
      String id = "exit_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exit_description() {
      String id = "exit_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exit_syntax() {
      String id = "exit_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomain_shortDescription() {
      String id = "readDomain_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomain_description() {
      String id = "readDomain_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomain_syntax() {
      String id = "readDomain_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomainForUpgrade_shortDescription() {
      String id = "readDomainForUpgrade_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomainForUpgrade_description() {
      String id = "readDomainForUpgrade_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readDomainForUpgrade_syntax() {
      String id = "readDomainForUpgrade_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplate_shortDescription() {
      String id = "readTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplate_description() {
      String id = "readTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplate_syntax() {
      String id = "readTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplateForUpdate_shortDescription() {
      String id = "readTemplateForUpdate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplateForUpdate_description() {
      String id = "readTemplateForUpdate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_readTemplateForUpdate_syntax() {
      String id = "readTemplateForUpdate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectTemplate_shortDescription() {
      String id = "selectTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectTemplate_description() {
      String id = "selectTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectTemplate_syntax() {
      String id = "selectTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectCustomTemplate_shortDescription() {
      String id = "selectCustomTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectCustomTemplate_description() {
      String id = "selectCustomTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_selectCustomTemplate_syntax() {
      String id = "selectCustomTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectTemplate_shortDescription() {
      String id = "unselectTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectTemplate_description() {
      String id = "unselectTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectTemplate_syntax() {
      String id = "unselectTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectCustomTemplate_shortDescription() {
      String id = "unselectCustomTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectCustomTemplate_description() {
      String id = "unselectCustomTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unselectCustomTemplate_syntax() {
      String id = "unselectCustomTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setTopologyProfile_shortDescription() {
      String id = "setTopologyProfile_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setTopologyProfile_description() {
      String id = "setTopologyProfile_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setTopologyProfile_syntax() {
      String id = "setTopologyProfile_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadTemplates_shortDescription() {
      String id = "loadTemplates_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadTemplates_description() {
      String id = "loadTemplates_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadTemplates_syntax() {
      String id = "loadTemplates_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showTemplates_shortDescription() {
      String id = "showTemplates_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showTemplates_description() {
      String id = "showTemplates_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showTemplates_syntax() {
      String id = "showTemplates_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showAvailableTemplates_shortDescription() {
      String id = "showAvailableTemplates_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showAvailableTemplates_description() {
      String id = "showAvailableTemplates_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showAvailableTemplates_syntax() {
      String id = "showAvailableTemplates_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_consolidateDatasources_shortDescription() {
      String id = "consolidateDatasources_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_consolidateDatasources_description() {
      String id = "consolidateDatasources_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_consolidateDatasources_syntax() {
      String id = "consolidateDatasources_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvConfigurable_shortDescription() {
      String id = "isASMAutoProvConfigurable_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvConfigurable_description() {
      String id = "isASMAutoProvConfigurable_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvConfigurable_syntax() {
      String id = "isASMAutoProvConfigurable_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvSet_shortDescription() {
      String id = "isASMAutoProvSet_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvSet_description() {
      String id = "isASMAutoProvSet_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvSet_syntax() {
      String id = "isASMAutoProvSet_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvEnabled_shortDescription() {
      String id = "isASMAutoProvEnabled_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvEnabled_description() {
      String id = "isASMAutoProvEnabled_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMAutoProvEnabled_syntax() {
      String id = "isASMAutoProvEnabled_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMAutoProv_shortDescription() {
      String id = "enableASMAutoProv_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMAutoProv_description() {
      String id = "enableASMAutoProv_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMAutoProv_syntax() {
      String id = "enableASMAutoProv_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMDBBasisEnabled_shortDescription() {
      String id = "isASMDBBasisEnabled_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMDBBasisEnabled_description() {
      String id = "isASMDBBasisEnabled_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isASMDBBasisEnabled_syntax() {
      String id = "isASMDBBasisEnabled_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMDBBasis_shortDescription() {
      String id = "enableASMDBBasis_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMDBBasis_description() {
      String id = "enableASMDBBasis_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableASMDBBasis_syntax() {
      String id = "enableASMDBBasis_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogPersistenceConfigurable_shortDescription() {
      String id = "isJTATLogPersistenceConfigurable_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogPersistenceConfigurable_description() {
      String id = "isJTATLogPersistenceConfigurable_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogPersistenceConfigurable_syntax() {
      String id = "isJTATLogPersistenceConfigurable_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceSet_shortDescription() {
      String id = "isJTATLogDBPersistenceSet_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceSet_description() {
      String id = "isJTATLogDBPersistenceSet_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceSet_syntax() {
      String id = "isJTATLogDBPersistenceSet_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceEnabled_shortDescription() {
      String id = "isJTATLogDBPersistenceEnabled_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceEnabled_description() {
      String id = "isJTATLogDBPersistenceEnabled_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJTATLogDBPersistenceEnabled_syntax() {
      String id = "isJTATLogDBPersistenceEnabled_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJTATLogDBPersistence_shortDescription() {
      String id = "enableJTATLogDBPersistence_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJTATLogDBPersistence_description() {
      String id = "enableJTATLogDBPersistence_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJTATLogDBPersistence_syntax() {
      String id = "enableJTATLogDBPersistence_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStorePersistenceConfigurable_shortDescription() {
      String id = "isJMSStorePersistenceConfigurable_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStorePersistenceConfigurable_description() {
      String id = "isJMSStorePersistenceConfigurable_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStorePersistenceConfigurable_syntax() {
      String id = "isJMSStorePersistenceConfigurable_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceSet_shortDescription() {
      String id = "isJMSStoreDBPersistenceSet_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceSet_description() {
      String id = "isJMSStoreDBPersistenceSet_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceSet_syntax() {
      String id = "isJMSStoreDBPersistenceSet_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceEnabled_shortDescription() {
      String id = "isJMSStoreDBPersistenceEnabled_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceEnabled_description() {
      String id = "isJMSStoreDBPersistenceEnabled_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isJMSStoreDBPersistenceEnabled_syntax() {
      String id = "isJMSStoreDBPersistenceEnabled_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJMSStoreDBPersistence_shortDescription() {
      String id = "enableJMSStoreDBPersistence_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJMSStoreDBPersistence_description() {
      String id = "enableJMSStoreDBPersistence_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableJMSStoreDBPersistence_syntax() {
      String id = "enableJMSStoreDBPersistence_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_initializejdbcstores_shortDescription() {
      String id = "initializejdbcstores_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_initializejdbcstores_description() {
      String id = "initializejdbcstores_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_initializejdbcstores_syntax() {
      String id = "initializejdbcstores_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateDomain_shortDescription() {
      String id = "updateDomain_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateDomain_description() {
      String id = "updateDomain_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateDomain_syntax() {
      String id = "updateDomain_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeDomain_shortDescription() {
      String id = "writeDomain_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeDomain_description() {
      String id = "writeDomain_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeDomain_syntax() {
      String id = "writeDomain_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeTemplate_shortDescription() {
      String id = "writeTemplate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeTemplate_description() {
      String id = "writeTemplate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeTemplate_syntax() {
      String id = "writeTemplate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getStartupGroup_shortDescription() {
      String id = "getStartupGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getStartupGroup_description() {
      String id = "getStartupGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getStartupGroup_syntax() {
      String id = "getStartupGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addStartupGroup_shortDescription() {
      String id = "addStartupGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addStartupGroup_description() {
      String id = "addStartupGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addStartupGroup_syntax() {
      String id = "addStartupGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteStartupGroup_shortDescription() {
      String id = "deleteStartupGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteStartupGroup_description() {
      String id = "deleteStartupGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteStartupGroup_syntax() {
      String id = "deleteStartupGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setStartupGroup_shortDescription() {
      String id = "setStartupGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setStartupGroup_description() {
      String id = "setStartupGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setStartupGroup_syntax() {
      String id = "setStartupGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getDatabaseDefaults_shortDescription() {
      String id = "getDatabaseDefaults_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getDatabaseDefaults_description() {
      String id = "getDatabaseDefaults_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getDatabaseDefaults_syntax() {
      String id = "getDatabaseDefaults_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listServerGroups_shortDescription() {
      String id = "listServerGroups_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listServerGroups_description() {
      String id = "listServerGroups_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listServerGroups_syntax() {
      String id = "listServerGroups_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getServerGroups_shortDescription() {
      String id = "getServerGroups_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getServerGroups_description() {
      String id = "getServerGroups_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getServerGroups_syntax() {
      String id = "getServerGroups_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setServerGroups_shortDescription() {
      String id = "setServerGroups_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setServerGroups_description() {
      String id = "setServerGroups_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setServerGroups_syntax() {
      String id = "setServerGroups_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deploy_shortDescription() {
      String id = "deploy_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deploy_description() {
      String id = "deploy_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deploy_syntax() {
      String id = "deploy_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_distributeApplication_shortDescription() {
      String id = "distributeApplication_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_distributeApplication_description() {
      String id = "distributeApplication_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_distributeApplication_syntax() {
      String id = "distributeApplication_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getWLDM_shortDescription() {
      String id = "getWLDM_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getWLDM_description() {
      String id = "getWLDM_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getWLDM_syntax() {
      String id = "getWLDM_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadApplication_shortDescription() {
      String id = "loadApplication_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadApplication_description() {
      String id = "loadApplication_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadApplication_syntax() {
      String id = "loadApplication_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redeploy_shortDescription() {
      String id = "redeploy_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redeploy_description() {
      String id = "redeploy_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redeploy_syntax() {
      String id = "redeploy_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startApplication_shortDescription() {
      String id = "startApplication_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startApplication_description() {
      String id = "startApplication_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startApplication_syntax() {
      String id = "startApplication_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopApplication_shortDescription() {
      String id = "stopApplication_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopApplication_description() {
      String id = "stopApplication_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopApplication_syntax() {
      String id = "stopApplication_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undeploy_shortDescription() {
      String id = "undeploy_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undeploy_description() {
      String id = "undeploy_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undeploy_syntax() {
      String id = "undeploy_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateApplication_shortDescription() {
      String id = "updateApplication_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateApplication_description() {
      String id = "updateApplication_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_updateApplication_syntax() {
      String id = "updateApplication_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_appendToExtensionLoader_shortDescription() {
      String id = "appendToExtensionLoader_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_appendToExtensionLoader_description() {
      String id = "appendToExtensionLoader_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_appendToExtensionLoader_syntax() {
      String id = "appendToExtensionLoader_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticData_shortDescription() {
      String id = "exportDiagnosticData_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticData_description() {
      String id = "exportDiagnosticData_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticData_syntax() {
      String id = "exportDiagnosticData_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticDataFromServer_shortDescription() {
      String id = "exportDiagnosticDataFromServer_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticDataFromServer_description() {
      String id = "exportDiagnosticDataFromServer_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportDiagnosticDataFromServer_syntax() {
      String id = "exportDiagnosticDataFromServer_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableCapturedImages_shortDescription() {
      String id = "getAvailableCapturedImages_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableCapturedImages_description() {
      String id = "getAvailableCapturedImages_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableCapturedImages_syntax() {
      String id = "getAvailableCapturedImages_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeCapturedImages_shortDescription() {
      String id = "purgeCapturedImages_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeCapturedImages_description() {
      String id = "purgeCapturedImages_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeCapturedImages_syntax() {
      String id = "getPurgeImages_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureFile_shortDescription() {
      String id = "saveDiagnosticImageCaptureFile_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureFile_description() {
      String id = "saveDiagnosticImageCaptureFile_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureFile_syntax() {
      String id = "saveDiagnosticImageCaptureFile_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureEntryFile_shortDescription() {
      String id = "saveDiagnosticImageCaptureEntryFile_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureEntryFile_description() {
      String id = "saveDiagnosticImageCaptureEntryFile_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_saveDiagnosticImageCaptureEntryFile_syntax() {
      String id = "saveDiagnosticImageCaptureEntryFile_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listSystemResourceControls_shortDescription() {
      String id = "listSystemResourceControls_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listSystemResourceControls_description() {
      String id = "listSystemResourceControls_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listSystemResourceControls_syntax() {
      String id = "listSystemResourceControls_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableSystemResource_shortDescription() {
      String id = "enableSystemResource_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableSystemResource_description() {
      String id = "enableSystemResource_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableSystemResource_syntax() {
      String id = "enableSystemResource_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disableSystemResource_shortDescription() {
      String id = "disableSystemResource_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disableSystemResource_description() {
      String id = "disableSystemResource_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_disableSystemResource_syntax() {
      String id = "disableSystemResource_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createSystemResourceControl_shortDescription() {
      String id = "createSystemResourceControl_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createSystemResourceControl_description() {
      String id = "createSystemResourceControl_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createSystemResourceControl_syntax() {
      String id = "createSystemResourceControl_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroySystemResourceControl_description() {
      String id = "destroySystemResourceControl_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroySystemResourceControl_shortDescription() {
      String id = "destroySystemResourceControl_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroySystemResourceControl_syntax() {
      String id = "destroySystemResourceControl_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpDiagnosticData_shortDescription() {
      String id = "dumpDiagnosticData_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpDiagnosticData_description() {
      String id = "dumpDiagnosticData_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpDiagnosticData_syntax() {
      String id = "dumpDiagnosticData_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_mergeDiagnosticData_shortDescription() {
      String id = "mergeDiagnosticData_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_mergeDiagnosticData_description() {
      String id = "mergeDiagnosticData_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_mergeDiagnosticData_syntax() {
      String id = "mergeDiagnosticData_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_captureAndSaveDiagnosticImage_shortDescription() {
      String id = "captureAndSaveDiagnosticImage_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_captureAndSaveDiagnosticImage_description() {
      String id = "captureAndSaveDiagnosticImage_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_captureAndSaveDiagnosticImage_syntax() {
      String id = "captureAndSaveDiagnosticImage_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableDiagnosticDataAccessorNames_shortDescription() {
      String id = "getAvailableDiagnosticDataAccessorNames_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableDiagnosticDataAccessorNames_description() {
      String id = "getAvailableDiagnosticDataAccessorNames_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getAvailableDiagnosticDataAccessorNames_syntax() {
      String id = "getAvailableDiagnosticDataAccessorNames_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesData_shortDescription() {
      String id = "exportHarvestedTimeSeriesData_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesData_description() {
      String id = "exportHarvestedTimeSeriesData_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesData_syntax() {
      String id = "exportHarvestedTimeSeriesData_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesDataOffline_shortDescription() {
      String id = "exportHarvestedTimeSeriesDataOffline_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesDataOffline_description() {
      String id = "exportHarvestedTimeSeriesDataOffline_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportHarvestedTimeSeriesDataOffline_syntax() {
      String id = "exportHarvestedTimeSeriesDataOffline_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatches_shortDescription() {
      String id = "listDebugPatches_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatches_description() {
      String id = "listDebugPatches_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatches_syntax() {
      String id = "listDebugPatches_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showDebugPatchInfo_shortDescription() {
      String id = "showDebugPatchInfo_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showDebugPatchInfo_description() {
      String id = "showDebugPatchInfo_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showDebugPatchInfo_syntax() {
      String id = "showDebugPatchInfo_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activateDebugPatch_shortDescription() {
      String id = "activateDebugPatch_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activateDebugPatch_description() {
      String id = "activateDebugPatch_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activateDebugPatch_syntax() {
      String id = "activateDebugPatch_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateDebugPatches_shortDescription() {
      String id = "deactivateDebugPatches_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateDebugPatches_description() {
      String id = "deactivateDebugPatches_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateDebugPatches_syntax() {
      String id = "deactivateDebugPatches_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatchTasks_shortDescription() {
      String id = "listDebugPatchTasks_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatchTasks_description() {
      String id = "listDebugPatchTasks_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listDebugPatchTasks_syntax() {
      String id = "listDebugPatchTasks_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeDebugPatchTasks_shortDescription() {
      String id = "purgeDebugPatchTasks_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeDebugPatchTasks_description() {
      String id = "purgeDebugPatchTasks_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_purgeDebugPatchTasks_syntax() {
      String id = "purgeDebugPatchTasks_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateAllDebugPatches_shortDescription() {
      String id = "deactivateAllDebugPatches_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateAllDebugPatches_description() {
      String id = "deactivateAllDebugPatches_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deactivateAllDebugPatches_syntax() {
      String id = "deactivateAllDebugPatches_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activate_shortDescription() {
      String id = "activate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activate_description() {
      String id = "activate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_activate_syntax() {
      String id = "activate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assign_shortDescription() {
      String id = "assign_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assign_description() {
      String id = "assign_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assign_syntax() {
      String id = "assign_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assignAll_shortDescription() {
      String id = "assignAll_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assignAll_description() {
      String id = "assignAll_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_assignAll_syntax() {
      String id = "assignAll_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cancelEdit_shortDescription() {
      String id = "cancelEdit_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cancelEdit_description() {
      String id = "cancelEdit_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cancelEdit_syntax() {
      String id = "cancelEdit_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_create_shortDescription() {
      String id = "create_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_create_description() {
      String id = "create_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_create_syntax() {
      String id = "create_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_delete_shortDescription() {
      String id = "delete_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_delete_description() {
      String id = "delete_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_delete_syntax() {
      String id = "delete_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_get_shortDescription() {
      String id = "get_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_get_description() {
      String id = "get_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_get_syntax() {
      String id = "get_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getActivationTask_shortDescription() {
      String id = "getActivationTask_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getActivationTask_description() {
      String id = "getActivationTask_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getActivationTask_syntax() {
      String id = "getActivationTask_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBean_shortDescription() {
      String id = "getMBean_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBean_description() {
      String id = "getMBean_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBean_syntax() {
      String id = "getMBean_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getPath_shortDescription() {
      String id = "getPath_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getPath_description() {
      String id = "getPath_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getPath_syntax() {
      String id = "getPath_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_invoke_shortDescription() {
      String id = "invoke_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_invoke_description() {
      String id = "invoke_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_invoke_syntax() {
      String id = "invoke_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isRestartRequired_shortDescription() {
      String id = "isRestartRequired_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isRestartRequired_description() {
      String id = "isRestartRequired_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isRestartRequired_syntax() {
      String id = "isRestartRequired_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadDB_shortDescription() {
      String id = "loadDB_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadDB_description() {
      String id = "loadDB_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadDB_syntax() {
      String id = "loadDB_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadProperties_shortDescription() {
      String id = "loadProperties_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadProperties_description() {
      String id = "loadProperties_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_loadProperties_syntax() {
      String id = "loadProperties_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_save_shortDescription() {
      String id = "save_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_save_description() {
      String id = "save_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_save_syntax() {
      String id = "save_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_set_shortDescription() {
      String id = "set_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_set_description() {
      String id = "set_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_set_syntax() {
      String id = "set_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setOption_shortDescription() {
      String id = "setOption_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setOption_description() {
      String id = "setOption_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setOption_syntax() {
      String id = "setOption_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showChanges_shortDescription() {
      String id = "showChanges_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showChanges_description() {
      String id = "showChanges_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showChanges_syntax() {
      String id = "showChanges_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startEdit_shortDescription() {
      String id = "startEdit_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startEdit_description() {
      String id = "startEdit_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startEdit_syntax() {
      String id = "startEdit_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopEdit_shortDescription() {
      String id = "stopEdit_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopEdit_description() {
      String id = "stopEdit_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopEdit_syntax() {
      String id = "stopEdit_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassign_shortDescription() {
      String id = "unassign_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassign_description() {
      String id = "unassign_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassign_syntax() {
      String id = "unassign_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassignAll_shortDescription() {
      String id = "unassignAll_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassignAll_description() {
      String id = "unassignAll_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_unassignAll_syntax() {
      String id = "unassignAll_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undo_shortDescription() {
      String id = "undo_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undo_description() {
      String id = "undo_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_undo_syntax() {
      String id = "undo_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validate_shortDescription() {
      String id = "validate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validate_description() {
      String id = "validate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_validate_syntax() {
      String id = "validate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_configToScript_shortDescription() {
      String id = "configToScript_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_configToScript_description() {
      String id = "configToScript_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_configToScript_syntax() {
      String id = "configToScript_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpStack_shortDescription() {
      String id = "dumpStack_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpStack_description() {
      String id = "dumpStack_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpStack_syntax() {
      String id = "dumpStack_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpVariables_shortDescription() {
      String id = "dumpVariables_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpVariables_description() {
      String id = "dumpVariables_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpVariables_syntax() {
      String id = "dumpVariables_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_find_shortDescription() {
      String id = "find_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_find_description() {
      String id = "find_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_find_syntax() {
      String id = "find_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getConfigManager_shortDescription() {
      String id = "getConfigManager_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getConfigManager_description() {
      String id = "getConfigManager_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getConfigManager_syntax() {
      String id = "getConfigManager_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBI_shortDescription() {
      String id = "getMBI_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBI_description() {
      String id = "getMBI_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getMBI_syntax() {
      String id = "getMBI_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listChildTypes_shortDescription() {
      String id = "listChildTypes_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listChildTypes_description() {
      String id = "listChildTypes_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listChildTypes_syntax() {
      String id = "listChildTypes_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_ls_shortDescription() {
      String id = "ls_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_ls_description() {
      String id = "ls_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_ls_syntax() {
      String id = "ls_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redirect_shortDescription() {
      String id = "redirect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redirect_description() {
      String id = "redirect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_redirect_syntax() {
      String id = "redirect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_removeListener_shortDescription() {
      String id = "removeListener_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_removeListener_description() {
      String id = "removeListener_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_removeListener_syntax() {
      String id = "removeListener_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showListeners_shortDescription() {
      String id = "showListeners_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showListeners_description() {
      String id = "showListeners_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showListeners_syntax() {
      String id = "showListeners_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startRecording_shortDescription() {
      String id = "startRecording_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startRecording_description() {
      String id = "startRecording_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startRecording_syntax() {
      String id = "startRecording_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_state_shortDescription() {
      String id = "state_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_state_description() {
      String id = "state_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_state_syntax() {
      String id = "state_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRecording_shortDescription() {
      String id = "stopRecording_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRecording_description() {
      String id = "stopRecording_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRecording_syntax() {
      String id = "stopRecording_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRedirect_shortDescription() {
      String id = "stopRedirect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRedirect_description() {
      String id = "stopRedirect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopRedirect_syntax() {
      String id = "stopRedirect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeUserConfig_shortDescription() {
      String id = "storeUserConfig_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeUserConfig_description() {
      String id = "storeUserConfig_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeUserConfig_syntax() {
      String id = "storeUserConfig_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_threadDump_shortDescription() {
      String id = "threadDump_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_threadDump_description() {
      String id = "threadDump_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_threadDump_syntax() {
      String id = "threadDump_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addListener_shortDescription() {
      String id = "addListener_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addListener_description() {
      String id = "addListener_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_addListener_syntax() {
      String id = "addListener_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_viewMBean_shortDescription() {
      String id = "viewMBean_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_viewMBean_description() {
      String id = "viewMBean_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_viewMBean_syntax() {
      String id = "viewMBean_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeIniFile_shortDescription() {
      String id = "writeIniFile_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeIniFile_description() {
      String id = "writeIniFile_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_writeIniFile_syntax() {
      String id = "writeIniFile_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrate_shortDescription() {
      String id = "migrate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrate_description() {
      String id = "migrate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrate_syntax() {
      String id = "migrate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resume_shortDescription() {
      String id = "resume_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resume_description() {
      String id = "resume_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resume_syntax() {
      String id = "resume_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_shutdown_shortDescription() {
      String id = "shutdown_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_shutdown_description() {
      String id = "shutdown_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_shutdown_syntax() {
      String id = "shutdown_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_start_shortDescription() {
      String id = "start_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_start_description() {
      String id = "start_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_start_syntax() {
      String id = "start_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startServer_shortDescription() {
      String id = "startServer_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startServer_description() {
      String id = "startServer_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startServer_syntax() {
      String id = "startServer_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_suspend_shortDescription() {
      String id = "suspend_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_suspend_description() {
      String id = "suspend_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_suspend_syntax() {
      String id = "suspend_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_softRestart_shortDescription() {
      String id = "softRestart_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_softRestart_description() {
      String id = "softRestart_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_softRestart_syntax() {
      String id = "softRestart_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleUp_shortDescription() {
      String id = "scaleUp_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleUp_description() {
      String id = "scaleUp_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleUp_syntax() {
      String id = "scaleUp_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleDown_shortDescription() {
      String id = "scaleDown_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleDown_description() {
      String id = "scaleDown_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_scaleDown_syntax() {
      String id = "scaleDown_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nm_shortDescription() {
      String id = "nm_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nm_description() {
      String id = "nm_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nm_syntax() {
      String id = "nm_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmConnect_shortDescription() {
      String id = "nmConnect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmConnect_description() {
      String id = "nmConnect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmConnect_syntax() {
      String id = "nmConnect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDisconnect_shortDescription() {
      String id = "nmDisconnect_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDisconnect_description() {
      String id = "nmDisconnect_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDisconnect_syntax() {
      String id = "nmDisconnect_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmEnroll_shortDescription() {
      String id = "nmEnroll_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmEnroll_description() {
      String id = "nmEnroll_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmEnroll_syntax() {
      String id = "nmEnroll_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmKill_shortDescription() {
      String id = "nmKill_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmKill_description() {
      String id = "nmKill_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmKill_syntax() {
      String id = "nmKill_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmSoftRestart_shortDescription() {
      String id = "nmSoftRestart_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmSoftRestart_description() {
      String id = "nmSoftRestart_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmSoftRestart_syntax() {
      String id = "nmSoftRestart_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmGenBootStartupProps_shortDescription() {
      String id = "nmGenBootStartupProps_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmGenBootStartupProps_description() {
      String id = "nmGenBootStartupProps_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmGenBootStartupProps_syntax() {
      String id = "nmGenBootStartupProps_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmLog_shortDescription() {
      String id = "nmLog_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmLog_description() {
      String id = "nmLog_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmLog_syntax() {
      String id = "nmLog_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerLog_shortDescription() {
      String id = "nmServerLog_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerLog_description() {
      String id = "nmServerLog_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerLog_syntax() {
      String id = "nmServerLog_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerStatus_shortDescription() {
      String id = "nmServerStatus_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerStatus_description() {
      String id = "nmServerStatus_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmServerStatus_syntax() {
      String id = "nmServerStatus_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmStart_shortDescription() {
      String id = "nmStart_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmStart_description() {
      String id = "nmStart_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmStart_syntax() {
      String id = "nmStart_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmVersion_shortDescription() {
      String id = "nmVersion_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmVersion_description() {
      String id = "nmVersion_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmVersion_syntax() {
      String id = "nmVersion_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_config_shortDescription() {
      String id = "config_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_config_description() {
      String id = "config_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_config_syntax() {
      String id = "config_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_custom_shortDescription() {
      String id = "custom_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_custom_description() {
      String id = "custom_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_custom_syntax() {
      String id = "custom_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainCustom_shortDescription() {
      String id = "domainCustom_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainCustom_description() {
      String id = "domainCustom_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainCustom_syntax() {
      String id = "domainCustom_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editCustom_shortDescription() {
      String id = "editCustom_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editCustom_description() {
      String id = "editCustom_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editCustom_syntax() {
      String id = "editCustom_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainConfig_shortDescription() {
      String id = "domainConfig_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainConfig_description() {
      String id = "domainConfig_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainConfig_syntax() {
      String id = "domainConfig_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainRuntime_shortDescription() {
      String id = "domainRuntime_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainRuntime_description() {
      String id = "domainRuntime_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainRuntime_syntax() {
      String id = "domainRuntime_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_edit_shortDescription() {
      String id = "edit_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_edit_description() {
      String id = "edit_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_edit_syntax() {
      String id = "edit_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createEditSession_shortDescription() {
      String id = "createEditSession_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createEditSession_description() {
      String id = "createEditSession_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_createEditSession_syntax() {
      String id = "createEditSession_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroyEditSession_shortDescription() {
      String id = "destroyEditSession_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroyEditSession_description() {
      String id = "destroyEditSession_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_destroyEditSession_syntax() {
      String id = "destroyEditSession_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showEditSession_shortDescription() {
      String id = "showEditSession_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showEditSession_description() {
      String id = "showEditSession_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showEditSession_syntax() {
      String id = "showEditSession_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resolve_shortDescription() {
      String id = "resolve_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resolve_description() {
      String id = "resolve_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resolve_syntax() {
      String id = "resolve_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_jndi_shortDescription() {
      String id = "jndi_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_jndi_description() {
      String id = "jndi_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_jndi_syntax() {
      String id = "jndi_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_runtime_shortDescription() {
      String id = "runtime_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_runtime_description() {
      String id = "runtime_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_runtime_syntax() {
      String id = "runtime_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverConfig_shortDescription() {
      String id = "serverConfig_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverConfig_description() {
      String id = "serverConfig_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverConfig_syntax() {
      String id = "serverConfig_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverRuntime_shortDescription() {
      String id = "serverRuntime_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverRuntime_description() {
      String id = "serverRuntime_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverRuntime_syntax() {
      String id = "serverRuntime_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_adminHome_shortDescription() {
      String id = "adminHome_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_adminHome_description() {
      String id = "adminHome_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cmo_shortDescription() {
      String id = "cmo_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_cmo_description() {
      String id = "cmo_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_connected_shortDescription() {
      String id = "connected_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_connected_description() {
      String id = "connected_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainName_shortDescription() {
      String id = "domainName_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainName_description() {
      String id = "domainName_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainRuntimeService_shortDescription() {
      String id = "domainRuntimeService_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_domainRuntimeService_description() {
      String id = "domainRuntimeService_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editService_shortDescription() {
      String id = "editService_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editService_description() {
      String id = "editService_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exitonerror_shortDescription() {
      String id = "exitonerror_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exitonerror_description() {
      String id = "exitonerror_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_home_shortDescription() {
      String id = "home_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_home_description() {
      String id = "home_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isAdminServer_shortDescription() {
      String id = "isAdminServer_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_isAdminServer_description() {
      String id = "isAdminServer_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_mbs_shortDescription() {
      String id = "mbs_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_mbs_description() {
      String id = "mbs_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_recording_shortDescription() {
      String id = "recording_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_recording_description() {
      String id = "recording_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_runtimeService_shortDescription() {
      String id = "runtimeService_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_runtimeService_description() {
      String id = "runtimeService_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverName_shortDescription() {
      String id = "serverName_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_serverName_description() {
      String id = "serverName_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_typeService_shortDescription() {
      String id = "typeService_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_typeService_description() {
      String id = "typeService_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_username_shortDescription() {
      String id = "username_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_username_description() {
      String id = "username_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_version_shortDescription() {
      String id = "version_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_version_description() {
      String id = "version_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_idd_shortDescription() {
      String id = "idd_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_idd_description() {
      String id = "idd_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_lookup_shortDescription() {
      String id = "lookup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_lookup_description() {
      String id = "lookup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_lookup_syntax() {
      String id = "lookup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_easeSyntax_shortDescription() {
      String id = "easeSyntax_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_easeSyntax_description() {
      String id = "easeSyntax_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_easeSyntax_syntax() {
      String id = "easeSyntax_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_man_shortDescription() {
      String id = "man_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_man_description() {
      String id = "man_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_man_syntax() {
      String id = "man_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_encrypt_shortDescription() {
      String id = "encrypt_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_encrypt_description() {
      String id = "encrypt_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_encrypt_syntax() {
      String id = "encrypt_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startNodeManager_shortDescription() {
      String id = "startNodeManager_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startNodeManager_description() {
      String id = "startNodeManager_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startNodeManager_syntax() {
      String id = "startNodeManager_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopNodeManager_shortDescription() {
      String id = "stopNodeManager_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopNodeManager_description() {
      String id = "stopNodeManager_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_stopNodeManager_syntax() {
      String id = "stopNodeManager_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_MainDescription_HelpString() {
      String id = "MainDescription_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_control_HelpString() {
      String id = "control_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_lifecycle_HelpString() {
      String id = "lifecycle_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_browse_HelpString() {
      String id = "browse_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editing_HelpString() {
      String id = "editing_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_information_HelpString() {
      String id = "information_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deployment_HelpString() {
      String id = "deployment_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_all_HelpString() {
      String id = "all_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_variables_HelpString() {
      String id = "variables_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_trees_HelpString() {
      String id = "trees_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nodemanager_HelpString() {
      String id = "nodemanager_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_miscellaneous_HelpString() {
      String id = "miscellaneous_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_common_HelpString() {
      String id = "common_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_diagnostics_HelpString() {
      String id = "diagnostics_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_HelpFor_HelpString() {
      String id = "HelpFor_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_NoHelp1_HelpString() {
      String id = "NoHelp1_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_NoHelp2_HelpString() {
      String id = "NoHelp2_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_Description_HelpString() {
      String id = "Description_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_Syntax_HelpString() {
      String id = "Syntax_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_Example_HelpString() {
      String id = "Example_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeadmin_HelpString() {
      String id = "storeadmin_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openfilestore_shortDescription() {
      String id = "openfilestore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openfilestore_description() {
      String id = "openfilestore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openfilestore_syntax() {
      String id = "openfilestore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openjdbcstore_shortDescription() {
      String id = "openjdbcstore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openjdbcstore_description() {
      String id = "openjdbcstore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_openjdbcstore_syntax() {
      String id = "openjdbcstore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closestore_shortDescription() {
      String id = "closestore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closestore_description() {
      String id = "closestore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_closestore_syntax() {
      String id = "closestore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_liststore_shortDescription() {
      String id = "liststore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_liststore_description() {
      String id = "liststore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_liststore_syntax() {
      String id = "liststore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpstore_shortDescription() {
      String id = "dumpstore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpstore_description() {
      String id = "dumpstore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_dumpstore_syntax() {
      String id = "dumpstore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_compactstore_shortDescription() {
      String id = "compactstore_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_compactstore_description() {
      String id = "compactstore_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_compactstore_syntax() {
      String id = "compactstore_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getopenstores_shortDescription() {
      String id = "getopenstores_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getopenstores_description() {
      String id = "getopenstores_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getopenstores_syntax() {
      String id = "getopenstores_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getstoreconns_shortDescription() {
      String id = "getstoreconns_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getstoreconns_description() {
      String id = "getstoreconns_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getstoreconns_syntax() {
      String id = "getstoreconns_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listApplications_shortDescription() {
      String id = "listApplications_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listApplications_description() {
      String id = "listApplications_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_listApplications_syntax() {
      String id = "listApplications_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_all_shortDescription() {
      String id = "all_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_browse_shortDescription() {
      String id = "browse_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_common_shortDescription() {
      String id = "common_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_control_shortDescription() {
      String id = "control_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deployment_shortDescription() {
      String id = "deployment_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_diagnostics_shortDescription() {
      String id = "diagnostics_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_editing_shortDescription() {
      String id = "editing_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_information_shortDescription() {
      String id = "information_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_lifecycle_shortDescription() {
      String id = "lifecycle_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nodemanager_shortDescription() {
      String id = "nodemanager_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_offline_shortDescription() {
      String id = "offline_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_online_shortDescription() {
      String id = "online_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_storeadmin_shortDescription() {
      String id = "storeadmin_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_trees_shortDescription() {
      String id = "trees_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_variables_shortDescription() {
      String id = "variable_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_offline_HelpString() {
      String id = "offline_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_online_HelpString() {
      String id = "online_HelpString";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDiagnostics_shortDescription() {
      String id = "nmDiagnostics_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDiagnostics_description() {
      String id = "nmDiagnostics_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmDiagnostics_syntax() {
      String id = "nmDiagnostics_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmInvocation_shortDescription() {
      String id = "nmInvocation_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmInvocation_description() {
      String id = "nmInvocation_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmInvocation_syntax() {
      String id = "nmInvocation_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setFEHostURL_shortDescription() {
      String id = "setFEHostURL_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setFEHostURL_description() {
      String id = "setFEHostURL_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setFEHostURL_syntax() {
      String id = "setFEHostURL_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getFEHostURL_shortDescription() {
      String id = "getFEHostURL_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getFEHostURL_description() {
      String id = "getFEHostURL_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_getFEHostURL_syntax() {
      String id = "getFEHostURL_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteFEHost_shortDescription() {
      String id = "deleteFEHost_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteFEHost_description() {
      String id = "deleteFEHost_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_deleteFEHost_syntax() {
      String id = "deleteFEHost_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resync_shortDescription() {
      String id = "resync_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resync_description() {
      String id = "resync_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resync_syntax() {
      String id = "resync_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resyncAll_shortDescription() {
      String id = "resyncAll_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resyncAll_description() {
      String id = "resyncAll_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_resyncAll_syntax() {
      String id = "resyncAll_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showComponentChanges_shortDescription() {
      String id = "showComponentChanges_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showComponentChanges_description() {
      String id = "showComponentChanges_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_showComponentChanges_syntax() {
      String id = "showComponentChanges_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pullComponentChanges_shortDescription() {
      String id = "pullComponentChanges_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pullComponentChanges_description() {
      String id = "pullComponentChanges_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_pullComponentChanges_syntax() {
      String id = "pullComponentChanges_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableOverwriteComponentChanges_shortDescription() {
      String id = "enableOverwriteComponentChanges_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableOverwriteComponentChanges_description() {
      String id = "enableOverwriteComponentChanges_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_enableOverwriteComponentChanges_syntax() {
      String id = "enableOverwriteComponentChanges_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmRestart_shortDescription() {
      String id = "nmRestart_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmRestart_description() {
      String id = "nmRestart_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmRestart_syntax() {
      String id = "nmRestart_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmExecScript_shortDescription() {
      String id = "nmExecScript_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmExecScript_description() {
      String id = "nmExecScript_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmExecScript_syntax() {
      String id = "nmExecScript_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setShowLSResult_shortDescription() {
      String id = "setShowLSResult_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setShowLSResult_description() {
      String id = "setShowLSResult_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_setShowLSResult_syntax() {
      String id = "setShowLSResult_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startPartitionWait_shortDescription() {
      String id = "startPartitionWait_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startPartitionWait_description() {
      String id = "startPartitionWait_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_startPartitionWait_syntax() {
      String id = "startPartitionWait_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_forceShutdownPartitionWait_shortDescription() {
      String id = "forceShutdownPartitionWait_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_forceShutdownPartitionWait_description() {
      String id = "forceShutdownPartitionWait_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_forceShutdownPartitionWait_syntax() {
      String id = "forceShutdownPartitionWait_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrateResourceGroup_shortDescription() {
      String id = "migrateResourceGroup_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrateResourceGroup_description() {
      String id = "migrateResourceGroup_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_migrateResourceGroup_syntax() {
      String id = "migrateResourceGroup_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportPartition_shortDescription() {
      String id = "exportPartition_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportPartition_description() {
      String id = "exportPartition_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_exportPartition_syntax() {
      String id = "exportPartition_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_importPartition_shortDescription() {
      String id = "importPartition_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_importPartition_description() {
      String id = "importPartition_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_importPartition_syntax() {
      String id = "importPartition_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_uploadUserFileWait_shortDescription() {
      String id = "uploadUserFileWait_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_uploadUserFileWait_description() {
      String id = "uploadUserFileWait_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_uploadUserFileWait_syntax() {
      String id = "uploadUserFileWait_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutUpdate_shortDescription() {
      String id = "rolloutUpdate_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutUpdate_description() {
      String id = "rolloutUpdate_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutUpdate_syntax() {
      String id = "rolloutUpdate_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutOracleHome_shortDescription() {
      String id = "rolloutOracleHome_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutOracleHome_description() {
      String id = "rolloutOracleHome_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutOracleHome_syntax() {
      String id = "rolloutOracleHome_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutJavaHome_shortDescription() {
      String id = "rolloutJavaHome_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutJavaHome_description() {
      String id = "rolloutJavaHome_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutJavaHome_syntax() {
      String id = "rolloutJavaHome_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutApplications_shortDescription() {
      String id = "rolloutApplications_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutApplications_description() {
      String id = "rolloutApplications_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rolloutApplications_syntax() {
      String id = "rolloutApplications_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rollingRestart_shortDescription() {
      String id = "rollingRestart_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rollingRestart_description() {
      String id = "rollingRestart_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_rollingRestart_syntax() {
      String id = "rollingRestart_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmPrintThreadDump_shortDescription() {
      String id = "nmPrintThreadDump_shortDescription";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_nmPrintThreadDump_description() {
      String id = "nmPrintThreadDump_description";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String get_PrintThreadDump_syntax() {
      String id = "nmPrintThreadDump_syntax";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
