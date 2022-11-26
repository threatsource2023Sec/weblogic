package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webapp.ServletMappingMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ServletMappingDescriptor extends BaseServletDescriptor implements ToXML, ServletMappingMBean {
   private static final long serialVersionUID = -2074799428020037595L;
   private static final String refErr = "servlet-mapping references a servlet that has not been defined";
   private static final String SERVLET_NAME = "servlet-name";
   private static final String URL_PATTERN = "url-pattern";
   private ServletMBean servlet;
   private String urlPattern;
   private String servletNameLink;

   public ServletMappingDescriptor() {
      this.servlet = null;
      this.urlPattern = "";
   }

   public ServletMappingDescriptor(WebAppDescriptor wad, ServletMappingMBean mbean) {
      this(mbean.getServlet(), mbean.getURLPattern());
   }

   public ServletMappingDescriptor(ServletMBean svlt, String pattern) {
      this.servlet = svlt;
      this.urlPattern = pattern;
   }

   public ServletMappingDescriptor(WebAppDescriptor wad, String sname, String pattern) {
      this.setServlet(wad, sname);
      this.urlPattern = pattern;
   }

   public ServletMappingDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.urlPattern = DOMUtils.getValueByTagName(parentElement, "url-pattern");
      String servletName = DOMUtils.getValueByTagName(parentElement, "servlet-name");
      if (wad != null) {
         this.setServlet(wad, servletName);
      }

   }

   private void setServlet(WebAppDescriptor wad, String name) {
      if (wad == null) {
         HTTPLogger.logServletNotFound(name);
      } else if (name == null) {
         HTTPLogger.logServletNotFound("null");
      } else {
         ServletMBean[] s = wad.getServlets();
         if (s == null) {
            HTTPLogger.logServletNotFound(name);
            this.servletNameLink = name;
         } else {
            for(int i = 0; i < s.length; ++i) {
               if (s[i] != null && name.equals(s[i].getServletName())) {
                  this.servlet = s[i];
                  break;
               }
            }

            if (this.servlet == null) {
               this.servletNameLink = name;
               HTTPLogger.logServletNotFound(name);
            }
         }
      }
   }

   public ServletMBean getServlet() {
      return this.servlet;
   }

   public void setServlet(ServletMBean sd) {
      ServletMBean old = this.servlet;
      this.servlet = sd;
      if (!comp(old, sd)) {
         this.firePropertyChange("servlet", old, sd);
      }

   }

   public String getURLPattern() {
      return this.urlPattern;
   }

   public void setURLPattern(String pattern) {
      String old = this.urlPattern;
      this.urlPattern = pattern;
      if (!comp(old, pattern)) {
         this.firePropertyChange("urlPattern", old, pattern);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      ServletMBean s = this.getServlet();
      String u = this.getURLPattern();
      if (u != null) {
         u = u.trim();
         this.setURLPattern(u);
      }

      if (u == null || u.length() == 0) {
         this.addDescriptorError("NO_URL_PATTERN", s == null ? "" : s.getServletName());
         ok = false;
      }

      if (s == null) {
         this.addDescriptorError("NO_MAPPING_SERVLET_NAME", u);
         ok = false;
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
      ServletMBean s = this.getServlet();
      String sName = s == null ? this.servletNameLink : s.getServletName();
      result = result + this.indentStr(indent) + "<servlet-mapping>\n";
      result = result + this.indentStr(indent + 2) + "<servlet-name>" + sName + "</servlet-name>\n";
      result = result + this.indentStr(indent + 2) + "<url-pattern>" + this.urlPattern + "</url-pattern>\n";
      result = result + this.indentStr(indent) + "</servlet-mapping>\n";
      return result;
   }
}
