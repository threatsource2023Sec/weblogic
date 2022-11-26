package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class QueryMethodMBeanImpl extends XMLElementMBeanDelegate implements QueryMethodMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_methodParams = false;
   private MethodParamsMBean methodParams;
   private boolean isSet_methodName = false;
   private String methodName;
   private boolean isSet_methodIntf = false;
   private String methodIntf;

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
      result.append(ToXML.indent(indentLevel)).append("<query-method");
      result.append(">\n");
      if (null != this.getMethodName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<method-name>").append(this.getMethodName()).append("</method-name>\n");
      }

      if (null != this.getMethodIntf()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<method-intf>").append(this.getMethodIntf()).append("</method-intf>\n");
      }

      if (null != this.getMethodParams()) {
         result.append(this.getMethodParams().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</query-method>\n");
      return result.toString();
   }
}
