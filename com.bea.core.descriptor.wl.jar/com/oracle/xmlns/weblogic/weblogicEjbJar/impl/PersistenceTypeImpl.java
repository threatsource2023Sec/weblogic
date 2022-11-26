package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.IsModifiedMethodNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PersistenceType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PersistenceUseType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class PersistenceTypeImpl extends XmlComplexContentImpl implements PersistenceType {
   private static final long serialVersionUID = 1L;
   private static final QName ISMODIFIEDMETHODNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "is-modified-method-name");
   private static final QName DELAYUPDATESUNTILENDOFTX$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "delay-updates-until-end-of-tx");
   private static final QName FINDERSLOADBEAN$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "finders-load-bean");
   private static final QName PERSISTENCEUSE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "persistence-use");
   private static final QName ID$8 = new QName("", "id");

   public PersistenceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public IsModifiedMethodNameType getIsModifiedMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsModifiedMethodNameType target = null;
         target = (IsModifiedMethodNameType)this.get_store().find_element_user(ISMODIFIEDMETHODNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIsModifiedMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ISMODIFIEDMETHODNAME$0) != 0;
      }
   }

   public void setIsModifiedMethodName(IsModifiedMethodNameType isModifiedMethodName) {
      this.generatedSetterHelperImpl(isModifiedMethodName, ISMODIFIEDMETHODNAME$0, 0, (short)1);
   }

   public IsModifiedMethodNameType addNewIsModifiedMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsModifiedMethodNameType target = null;
         target = (IsModifiedMethodNameType)this.get_store().add_element_user(ISMODIFIEDMETHODNAME$0);
         return target;
      }
   }

   public void unsetIsModifiedMethodName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ISMODIFIEDMETHODNAME$0, 0);
      }
   }

   public TrueFalseType getDelayUpdatesUntilEndOfTx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DELAYUPDATESUNTILENDOFTX$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDelayUpdatesUntilEndOfTx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELAYUPDATESUNTILENDOFTX$2) != 0;
      }
   }

   public void setDelayUpdatesUntilEndOfTx(TrueFalseType delayUpdatesUntilEndOfTx) {
      this.generatedSetterHelperImpl(delayUpdatesUntilEndOfTx, DELAYUPDATESUNTILENDOFTX$2, 0, (short)1);
   }

   public TrueFalseType addNewDelayUpdatesUntilEndOfTx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DELAYUPDATESUNTILENDOFTX$2);
         return target;
      }
   }

   public void unsetDelayUpdatesUntilEndOfTx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELAYUPDATESUNTILENDOFTX$2, 0);
      }
   }

   public TrueFalseType getFindersLoadBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(FINDERSLOADBEAN$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFindersLoadBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDERSLOADBEAN$4) != 0;
      }
   }

   public void setFindersLoadBean(TrueFalseType findersLoadBean) {
      this.generatedSetterHelperImpl(findersLoadBean, FINDERSLOADBEAN$4, 0, (short)1);
   }

   public TrueFalseType addNewFindersLoadBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(FINDERSLOADBEAN$4);
         return target;
      }
   }

   public void unsetFindersLoadBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDERSLOADBEAN$4, 0);
      }
   }

   public PersistenceUseType getPersistenceUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUseType target = null;
         target = (PersistenceUseType)this.get_store().find_element_user(PERSISTENCEUSE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistenceUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUSE$6) != 0;
      }
   }

   public void setPersistenceUse(PersistenceUseType persistenceUse) {
      this.generatedSetterHelperImpl(persistenceUse, PERSISTENCEUSE$6, 0, (short)1);
   }

   public PersistenceUseType addNewPersistenceUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUseType target = null;
         target = (PersistenceUseType)this.get_store().add_element_user(PERSISTENCEUSE$6);
         return target;
      }
   }

   public void unsetPersistenceUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUSE$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
