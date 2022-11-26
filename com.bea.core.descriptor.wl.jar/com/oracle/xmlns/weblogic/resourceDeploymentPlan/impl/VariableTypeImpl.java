package com.oracle.xmlns.weblogic.resourceDeploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.resourceDeploymentPlan.VariableType;
import javax.xml.namespace.QName;

public class VariableTypeImpl extends XmlComplexContentImpl implements VariableType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "name");
   private static final QName VALUE$2 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "value");
   private static final QName DESCRIPTION$4 = new QName("http://xmlns.oracle.com/weblogic/resource-deployment-plan", "description");

   public VariableTypeImpl(SchemaType sType) {
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

   public String getValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         return target;
      }
   }

   public boolean isNilValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALUE$2) != 0;
      }
   }

   public void setValue(String value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALUE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALUE$2);
         }

         target.setStringValue(value);
      }
   }

   public void xsetValue(XmlString value) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALUE$2);
         }

         target.set(value);
      }
   }

   public void setNilValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VALUE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VALUE$2);
         }

         target.setNil();
      }
   }

   public void unsetValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALUE$2, 0);
      }
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$4, 0);
         return target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$4) != 0;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$4);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$4);
         }

         target.set(description);
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$4, 0);
      }
   }
}
