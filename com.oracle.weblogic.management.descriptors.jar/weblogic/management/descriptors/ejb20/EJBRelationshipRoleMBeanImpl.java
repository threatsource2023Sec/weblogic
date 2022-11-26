package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBRelationshipRoleMBeanImpl extends XMLElementMBeanDelegate implements EJBRelationshipRoleMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbRelationshipRoleName = false;
   private String ejbRelationshipRoleName;
   private boolean isSet_cmrField = false;
   private CMRFieldMBean cmrField;
   private boolean isSet_multiplicity = false;
   private String multiplicity;
   private boolean isSet_relationshipRoleSource = false;
   private RelationshipRoleSourceMBean relationshipRoleSource;
   private boolean isSet_cascadeDelete = false;
   private boolean cascadeDelete;

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

   public String getEJBRelationshipRoleName() {
      return this.ejbRelationshipRoleName;
   }

   public void setEJBRelationshipRoleName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbRelationshipRoleName;
      this.ejbRelationshipRoleName = value;
      this.isSet_ejbRelationshipRoleName = value != null;
      this.checkChange("ejbRelationshipRoleName", old, this.ejbRelationshipRoleName);
   }

   public CMRFieldMBean getCMRField() {
      return this.cmrField;
   }

   public void setCMRField(CMRFieldMBean value) {
      CMRFieldMBean old = this.cmrField;
      this.cmrField = value;
      this.isSet_cmrField = value != null;
      this.checkChange("cmrField", old, this.cmrField);
   }

   public String getMultiplicity() {
      return this.multiplicity;
   }

   public void setMultiplicity(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.multiplicity;
      this.multiplicity = value;
      this.isSet_multiplicity = value != null;
      this.checkChange("multiplicity", old, this.multiplicity);
   }

   public RelationshipRoleSourceMBean getRelationshipRoleSource() {
      return this.relationshipRoleSource;
   }

   public void setRelationshipRoleSource(RelationshipRoleSourceMBean value) {
      RelationshipRoleSourceMBean old = this.relationshipRoleSource;
      this.relationshipRoleSource = value;
      this.isSet_relationshipRoleSource = value != null;
      this.checkChange("relationshipRoleSource", old, this.relationshipRoleSource);
   }

   public boolean getCascadeDelete() {
      return this.cascadeDelete;
   }

   public void setCascadeDelete(boolean value) {
      boolean old = this.cascadeDelete;
      this.cascadeDelete = value;
      this.isSet_cascadeDelete = true;
      this.checkChange("cascadeDelete", old, this.cascadeDelete);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-relationship-role");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBRelationshipRoleName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-relationship-role-name>").append(this.getEJBRelationshipRoleName()).append("</ejb-relationship-role-name>\n");
      }

      if (null != this.getMultiplicity()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<multiplicity>").append(this.getMultiplicity()).append("</multiplicity>\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append(this.getCascadeDelete() ? "<cascade-delete/>\n" : "");
      if (null != this.getRelationshipRoleSource()) {
         result.append(this.getRelationshipRoleSource().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getCMRField()) {
         result.append(this.getCMRField().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-relationship-role>\n");
      return result.toString();
   }
}
