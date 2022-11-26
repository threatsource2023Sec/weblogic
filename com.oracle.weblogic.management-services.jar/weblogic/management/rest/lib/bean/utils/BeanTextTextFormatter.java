package weblogic.management.rest.lib.bean.utils;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class BeanTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public BeanTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.rest.lib.bean.utils.BeanTextTextLocalizer", BeanTextTextFormatter.class.getClassLoader());
   }

   public BeanTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.rest.lib.bean.utils.BeanTextTextLocalizer", BeanTextTextFormatter.class.getClassLoader());
   }

   public static BeanTextTextFormatter getInstance() {
      return new BeanTextTextFormatter();
   }

   public static BeanTextTextFormatter getInstance(Locale l) {
      return new BeanTextTextFormatter(l);
   }

   public String msgStartEditFailed() {
      String id = "START_EDIT_FAILED";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCancelEditFailed() {
      String id = "CANCEL_EDIT_FAILED";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgActivateFailed() {
      String id = "ACTIVATE_FAILED";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgResolveFailed() {
      String id = "RESOLVE_FAILED";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSecurityOperationAdminServerNeedsReboot() {
      String id = "SECURITY_OPERATION_ADMIN_SERVER_NEEDS_REBOOT";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCannotEditReadOnlyExistingOptionalSingleton() {
      String id = "CANNOT_EDIT_READ_ONLY_EXISTING_OPTIONAL_SINGLETON";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCannotCreateNonExistingOptionalSingleton() {
      String id = "CANNOT_CREATE_NON_EXISTING_OPTIONAL_SINGLETON";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgGETJobFailed(String arg0, int arg1, String arg2) {
      String id = "GET_JOB_FAILED";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgJsonToJavaTypeMismatch(String arg0, String arg1) {
      String id = "JSON_TO_JAVA_TYPE_MISSMATCH";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMissingVBeanProperty(String arg0) {
      String id = "MISSING_VBEAN_PROPERTY";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMarshalUnknownPropertyType(String arg0) {
      String id = "MARSHAL_UNKNOWN_PROPERTY_TYPE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMarshalUnknownType(String arg0) {
      String id = "MARSHAL_UNKNOWN_TYPE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUnmarshalUnknownType(String arg0) {
      String id = "UNMARSHAL_UNKNOWN_TYPE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUnmarshalUnknownPropertyType(String arg0) {
      String id = "UNMARSHAL_UNKNOWN_PROPERTY_TYPE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgBeanNotFound(String arg0) {
      String id = "BEAN_NOT_FOUND";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNonExistentEditSession(String arg0) {
      String id = "NON_EXISTENT_EDIT_SESSION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCannotDetermineDefaultEditSession(String arg0, String arg1, String arg2) {
      String id = "CANNOT_DETERMINE_DEFAULT_EDIT_SESSION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAmbiguousMethod() {
      String id = "AMBIGUOUS_METHOD";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidParameters(String arg0) {
      String id = "INVALID_METHOD";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidExpandedValue(String arg0) {
      String id = "INVALID_EXPANDED_VALUE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMissingValueProperty() {
      String id = "MISSING_VALUE_PROPERTY";
      String subsystem = "management-services";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidQueryPropertyNotAnObject(String arg0) {
      String id = "INVALID_QUERY_PROPERTY_NOT_AN_OBJECT";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidQueryPropertyContainsNonObjects(String arg0) {
      String id = "INVALID_QUERY_PROPERTY_CONTAINS_NON_OBJECTS";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidQueryPropertyNotAnArray(String arg0) {
      String id = "INVALID_QUERY_PROPERTY_NOT_AN_ARRAY";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidQueryPropertyNotAStringArray(String arg0) {
      String id = "INVALID_QUERY_PROPERTY_NOT_A_STRING_ARRAY";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgGetNotAuthorized(String arg0, String arg1, String arg2) {
      String id = "GET_NOT_AUTHORIZED";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgGetNotAuthorizedForApplication(String arg0, String arg1, String arg2, String arg3) {
      String id = "GET_NOT_AUTHORIZED_FOR_APPLICATION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSetNotAuthorized(String arg0, String arg1, String arg2) {
      String id = "SET_NOT_AUTHORIZED";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSetNotAuthorizedForApplication(String arg0, String arg1, String arg2, String arg3) {
      String id = "SET_NOT_AUTHORIZED_FOR_APPLICATION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvokeNotAuthorized(String arg0, String arg1, String arg2) {
      String id = "INVOKE_NOT_AUTHORIZED";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvokeNotAuthorizedForApplication(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVOKE_NOT_AUTHORIZED_FOR_APPLICATION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgPropertyInvokeNotAuthorized(String arg0, String arg1, String arg2, String arg3) {
      String id = "PROPERTY_INVOKE_NOT_AUTHORIZED";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgPropertyInvokeNotAuthorizedForApplication(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "PROPERTY_INVOKE_NOT_AUTHORIZED_FOR_APPLICATION";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCreateNotSupportedForType(String arg0, String arg1) {
      String id = "CREATE_NOT_SUPPORTED_FOR_TYPE";
      String subsystem = "management-services";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
