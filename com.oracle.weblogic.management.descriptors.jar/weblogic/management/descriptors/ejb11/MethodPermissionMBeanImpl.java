package weblogic.management.descriptors.ejb11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MethodPermissionMBeanImpl extends XMLElementMBeanDelegate implements MethodPermissionMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_roleNames = false;
   private List roleNames;
   private boolean isSet_methods = false;
   private List methods;

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

   public String[] getRoleNames() {
      if (this.roleNames == null) {
         return new String[0];
      } else {
         String[] result = new String[this.roleNames.size()];
         result = (String[])((String[])this.roleNames.toArray(result));
         return result;
      }
   }

   public void setRoleNames(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getRoleNames();
      }

      this.isSet_roleNames = true;
      if (this.roleNames == null) {
         this.roleNames = Collections.synchronizedList(new ArrayList());
      } else {
         this.roleNames.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.roleNames.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("RoleNames", _oldVal, this.getRoleNames());
      }

   }

   public void addRoleName(String value) {
      this.isSet_roleNames = true;
      if (this.roleNames == null) {
         this.roleNames = Collections.synchronizedList(new ArrayList());
      }

      this.roleNames.add(value);
   }

   public void removeRoleName(String value) {
      if (this.roleNames != null) {
         this.roleNames.remove(value);
      }
   }

   public MethodMBean[] getMethods() {
      if (this.methods == null) {
         return new MethodMBean[0];
      } else {
         MethodMBean[] result = new MethodMBean[this.methods.size()];
         result = (MethodMBean[])((MethodMBean[])this.methods.toArray(result));
         return result;
      }
   }

   public void setMethods(MethodMBean[] value) {
      MethodMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getMethods();
      }

      this.isSet_methods = true;
      if (this.methods == null) {
         this.methods = Collections.synchronizedList(new ArrayList());
      } else {
         this.methods.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.methods.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Methods", _oldVal, this.getMethods());
      }

   }

   public void addMethod(MethodMBean value) {
      this.isSet_methods = true;
      if (this.methods == null) {
         this.methods = Collections.synchronizedList(new ArrayList());
      }

      this.methods.add(value);
   }

   public void removeMethod(MethodMBean value) {
      if (this.methods != null) {
         this.methods.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<method-permission");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      int i;
      for(i = 0; i < this.getRoleNames().length; ++i) {
         result.append(ToXML.indent(indentLevel + 2)).append("<role-name>").append(this.getRoleNames()[i]).append("</role-name>\n");
      }

      if (null != this.getMethods()) {
         for(i = 0; i < this.getMethods().length; ++i) {
            result.append(this.getMethods()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</method-permission>\n");
      return result.toString();
   }
}
