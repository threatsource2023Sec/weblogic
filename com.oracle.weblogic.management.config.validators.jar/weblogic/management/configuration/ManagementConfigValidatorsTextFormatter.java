package weblogic.management.configuration;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ManagementConfigValidatorsTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ManagementConfigValidatorsTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.configuration.ManagementConfigValidatorsTextLocalizer", ManagementConfigValidatorsTextFormatter.class.getClassLoader());
   }

   public ManagementConfigValidatorsTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.configuration.ManagementConfigValidatorsTextLocalizer", ManagementConfigValidatorsTextFormatter.class.getClassLoader());
   }

   public static ManagementConfigValidatorsTextFormatter getInstance() {
      return new ManagementConfigValidatorsTextFormatter();
   }

   public static ManagementConfigValidatorsTextFormatter getInstance(Locale l) {
      return new ManagementConfigValidatorsTextFormatter(l);
   }

   public String getcheckLegalStringSetString(String arg0, String arg1, String arg2) {
      String id = "checkLegalStringSetString";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidcheckLegalRangeString(String arg0, String arg1, String arg2, String arg3) {
      String id = "checkLegalRangeString";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonNullString(String arg0) {
      String id = "CheckNonNullString";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonEmptyStringString(String arg0, String arg1) {
      String id = "CheckNonEmptyString";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetRGTValidationMsg(String arg0) {
      String id = "SetRGTValidationMsg";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMultipleResourceManagers(String arg0) {
      String id = "multipleResourceManagers";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidActionForResource(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "invalidActionForResource";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailAndShutdownSpecifiedTogether(String arg0, String arg1) {
      String id = "failAndShutdownSpecifiedTogether";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailAndRestartSpecifiedTogether(String arg0, String arg1) {
      String id = "failAndRestartSpecifiedTogether";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartAndShutdownSpecifiedTogether(String arg0, String arg1) {
      String id = "restartAndShutdownSpecifiedTogether";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartLoopProtectionError(String arg0) {
      String id = "restartLoopProtectionError";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSameActionSpecifiedTwice(String arg0, String arg1, String arg2, String arg3) {
      String id = "sameActionSpecifiedTwice";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCpuUtilizationNotSupportedOnPlatform(String arg0, String arg1) {
      String id = "cpuUtilizationNotSupportedOnPlatform";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSlowSpecifiedTogetherForTriggerAndFairShareConstraint(String arg0, String arg1, String arg2) {
      String id = "slowSpecifiedTogetherForTriggerAndFairShareConstraint";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidTriggerValueRange(String arg0, String arg1, String arg2, String arg3) {
      String id = "invalidTriggerValueRange";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidNotifyOrSLowValues(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      String id = "InvalidNotifyOrSLowValues";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckRGAddTargetMsg(String arg0, String arg1, String arg2, String arg3) {
      String id = "checkRGAddTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckDefaultTargetFlagMsg() {
      String id = "checkDefaultTargetFlag";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckDefaultTargetFlagAutoTargetMsg() {
      String id = "checkDefaultTargetFlagAutoTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckRGMultiTargetMsg(String arg0, String arg1, String arg2, String arg3) {
      String id = "checkRGMultiTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTargetOfVTOrVHMsg(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "checkDomainRGAddTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckBlackListResMsg(String arg0, String arg1) {
      String id = "checkBlackListRes";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckResIndTargetMsg(String arg0, String arg1) {
      String id = "checkResIndTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckPartRGUniqueMsg(String arg0, String arg1, String arg2) {
      String id = "checkPartRGUnique";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckPartNameNotDomainMsg(String arg0) {
      String id = "checkPartNameNotDomain";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckPartNameClashMsg(String arg0, String arg1, String arg2) {
      String id = "checkPartNameClash";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckResourceUniqInRg(String arg0, String arg1) {
      String id = "checkResourceUniqInRg";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckNameNotContainDollarSignMsg(String arg0) {
      String id = "checkNameNotContainDollarSign";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckNameNotContainInvalidCharactersMsg(String arg0, String arg1) {
      String id = "checkNameNotContainInvalidChars";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckDirectTargOfRes(String arg0) {
      String id = "checkDirectTargOfRes";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentNameNotContainDollarSignMsg(String arg0, String arg1) {
      String id = "checkDeploymentNameNotContainDollarSign";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckAmbiguousResNameMsg(String arg0, String arg1, String arg2) {
      String id = "checkAmbiguousResName";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckTargetNotSetForGlobalRGMsg(String arg0, String arg1) {
      String id = "checkTargetNotSetForOtherGlobalRG";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckTargetNotSetForOtherPartitionMsg(String arg0, String arg1) {
      String id = "checkTargetNotSetForOtherPartition";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckTargetAlreadyExistsMsg(String arg0, String arg1) {
      String id = "checkTargetAlreadyExists";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckAddDefTargetMsg(String arg0, String arg1) {
      String id = "checkAddDefTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckRemoveAvailTargetMsg(String arg0, String arg1) {
      String id = "checkRemoveAvailTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckParentNotFound(String arg0) {
      String id = "checkParentNotFound";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckDelTargetWhenRGisRunning(String arg0, String arg1) {
      String id = "checkDelTargetWhenRGisRunning";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckRuntimeNotFound(String arg0) {
      String id = "checkRuntimeNotFound";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTUriPrefixPartitionNamespaceMsg(String arg0, String arg1) {
      String id = "checkVTUriPrefixPartitionNamespace";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTUriPrefixMsg(String arg0) {
      String id = "checkVTUriPrefix";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTPortMsg(String arg0) {
      String id = "checkVTPort";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTOnlyOneTargetMsg() {
      String id = "checkVTOnlyOneTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTMigratableTargetMsg(String arg0, String arg1, String arg2, String arg3) {
      String id = "checkVTMigratableTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTTargetNotPartOfClusterMSg(String arg0, String arg1, String arg2) {
      String id = "checkVTTargetNotPartOfCluster";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTNamePortUriTargetMsg(String arg0, String arg1) {
      String id = "checkVTNamePortUriTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckVTAsDefaultMsg(String arg0, String arg1, String arg2, String arg3) {
      String id = "checkVTAsDefault";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckValidParentForJmsServer(String arg0, String arg1) {
      String id = "checkValidParentForJmsServer";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidWLDFScalingActionText(String arg0, String arg1, String arg2) {
      String id = "invalidWLDFScalingActionText";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getcheckVTNameClashWithPartitionAdminVT(String arg0) {
      String id = "checkVTNameClashWithPartitionAdminVT";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getcheckAutoTargetAdminFlagWithExplicitTarget(String arg0, String arg1) {
      String id = "checkAutoTargetAdminFlagWithExplicitTarget";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionToDestroyNotNullMsg() {
      String id = "checkPartitionToDestroyNotNull";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionToDestroyShutdownOrHaltedMsg(String arg0, String arg1) {
      String id = "checkPartitionToDestroyShutdownOrHalted";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRGToDestroyShutdownMsg(String arg0, String arg1) {
      String id = "checkRGToDestroyShutdown";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUniqueReferenceToRGTMsg(String arg0) {
      String id = "checkUniqueReferenceToRGT";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentConfigurationsMsg(String arg0) {
      String id = "checkComponentConfigurations";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemComponentsMsg(String arg0) {
      String id = "checkSystemComponents";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemResourceNameConflictMsg(String arg0) {
      String id = "checkSystemResourceNameConflict";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNameNotNullOrEmptyMsg() {
      String id = "checkNameNotNullOrEmpty";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidAdminProtocolMsg(String arg0) {
      String id = "checkValidAdminProtocol";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminProtocolNotNullMsg() {
      String id = "checkAdminProtocolNotNull";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSystemAndUserRootParentsForPartitionMsg(String arg0, String arg1) {
      String id = "checkSystemAndUserRootParentsForPartition";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckNullSystemRootParentForPartitionMsg(String arg0) {
      String id = "checkNullSystemRootParentForPartition";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckNullUserRootParentForPartitionMsg(String arg0) {
      String id = "checkNullUserRootParentForPartition";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameSystemAndUserRootForPartitionMsg(String arg0) {
      String id = "checkSameSystemAndUserRootForPartition";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameSystemRootsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameSystemRootsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameSystemRootUserRootForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameSystemRootUserRootForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameUserRootSystemRootForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameUserRootSystemRootForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameUserRootsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameUserRootsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameSystemRootParentsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameSystemRootParentsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameSystemRootUserRootParentsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameSystemRootUserRootParentsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameUserRootSystemRootParentsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameUserRootSystemRootParentsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSameUserRootParentsForPartitionsMsg(String arg0, String arg1) {
      String id = "checkSameUserRootParentsForPartitions";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJndiNameConflictMsg(String arg0, String arg1, String arg2) {
      String id = "checkJndiNameConflict";
      String subsystem = "ManagementConfigValidators";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
