package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JmsRemoteCommitProviderType;
import javax.xml.namespace.QName;

public class JmsRemoteCommitProviderTypeImpl extends RemoteCommitProviderTypeImpl implements JmsRemoteCommitProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName TOPIC$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "topic");
   private static final QName EXCEPTIONRECONNECTATTEMPTS$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "exception-reconnect-attempts");
   private static final QName TOPICCONNECTIONFACTORY$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "topic-connection-factory");

   public JmsRemoteCommitProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOPIC$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPIC$0, 0);
         return target;
      }
   }

   public boolean isNilTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPIC$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOPIC$0) != 0;
      }
   }

   public void setTopic(String topic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOPIC$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TOPIC$0);
         }

         target.setStringValue(topic);
      }
   }

   public void xsetTopic(XmlString topic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPIC$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOPIC$0);
         }

         target.set(topic);
      }
   }

   public void setNilTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPIC$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOPIC$0);
         }

         target.setNil();
      }
   }

   public void unsetTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOPIC$0, 0);
      }
   }

   public int getExceptionReconnectAttempts() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCEPTIONRECONNECTATTEMPTS$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetExceptionReconnectAttempts() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(EXCEPTIONRECONNECTATTEMPTS$2, 0);
         return target;
      }
   }

   public boolean isSetExceptionReconnectAttempts() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCEPTIONRECONNECTATTEMPTS$2) != 0;
      }
   }

   public void setExceptionReconnectAttempts(int exceptionReconnectAttempts) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXCEPTIONRECONNECTATTEMPTS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXCEPTIONRECONNECTATTEMPTS$2);
         }

         target.setIntValue(exceptionReconnectAttempts);
      }
   }

   public void xsetExceptionReconnectAttempts(XmlInt exceptionReconnectAttempts) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(EXCEPTIONRECONNECTATTEMPTS$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(EXCEPTIONRECONNECTATTEMPTS$2);
         }

         target.set(exceptionReconnectAttempts);
      }
   }

   public void unsetExceptionReconnectAttempts() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCEPTIONRECONNECTATTEMPTS$2, 0);
      }
   }

   public String getTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         return target;
      }
   }

   public boolean isNilTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOPICCONNECTIONFACTORY$4) != 0;
      }
   }

   public void setTopicConnectionFactory(String topicConnectionFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TOPICCONNECTIONFACTORY$4);
         }

         target.setStringValue(topicConnectionFactory);
      }
   }

   public void xsetTopicConnectionFactory(XmlString topicConnectionFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOPICCONNECTIONFACTORY$4);
         }

         target.set(topicConnectionFactory);
      }
   }

   public void setNilTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TOPICCONNECTIONFACTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TOPICCONNECTIONFACTORY$4);
         }

         target.setNil();
      }
   }

   public void unsetTopicConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOPICCONNECTIONFACTORY$4, 0);
      }
   }
}
