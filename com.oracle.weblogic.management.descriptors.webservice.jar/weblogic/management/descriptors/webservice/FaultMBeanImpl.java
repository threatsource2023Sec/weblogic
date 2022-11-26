package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;
import weblogic.xml.stream.XMLName;

public class FaultMBeanImpl extends XMLElementMBeanDelegate implements FaultMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_faultType = false;
   private XMLName faultType;
   private boolean isSet_faultName = false;
   private String faultName;
   private boolean isSet_className = false;
   private String className;

   public XMLName getFaultType() {
      return this.faultType;
   }

   public void setFaultType(XMLName value) {
      XMLName old = this.faultType;
      this.faultType = value;
      this.isSet_faultType = value != null;
      this.checkChange("faultType", old, this.faultType);
   }

   public String getFaultName() {
      return this.faultName;
   }

   public void setFaultName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.faultName;
      this.faultName = value;
      this.isSet_faultName = value != null;
      this.checkChange("faultName", old, this.faultName);
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.className;
      this.className = value;
      this.isSet_className = value != null;
      this.checkChange("className", old, this.className);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<fault");
      if (this.isSet_faultType) {
         result.append(" xmlns:").append(this.getFaultType().getPrefix()).append("=\"").append(this.getFaultType().getNamespaceUri()).append("\" type=\"").append(this.getFaultType().getQualifiedName()).append("\"");
      }

      if (this.isSet_className) {
         result.append(" class-name=\"").append(String.valueOf(this.getClassName())).append("\"");
      }

      if (this.isSet_faultName) {
         result.append(" name=\"").append(String.valueOf(this.getFaultName())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</fault>\n");
      return result.toString();
   }
}
