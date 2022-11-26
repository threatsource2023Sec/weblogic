package weblogic.servlet.internal.dd;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.URLMatchMapMBean;

public class URLMatchMapDescriptor extends BaseServletDescriptor implements ToXML, URLMatchMapMBean {
   private static final String URL_MATCH_MAP = "url-match-map";
   private String mapClass;

   public URLMatchMapDescriptor() {
   }

   public URLMatchMapDescriptor(String clazz) {
      this.mapClass = clazz;
   }

   public URLMatchMapDescriptor(URLMatchMapMBean mb) {
      if (mb != null) {
         this.mapClass = mb.getClassName();
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String clazz = this.getClassName();
      if (clazz == null || clazz.length() == 0) {
         this.addDescriptorError("NO_URL_MATCH_MAP_CLASS");
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String getClassName() {
      return this.mapClass;
   }

   public void setClassName(String s) {
      String old = this.mapClass;
      this.mapClass = s;
      if (!comp(old, s)) {
         this.firePropertyChange("className", old, s);
      }

   }

   public String toXML(int indent) {
      if (this.mapClass != null) {
         this.mapClass = this.mapClass.trim();
         if (this.mapClass.length() == 0) {
            this.mapClass = null;
         }
      }

      if (this.mapClass != null) {
         String result = "";
         result = result + this.indentStr(indent) + "<" + "url-match-map" + ">\n";
         indent += 2;
         result = result + this.indentStr(indent) + this.mapClass + "\n";
         indent -= 2;
         result = result + this.indentStr(indent) + "</" + "url-match-map" + ">\n";
         return result;
      } else {
         return "";
      }
   }
}
