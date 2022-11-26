package org.glassfish.admin.rest;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class RestTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public RestTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "org.glassfish.admin.rest.RestTextTextLocalizer", RestTextTextFormatter.class.getClassLoader());
   }

   public RestTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "org.glassfish.admin.rest.RestTextTextLocalizer", RestTextTextFormatter.class.getClassLoader());
   }

   public static RestTextTextFormatter getInstance() {
      return new RestTextTextFormatter();
   }

   public static RestTextTextFormatter getInstance(Locale l) {
      return new RestTextTextFormatter(l);
   }

   public String msgAlreadyExists(String arg0) {
      String id = "ALREADY_EXISTS";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAlreadyExistsAnonymous() {
      String id = "ALREADY_EXISTS_ANONYMOUS";
      String subsystem = "REST";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotFound(String arg0) {
      String id = "NOT_FOUND";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUpdatedConfig() {
      String id = "UPDATED_CONFIG";
      String subsystem = "REST";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCreated(String arg0) {
      String id = "CREATED";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUpdated(String arg0) {
      String id = "UPDATED";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDeleted(String arg0) {
      String id = "DELETED";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNotInConfigurationTransaction() {
      String id = "NOT_IN_CONFIGURATION_TRANSACTION";
      String subsystem = "REST";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRestrictedToDevelopmentMode() {
      String id = "RESTRICTED_TO_DEVELOPMENT_MODE";
      String subsystem = "REST";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUnsupportedBeanProperty(String arg0) {
      String id = "UNSUPPORTED_BEAN_PROPERTY";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgIncludeAndExcludeFieldsSpecified(String arg0, String arg1) {
      String id = "INCLUDE_AND_EXCLUDE_FIELDS_SPECIFIED";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgOverLappingFieldsSpecified(String arg0) {
      String id = "OVERLAPPING_FIELDS_SPECIFIED";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSetPropertyError(String arg0, String arg1) {
      String id = "SET_PROPERTY_ERROR";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgModelConstraintViolations(String arg0) {
      String id = "MODEL_CONSTRAINT_VIOLATIONS";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgPropertyConstraintViolation(String arg0, String arg1) {
      String id = "PROPERTY_CONSTRAINT_VIOLATION";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMethodNotAllowed() {
      String id = "METHOD_NOT_ALLOWED";
      String subsystem = "REST";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFieldLessThanMin(String arg0, double arg1) {
      String id = "FIELD_LESS_THAN_MIN";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMissingRequiredField(String arg0) {
      String id = "MISSING_REQUIRED_FIELD";
      String subsystem = "REST";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
