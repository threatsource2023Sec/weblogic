package com.oracle.xmlns.weblogic.weblogicExtension.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicExtension.ModuleProviderType;
import javax.xml.namespace.QName;

public class ModuleProviderTypeImpl extends XmlComplexContentImpl implements ModuleProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "name");
   private static final QName MODULEFACTORYCLASSNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "module-factory-class-name");
   private static final QName BINDINGJARURI$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "binding-jar-uri");

   public ModuleProviderTypeImpl(SchemaType sType) {
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

   public String getModuleFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULEFACTORYCLASSNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetModuleFactoryClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULEFACTORYCLASSNAME$2, 0);
         return target;
      }
   }

   public void setModuleFactoryClassName(String moduleFactoryClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULEFACTORYCLASSNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MODULEFACTORYCLASSNAME$2);
         }

         target.setStringValue(moduleFactoryClassName);
      }
   }

   public void xsetModuleFactoryClassName(XmlString moduleFactoryClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULEFACTORYCLASSNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MODULEFACTORYCLASSNAME$2);
         }

         target.set(moduleFactoryClassName);
      }
   }

   public String getBindingJarUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BINDINGJARURI$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBindingJarUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINDINGJARURI$4, 0);
         return target;
      }
   }

   public boolean isSetBindingJarUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BINDINGJARURI$4) != 0;
      }
   }

   public void setBindingJarUri(String bindingJarUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BINDINGJARURI$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BINDINGJARURI$4);
         }

         target.setStringValue(bindingJarUri);
      }
   }

   public void xsetBindingJarUri(XmlString bindingJarUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BINDINGJARURI$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BINDINGJARURI$4);
         }

         target.set(bindingJarUri);
      }
   }

   public void unsetBindingJarUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BINDINGJARURI$4, 0);
      }
   }
}
