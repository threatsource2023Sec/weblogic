package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBJarMBeanImpl extends XMLElementMBeanDelegate implements EJBJarMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_relationships = false;
   private RelationshipsMBean relationships;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbClientJar = false;
   private String ejbClientJar;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_displayName = false;
   private String displayName;
   private boolean isSet_enterpriseBeans = false;
   private weblogic.management.descriptors.ejb11.EnterpriseBeansMBean enterpriseBeans;
   private boolean isSet_encoding = false;
   private String encoding;
   private boolean isSet_largeIcon = false;
   private String largeIcon;
   private boolean isSet_assemblyDescriptor = false;
   private weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean assemblyDescriptor;
   private boolean isSet_smallIcon = false;
   private String smallIcon;

   public RelationshipsMBean getRelationships() {
      return this.relationships;
   }

   public void setRelationships(RelationshipsMBean value) {
      RelationshipsMBean old = this.relationships;
      this.relationships = value;
      this.isSet_relationships = value != null;
      this.checkChange("relationships", old, this.relationships);
   }

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

   public String getEJBClientJar() {
      return this.ejbClientJar;
   }

   public void setEJBClientJar(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbClientJar;
      this.ejbClientJar = value;
      this.isSet_ejbClientJar = value != null;
      this.checkChange("ejbClientJar", old, this.ejbClientJar);
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.version;
      this.version = value;
      this.isSet_version = value != null;
      this.checkChange("version", old, this.version);
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.displayName;
      this.displayName = value;
      this.isSet_displayName = value != null;
      this.checkChange("displayName", old, this.displayName);
   }

   public weblogic.management.descriptors.ejb11.EnterpriseBeansMBean getEnterpriseBeans() {
      return this.enterpriseBeans;
   }

   public void setEnterpriseBeans(weblogic.management.descriptors.ejb11.EnterpriseBeansMBean value) {
      weblogic.management.descriptors.ejb11.EnterpriseBeansMBean old = this.enterpriseBeans;
      this.enterpriseBeans = value;
      this.isSet_enterpriseBeans = value != null;
      this.checkChange("enterpriseBeans", old, this.enterpriseBeans);
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public String getLargeIcon() {
      return this.largeIcon;
   }

   public void setLargeIcon(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.largeIcon;
      this.largeIcon = value;
      this.isSet_largeIcon = value != null;
      this.checkChange("largeIcon", old, this.largeIcon);
   }

   public weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean getAssemblyDescriptor() {
      return this.assemblyDescriptor;
   }

   public void setAssemblyDescriptor(weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean value) {
      weblogic.management.descriptors.ejb11.AssemblyDescriptorMBean old = this.assemblyDescriptor;
      this.assemblyDescriptor = value;
      this.isSet_assemblyDescriptor = value != null;
      this.checkChange("assemblyDescriptor", old, this.assemblyDescriptor);
   }

   public String getSmallIcon() {
      return this.smallIcon;
   }

   public void setSmallIcon(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.smallIcon;
      this.smallIcon = value;
      this.isSet_smallIcon = value != null;
      this.checkChange("smallIcon", old, this.smallIcon);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-jar");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getDisplayName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<display-name>").append(this.getDisplayName()).append("</display-name>\n");
      }

      if (null != this.getSmallIcon()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<small-icon>").append(this.getSmallIcon()).append("</small-icon>\n");
      }

      if (null != this.getLargeIcon()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<large-icon>").append(this.getLargeIcon()).append("</large-icon>\n");
      }

      if (null != this.getEnterpriseBeans()) {
         result.append(this.getEnterpriseBeans().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getRelationships()) {
         result.append(this.getRelationships().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getAssemblyDescriptor()) {
         result.append(this.getAssemblyDescriptor().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getEJBClientJar()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-client-jar>").append(this.getEJBClientJar()).append("</ejb-client-jar>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-jar>\n");
      return result.toString();
   }
}
