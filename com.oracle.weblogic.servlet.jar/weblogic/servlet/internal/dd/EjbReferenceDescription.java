package weblogic.servlet.internal.dd;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.EjbRefMBean;
import weblogic.management.descriptors.webappext.EjbReferenceDescriptionMBean;

public final class EjbReferenceDescription extends BaseServletDescriptor implements EjbReferenceDescriptionMBean {
   private static final long serialVersionUID = -4288451935850905034L;
   private static String refErr = "Can't define ejb-reference-description in weblogic.xml because web.xml has no matching ejb-ref";
   EjbRefMBean ref;
   String jndiName;

   public EjbReferenceDescription() {
   }

   public EjbReferenceDescription(EjbReferenceDescriptionMBean mbean) {
      this.setEjbReference(mbean.getEjbReference());
      this.setJndiName(mbean.getJndiName());
   }

   public void setEjbReference(EjbRefMBean mb) {
      EjbRefMBean old = this.ref;
      this.ref = mb;
      if (!comp(old, this.ref)) {
         this.firePropertyChange("ejbReference", old, this.ref);
      }

   }

   public EjbRefMBean getEjbReference() {
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
      return this.jndiName != null ? this.jndiName : this.getEjbReference().getEJBRefName();
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      return "NYI";
   }

   public String toString() {
      return "EjbReferenceDescription(" + this.ref + "," + this.jndiName + ")";
   }
}
