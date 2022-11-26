package weblogic.management.upgrade;

public class ConfigFileHelperConstants {
   public static final String FORCE_IMPLICIT_UPGRADE_IF_NEEDED = "weblogic.ForceImplicitUpgradeIfNeeded";
   public static final boolean FORCE_IMPLICIT_UPGRADE_IF_NEEDED_DEFAULT = false;
   public static final String[] UPGRADE_XSLT_SCRIPTS = new String[]{"weblogic/upgrade/domain/directoryselection/SelectWebLogicVersion.xsl", "weblogic/security/internal/SecurityPre90Upgrade.xsl"};
}
