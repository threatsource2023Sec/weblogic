package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultPersistentStoreType;
import javax.xml.namespace.QName;

public class DefaultPersistentStoreTypeImpl extends XmlComplexContentImpl implements DefaultPersistentStoreType {
   private static final long serialVersionUID = 1L;
   private static final QName NOTES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "notes");
   private static final QName DIRECTORYPATH$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "directory-path");
   private static final QName SYNCHRONOUSWRITEPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "synchronous-write-policy");

   public DefaultPersistentStoreTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         return target;
      }
   }

   public boolean isSetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NOTES$0) != 0;
      }
   }

   public void setNotes(String notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NOTES$0);
         }

         target.setStringValue(notes);
      }
   }

   public void xsetNotes(XmlString notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$0);
         }

         target.set(notes);
      }
   }

   public void unsetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NOTES$0, 0);
      }
   }

   public String getDirectoryPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DIRECTORYPATH$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDirectoryPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIRECTORYPATH$2, 0);
         return target;
      }
   }

   public boolean isSetDirectoryPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DIRECTORYPATH$2) != 0;
      }
   }

   public void setDirectoryPath(String directoryPath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DIRECTORYPATH$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DIRECTORYPATH$2);
         }

         target.setStringValue(directoryPath);
      }
   }

   public void xsetDirectoryPath(XmlString directoryPath) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIRECTORYPATH$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DIRECTORYPATH$2);
         }

         target.set(directoryPath);
      }
   }

   public void unsetDirectoryPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DIRECTORYPATH$2, 0);
      }
   }

   public DefaultPersistentStoreType.SynchronousWritePolicy.Enum getSynchronousWritePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONOUSWRITEPOLICY$4, 0);
         return target == null ? null : (DefaultPersistentStoreType.SynchronousWritePolicy.Enum)target.getEnumValue();
      }
   }

   public DefaultPersistentStoreType.SynchronousWritePolicy xgetSynchronousWritePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultPersistentStoreType.SynchronousWritePolicy target = null;
         target = (DefaultPersistentStoreType.SynchronousWritePolicy)this.get_store().find_element_user(SYNCHRONOUSWRITEPOLICY$4, 0);
         return target;
      }
   }

   public boolean isSetSynchronousWritePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYNCHRONOUSWRITEPOLICY$4) != 0;
      }
   }

   public void setSynchronousWritePolicy(DefaultPersistentStoreType.SynchronousWritePolicy.Enum synchronousWritePolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONOUSWRITEPOLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYNCHRONOUSWRITEPOLICY$4);
         }

         target.setEnumValue(synchronousWritePolicy);
      }
   }

   public void xsetSynchronousWritePolicy(DefaultPersistentStoreType.SynchronousWritePolicy synchronousWritePolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultPersistentStoreType.SynchronousWritePolicy target = null;
         target = (DefaultPersistentStoreType.SynchronousWritePolicy)this.get_store().find_element_user(SYNCHRONOUSWRITEPOLICY$4, 0);
         if (target == null) {
            target = (DefaultPersistentStoreType.SynchronousWritePolicy)this.get_store().add_element_user(SYNCHRONOUSWRITEPOLICY$4);
         }

         target.set(synchronousWritePolicy);
      }
   }

   public void unsetSynchronousWritePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYNCHRONOUSWRITEPOLICY$4, 0);
      }
   }

   public static class SynchronousWritePolicyImpl extends JavaStringEnumerationHolderEx implements DefaultPersistentStoreType.SynchronousWritePolicy {
      private static final long serialVersionUID = 1L;

      public SynchronousWritePolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SynchronousWritePolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
