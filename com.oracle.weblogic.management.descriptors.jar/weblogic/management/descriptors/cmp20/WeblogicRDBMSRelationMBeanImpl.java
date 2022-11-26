package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRDBMSRelationMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRDBMSRelationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_weblogicRelationshipRoles = false;
   private List weblogicRelationshipRoles;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_relationName = false;
   private String relationName;

   public WeblogicRelationshipRoleMBean[] getWeblogicRelationshipRoles() {
      if (this.weblogicRelationshipRoles == null) {
         return new WeblogicRelationshipRoleMBean[0];
      } else {
         WeblogicRelationshipRoleMBean[] result = new WeblogicRelationshipRoleMBean[this.weblogicRelationshipRoles.size()];
         result = (WeblogicRelationshipRoleMBean[])((WeblogicRelationshipRoleMBean[])this.weblogicRelationshipRoles.toArray(result));
         return result;
      }
   }

   public void setWeblogicRelationshipRoles(WeblogicRelationshipRoleMBean[] value) {
      WeblogicRelationshipRoleMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getWeblogicRelationshipRoles();
      }

      this.isSet_weblogicRelationshipRoles = true;
      if (this.weblogicRelationshipRoles == null) {
         this.weblogicRelationshipRoles = Collections.synchronizedList(new ArrayList());
      } else {
         this.weblogicRelationshipRoles.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.weblogicRelationshipRoles.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("WeblogicRelationshipRoles", _oldVal, this.getWeblogicRelationshipRoles());
      }

   }

   public void addWeblogicRelationshipRole(WeblogicRelationshipRoleMBean value) {
      this.isSet_weblogicRelationshipRoles = true;
      if (this.weblogicRelationshipRoles == null) {
         this.weblogicRelationshipRoles = Collections.synchronizedList(new ArrayList());
      }

      this.weblogicRelationshipRoles.add(value);
   }

   public void removeWeblogicRelationshipRole(WeblogicRelationshipRoleMBean value) {
      if (this.weblogicRelationshipRoles != null) {
         this.weblogicRelationshipRoles.remove(value);
      }
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.tableName;
      this.tableName = value;
      this.isSet_tableName = value != null;
      this.checkChange("tableName", old, this.tableName);
   }

   public String getRelationName() {
      return this.relationName;
   }

   public void setRelationName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.relationName;
      this.relationName = value;
      this.isSet_relationName = value != null;
      this.checkChange("relationName", old, this.relationName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-rdbms-relation");
      result.append(">\n");
      if (null != this.getRelationName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<relation-name>").append(this.getRelationName()).append("</relation-name>\n");
      }

      if (null != this.getTableName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<table-name>").append(this.getTableName()).append("</table-name>\n");
      }

      if (null != this.getWeblogicRelationshipRoles()) {
         for(int i = 0; i < this.getWeblogicRelationshipRoles().length; ++i) {
            result.append(this.getWeblogicRelationshipRoles()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-rdbms-relation>\n");
      return result.toString();
   }
}
