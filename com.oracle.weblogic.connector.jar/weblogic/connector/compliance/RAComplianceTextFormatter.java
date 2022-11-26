package weblogic.connector.compliance;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class RAComplianceTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public RAComplianceTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.connector.compliance.RAComplianceTextLocalizer", RAComplianceTextFormatter.class.getClassLoader());
   }

   public RAComplianceTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.connector.compliance.RAComplianceTextLocalizer", RAComplianceTextFormatter.class.getClassLoader());
   }

   public static RAComplianceTextFormatter getInstance() {
      return new RAComplianceTextFormatter();
   }

   public static RAComplianceTextFormatter getInstance(Locale l) {
      return new RAComplianceTextFormatter(l);
   }

   public String ELEMENT_IS_EMPTY(String arg0, String arg1) {
      String id = "ELEMENT_IS_EMPTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MUST_IMPLEMENT_INTERFACE(String arg0, String arg1, String arg2, String arg3) {
      String id = "MUST_IMPLEMENT_INTERFACE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_RA_BEAN() {
      String id = "MISSING_RA_BEAN";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SHOULD_NOT_OVERRIDE(String arg0, String arg1, String arg2) {
      String id = "SHOULD_NOT_OVERRIDE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ZERO_LENGTH_NAME(String arg0, String arg1, String arg2) {
      String id = "ZERO_LENGTH_NAME";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CLASS_NOT_FOUND(String arg0, String arg1, String arg2) {
      String id = "CLASS_NOT_FOUND";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COULDNT_LOAD_CLASS(String arg0, String arg1, String arg2, String arg3) {
      String id = "COULDNT_LOAD_CLASS";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MUST_OVERRIDE(String arg0, String arg1, String arg2) {
      String id = "MUST_OVERRIDE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SHOULD_IMPLEMENT_INTERFACE(String arg0, String arg1, String arg2, String arg3) {
      String id = "SHOULD_IMPLEMENT_INTERFACE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FILE_NOT_FOUND(String arg0, String arg1, String arg2) {
      String id = "FILE_NOT_FOUND";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NOT_A_JAVA_BEAN(String arg0, String arg1, String arg2, String arg3) {
      String id = "NOT_A_JAVA_BEAN";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_JNDI_NAME() {
      String id = "MISSING_JNDI_NAME";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_RA_BEAN_FOR_INBOUND() {
      String id = "MISSING_RA_BEAN_FOR_INBOUND";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_RA_XML() {
      String id = "MISSING_RA_XML";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_ADMIN_INTERFACE(String arg0) {
      String id = "NO_MATCHING_ADMIN_INTERFACE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_CONN_FACTORY_INTERFACE(String arg0) {
      String id = "NO_MATCHING_CONN_FACTORY_INTERFACE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_RA_PROPERTY(String arg0, String arg1) {
      String id = "DUPLICATE_RA_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_WLRA_PROPERTY(String arg0, String arg1) {
      String id = "DUPLICATE_WLRA_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_RA_PROPERTY_MULTI_TYPES(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "DUPLICATE_RA_PROPERTY_MULTI_TYPES";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_RA_PROPERTY(String arg0, String arg1, String arg2) {
      String id = "MISSING_RA_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_CF_PROPERTY(String arg0, String arg1, String arg2, String arg3) {
      String id = "MISSING_CF_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_AO_PROPERTY(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "MISSING_AO_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NULL_PROPERTY_NAME(String arg0, String arg1, String arg2) {
      String id = "NULL_PROPERTY_NAME";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PROPERTY_TYPE_VALUE_MISMATCH(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "PROPERTY_TYPE_VALUE_MISMATCH";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_PROPERTY_TYPE(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_PROPERTY_TYPE(";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SET_METHOD_FOR_PROPERTY(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "NO_SET_METHOD_FOR_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PROPERTY_TYEPE_MISMATCH(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "PROPERTY_TYEPE_MISMATCH";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NULL_PROPERTYDESCRIPTOR_TYPE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "NULL_PROPERTYDESCRIPTOR_TYPE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MUST_SPECIFY_ADAPTERBEN_INDD(String arg0) {
      String id = "MUST_SPECIFY_ADAPTERBEN_INDD";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_PROPERTY_TYPE(String arg0, String arg1, String arg2) {
      String id = "CONFIGPROPERTY_NOT_VALID_TYPE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONFIGPROPERTY_NOT_VALID_TYPE_MATCH(String arg0, String arg1) {
      String id = "CONFIGPROPERTY_NOT_VALID_TYPE_MATCH";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONFIGPROPERTY_FEILD_IS_NOT_PROPERTY(String arg0, String arg1) {
      String id = "CONFIGPROPERTY_FEILD_IS_NOT_PROPERTY";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONFIGPROPERTY_NOT_VALID_SETTER_METHOD(String arg0, String arg1) {
      String id = "CONFIGPROPERTY_NOT_VALID_SETTER_METHOD";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONFIGPROPERTY_NOT_VALID_SETTER_NO_PROPERTY_FOUND(String arg0, String arg1, String arg2) {
      String id = "CONFIGPROPERTY_NOT_VALID_SETTER_NO_PROPERTY_FOUND";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CANT_DETERMINE_ADMIN_INTERFACE(String arg0) {
      String id = "CANT_DETERMINE_ADMIN_INTERFACE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONNECTOR_MUST_ON_ADATERBEN(String arg0) {
      String id = "CONNECTOR_MUST_ON_ADATERBEN";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CONNECTION_MUST_ON_MCF(String arg0) {
      String id = "CONNECTION_MUST_ON_MCF";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String requiredWorkContexts_ClassNotFound(String arg0, String arg1) {
      String id = "requiredWorkContexts_ClassNotFound";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String requiredWorkContexts_NotImplementWorkContext(String arg0) {
      String id = "requiredWorkContexts_NotImplementWorkContext";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String requiredWorkContexts_NotSupported(String arg0) {
      String id = "requiredWorkContexts_NotSupported";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String adminObjectInterfaceNotUnique(String arg0) {
      String id = "adminObjectInterfaceNotUnique";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noMatchingAdminInterfaceAndClass(String arg0, String arg1) {
      String id = "noMatchingAdminInterfaceAndClass";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ANNOTATION_TYPE(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_ANNOTATION_TYPE";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ANNOTATION(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_ANNOTATION";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String principalNameNotEmpty(String arg0) {
      String id = "principalNameNotEmpty";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noJNDINameForAdminObjectInstance(String arg0, String arg1) {
      String id = "noJNDINameForAdminObjectInstance";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noJNDINameForConnectionInstance(String arg0) {
      String id = "noJNDINameForConnectionInstance";
      String subsystem = "Connector Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
