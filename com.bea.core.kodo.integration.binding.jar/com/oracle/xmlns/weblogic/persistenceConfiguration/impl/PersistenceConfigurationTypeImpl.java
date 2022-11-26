package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceConfigurationType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceUnitConfigurationType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PersistenceConfigurationTypeImpl extends XmlComplexContentImpl implements PersistenceConfigurationType {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENCEUNITCONFIGURATION$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "persistence-unit-configuration");

   public PersistenceConfigurationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PersistenceUnitConfigurationType[] getPersistenceUnitConfigurationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITCONFIGURATION$0, targetList);
         PersistenceUnitConfigurationType[] result = new PersistenceUnitConfigurationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitConfigurationType getPersistenceUnitConfigurationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType target = null;
         target = (PersistenceUnitConfigurationType)this.get_store().find_element_user(PERSISTENCEUNITCONFIGURATION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilPersistenceUnitConfigurationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType target = null;
         target = (PersistenceUnitConfigurationType)this.get_store().find_element_user(PERSISTENCEUNITCONFIGURATION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfPersistenceUnitConfigurationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNITCONFIGURATION$0);
      }
   }

   public void setPersistenceUnitConfigurationArray(PersistenceUnitConfigurationType[] persistenceUnitConfigurationArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitConfigurationArray, PERSISTENCEUNITCONFIGURATION$0);
   }

   public void setPersistenceUnitConfigurationArray(int i, PersistenceUnitConfigurationType persistenceUnitConfiguration) {
      this.generatedSetterHelperImpl(persistenceUnitConfiguration, PERSISTENCEUNITCONFIGURATION$0, i, (short)2);
   }

   public void setNilPersistenceUnitConfigurationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType target = null;
         target = (PersistenceUnitConfigurationType)this.get_store().find_element_user(PERSISTENCEUNITCONFIGURATION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public PersistenceUnitConfigurationType insertNewPersistenceUnitConfiguration(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType target = null;
         target = (PersistenceUnitConfigurationType)this.get_store().insert_element_user(PERSISTENCEUNITCONFIGURATION$0, i);
         return target;
      }
   }

   public PersistenceUnitConfigurationType addNewPersistenceUnitConfiguration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType target = null;
         target = (PersistenceUnitConfigurationType)this.get_store().add_element_user(PERSISTENCEUNITCONFIGURATION$0);
         return target;
      }
   }

   public void removePersistenceUnitConfiguration(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITCONFIGURATION$0, i);
      }
   }
}
