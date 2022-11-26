package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryFailureParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.ExpirationPolicyType;
import javax.xml.namespace.QName;

public class DeliveryFailureParamsTypeImpl extends XmlComplexContentImpl implements DeliveryFailureParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName ERRORDESTINATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "error-destination");
   private static final QName REDELIVERYLIMIT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "redelivery-limit");
   private static final QName EXPIRATIONPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "expiration-policy");
   private static final QName EXPIRATIONLOGGINGPOLICY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "expiration-logging-policy");

   public DeliveryFailureParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target;
      }
   }

   public boolean isNilErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ERRORDESTINATION$0) != 0;
      }
   }

   public void setErrorDestination(String errorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.setStringValue(errorDestination);
      }
   }

   public void xsetErrorDestination(XmlString errorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.set(errorDestination);
      }
   }

   public void setNilErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ERRORDESTINATION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ERRORDESTINATION$0);
         }

         target.setNil();
      }
   }

   public void unsetErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ERRORDESTINATION$0, 0);
      }
   }

   public int getRedeliveryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REDELIVERYLIMIT$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRedeliveryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(REDELIVERYLIMIT$2, 0);
         return target;
      }
   }

   public boolean isSetRedeliveryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDELIVERYLIMIT$2) != 0;
      }
   }

   public void setRedeliveryLimit(int redeliveryLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REDELIVERYLIMIT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REDELIVERYLIMIT$2);
         }

         target.setIntValue(redeliveryLimit);
      }
   }

   public void xsetRedeliveryLimit(XmlInt redeliveryLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(REDELIVERYLIMIT$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(REDELIVERYLIMIT$2);
         }

         target.set(redeliveryLimit);
      }
   }

   public void unsetRedeliveryLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDELIVERYLIMIT$2, 0);
      }
   }

   public ExpirationPolicyType.Enum getExpirationPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPIRATIONPOLICY$4, 0);
         return target == null ? null : (ExpirationPolicyType.Enum)target.getEnumValue();
      }
   }

   public ExpirationPolicyType xgetExpirationPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExpirationPolicyType target = null;
         target = (ExpirationPolicyType)this.get_store().find_element_user(EXPIRATIONPOLICY$4, 0);
         return target;
      }
   }

   public boolean isSetExpirationPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXPIRATIONPOLICY$4) != 0;
      }
   }

   public void setExpirationPolicy(ExpirationPolicyType.Enum expirationPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPIRATIONPOLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXPIRATIONPOLICY$4);
         }

         target.setEnumValue(expirationPolicy);
      }
   }

   public void xsetExpirationPolicy(ExpirationPolicyType expirationPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExpirationPolicyType target = null;
         target = (ExpirationPolicyType)this.get_store().find_element_user(EXPIRATIONPOLICY$4, 0);
         if (target == null) {
            target = (ExpirationPolicyType)this.get_store().add_element_user(EXPIRATIONPOLICY$4);
         }

         target.set(expirationPolicy);
      }
   }

   public void unsetExpirationPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXPIRATIONPOLICY$4, 0);
      }
   }

   public String getExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         return target;
      }
   }

   public boolean isNilExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXPIRATIONLOGGINGPOLICY$6) != 0;
      }
   }

   public void setExpirationLoggingPolicy(String expirationLoggingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXPIRATIONLOGGINGPOLICY$6);
         }

         target.setStringValue(expirationLoggingPolicy);
      }
   }

   public void xsetExpirationLoggingPolicy(XmlString expirationLoggingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EXPIRATIONLOGGINGPOLICY$6);
         }

         target.set(expirationLoggingPolicy);
      }
   }

   public void setNilExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EXPIRATIONLOGGINGPOLICY$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EXPIRATIONLOGGINGPOLICY$6);
         }

         target.setNil();
      }
   }

   public void unsetExpirationLoggingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXPIRATIONLOGGINGPOLICY$6, 0);
      }
   }
}
