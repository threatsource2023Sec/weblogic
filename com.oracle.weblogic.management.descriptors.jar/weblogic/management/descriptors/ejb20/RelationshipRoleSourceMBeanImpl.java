package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RelationshipRoleSourceMBeanImpl extends XMLElementMBeanDelegate implements RelationshipRoleSourceMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbName = false;
   private String ejbName;

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

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
      result.append(ToXML.indent(indentLevel)).append("<relationship-role-source");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</relationship-role-source>\n");
      return result.toString();
   }
}
