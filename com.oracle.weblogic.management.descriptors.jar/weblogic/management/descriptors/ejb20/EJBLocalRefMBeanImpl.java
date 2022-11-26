package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBLocalRefMBeanImpl extends XMLElementMBeanDelegate implements EJBLocalRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_local = false;
   private String local;
   private boolean isSet_ejbRefName = false;
   private String ejbRefName;
   private boolean isSet_localHome = false;
   private String localHome;
   private boolean isSet_ejbLink = false;
   private String ejbLink;
   private boolean isSet_ejbRefType = false;
   private String ejbRefType;

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

   public String getLocal() {
      return this.local;
   }

   public void setLocal(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.local;
      this.local = value;
      this.isSet_local = value != null;
      this.checkChange("local", old, this.local);
   }

   public String getEJBRefName() {
      return this.ejbRefName;
   }

   public void setEJBRefName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbRefName;
      this.ejbRefName = value;
      this.isSet_ejbRefName = value != null;
      this.checkChange("ejbRefName", old, this.ejbRefName);
   }

   public String getLocalHome() {
      return this.localHome;
   }

   public void setLocalHome(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.localHome;
      this.localHome = value;
      this.isSet_localHome = value != null;
      this.checkChange("localHome", old, this.localHome);
   }

   public String getEJBLink() {
      return this.ejbLink;
   }

   public void setEJBLink(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbLink;
      this.ejbLink = value;
      this.isSet_ejbLink = value != null;
      this.checkChange("ejbLink", old, this.ejbLink);
   }

   public String getEJBRefType() {
      return this.ejbRefType;
   }

   public void setEJBRefType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbRefType;
      this.ejbRefType = value;
      this.isSet_ejbRefType = value != null;
      this.checkChange("ejbRefType", old, this.ejbRefType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-local-ref");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBRefName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-ref-name>").append(this.getEJBRefName()).append("</ejb-ref-name>\n");
      }

      if (null != this.getEJBRefType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-ref-type>").append(this.getEJBRefType()).append("</ejb-ref-type>\n");
      }

      if (null != this.getLocalHome()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<local-home>").append(this.getLocalHome()).append("</local-home>\n");
      }

      if (null != this.getLocal()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<local>").append(this.getLocal()).append("</local>\n");
      }

      if (null != this.getEJBLink()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-link>").append(this.getEJBLink()).append("</ejb-link>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-local-ref>\n");
      return result.toString();
   }
}
