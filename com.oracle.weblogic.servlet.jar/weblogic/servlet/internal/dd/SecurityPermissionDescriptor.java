package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.SecurityPermissionMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class SecurityPermissionDescriptor extends BaseServletDescriptor implements SecurityPermissionMBean, ToXML {
   private static final String SECURITY_PERMISSION = "security-permission";
   private static final String SECURITY_PERMISSION_SPEC = "security-permission-spec";
   private static final String DESCRIPTION = "description";
   private String spec;
   private String desc;

   public SecurityPermissionDescriptor() {
      this.spec = null;
      this.desc = null;
   }

   public SecurityPermissionDescriptor(String s, String d) {
      this.spec = s;
      this.desc = d;
   }

   public SecurityPermissionDescriptor(Element parent) throws DOMProcessingException {
      this.desc = DOMUtils.getOptionalValueByTagName(parent, "description");
      this.spec = DOMUtils.getValueByTagName(parent, "security-permission-spec");
   }

   public void setDescription(String description) {
      String old = this.desc;
      this.desc = description;
      if (!comp(old, this.desc)) {
         this.firePropertyChange("description", old, this.desc);
      }

   }

   public String getDescription() {
      return this.desc;
   }

   public void setSecurityPermissionSpec(String permSpec) {
      String old = this.spec;
      this.spec = permSpec;
      if (!comp(old, this.spec)) {
         this.firePropertyChange("securityPermissionSpec", old, this.spec);
      }

   }

   public String getSecurityPermissionSpec() {
      return this.spec;
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<" + "security-permission" + ">\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<" + "security-permission-spec" + ">" + this.getSecurityPermissionSpec() + "</" + "security-permission-spec" + ">\n";
      result = result + this.indentStr(indent) + "<" + "description" + ">" + this.getDescription() + "</" + "description" + ">\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</" + "security-permission" + ">\n";
      return result;
   }
}
