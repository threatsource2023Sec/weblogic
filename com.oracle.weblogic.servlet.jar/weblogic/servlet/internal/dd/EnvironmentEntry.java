package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.EnvEntryMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class EnvironmentEntry extends BaseServletDescriptor implements EnvEntryMBean {
   private static final long serialVersionUID = -4909291169304419758L;
   private String description;
   private String name;
   private String value;
   private String type;

   public EnvironmentEntry() {
   }

   public EnvironmentEntry(EnvEntryMBean mbean) {
   }

   public EnvironmentEntry(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setEnvEntryName(DOMUtils.getValueByTagName(parentElement, "env-entry-name"));
      this.setEnvEntryValue(DOMUtils.getOptionalValueByTagName(parentElement, "env-entry-value"));
      this.setEnvEntryType(DOMUtils.getValueByTagName(parentElement, "env-entry-type"));
   }

   public void setDescription(String d) {
      String old = this.description;
      this.description = d;
      if (!comp(old, d)) {
         this.firePropertyChange("description", old, d);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setEnvEntryName(String n) {
      String old = this.name;
      this.name = n;
      if (!comp(old, n)) {
         this.firePropertyChange("envEntryName", old, n);
      }

   }

   public String getEnvEntryName() {
      return this.name;
   }

   public void setEnvEntryValue(String v) {
      String old = this.value;
      this.value = v;
      if (!comp(old, v)) {
         this.firePropertyChange("envEntryValue", old, v);
      }

   }

   public String getEnvEntryValue() {
      return this.value;
   }

   public void setEnvEntryType(String t) {
      String old = this.type;
      this.type = t;
      if (!comp(old, t)) {
         this.firePropertyChange("envEntryType", old, t);
      }

   }

   public String getEnvEntryType() {
      return this.type;
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toString() {
      return "EnvironmentEntry(" + this.hashCode() + "," + this.getEnvEntryName() + ")";
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<env-entry>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<env-entry-name>" + this.getEnvEntryName() + "</env-entry-name>\n";
      String v = this.getEnvEntryValue();
      if (v != null) {
         result = result + this.indentStr(indent) + "<env-entry-value>" + v + "</env-entry-value>\n";
      }

      result = result + this.indentStr(indent) + "<env-entry-type>" + this.getEnvEntryType() + "</env-entry-type>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</env-entry>\n";
      return result;
   }
}
