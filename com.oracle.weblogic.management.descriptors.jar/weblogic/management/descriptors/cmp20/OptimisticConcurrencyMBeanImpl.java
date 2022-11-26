package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class OptimisticConcurrencyMBeanImpl extends XMLElementMBeanDelegate implements OptimisticConcurrencyMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_verifyFields = false;
   private String verifyFields;

   public String getVerifyFields() {
      return this.verifyFields;
   }

   public void setVerifyFields(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.verifyFields;
      this.verifyFields = value;
      this.isSet_verifyFields = value != null;
      this.checkChange("verifyFields", old, this.verifyFields);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<optimistic-concurrency");
      result.append(">\n");
      if (null != this.getVerifyFields()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<verify-fields>").append(this.getVerifyFields()).append("</verify-fields>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</optimistic-concurrency>\n");
      return result.toString();
   }
}
