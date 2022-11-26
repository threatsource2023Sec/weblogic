package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class HandlerMBeanImpl extends XMLElementMBeanDelegate implements HandlerMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_initParams = false;
   private InitParamsMBean initParams;
   private boolean isSet_handlerName = false;
   private String handlerName;
   private boolean isSet_className = false;
   private String className;

   public InitParamsMBean getInitParams() {
      return this.initParams;
   }

   public void setInitParams(InitParamsMBean value) {
      InitParamsMBean old = this.initParams;
      this.initParams = value;
      this.isSet_initParams = value != null;
      this.checkChange("initParams", old, this.initParams);
   }

   public String getHandlerName() {
      return this.handlerName;
   }

   public void setHandlerName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.handlerName;
      this.handlerName = value;
      this.isSet_handlerName = value != null;
      this.checkChange("handlerName", old, this.handlerName);
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
      result.append(ToXML.indent(indentLevel)).append("<handler");
      if (this.isSet_handlerName) {
         result.append(" name=\"").append(String.valueOf(this.getHandlerName())).append("\"");
      }

      if (this.isSet_className) {
         result.append(" class-name=\"").append(String.valueOf(this.getClassName())).append("\"");
      }

      result.append(">\n");
      if (null != this.getInitParams()) {
         result.append(this.getInitParams().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</handler>\n");
      return result.toString();
   }
}
