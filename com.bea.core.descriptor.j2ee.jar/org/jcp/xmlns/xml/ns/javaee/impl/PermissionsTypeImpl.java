package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DeweyVersionType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.PermissionsType;

public class PermissionsTypeImpl extends XmlComplexContentImpl implements PermissionsType {
   private static final long serialVersionUID = 1L;
   private static final QName PERMISSION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "permission");
   private static final QName VERSION$2 = new QName("", "version");

   public PermissionsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PermissionsType.Permission[] getPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERMISSION$0, targetList);
         PermissionsType.Permission[] result = new PermissionsType.Permission[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PermissionsType.Permission getPermissionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PermissionsType.Permission target = null;
         target = (PermissionsType.Permission)this.get_store().find_element_user(PERMISSION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPermissionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERMISSION$0);
      }
   }

   public void setPermissionArray(PermissionsType.Permission[] permissionArray) {
      this.check_orphaned();
      this.arraySetterHelper(permissionArray, PERMISSION$0);
   }

   public void setPermissionArray(int i, PermissionsType.Permission permission) {
      this.generatedSetterHelperImpl(permission, PERMISSION$0, i, (short)2);
   }

   public PermissionsType.Permission insertNewPermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PermissionsType.Permission target = null;
         target = (PermissionsType.Permission)this.get_store().insert_element_user(PERMISSION$0, i);
         return target;
      }
   }

   public PermissionsType.Permission addNewPermission() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PermissionsType.Permission target = null;
         target = (PermissionsType.Permission)this.get_store().add_element_user(PERMISSION$0);
         return target;
      }
   }

   public void removePermission(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERMISSION$0, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$2);
         }

         return target == null ? null : target.getStringValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$2);
         }

         return target;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$2);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$2);
         }

         target.set(version);
      }
   }

   public static class PermissionImpl extends XmlComplexContentImpl implements PermissionsType.Permission {
      private static final long serialVersionUID = 1L;
      private static final QName CLASSNAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "class-name");
      private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
      private static final QName ACTIONS$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "actions");

      public PermissionImpl(SchemaType sType) {
         super(sType);
      }

      public FullyQualifiedClassType getClassName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FullyQualifiedClassType target = null;
            target = (FullyQualifiedClassType)this.get_store().find_element_user(CLASSNAME$0, 0);
            return target == null ? null : target;
         }
      }

      public void setClassName(FullyQualifiedClassType className) {
         this.generatedSetterHelperImpl(className, CLASSNAME$0, 0, (short)1);
      }

      public FullyQualifiedClassType addNewClassName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FullyQualifiedClassType target = null;
            target = (FullyQualifiedClassType)this.get_store().add_element_user(CLASSNAME$0);
            return target;
         }
      }

      public org.jcp.xmlns.xml.ns.javaee.String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            org.jcp.xmlns.xml.ns.javaee.String target = null;
            target = (org.jcp.xmlns.xml.ns.javaee.String)this.get_store().find_element_user(NAME$2, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(NAME$2) != 0;
         }
      }

      public void setName(org.jcp.xmlns.xml.ns.javaee.String name) {
         this.generatedSetterHelperImpl(name, NAME$2, 0, (short)1);
      }

      public org.jcp.xmlns.xml.ns.javaee.String addNewName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            org.jcp.xmlns.xml.ns.javaee.String target = null;
            target = (org.jcp.xmlns.xml.ns.javaee.String)this.get_store().add_element_user(NAME$2);
            return target;
         }
      }

      public void unsetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(NAME$2, 0);
         }
      }

      public org.jcp.xmlns.xml.ns.javaee.String getActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            org.jcp.xmlns.xml.ns.javaee.String target = null;
            target = (org.jcp.xmlns.xml.ns.javaee.String)this.get_store().find_element_user(ACTIONS$4, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ACTIONS$4) != 0;
         }
      }

      public void setActions(org.jcp.xmlns.xml.ns.javaee.String actions) {
         this.generatedSetterHelperImpl(actions, ACTIONS$4, 0, (short)1);
      }

      public org.jcp.xmlns.xml.ns.javaee.String addNewActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            org.jcp.xmlns.xml.ns.javaee.String target = null;
            target = (org.jcp.xmlns.xml.ns.javaee.String)this.get_store().add_element_user(ACTIONS$4);
            return target;
         }
      }

      public void unsetActions() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ACTIONS$4, 0);
         }
      }
   }
}
