package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebservices.BufferingQueueType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class BufferingQueueTypeImpl extends XmlComplexContentImpl implements BufferingQueueType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "name");
   private static final QName ENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "enabled");
   private static final QName CONNECTIONFACTORYJNDINAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "connection-factory-jndi-name");
   private static final QName TRANSACTIONENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "transaction-enabled");

   public BufferingQueueTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAME$0) != 0;
      }
   }

   public void setName(String name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public String addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public void setNilName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(NAME$0);
         }

         target.setNil();
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAME$0, 0);
      }
   }

   public boolean getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLED$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLED$2, 0);
         return target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$2) != 0;
      }
   }

   public void setEnabled(boolean enabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLED$2);
         }

         target.setBooleanValue(enabled);
      }
   }

   public void xsetEnabled(XmlBoolean enabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLED$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLED$2);
         }

         target.set(enabled);
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$2, 0);
      }
   }

   public java.lang.String getConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYJNDINAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYJNDINAME$4, 0);
         return target;
      }
   }

   public boolean isSetConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYJNDINAME$4) != 0;
      }
   }

   public void setConnectionFactoryJndiName(java.lang.String connectionFactoryJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYJNDINAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYJNDINAME$4);
         }

         target.setStringValue(connectionFactoryJndiName);
      }
   }

   public void xsetConnectionFactoryJndiName(XmlString connectionFactoryJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYJNDINAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYJNDINAME$4);
         }

         target.set(connectionFactoryJndiName);
      }
   }

   public void unsetConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYJNDINAME$4, 0);
      }
   }

   public boolean getTransactionEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONENABLED$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetTransactionEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TRANSACTIONENABLED$6, 0);
         return target;
      }
   }

   public boolean isSetTransactionEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONENABLED$6) != 0;
      }
   }

   public void setTransactionEnabled(boolean transactionEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONENABLED$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONENABLED$6);
         }

         target.setBooleanValue(transactionEnabled);
      }
   }

   public void xsetTransactionEnabled(XmlBoolean transactionEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TRANSACTIONENABLED$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(TRANSACTIONENABLED$6);
         }

         target.set(transactionEnabled);
      }
   }

   public void unsetTransactionEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONENABLED$6, 0);
      }
   }
}
