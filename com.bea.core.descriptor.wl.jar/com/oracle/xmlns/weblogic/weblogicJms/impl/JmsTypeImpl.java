package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DestinationKeyType;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedQueueType;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedTopicType;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignServerType;
import com.oracle.xmlns.weblogic.weblogicJms.JmsConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicJms.JmsType;
import com.oracle.xmlns.weblogic.weblogicJms.QueueType;
import com.oracle.xmlns.weblogic.weblogicJms.QuotaType;
import com.oracle.xmlns.weblogic.weblogicJms.SafErrorHandlingType;
import com.oracle.xmlns.weblogic.weblogicJms.SafImportedDestinationsType;
import com.oracle.xmlns.weblogic.weblogicJms.SafRemoteContextType;
import com.oracle.xmlns.weblogic.weblogicJms.TemplateType;
import com.oracle.xmlns.weblogic.weblogicJms.TopicType;
import com.oracle.xmlns.weblogic.weblogicJms.UniformDistributedQueueType;
import com.oracle.xmlns.weblogic.weblogicJms.UniformDistributedTopicType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JmsTypeImpl extends XmlComplexContentImpl implements JmsType {
   private static final long serialVersionUID = 1L;
   private static final QName VERSION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "version");
   private static final QName NOTES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "notes");
   private static final QName QUOTA$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "quota");
   private static final QName TEMPLATE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "template");
   private static final QName DESTINATIONKEY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "destination-key");
   private static final QName CONNECTIONFACTORY$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "connection-factory");
   private static final QName FOREIGNSERVER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "foreign-server");
   private static final QName QUEUE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "queue");
   private static final QName TOPIC$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "topic");
   private static final QName DISTRIBUTEDQUEUE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "distributed-queue");
   private static final QName DISTRIBUTEDTOPIC$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "distributed-topic");
   private static final QName UNIFORMDISTRIBUTEDQUEUE$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "uniform-distributed-queue");
   private static final QName UNIFORMDISTRIBUTEDTOPIC$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "uniform-distributed-topic");
   private static final QName SAFIMPORTEDDESTINATIONS$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-imported-destinations");
   private static final QName SAFREMOTECONTEXT$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-remote-context");
   private static final QName SAFERRORHANDLING$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-error-handling");

   public JmsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(VERSION$0, 0);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSION$0) != 0;
      }
   }

   public void setVersion(int version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSION$0);
         }

         target.setIntValue(version);
      }
   }

   public void xsetVersion(XmlInt version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(VERSION$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(VERSION$0);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSION$0, 0);
      }
   }

   public String getNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$2, 0);
         return target;
      }
   }

   public boolean isNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NOTES$2) != 0;
      }
   }

   public void setNotes(String notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NOTES$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NOTES$2);
         }

         target.setStringValue(notes);
      }
   }

   public void xsetNotes(XmlString notes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$2);
         }

         target.set(notes);
      }
   }

   public void setNilNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NOTES$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NOTES$2);
         }

         target.setNil();
      }
   }

   public void unsetNotes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NOTES$2, 0);
      }
   }

   public QuotaType[] getQuotaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(QUOTA$4, targetList);
         QuotaType[] result = new QuotaType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QuotaType getQuotaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QuotaType target = null;
         target = (QuotaType)this.get_store().find_element_user(QUOTA$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfQuotaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUOTA$4);
      }
   }

   public void setQuotaArray(QuotaType[] quotaArray) {
      this.check_orphaned();
      this.arraySetterHelper(quotaArray, QUOTA$4);
   }

   public void setQuotaArray(int i, QuotaType quota) {
      this.generatedSetterHelperImpl(quota, QUOTA$4, i, (short)2);
   }

   public QuotaType insertNewQuota(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QuotaType target = null;
         target = (QuotaType)this.get_store().insert_element_user(QUOTA$4, i);
         return target;
      }
   }

   public QuotaType addNewQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QuotaType target = null;
         target = (QuotaType)this.get_store().add_element_user(QUOTA$4);
         return target;
      }
   }

   public void removeQuota(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUOTA$4, i);
      }
   }

   public TemplateType[] getTemplateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TEMPLATE$6, targetList);
         TemplateType[] result = new TemplateType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TemplateType getTemplateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType target = null;
         target = (TemplateType)this.get_store().find_element_user(TEMPLATE$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTemplateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TEMPLATE$6);
      }
   }

   public void setTemplateArray(TemplateType[] templateArray) {
      this.check_orphaned();
      this.arraySetterHelper(templateArray, TEMPLATE$6);
   }

   public void setTemplateArray(int i, TemplateType template) {
      this.generatedSetterHelperImpl(template, TEMPLATE$6, i, (short)2);
   }

   public TemplateType insertNewTemplate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType target = null;
         target = (TemplateType)this.get_store().insert_element_user(TEMPLATE$6, i);
         return target;
      }
   }

   public TemplateType addNewTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType target = null;
         target = (TemplateType)this.get_store().add_element_user(TEMPLATE$6);
         return target;
      }
   }

   public void removeTemplate(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TEMPLATE$6, i);
      }
   }

   public DestinationKeyType[] getDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESTINATIONKEY$8, targetList);
         DestinationKeyType[] result = new DestinationKeyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DestinationKeyType getDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationKeyType target = null;
         target = (DestinationKeyType)this.get_store().find_element_user(DESTINATIONKEY$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONKEY$8);
      }
   }

   public void setDestinationKeyArray(DestinationKeyType[] destinationKeyArray) {
      this.check_orphaned();
      this.arraySetterHelper(destinationKeyArray, DESTINATIONKEY$8);
   }

   public void setDestinationKeyArray(int i, DestinationKeyType destinationKey) {
      this.generatedSetterHelperImpl(destinationKey, DESTINATIONKEY$8, i, (short)2);
   }

   public DestinationKeyType insertNewDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationKeyType target = null;
         target = (DestinationKeyType)this.get_store().insert_element_user(DESTINATIONKEY$8, i);
         return target;
      }
   }

   public DestinationKeyType addNewDestinationKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationKeyType target = null;
         target = (DestinationKeyType)this.get_store().add_element_user(DESTINATIONKEY$8);
         return target;
      }
   }

   public void removeDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONKEY$8, i);
      }
   }

   public JmsConnectionFactoryType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$10, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(CONNECTIONFACTORY$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORY$10);
      }
   }

   public void setConnectionFactoryArray(JmsConnectionFactoryType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$10);
   }

   public void setConnectionFactoryArray(int i, JmsConnectionFactoryType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$10, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(CONNECTIONFACTORY$10, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(CONNECTIONFACTORY$10);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$10, i);
      }
   }

   public ForeignServerType[] getForeignServerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FOREIGNSERVER$12, targetList);
         ForeignServerType[] result = new ForeignServerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ForeignServerType getForeignServerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignServerType target = null;
         target = (ForeignServerType)this.get_store().find_element_user(FOREIGNSERVER$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfForeignServerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNSERVER$12);
      }
   }

   public void setForeignServerArray(ForeignServerType[] foreignServerArray) {
      this.check_orphaned();
      this.arraySetterHelper(foreignServerArray, FOREIGNSERVER$12);
   }

   public void setForeignServerArray(int i, ForeignServerType foreignServer) {
      this.generatedSetterHelperImpl(foreignServer, FOREIGNSERVER$12, i, (short)2);
   }

   public ForeignServerType insertNewForeignServer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignServerType target = null;
         target = (ForeignServerType)this.get_store().insert_element_user(FOREIGNSERVER$12, i);
         return target;
      }
   }

   public ForeignServerType addNewForeignServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignServerType target = null;
         target = (ForeignServerType)this.get_store().add_element_user(FOREIGNSERVER$12);
         return target;
      }
   }

   public void removeForeignServer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNSERVER$12, i);
      }
   }

   public QueueType[] getQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(QUEUE$14, targetList);
         QueueType[] result = new QueueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QueueType getQueueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueueType target = null;
         target = (QueueType)this.get_store().find_element_user(QUEUE$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUEUE$14);
      }
   }

   public void setQueueArray(QueueType[] queueArray) {
      this.check_orphaned();
      this.arraySetterHelper(queueArray, QUEUE$14);
   }

   public void setQueueArray(int i, QueueType queue) {
      this.generatedSetterHelperImpl(queue, QUEUE$14, i, (short)2);
   }

   public QueueType insertNewQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueueType target = null;
         target = (QueueType)this.get_store().insert_element_user(QUEUE$14, i);
         return target;
      }
   }

   public QueueType addNewQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueueType target = null;
         target = (QueueType)this.get_store().add_element_user(QUEUE$14);
         return target;
      }
   }

   public void removeQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUEUE$14, i);
      }
   }

   public TopicType[] getTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(TOPIC$16, targetList);
         TopicType[] result = new TopicType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public TopicType getTopicArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicType target = null;
         target = (TopicType)this.get_store().find_element_user(TOPIC$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOPIC$16);
      }
   }

   public void setTopicArray(TopicType[] topicArray) {
      this.check_orphaned();
      this.arraySetterHelper(topicArray, TOPIC$16);
   }

   public void setTopicArray(int i, TopicType topic) {
      this.generatedSetterHelperImpl(topic, TOPIC$16, i, (short)2);
   }

   public TopicType insertNewTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicType target = null;
         target = (TopicType)this.get_store().insert_element_user(TOPIC$16, i);
         return target;
      }
   }

   public TopicType addNewTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicType target = null;
         target = (TopicType)this.get_store().add_element_user(TOPIC$16);
         return target;
      }
   }

   public void removeTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOPIC$16, i);
      }
   }

   public DistributedQueueType[] getDistributedQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTEDQUEUE$18, targetList);
         DistributedQueueType[] result = new DistributedQueueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DistributedQueueType getDistributedQueueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedQueueType target = null;
         target = (DistributedQueueType)this.get_store().find_element_user(DISTRIBUTEDQUEUE$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDistributedQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTEDQUEUE$18);
      }
   }

   public void setDistributedQueueArray(DistributedQueueType[] distributedQueueArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributedQueueArray, DISTRIBUTEDQUEUE$18);
   }

   public void setDistributedQueueArray(int i, DistributedQueueType distributedQueue) {
      this.generatedSetterHelperImpl(distributedQueue, DISTRIBUTEDQUEUE$18, i, (short)2);
   }

   public DistributedQueueType insertNewDistributedQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedQueueType target = null;
         target = (DistributedQueueType)this.get_store().insert_element_user(DISTRIBUTEDQUEUE$18, i);
         return target;
      }
   }

   public DistributedQueueType addNewDistributedQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedQueueType target = null;
         target = (DistributedQueueType)this.get_store().add_element_user(DISTRIBUTEDQUEUE$18);
         return target;
      }
   }

   public void removeDistributedQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTEDQUEUE$18, i);
      }
   }

   public DistributedTopicType[] getDistributedTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTEDTOPIC$20, targetList);
         DistributedTopicType[] result = new DistributedTopicType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DistributedTopicType getDistributedTopicArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedTopicType target = null;
         target = (DistributedTopicType)this.get_store().find_element_user(DISTRIBUTEDTOPIC$20, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDistributedTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTEDTOPIC$20);
      }
   }

   public void setDistributedTopicArray(DistributedTopicType[] distributedTopicArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributedTopicArray, DISTRIBUTEDTOPIC$20);
   }

   public void setDistributedTopicArray(int i, DistributedTopicType distributedTopic) {
      this.generatedSetterHelperImpl(distributedTopic, DISTRIBUTEDTOPIC$20, i, (short)2);
   }

   public DistributedTopicType insertNewDistributedTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedTopicType target = null;
         target = (DistributedTopicType)this.get_store().insert_element_user(DISTRIBUTEDTOPIC$20, i);
         return target;
      }
   }

   public DistributedTopicType addNewDistributedTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedTopicType target = null;
         target = (DistributedTopicType)this.get_store().add_element_user(DISTRIBUTEDTOPIC$20);
         return target;
      }
   }

   public void removeDistributedTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTEDTOPIC$20, i);
      }
   }

   public UniformDistributedQueueType[] getUniformDistributedQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(UNIFORMDISTRIBUTEDQUEUE$22, targetList);
         UniformDistributedQueueType[] result = new UniformDistributedQueueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UniformDistributedQueueType getUniformDistributedQueueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedQueueType target = null;
         target = (UniformDistributedQueueType)this.get_store().find_element_user(UNIFORMDISTRIBUTEDQUEUE$22, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUniformDistributedQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNIFORMDISTRIBUTEDQUEUE$22);
      }
   }

   public void setUniformDistributedQueueArray(UniformDistributedQueueType[] uniformDistributedQueueArray) {
      this.check_orphaned();
      this.arraySetterHelper(uniformDistributedQueueArray, UNIFORMDISTRIBUTEDQUEUE$22);
   }

   public void setUniformDistributedQueueArray(int i, UniformDistributedQueueType uniformDistributedQueue) {
      this.generatedSetterHelperImpl(uniformDistributedQueue, UNIFORMDISTRIBUTEDQUEUE$22, i, (short)2);
   }

   public UniformDistributedQueueType insertNewUniformDistributedQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedQueueType target = null;
         target = (UniformDistributedQueueType)this.get_store().insert_element_user(UNIFORMDISTRIBUTEDQUEUE$22, i);
         return target;
      }
   }

   public UniformDistributedQueueType addNewUniformDistributedQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedQueueType target = null;
         target = (UniformDistributedQueueType)this.get_store().add_element_user(UNIFORMDISTRIBUTEDQUEUE$22);
         return target;
      }
   }

   public void removeUniformDistributedQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNIFORMDISTRIBUTEDQUEUE$22, i);
      }
   }

   public UniformDistributedTopicType[] getUniformDistributedTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(UNIFORMDISTRIBUTEDTOPIC$24, targetList);
         UniformDistributedTopicType[] result = new UniformDistributedTopicType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UniformDistributedTopicType getUniformDistributedTopicArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedTopicType target = null;
         target = (UniformDistributedTopicType)this.get_store().find_element_user(UNIFORMDISTRIBUTEDTOPIC$24, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUniformDistributedTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNIFORMDISTRIBUTEDTOPIC$24);
      }
   }

   public void setUniformDistributedTopicArray(UniformDistributedTopicType[] uniformDistributedTopicArray) {
      this.check_orphaned();
      this.arraySetterHelper(uniformDistributedTopicArray, UNIFORMDISTRIBUTEDTOPIC$24);
   }

   public void setUniformDistributedTopicArray(int i, UniformDistributedTopicType uniformDistributedTopic) {
      this.generatedSetterHelperImpl(uniformDistributedTopic, UNIFORMDISTRIBUTEDTOPIC$24, i, (short)2);
   }

   public UniformDistributedTopicType insertNewUniformDistributedTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedTopicType target = null;
         target = (UniformDistributedTopicType)this.get_store().insert_element_user(UNIFORMDISTRIBUTEDTOPIC$24, i);
         return target;
      }
   }

   public UniformDistributedTopicType addNewUniformDistributedTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UniformDistributedTopicType target = null;
         target = (UniformDistributedTopicType)this.get_store().add_element_user(UNIFORMDISTRIBUTEDTOPIC$24);
         return target;
      }
   }

   public void removeUniformDistributedTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNIFORMDISTRIBUTEDTOPIC$24, i);
      }
   }

   public SafImportedDestinationsType[] getSafImportedDestinationsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFIMPORTEDDESTINATIONS$26, targetList);
         SafImportedDestinationsType[] result = new SafImportedDestinationsType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafImportedDestinationsType getSafImportedDestinationsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().find_element_user(SAFIMPORTEDDESTINATIONS$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSafImportedDestinationsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFIMPORTEDDESTINATIONS$26);
      }
   }

   public void setSafImportedDestinationsArray(SafImportedDestinationsType[] safImportedDestinationsArray) {
      this.check_orphaned();
      this.arraySetterHelper(safImportedDestinationsArray, SAFIMPORTEDDESTINATIONS$26);
   }

   public void setSafImportedDestinationsArray(int i, SafImportedDestinationsType safImportedDestinations) {
      this.generatedSetterHelperImpl(safImportedDestinations, SAFIMPORTEDDESTINATIONS$26, i, (short)2);
   }

   public SafImportedDestinationsType insertNewSafImportedDestinations(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().insert_element_user(SAFIMPORTEDDESTINATIONS$26, i);
         return target;
      }
   }

   public SafImportedDestinationsType addNewSafImportedDestinations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().add_element_user(SAFIMPORTEDDESTINATIONS$26);
         return target;
      }
   }

   public void removeSafImportedDestinations(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFIMPORTEDDESTINATIONS$26, i);
      }
   }

   public SafRemoteContextType[] getSafRemoteContextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFREMOTECONTEXT$28, targetList);
         SafRemoteContextType[] result = new SafRemoteContextType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafRemoteContextType getSafRemoteContextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().find_element_user(SAFREMOTECONTEXT$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSafRemoteContextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFREMOTECONTEXT$28);
      }
   }

   public void setSafRemoteContextArray(SafRemoteContextType[] safRemoteContextArray) {
      this.check_orphaned();
      this.arraySetterHelper(safRemoteContextArray, SAFREMOTECONTEXT$28);
   }

   public void setSafRemoteContextArray(int i, SafRemoteContextType safRemoteContext) {
      this.generatedSetterHelperImpl(safRemoteContext, SAFREMOTECONTEXT$28, i, (short)2);
   }

   public SafRemoteContextType insertNewSafRemoteContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().insert_element_user(SAFREMOTECONTEXT$28, i);
         return target;
      }
   }

   public SafRemoteContextType addNewSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().add_element_user(SAFREMOTECONTEXT$28);
         return target;
      }
   }

   public void removeSafRemoteContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFREMOTECONTEXT$28, i);
      }
   }

   public SafErrorHandlingType[] getSafErrorHandlingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFERRORHANDLING$30, targetList);
         SafErrorHandlingType[] result = new SafErrorHandlingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafErrorHandlingType getSafErrorHandlingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().find_element_user(SAFERRORHANDLING$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSafErrorHandlingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFERRORHANDLING$30);
      }
   }

   public void setSafErrorHandlingArray(SafErrorHandlingType[] safErrorHandlingArray) {
      this.check_orphaned();
      this.arraySetterHelper(safErrorHandlingArray, SAFERRORHANDLING$30);
   }

   public void setSafErrorHandlingArray(int i, SafErrorHandlingType safErrorHandling) {
      this.generatedSetterHelperImpl(safErrorHandling, SAFERRORHANDLING$30, i, (short)2);
   }

   public SafErrorHandlingType insertNewSafErrorHandling(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().insert_element_user(SAFERRORHANDLING$30, i);
         return target;
      }
   }

   public SafErrorHandlingType addNewSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().add_element_user(SAFERRORHANDLING$30);
         return target;
      }
   }

   public void removeSafErrorHandling(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFERRORHANDLING$30, i);
      }
   }
}
