package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class TransactionDescriptorMBeanImpl extends XMLElementMBeanDelegate implements TransactionDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_transTimeoutSeconds = false;
   private int transTimeoutSeconds = 0;

   public int getTransTimeoutSeconds() {
      return this.transTimeoutSeconds;
   }

   public void setTransTimeoutSeconds(int value) {
      int old = this.transTimeoutSeconds;
      this.transTimeoutSeconds = value;
      this.isSet_transTimeoutSeconds = value != -1;
      this.checkChange("transTimeoutSeconds", old, this.transTimeoutSeconds);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<transaction-descriptor");
      result.append(">\n");
      if (this.isSet_transTimeoutSeconds || 0 != this.getTransTimeoutSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<trans-timeout-seconds>").append(this.getTransTimeoutSeconds()).append("</trans-timeout-seconds>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</transaction-descriptor>\n");
      return result.toString();
   }
}
