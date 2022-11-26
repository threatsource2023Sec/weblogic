package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.MessageLoggingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.SafDestinationType;
import com.oracle.xmlns.weblogic.weblogicJms.UnitOfOrderRoutingType;
import javax.xml.namespace.QName;

public class SafDestinationTypeImpl extends NamedEntityTypeImpl implements SafDestinationType {
   private static final long serialVersionUID = 1L;
   private static final QName REMOTEJNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "remote-jndi-name");
   private static final QName LOCALJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "local-jndi-name");
   private static final QName PERSISTENTQOS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "persistent-qos");
   private static final QName NONPERSISTENTQOS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "non-persistent-qos");
   private static final QName SAFERRORHANDLING$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-error-handling");
   private static final QName TIMETOLIVEDEFAULT$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "time-to-live-default");
   private static final QName USESAFTIMETOLIVEDEFAULT$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "use-saf-time-to-live-default");
   private static final QName UNITOFORDERROUTING$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-order-routing");
   private static final QName MESSAGELOGGINGPARAMS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-params");

   public SafDestinationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRemoteJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEJNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRemoteJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEJNDINAME$0, 0);
         return target;
      }
   }

   public void setRemoteJndiName(String remoteJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEJNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REMOTEJNDINAME$0);
         }

         target.setStringValue(remoteJndiName);
      }
   }

   public void xsetRemoteJndiName(XmlString remoteJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEJNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REMOTEJNDINAME$0);
         }

         target.set(remoteJndiName);
      }
   }

   public String getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target;
      }
   }

   public boolean isNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJNDINAME$2) != 0;
      }
   }

   public void setLocalJndiName(String localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setStringValue(localJndiName);
      }
   }

   public void xsetLocalJndiName(XmlString localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.set(localJndiName);
      }
   }

   public void setNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setNil();
      }
   }

   public void unsetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJNDINAME$2, 0);
      }
   }

   public SafDestinationType.PersistentQos.Enum getPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTQOS$4, 0);
         return target == null ? null : (SafDestinationType.PersistentQos.Enum)target.getEnumValue();
      }
   }

   public SafDestinationType.PersistentQos xgetPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafDestinationType.PersistentQos target = null;
         target = (SafDestinationType.PersistentQos)this.get_store().find_element_user(PERSISTENTQOS$4, 0);
         return target;
      }
   }

   public boolean isSetPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTQOS$4) != 0;
      }
   }

   public void setPersistentQos(SafDestinationType.PersistentQos.Enum persistentQos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PERSISTENTQOS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PERSISTENTQOS$4);
         }

         target.setEnumValue(persistentQos);
      }
   }

   public void xsetPersistentQos(SafDestinationType.PersistentQos persistentQos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafDestinationType.PersistentQos target = null;
         target = (SafDestinationType.PersistentQos)this.get_store().find_element_user(PERSISTENTQOS$4, 0);
         if (target == null) {
            target = (SafDestinationType.PersistentQos)this.get_store().add_element_user(PERSISTENTQOS$4);
         }

         target.set(persistentQos);
      }
   }

   public void unsetPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTQOS$4, 0);
      }
   }

   public SafDestinationType.NonPersistentQos.Enum getNonPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONPERSISTENTQOS$6, 0);
         return target == null ? null : (SafDestinationType.NonPersistentQos.Enum)target.getEnumValue();
      }
   }

   public SafDestinationType.NonPersistentQos xgetNonPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafDestinationType.NonPersistentQos target = null;
         target = (SafDestinationType.NonPersistentQos)this.get_store().find_element_user(NONPERSISTENTQOS$6, 0);
         return target;
      }
   }

   public boolean isSetNonPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONPERSISTENTQOS$6) != 0;
      }
   }

   public void setNonPersistentQos(SafDestinationType.NonPersistentQos.Enum nonPersistentQos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONPERSISTENTQOS$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONPERSISTENTQOS$6);
         }

         target.setEnumValue(nonPersistentQos);
      }
   }

   public void xsetNonPersistentQos(SafDestinationType.NonPersistentQos nonPersistentQos) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafDestinationType.NonPersistentQos target = null;
         target = (SafDestinationType.NonPersistentQos)this.get_store().find_element_user(NONPERSISTENTQOS$6, 0);
         if (target == null) {
            target = (SafDestinationType.NonPersistentQos)this.get_store().add_element_user(NONPERSISTENTQOS$6);
         }

         target.set(nonPersistentQos);
      }
   }

   public void unsetNonPersistentQos() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONPERSISTENTQOS$6, 0);
      }
   }

   public String getSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target;
      }
   }

   public boolean isNilSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFERRORHANDLING$8) != 0;
      }
   }

   public void setSafErrorHandling(String safErrorHandling) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.setStringValue(safErrorHandling);
      }
   }

   public void xsetSafErrorHandling(XmlString safErrorHandling) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.set(safErrorHandling);
      }
   }

   public void setNilSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.setNil();
      }
   }

   public void unsetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFERRORHANDLING$8, 0);
      }
   }

   public long getTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         return target;
      }
   }

   public boolean isSetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETOLIVEDEFAULT$10) != 0;
      }
   }

   public void setTimeToLiveDefault(long timeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETOLIVEDEFAULT$10);
         }

         target.setLongValue(timeToLiveDefault);
      }
   }

   public void xsetTimeToLiveDefault(XmlLong timeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TIMETOLIVEDEFAULT$10);
         }

         target.set(timeToLiveDefault);
      }
   }

   public void unsetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETOLIVEDEFAULT$10, 0);
      }
   }

   public boolean getUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         return target;
      }
   }

   public boolean isSetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESAFTIMETOLIVEDEFAULT$12) != 0;
      }
   }

   public void setUseSafTimeToLiveDefault(boolean useSafTimeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESAFTIMETOLIVEDEFAULT$12);
         }

         target.setBooleanValue(useSafTimeToLiveDefault);
      }
   }

   public void xsetUseSafTimeToLiveDefault(XmlBoolean useSafTimeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESAFTIMETOLIVEDEFAULT$12);
         }

         target.set(useSafTimeToLiveDefault);
      }
   }

   public void unsetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESAFTIMETOLIVEDEFAULT$12, 0);
      }
   }

   public UnitOfOrderRoutingType.Enum getUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         return target == null ? null : (UnitOfOrderRoutingType.Enum)target.getEnumValue();
      }
   }

   public UnitOfOrderRoutingType xgetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         return target;
      }
   }

   public boolean isSetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFORDERROUTING$14) != 0;
      }
   }

   public void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFORDERROUTING$14);
         }

         target.setEnumValue(unitOfOrderRouting);
      }
   }

   public void xsetUnitOfOrderRouting(UnitOfOrderRoutingType unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         if (target == null) {
            target = (UnitOfOrderRoutingType)this.get_store().add_element_user(UNITOFORDERROUTING$14);
         }

         target.set(unitOfOrderRouting);
      }
   }

   public void unsetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFORDERROUTING$14, 0);
      }
   }

   public MessageLoggingParamsType getMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().find_element_user(MESSAGELOGGINGPARAMS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGPARAMS$16) != 0;
      }
   }

   public void setMessageLoggingParams(MessageLoggingParamsType messageLoggingParams) {
      this.generatedSetterHelperImpl(messageLoggingParams, MESSAGELOGGINGPARAMS$16, 0, (short)1);
   }

   public MessageLoggingParamsType addNewMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().add_element_user(MESSAGELOGGINGPARAMS$16);
         return target;
      }
   }

   public void unsetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGPARAMS$16, 0);
      }
   }

   public static class NonPersistentQosImpl extends JavaStringEnumerationHolderEx implements SafDestinationType.NonPersistentQos {
      private static final long serialVersionUID = 1L;

      public NonPersistentQosImpl(SchemaType sType) {
         super(sType, false);
      }

      protected NonPersistentQosImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class PersistentQosImpl extends JavaStringEnumerationHolderEx implements SafDestinationType.PersistentQos {
      private static final long serialVersionUID = 1L;

      public PersistentQosImpl(SchemaType sType) {
         super(sType, false);
      }

      protected PersistentQosImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
