package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webappext.ServletDescriptorMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class WLServletDescriptor extends BaseServletDescriptor implements ServletDescriptorMBean {
   private static final String SERVLET_DESCRIPTOR = "servlet-descriptor";
   private static final String SERVLET_NAME = "servlet-name";
   private static final String RUN_AS_PRINCIPAL_NAME = "run-as-principal-name";
   private static final String INIT_AS_PRINCIPAL_NAME = "init-as-principal-name";
   private static final String DESTROY_AS_PRINCIPAL_NAME = "destroy-as-principal-name";
   private static final String DISPATCH_POLICY = "dispatch-policy";
   private String servletName = null;
   private String runAsPrincipalName = null;
   private String initAsPrincipalName = null;
   private String destroyAsPrincipalName = null;
   private String dispatchPolicy = null;
   private WebAppDescriptor wad = null;
   private ServletMBean servlet = null;

   public WLServletDescriptor() {
   }

   public WLServletDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.wad = wad;
      this.setServletName(DOMUtils.getValueByTagName(parentElement, "servlet-name"));
      this.setRunAsPrincipalName(DOMUtils.getOptionalValueByTagName(parentElement, "run-as-principal-name"));
      this.setInitAsPrincipalName(DOMUtils.getOptionalValueByTagName(parentElement, "init-as-principal-name"));
      this.setDestroyAsPrincipalName(DOMUtils.getOptionalValueByTagName(parentElement, "destroy-as-principal-name"));
      this.setDispatchPolicy(DOMUtils.getOptionalValueByTagName(parentElement, "dispatch-policy"));
   }

   public WLServletDescriptor(WebAppDescriptor wad, String sName) throws DOMProcessingException {
      this.wad = wad;
      this.setServletName(sName);
   }

   public void setServletName(String name) throws DOMProcessingException {
      String old = this.servletName;
      this.servletName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("servletName", old, name);
      }

      ServletMBean[] s = this.wad.getServlets();
      if (s == null) {
         HTTPLogger.logServletNotFound(name);
         throw new DOMProcessingException("Servlet with name " + name + " not defined in web.xml");
      } else {
         for(int i = 0; i < s.length; ++i) {
            if (s[i] != null && name.equals(s[i].getServletName())) {
               this.servlet = s[i];
               break;
            }
         }

         if (this.servlet == null) {
            HTTPLogger.logServletNotFound(name);
            throw new DOMProcessingException("Servlet with name " + name + " not defined in web.xml");
         } else {
            this.servlet.setServletDescriptor(this);
         }
      }
   }

   public String getServletName() {
      return this.servlet != null ? this.servlet.getServletName() : this.servletName;
   }

   public void setServlet(ServletMBean servlet) {
      this.servlet = servlet;
      this.servletName = servlet.getServletName();
      servlet.setServletDescriptor(this);
   }

   public ServletMBean getServlet() {
      return this.servlet;
   }

   public void setRunAsPrincipalName(String name) {
      String old = this.runAsPrincipalName;
      this.runAsPrincipalName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("runAsPrincipalName", old, name);
      }

   }

   public String getRunAsPrincipalName() {
      return this.runAsPrincipalName;
   }

   public void setInitAsPrincipalName(String name) {
      String old = this.initAsPrincipalName;
      this.initAsPrincipalName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("initAsPrincipalName", old, name);
      }

   }

   public String getInitAsPrincipalName() {
      return this.initAsPrincipalName;
   }

   public void setDestroyAsPrincipalName(String name) {
      String old = this.destroyAsPrincipalName;
      this.destroyAsPrincipalName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("destroyAsPrincipalName", old, name);
      }

   }

   public String getDestroyAsPrincipalName() {
      return this.destroyAsPrincipalName;
   }

   public void setDispatchPolicy(String name) {
      String old = this.dispatchPolicy;
      this.dispatchPolicy = name;
      if (!comp(old, name)) {
         this.firePropertyChange("dispatchPolicy", old, name);
      }

   }

   public String getDispatchPolicy() {
      return this.dispatchPolicy;
   }

   public String toString() {
      return this.getServletName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<" + "servlet-descriptor" + ">\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<" + "servlet-name" + ">" + this.getServletName() + "</" + "servlet-name" + ">\n";
      if (this.getRunAsPrincipalName() != null) {
         result = result + this.indentStr(indent) + "<" + "run-as-principal-name" + ">" + this.getRunAsPrincipalName() + "</" + "run-as-principal-name" + ">\n";
      }

      if (this.getInitAsPrincipalName() != null) {
         result = result + this.indentStr(indent) + "<" + "init-as-principal-name" + ">" + this.getInitAsPrincipalName() + "</" + "init-as-principal-name" + ">\n";
      }

      if (this.getDestroyAsPrincipalName() != null) {
         result = result + this.indentStr(indent) + "<" + "destroy-as-principal-name" + ">" + this.getDestroyAsPrincipalName() + "</" + "destroy-as-principal-name" + ">\n";
      }

      if (this.getDispatchPolicy() != null) {
         result = result + this.indentStr(indent) + "<" + "dispatch-policy" + ">" + this.getDispatchPolicy() + "</" + "dispatch-policy" + ">\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</" + "servlet-descriptor" + ">\n";
      return result;
   }
}
