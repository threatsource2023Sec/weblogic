package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExecutionContextNameProviderDocument;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExecutionContextNameProviderType;
import javax.xml.namespace.QName;

public class ExecutionContextNameProviderDocumentImpl extends XmlComplexContentImpl implements ExecutionContextNameProviderDocument {
   private static final long serialVersionUID = 1L;
   private static final QName EXECUTIONCONTEXTNAMEPROVIDER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "execution-context-name-provider");

   public ExecutionContextNameProviderDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ExecutionContextNameProviderType getExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExecutionContextNameProviderType target = null;
         target = (ExecutionContextNameProviderType)this.get_store().find_element_user(EXECUTIONCONTEXTNAMEPROVIDER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setExecutionContextNameProvider(ExecutionContextNameProviderType executionContextNameProvider) {
      this.generatedSetterHelperImpl(executionContextNameProvider, EXECUTIONCONTEXTNAMEPROVIDER$0, 0, (short)1);
   }

   public ExecutionContextNameProviderType addNewExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExecutionContextNameProviderType target = null;
         target = (ExecutionContextNameProviderType)this.get_store().add_element_user(EXECUTIONCONTEXTNAMEPROVIDER$0);
         return target;
      }
   }
}
