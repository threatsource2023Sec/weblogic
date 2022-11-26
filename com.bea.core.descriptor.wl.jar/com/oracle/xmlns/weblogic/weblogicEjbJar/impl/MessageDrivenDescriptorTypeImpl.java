package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ConnectionFactoryJndiNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.DestinationJndiNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.DistributedDestinationConnectionType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.InitialContextFactoryType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.JmsClientIdType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.MessageDrivenDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PoolType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ProviderUrlType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.ResourceAdapterJndiNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SecurityPluginType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TimerDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import javax.xml.namespace.QName;

public class MessageDrivenDescriptorTypeImpl extends XmlComplexContentImpl implements MessageDrivenDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName POOL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "pool");
   private static final QName TIMERDESCRIPTOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "timer-descriptor");
   private static final QName RESOURCEADAPTERJNDINAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "resource-adapter-jndi-name");
   private static final QName DESTINATIONJNDINAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "destination-jndi-name");
   private static final QName INITIALCONTEXTFACTORY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "initial-context-factory");
   private static final QName PROVIDERURL$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "provider-url");
   private static final QName CONNECTIONFACTORYJNDINAME$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "connection-factory-jndi-name");
   private static final QName DESTINATIONRESOURCELINK$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "destination-resource-link");
   private static final QName CONNECTIONFACTORYRESOURCELINK$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "connection-factory-resource-link");
   private static final QName JMSPOLLINGINTERVALSECONDS$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "jms-polling-interval-seconds");
   private static final QName JMSCLIENTID$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "jms-client-id");
   private static final QName GENERATEUNIQUEJMSCLIENTID$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "generate-unique-jms-client-id");
   private static final QName DURABLESUBSCRIPTIONDELETION$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "durable-subscription-deletion");
   private static final QName MAXMESSAGESINTRANSACTION$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-messages-in-transaction");
   private static final QName DISTRIBUTEDDESTINATIONCONNECTION$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "distributed-destination-connection");
   private static final QName USE81STYLEPOLLING$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "use81-style-polling");
   private static final QName INITSUSPENDSECONDS$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "init-suspend-seconds");
   private static final QName MAXSUSPENDSECONDS$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "max-suspend-seconds");
   private static final QName SECURITYPLUGIN$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "security-plugin");
   private static final QName ID$38 = new QName("", "id");

   public MessageDrivenDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PoolType getPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().find_element_user(POOL$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOL$0) != 0;
      }
   }

   public void setPool(PoolType pool) {
      this.generatedSetterHelperImpl(pool, POOL$0, 0, (short)1);
   }

   public PoolType addNewPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolType target = null;
         target = (PoolType)this.get_store().add_element_user(POOL$0);
         return target;
      }
   }

   public void unsetPool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOL$0, 0);
      }
   }

   public TimerDescriptorType getTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().find_element_user(TIMERDESCRIPTOR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMERDESCRIPTOR$2) != 0;
      }
   }

   public void setTimerDescriptor(TimerDescriptorType timerDescriptor) {
      this.generatedSetterHelperImpl(timerDescriptor, TIMERDESCRIPTOR$2, 0, (short)1);
   }

   public TimerDescriptorType addNewTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimerDescriptorType target = null;
         target = (TimerDescriptorType)this.get_store().add_element_user(TIMERDESCRIPTOR$2);
         return target;
      }
   }

   public void unsetTimerDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMERDESCRIPTOR$2, 0);
      }
   }

   public ResourceAdapterJndiNameType getResourceAdapterJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterJndiNameType target = null;
         target = (ResourceAdapterJndiNameType)this.get_store().find_element_user(RESOURCEADAPTERJNDINAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceAdapterJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEADAPTERJNDINAME$4) != 0;
      }
   }

   public void setResourceAdapterJndiName(ResourceAdapterJndiNameType resourceAdapterJndiName) {
      this.generatedSetterHelperImpl(resourceAdapterJndiName, RESOURCEADAPTERJNDINAME$4, 0, (short)1);
   }

   public ResourceAdapterJndiNameType addNewResourceAdapterJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterJndiNameType target = null;
         target = (ResourceAdapterJndiNameType)this.get_store().add_element_user(RESOURCEADAPTERJNDINAME$4);
         return target;
      }
   }

   public void unsetResourceAdapterJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEADAPTERJNDINAME$4, 0);
      }
   }

   public DestinationJndiNameType getDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationJndiNameType target = null;
         target = (DestinationJndiNameType)this.get_store().find_element_user(DESTINATIONJNDINAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONJNDINAME$6) != 0;
      }
   }

   public void setDestinationJndiName(DestinationJndiNameType destinationJndiName) {
      this.generatedSetterHelperImpl(destinationJndiName, DESTINATIONJNDINAME$6, 0, (short)1);
   }

   public DestinationJndiNameType addNewDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationJndiNameType target = null;
         target = (DestinationJndiNameType)this.get_store().add_element_user(DESTINATIONJNDINAME$6);
         return target;
      }
   }

   public void unsetDestinationJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONJNDINAME$6, 0);
      }
   }

   public InitialContextFactoryType getInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitialContextFactoryType target = null;
         target = (InitialContextFactoryType)this.get_store().find_element_user(INITIALCONTEXTFACTORY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALCONTEXTFACTORY$8) != 0;
      }
   }

   public void setInitialContextFactory(InitialContextFactoryType initialContextFactory) {
      this.generatedSetterHelperImpl(initialContextFactory, INITIALCONTEXTFACTORY$8, 0, (short)1);
   }

   public InitialContextFactoryType addNewInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitialContextFactoryType target = null;
         target = (InitialContextFactoryType)this.get_store().add_element_user(INITIALCONTEXTFACTORY$8);
         return target;
      }
   }

   public void unsetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALCONTEXTFACTORY$8, 0);
      }
   }

   public ProviderUrlType getProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProviderUrlType target = null;
         target = (ProviderUrlType)this.get_store().find_element_user(PROVIDERURL$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROVIDERURL$10) != 0;
      }
   }

   public void setProviderUrl(ProviderUrlType providerUrl) {
      this.generatedSetterHelperImpl(providerUrl, PROVIDERURL$10, 0, (short)1);
   }

   public ProviderUrlType addNewProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProviderUrlType target = null;
         target = (ProviderUrlType)this.get_store().add_element_user(PROVIDERURL$10);
         return target;
      }
   }

   public void unsetProviderUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROVIDERURL$10, 0);
      }
   }

   public ConnectionFactoryJndiNameType getConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryJndiNameType target = null;
         target = (ConnectionFactoryJndiNameType)this.get_store().find_element_user(CONNECTIONFACTORYJNDINAME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYJNDINAME$12) != 0;
      }
   }

   public void setConnectionFactoryJndiName(ConnectionFactoryJndiNameType connectionFactoryJndiName) {
      this.generatedSetterHelperImpl(connectionFactoryJndiName, CONNECTIONFACTORYJNDINAME$12, 0, (short)1);
   }

   public ConnectionFactoryJndiNameType addNewConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionFactoryJndiNameType target = null;
         target = (ConnectionFactoryJndiNameType)this.get_store().add_element_user(CONNECTIONFACTORYJNDINAME$12);
         return target;
      }
   }

   public void unsetConnectionFactoryJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYJNDINAME$12, 0);
      }
   }

   public String getDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONRESOURCELINK$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONRESOURCELINK$14) != 0;
      }
   }

   public void setDestinationResourceLink(String destinationResourceLink) {
      this.generatedSetterHelperImpl(destinationResourceLink, DESTINATIONRESOURCELINK$14, 0, (short)1);
   }

   public String addNewDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DESTINATIONRESOURCELINK$14);
         return target;
      }
   }

   public void unsetDestinationResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONRESOURCELINK$14, 0);
      }
   }

   public String getConnectionFactoryResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CONNECTIONFACTORYRESOURCELINK$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionFactoryResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYRESOURCELINK$16) != 0;
      }
   }

   public void setConnectionFactoryResourceLink(String connectionFactoryResourceLink) {
      this.generatedSetterHelperImpl(connectionFactoryResourceLink, CONNECTIONFACTORYRESOURCELINK$16, 0, (short)1);
   }

   public String addNewConnectionFactoryResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CONNECTIONFACTORYRESOURCELINK$16);
         return target;
      }
   }

   public void unsetConnectionFactoryResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYRESOURCELINK$16, 0);
      }
   }

   public XsdNonNegativeIntegerType getJmsPollingIntervalSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(JMSPOLLINGINTERVALSECONDS$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJmsPollingIntervalSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSPOLLINGINTERVALSECONDS$18) != 0;
      }
   }

   public void setJmsPollingIntervalSeconds(XsdNonNegativeIntegerType jmsPollingIntervalSeconds) {
      this.generatedSetterHelperImpl(jmsPollingIntervalSeconds, JMSPOLLINGINTERVALSECONDS$18, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewJmsPollingIntervalSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(JMSPOLLINGINTERVALSECONDS$18);
         return target;
      }
   }

   public void unsetJmsPollingIntervalSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSPOLLINGINTERVALSECONDS$18, 0);
      }
   }

   public JmsClientIdType getJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsClientIdType target = null;
         target = (JmsClientIdType)this.get_store().find_element_user(JMSCLIENTID$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSCLIENTID$20) != 0;
      }
   }

   public void setJmsClientId(JmsClientIdType jmsClientId) {
      this.generatedSetterHelperImpl(jmsClientId, JMSCLIENTID$20, 0, (short)1);
   }

   public JmsClientIdType addNewJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsClientIdType target = null;
         target = (JmsClientIdType)this.get_store().add_element_user(JMSCLIENTID$20);
         return target;
      }
   }

   public void unsetJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCLIENTID$20, 0);
      }
   }

   public TrueFalseType getGenerateUniqueJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(GENERATEUNIQUEJMSCLIENTID$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGenerateUniqueJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GENERATEUNIQUEJMSCLIENTID$22) != 0;
      }
   }

   public void setGenerateUniqueJmsClientId(TrueFalseType generateUniqueJmsClientId) {
      this.generatedSetterHelperImpl(generateUniqueJmsClientId, GENERATEUNIQUEJMSCLIENTID$22, 0, (short)1);
   }

   public TrueFalseType addNewGenerateUniqueJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(GENERATEUNIQUEJMSCLIENTID$22);
         return target;
      }
   }

   public void unsetGenerateUniqueJmsClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GENERATEUNIQUEJMSCLIENTID$22, 0);
      }
   }

   public TrueFalseType getDurableSubscriptionDeletion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DURABLESUBSCRIPTIONDELETION$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDurableSubscriptionDeletion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DURABLESUBSCRIPTIONDELETION$24) != 0;
      }
   }

   public void setDurableSubscriptionDeletion(TrueFalseType durableSubscriptionDeletion) {
      this.generatedSetterHelperImpl(durableSubscriptionDeletion, DURABLESUBSCRIPTIONDELETION$24, 0, (short)1);
   }

   public TrueFalseType addNewDurableSubscriptionDeletion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DURABLESUBSCRIPTIONDELETION$24);
         return target;
      }
   }

   public void unsetDurableSubscriptionDeletion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DURABLESUBSCRIPTIONDELETION$24, 0);
      }
   }

   public XsdNonNegativeIntegerType getMaxMessagesInTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXMESSAGESINTRANSACTION$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxMessagesInTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXMESSAGESINTRANSACTION$26) != 0;
      }
   }

   public void setMaxMessagesInTransaction(XsdNonNegativeIntegerType maxMessagesInTransaction) {
      this.generatedSetterHelperImpl(maxMessagesInTransaction, MAXMESSAGESINTRANSACTION$26, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxMessagesInTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXMESSAGESINTRANSACTION$26);
         return target;
      }
   }

   public void unsetMaxMessagesInTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXMESSAGESINTRANSACTION$26, 0);
      }
   }

   public DistributedDestinationConnectionType getDistributedDestinationConnection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationConnectionType target = null;
         target = (DistributedDestinationConnectionType)this.get_store().find_element_user(DISTRIBUTEDDESTINATIONCONNECTION$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDistributedDestinationConnection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTEDDESTINATIONCONNECTION$28) != 0;
      }
   }

   public void setDistributedDestinationConnection(DistributedDestinationConnectionType distributedDestinationConnection) {
      this.generatedSetterHelperImpl(distributedDestinationConnection, DISTRIBUTEDDESTINATIONCONNECTION$28, 0, (short)1);
   }

   public DistributedDestinationConnectionType addNewDistributedDestinationConnection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationConnectionType target = null;
         target = (DistributedDestinationConnectionType)this.get_store().add_element_user(DISTRIBUTEDDESTINATIONCONNECTION$28);
         return target;
      }
   }

   public void unsetDistributedDestinationConnection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTEDDESTINATIONCONNECTION$28, 0);
      }
   }

   public TrueFalseType getUse81StylePolling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USE81STYLEPOLLING$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUse81StylePolling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USE81STYLEPOLLING$30) != 0;
      }
   }

   public void setUse81StylePolling(TrueFalseType use81StylePolling) {
      this.generatedSetterHelperImpl(use81StylePolling, USE81STYLEPOLLING$30, 0, (short)1);
   }

   public TrueFalseType addNewUse81StylePolling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USE81STYLEPOLLING$30);
         return target;
      }
   }

   public void unsetUse81StylePolling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USE81STYLEPOLLING$30, 0);
      }
   }

   public XsdNonNegativeIntegerType getInitSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(INITSUSPENDSECONDS$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITSUSPENDSECONDS$32) != 0;
      }
   }

   public void setInitSuspendSeconds(XsdNonNegativeIntegerType initSuspendSeconds) {
      this.generatedSetterHelperImpl(initSuspendSeconds, INITSUSPENDSECONDS$32, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewInitSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(INITSUSPENDSECONDS$32);
         return target;
      }
   }

   public void unsetInitSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITSUSPENDSECONDS$32, 0);
      }
   }

   public XsdNonNegativeIntegerType getMaxSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MAXSUSPENDSECONDS$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSUSPENDSECONDS$34) != 0;
      }
   }

   public void setMaxSuspendSeconds(XsdNonNegativeIntegerType maxSuspendSeconds) {
      this.generatedSetterHelperImpl(maxSuspendSeconds, MAXSUSPENDSECONDS$34, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMaxSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MAXSUSPENDSECONDS$34);
         return target;
      }
   }

   public void unsetMaxSuspendSeconds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSUSPENDSECONDS$34, 0);
      }
   }

   public SecurityPluginType getSecurityPlugin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPluginType target = null;
         target = (SecurityPluginType)this.get_store().find_element_user(SECURITYPLUGIN$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityPlugin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYPLUGIN$36) != 0;
      }
   }

   public void setSecurityPlugin(SecurityPluginType securityPlugin) {
      this.generatedSetterHelperImpl(securityPlugin, SECURITYPLUGIN$36, 0, (short)1);
   }

   public SecurityPluginType addNewSecurityPlugin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPluginType target = null;
         target = (SecurityPluginType)this.get_store().add_element_user(SECURITYPLUGIN$36);
         return target;
      }
   }

   public void unsetSecurityPlugin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPLUGIN$36, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$38);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$38);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$38) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$38);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$38);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$38);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$38);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$38);
      }
   }
}
