package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoMappingRepositoryType;
import javax.xml.namespace.QName;

public class KodoMappingRepositoryTypeImpl extends MetaDataRepositoryTypeImpl implements KodoMappingRepositoryType {
   private static final long serialVersionUID = 1L;
   private static final QName RESOLVE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "resolve");
   private static final QName VALIDATE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "validate");
   private static final QName SOURCEMODE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "source-mode");

   public KodoMappingRepositoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getResolve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOLVE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetResolve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RESOLVE$0, 0);
         return target;
      }
   }

   public boolean isSetResolve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOLVE$0) != 0;
      }
   }

   public void setResolve(int resolve) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOLVE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOLVE$0);
         }

         target.setIntValue(resolve);
      }
   }

   public void xsetResolve(XmlInt resolve) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RESOLVE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(RESOLVE$0);
         }

         target.set(resolve);
      }
   }

   public void unsetResolve() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOLVE$0, 0);
      }
   }

   public int getValidate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATE$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetValidate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(VALIDATE$2, 0);
         return target;
      }
   }

   public boolean isSetValidate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATE$2) != 0;
      }
   }

   public void setValidate(int validate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATE$2);
         }

         target.setIntValue(validate);
      }
   }

   public void xsetValidate(XmlInt validate) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(VALIDATE$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(VALIDATE$2);
         }

         target.set(validate);
      }
   }

   public void unsetValidate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATE$2, 0);
      }
   }

   public int getSourceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOURCEMODE$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetSourceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOURCEMODE$4, 0);
         return target;
      }
   }

   public boolean isSetSourceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOURCEMODE$4) != 0;
      }
   }

   public void setSourceMode(int sourceMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOURCEMODE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SOURCEMODE$4);
         }

         target.setIntValue(sourceMode);
      }
   }

   public void xsetSourceMode(XmlInt sourceMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOURCEMODE$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SOURCEMODE$4);
         }

         target.set(sourceMode);
      }
   }

   public void unsetSourceMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOURCEMODE$4, 0);
      }
   }
}
