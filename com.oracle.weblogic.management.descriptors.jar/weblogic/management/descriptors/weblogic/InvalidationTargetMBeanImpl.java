package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class InvalidationTargetMBeanImpl extends XMLElementMBeanDelegate implements InvalidationTargetMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbName = false;
   private String ejbName;

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbName;
      this.ejbName = value;
      this.isSet_ejbName = value != null;
      this.checkChange("ejbName", old, this.ejbName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<invalidation-target");
      result.append(">\n");
      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</invalidation-target>\n");
      return result.toString();
   }
}
