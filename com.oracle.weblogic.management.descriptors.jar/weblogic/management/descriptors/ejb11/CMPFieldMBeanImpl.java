package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class CMPFieldMBeanImpl extends XMLElementMBeanDelegate implements CMPFieldMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_fieldName = false;
   private String fieldName;

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

   public String getFieldName() {
      return this.fieldName;
   }

   public void setFieldName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.fieldName;
      this.fieldName = value;
      this.isSet_fieldName = value != null;
      this.checkChange("fieldName", old, this.fieldName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<cmp-field");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getFieldName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<field-name>").append(this.getFieldName()).append("</field-name>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</cmp-field>\n");
      return result.toString();
   }
}
