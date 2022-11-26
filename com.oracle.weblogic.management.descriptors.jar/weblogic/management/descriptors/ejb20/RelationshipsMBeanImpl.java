package weblogic.management.descriptors.ejb20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RelationshipsMBeanImpl extends XMLElementMBeanDelegate implements RelationshipsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbRelations = false;
   private List ejbRelations;

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

   public EJBRelationMBean[] getEJBRelations() {
      if (this.ejbRelations == null) {
         return new EJBRelationMBean[0];
      } else {
         EJBRelationMBean[] result = new EJBRelationMBean[this.ejbRelations.size()];
         result = (EJBRelationMBean[])((EJBRelationMBean[])this.ejbRelations.toArray(result));
         return result;
      }
   }

   public void setEJBRelations(EJBRelationMBean[] value) {
      EJBRelationMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBRelations();
      }

      this.isSet_ejbRelations = true;
      if (this.ejbRelations == null) {
         this.ejbRelations = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbRelations.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbRelations.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBRelations", _oldVal, this.getEJBRelations());
      }

   }

   public void addEJBRelation(EJBRelationMBean value) {
      this.isSet_ejbRelations = true;
      if (this.ejbRelations == null) {
         this.ejbRelations = Collections.synchronizedList(new ArrayList());
      }

      this.ejbRelations.add(value);
   }

   public void removeEJBRelation(EJBRelationMBean value) {
      if (this.ejbRelations != null) {
         this.ejbRelations.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<relationships");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getEJBRelations()) {
         for(int i = 0; i < this.getEJBRelations().length; ++i) {
            result.append(this.getEJBRelations()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</relationships>\n");
      return result.toString();
   }
}
