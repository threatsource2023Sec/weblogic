package com.oracle.weblogic.diagnostics.l10n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsFrameworkTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsFrameworkTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextLocalizer", DiagnosticsFrameworkTextTextFormatter.class.getClassLoader());
   }

   public DiagnosticsFrameworkTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextLocalizer", DiagnosticsFrameworkTextTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsFrameworkTextTextFormatter getInstance() {
      return new DiagnosticsFrameworkTextTextFormatter();
   }

   public static DiagnosticsFrameworkTextTextFormatter getInstance(Locale l) {
      return new DiagnosticsFrameworkTextTextFormatter(l);
   }

   public String getTableAveragesNonIterableRowText(String arg0) {
      String id = "TableAveragesNonIterableRow";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangesValueNotANumber(String arg0) {
      String id = "ChangesValueNotANumber";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullPolledObject() {
      String id = "NullPolledObject";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPollerFactoryNullSourceObject() {
      String id = "PollerFactoryNullSourceObject";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPollerFrequencyMustBeGreaterThanZero(int arg0) {
      String id = "PollerFrequencyMustBeGreaterThanZero";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPollerMaxSamplesMustBeGreaterThanZero(int arg0) {
      String id = "PollerMaxSamplesMustBeGreaterThanZero";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalPercentMatchArguments(String arg0) {
      String id = "IllegalPercentMatchArguments";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExtractSampleSizeLimitExceeded(long arg0, long arg1) {
      String id = "ExtractSampleSizeLimitExceeded";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExtractEvaluationContext() {
      String id = "ExtractEvaluationContext";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPollerFactoryNotFoundForExtract() {
      String id = "PollerFactoryNotFoundForExtract";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalExtractFrequencyArgument(String arg0, String arg1) {
      String id = "IllegalExtractFrequencyArgument";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalExtractDurationArgument(String arg0) {
      String id = "IllegalExtractDurationArgument";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoEvaluationContextForExtractFunction() {
      String id = "NoEvaluationContextForExtractFunction";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExpressionExtensionBeanNotFound(String arg0, String arg1) {
      String id = "ExpressionExtensionBeanNotFound";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFunctionExtensionNotFoundText(String arg0, String arg1) {
      String id = "FunctionExtensionNotFoundText";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAverageInputValueNotANumberText(Object arg0) {
      String id = "AverageInputValueNaNText";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTableAveragesInvalidTableRowStructureText(String arg0) {
      String id = "TableAveragesInvalidTableRowStructureText";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getELBeanNameAlreadyBoundText(String arg0, String arg1) {
      String id = "ELBeanNameAlreadyBoundText";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetAttributeNameMapsToMultipleInstances() {
      String id = "GetAttributeNameMapsToMultipleInstances";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetAttributeInstanceNotFoundForPattern(String arg0) {
      String id = "GetAttributeInstanceNotFoundForPattern";
      String subsystem = "DiagnosticFramework";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
