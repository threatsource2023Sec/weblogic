package weblogic.servlet.internal.dd;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.UserDataConstraintMBean;

public final class UserDataConstraint extends BaseServletDescriptor implements UserDataConstraintMBean {
   private static final long serialVersionUID = 4772501842036136387L;
   public static final String NONE = "NONE";
   public static final String INTEGRAL = "INTEGRAL";
   public static final String CONFIDENTIAL = "CONFIDENTIAL";
   String description;
   String transportGuarantee;

   public UserDataConstraint() {
   }

   public UserDataConstraint(UserDataConstraintMBean mbean) {
      if (mbean != null) {
         this.setDescription(mbean.getDescription());
         this.setTransportGuarantee(mbean.getTransportGuarantee());
      } else {
         this.transportGuarantee = "NONE";
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

   public String getTransportGuarantee() {
      return this.transportGuarantee;
   }

   public void setTransportGuarantee(String g) {
      String old = this.transportGuarantee;
      this.transportGuarantee = g;
      if (!comp(old, g)) {
         this.firePropertyChange("transportGuarantee", old, g);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toString() {
      String result = "AuthConstraintDescriptor(";
      result = result + "guarantee=" + this.transportGuarantee;
      result = result + ")";
      return result;
   }

   public String toXML(int indent) {
      String result = "";
      String s = "NONE";
      if ("INTEGRAL".equals(this.getTransportGuarantee())) {
         s = "INTEGRAL";
      } else if ("CONFIDENTIAL".equals(this.getTransportGuarantee())) {
         s = "CONFIDENTIAL";
      }

      result = result + this.indentStr(indent) + "<user-data-constraint>\n";
      indent += 2;
      if (this.description != null) {
         result = result + this.indentStr(indent) + "<description>" + this.description + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<transport-guarantee>" + s + "</transport-guarantee>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</user-data-constraint>\n";
      return result;
   }
}
