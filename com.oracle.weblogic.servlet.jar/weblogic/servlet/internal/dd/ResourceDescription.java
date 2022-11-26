package weblogic.servlet.internal.dd;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.ResourceRefMBean;
import weblogic.management.descriptors.webappext.ResourceDescriptionMBean;

public final class ResourceDescription extends BaseServletDescriptor implements ResourceDescriptionMBean {
   private static final long serialVersionUID = 1215314657449753272L;
   private static String refErr = "Can't define resource-description in weblogic.xml because web.xml has no matching resource-ref";
   private ResourceRefMBean ref;
   private String jndiName;

   public ResourceDescription() {
   }

   public ResourceDescription(ResourceDescriptionMBean mbean) {
      this.jndiName = mbean.getJndiName();
      this.ref = mbean.getResourceReference();
   }

   public void setResourceReferenceName(String name) {
      String old = this.getResourceReferenceName();
      this.ref.setRefName(name);
      if (!comp(old, name)) {
         this.firePropertyChange("resourceReferenceName", old, name);
      }

   }

   public String getResourceReferenceName() {
      return this.ref == null ? null : this.ref.getRefName();
   }

   public void setResourceReference(ResourceRefMBean rr) {
      ResourceRefMBean old = this.ref;
      this.ref = rr;
      if (!comp(old, rr)) {
         this.firePropertyChange("resourceReference", old, rr);
      }

   }

   public ResourceRefMBean getResourceReference() {
      return this.ref;
   }

   public void setJndiName(String name) {
      String old = this.jndiName;
      this.jndiName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("jndiName", old, name);
      }

   }

   public String getJndiName() {
      return this.jndiName;
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<resource-description>\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<res-ref-name>" + this.getResourceReferenceName() + "</res-ref-name>\n";
      result = result + this.indentStr(indent) + "<jndi-name>" + this.getJndiName() + "</jndi-name>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</resource-description>\n";
      return result;
   }
}
