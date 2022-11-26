package weblogic.ejb.container.compliance;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class EJBComplianceTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public EJBComplianceTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.compliance.EJBComplianceTextLocalizer", EJBComplianceTextFormatter.class.getClassLoader());
   }

   public EJBComplianceTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.ejb.container.compliance.EJBComplianceTextLocalizer", EJBComplianceTextFormatter.class.getClassLoader());
   }

   public static EJBComplianceTextFormatter getInstance() {
      return new EJBComplianceTextFormatter();
   }

   public static EJBComplianceTextFormatter getInstance(Locale l) {
      return new EJBComplianceTextFormatter(l);
   }

   public String warning() {
      String id = "WARNING";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String error() {
      String id = "ERROR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logWriteError(String arg0, String arg1) {
      String id = "LOG_WRITE_ERROR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMessage() {
      String id = "ERROR_MESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PUBLIC_EJBCREATE(String arg0) {
      String id = "PUBLIC_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINAL_EJBCREATE(String arg0) {
      String id = "FINAL_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATIC_EJBCREATE(String arg0) {
      String id = "STATIC_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ENTITY_IMPLEMENT_SESSIONSYNCHRONIZATION(String arg0) {
      String id = "ENTITY_IMPLEMENT_SESSIONSYNCHRONIZATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATELESS_IMPLEMENT_SESSIONSYNCHRONIZATION(String arg0) {
      String id = "STATELESS_IMPLEMENT_SESSIONSYNCHRONIZATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SINGLETON_METHOD_CANNOT_USE_SESSIONSYNCHRONIZATION_ANNOTATION(String arg0, String arg1) {
      String id = "SINGLETON_METHOD_CANNOT_USE_SESSIONSYNCHRONIZATION_ANNOTATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_ACCESS_TIMEOUT_VALUE(String arg0, String arg1) {
      String id = "WRONG_ACCESS_TIMEOUT_VALUE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_STATEFUL_TIMEOUT_VALUE(String arg0) {
      String id = "WRONG_STATEFUL_TIMEOUT_VALUE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFUL_SESSION_BEAN_MUST_NOT_HAVE_LOCKING_METADATA(String arg0, String arg1) {
      String id = "STATEFUL_SESSION_BEAN_MUST_NOT_HAVE_LOCKING_METADATA";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SINGLETON_IMPLEMENT_SESSIONSYNCHRONIZATION(String arg0) {
      String id = "SINGLETON_IMPLEMENT_SESSIONSYNCHRONIZATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MANAGED_IMPLEMENT_SESSIONSYNCHRONIZATION(String arg0) {
      String id = "BEAN_MANAGED_IMPLEMENT_SESSIONSYNCHRONIZATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PUBLIC_BEAN_CLASS(String arg0) {
      String id = "PUBLIC_BEAN_CLASS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINAL_BEAN_CLASS(String arg0) {
      String id = "FINAL_BEAN_CLASS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PUBLIC_NOARG_BEAN_CTOR(String arg0) {
      String id = "PUBLIC_NOARG_BEAN_CTOR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_FINALIZE_IN_BEAN(String arg0) {
      String id = "NO_FINALIZE_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SYNCHRONIZED_METHODS(String arg0, String arg1) {
      String id = "NO_SYNCHRONIZED_METHODS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MUST_NOT_THROW_REMOTE_EXCEPTION(String arg0, String arg1) {
      String id = "BEAN_MUST_NOT_THROW_REMOTE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MUST_NOT_THROW_RUNTIME_EXCEPTION(String arg0, String arg1) {
      String id = "BEAN_MUST_NOT_THROW_RUNTIME_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NOT_ALL_BEANS_USE_SAME_PERSISTENCE(String arg0) {
      String id = "NOT_ALL_BEANS_USE_SAME_PERSISTENCE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ABSTRACT_SCHEMA_NAME_NOT_UNIQUE(String arg0) {
      String id = "ABSTRACT_SCHEMA_NAME_NOT_UNIQUE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ABSTRACT_BEAN_CLASS(String arg0) {
      String id = "ABSTRACT_BEAN_CLASS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ISMODIFIED_NOT_EXIST(String arg0, String arg1) {
      String id = "ISMODIFIED_NOT_EXIST";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ISMODIFIED_RETURNS_BOOL(String arg0, String arg1) {
      String id = "ISMODIFIED_RETURNS_BOOL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_FIELDS_MUST_BE_BEAN_FIELDS(String arg0, String arg1) {
      String id = "CMP_FIELDS_MUST_BE_BEAN_FIELDS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_FIELDS_MUST_BE_PUBLIC(String arg0, String arg1) {
      String id = "CMP_FIELDS_MUST_BE_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_FIELDS_MUST_NOT_BE_STATIC(String arg0, String arg1) {
      String id = "CMP_FIELDS_MUST_NOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELD_MUST_EXIST(String arg0, String arg1) {
      String id = "PK_FIELD_MUST_EXIST";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELD_WRONG_TYPE(String arg0, String arg1, String arg2) {
      String id = "PK_FIELD_WRONG_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP11_CANNOT_USE_OPTIMISTIC_CONCURRENCY(String arg0) {
      String id = "CMP11_CANNOT_USE_OPTIMISTIC_CONCURRENCY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CM_FIELD_MUST_START_WITH_LOWERCASE(String arg0, String arg1) {
      String id = "CM_FIELD_MUST_START_WITH_LOWERCASE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DEFINE_CMP_ACCESSOR_METHOD_20(String arg0, String arg1) {
      String id = "DEFINE_CMP_ACCESSOR_METHOD_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SETTER_DOES_NOT_RETURN_VOID(String arg0) {
      String id = "SETTER_DOES_NOT_RETURN_VOID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_ACCESSOR_NOT_PUBLIC(String arg0, String arg1) {
      String id = "CMP_ACCESSOR_NOT_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SETTER_DOES_NOT_HAVE_SINGLE_PARAM(String arg0, String arg1) {
      String id = "SETTER_DOES_NOT_HAVE_SINGLE_PARAM";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SETTER_PARAM_DOES_NOT_MATCH_GETTER_RETURN(String arg0, String arg1) {
      String id = "SETTER_PARAM_DOES_NOT_MATCH_GETTER_RETURN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DO_NOT_DEFINE_CMFIELD_20(String arg0) {
      String id = "DO_NOT_DEFINE_CMFIELD_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_SELECT_CANNOT_RETURN_ENUMERATION(String arg0, String arg1) {
      String id = "EJB_SELECT_CANNOT_RETURN_ENUMERATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_SELECT_MUST_THROW(String arg0, String arg1) {
      String id = "EJB_SELECT_MUST_THROW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_ENTITY_BEAN_METHOD(String arg0, String arg1) {
      String id = "MISSING_ENTITY_BEAN_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_GETTER(String arg0, String arg1, String arg2) {
      String id = "PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_GETTER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_SETTER(String arg0, String arg1, String arg2) {
      String id = "PRIMKEY_CLASS_DOES_NOT_MATCH_ACCESSOR_FOR_SETTER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_GETTER(String arg0, String arg1, String arg2, String arg3) {
      String id = "BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_GETTER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_SETTER(String arg0, String arg1, String arg2, String arg3) {
      String id = "BEAN_PK_CLASS_DOES_NOT_MATCH_PKFIELD_FOR_SETTER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BMP_CANNOT_USE_OPTIMISTIC_CONCURRENCY(String arg0) {
      String id = "BMP_CANNOT_USE_OPTIMISTIC_CONCURRENCY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String jarFileMissing(String arg0) {
      String id = "jarFileMissing";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String checkingJarFile(String arg0) {
      String id = "checkingJarFile";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String jarFileIsDirectory(String arg0) {
      String id = "jarFileIsDirectory";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String compliant(String arg0) {
      String id = "compliant";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String notValid(String arg0, IOException arg1) {
      String id = "notValid";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToParse(String arg0, Exception arg1) {
      String id = "failedToParse";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToLoad(String arg0, Exception arg1) {
      String id = "failedToLoad";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String complianceError(String arg0) {
      String id = "complianceError";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String loadFailure(String arg0) {
      String id = "loadFailure";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_IMPLEMENTS_EJBOBJECT(String arg0) {
      String id = "EO_IMPLEMENTS_EJBOBJECT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_IMPLEMENTS_EJB_LOCAL_OBJECT(String arg0) {
      String id = "ELO_IMPLEMENTS_EJB_LOCAL_OBJECT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_THROWS_REMOTE_EXCEPTION(String arg0, String arg1) {
      String id = "EO_THROWS_REMOTE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_THROWS_REMOTE_EXCEPTION(String arg0, String arg1) {
      String id = "ELO_THROWS_REMOTE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_RETURN_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "EO_RETURN_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_RETURN_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "ELO_RETURN_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "EO_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "ELO_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "EO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "ELO_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_APPLICATION_EXCEPTION(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_APPLICATION_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_APPLICATION_EXCEPTION_ON_HOME(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_APPLICATION_EXCEPTION_ON_HOME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CANNOT_EXPOSE_RELATIONSHIP_ACCESSOR_IN_REMOTE(String arg0, String arg1, String arg2) {
      String id = "CANNOT_EXPOSE_RELATIONSHIP_ACCESSOR_IN_REMOTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATES_MATCH_POSTCREATE(String arg0, String arg1) {
      String id = "CREATES_MATCH_POSTCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EXTRA_POSTCREATE(String arg0, String arg1) {
      String id = "EXTRA_POSTCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MUST_IMPLEMENT_ENTITYBEAN(String arg0) {
      String id = "MUST_IMPLEMENT_ENTITYBEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBCREATE_RETURNS_PK(String arg0, String arg1) {
      String id = "EJBCREATE_RETURNS_PK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBPOSTCREATE_MUST_BE_PUBLIC(String arg0, String arg1) {
      String id = "EJBPOSTCREATE_MUST_BE_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBPOSTCREATE_MUST_NOT_BE_FINAL(String arg0, String arg1) {
      String id = "EJBPOSTCREATE_MUST_NOT_BE_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBPOSTCREATE_MUST_NOT_BE_STATIC(String arg0, String arg1) {
      String id = "EJBPOSTCREATE_MUST_NOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBPOSTCREATE_MUST_RETURN_VOID(String arg0, String arg1) {
      String id = "EJBPOSTCREATE_MUST_RETURN_VOID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINDER_IN_CMP_BEAN(String arg0, String arg1) {
      String id = "FINDER_IN_CMP_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MISSING_PERSISTENCE_USE(String arg0) {
      String id = "BEAN_MISSING_PERSISTENCE_USE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_FIELD_CLASS_NOT_SUPPORTED_IN_CMP11(String arg0, String arg1, String arg2) {
      String id = "CMP_FIELD_CLASS_NOT_SUPPORTED_IN_CMP11";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PRIMKEY_FIELD_MUST_BE_CMP_FIELD(String arg0) {
      String id = "PRIMKEY_FIELD_MUST_BE_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELDS_MUST_BE_CMP_FIELDS(String arg0, String arg1) {
      String id = "PK_FIELDS_MUST_BE_CMP_FIELDS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CACHE_BETWEEN_TRANS_MUST_BE_FALSE_FOR_CONCURRENCY_DB(String arg0) {
      String id = "CACHE_BETWEEN_TRANS_MUST_BE_FALSE_FOR_CONCURRENCY_DB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_MUST_HAVE_FIND_PK(String arg0) {
      String id = "HOME_MUST_HAVE_FIND_PK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_HOME_MUST_HAVE_FIND_PK(String arg0) {
      String id = "LOCAL_HOME_MUST_HAVE_FIND_PK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIND_BY_PK_RETURNS_REMOTE_INTF(String arg0) {
      String id = "FIND_BY_PK_RETURNS_REMOTE_INTF";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIND_BY_PK_RETURNS_LOCAL_INTF(String arg0) {
      String id = "FIND_BY_PK_RETURNS_LOCAL_INTF";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_FIND_PK_CORRECT_PARAMETERS(String arg0, String arg1) {
      String id = "HOME_FIND_PK_CORRECT_PARAMETERS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_HOME_FIND_PK_CORRECT_PARAMETERS(String arg0, String arg1) {
      String id = "LOCAL_HOME_FIND_PK_CORRECT_PARAMETERS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINDER_MUST_THROW_FE(String arg0, String arg1) {
      String id = "FINDER_MUST_THROW_FE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_FINDER_MUST_THROW_FE(String arg0, String arg1) {
      String id = "LOCAL_FINDER_MUST_THROW_FE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINDER_RETURNS_BAD_TYPE(String arg0, String arg1) {
      String id = "FINDER_RETURNS_BAD_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_FINDER_RETURNS_BAD_TYPE(String arg0, String arg1) {
      String id = "LOCAL_FINDER_RETURNS_BAD_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SCALAR_FINDER_DOESNT_RETURN_REMOTE_INTF(String arg0, String arg1) {
      String id = "SCALAR_FINDER_DOESNT_RETURN_REMOTE_INTF";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SCALAR_FINDER_DOESNT_RETURN_LOCAL_INTF(String arg0, String arg1) {
      String id = "SCALAR_FINDER_DOESNT_RETURN_LOCAL_INTF";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ENUM_FINDER_DOESNT_RETURN_REMOTE_INTF(String arg0, String arg1) {
      String id = "ENUM_FINDER_DOESNT_RETURN_REMOTE_INTF";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COLL_FINDER_DOESNT_RETURN_COLL(String arg0, String arg1) {
      String id = "COLL_FINDER_DOESNT_RETURN_COLL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_COLL_FINDER_DOESNT_RETURN_COLL(String arg0, String arg1) {
      String id = "LOCAL_COLL_FINDER_DOESNT_RETURN_COLL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String UNEXPECTED_FINDER_RETURN_TYPE(String arg0, String arg1) {
      String id = "UNEXPECTED_FINDER_RETURN_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIND_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "FIND_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "LOCAL_FIND_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String POST_CREATE_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "POST_CREATE_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String POST_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "POST_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EXTRA_HOME_METHOD_20(String arg0, String arg1, String arg2) {
      String id = "EXTRA_HOME_METHOD_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EXTRA_LOCAL_HOME_METHOD_20(String arg0, String arg1, String arg2) {
      String id = "EXTRA_LOCAL_HOME_METHOD_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_METHOD_NAME_IN_BEAN_CLASS_LOWER_CASE_20(String arg0, String arg1, String arg2) {
      String id = "HOME_METHOD_NAME_IN_BEAN_CLASS_LOWER_CASE_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_METHOD_NAME_IN_BEAN_CLASS_INCOMPLETE_20(String arg0) {
      String id = "HOME_METHOD_NAME_IN_BEAN_CLASS_INCOMPLETE_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_CMR_FIELD(String arg0) {
      String id = "DUPLICATE_CMR_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_EXTENDS_EJBHOME(String arg0, String arg1) {
      String id = "HOME_EXTENDS_EJBHOME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_HOME_EXTENDS_EJBLOCALHOME(String arg0, String arg1) {
      String id = "LOCAL_HOME_EXTENDS_EJBLOCALHOME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NOT_RMIIIOP_LEGAL_TYPE_20(String arg0) {
      String id = "NOT_RMIIIOP_LEGAL_TYPE_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_METHOD_NOT_THROW_REMOTE_EXCEPTION(String arg0, String arg1) {
      String id = "HOME_METHOD_NOT_THROW_REMOTE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_HOME_METHOD_THROW_REMOTE_EXCEPTION(String arg0, String arg1) {
      String id = "LOCAL_HOME_METHOD_THROW_REMOTE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATE_METHOD_RETURNS_COMPONENT_INTERFACE(String arg0, String arg1) {
      String id = "CREATE_METHOD_RETURNS_COMPONENT_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATE_METHOD_RETURNS_LOCAL_COMPONENT_INTERFACE(String arg0, String arg1) {
      String id = "CREATE_METHOD_RETURNS_LOCAL_COMPONENT_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATE_METHOD_THROWS_CREATE_EXCEPTION(String arg0, String arg1) {
      String id = "CREATE_METHOD_THROWS_CREATE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_CREATE_METHOD_THROWS_CREATE_EXCEPTION(String arg0, String arg1) {
      String id = "LOCAL_CREATE_METHOD_THROWS_CREATE_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATE_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "CREATE_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_CREATE_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "LOCAL_CREATE_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN(String arg0, String arg1) {
      String id = "LOCAL_CREATE_EXCEPTION_TYPE_DOESNT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_CLASS_IMPLEMENTS_MESSAGE_DRIVEN(String arg0) {
      String id = "BEAN_CLASS_IMPLEMENTS_MESSAGE_DRIVEN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_CLASS_IMPLEMENTS_MESSAGE_LISTENER(String arg0, String arg1) {
      String id = "BEAN_CLASS_IMPLEMENTS_MESSAGE_LISTENER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PUBLIC_ONMESSAGE(String arg0) {
      String id = "PUBLIC_ONMESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINAL_ONMESSAGE(String arg0) {
      String id = "FINAL_ONMESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATIC_ONMESSAGE(String arg0) {
      String id = "STATIC_ONMESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SINGLE_ONMESSAGE_REQUIRED(String arg0) {
      String id = "SINGLE_ONMESSAGE_REQUIRED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ONMESSAGE_TAKES_MESSAGE(String arg0) {
      String id = "ONMESSAGE_TAKES_MESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ONMESSAGE_RETURNS_VOID(String arg0) {
      String id = "ONMESSAGE_RETURNS_VOID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ONMESSAGE_THROWS_APP_EXCEPTION(String arg0) {
      String id = "ONMESSAGE_THROWS_APP_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MUST_HAVE_ONMESSAGE(String arg0) {
      String id = "BEAN_MUST_HAVE_ONMESSAGE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MT_METHOD_DOESNT_EXIST_IN_BEAN(String arg0, String arg1) {
      String id = "MT_METHOD_DOESNT_EXIST_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBCREATE_RETURNS_VOID(String arg0) {
      String id = "EJBCREATE_RETURNS_VOID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_NOARG_EJBCREATE(String arg0) {
      String id = "MESSAGE_NOARG_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_EJBCREATE_THROWS_APP_EXCEPTION(String arg0) {
      String id = "MESSAGE_EJBCREATE_THROWS_APP_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_DEFINES_EJBCREATE(String arg0) {
      String id = "MESSAGE_DEFINES_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PUBLIC_EJBREMOVE(String arg0) {
      String id = "PUBLIC_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FINAL_EJBREMOVE(String arg0) {
      String id = "FINAL_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATIC_EJBREMOVE(String arg0) {
      String id = "STATIC_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBREMOVE_RETURNS_VOID(String arg0) {
      String id = "EJBREMOVE_RETURNS_VOID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_NOARG_EJBREMOVE(String arg0) {
      String id = "MESSAGE_NOARG_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_EJBREMOVE_THROWS_APP_EXCEPTION(String arg0) {
      String id = "MESSAGE_EJBREMOVE_THROWS_APP_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_DEFINES_EJBREMOVE(String arg0) {
      String id = "MESSAGE_DEFINES_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_BAD_TX_ATTRIBUTE(String arg0) {
      String id = "MESSAGE_BAD_TX_ATTRIBUTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_MUST_IMPLEMENT_HASHCODE(String arg0) {
      String id = "PK_MUST_IMPLEMENT_HASHCODE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_MUST_IMPLEMENT_EQUALS(String arg0) {
      String id = "PK_MUST_IMPLEMENT_EQUALS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_PK_MUST_IMPLEMENT_SERIALIZABLE(String arg0) {
      String id = "CMP_PK_MUST_IMPLEMENT_SERIALIZABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_PK_CANNOT_BE_JAVA_LANG_OBJECT(String arg0) {
      String id = "CMP_PK_CANNOT_BE_JAVA_LANG_OBJECT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_PK_MUST_BE_PUBLIC(String arg0, String arg1) {
      String id = "CMP_PK_MUST_BE_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP_PK_MUST_HAVE_NOARG_CONSTRUCTOR(String arg0, String arg1) {
      String id = "CMP_PK_MUST_HAVE_NOARG_CONSTRUCTOR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELDS_MUST_BE_PUBLIC(String arg0, String arg1, String arg2) {
      String id = "PK_FIELDS_MUST_BE_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELDS_MUST_NOT_BE_STATIC(String arg0, String arg1, String arg2) {
      String id = "PK_FIELDS_MUST_NOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String N1_RELATION_HAS_DUP_FIELD_FOR_SAME_BEAN(String arg0) {
      String id = "N1_RELATION_HAS_DUP_FIELD_FOR_SAME_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BOTH_ROLES_REMOTE(String arg0) {
      String id = "BOTH_ROLES_REMOTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NON_EXISTENT_BEAN_IN_ROLE(String arg0, String arg1) {
      String id = "NON_EXISTENT_BEAN_IN_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NON_ENTITY_BEAN_IN_ROLE(String arg0) {
      String id = "NON_ENTITY_BEAN_IN_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BM_BEAN_IN_ROLE(String arg0) {
      String id = "BM_BEAN_IN_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMP11_BEAN_IN_ROLE(String arg0) {
      String id = "CMP11_BEAN_IN_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIELD_NOT_DEFINED_FOR_ROLE(String arg0) {
      String id = "FIELD_NOT_DEFINED_FOR_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COLLECTION_FIELD_HAS_NO_TYPE(String arg0) {
      String id = "COLLECTION_FIELD_HAS_NO_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SINGLETON_FIELD_HAS_TYPE(String arg0) {
      String id = "SINGLETON_FIELD_HAS_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GET_METHOD_NOT_DEFINED_FOR_ROLE(String arg0, String arg1, String arg2) {
      String id = "GET_METHOD_NOT_DEFINED_FOR_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GET_METHOD_HAS_WRONG_RETURN_TYPE(String arg0, String arg1, String arg2) {
      String id = "GET_METHOD_HAS_WRONG_RETURN_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GET_METHOD_IS_NOT_ABSTRACT(String arg0, String arg1, String arg2) {
      String id = "GET_METHOD_IS_NOT_ABSTRACT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GET_METHOD_IS_NOT_PUBLIC(String arg0, String arg1, String arg2) {
      String id = "GET_METHOD_IS_NOT_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SET_METHOD_NOT_DEFINED_FOR_ROLE(String arg0, String arg1, String arg2) {
      String id = "SET_METHOD_NOT_DEFINED_FOR_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SET_METHOD_HAS_WRONG_RETURN_TYPE(String arg0, String arg1, String arg2) {
      String id = "SET_METHOD_HAS_WRONG_RETURN_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SET_METHOD_IS_NOT_ABSTRACT(String arg0, String arg1, String arg2) {
      String id = "SET_METHOD_IS_NOT_ABSTRACT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SET_METHOD_IS_NOT_PUBLIC(String arg0, String arg1, String arg2) {
      String id = "SET_METHOD_IS_NOT_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CMR_FIELD_SAME_AS_CMP_FIELD(String arg0) {
      String id = "CMR_FIELD_SAME_AS_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CASCADE_DELETE_CANNOT_BE_SPECIFIED(String arg0) {
      String id = "CASCADE_DELETE_CANNOT_BE_SPECIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBCONTEXT_IS_TRANSIENT(String arg0) {
      String id = "EJBCONTEXT_IS_TRANSIENT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_IMPLEMENT_SESSIONBEAN(String arg0) {
      String id = "BEAN_IMPLEMENT_SESSIONBEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATELESS_NOARG_EJBCREATE(String arg0) {
      String id = "STATELESS_NOARG_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFUL_DEFINE_EJBCREATE(String arg0) {
      String id = "STATEFUL_DEFINE_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFUL_HOME_CREATE(String arg0) {
      String id = "STATEFUL_HOME_CREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFUL_LOCAL_HOME_CREATE(String arg0) {
      String id = "STATEFUL_LOCAL_HOME_CREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATELESS_HOME_NOARG_CREATE(String arg0) {
      String id = "STATELESS_HOME_NOARG_CREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATELESS_LOCAL_HOME_NOARG_CREATE(String arg0) {
      String id = "STATELESS_LOCAL_HOME_NOARG_CREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String HOME_METHODS_NOT_ALLOWED_ON_SESSION_20(String arg0, String arg1) {
      String id = "HOME_METHODS_NOT_ALLOWED_ON_SESSION_20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_BEAN(String arg0) {
      String id = "NO_MATCHING_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String OVERLOADED_ABSTRACT_METHOD(String arg0, String arg1) {
      String id = "OVERLOADED_ABSTRACT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EXTRA_ABSTRACT_METHOD(String arg0, String arg1) {
      String id = "EXTRA_ABSTRACT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_FIELD_MAP(String arg0, String arg1) {
      String id = "NO_MATCHING_FIELD_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIELDCLASSTYPE_MUST_BE_STRING_FOR_ORACLECLOB_COLUMNTYPE(String arg0, String arg1, String arg2) {
      String id = "FIELDCLASSTYPE_MUST_BE_STRING_FOR_ORACLECLOB_COLUMNTYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_CMP_FIELD(String arg0, String arg1) {
      String id = "NO_MATCHING_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GROUP_CONTAINS_UNDEFINED_CMP_FIELD(String arg0, String arg1, String arg2) {
      String id = "GROUP_CONTAINS_UNDEFINED_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GROUP_CONTAINS_UNDEFINED_CMR_FIELD(String arg0, String arg1, String arg2) {
      String id = "GROUP_CONTAINS_UNDEFINED_CMR_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_CACHING_CANNOT_BE_SPECIFIED(String arg0, String arg1, String arg2) {
      String id = "RELATIONSHIP_CACHING_CANNOT_BE_SPECIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_CMR_FIELD(String arg0, String arg1, String arg2) {
      String id = "RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_CMR_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_GROUP_NAME(String arg0, String arg1, String arg2, String arg3) {
      String id = "RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_GROUP_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_CACHING_INCONSISTENT_CONCURRENCY_STRATEGY(String arg0, String arg1, String arg2) {
      String id = "RELATIONSHIP_CACHING_INCONSISTENT_CONCURRENCY_STRATEGY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_CACHING_REQUIRE_DATABASETYPE(String arg0) {
      String id = "RELATIONSHIP_CACHING_REQUIRE_DATABASETYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DelayDatabaseInsertUntilConflictEnableBatchOperations(String arg0) {
      String id = "DelayDatabaseInsertUntilConflictEnableBatchOperations";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GROUP_CONTAINS_PK_SUBSET(String arg0, String arg1) {
      String id = "GROUP_CONTAINS_PK_SUBSET";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CONTAINS_UNDEFINED_GROUP(String arg0, String arg1) {
      String id = "QUERY_CONTAINS_UNDEFINED_GROUP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CONTAINS_UNDEFINED_CACHING_NAME(String arg0, String arg1) {
      String id = "QUERY_CONTAINS_UNDEFINED_CACHING_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GENKEY_PK_IS_INTEGER(String arg0) {
      String id = "GENKEY_PK_IS_INTEGER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GENKEY_PK_SEQUENCE_WITH_UNSUPPORTED_DB(String arg0, String arg1) {
      String id = "GENKEY_PK_SEQUENCE_WITH_UNSUPPORTED_DB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GENKEY_PK_IDENTITY_WITH_UNSUPPORTED_DB(String arg0, String arg1) {
      String id = "GENKEY_PK_IDENTITY_WITH_UNSUPPORTED_DB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String GENKEY_PK_IS_INTEGER_OR_LONG(String arg0) {
      String id = "GENKEY_PK_IS_INTEGER_OR_LONG";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String AUTO_PK_KEY_CACHE_SIZE_NOT_SPECIFIED(String arg0) {
      String id = "AUTO_PK_KEY_CACHE_SIZE_NOT_SPECIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String VERSION_FIELD_WRONG_TYPE(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "VERSION_FIELD_WRONG_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMESTAMP_FIELD_WRONG_TYPE(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "TIMESTAMP_FIELD_WRONG_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_VERIFY_COLUMNS(String arg0, String arg1) {
      String id = "MISSING_VERIFY_COLUMNS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_VERIFY_COLUMNS(String arg0, String arg1, String arg2) {
      String id = "ILLEGAL_VERIFY_COLUMNS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_OPTIMISTIC_COLUMN(String arg0, String arg1, String arg2) {
      String id = "MISSING_OPTIMISTIC_COLUMN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_VERIFY_ROWS(String arg0, String arg1, String arg2) {
      String id = "ILLEGAL_VERIFY_ROWS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_VERIFY_READ_MODIFIED(String arg0, String arg1) {
      String id = "ILLEGAL_VERIFY_READ_MODIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NoSqlSelectDistinctWithBlobClobField(String arg0, String arg1, String arg2) {
      String id = "NoSqlSelectDistinctWithBlobClobField";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BLOB_CLOB_WITH_UNKNOWN_DB(String arg0) {
      String id = "BLOB_CLOB_WITH_UNKNOWN_DB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BLOB_CLOB_WITH_UNSUPPORTED_DB(String arg0, String arg1) {
      String id = "BLOB_CLOB_WITH_UNSUPPORTED_DB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DEPENDENT_OBJECTS_NOT_SUPPORTED() {
      String id = "DEPENDENT_OBJECTS_NOT_SUPPORTED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_CMP_BEAN(String arg0) {
      String id = "NO_MATCHING_CMP_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_EJB_RELATION(String arg0) {
      String id = "NO_MATCHING_EJB_RELATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_EJB_RELATION_IN_EJB_DD(String arg0) {
      String id = "NO_MATCHING_EJB_RELATION_IN_EJB_DD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_RELATION_FOR_BEAN(String arg0, String arg1) {
      String id = "MISSING_RELATION_FOR_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_BEAN_FOR_BEAN(String arg0, String arg1) {
      String id = "MISSING_BEAN_FOR_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_WL_RELATIONSHIP_ROLE(String arg0, String arg1) {
      String id = "NO_MATCHING_WL_RELATIONSHIP_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MANY_SIDE_OF_M_1_MUST_HAVE_FOREIGN_KEY_MAP(String arg0) {
      String id = "MANY_SIDE_OF_M_1_MUST_HAVE_FOREIGN_KEY_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ONE_SIDE_OF_M_1_MUST_NOT_HAVE_FOREIGN_KEY_MAP(String arg0) {
      String id = "ONE_SIDE_OF_M_1_MUST_NOT_HAVE_FOREIGN_KEY_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_MAY_NOT_REFER_TO_DEPENDT_OBJ(String arg0) {
      String id = "ROLE_MAY_NOT_REFER_TO_DEPENDT_OBJ";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_WL_RELATION(String arg0) {
      String id = "NO_MATCHING_WL_RELATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MATCHING_EJB_RELATIONSHIP_ROLE(String arg0, String arg1) {
      String id = "NO_MATCHING_EJB_RELATIONSHIP_ROLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MANY_TO_MANY_RELATIONSHIP_MUST_HAVE_BOTH_WL_ROLES(String arg0) {
      String id = "MANY_TO_MANY_RELATIONSHIP_MUST_HAVE_BOTH_WL_ROLES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MANY_TO_MANY_RELATIONSHIP_MISSING_TABLE_NAME(String arg0) {
      String id = "MANY_TO_MANY_RELATIONSHIP_MISSING_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATIONSHIP_ROLE_HAS_INVALID_GROUP(String arg0, String arg1) {
      String id = "RELATIONSHIP_ROLE_HAS_INVALID_GROUP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_HAS_WRONG_NUMBER_OF_COLUMNS_IN_MAP(String arg0, String arg1) {
      String id = "ROLE_HAS_WRONG_NUMBER_OF_COLUMNS_IN_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ONE_ONE_MISSING_COLUMN_MAP(String arg0) {
      String id = "ONE_ONE_MISSING_COLUMN_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MANY_MANY_MISSING_COLUMN_MAP(String arg0) {
      String id = "MANY_MANY_MISSING_COLUMN_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_HAS_INVALID_KEY_COLUMN_IN_MAP(String arg0, String arg1, String arg2) {
      String id = "ROLE_HAS_INVALID_KEY_COLUMN_IN_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_HAS_INVALID_KEY_COLUMN_IN_MAP2(String arg0, String arg1, String arg2, String arg3) {
      String id = "ROLE_HAS_INVALID_KEY_COLUMN_IN_MAP2";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FKCOLUMNS_MIX_OF_PK_NONPK_COLUMNS(String arg0, String arg1) {
      String id = "FKCOLUMNS_MIX_OF_PK_NONPK_COLUMNS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_FOREIGN_KEY_FIELD_CLASS(String arg0, String arg1, String arg2, String arg3) {
      String id = "INVALID_FOREIGN_KEY_FIELD_CLASS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CASCADE_DELETE_MUST_SPECIFIED_IF_DB_CASCADE_DELETE_SPECIFIED(String arg0) {
      String id = "CASCADE_DELETE_MUST_SPECIFIED_IF_DB_CASCADE_DELETE_SPECIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CASCADE_DELETE_MUST_HAVE_FOREIGN_KEY_MAP(String arg0) {
      String id = "CASCADE_DELETE_MUST_HAVE_FOREIGN_KEY_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ENTITYBEANINFOIMPL(String arg0) {
      String id = "ENTITYBEANINFOIMPL_INIT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN(String arg0, String arg1) {
      String id = "EO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ELO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN(String arg0, String arg1) {
      String id = "ELO_METHOD_SIGNATURE_DOES_NOT_MATCH_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_MAPPING_FOR_DBMS_COLUMN(String arg0, String arg1, String arg2) {
      String id = "DUPLICATE_MAPPING_FOR_DBMS_COLUMN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_MAPPING_FOR_CMP_FIELD(String arg0, String arg1, String arg2) {
      String id = "DUPLICATE_MAPPING_FOR_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_VALUE_FOR_CHECK_EXISTS(String arg0, String arg1, String arg2) {
      String id = "ILLEGAL_VALUE_FOR_CHECK_EXISTS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EMPTY_CLIENT_JAR_TAG() {
      String id = "EMPTY_CLIENT_JAR_TAG";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PRIMARY_KEY_WITHOUT_PRIMKEY_FIELD(String arg0, String arg1) {
      String id = "PRIMARY_KEY_WITHOUT_PRIMKEY_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CANNOT_FIND_WL_DESCRIPTOR_FOR_EJB(String arg0) {
      String id = "CANNOT_FIND_WL_DESCRIPTOR_FOR_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String CANNOT_FIND_CMP_DESCRIPTOR_FOR_EJB(String arg0) {
      String id = "CANNOT_FIND_CMP_DESCRIPTOR_FOR_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_ILLEGAL_MAX_BEANS_IN_FREE_POOL(String arg0, int arg1) {
      String id = "MESSAGE_ILLEGAL_MAX_BEANS_IN_FREE_POOL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MISSING_LREF_JNDI_NAME(String arg0, String arg1) {
      String id = "BEAN_MISSING_LREF_JNDI_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_MISSING_REF_JNDI_NAME(String arg0, String arg1) {
      String id = "BEAN_MISSING_REF_JNDI_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INCONSISTENT_REMOTE_VIEW(String arg0) {
      String id = "INCONSISTENT_REMOTE_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INCONSISTENT_LOCAL_VIEW(String arg0) {
      String id = "INCONSISTENT_LOCAL_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NULL_SECURITY_ROLE_REF_LINK(String arg0, String arg1) {
      String id = "NULL_SECURITY_ROLE_REF_LINK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_SECURITY_ROLE_REF_LINK(String arg0, String arg1) {
      String id = "INVALID_SECURITY_ROLE_REF_LINK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_NOT_MAPPED_TO_PRINCIPALS(String arg0) {
      String id = "ROLE_NOT_MAPPED_TO_PRINCIPALS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_CLIENT_VIEW(String arg0) {
      String id = "NO_CLIENT_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_QUERY_IN_EJBJAR(String arg0, String arg1) {
      String id = "MISSING_QUERY_IN_EJBJAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_QUERY_NAME(String arg0, String arg1) {
      String id = "INVALID_QUERY_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_NOT_FOUND(String arg0, String arg1) {
      String id = "QUERY_NOT_FOUND";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_RESULT_TYPE_LOCAL(String arg0, String arg1, String arg2) {
      String id = "INVALID_RESULT_TYPE_LOCAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_RESULT_TYPE_REMOTE(String arg0, String arg1, String arg2) {
      String id = "INVALID_RESULT_TYPE_REMOTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALIDATION_TARGET_DOES_NOT_EXIST(String arg0, String arg1) {
      String id = "INVALIDATION_TARGET_DOES_NOT_EXIST";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALIDATION_TARGET_MUST_BE_RO_ENTITY(String arg0, String arg1) {
      String id = "INVALIDATION_TARGET_MUST_BE_RO_ENTITY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALIDATION_TARGET_CANNOT_BE_SET_FOR_RO_ENTITY(String arg0) {
      String id = "INVALIDATION_TARGET_CANNOT_BE_SET_FOR_RO_ENTITY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALIDATION_TARGET_MUST_BE_SET_ON_CMP20(String arg0) {
      String id = "INVALIDATION_TARGET_MUST_BE_SET_ON_CMP20";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COULD_NOT_DETERMINE_RUN_AS_PRINCIPAL_FROM_ROLE_ASSIGNMENT(String arg0, String arg1) {
      String id = "COULD_NOT_DETERMINE_RUN_AS_PRINCIPAL_FROM_ROLE_ASSIGNMENT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_RUN_AS_PRINCIPAL_FOR_EJB(String arg0, String arg1) {
      String id = "INVALID_RUN_AS_PRINCIPAL_FOR_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUS_METHOD_NOT_PUBLIC(String arg0, String arg1) {
      String id = "BUS_METHOD_NOT_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUS_METHOD_MUST_NOT_FINAL(String arg0, String arg1) {
      String id = "BUS_METHOD_MUST_NOT_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUS_METHOD_MUST_NOT_STATIC(String arg0, String arg1) {
      String id = "BUS_METHOD_MUST_NOT_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String PK_FIELD_CLASS_MUST_HAVE_ATLEAST_ONE_CMP_FIELD(String arg0, String arg1) {
      String id = "PK_FIELD_CLASS_MUST_HAVE_ATLEAST_ONE_CMP_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_REF_MUST_HAVE_EJB_LINK_OR_REF_DESC(String arg0, String arg1) {
      String id = "EJB_REF_MUST_HAVE_EJB_LINK_OR_REF_DESC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_LOCAL_EJB_LINK_TO_MDB(String arg0, String arg1) {
      String id = "ILLEGAL_LOCAL_EJB_LINK_TO_MDB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_REMOTE_INTERFACE(String arg0, String arg1) {
      String id = "LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_REMOTE_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_HOME_INTERFACE(String arg0, String arg1) {
      String id = "LOCAL_INTERFACE_TYPES_EXPOSE_THROUGH_HOME_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_JNDI_NAME(String arg0, String arg1) {
      String id = "DUPLICATE_JNDI_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_HOME_METHOD_RETURN_TYPE_SHOULD_MATCH(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      String id = "EJB_HOME_METHOD_RETURN_TYPE_SHOULD_MATCH";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String METHOD_PERMISSION_ROLE_NAME_NOT_DECLARED(String arg0) {
      String id = "METHOD_PERMISSION_ROLE_NAME_NOT_DECLARED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NON_PK_CMP_FIELD_MAPPED_TO_MORE_THAN_ONE_TABLE(String arg0, String arg1, int arg2, String arg3) {
      String id = "NON_PK_CMP_FIELD_MAPPED_TO_MORE_THAN_ONE_TABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_MULTITABLE_PK_FIELD_MAP(String arg0, String arg1, String arg2) {
      String id = "MISSING_MULTITABLE_PK_FIELD_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_ON_MULTITABLE_BEAN_MUST_SPECIFY_FK_TABLE_NAME(String arg0, String arg1) {
      String id = "ROLE_ON_MULTITABLE_BEAN_MUST_SPECIFY_FK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MISSING_FK_TABLE_NAME_FOR_MULTITABLE_BEAN(String arg0, String arg1, String arg2) {
      String id = "MISSING_FK_TABLE_NAME_FOR_MULTITABLE_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_HAS_NO_COLUMN_MAP(String arg0, String arg1) {
      String id = "ROLE_HAS_NO_COLUMN_MAP";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_11_SPECIFIES_INVALID_PK_TABLE_NAME(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "ROLE_11_SPECIFIES_INVALID_PK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_1N_SPECIFIES_INVALID_PK_TABLE_NAME(String arg0, String arg1, String arg2, String arg3) {
      String id = "ROLE_1N_SPECIFIES_INVALID_PK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_MN_SPECIFIES_INVALID_PK_TABLE_NAME(String arg0, String arg1, String arg2, String arg3) {
      String id = "ROLE_MN_SPECIFIES_INVALID_PK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_11_SPECIFIES_INVALID_FK_TABLE_NAME(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "ROLE_11_SPECIFIES_INVALID_FK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_1N_SPECIFIES_INVALID_FK_TABLE_NAME(String arg0, String arg1, String arg2, String arg3) {
      String id = "ROLE_1N_SPECIFIES_INVALID_FK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_MN_SPECIFIES_INVALID_FK_TABLE_NAME(String arg0, String arg1, String arg2) {
      String id = "ROLE_MN_SPECIFIES_INVALID_FK_TABLE_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ROLE_11_SPECIFIES_FK_AND_PK_TABLE_NAMES_NOT_IN_DIFFERENT_ROLES(String arg0, String arg1, String arg2, String arg3) {
      String id = "ROLE_11_SPECIFIES_FK_AND_PK_TABLE_NAMES_NOT_IN_DIFFERENT_ROLES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RELATED_BEANS_MUST_SHARE_DATASOURCE(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      String id = "RELATED_BEANS_MUST_SHARE_DATASOURCE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExpandJar(String arg0, String arg1) {
      String id = "EXPAND_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExtractingDesc(String arg0) {
      String id = "EXTRACT_DESC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompilingJar(String arg0) {
      String id = "COMPILING_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingOutputJar(String arg0) {
      String id = "CREATING_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEJBCSuccess() {
      String id = "EJBC_SUCCESS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessDesc() {
      String id = "PROCESS_DESC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorProcessDesc() {
      String id = "ERROR_PROCESS_DESC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckCompliance() {
      String id = "CHECK_COMPLIANCE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeansCompliant() {
      String id = "BEANS_COMPLIANT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCheckingCompliance() {
      String id = "ERROR_CHECKING_COMPLIANCE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedBeanSources(String arg0) {
      String id = "GEN_BEAN_SRC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGeneratingBeanSources(String arg0) {
      String id = "ERROR_GEN_BEAN_SRC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedPersSources(String arg0) {
      String id = "GEN_PERS_SRC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGeneratingPersSources(String arg0) {
      String id = "ERROR_GEN_PERS_SRC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompilingUnnecessary() {
      String id = "COMPILING_UNECESSARY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRMICUnnecessary() {
      String id = "RMI_COMPILING_UNECESSARY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedClientJar(String arg0) {
      String id = "CREATED_CLIENT_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingClientJar(String arg0) {
      String id = "ERROR_CREATING_CLIENT_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompilingEJBSources() {
      String id = "COMPILING_EJB_SOURCES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCompilingEJBSources() {
      String id = "ERROR_COMPILING_EJB_SOURCES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFinishCompilingEJBSources() {
      String id = "FINISH_COMPILING_EJB_SOURCES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedChecksum(String arg0) {
      String id = "CREATE_CHECKSUM";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingChecksum(String arg0) {
      String id = "ERROR_CREATING_CHECKSUM";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindRMICClasses(String arg0) {
      String id = "FINDING_RMIC_CLASSES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoRMICClasses(String arg0) {
      String id = "NO_RMIC_CLASSES";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompilingRMIC() {
      String id = "COMPILING_RMIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCompilingRMIC() {
      String id = "ERROR_COMPILING_RMIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_DATABASETYPE_VALUE(String arg0) {
      String id = "ILLEGAL_DATABASETYPE_VALUE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_DATABASETYPE_VALUE_VER71(String arg0) {
      String id = "ILLEGAL_DATABASETYPE_VALUE_VER71";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ILLEGAL_AUTOKEY_GENERATOR_VALUE(String arg0, String arg1, String arg2) {
      String id = "ILLEGAL_AUTOKEY_GENERATOR_VALUE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBQLCANNOTBENULL(String arg0, String arg1) {
      String id = "EJBQLCANNOTBENULL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ENV_VALUE_CANNOT_BE_NULL(String arg0) {
      String id = "ENV_VALUE_CANNOT_BE_NULL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJBANDWLQLCANNOTBENULL(String arg0, String arg1) {
      String id = "EJBANDWLQLCANNOTBENULL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WLQL_CANNOT_OVERRIDE_FINDBYPK_QL(String arg0, String arg1) {
      String id = "WLQL_CANNOT_OVERRIDE_FINDBYPK_QL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String FIELDS_IN_PK_CLASS_SHOULD_BE_CMP_FIELDS(String arg0, String arg1) {
      String id = "FIELDS_IN_PK_CLASS_SHOULD_BE_CMP_FIELDS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDuplicateEjbRefNamesAllowed(String arg0, String arg1) {
      String id = "noDuplicateEjbRefNamesAllowed";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDuplicateServiceRefNamesAllowed(String arg0, String arg1) {
      String id = "noDuplicateServiceRefNamesAllowed";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDuplicateServiceReferenceDescriptionNamesAllowed(String arg0, String arg1) {
      String id = "noDuplicateServiceReferenceDescriptionNamesAllowed";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noServiceRefForReferenceDescription(String arg0, String arg1) {
      String id = "noServiceRefForReferenceDescription";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noMethodFoundForEJBDeploymentDescriptorSetting(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "noMethodFoundForEJBDeploymentDescriptorSetting";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String vendorPersistenceTypeNotInstalled(String arg0, String arg1, String arg2) {
      String id = "vendorPersistenceTypeNotInstalled";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incompatibleCmpVersion(String arg0, String arg1, String arg2) {
      String id = "incompatibleCmpVersion";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incompatibleVendorPersistenceType(String arg0, String arg1, String arg2) {
      String id = "incompatibleVendorPersistenceType";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String resourceRefNotMapped(String arg0, String arg1) {
      String id = "resourceRefNotMapped";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String resourceEnvRefNotMapped(String arg0, String arg1) {
      String id = "resourceEnvRefNotMapped";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noEJBRefForReferenceDescription(String arg0, String arg1) {
      String id = "noEJBRefForReferenceDescription";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noEJBLocalRefForReferenceDescription(String arg0, String arg1) {
      String id = "noEJBLocalRefForReferenceDescription";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String roleNotFound(String arg0) {
      String id = "roleNotFound";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cmpFileNotFound(String arg0, String arg1, String arg2) {
      String id = "cmpFileNotFound";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String destinationNotFound(String arg0) {
      String id = "destinationNotFound";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingRelationshipRoleMap(String arg0) {
      String id = "missingRelationshipRoleMap";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COMMIT_OPTION_FOR_delayDatabaseInsertUntil_IGNORED() {
      String id = "COMMIT_OPTION_FOR_delayDatabaseInsertUntil_IGNORED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_VALUE_FOR_DBMS_TABLE() {
      String id = "WRONG_VALUE_FOR_DBMS_TABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String JNDI_NAME_MUST_HAVE_REMOTE_INTERFACE(String arg0) {
      String id = "JNDI_NAME_MUST_HAVE_REMOTE_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_JNDI_NAME_MUST_HAVE_LOCAL_INTERFACE(String arg0) {
      String id = "LOCAL_JNDI_NAME_MUST_HAVE_LOCAL_INTERFACE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INCORRECT_JNDI_NAME(String arg0, String arg1) {
      String id = "INCORRECT_JNDI_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String RESERVED_WORD_USED_FOR_COLUMN_OR_TABLE(String arg0, String arg1) {
      String id = "RESERVED_WORD_USED_FOR_COLUMN_OR_TABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DuplicateWeblogicQueryElementsDetected(String arg0, String arg1, String arg2) {
      String id = "DUPLICATE_WEBLOGIC-QUERY_ELEMENTS_DETECTED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DuplicateRelationshipRoleNamesDetected(String arg0, String arg1) {
      String id = "DUPLICATE_RELATIONSHIP_ROLE_NAMES_DETECTED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_INSTANCE_LOCK_ORDER(String arg0, String arg1, String arg2) {
      String id = "INVALID_INSTANCE_LOCK_ORDER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MESSAGE_DESTINATION_REF_NOT_LINKED(String arg0, String arg1) {
      String id = "MESSAGE_DESTINATION_REF_NOT_LINKED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DUPLICATE_SQL_CACHING_NAME(String arg0, String arg1, String arg2) {
      String id = "DUPLICATE_SQL_CACHING_NAME";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_TIMEOUT_BAD_TX_ATTRIBUTE(String arg0) {
      String id = "EJB_TIMEOUT_BAD_TX_ATTRIBUTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String OptimisticWithReadTimeoutSecondsNoCacheBetweenTx(String arg0) {
      String id = "OptimisticWithReadTimeoutSecondsNoCacheBetweenTx";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SQL_SHAPE_DOES_NOT_EXIST(String arg0, String arg1, String arg2, String arg3) {
      String id = "SQL_SHAPE_DOES_NOT_EXIST";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SQL_QUERY_NOT_SPECIFIED(String arg0, String arg1) {
      String id = "SQL_QUERY_NOT_SPECIFIED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String UNKNOWN_PK_NEVER_ASSIGNED(String arg0) {
      String id = "UNKNOWN_PK_NEVER_ASSIGNED";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String UNKNOWN_PK_WITHOUT_AUTO_KEY(String arg0) {
      String id = "UNKNOWN_PK_WITHOUT_AUTO_KEY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_SUPPORTED_FOR_RO_BEANS_ONLY(String arg0, String arg1) {
      String id = "QUERY_CACHING_SUPPORTED_FOR_RO_BEANS_ONLY";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_UNNECESSARY_FOR_FIND_BY_PK(String arg0) {
      String id = "QUERY_CACHING_UNNECESSARY_FOR_FIND_BY_PK";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_ENABLED_FOR_CMR_TO_RW_BEAN(String arg0, String arg1, String arg2) {
      String id = "QUERY_CACHING_ENABLED_FOR_CMR_TO_RW_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_NOT_SUPPORTED_FOR_EJBSELECTS(String arg0, String arg1) {
      String id = "QUERY_CACHING_NOT_SUPPORTED_FOR_EJBSELECTS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_NOT_SUPPORTED_FOR_ENUMFINDERS(String arg0, String arg1) {
      String id = "QUERY_CACHING_NOT_SUPPORTED_FOR_ENUMFINDERS";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_FINDER_HAS_RW_CACHING_ELEMENT_CMR_FIELD(String arg0, String arg1, String arg2) {
      String id = "QUERY_CACHING_FINDER_HAS_RW_CACHING_ELEMENT_CMR_FIELD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_NOT_SUPORTED_FOR_PREPARED_QUERY_FINDER(String arg0, String arg1) {
      String id = "QUERY_CACHING_NOT_SUPORTED_FOR_PREPARED_QUERY_FINDER";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_SQLFINDER_RETURNS_RW_BEAN(String arg0, String arg1, String arg2) {
      String id = "QUERY_CACHING_SQLFINDER_RETURNS_RW_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String QUERY_CACHING_SQLFINDER_HAS_RW_RELATED_BEAN(String arg0, String arg1, String arg2, String arg3) {
      String id = "QUERY_CACHING_SQLFINDER_HAS_RW_RELATED_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_JNDI_NAME_DEFINED_FOR_REMOTE_VIEW(String arg0) {
      String id = "NO_JNDI_NAME_DEFINED_FOR_REMOTE_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_TIMEOUT_METHOD_NOT_FOUND(String arg0, String arg1) {
      String id = "EJB_TIMEOUT_METHOD_NOT_FOUND";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EXCEPTION_CANNOT_EXTEND_REMOTEEXCEPTION(String arg0) {
      String id = "EXCEPTION_CANNOT_EXTEND_REMOTEEXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_INTERFACE_CANNOT_REMOTE(String arg0) {
      String id = "LOCAL_INTERFACE_CANNOT_REMOTE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_INTERFACE_NOT_FOUND_IN_BEAN(String arg0, String arg1, String arg2) {
      String id = "LOCAL_INTERFACE_NOT_FOUND_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LOCAL_INTERFACE_CANNOT_EXTEND_EJBLocalObject(String arg0) {
      String id = "LOCAL_INTERFACE_CANNOT_EXTEND_EJBLocalObject";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUSINESS_INTERFACE_WITHOUT_METHOD(String arg0) {
      String id = "BUSINESS_INTERFACE_WITHOUT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_CLASS_WITHOUT_METHOD(String arg0) {
      String id = "BEAN_CLASS_WITHOUT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TWO_ARROUNDINVOKE_METHOD(String arg0) {
      String id = "TWO_ARROUNDINVOKE_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ARROUNDINVOKE_METHOD_CANNOT_BUSINESS_METHOD(String arg0, String arg1) {
      String id = "ARROUNDINVOKE_METHOD_CANNOT_BUSINESS_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ARROUNDINVOKE_METHOD_CANNOT_BE_FINAL(String arg0, String arg1) {
      String id = "ARROUNDINVOKE_METHOD_CANNOT_BE_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ARROUNDINVOKE_METHOD_CANNOT_BE_STATIC(String arg0, String arg1) {
      String id = "ARROUNDINVOKE_METHOD_CANNOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ARROUNDINVOKE_METHOD_IS_INVALID(String arg0, String arg1) {
      String id = "ARROUNDINVOKE_METHOD_IS_INVALID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TEMOTE_INTERFACE_IS_LOCAL(String arg0) {
      String id = "TEMOTE_INTERFACE_IS_LOCAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_INTERFACE_IS_LOCAL(String arg0) {
      String id = "REMOTE_INTERFACE_IS_LOCAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_METHOD_NOT_FOUND_IN_BEAN(String arg0, String arg1, String arg2) {
      String id = "REMOTE_METHOD_NOT_FOUND_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_INTERFACE_EXTEND_EJBOBJECT(String arg0) {
      String id = "REMOTE_INTERFACE_EXTEND_EJBOBJECT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_BUSINESS_INTERFACE_NO_METHOD(String arg0) {
      String id = "REMOTE_BUSINESS_INTERFACE_NO_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_BUSINESS_INTERFACE_THROW_REMOTEEXCEPTION(String arg0, String arg1) {
      String id = "REMOTE_BUSINESS_INTERFACE_THROW_REMOTEEXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_INTERFACE_VIEW_METHOD_CANNOT_THROW_REMOTEEXCEPTION(String arg0, String arg1) {
      String id = "NO_INTERFACE_VIEW_METHOD_CANNOT_THROW_REMOTEEXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ASYNC_METHOD_RETURN_ERROR_VALUE_TYPE(String arg0, String arg1, String arg2) {
      String id = "ASYNC_METHOD_RETURN_ERROR_VALUE_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ASYNC_METHOD_CANNOT_IN_WEBSERVICE_CLIENT_VIEW(String arg0, String arg1) {
      String id = "ASYNC_METHOD_CANNOT_IN_WEBSERVICE_CLIENT_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ASYNC_METHOD_CANNOT_IN_EJB2X_CLIENT_VIEW(String arg0, String arg1) {
      String id = "ASYNC_METHOD_CANNOT_IN_EJB2X_CLIENT_VIEW";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ASYNC_METHOD_DECLARED_ERROR_EXCEPTION_TYPE(String arg0, String arg1, String arg2) {
      String id = "ASYNC_METHOD_DECLARED_ERROR_EXCEPTION_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOTE_INTERFACE_NOT_THROW_REMOTEEXCEPTION(String arg0, String arg1) {
      String id = "REMOTE_INTERFACE_NOT_THROW_REMOTEEXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MDB_PREDESTROY_NOT_APPLY_EJBREMOVE(String arg0) {
      String id = "MDB_PREDESTROY_NOT_APPLY_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SESSION_BEAN_PREDESTROY_NOT_APPLY_EJBREMOVE(String arg0) {
      String id = "SESSION_BEAN_PREDESTROY_NOT_APPLY_EJBREMOVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SESSION_BEAN_POSTACTIVATE_NOT_APPLY_EJBACTIVE(String arg0) {
      String id = "SESSION_BEAN_POSTACTIVATE_NOT_APPLY_EJBACTIVE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SESSION_BEAN_PREPASSIVATE_NOT_APPLY_EJBPASSIVATE(String arg0) {
      String id = "SESSION_BEAN_PREPASSIVATE_NOT_APPLY_EJBPASSIVATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String METHOD_CANNOT_START_WITH_EJB(String arg0, String arg1) {
      String id = "METHOD_CANNOT_START_WITH_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUSINESS_METHOD_MUST_BE_PUBLIC(String arg0, String arg1) {
      String id = "BUSINESS_METHOD_MUST_BE_PUBLIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUSINESS_METHOD_MUST_NOT_BE_FINAL(String arg0, String arg1) {
      String id = "BUSINESS_METHOD_MUST_NOT_BE_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUSINESS_METHOD_MUST_NOT_BE_STATIC(String arg0, String arg1) {
      String id = "BUSINESS_METHOD_MUST_NOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String REMOVE_METHOD_NOT_BE_BUSINESS_METHOD(String arg0, String arg1) {
      String id = "REMOVE_METHOD_NOT_BE_BUSINESS_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INTERCEPTOR_CLASS_DECLARED_IN_DD(String arg0) {
      String id = "INTERCEPTOR_CLASS_DECLARED_IN_DD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INTERCEPTOR_CLASS_WITHOUT_NOARG_CONSTRUCTOR(String arg0) {
      String id = "INTERCEPTOR_CLASS_WITHOUT_NOARG_CONSTRUCTOR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String AROUNDINVOKE_METHOD_CANNOT_BE_FINAL(String arg0) {
      String id = "AROUNDINVOKE_METHOD_CANNOT_BE_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String AROUNDINVOKE_METHOD_WITH_INVALIDE_SIGNATURE(String arg0, String arg1) {
      String id = "AROUNDINVOKE_METHOD_WITH_INVALIDE_SIGNATURE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE(String arg0, String arg1, String arg2) {
      String id = "BEAN_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INTERCEPTOR_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE(String arg0, String arg1, String arg2) {
      String id = "INTERCEPTOR_LIFECYCLE_METHOD_WITH_INVALIDE_SIGNATURE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_FINAL(String arg0, String arg1) {
      String id = "LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_FINAL";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_STATIC(String arg0, String arg1) {
      String id = "LIFECYCLE_INTERCEPTOR_METHOD_NOT_BE_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LIFECYCLE_INTERCEPTOR_METHOD_WITH_INVALID_SIGNATURE(String arg0, String arg1, String arg2) {
      String id = "LIFECYCLE_INTERCEPTOR_METHOD_WITH_INVALID_SIGNATURE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TWO_LIFECYCLE_INTERCEPTOR_METHOD_IN_BEAN(String arg0, String arg1) {
      String id = "TWO_LIFECYCLE_INTERCEPTOR_METHOD_IN_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BUSINESS_INTERFACE_NOT_FOUND_IN_SESSION_BEAN(String arg0) {
      String id = "BUSINESS_INTERFACE_NOT_FOUND_IN_SESSION_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String COMPONENT_INTERFACE_NOT_FOUND_IN_SESSION_BEAN(String arg0) {
      String id = "COMPONENT_INTERFACE_NOT_FOUND_IN_SESSION_BEAN";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_IN_DD_NOT_COMPATIBLE_WITH_ANNOTATION() {
      String id = "TIMEOUT_IN_DD_NOT_COMPATIBLE_WITH_ANNOTATION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String BEAN_CAN_HAVE_ONE_TIMEOUT_METHOD() {
      String id = "BEAN_CAN_HAVE_ONE_TIMEOUT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_CAN_ONLY_SPECIFY_EJBTIMEOUT_METHOD(String arg0) {
      String id = "TIMEOUT_CAN_ONLY_SPECIFY_EJBTIMEOUT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFULE_BEAN_CANNOT_IMPLEMENTS_TIMEOUT() {
      String id = "STATEFULE_BEAN_CANNOT_IMPLEMENTS_TIMEOUT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_METHOD_CANNOT_BE_FINAL_OR_STATIC(String arg0) {
      String id = "TIMEOUT_METHOD_CANNOT_BE_FINAL_OR_STATIC";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_METHOD_WITH_INVALID_SIGNATURE(String arg0) {
      String id = "TIMEOUT_METHOD_WITH_INVALID_SIGNATURE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_NUMBER_IN_SCHEDULE_EXPRESSION(String arg0) {
      String id = "INVALID_NUMBER_IN_SCHEDULE_EXPRESSION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_ENUM_IN_SCHEDULE_EXPRESSION(String arg0) {
      String id = "INVALID_ENUM_IN_SCHEDULE_EXPRESSION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_METHOD_CANNOT_THROW_APPLICATION_EXCEPTION(String arg0) {
      String id = "TIMEOUT_METHOD_CANNOT_THROW_APPLICATION_EXCEPTION";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_EJBS_FOUND_IN_EJB_JAR(String arg0) {
      String id = "NO_EJBS_FOUND_IN_EJB_JAR";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_SESSION_BEAN_CLASS_FOUND_FOR_EJB(String arg0) {
      String id = "NO_SESSION_BEAN_CLASS_FOUND_FOR_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SESSION_BEAN_NO_SESSION_TYPE(String arg0) {
      String id = "SESSION_BEAN_NO_SESSION_TYPE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NO_MDB_CLASS_FOUND_FOR_EJB(String arg0) {
      String id = "NO_MDB_CLASS_FOUND_FOR_EJB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MDB_POSTCONSTRUCT_NOT_APPLY_EJBCREATE(String arg0) {
      String id = "MDB_POSTCONSTRUCT_NOT_APPLY_EJBCREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String SLSB_POSTCONSTRUCT_NOT_APPLY_EJBCREATE(String arg0) {
      String id = "SLSB_POSTCONSTRUCT_NOT_APPLY_CREATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_ANNOTATION_VALUE_IS_DUPLICATE(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "EJB_ANNOTATION_VALUE_IS_DUPLICATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_MAY_BE_MISSING_BRIDGE_METHOD(String arg0, String arg1) {
      String id = "EJB_MAY_BE_MISSING_BRIDGE_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_EJBNAME_IN_WEBLOGIC_EJB_JAR_XML(String arg0) {
      String id = "WRONG_EJBNAME_IN_WEBLOGIC_EJB_JAR_XML";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TWO_ARROUNDTIMEOUT_METHOD(String arg0) {
      String id = "TWO_ARROUNDTIMEOUT_METHOD";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String TIMEOUT_METHOD_WITH_INVALID_SIGNATURE2(String arg0) {
      String id = "TIMEOUT_METHOD_WITH_INVALID_SIGNATURE2";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String EJB_COMPONENT_DEFINING_ANNOTATION_INVALID(String arg0, String arg1) {
      String id = "EJB_COMPONENT_DEFINING_ANNOTATION_INVALID";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String INVALID_AUTO_CREATED_CLUSTERED_TIMER_COUNT(String arg0, String arg1) {
      String id = "INVALID_AUTO_CREATED_CLUSTERED_TIMER_COUNT";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_STATEFUL_LIFECYCLE_NONPASSIVATABLE(String arg0) {
      String id = "WRONG_STATEFUL_LIFECYCLE_NONPASSIVATABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String WRONG_STATEFUL_REPLICATETYPE_NONPASSIVATABLE(String arg0) {
      String id = "WRONG_STATEFUL_REPLICATETYPE_NONPASSIVATABLE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LIFECYCLE_TXATTRIBUTE_INCORRECT_SFSB(String arg0, String arg1) {
      String id = "LIFECYCLE_TXATTRIBUTE_INCORRECT_SFSB";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String LIFECYCLE_TXATTRIBUTE_INCORRECT_SINGLETON(String arg0, String arg1) {
      String id = "LIFECYCLE_TXATTRIBUTE_INCORRECT_SINGLETON";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String STATEFUL_BEAN_CANNOT_REPLICATE_IN_MEMORY_STATE(String arg0) {
      String id = "STATEFUL_BEAN_CANNOT_REPLICATE_IN_MEMORY_STATE";
      String subsystem = "EJB Compliance";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
