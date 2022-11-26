package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webappext.InitAsMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class InitAsDescriptor extends BaseServletDescriptor implements InitAsMBean {
   private String servletName;
   private String principalName;
   private ServletMBean servlet;
   private WebAppDescriptor wad;

   public InitAsDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.wad = wad;
      this.setServletName(DOMUtils.getValueByTagName(parentElement, "servlet-name"));
      this.setPrincipalName(DOMUtils.getValueByTagName(parentElement, "principal-name"));
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
            this.setIdentity();
         }
      }
   }

   public String getServletName() {
      return this.servletName;
   }

   public void setServlet(ServletMBean servlet) {
      this.servlet = servlet;
      this.servletName = servlet.getName();
      this.setIdentity();
   }

   public ServletMBean getServlet() {
      return this.servlet;
   }

   public void setPrincipalName(String name) {
      String old = this.principalName;
      this.principalName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("principalName", old, name);
      }

      this.setIdentity();
   }

   public String getPrincipalName() {
      return this.principalName;
   }

   public void setIdentity() {
      if (this.servlet != null && this.principalName != null) {
         this.servlet.setInitAs(this.principalName);
      }

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
      result = result + this.indentStr(indent) + "<init-as>\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<servlet-name>" + this.getServletName() + "</servlet-name>\n";
      result = result + this.indentStr(indent) + "<principal-name>" + this.getPrincipalName() + "</principal-name>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</init-as>\n";
      return result;
   }
}
