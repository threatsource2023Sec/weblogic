package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.LoginConfigMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class LoginDescriptor extends BaseServletDescriptor implements ToXML, LoginConfigMBean {
   private static final long serialVersionUID = -6175838651266572918L;
   private static final String AUTH_METHOD = "auth-method";
   private static final String REALM_NAME = "realm-name";
   private static final String FORM_LOGIN_CONFIG = "form-login-config";
   private static final String FORM_LOGIN_PAGE = "form-login-page";
   private static final String FORM_ERROR_PAGE = "form-error-page";
   public static final String AM_NONE = "";
   public static final String AM_BASIC = "BASIC";
   public static final String AM_DIGEST = "DIGEST";
   public static final String AM_FORM = "FORM";
   public static final String AM_CLIENT_CERT = "CLIENT-CERT";
   String authMethod;
   String realmName;
   String loginPage;
   String errorPage;

   public LoginDescriptor() {
   }

   public LoginDescriptor(LoginConfigMBean mb) {
      this.setAuthMethod(mb.getAuthMethod());
      this.setRealmName(mb.getRealmName());
      this.setLoginPage(mb.getLoginPage());
      this.setErrorPage(mb.getErrorPage());
   }

   public LoginDescriptor(Element parentElement) throws DOMProcessingException {
      this.realmName = DOMUtils.getOptionalValueByTagName(parentElement, "realm-name");
      this.authMethod = DOMUtils.getOptionalValueByTagName(parentElement, "auth-method");
      if (this.authMethod != null) {
         if ("BASIC".equalsIgnoreCase(this.authMethod)) {
            this.authMethod = "BASIC";
         } else if ("CLIENT-CERT".equalsIgnoreCase(this.authMethod)) {
            this.authMethod = "CLIENT-CERT";
         } else if ("DIGEST".equalsIgnoreCase(this.authMethod)) {
            this.authMethod = "DIGEST";
         } else if ("FORM".equalsIgnoreCase(this.authMethod)) {
            this.authMethod = "FORM";
         }
      }

      Element elt = DOMUtils.getOptionalElementByTagName(parentElement, "form-login-config");
      if (elt != null) {
         this.loginPage = DOMUtils.getValueByTagName(elt, "form-login-page");
         this.errorPage = DOMUtils.getValueByTagName(elt, "form-error-page");
         if (this.loginPage != null && !this.loginPage.startsWith("/")) {
            this.loginPage = "/" + this.loginPage;
         }

         if (this.errorPage != null && !this.errorPage.startsWith("/")) {
            this.errorPage = "/" + this.errorPage;
         }
      }

   }

   public String getAuthMethod() {
      return this.authMethod;
   }

   public void setAuthMethod(String m) {
      String old = this.getAuthMethod();
      this.authMethod = m;
      if (!comp(old, this.getAuthMethod())) {
         this.firePropertyChange("authMethod", old, this.getAuthMethod());
      }

   }

   public String getRealmName() {
      return this.realmName;
   }

   public void setRealmName(String name) {
      String old = this.realmName;
      this.realmName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("realmName", old, name);
      }

   }

   public String getLoginPage() {
      return this.loginPage;
   }

   public void setLoginPage(String page) {
      String old = this.loginPage;
      this.loginPage = page;
      if (!comp(old, page)) {
         this.firePropertyChange("loginPage", old, page);
      }

   }

   public String getErrorPage() {
      return this.errorPage;
   }

   public void setErrorPage(String page) {
      String old = this.errorPage;
      this.errorPage = page;
      if (!comp(old, page)) {
         this.firePropertyChange("errorPage", old, page);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.loginPage != null) {
         this.loginPage = this.loginPage.trim();
      }

      if (this.errorPage != null) {
         this.errorPage = this.errorPage.trim();
      }

      if (this.loginPage != null && this.loginPage.length() == 0) {
         this.addDescriptorError("NO_LOGIN_PAGE");
         ok = false;
      }

      if (this.errorPage != null && this.errorPage.length() == 0) {
         this.addDescriptorError("NO_LOGIN_ERROR_PAGE");
         ok = false;
      }

      if (ok) {
         if (this.loginPage != null && this.errorPage == null) {
            this.addDescriptorError("NO_LOGIN_ERROR_PAGE");
            ok = false;
         } else if (this.errorPage != null && this.loginPage == null) {
            this.addDescriptorError("NO_LOGIN_PAGE");
            ok = false;
         }
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      String mname = this.getAuthMethod();
      if (mname == null && this.realmName == null && this.loginPage == null && this.errorPage == null) {
         return result;
      } else {
         result = result + this.indentStr(indent) + "<login-config>\n";
         indent += 2;
         if (mname != null) {
            result = result + this.indentStr(indent) + "<auth-method>" + mname + "</auth-method>\n";
         }

         if (this.realmName != null) {
            result = result + this.indentStr(indent) + "<realm-name>" + this.realmName + "</realm-name>\n";
         }

         if (this.authMethod == "FORM" && this.loginPage != null && this.errorPage != null) {
            result = result + this.indentStr(indent) + "<form-login-config>\n";
            indent += 2;
            result = result + this.indentStr(indent) + "<form-login-page>" + this.loginPage + "</form-login-page>\n";
            result = result + this.indentStr(indent) + "<form-error-page>" + this.errorPage + "</form-error-page>\n";
            indent -= 2;
            result = result + this.indentStr(indent) + "</form-login-config>\n";
         }

         indent -= 2;
         result = result + this.indentStr(indent) + "</login-config>\n";
         return result;
      }
   }

   public String toString() {
      return "LoginDescriptor(method=" + this.getAuthMethod() + ", realm=" + this.realmName + ", login=" + this.loginPage + ", error=" + this.errorPage + ")";
   }
}
