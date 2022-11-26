package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PersistenceUseType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TypeIdentifierType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TypeStorageType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TypeVersionType;
import javax.xml.namespace.QName;

public class PersistenceUseTypeImpl extends XmlComplexContentImpl implements PersistenceUseType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPEIDENTIFIER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "type-identifier");
   private static final QName TYPEVERSION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "type-version");
   private static final QName TYPESTORAGE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "type-storage");
   private static final QName ID$6 = new QName("", "id");

   public PersistenceUseTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TypeIdentifierType getTypeIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeIdentifierType target = null;
         target = (TypeIdentifierType)this.get_store().find_element_user(TYPEIDENTIFIER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTypeIdentifier(TypeIdentifierType typeIdentifier) {
      this.generatedSetterHelperImpl(typeIdentifier, TYPEIDENTIFIER$0, 0, (short)1);
   }

   public TypeIdentifierType addNewTypeIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeIdentifierType target = null;
         target = (TypeIdentifierType)this.get_store().add_element_user(TYPEIDENTIFIER$0);
         return target;
      }
   }

   public TypeVersionType getTypeVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeVersionType target = null;
         target = (TypeVersionType)this.get_store().find_element_user(TYPEVERSION$2, 0);
         return target == null ? null : target;
      }
   }

   public void setTypeVersion(TypeVersionType typeVersion) {
      this.generatedSetterHelperImpl(typeVersion, TYPEVERSION$2, 0, (short)1);
   }

   public TypeVersionType addNewTypeVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeVersionType target = null;
         target = (TypeVersionType)this.get_store().add_element_user(TYPEVERSION$2);
         return target;
      }
   }

   public TypeStorageType getTypeStorage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeStorageType target = null;
         target = (TypeStorageType)this.get_store().find_element_user(TYPESTORAGE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setTypeStorage(TypeStorageType typeStorage) {
      this.generatedSetterHelperImpl(typeStorage, TYPESTORAGE$4, 0, (short)1);
   }

   public TypeStorageType addNewTypeStorage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TypeStorageType target = null;
         target = (TypeStorageType)this.get_store().add_element_user(TYPESTORAGE$4);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
