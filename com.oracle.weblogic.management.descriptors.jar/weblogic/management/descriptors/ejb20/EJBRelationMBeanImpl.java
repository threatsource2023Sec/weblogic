package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EJBRelationMBeanImpl extends XMLElementMBeanDelegate implements EJBRelationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbRelationName = false;
   private String ejbRelationName;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbRelationshipRoles = false;
   private List ejbRelationshipRoles;

   public String getEJBRelationName() {
      return this.ejbRelationName;
   }

   public void setEJBRelationName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbRelationName;
      this.ejbRelationName = value;
      this.isSet_ejbRelationName = value != null;
      this.checkChange("ejbRelationName", old, this.ejbRelationName);
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

   public EJBRelationshipRoleMBean[] getEJBRelationshipRoles() {
      if (this.ejbRelationshipRoles == null) {
         return new EJBRelationshipRoleMBean[0];
      } else {
         EJBRelationshipRoleMBean[] result = new EJBRelationshipRoleMBean[this.ejbRelationshipRoles.size()];
         result = (EJBRelationshipRoleMBean[])((EJBRelationshipRoleMBean[])this.ejbRelationshipRoles.toArray(result));
         return result;
      }
   }

   public void setEJBRelationshipRoles(EJBRelationshipRoleMBean[] value) {
      EJBRelationshipRoleMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBRelationshipRoles();
      }

      this.isSet_ejbRelationshipRoles = true;
      if (this.ejbRelationshipRoles == null) {
         this.ejbRelationshipRoles = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbRelationshipRoles.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbRelationshipRoles.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBRelationshipRoles", _oldVal, this.getEJBRelationshipRoles());
      }

   }

   public void addEJBRelationshipRole(EJBRelationshipRoleMBean value) {
      this.isSet_ejbRelationshipRoles = true;
      if (this.ejbRelationshipRoles == null) {
         this.ejbRelationshipRoles = Collections.synchronizedList(new ArrayList());
      }

      this.ejbRelationshipRoles.add(value);
   }

   public void removeEJBRelationshipRole(EJBRelationshipRoleMBean value) {
      if (this.ejbRelationshipRoles != null) {
         this.ejbRelationshipRoles.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<ejb-relation");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBRelationName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-relation-name>").append(this.getEJBRelationName()).append("</ejb-relation-name>\n");
      }

      if (null != this.getEJBRelationshipRoles()) {
         for(int i = 0; i < this.getEJBRelationshipRoles().length; ++i) {
            result.append(this.getEJBRelationshipRoles()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</ejb-relation>\n");
      return result.toString();
   }
}
