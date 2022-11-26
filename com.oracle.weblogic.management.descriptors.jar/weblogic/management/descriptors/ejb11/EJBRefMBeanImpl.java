package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBRefMBeanImpl extends XMLElementMBeanDelegate implements EJBRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_remote = false;
   private String remote;
   private boolean isSet_ejbRefName = false;
   private String ejbRefName;
   private boolean isSet_ejbLink = false;
   private String ejbLink;
   private boolean isSet_home = false;
   private String home;
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

   public String getRemote() {
      return this.remote;
   }

   public void setRemote(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.remote;
      this.remote = value;
      this.isSet_remote = value != null;
      this.checkChange("remote", old, this.remote);
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

   public String getHome() {
      return this.home;
   }

   public void setHome(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.home;
      this.home = value;
      this.isSet_home = value != null;
      this.checkChange("home", old, this.home);
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
      result.append(ToXML.indent(indentLevel)).append("<ejb-ref");
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

      if (null != this.getHome()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<home>").append(this.getHome()).append("</home>\n");
      }

      if (null != this.getRemote()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<remote>").append(this.getRemote()).append("</remote>\n");
      }

      if (null != this.getEJBLink()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-link>").append(this.getEJBLink()).append("</ejb-link>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-ref>\n");
      return result.toString();
   }
}
