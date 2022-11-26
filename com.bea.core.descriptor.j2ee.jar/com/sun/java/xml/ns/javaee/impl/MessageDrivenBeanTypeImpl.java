package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.ActivationConfigType;
import com.sun.java.xml.ns.javaee.AroundInvokeType;
import com.sun.java.xml.ns.javaee.AroundTimeoutType;
import com.sun.java.xml.ns.javaee.DataSourceType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.DisplayNameType;
import com.sun.java.xml.ns.javaee.EjbClassType;
import com.sun.java.xml.ns.javaee.EjbLocalRefType;
import com.sun.java.xml.ns.javaee.EjbNameType;
import com.sun.java.xml.ns.javaee.EjbRefType;
import com.sun.java.xml.ns.javaee.EnvEntryType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.IconType;
import com.sun.java.xml.ns.javaee.LifecycleCallbackType;
import com.sun.java.xml.ns.javaee.MessageDestinationLinkType;
import com.sun.java.xml.ns.javaee.MessageDestinationRefType;
import com.sun.java.xml.ns.javaee.MessageDestinationTypeType;
import com.sun.java.xml.ns.javaee.MessageDrivenBeanType;
import com.sun.java.xml.ns.javaee.NamedMethodType;
import com.sun.java.xml.ns.javaee.PersistenceContextRefType;
import com.sun.java.xml.ns.javaee.PersistenceUnitRefType;
import com.sun.java.xml.ns.javaee.ResourceEnvRefType;
import com.sun.java.xml.ns.javaee.ResourceRefType;
import com.sun.java.xml.ns.javaee.SecurityIdentityType;
import com.sun.java.xml.ns.javaee.SecurityRoleRefType;
import com.sun.java.xml.ns.javaee.ServiceRefType;
import com.sun.java.xml.ns.javaee.TimerType;
import com.sun.java.xml.ns.javaee.TransactionTypeType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MessageDrivenBeanTypeImpl extends XmlComplexContentImpl implements MessageDrivenBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/javaee", "icon");
   private static final QName EJBNAME$6 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-name");
   private static final QName MAPPEDNAME$8 = new QName("http://java.sun.com/xml/ns/javaee", "mapped-name");
   private static final QName EJBCLASS$10 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-class");
   private static final QName MESSAGINGTYPE$12 = new QName("http://java.sun.com/xml/ns/javaee", "messaging-type");
   private static final QName TIMEOUTMETHOD$14 = new QName("http://java.sun.com/xml/ns/javaee", "timeout-method");
   private static final QName TIMER$16 = new QName("http://java.sun.com/xml/ns/javaee", "timer");
   private static final QName TRANSACTIONTYPE$18 = new QName("http://java.sun.com/xml/ns/javaee", "transaction-type");
   private static final QName MESSAGEDESTINATIONTYPE$20 = new QName("http://java.sun.com/xml/ns/javaee", "message-destination-type");
   private static final QName MESSAGEDESTINATIONLINK$22 = new QName("http://java.sun.com/xml/ns/javaee", "message-destination-link");
   private static final QName ACTIVATIONCONFIG$24 = new QName("http://java.sun.com/xml/ns/javaee", "activation-config");
   private static final QName AROUNDINVOKE$26 = new QName("http://java.sun.com/xml/ns/javaee", "around-invoke");
   private static final QName AROUNDTIMEOUT$28 = new QName("http://java.sun.com/xml/ns/javaee", "around-timeout");
   private static final QName ENVENTRY$30 = new QName("http://java.sun.com/xml/ns/javaee", "env-entry");
   private static final QName EJBREF$32 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-ref");
   private static final QName EJBLOCALREF$34 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-local-ref");
   private static final QName SERVICEREF$36 = new QName("http://java.sun.com/xml/ns/javaee", "service-ref");
   private static final QName RESOURCEREF$38 = new QName("http://java.sun.com/xml/ns/javaee", "resource-ref");
   private static final QName RESOURCEENVREF$40 = new QName("http://java.sun.com/xml/ns/javaee", "resource-env-ref");
   private static final QName MESSAGEDESTINATIONREF$42 = new QName("http://java.sun.com/xml/ns/javaee", "message-destination-ref");
   private static final QName PERSISTENCECONTEXTREF$44 = new QName("http://java.sun.com/xml/ns/javaee", "persistence-context-ref");
   private static final QName PERSISTENCEUNITREF$46 = new QName("http://java.sun.com/xml/ns/javaee", "persistence-unit-ref");
   private static final QName POSTCONSTRUCT$48 = new QName("http://java.sun.com/xml/ns/javaee", "post-construct");
   private static final QName PREDESTROY$50 = new QName("http://java.sun.com/xml/ns/javaee", "pre-destroy");
   private static final QName DATASOURCE$52 = new QName("http://java.sun.com/xml/ns/javaee", "data-source");
   private static final QName SECURITYROLEREF$54 = new QName("http://java.sun.com/xml/ns/javaee", "security-role-ref");
   private static final QName SECURITYIDENTITY$56 = new QName("http://java.sun.com/xml/ns/javaee", "security-identity");
   private static final QName ID$58 = new QName("", "id");

   public MessageDrivenBeanTypeImpl(SchemaType sType) {
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

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$2, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$2);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$2, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$4, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$4);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$4, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, i);
      }
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$6, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$6);
         return target;
      }
   }

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$8) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$8, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$8);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$8, 0);
      }
   }

   public EjbClassType getEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().find_element_user(EJBCLASS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBCLASS$10) != 0;
      }
   }

   public void setEjbClass(EjbClassType ejbClass) {
      this.generatedSetterHelperImpl(ejbClass, EJBCLASS$10, 0, (short)1);
   }

   public EjbClassType addNewEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbClassType target = null;
         target = (EjbClassType)this.get_store().add_element_user(EJBCLASS$10);
         return target;
      }
   }

   public void unsetEjbClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBCLASS$10, 0);
      }
   }

   public FullyQualifiedClassType getMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(MESSAGINGTYPE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGTYPE$12) != 0;
      }
   }

   public void setMessagingType(FullyQualifiedClassType messagingType) {
      this.generatedSetterHelperImpl(messagingType, MESSAGINGTYPE$12, 0, (short)1);
   }

   public FullyQualifiedClassType addNewMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(MESSAGINGTYPE$12);
         return target;
      }
   }

   public void unsetMessagingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGTYPE$12, 0);
      }
   }

   public NamedMethodType getTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(TIMEOUTMETHOD$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMEOUTMETHOD$14) != 0;
      }
   }

   public void setTimeoutMethod(NamedMethodType timeoutMethod) {
      this.generatedSetterHelperImpl(timeoutMethod, TIMEOUTMETHOD$14, 0, (short)1);
   }

   public NamedMethodType addNewTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(TIMEOUTMETHOD$14);
         return target;
      }
   }

   public void unsetTimeoutMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMEOUTMETHOD$14, 0);
      }
   }

   public TimerType[] getTimerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TIMER$16, targetList);
         TimerType[] result = new TimerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TimerType getTimerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().find_element_user(TIMER$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTimerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMER$16);
      }
   }

   public void setTimerArray(TimerType[] timerArray) {
      this.check_orphaned();
      this.arraySetterHelper(timerArray, TIMER$16);
   }

   public void setTimerArray(int i, TimerType timer) {
      this.generatedSetterHelperImpl(timer, TIMER$16, i, (short)2);
   }

   public TimerType insertNewTimer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().insert_element_user(TIMER$16, i);
         return target;
      }
   }

   public TimerType addNewTimer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerType target = null;
         target = (TimerType)this.get_store().add_element_user(TIMER$16);
         return target;
      }
   }

   public void removeTimer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMER$16, i);
      }
   }

   public TransactionTypeType getTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().find_element_user(TRANSACTIONTYPE$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONTYPE$18) != 0;
      }
   }

   public void setTransactionType(TransactionTypeType transactionType) {
      this.generatedSetterHelperImpl(transactionType, TRANSACTIONTYPE$18, 0, (short)1);
   }

   public TransactionTypeType addNewTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionTypeType target = null;
         target = (TransactionTypeType)this.get_store().add_element_user(TRANSACTIONTYPE$18);
         return target;
      }
   }

   public void unsetTransactionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONTYPE$18, 0);
      }
   }

   public MessageDestinationTypeType getMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().find_element_user(MESSAGEDESTINATIONTYPE$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONTYPE$20) != 0;
      }
   }

   public void setMessageDestinationType(MessageDestinationTypeType messageDestinationType) {
      this.generatedSetterHelperImpl(messageDestinationType, MESSAGEDESTINATIONTYPE$20, 0, (short)1);
   }

   public MessageDestinationTypeType addNewMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationTypeType target = null;
         target = (MessageDestinationTypeType)this.get_store().add_element_user(MESSAGEDESTINATIONTYPE$20);
         return target;
      }
   }

   public void unsetMessageDestinationType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONTYPE$20, 0);
      }
   }

   public MessageDestinationLinkType getMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().find_element_user(MESSAGEDESTINATIONLINK$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONLINK$22) != 0;
      }
   }

   public void setMessageDestinationLink(MessageDestinationLinkType messageDestinationLink) {
      this.generatedSetterHelperImpl(messageDestinationLink, MESSAGEDESTINATIONLINK$22, 0, (short)1);
   }

   public MessageDestinationLinkType addNewMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationLinkType target = null;
         target = (MessageDestinationLinkType)this.get_store().add_element_user(MESSAGEDESTINATIONLINK$22);
         return target;
      }
   }

   public void unsetMessageDestinationLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONLINK$22, 0);
      }
   }

   public ActivationConfigType getActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigType target = null;
         target = (ActivationConfigType)this.get_store().find_element_user(ACTIVATIONCONFIG$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACTIVATIONCONFIG$24) != 0;
      }
   }

   public void setActivationConfig(ActivationConfigType activationConfig) {
      this.generatedSetterHelperImpl(activationConfig, ACTIVATIONCONFIG$24, 0, (short)1);
   }

   public ActivationConfigType addNewActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationConfigType target = null;
         target = (ActivationConfigType)this.get_store().add_element_user(ACTIVATIONCONFIG$24);
         return target;
      }
   }

   public void unsetActivationConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACTIVATIONCONFIG$24, 0);
      }
   }

   public AroundInvokeType[] getAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDINVOKE$26, targetList);
         AroundInvokeType[] result = new AroundInvokeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundInvokeType getAroundInvokeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().find_element_user(AROUNDINVOKE$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundInvokeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDINVOKE$26);
      }
   }

   public void setAroundInvokeArray(AroundInvokeType[] aroundInvokeArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundInvokeArray, AROUNDINVOKE$26);
   }

   public void setAroundInvokeArray(int i, AroundInvokeType aroundInvoke) {
      this.generatedSetterHelperImpl(aroundInvoke, AROUNDINVOKE$26, i, (short)2);
   }

   public AroundInvokeType insertNewAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().insert_element_user(AROUNDINVOKE$26, i);
         return target;
      }
   }

   public AroundInvokeType addNewAroundInvoke() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundInvokeType target = null;
         target = (AroundInvokeType)this.get_store().add_element_user(AROUNDINVOKE$26);
         return target;
      }
   }

   public void removeAroundInvoke(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDINVOKE$26, i);
      }
   }

   public AroundTimeoutType[] getAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(AROUNDTIMEOUT$28, targetList);
         AroundTimeoutType[] result = new AroundTimeoutType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AroundTimeoutType getAroundTimeoutArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().find_element_user(AROUNDTIMEOUT$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAroundTimeoutArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AROUNDTIMEOUT$28);
      }
   }

   public void setAroundTimeoutArray(AroundTimeoutType[] aroundTimeoutArray) {
      this.check_orphaned();
      this.arraySetterHelper(aroundTimeoutArray, AROUNDTIMEOUT$28);
   }

   public void setAroundTimeoutArray(int i, AroundTimeoutType aroundTimeout) {
      this.generatedSetterHelperImpl(aroundTimeout, AROUNDTIMEOUT$28, i, (short)2);
   }

   public AroundTimeoutType insertNewAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().insert_element_user(AROUNDTIMEOUT$28, i);
         return target;
      }
   }

   public AroundTimeoutType addNewAroundTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AroundTimeoutType target = null;
         target = (AroundTimeoutType)this.get_store().add_element_user(AROUNDTIMEOUT$28);
         return target;
      }
   }

   public void removeAroundTimeout(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AROUNDTIMEOUT$28, i);
      }
   }

   public EnvEntryType[] getEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENVENTRY$30, targetList);
         EnvEntryType[] result = new EnvEntryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EnvEntryType getEnvEntryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().find_element_user(ENVENTRY$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEnvEntryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENVENTRY$30);
      }
   }

   public void setEnvEntryArray(EnvEntryType[] envEntryArray) {
      this.check_orphaned();
      this.arraySetterHelper(envEntryArray, ENVENTRY$30);
   }

   public void setEnvEntryArray(int i, EnvEntryType envEntry) {
      this.generatedSetterHelperImpl(envEntry, ENVENTRY$30, i, (short)2);
   }

   public EnvEntryType insertNewEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().insert_element_user(ENVENTRY$30, i);
         return target;
      }
   }

   public EnvEntryType addNewEnvEntry() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnvEntryType target = null;
         target = (EnvEntryType)this.get_store().add_element_user(ENVENTRY$30);
         return target;
      }
   }

   public void removeEnvEntry(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENVENTRY$30, i);
      }
   }

   public EjbRefType[] getEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBREF$32, targetList);
         EjbRefType[] result = new EjbRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbRefType getEjbRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().find_element_user(EJBREF$32, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBREF$32);
      }
   }

   public void setEjbRefArray(EjbRefType[] ejbRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbRefArray, EJBREF$32);
   }

   public void setEjbRefArray(int i, EjbRefType ejbRef) {
      this.generatedSetterHelperImpl(ejbRef, EJBREF$32, i, (short)2);
   }

   public EjbRefType insertNewEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().insert_element_user(EJBREF$32, i);
         return target;
      }
   }

   public EjbRefType addNewEjbRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefType target = null;
         target = (EjbRefType)this.get_store().add_element_user(EJBREF$32);
         return target;
      }
   }

   public void removeEjbRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBREF$32, i);
      }
   }

   public EjbLocalRefType[] getEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(EJBLOCALREF$34, targetList);
         EjbLocalRefType[] result = new EjbLocalRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EjbLocalRefType getEjbLocalRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().find_element_user(EJBLOCALREF$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEjbLocalRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLOCALREF$34);
      }
   }

   public void setEjbLocalRefArray(EjbLocalRefType[] ejbLocalRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(ejbLocalRefArray, EJBLOCALREF$34);
   }

   public void setEjbLocalRefArray(int i, EjbLocalRefType ejbLocalRef) {
      this.generatedSetterHelperImpl(ejbLocalRef, EJBLOCALREF$34, i, (short)2);
   }

   public EjbLocalRefType insertNewEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().insert_element_user(EJBLOCALREF$34, i);
         return target;
      }
   }

   public EjbLocalRefType addNewEjbLocalRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLocalRefType target = null;
         target = (EjbLocalRefType)this.get_store().add_element_user(EJBLOCALREF$34);
         return target;
      }
   }

   public void removeEjbLocalRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLOCALREF$34, i);
      }
   }

   public ServiceRefType[] getServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SERVICEREF$36, targetList);
         ServiceRefType[] result = new ServiceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefType getServiceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$36, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfServiceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREF$36);
      }
   }

   public void setServiceRefArray(ServiceRefType[] serviceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(serviceRefArray, SERVICEREF$36);
   }

   public void setServiceRefArray(int i, ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$36, i, (short)2);
   }

   public ServiceRefType insertNewServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().insert_element_user(SERVICEREF$36, i);
         return target;
      }
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$36);
         return target;
      }
   }

   public void removeServiceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREF$36, i);
      }
   }

   public ResourceRefType[] getResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEREF$38, targetList);
         ResourceRefType[] result = new ResourceRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceRefType getResourceRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().find_element_user(RESOURCEREF$38, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEREF$38);
      }
   }

   public void setResourceRefArray(ResourceRefType[] resourceRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceRefArray, RESOURCEREF$38);
   }

   public void setResourceRefArray(int i, ResourceRefType resourceRef) {
      this.generatedSetterHelperImpl(resourceRef, RESOURCEREF$38, i, (short)2);
   }

   public ResourceRefType insertNewResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().insert_element_user(RESOURCEREF$38, i);
         return target;
      }
   }

   public ResourceRefType addNewResourceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceRefType target = null;
         target = (ResourceRefType)this.get_store().add_element_user(RESOURCEREF$38);
         return target;
      }
   }

   public void removeResourceRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEREF$38, i);
      }
   }

   public ResourceEnvRefType[] getResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(RESOURCEENVREF$40, targetList);
         ResourceEnvRefType[] result = new ResourceEnvRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ResourceEnvRefType getResourceEnvRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().find_element_user(RESOURCEENVREF$40, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfResourceEnvRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEENVREF$40);
      }
   }

   public void setResourceEnvRefArray(ResourceEnvRefType[] resourceEnvRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(resourceEnvRefArray, RESOURCEENVREF$40);
   }

   public void setResourceEnvRefArray(int i, ResourceEnvRefType resourceEnvRef) {
      this.generatedSetterHelperImpl(resourceEnvRef, RESOURCEENVREF$40, i, (short)2);
   }

   public ResourceEnvRefType insertNewResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().insert_element_user(RESOURCEENVREF$40, i);
         return target;
      }
   }

   public ResourceEnvRefType addNewResourceEnvRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceEnvRefType target = null;
         target = (ResourceEnvRefType)this.get_store().add_element_user(RESOURCEENVREF$40);
         return target;
      }
   }

   public void removeResourceEnvRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEENVREF$40, i);
      }
   }

   public MessageDestinationRefType[] getMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MESSAGEDESTINATIONREF$42, targetList);
         MessageDestinationRefType[] result = new MessageDestinationRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public MessageDestinationRefType getMessageDestinationRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().find_element_user(MESSAGEDESTINATIONREF$42, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMessageDestinationRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEDESTINATIONREF$42);
      }
   }

   public void setMessageDestinationRefArray(MessageDestinationRefType[] messageDestinationRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(messageDestinationRefArray, MESSAGEDESTINATIONREF$42);
   }

   public void setMessageDestinationRefArray(int i, MessageDestinationRefType messageDestinationRef) {
      this.generatedSetterHelperImpl(messageDestinationRef, MESSAGEDESTINATIONREF$42, i, (short)2);
   }

   public MessageDestinationRefType insertNewMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().insert_element_user(MESSAGEDESTINATIONREF$42, i);
         return target;
      }
   }

   public MessageDestinationRefType addNewMessageDestinationRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageDestinationRefType target = null;
         target = (MessageDestinationRefType)this.get_store().add_element_user(MESSAGEDESTINATIONREF$42);
         return target;
      }
   }

   public void removeMessageDestinationRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEDESTINATIONREF$42, i);
      }
   }

   public PersistenceContextRefType[] getPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCECONTEXTREF$44, targetList);
         PersistenceContextRefType[] result = new PersistenceContextRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceContextRefType getPersistenceContextRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().find_element_user(PERSISTENCECONTEXTREF$44, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceContextRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONTEXTREF$44);
      }
   }

   public void setPersistenceContextRefArray(PersistenceContextRefType[] persistenceContextRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceContextRefArray, PERSISTENCECONTEXTREF$44);
   }

   public void setPersistenceContextRefArray(int i, PersistenceContextRefType persistenceContextRef) {
      this.generatedSetterHelperImpl(persistenceContextRef, PERSISTENCECONTEXTREF$44, i, (short)2);
   }

   public PersistenceContextRefType insertNewPersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().insert_element_user(PERSISTENCECONTEXTREF$44, i);
         return target;
      }
   }

   public PersistenceContextRefType addNewPersistenceContextRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceContextRefType target = null;
         target = (PersistenceContextRefType)this.get_store().add_element_user(PERSISTENCECONTEXTREF$44);
         return target;
      }
   }

   public void removePersistenceContextRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONTEXTREF$44, i);
      }
   }

   public PersistenceUnitRefType[] getPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PERSISTENCEUNITREF$46, targetList);
         PersistenceUnitRefType[] result = new PersistenceUnitRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PersistenceUnitRefType getPersistenceUnitRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().find_element_user(PERSISTENCEUNITREF$46, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPersistenceUnitRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEUNITREF$46);
      }
   }

   public void setPersistenceUnitRefArray(PersistenceUnitRefType[] persistenceUnitRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(persistenceUnitRefArray, PERSISTENCEUNITREF$46);
   }

   public void setPersistenceUnitRefArray(int i, PersistenceUnitRefType persistenceUnitRef) {
      this.generatedSetterHelperImpl(persistenceUnitRef, PERSISTENCEUNITREF$46, i, (short)2);
   }

   public PersistenceUnitRefType insertNewPersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().insert_element_user(PERSISTENCEUNITREF$46, i);
         return target;
      }
   }

   public PersistenceUnitRefType addNewPersistenceUnitRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitRefType target = null;
         target = (PersistenceUnitRefType)this.get_store().add_element_user(PERSISTENCEUNITREF$46);
         return target;
      }
   }

   public void removePersistenceUnitRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEUNITREF$46, i);
      }
   }

   public LifecycleCallbackType[] getPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(POSTCONSTRUCT$48, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPostConstructArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(POSTCONSTRUCT$48, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPostConstructArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTCONSTRUCT$48);
      }
   }

   public void setPostConstructArray(LifecycleCallbackType[] postConstructArray) {
      this.check_orphaned();
      this.arraySetterHelper(postConstructArray, POSTCONSTRUCT$48);
   }

   public void setPostConstructArray(int i, LifecycleCallbackType postConstruct) {
      this.generatedSetterHelperImpl(postConstruct, POSTCONSTRUCT$48, i, (short)2);
   }

   public LifecycleCallbackType insertNewPostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(POSTCONSTRUCT$48, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPostConstruct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(POSTCONSTRUCT$48);
         return target;
      }
   }

   public void removePostConstruct(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTCONSTRUCT$48, i);
      }
   }

   public LifecycleCallbackType[] getPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PREDESTROY$50, targetList);
         LifecycleCallbackType[] result = new LifecycleCallbackType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LifecycleCallbackType getPreDestroyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().find_element_user(PREDESTROY$50, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPreDestroyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREDESTROY$50);
      }
   }

   public void setPreDestroyArray(LifecycleCallbackType[] preDestroyArray) {
      this.check_orphaned();
      this.arraySetterHelper(preDestroyArray, PREDESTROY$50);
   }

   public void setPreDestroyArray(int i, LifecycleCallbackType preDestroy) {
      this.generatedSetterHelperImpl(preDestroy, PREDESTROY$50, i, (short)2);
   }

   public LifecycleCallbackType insertNewPreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().insert_element_user(PREDESTROY$50, i);
         return target;
      }
   }

   public LifecycleCallbackType addNewPreDestroy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LifecycleCallbackType target = null;
         target = (LifecycleCallbackType)this.get_store().add_element_user(PREDESTROY$50);
         return target;
      }
   }

   public void removePreDestroy(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREDESTROY$50, i);
      }
   }

   public DataSourceType[] getDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DATASOURCE$52, targetList);
         DataSourceType[] result = new DataSourceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DataSourceType getDataSourceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().find_element_user(DATASOURCE$52, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDataSourceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATASOURCE$52);
      }
   }

   public void setDataSourceArray(DataSourceType[] dataSourceArray) {
      this.check_orphaned();
      this.arraySetterHelper(dataSourceArray, DATASOURCE$52);
   }

   public void setDataSourceArray(int i, DataSourceType dataSource) {
      this.generatedSetterHelperImpl(dataSource, DATASOURCE$52, i, (short)2);
   }

   public DataSourceType insertNewDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().insert_element_user(DATASOURCE$52, i);
         return target;
      }
   }

   public DataSourceType addNewDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceType target = null;
         target = (DataSourceType)this.get_store().add_element_user(DATASOURCE$52);
         return target;
      }
   }

   public void removeDataSource(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCE$52, i);
      }
   }

   public SecurityRoleRefType[] getSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEREF$54, targetList);
         SecurityRoleRefType[] result = new SecurityRoleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleRefType getSecurityRoleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().find_element_user(SECURITYROLEREF$54, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLEREF$54);
      }
   }

   public void setSecurityRoleRefArray(SecurityRoleRefType[] securityRoleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleRefArray, SECURITYROLEREF$54);
   }

   public void setSecurityRoleRefArray(int i, SecurityRoleRefType securityRoleRef) {
      this.generatedSetterHelperImpl(securityRoleRef, SECURITYROLEREF$54, i, (short)2);
   }

   public SecurityRoleRefType insertNewSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().insert_element_user(SECURITYROLEREF$54, i);
         return target;
      }
   }

   public SecurityRoleRefType addNewSecurityRoleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().add_element_user(SECURITYROLEREF$54);
         return target;
      }
   }

   public void removeSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEREF$54, i);
      }
   }

   public SecurityIdentityType getSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().find_element_user(SECURITYIDENTITY$56, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYIDENTITY$56) != 0;
      }
   }

   public void setSecurityIdentity(SecurityIdentityType securityIdentity) {
      this.generatedSetterHelperImpl(securityIdentity, SECURITYIDENTITY$56, 0, (short)1);
   }

   public SecurityIdentityType addNewSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityIdentityType target = null;
         target = (SecurityIdentityType)this.get_store().add_element_user(SECURITYIDENTITY$56);
         return target;
      }
   }

   public void unsetSecurityIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYIDENTITY$56, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$58);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$58);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$58) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$58);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$58);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$58);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$58);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$58);
      }
   }
}
