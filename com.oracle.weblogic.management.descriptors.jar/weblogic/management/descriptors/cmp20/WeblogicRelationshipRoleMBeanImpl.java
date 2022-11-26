package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRelationshipRoleMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRelationshipRoleMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_groupName = false;
   private String groupName;
   private boolean isSet_relationshipRoleMap = false;
   private RelationshipRoleMapMBean relationshipRoleMap;
   private boolean isSet_relationshipRoleName = false;
   private String relationshipRoleName;
   private boolean isSet_dbCascadeDelete = false;
   private boolean dbCascadeDelete;

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.groupName;
      this.groupName = value;
      this.isSet_groupName = value != null;
      this.checkChange("groupName", old, this.groupName);
   }

   public RelationshipRoleMapMBean getRelationshipRoleMap() {
      return this.relationshipRoleMap;
   }

   public void setRelationshipRoleMap(RelationshipRoleMapMBean value) {
      RelationshipRoleMapMBean old = this.relationshipRoleMap;
      this.relationshipRoleMap = value;
      this.isSet_relationshipRoleMap = value != null;
      this.checkChange("relationshipRoleMap", old, this.relationshipRoleMap);
   }

   public String getRelationshipRoleName() {
      return this.relationshipRoleName;
   }

   public void setRelationshipRoleName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.relationshipRoleName;
      this.relationshipRoleName = value;
      this.isSet_relationshipRoleName = value != null;
      this.checkChange("relationshipRoleName", old, this.relationshipRoleName);
   }

   public boolean getDBCascadeDelete() {
      return this.dbCascadeDelete;
   }

   public void setDBCascadeDelete(boolean value) {
      boolean old = this.dbCascadeDelete;
      this.dbCascadeDelete = value;
      this.isSet_dbCascadeDelete = true;
      this.checkChange("dbCascadeDelete", old, this.dbCascadeDelete);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-relationship-role");
      result.append(">\n");
      if (null != this.getRelationshipRoleName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<relationship-role-name>").append(this.getRelationshipRoleName()).append("</relationship-role-name>\n");
      }

      if (null != this.getGroupName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<group-name>").append(this.getGroupName()).append("</group-name>\n");
      }

      if (null != this.getRelationshipRoleMap()) {
         result.append(this.getRelationshipRoleMap().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append(this.getDBCascadeDelete() ? "<db-cascade-delete/>\n" : "");
      result.append(ToXML.indent(indentLevel)).append("</weblogic-relationship-role>\n");
      return result.toString();
   }
}
