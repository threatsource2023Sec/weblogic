package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ClientSafType;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultPersistentStoreType;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultSafAgentType;
import com.oracle.xmlns.weblogic.weblogicJms.JmsConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicJms.SafErrorHandlingType;
import com.oracle.xmlns.weblogic.weblogicJms.SafImportedDestinationsType;
import com.oracle.xmlns.weblogic.weblogicJms.SafRemoteContextType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ClientSafTypeImpl extends XmlComplexContentImpl implements ClientSafType {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENTSTORE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "persistent-store");
   private static final QName SAFAGENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-agent");
   private static final QName CONNECTIONFACTORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "connection-factory");
   private static final QName SAFIMPORTEDDESTINATIONS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-imported-destinations");
   private static final QName SAFREMOTECONTEXT$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-remote-context");
   private static final QName SAFERRORHANDLING$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-error-handling");

   public ClientSafTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DefaultPersistentStoreType getPersistentStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultPersistentStoreType target = null;
         target = (DefaultPersistentStoreType)this.get_store().find_element_user(PERSISTENTSTORE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTORE$0) != 0;
      }
   }

   public void setPersistentStore(DefaultPersistentStoreType persistentStore) {
      this.generatedSetterHelperImpl(persistentStore, PERSISTENTSTORE$0, 0, (short)1);
   }

   public DefaultPersistentStoreType addNewPersistentStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultPersistentStoreType target = null;
         target = (DefaultPersistentStoreType)this.get_store().add_element_user(PERSISTENTSTORE$0);
         return target;
      }
   }

   public void unsetPersistentStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTORE$0, 0);
      }
   }

   public DefaultSafAgentType getSafAgent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSafAgentType target = null;
         target = (DefaultSafAgentType)this.get_store().find_element_user(SAFAGENT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSafAgent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFAGENT$2) != 0;
      }
   }

   public void setSafAgent(DefaultSafAgentType safAgent) {
      this.generatedSetterHelperImpl(safAgent, SAFAGENT$2, 0, (short)1);
   }

   public DefaultSafAgentType addNewSafAgent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSafAgentType target = null;
         target = (DefaultSafAgentType)this.get_store().add_element_user(SAFAGENT$2);
         return target;
      }
   }

   public void unsetSafAgent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFAGENT$2, 0);
      }
   }

   public JmsConnectionFactoryType[] getConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONFACTORY$4, targetList);
         JmsConnectionFactoryType[] result = new JmsConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JmsConnectionFactoryType getConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().find_element_user(CONNECTIONFACTORY$4, i);
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
         return this.get_store().count_elements(CONNECTIONFACTORY$4);
      }
   }

   public void setConnectionFactoryArray(JmsConnectionFactoryType[] connectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionFactoryArray, CONNECTIONFACTORY$4);
   }

   public void setConnectionFactoryArray(int i, JmsConnectionFactoryType connectionFactory) {
      this.generatedSetterHelperImpl(connectionFactory, CONNECTIONFACTORY$4, i, (short)2);
   }

   public JmsConnectionFactoryType insertNewConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().insert_element_user(CONNECTIONFACTORY$4, i);
         return target;
      }
   }

   public JmsConnectionFactoryType addNewConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsConnectionFactoryType target = null;
         target = (JmsConnectionFactoryType)this.get_store().add_element_user(CONNECTIONFACTORY$4);
         return target;
      }
   }

   public void removeConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY$4, i);
      }
   }

   public SafImportedDestinationsType[] getSafImportedDestinationsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFIMPORTEDDESTINATIONS$6, targetList);
         SafImportedDestinationsType[] result = new SafImportedDestinationsType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafImportedDestinationsType getSafImportedDestinationsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().find_element_user(SAFIMPORTEDDESTINATIONS$6, i);
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
         return this.get_store().count_elements(SAFIMPORTEDDESTINATIONS$6);
      }
   }

   public void setSafImportedDestinationsArray(SafImportedDestinationsType[] safImportedDestinationsArray) {
      this.check_orphaned();
      this.arraySetterHelper(safImportedDestinationsArray, SAFIMPORTEDDESTINATIONS$6);
   }

   public void setSafImportedDestinationsArray(int i, SafImportedDestinationsType safImportedDestinations) {
      this.generatedSetterHelperImpl(safImportedDestinations, SAFIMPORTEDDESTINATIONS$6, i, (short)2);
   }

   public SafImportedDestinationsType insertNewSafImportedDestinations(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().insert_element_user(SAFIMPORTEDDESTINATIONS$6, i);
         return target;
      }
   }

   public SafImportedDestinationsType addNewSafImportedDestinations() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafImportedDestinationsType target = null;
         target = (SafImportedDestinationsType)this.get_store().add_element_user(SAFIMPORTEDDESTINATIONS$6);
         return target;
      }
   }

   public void removeSafImportedDestinations(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFIMPORTEDDESTINATIONS$6, i);
      }
   }

   public SafRemoteContextType[] getSafRemoteContextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFREMOTECONTEXT$8, targetList);
         SafRemoteContextType[] result = new SafRemoteContextType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafRemoteContextType getSafRemoteContextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().find_element_user(SAFREMOTECONTEXT$8, i);
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
         return this.get_store().count_elements(SAFREMOTECONTEXT$8);
      }
   }

   public void setSafRemoteContextArray(SafRemoteContextType[] safRemoteContextArray) {
      this.check_orphaned();
      this.arraySetterHelper(safRemoteContextArray, SAFREMOTECONTEXT$8);
   }

   public void setSafRemoteContextArray(int i, SafRemoteContextType safRemoteContext) {
      this.generatedSetterHelperImpl(safRemoteContext, SAFREMOTECONTEXT$8, i, (short)2);
   }

   public SafRemoteContextType insertNewSafRemoteContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().insert_element_user(SAFREMOTECONTEXT$8, i);
         return target;
      }
   }

   public SafRemoteContextType addNewSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafRemoteContextType target = null;
         target = (SafRemoteContextType)this.get_store().add_element_user(SAFREMOTECONTEXT$8);
         return target;
      }
   }

   public void removeSafRemoteContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFREMOTECONTEXT$8, i);
      }
   }

   public SafErrorHandlingType[] getSafErrorHandlingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFERRORHANDLING$10, targetList);
         SafErrorHandlingType[] result = new SafErrorHandlingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafErrorHandlingType getSafErrorHandlingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().find_element_user(SAFERRORHANDLING$10, i);
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
         return this.get_store().count_elements(SAFERRORHANDLING$10);
      }
   }

   public void setSafErrorHandlingArray(SafErrorHandlingType[] safErrorHandlingArray) {
      this.check_orphaned();
      this.arraySetterHelper(safErrorHandlingArray, SAFERRORHANDLING$10);
   }

   public void setSafErrorHandlingArray(int i, SafErrorHandlingType safErrorHandling) {
      this.generatedSetterHelperImpl(safErrorHandling, SAFERRORHANDLING$10, i, (short)2);
   }

   public SafErrorHandlingType insertNewSafErrorHandling(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().insert_element_user(SAFERRORHANDLING$10, i);
         return target;
      }
   }

   public SafErrorHandlingType addNewSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafErrorHandlingType target = null;
         target = (SafErrorHandlingType)this.get_store().add_element_user(SAFERRORHANDLING$10);
         return target;
      }
   }

   public void removeSafErrorHandling(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFERRORHANDLING$10, i);
      }
   }
}
