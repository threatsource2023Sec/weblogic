package weblogic.servlet.internal.dd;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.AuthFilterMBean;

public class AuthFilter extends BaseServletDescriptor implements AuthFilterMBean {
   String filter;

   public AuthFilter() {
   }

   public AuthFilter(String f) {
      this.filter = f;
   }

   public AuthFilter(AuthFilterMBean mb) {
      if (mb != null) {
         this.filter = mb.getFilter();
      }

   }

   public String getFilter() {
      return this.filter;
   }

   public void setFilter(String f) {
      String old = this.filter;
      this.filter = f;
      if (!comp(old, f)) {
         this.firePropertyChange("filter", old, f);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      return this.filter == null ? "" : "<auth-filter>" + this.filter + "</auth-filter>";
   }
}
