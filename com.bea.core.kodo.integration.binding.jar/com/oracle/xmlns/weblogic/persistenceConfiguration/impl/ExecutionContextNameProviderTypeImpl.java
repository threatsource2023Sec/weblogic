package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.StackExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TransactionNameExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.UserObjectExecutionContextNameProviderType;
import javax.xml.namespace.QName;

public class ExecutionContextNameProviderTypeImpl extends XmlComplexContentImpl implements ExecutionContextNameProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName STACKEXECUTIONCONTEXTNAMEPROVIDER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "stack-execution-context-name-provider");
   private static final QName TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "transaction-name-execution-context-name-provider");
   private static final QName USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "user-object-execution-context-name-provider");

   public ExecutionContextNameProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public StackExecutionContextNameProviderType getStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STACKEXECUTIONCONTEXTNAMEPROVIDER$0) != 0;
      }
   }

   public void setStackExecutionContextNameProvider(StackExecutionContextNameProviderType stackExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(stackExecutionContextNameProvider, STACKEXECUTIONCONTEXTNAMEPROVIDER$0, 0, (short)1);
   }

   public StackExecutionContextNameProviderType addNewStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().add_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$0);
         return target;
      }
   }

   public void setNilStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$0, 0);
         if (target == null) {
            target = (StackExecutionContextNameProviderType)this.get_store().add_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$0);
         }

         target.setNil();
      }
   }

   public void unsetStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STACKEXECUTIONCONTEXTNAMEPROVIDER$0, 0);
      }
   }

   public TransactionNameExecutionContextNameProviderType getTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2) != 0;
      }
   }

   public void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderType transactionNameExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(transactionNameExecutionContextNameProvider, TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2, 0, (short)1);
   }

   public TransactionNameExecutionContextNameProviderType addNewTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().add_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2);
         return target;
      }
   }

   public void setNilTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2, 0);
         if (target == null) {
            target = (TransactionNameExecutionContextNameProviderType)this.get_store().add_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2);
         }

         target.setNil();
      }
   }

   public void unsetTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$2, 0);
      }
   }

   public UserObjectExecutionContextNameProviderType getUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4) != 0;
      }
   }

   public void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderType userObjectExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(userObjectExecutionContextNameProvider, USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4, 0, (short)1);
   }

   public UserObjectExecutionContextNameProviderType addNewUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().add_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4);
         return target;
      }
   }

   public void setNilUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4, 0);
         if (target == null) {
            target = (UserObjectExecutionContextNameProviderType)this.get_store().add_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4);
         }

         target.setNil();
      }
   }

   public void unsetUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$4, 0);
      }
   }
}
