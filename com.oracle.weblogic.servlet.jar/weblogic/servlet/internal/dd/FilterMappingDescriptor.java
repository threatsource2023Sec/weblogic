package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.FilterMBean;
import weblogic.management.descriptors.webapp.FilterMappingMBean;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class FilterMappingDescriptor extends BaseServletDescriptor implements ToXML, FilterMappingMBean {
   private static final long serialVersionUID = 3697699795239081065L;
   private static final String FILTER_NAME = "filter-name";
   private static final String URL_PATTERN = "url-pattern";
   private static final String SERVLET_NAME = "servlet-name";
   private FilterMBean filter;
   private String urlPattern;
   private ServletMBean servlet;
   private String servletNameLink;

   public FilterMappingDescriptor() {
   }

   public FilterMappingDescriptor(FilterMappingMBean mbean) {
      this.setFilter(mbean.getFilter());
      this.setUrlPattern(mbean.getUrlPattern());
      this.setServlet(mbean.getServlet());
   }

   public FilterMappingDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      String filterName = DOMUtils.getValueByTagName(parentElement, "filter-name");
      FilterMBean[] f = wad.getFilters();
      if (f != null) {
         for(int i = 0; i < f.length; ++i) {
            if (f[i].getFilterName().equals(filterName)) {
               this.filter = f[i];
            }
         }
      }

      this.urlPattern = DOMUtils.getOptionalValueByTagName(parentElement, "url-pattern");
      String servletName = DOMUtils.getOptionalValueByTagName(parentElement, "servlet-name");
      ServletMBean[] s = wad.getServlets();
      if (s != null) {
         for(int i = 0; i < s.length; ++i) {
            if (s[i].getServletName().equals(servletName)) {
               this.servlet = s[i];
            }
         }
      } else {
         this.servletNameLink = servletName;
      }

      if (this.urlPattern != null && this.urlPattern.length() > 0 && (this.servlet != null || this.servletNameLink != null)) {
         this.addDescriptorError("MULTIPLE_DEFINES_FILTER_MAPPING", filterName);
      }

      if ((this.urlPattern == null || this.urlPattern.length() == 0) && this.servlet == null && this.servletNameLink == null) {
         this.addDescriptorError("NO_FILTER_MAPPING_DEF", filterName);
      }

   }

   public FilterMBean getFilter() {
      return this.filter;
   }

   public void setFilter(FilterMBean x) {
      FilterMBean old = this.filter;
      this.filter = x;
      if (!comp(old, x)) {
         this.firePropertyChange("filter", old, x);
      }

   }

   public ServletMBean getServlet() {
      return this.servlet;
   }

   public void setServlet(ServletMBean x) {
      ServletMBean old = this.servlet;
      this.servlet = x;
      if (!comp(old, x)) {
         this.firePropertyChange("servet", old, x);
      }

   }

   public String getUrlPattern() {
      return this.urlPattern;
   }

   public void setUrlPattern(String pattern) {
      String old = this.urlPattern;
      this.urlPattern = pattern;
      if (!comp(old, this.urlPattern)) {
         this.firePropertyChange("urlPattern", old, this.urlPattern);
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      if (this.getFilter() == null) {
         this.addDescriptorError("NO_FILTER_NAME");
         ok = false;
      }

      ServletMBean svlt = this.getServlet();
      String url = this.getUrlPattern();
      if ((svlt != null || this.servletNameLink != null) && url != null && url.length() > 0) {
         this.addDescriptorError("MULTIPLE_DEFINES_FILTER_MAPPING", svlt.getServletName());
         ok = false;
      }

      if (svlt == null && this.servletNameLink == null && (url == null || url.length() == 0)) {
         this.addDescriptorError("NO_FILTER_MAPPING_DEF");
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
      result = result + this.indentStr(indent) + "<filter-mapping>\n";
      indent += 2;
      FilterMBean fmb = this.getFilter();
      if (fmb != null) {
         result = result + this.indentStr(indent) + "<filter-name>" + fmb.getFilterName() + "</filter-name>\n";
      }

      ServletMBean servlet = this.getServlet();
      if (servlet != null) {
         String name = servlet.getServletName();
         result = result + this.indentStr(indent) + "<servlet-name>" + name + "</servlet-name>\n";
      } else if (this.servletNameLink != null) {
         result = result + this.indentStr(indent) + "<servlet-name>" + this.servletNameLink + "</servlet-name>\n";
      } else {
         result = result + this.indentStr(indent) + "<url-pattern>" + this.getUrlPattern() + "</url-pattern>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</filter-mapping>\n";
      return result;
   }
}
