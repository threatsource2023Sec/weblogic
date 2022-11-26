package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.RunAsMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class RunAsDescriptor extends BaseServletDescriptor implements RunAsMBean {
   private String description;
   private String roleName;

   public RunAsDescriptor() {
   }

   public RunAsDescriptor(RunAsMBean mbean) {
      this.setDescription(mbean.getDescription());
      this.setRoleName(mbean.getRoleName());
   }

   public RunAsDescriptor(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setRoleName(DOMUtils.getValueByTagName(parentElement, "role-name"));
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

   public void setRoleName(String name) {
      String old = this.roleName;
      this.roleName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("roleName", old, name);
      }

   }

   public String getRoleName() {
      return this.roleName;
   }

   public String toString() {
      return this.getRoleName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<run-as>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<role-name>" + this.getRoleName() + "</role-name>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</run-as>\n";
      return result;
   }
}
