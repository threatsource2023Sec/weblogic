package weblogic.servlet.internal.dd;

import java.util.ListResourceBundle;
import weblogic.utils.io.XMLWriter;

public final class ServletDescriptorBundle extends ListResourceBundle implements ToXML {
   private static final Object[][] contents = new Object[][]{{"INVALID_WELCOME_FILE", "The specified welcome file at index {0} is invalid"}, {"NO_ROLE_NAMES", "There must be at least one role defined within an <auth-constraint> element"}, {"INVALID_ERROR_CODE", "An error-code must be an HTTP status number, not \"{0}\""}, {"MULTIPLE_DEFINES_ERROR_PAGE", "An error-page must define either an error-code or an exception type, but not both (error-code=\"{0}\" exception-type=\"{1}\""}, {"NO_ERROR_PAGE_LOCATION", "An error-page entry must specify a non-empty location element"}, {"NO_LOGIN_PAGE", "A form-login-page must be specified for login configuration"}, {"NO_LOGIN_ERROR_PAGE", "A form-error-page must be specified for login configuration"}, {"NO_MIME_EXTENSION", "mime-mapping for mime-type=\"{0}\" must specify a non-empty extension"}, {"NO_MIME_TYPE", "mime-mapping for extension=\"{0}\" must specify a non-empty mime-type"}, {"NO_PARAM_NAME", "parameter for param-value=\"{0}\" must specify a non-empty param-name"}, {"NO_PARAM_VALUE", "parameter for param-name=\"{0}\" must specify a non-empty param-value"}, {"NO_SERVLET_NAME", "servlet definition must specify a non-empty servlet-name"}, {"NO_SERVLET_DEF", "servlet definition for servlet-name=\"{0}\" must specify a non-empty servlet-class or jsp-file"}, {"MULTIPLE_DEFINES_SERVLET_DEF", "servlet definition for servlet-name=\"{0}\" must specify only servlet-class or jsp-file, not both"}, {"NO_MAPPING_SERVLET_NAME", "servlet-mapping for url-pattern=\"{0}\" must specify a non-empty servlet-name"}, {"NO_URL_PATTERN", "servlet-mapping for servlet-name=\"{0}\" must specify a non-empty url-pattern"}, {"INVALID_SESSION_TIMEOUT", "session-timeout in minutes must be -2 to use the global server timeout for weblogic sessions, or else be greater than zero, not \"{0}\""}, {"NO_TAGLIB_URI", "taglib must specify a non-empty taglib-uri"}, {"NO_TAGLIB_LOCATION", "taglib for uri=\"{0}\" must specify a non-empty taglib-location"}, {"NO_WEB_RESOURCE", "web-resource-collection must specify a non-empty web-resource-name"}, {"NO_RES_REF_NAME_SET", "resource-ref requires a res-ref-name"}, {"NO_RES_REF_TYPE_SET", "A resource type was not set for this ResourceReference"}, {"INVALID_RES_TYPE", "The referenced resource factory type was not one of \"javax.sql.DataSource,\"\n\"javax.jms.QueueConnectionFactory,\" or \"javax.jms.TopicConnectionFactory\""}, {"NO_RES_AUTH_SET", "A resource authorization was not set for this ResourceReference"}, {"INVALID_RES_AUTH", "The resource authorization was not \"Container\" or \"Application\""}, {"NO_RES_JNDI_NAME", "No JNDI name set for the referenced resource factory"}, {"INVALID_RES_JNDI_NAME", "The JNDI name for the referenced resource factory is not a valid composite name"}, {"NO_ENV_ENTRY_VALUE_SET", "A value was not set for this EnvironmentEntry"}, {"NO_ENV_ENTRY_NAME_SET", "A name was not set for this EnvironmentEntry"}, {"INVALID_ENV_TYPE", "{0} is not a valid environment type"}, {"ENV_VALUE_NOT_OF_TYPE", "{0} cannot be converted to type {1}"}, {"NO_EJB_REF_TYPE_SET", "A type was not set for this EJBReference"}, {"INVALID_EJB_REF_TYPE", "Invalid EJB Reference type"}, {"NO_EJB_REF_HOME_SET", "A home interface name was not set for this EJBReference"}, {"NO_EJB_REF_REMOTE_SET", "A remote interface name was not set for this EJBReference"}, {"NO_EJB_REF_JNDI_NAME_SET", "No JNDI name set for the referenced EJB"}, {"NO_ROLE_NAME_SET", "A role name was not set for this RoleDescriptor"}, {"NO_SECURITY_ROLE_NAME_SET", "A role name was not set for this SecurityRoleRefDescriptor"}, {"NO_SECURITY_ROLE_LINK_SET", "A role link was not set for this SecurityRoleRefDescriptor"}, {"NO_PREPROCESSOR_NAME", "A preprocessor name was not set for this PreprocessorDescriptor"}, {"NO_PREPROCESSOR_CLASS", "A preprocessor-class was not set for preprocessor-name =\"{0}\" "}, {"NO_PREPROCESSOR_URL_PATTERN", "preprocessor-mapping for preprocessor-name=\"{0}\" must specify a non-empty url-pattern"}, {"NO_MAPPING_PREPROCESSOR_NAME", "preprocessor-mapping for url-pattern=\"{0}\" must specify a non-empty preprocessor-name"}};

   public void toXML(XMLWriter x) {
      x.println(this.toXML());
   }

   public String toXML() {
      return this.toXML(0);
   }

   public String toXML(int indent) {
      return "NYI";
   }

   public Object[][] getContents() {
      return contents;
   }
}
