package com.bea.adaptive.harvester.jmx;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class HarvesterJMXTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public HarvesterJMXTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.harvester.jmx.HarvesterJMXTextTextLocalizer", HarvesterJMXTextTextFormatter.class.getClassLoader());
   }

   public HarvesterJMXTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "com.bea.adaptive.harvester.jmx.HarvesterJMXTextTextLocalizer", HarvesterJMXTextTextFormatter.class.getClassLoader());
   }

   public static HarvesterJMXTextTextFormatter getInstance() {
      return new HarvesterJMXTextTextFormatter();
   }

   public static HarvesterJMXTextTextFormatter getInstance(Locale l) {
      return new HarvesterJMXTextTextFormatter(l);
   }

   public String getHarvesterLabel(String arg0) {
      String id = "HarvesterLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnconfirmedLabel() {
      String id = "UnconfirmedLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugMessageLabel() {
      String id = "DebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterDebugMessageLabel() {
      String id = "HarvesterDebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterManagerDebugMessageLabel() {
      String id = "HarvesterManagerDebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVerboseSuffixDebugMessageLabel() {
      String id = "VerboseSuffixDebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVeryVerboseSuffixDebugMessageLabel() {
      String id = "VeryVerboseSuffixDebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadingSuffixDebugMessageLabel() {
      String id = "ThreadingSuffixDebugMessageLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExcpResolvingMBean(String arg0, String arg1) {
      String id = "ExcpResolvingMBean";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvInstNameMess(String arg0) {
      String id = "InvInstNameMess";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeDoesNotContainAttrMess(String arg0, String arg1) {
      String id = "TypeDoesNotContainAttrMess";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExcpWhilstTryingToAccessAttrMess(String arg0, String arg1, String arg2) {
      String id = "ExcpWhilstTryingToAccessAttrMess";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExcpWhilstShuttingDownHarvester(String arg0, String arg1, String arg2) {
      String id = "ExcpWhilstShuttingDownHarvester";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSimpleExprTermStr(String arg0) {
      String id = "SimpleExprTermStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMapExprTermStr(String arg0) {
      String id = "MapExprTermStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMapRegexExprTermStr(String arg0) {
      String id = "MapRegexExprTermStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMapArrayTermStr(int arg0) {
      String id = "MapArrayTermStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyRegisteredStr(String arg0) {
      String id = "AlreadyRegisteredStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidStateStr(String arg0, String arg1) {
      String id = "InvalidStateStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDataCollectionProblemStr(String arg0, String arg1) {
      String id = "DataCollectionProblemStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprErrStr(String arg0, String arg1) {
      String id = "AttrExprErrStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprUnnamedSimpleTermErrStr() {
      String id = "AttrExprUnnamedSimpleTermErrStr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprCantLocateAccessors(String arg0) {
      String id = "AttrExprCantLocateAccessors";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprExpectedListOrArray() {
      String id = "AttrExprExpectedListOrArray";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprExpectedMap() {
      String id = "AttrExprExpectedMap";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprExpectedStringMapKey() {
      String id = "AttrExprExpectedStringMapKey";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrExprUnknownTermType() {
      String id = "AttrExprUnknownTermType";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullTypeNameErr() {
      String id = "NullTypeNameErr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullInstNameErr() {
      String id = "NullInstNameErr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchWVID(int arg0) {
      String id = "NoSuchWVID";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateInactiveLabel() {
      String id = "StateInactiveLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateActiveLabel() {
      String id = "StateActiveLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateShuttingDownLabel() {
      String id = "StateShuttingDownLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateTerminatedLabel() {
      String id = "StateTerminatedLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterManagerLabel() {
      String id = "HarvesterManagerLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWVIDExistsMsg(int arg0) {
      String id = "WVIDExistsMsg";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWVIDLabel() {
      String id = "WVIDLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForMetricDescrLabel() {
      String id = "ForMetricDescrLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstLabel() {
      String id = "InstLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrLabel() {
      String id = "AttrLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPullCategoryForWVIDLabel(int arg0) {
      String id = "PullCategoryForWVIDLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCategoriesLabel() {
      String id = "CategoriesLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMetricDataLabel() {
      String id = "MetricDataLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeNameLabel() {
      String id = "TypeNameLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstNameLabel() {
      String id = "InstNameLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttrNameLabel() {
      String id = "AttrNameLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSlotDataLabel() {
      String id = "SlotDataLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeyLabel() {
      String id = "KeyLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchTypeMessage(String arg0) {
      String id = "NoSuchTypeMessage";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantHandleAttrTypeMessage(String arg0) {
      String id = "CantHandleAttrTypeMessage";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownLabel() {
      String id = "UnknownLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErr(String arg0, int arg1, String arg2) {
      String id = "ExprParseErr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrMissingKeyValue() {
      String id = "ExprParseErrMissingKeyValue";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseRegexCompilerErr(String arg0, String arg1) {
      String id = "ExprParseRegexCompilerErr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrMissingOrderedIndexValue() {
      String id = "ExprParseErrMissingOrderedIndexValue";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrOrderedIndexValueInvalid(String arg0) {
      String id = "ExprParseErrOrderedIndexValueInvalid";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrNoEnclosureStart(char arg0) {
      String id = "ExprParseErrNoEnclosureStart";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrEmptyAttr() {
      String id = "ExprParseErrEmptyAttr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrUnterminatedEnclosure(char arg0, int arg1) {
      String id = "ExprParseErrUnterminatedEnclosure";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrNestedEnclosure(int arg0) {
      String id = "ExprParseErrNestedEnclosure";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExprParseErrSpuriousChars(char arg0) {
      String id = "ExprParseErrSpuriousChars";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeDefinitivelyNotHandledByHarvester(String arg0, String arg1) {
      String id = "TypeDefinitivelyNotHandledByHarvester";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoMBeansForMetricErr() {
      String id = "NoMBeansForMetricErr";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvestingErrorPrefixLabel() {
      String id = "HarvestingErrorPrefixLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResolutionErrorPrefixLabel() {
      String id = "ResolutionErrorPrefixLabel";
      String subsystem = "HarvesterJMX";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
