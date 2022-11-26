package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class JNDINameMBeanImpl extends XMLElementMBeanDelegate implements JNDINameMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_path = false;
   private String path;

   public String getPath() {
      return this.path;
   }

   public void setPath(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.path;
      this.path = value;
      this.isSet_path = value != null;
      this.checkChange("path", old, this.path);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<jndi-name");
      if (this.isSet_path) {
         result.append(" path=\"").append(String.valueOf(this.getPath())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</jndi-name>\n");
      return result.toString();
   }
}
