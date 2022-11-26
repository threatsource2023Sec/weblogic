package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.WeblogicModuleType;
import javax.xml.namespace.QName;

public class WeblogicModuleTypeImpl extends XmlComplexContentImpl implements WeblogicModuleType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "name");
   private static final QName TYPE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "type");
   private static final QName PATH$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "path");

   public WeblogicModuleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public WeblogicModuleType.Type.Enum getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$2, 0);
         return target == null ? null : (WeblogicModuleType.Type.Enum)target.getEnumValue();
      }
   }

   public WeblogicModuleType.Type xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicModuleType.Type target = null;
         target = (WeblogicModuleType.Type)this.get_store().find_element_user(TYPE$2, 0);
         return target;
      }
   }

   public void setType(WeblogicModuleType.Type.Enum type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$2);
         }

         target.setEnumValue(type);
      }
   }

   public void xsetType(WeblogicModuleType.Type type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicModuleType.Type target = null;
         target = (WeblogicModuleType.Type)this.get_store().find_element_user(TYPE$2, 0);
         if (target == null) {
            target = (WeblogicModuleType.Type)this.get_store().add_element_user(TYPE$2);
         }

         target.set(type);
      }
   }

   public String getPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PATH$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PATH$4, 0);
         return target;
      }
   }

   public void setPath(String path) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PATH$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PATH$4);
         }

         target.setStringValue(path);
      }
   }

   public void xsetPath(XmlString path) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PATH$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PATH$4);
         }

         target.set(path);
      }
   }

   public static class TypeImpl extends JavaStringEnumerationHolderEx implements WeblogicModuleType.Type {
      private static final long serialVersionUID = 1L;

      public TypeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected TypeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
