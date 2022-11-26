package org.jcp.xmlns.xml.ns.persistence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.persistence.PersistenceType;
import org.jcp.xmlns.xml.ns.persistence.PersistenceUnitType;
import org.jcp.xmlns.xml.ns.persistence.VersionType;

public class PersistenceTypeImpl extends XmlComplexContentImpl implements PersistenceType {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENCEUNIT$0 = new QName("http://xmlns.jcp.org/xml/ns/persistence", "persistence-unit");
   private static final QName VERSION$2 = new QName("", "version");

   public PersistenceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PersistenceUnitType[] getPersistenceUnitArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNIT$0, targetList);
         PersistenceUnitType[] result = new PersistenceUnitType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitType getPersistenceUnitArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitType target = null;
         target = (PersistenceUnitType)this.get_store().find_element_user(PERSISTENCEUNIT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceUnitArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNIT$0);
      }
   }

   public void setPersistenceUnitArray(PersistenceUnitType[] persistenceUnitArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitArray, PERSISTENCEUNIT$0);
   }

   public void setPersistenceUnitArray(int i, PersistenceUnitType persistenceUnit) {
      this.generatedSetterHelperImpl(persistenceUnit, PERSISTENCEUNIT$0, i, (short)2);
   }

   public PersistenceUnitType insertNewPersistenceUnit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitType target = null;
         target = (PersistenceUnitType)this.get_store().insert_element_user(PERSISTENCEUNIT$0, i);
         return target;
      }
   }

   public PersistenceUnitType addNewPersistenceUnit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitType target = null;
         target = (PersistenceUnitType)this.get_store().add_element_user(PERSISTENCEUNIT$0);
         return target;
      }
   }

   public void removePersistenceUnit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNIT$0, i);
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

   public VersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionType target = null;
         target = (VersionType)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (VersionType)this.get_default_attribute_value(VERSION$2);
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

   public void xsetVersion(VersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionType target = null;
         target = (VersionType)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (VersionType)this.get_store().add_attribute_user(VERSION$2);
         }

         target.set(version);
      }
   }
}
