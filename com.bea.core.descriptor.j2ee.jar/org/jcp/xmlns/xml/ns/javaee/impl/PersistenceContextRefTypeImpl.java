package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.InjectionTargetType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextRefType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextSynchronizationType;
import org.jcp.xmlns.xml.ns.javaee.PersistenceContextTypeType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class PersistenceContextRefTypeImpl extends XmlComplexContentImpl implements PersistenceContextRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName PERSISTENCECONTEXTREFNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-ref-name");
   private static final QName PERSISTENCEUNITNAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-unit-name");
   private static final QName PERSISTENCECONTEXTTYPE$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-type");
   private static final QName PERSISTENCECONTEXTSYNCHRONIZATION$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-context-synchronization");
   private static final QName PERSISTENCEPROPERTY$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "persistence-property");
   private static final QName MAPPEDNAME$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapped-name");
   private static final QName INJECTIONTARGET$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "injection-target");
   private static final QName ID$16 = new QName("", "id");

   public PersistenceContextRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public JndiNameType getPersistenceContextRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(PERSISTENCECONTEXTREFNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setPersistenceContextRefName(JndiNameType persistenceContextRefName) {
      this.generatedSetterHelperImpl(persistenceContextRefName, PERSISTENCECONTEXTREFNAME$2, 0, (short)1);
   }

   public JndiNameType addNewPersistenceContextRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(PERSISTENCECONTEXTREFNAME$2);
         return target;
      }
   }

   public String getPersistenceUnitName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PERSISTENCEUNITNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistenceUnitName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNITNAME$4) != 0;
      }
   }

   public void setPersistenceUnitName(String persistenceUnitName) {
      this.generatedSetterHelperImpl(persistenceUnitName, PERSISTENCEUNITNAME$4, 0, (short)1);
   }

   public String addNewPersistenceUnitName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PERSISTENCEUNITNAME$4);
         return target;
      }
   }

   public void unsetPersistenceUnitName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITNAME$4, 0);
      }
   }

   public PersistenceContextTypeType getPersistenceContextType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextTypeType target = null;
         target = (PersistenceContextTypeType)this.get_store().find_element_user(PERSISTENCECONTEXTTYPE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistenceContextType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONTEXTTYPE$6) != 0;
      }
   }

   public void setPersistenceContextType(PersistenceContextTypeType persistenceContextType) {
      this.generatedSetterHelperImpl(persistenceContextType, PERSISTENCECONTEXTTYPE$6, 0, (short)1);
   }

   public PersistenceContextTypeType addNewPersistenceContextType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextTypeType target = null;
         target = (PersistenceContextTypeType)this.get_store().add_element_user(PERSISTENCECONTEXTTYPE$6);
         return target;
      }
   }

   public void unsetPersistenceContextType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTTYPE$6, 0);
      }
   }

   public PersistenceContextSynchronizationType getPersistenceContextSynchronization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextSynchronizationType target = null;
         target = (PersistenceContextSynchronizationType)this.get_store().find_element_user(PERSISTENCECONTEXTSYNCHRONIZATION$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistenceContextSynchronization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONTEXTSYNCHRONIZATION$8) != 0;
      }
   }

   public void setPersistenceContextSynchronization(PersistenceContextSynchronizationType persistenceContextSynchronization) {
      this.generatedSetterHelperImpl(persistenceContextSynchronization, PERSISTENCECONTEXTSYNCHRONIZATION$8, 0, (short)1);
   }

   public PersistenceContextSynchronizationType addNewPersistenceContextSynchronization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextSynchronizationType target = null;
         target = (PersistenceContextSynchronizationType)this.get_store().add_element_user(PERSISTENCECONTEXTSYNCHRONIZATION$8);
         return target;
      }
   }

   public void unsetPersistenceContextSynchronization() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTSYNCHRONIZATION$8, 0);
      }
   }

   public PropertyType[] getPersistencePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEPROPERTY$10, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPersistencePropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PERSISTENCEPROPERTY$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistencePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEPROPERTY$10);
      }
   }

   public void setPersistencePropertyArray(PropertyType[] persistencePropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistencePropertyArray, PERSISTENCEPROPERTY$10);
   }

   public void setPersistencePropertyArray(int i, PropertyType persistenceProperty) {
      this.generatedSetterHelperImpl(persistenceProperty, PERSISTENCEPROPERTY$10, i, (short)2);
   }

   public PropertyType insertNewPersistenceProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PERSISTENCEPROPERTY$10, i);
         return target;
      }
   }

   public PropertyType addNewPersistenceProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PERSISTENCEPROPERTY$10);
         return target;
      }
   }

   public void removePersistenceProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEPROPERTY$10, i);
      }
   }

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$12) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$12, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$12);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$12, 0);
      }
   }

   public InjectionTargetType[] getInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INJECTIONTARGET$14, targetList);
         InjectionTargetType[] result = new InjectionTargetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InjectionTargetType getInjectionTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().find_element_user(INJECTIONTARGET$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INJECTIONTARGET$14);
      }
   }

   public void setInjectionTargetArray(InjectionTargetType[] injectionTargetArray) {
      this.check_orphaned();
      this.arraySetterHelper(injectionTargetArray, INJECTIONTARGET$14);
   }

   public void setInjectionTargetArray(int i, InjectionTargetType injectionTarget) {
      this.generatedSetterHelperImpl(injectionTarget, INJECTIONTARGET$14, i, (short)2);
   }

   public InjectionTargetType insertNewInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().insert_element_user(INJECTIONTARGET$14, i);
         return target;
      }
   }

   public InjectionTargetType addNewInjectionTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().add_element_user(INJECTIONTARGET$14);
         return target;
      }
   }

   public void removeInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INJECTIONTARGET$14, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
