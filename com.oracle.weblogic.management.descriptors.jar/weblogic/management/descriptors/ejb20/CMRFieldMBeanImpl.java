package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class CMRFieldMBeanImpl extends XMLElementMBeanDelegate implements CMRFieldMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_cmrFieldType = false;
   private String cmrFieldType;
   private boolean isSet_cmrFieldName = false;
   private String cmrFieldName;

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

   public String getCMRFieldType() {
      return this.cmrFieldType;
   }

   public void setCMRFieldType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cmrFieldType;
      this.cmrFieldType = value;
      this.isSet_cmrFieldType = value != null;
      this.checkChange("cmrFieldType", old, this.cmrFieldType);
   }

   public String getCMRFieldName() {
      return this.cmrFieldName;
   }

   public void setCMRFieldName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cmrFieldName;
      this.cmrFieldName = value;
      this.isSet_cmrFieldName = value != null;
      this.checkChange("cmrFieldName", old, this.cmrFieldName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<cmr-field");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getCMRFieldName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cmr-field-name>").append(this.getCMRFieldName()).append("</cmr-field-name>\n");
      }

      if (null != this.getCMRFieldType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<cmr-field-type>").append(this.getCMRFieldType()).append("</cmr-field-type>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</cmr-field>\n");
      return result.toString();
   }
}
