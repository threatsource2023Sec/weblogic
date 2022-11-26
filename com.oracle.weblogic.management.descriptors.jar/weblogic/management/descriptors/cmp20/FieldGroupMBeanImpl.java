package weblogic.management.descriptors.cmp20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class FieldGroupMBeanImpl extends XMLElementMBeanDelegate implements FieldGroupMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_groupName = false;
   private String groupName;
   private boolean isSet_cmrFields = false;
   private List cmrFields;
   private boolean isSet_cmpFields = false;
   private List cmpFields;

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

   public String[] getCMRFields() {
      if (this.cmrFields == null) {
         return new String[0];
      } else {
         String[] result = new String[this.cmrFields.size()];
         result = (String[])((String[])this.cmrFields.toArray(result));
         return result;
      }
   }

   public void setCMRFields(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getCMRFields();
      }

      this.isSet_cmrFields = true;
      if (this.cmrFields == null) {
         this.cmrFields = Collections.synchronizedList(new ArrayList());
      } else {
         this.cmrFields.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.cmrFields.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("CMRFields", _oldVal, this.getCMRFields());
      }

   }

   public void addCMRField(String value) {
      this.isSet_cmrFields = true;
      if (this.cmrFields == null) {
         this.cmrFields = Collections.synchronizedList(new ArrayList());
      }

      this.cmrFields.add(value);
   }

   public void removeCMRField(String value) {
      if (this.cmrFields != null) {
         this.cmrFields.remove(value);
      }
   }

   public String[] getCMPFields() {
      if (this.cmpFields == null) {
         return new String[0];
      } else {
         String[] result = new String[this.cmpFields.size()];
         result = (String[])((String[])this.cmpFields.toArray(result));
         return result;
      }
   }

   public void setCMPFields(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getCMPFields();
      }

      this.isSet_cmpFields = true;
      if (this.cmpFields == null) {
         this.cmpFields = Collections.synchronizedList(new ArrayList());
      } else {
         this.cmpFields.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.cmpFields.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("CMPFields", _oldVal, this.getCMPFields());
      }

   }

   public void addCMPField(String value) {
      this.isSet_cmpFields = true;
      if (this.cmpFields == null) {
         this.cmpFields = Collections.synchronizedList(new ArrayList());
      }

      this.cmpFields.add(value);
   }

   public void removeCMPField(String value) {
      if (this.cmpFields != null) {
         this.cmpFields.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<field-group");
      result.append(">\n");
      if (null != this.getGroupName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<group-name>").append(this.getGroupName()).append("</group-name>\n");
      }

      int i;
      if (this.isSet_cmpFields) {
         for(i = 0; i < this.getCMPFields().length; ++i) {
            result.append(ToXML.indent(indentLevel + 2)).append("<cmp-field>").append(this.getCMPFields()[i]).append("</cmp-field>\n");
         }
      }

      if (this.isSet_cmrFields) {
         for(i = 0; i < this.getCMRFields().length; ++i) {
            result.append(ToXML.indent(indentLevel + 2)).append("<cmr-field>").append(this.getCMRFields()[i]).append("</cmr-field>\n");
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</field-group>\n");
      return result.toString();
   }
}
