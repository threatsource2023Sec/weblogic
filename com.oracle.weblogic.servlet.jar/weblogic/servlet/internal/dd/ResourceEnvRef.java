package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ResourceEnvRefMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ResourceEnvRef extends BaseServletDescriptor implements ResourceEnvRefMBean {
   private static final long serialVersionUID = 8512993589478342470L;
   String refName;
   String refType;
   String description;

   public ResourceEnvRef() {
   }

   public ResourceEnvRef(String description, String name, String type) {
      this.setDescription(description);
      this.setRefName(name);
      this.setRefType(type);
   }

   public ResourceEnvRef(ResourceEnvRefMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setRefName(mbean.getRefName());
      this.setRefType(mbean.getRefType());
   }

   public ResourceEnvRef(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setRefName(DOMUtils.getValueByTagName(parentElement, "resource-env-ref-name"));
      this.setRefType(DOMUtils.getValueByTagName(parentElement, "resource-env-ref-type"));
   }

   public String getRefName() {
      return this.refName;
   }

   public void setRefName(String n) {
      String old = this.refName;
      this.refName = n;
      if (!comp(old, n)) {
         this.firePropertyChange("refName", old, n);
      }

   }

   public String getRefType() {
      return this.refType;
   }

   public void setRefType(String t) {
      String old = this.refType;
      this.refType = t;
      if (!comp(old, t)) {
         this.firePropertyChange("refType", old, t);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String d) {
      String old = this.description;
      this.description = d;
      if (!comp(old, d)) {
         this.firePropertyChange("description", old, d);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<resource-env-ref>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<resource-env-ref-name>" + this.getRefName() + "</resource-env-ref-name>\n";

      try {
         result = result + this.indentStr(indent) + "<resource-env-ref-type>" + this.getRefType() + "</resource-env-ref-type>\n";
      } catch (Exception var5) {
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</resource-env-ref>\n";
      return result;
   }
}
