package weblogic.servlet.internal.dd.compliance;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class WebAppComplianceTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public WebAppComplianceTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.servlet.internal.dd.compliance.WebAppComplianceTextLocalizer", WebAppComplianceTextFormatter.class.getClassLoader());
   }

   public WebAppComplianceTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.servlet.internal.dd.compliance.WebAppComplianceTextLocalizer", WebAppComplianceTextFormatter.class.getClassLoader());
   }

   public static WebAppComplianceTextFormatter getInstance() {
      return new WebAppComplianceTextFormatter();
   }

   public static WebAppComplianceTextFormatter getInstance(Locale l) {
      return new WebAppComplianceTextFormatter(l);
   }

   public String NO_SERVLET_NAME() {
      String id = "NO_SERVLET_NAME";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String error() {
      String id = "ERROR";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warning() {
      String id = "WARNING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MULTIPLE_DEFINES_SERVLET_DEF(String arg0) {
      String id = "MULTIPLE_DEFINES_SERVLET_DEF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SERVLET_DEF(String arg0) {
      String id = "NO_SERVLET_DEF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CHECKING_SERVLET(String arg0) {
      String id = "CHECKING_SERVLET";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CHECKING_SERVLET_DONE(String arg0) {
      String id = "CHECKING_SERVLET_DONE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_URL_PATTERN(String arg0) {
      String id = "NO_URL_PATTERN";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SERVLET_DEF_FOR_MAPPING(String arg0) {
      String id = "NO_SERVLET_DEF_FOR_MAPPING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CHECKING_SERVLET_MAPPING(String arg0) {
      String id = "CHECKING_SERVLET_MAPPING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_SERVLET_DEF(String arg0) {
      String id = "DUPLICATE_SERVLET_DEF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SECURITY_ROLE_FOR_RUNAS(String arg0, String arg1) {
      String id = "NO_SECURITY_ROLE_FOR_RUNAS";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_FILTER_DEF(String arg0) {
      String id = "DUPLICATE_FILTER_DEF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MAPPING_FOR_FILTER(String arg0) {
      String id = "NO_MAPPING_FOR_FILTER";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_FILTER_NAME() {
      String id = "NO_FILTER_NAME";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_FILTER_CLASS(String arg0) {
      String id = "NO_FILTER_CLASS";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CHECKING_FILTER_MAPPING(String arg0) {
      String id = "CHECKING_FILTER_MAPPING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_FILTER_DEF_FOR_MAPPING(String arg0) {
      String id = "NO_FILTER_DEF_FOR_MAPPING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_URL_PATTERN_FOR_FILTER(String arg0) {
      String id = "NO_URL_PATTERN_FOR_FILTER";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SERVLET_DEF_FOR_FILTER(String arg0, String arg1) {
      String id = "NO_SERVLET_DEF_FOR_FILTER";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ERROR_CODE(String arg0) {
      String id = "INVALID_ERROR_CODE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MULTIPLE_DEFINES_ERROR_PAGE(String arg0, String arg1) {
      String id = "MULTIPLE_DEFINES_ERROR_PAGE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_ERROR_PAGE_LOCATION_TYPE(String arg0) {
      String id = "NO_ERROR_PAGE_LOCATION_TYPE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_ERROR_PAGE_LOCATION_CODE(String arg0) {
      String id = "NO_ERROR_PAGE_LOCATION_CODE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CLASS_NOT_FOUND(String arg0, String arg1) {
      String id = "CLASS_NOT_FOUND";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_ERROR_DEF(String arg0, String arg1) {
      String id = "DUPLICATE_ERROR_DEF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CLASS_NOT_ASSIGNABLE_FROM(String arg0, String arg1, String arg2) {
      String id = "CLASS_NOT_ASSIGNABLE_FROM";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_SESSION_TIMEOUT(String arg0) {
      String id = "INVALID_SESSION_TIMEOUT";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_TAGLIB_URI() {
      String id = "NO_TAGLIB_URI";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_TAGLIB_LOCATION() {
      String id = "NO_TAGLIB_LOCATION";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_URL_PATTERN(String arg0) {
      String id = "ILLEGAL_URL_PATTERN";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_TRANSPORT_GUARANTEE(String arg0) {
      String id = "INVALID_TRANSPORT_GUARANTEE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_RESOURCE_NAME(String arg0) {
      String id = "DUPLICATE_RESOURCE_NAME";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SECURITY_ROLE_FOR_AUTH(String arg0) {
      String id = "NO_SECURITY_ROLE_FOR_AUTH";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_LISTENER_CLASS(String arg0) {
      String id = "INVALID_LISTENER_CLASS";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_EJBLINK_AND_JNDI_NAME(String arg0, String arg1) {
      String id = "NO_EJBLINK_AND_JNDI_NAME";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_EJB_REF_TYPE(String arg0) {
      String id = "INVALID_EJB_REF_TYPE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ENV_ENTRY_TYPE(String arg0) {
      String id = "INVALID_ENV_ENTRY_TYPE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ENV_ENTRY_VALUE(String arg0, String arg1) {
      String id = "INVALID_ENV_ENTRY_VALUE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String UNSUPPORTED_ENCODING(String arg0, String arg1) {
      String id = "UNSUPPORTED_ENCODING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_LOCAL_PATH(String arg0) {
      String id = "INVALID_LOCAL_PATH";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_RES_DESC_FOR_RESOURCE_REF(String arg0) {
      String id = "NO_RES_DESC_FOR_RESOURCE_REF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_SHARING_SCOPE(String arg0, String arg1) {
      String id = "INVALID_SHARING_SCOPE";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_JNDI_NAME_FOR_RESOURCE_DESCRIPTOR(String arg0) {
      String id = "NO_JNDI_NAME_FOR_RESOURCE_DESCRIPTOR";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_RES_AUTH(String arg0, String arg1) {
      String id = "INVALID_RES_AUTH";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_COOKIE_DOMAIN(String arg0) {
      String id = "INVALID_COOKIE_DOMAIN";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_RES_DESC_FOR_ENV_REF(String arg0) {
      String id = "NO_RES_DESC_FOR_ENV_REF";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STAR_JSP_URL_PATTERN(String arg0) {
      String id = "STAR_JSP_URL_PATTERN";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_AUTH_METHOD(String arg0) {
      String id = "INVALID_AUTH_METHOD";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DIGEST_NOT_IMPLEMENTED() {
      String id = "DIGEST_NOT_IMPLEMENTED";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOGIN_PAGE_MISSING() {
      String id = "LOGIN_PAGE_MISSING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ERROR_PAGE_MISSING() {
      String id = "ERROR_PAGE_MISSING";
      String subsystem = "WebApp Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
