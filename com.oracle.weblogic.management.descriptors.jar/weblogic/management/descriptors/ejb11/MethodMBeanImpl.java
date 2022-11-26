package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MethodMBeanImpl extends XMLElementMBeanDelegate implements MethodMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_methodParams = false;
   private MethodParamsMBean methodParams;
   private boolean isSet_methodName = false;
   private String methodName;
   private boolean isSet_methodIntf = false;
   private String methodIntf;

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

   public MethodParamsMBean getMethodParams() {
      return this.methodParams;
   }

   public void setMethodParams(MethodParamsMBean value) {
      MethodParamsMBean old = this.methodParams;
      this.methodParams = value;
      this.isSet_methodParams = value != null;
      this.checkChange("methodParams", old, this.methodParams);
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setMethodName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.methodName;
      this.methodName = value;
      this.isSet_methodName = value != null;
      this.checkChange("methodName", old, this.methodName);
   }

   public String getMethodIntf() {
      return this.methodIntf;
   }

   public void setMethodIntf(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.methodIntf;
      this.methodIntf = value;
      this.isSet_methodIntf = value != null;
      this.checkChange("methodIntf", old, this.methodIntf);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<method");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      if (null != this.getMethodIntf()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<method-intf>").append(this.getMethodIntf()).append("</method-intf>\n");
      }

      if (null != this.getMethodName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<method-name>").append(this.getMethodName()).append("</method-name>\n");
      }

      if (null != this.getMethodParams()) {
         result.append(this.getMethodParams().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</method>\n");
      return result.toString();
   }
}
