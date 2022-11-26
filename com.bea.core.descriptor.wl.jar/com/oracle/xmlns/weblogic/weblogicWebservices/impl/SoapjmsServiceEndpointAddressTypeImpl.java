package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebservices.SoapjmsServiceEndpointAddressType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class SoapjmsServiceEndpointAddressTypeImpl extends XmlComplexContentImpl implements SoapjmsServiceEndpointAddressType {
   private static final long serialVersionUID = 1L;
   private static final QName LOOKUPVARIANT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "lookup-variant");
   private static final QName DESTINATIONNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "destination-name");
   private static final QName DESTINATIONTYPE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "destination-type");
   private static final QName JNDICONNECTIONFACTORYNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jndi-connection-factory-name");
   private static final QName JNDIINITIALCONTEXTFACTORY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jndi-initial-context-factory");
   private static final QName JNDIURL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jndi-url");
   private static final QName JNDICONTEXTPARAMETER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jndi-context-parameter");
   private static final QName TIMETOLIVE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "time-to-live");
   private static final QName PRIORITY$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "priority");
   private static final QName DELIVERYMODE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "delivery-mode");
   private static final QName REPLYTONAME$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "reply-to-name");
   private static final QName TARGETSERVICE$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "target-service");
   private static final QName BINDINGVERSION$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "binding-version");
   private static final QName MESSAGETYPE$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "message-type");
   private static final QName ENABLEHTTPWSDLACCESS$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "enable-http-wsdl-access");
   private static final QName RUNASPRINCIPAL$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "run-as-principal");
   private static final QName RUNASROLE$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "run-as-role");
   private static final QName MDBPERDESTINATION$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "mdb-per-destination");
   private static final QName ACTIVATIONCONFIG$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "activation-config");
   private static final QName JMSMESSAGEHEADER$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jms-message-header");
   private static final QName JMSMESSAGEPROPERTY$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "jms-message-property");

   public SoapjmsServiceEndpointAddressTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOOKUPVARIANT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOOKUPVARIANT$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOOKUPVARIANT$0) != 0;
      }
   }

   public void setLookupVariant(String lookupVariant) {
      this.generatedSetterHelperImpl(lookupVariant, LOOKUPVARIANT$0, 0, (short)1);
   }

   public String addNewLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(LOOKUPVARIANT$0);
         return target;
      }
   }

   public void setNilLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOOKUPVARIANT$0, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(LOOKUPVARIANT$0);
         }

         target.setNil();
      }
   }

   public void unsetLookupVariant() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOOKUPVARIANT$0, 0);
      }
   }

   public String getDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONNAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public void setDestinationName(String destinationName) {
      this.generatedSetterHelperImpl(destinationName, DESTINATIONNAME$2, 0, (short)1);
   }

   public String addNewDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DESTINATIONNAME$2);
         return target;
      }
   }

   public void setNilDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONNAME$2, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(DESTINATIONNAME$2);
         }

         target.setNil();
      }
   }

   public String getDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONTYPE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONTYPE$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONTYPE$4) != 0;
      }
   }

   public void setDestinationType(String destinationType) {
      this.generatedSetterHelperImpl(destinationType, DESTINATIONTYPE$4, 0, (short)1);
   }

   public String addNewDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DESTINATIONTYPE$4);
         return target;
      }
   }

   public void setNilDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONTYPE$4, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(DESTINATIONTYPE$4);
         }

         target.setNil();
      }
   }

   public void unsetDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONTYPE$4, 0);
      }
   }

   public String getJndiConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDICONNECTIONFACTORYNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJndiConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDICONNECTIONFACTORYNAME$6) != 0;
      }
   }

   public void setJndiConnectionFactoryName(String jndiConnectionFactoryName) {
      this.generatedSetterHelperImpl(jndiConnectionFactoryName, JNDICONNECTIONFACTORYNAME$6, 0, (short)1);
   }

   public String addNewJndiConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JNDICONNECTIONFACTORYNAME$6);
         return target;
      }
   }

   public void unsetJndiConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDICONNECTIONFACTORYNAME$6, 0);
      }
   }

   public String getJndiInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDIINITIALCONTEXTFACTORY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJndiInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIINITIALCONTEXTFACTORY$8) != 0;
      }
   }

   public void setJndiInitialContextFactory(String jndiInitialContextFactory) {
      this.generatedSetterHelperImpl(jndiInitialContextFactory, JNDIINITIALCONTEXTFACTORY$8, 0, (short)1);
   }

   public String addNewJndiInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JNDIINITIALCONTEXTFACTORY$8);
         return target;
      }
   }

   public void unsetJndiInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIINITIALCONTEXTFACTORY$8, 0);
      }
   }

   public String getJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDIURL$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDIURL$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIURL$10) != 0;
      }
   }

   public void setJndiUrl(String jndiUrl) {
      this.generatedSetterHelperImpl(jndiUrl, JNDIURL$10, 0, (short)1);
   }

   public String addNewJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JNDIURL$10);
         return target;
      }
   }

   public void setNilJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDIURL$10, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(JNDIURL$10);
         }

         target.setNil();
      }
   }

   public void unsetJndiUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIURL$10, 0);
      }
   }

   public String getJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDICONTEXTPARAMETER$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDICONTEXTPARAMETER$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDICONTEXTPARAMETER$12) != 0;
      }
   }

   public void setJndiContextParameter(String jndiContextParameter) {
      this.generatedSetterHelperImpl(jndiContextParameter, JNDICONTEXTPARAMETER$12, 0, (short)1);
   }

   public String addNewJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JNDICONTEXTPARAMETER$12);
         return target;
      }
   }

   public void setNilJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDICONTEXTPARAMETER$12, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(JNDICONTEXTPARAMETER$12);
         }

         target.setNil();
      }
   }

   public void unsetJndiContextParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDICONTEXTPARAMETER$12, 0);
      }
   }

   public long getTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVE$14, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVE$14, 0);
         return target;
      }
   }

   public boolean isSetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETOLIVE$14) != 0;
      }
   }

   public void setTimeToLive(long timeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVE$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETOLIVE$14);
         }

         target.setLongValue(timeToLive);
      }
   }

   public void xsetTimeToLive(XmlLong timeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVE$14, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TIMETOLIVE$14);
         }

         target.set(timeToLive);
      }
   }

   public void unsetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETOLIVE$14, 0);
      }
   }

   public int getPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIORITY$16, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PRIORITY$16, 0);
         return target;
      }
   }

   public boolean isSetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIORITY$16) != 0;
      }
   }

   public void setPriority(int priority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIORITY$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIORITY$16);
         }

         target.setIntValue(priority);
      }
   }

   public void xsetPriority(XmlInt priority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PRIORITY$16, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(PRIORITY$16);
         }

         target.set(priority);
      }
   }

   public void unsetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIORITY$16, 0);
      }
   }

   public java.lang.String getDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DELIVERYMODE$18, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DELIVERYMODE$18, 0);
         return target;
      }
   }

   public boolean isSetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYMODE$18) != 0;
      }
   }

   public void setDeliveryMode(java.lang.String deliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DELIVERYMODE$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DELIVERYMODE$18);
         }

         target.setStringValue(deliveryMode);
      }
   }

   public void xsetDeliveryMode(XmlString deliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DELIVERYMODE$18, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DELIVERYMODE$18);
         }

         target.set(deliveryMode);
      }
   }

   public void unsetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYMODE$18, 0);
      }
   }

   public String getReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(REPLYTONAME$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(REPLYTONAME$20, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REPLYTONAME$20) != 0;
      }
   }

   public void setReplyToName(String replyToName) {
      this.generatedSetterHelperImpl(replyToName, REPLYTONAME$20, 0, (short)1);
   }

   public String addNewReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(REPLYTONAME$20);
         return target;
      }
   }

   public void setNilReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(REPLYTONAME$20, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(REPLYTONAME$20);
         }

         target.setNil();
      }
   }

   public void unsetReplyToName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REPLYTONAME$20, 0);
      }
   }

   public String getTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TARGETSERVICE$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TARGETSERVICE$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TARGETSERVICE$22) != 0;
      }
   }

   public void setTargetService(String targetService) {
      this.generatedSetterHelperImpl(targetService, TARGETSERVICE$22, 0, (short)1);
   }

   public String addNewTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(TARGETSERVICE$22);
         return target;
      }
   }

   public void setNilTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TARGETSERVICE$22, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(TARGETSERVICE$22);
         }

         target.setNil();
      }
   }

   public void unsetTargetService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TARGETSERVICE$22, 0);
      }
   }

   public String getBindingVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(BINDINGVERSION$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBindingVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BINDINGVERSION$24) != 0;
      }
   }

   public void setBindingVersion(String bindingVersion) {
      this.generatedSetterHelperImpl(bindingVersion, BINDINGVERSION$24, 0, (short)1);
   }

   public String addNewBindingVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(BINDINGVERSION$24);
         return target;
      }
   }

   public void unsetBindingVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BINDINGVERSION$24, 0);
      }
   }

   public String getMessageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MESSAGETYPE$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGETYPE$26) != 0;
      }
   }

   public void setMessageType(String messageType) {
      this.generatedSetterHelperImpl(messageType, MESSAGETYPE$26, 0, (short)1);
   }

   public String addNewMessageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MESSAGETYPE$26);
         return target;
      }
   }

   public void unsetMessageType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGETYPE$26, 0);
      }
   }

   public boolean getEnableHttpWsdlAccess() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEHTTPWSDLACCESS$28, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnableHttpWsdlAccess() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEHTTPWSDLACCESS$28, 0);
         return target;
      }
   }

   public boolean isSetEnableHttpWsdlAccess() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEHTTPWSDLACCESS$28) != 0;
      }
   }

   public void setEnableHttpWsdlAccess(boolean enableHttpWsdlAccess) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEHTTPWSDLACCESS$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLEHTTPWSDLACCESS$28);
         }

         target.setBooleanValue(enableHttpWsdlAccess);
      }
   }

   public void xsetEnableHttpWsdlAccess(XmlBoolean enableHttpWsdlAccess) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEHTTPWSDLACCESS$28, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLEHTTPWSDLACCESS$28);
         }

         target.set(enableHttpWsdlAccess);
      }
   }

   public void unsetEnableHttpWsdlAccess() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEHTTPWSDLACCESS$28, 0);
      }
   }

   public String getRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASPRINCIPAL$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASPRINCIPAL$30, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASPRINCIPAL$30) != 0;
      }
   }

   public void setRunAsPrincipal(String runAsPrincipal) {
      this.generatedSetterHelperImpl(runAsPrincipal, RUNASPRINCIPAL$30, 0, (short)1);
   }

   public String addNewRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RUNASPRINCIPAL$30);
         return target;
      }
   }

   public void setNilRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASPRINCIPAL$30, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(RUNASPRINCIPAL$30);
         }

         target.setNil();
      }
   }

   public void unsetRunAsPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASPRINCIPAL$30, 0);
      }
   }

   public String getRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASROLE$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASROLE$32, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASROLE$32) != 0;
      }
   }

   public void setRunAsRole(String runAsRole) {
      this.generatedSetterHelperImpl(runAsRole, RUNASROLE$32, 0, (short)1);
   }

   public String addNewRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RUNASROLE$32);
         return target;
      }
   }

   public void setNilRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RUNASROLE$32, 0);
         if (target == null) {
            target = (String)this.get_store().add_element_user(RUNASROLE$32);
         }

         target.setNil();
      }
   }

   public void unsetRunAsRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASROLE$32, 0);
      }
   }

   public boolean getMdbPerDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MDBPERDESTINATION$34, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMdbPerDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MDBPERDESTINATION$34, 0);
         return target;
      }
   }

   public boolean isSetMdbPerDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MDBPERDESTINATION$34) != 0;
      }
   }

   public void setMdbPerDestination(boolean mdbPerDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MDBPERDESTINATION$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MDBPERDESTINATION$34);
         }

         target.setBooleanValue(mdbPerDestination);
      }
   }

   public void xsetMdbPerDestination(XmlBoolean mdbPerDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MDBPERDESTINATION$34, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MDBPERDESTINATION$34);
         }

         target.set(mdbPerDestination);
      }
   }

   public void unsetMdbPerDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MDBPERDESTINATION$34, 0);
      }
   }

   public String getActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(ACTIVATIONCONFIG$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTIVATIONCONFIG$36) != 0;
      }
   }

   public void setActivationConfig(String activationConfig) {
      this.generatedSetterHelperImpl(activationConfig, ACTIVATIONCONFIG$36, 0, (short)1);
   }

   public String addNewActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(ACTIVATIONCONFIG$36);
         return target;
      }
   }

   public void unsetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTIVATIONCONFIG$36, 0);
      }
   }

   public String getJmsMessageHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JMSMESSAGEHEADER$38, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJmsMessageHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSMESSAGEHEADER$38) != 0;
      }
   }

   public void setJmsMessageHeader(String jmsMessageHeader) {
      this.generatedSetterHelperImpl(jmsMessageHeader, JMSMESSAGEHEADER$38, 0, (short)1);
   }

   public String addNewJmsMessageHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JMSMESSAGEHEADER$38);
         return target;
      }
   }

   public void unsetJmsMessageHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSMESSAGEHEADER$38, 0);
      }
   }

   public String getJmsMessageProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JMSMESSAGEPROPERTY$40, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJmsMessageProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSMESSAGEPROPERTY$40) != 0;
      }
   }

   public void setJmsMessageProperty(String jmsMessageProperty) {
      this.generatedSetterHelperImpl(jmsMessageProperty, JMSMESSAGEPROPERTY$40, 0, (short)1);
   }

   public String addNewJmsMessageProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JMSMESSAGEPROPERTY$40);
         return target;
      }
   }

   public void unsetJmsMessageProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSMESSAGEPROPERTY$40, 0);
      }
   }
}
