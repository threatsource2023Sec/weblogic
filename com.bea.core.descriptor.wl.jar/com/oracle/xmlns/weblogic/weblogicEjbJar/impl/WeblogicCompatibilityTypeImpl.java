package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.WeblogicCompatibilityType;
import javax.xml.namespace.QName;

public class WeblogicCompatibilityTypeImpl extends XmlComplexContentImpl implements WeblogicCompatibilityType {
   private static final long serialVersionUID = 1L;
   private static final QName ENTITYALWAYSUSESTRANSACTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "entity-always-uses-transaction");
   private static final QName ID$2 = new QName("", "id");

   public WeblogicCompatibilityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEntityAlwaysUsesTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENTITYALWAYSUSESTRANSACTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEntityAlwaysUsesTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTITYALWAYSUSESTRANSACTION$0) != 0;
      }
   }

   public void setEntityAlwaysUsesTransaction(TrueFalseType entityAlwaysUsesTransaction) {
      this.generatedSetterHelperImpl(entityAlwaysUsesTransaction, ENTITYALWAYSUSESTRANSACTION$0, 0, (short)1);
   }

   public TrueFalseType addNewEntityAlwaysUsesTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENTITYALWAYSUSESTRANSACTION$0);
         return target;
      }
   }

   public void unsetEntityAlwaysUsesTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTITYALWAYSUSESTRANSACTION$0, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
