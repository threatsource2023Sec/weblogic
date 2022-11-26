package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ResourceRefMBeanImpl extends XMLElementMBeanDelegate implements ResourceRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_resAuth = false;
   private String resAuth;
   private boolean isSet_resRefName = false;
   private String resRefName;
   private boolean isSet_resType = false;
   private String resType;

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

   public String getResAuth() {
      return this.resAuth;
   }

   public void setResAuth(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resAuth;
      this.resAuth = value;
      this.isSet_resAuth = value != null;
      this.checkChange("resAuth", old, this.resAuth);
   }

   public String getResRefName() {
      return this.resRefName;
   }

   public void setResRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resRefName;
      this.resRefName = value;
      this.isSet_resRefName = value != null;
      this.checkChange("resRefName", old, this.resRefName);
   }

   public String getResType() {
      return this.resType;
   }

   public void setResType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resType;
      this.resType = value;
      this.isSet_resType = value != null;
      this.checkChange("resType", old, this.resType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<resource-ref");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getResRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<res-ref-name>").append(this.getResRefName()).append("</res-ref-name>\n");
      }

      if (null != this.getResType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<res-type>").append(this.getResType()).append("</res-type>\n");
      }

      if (null != this.getResAuth()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<res-auth>").append(this.getResAuth()).append("</res-auth>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</resource-ref>\n");
      return result.toString();
   }
}
