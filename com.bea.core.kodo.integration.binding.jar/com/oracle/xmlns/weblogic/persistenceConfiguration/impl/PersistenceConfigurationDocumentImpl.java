package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceConfigurationDocument;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceConfigurationType;
import javax.xml.namespace.QName;

public class PersistenceConfigurationDocumentImpl extends XmlComplexContentImpl implements PersistenceConfigurationDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PERSISTENCECONFIGURATION$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "persistence-configuration");

   public PersistenceConfigurationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public PersistenceConfigurationType getPersistenceConfiguration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceConfigurationType target = null;
         target = (PersistenceConfigurationType)this.get_store().find_element_user(PERSISTENCECONFIGURATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPersistenceConfiguration(PersistenceConfigurationType persistenceConfiguration) {
      this.generatedSetterHelperImpl(persistenceConfiguration, PERSISTENCECONFIGURATION$0, 0, (short)1);
   }

   public PersistenceConfigurationType addNewPersistenceConfiguration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceConfigurationType target = null;
         target = (PersistenceConfigurationType)this.get_store().add_element_user(PERSISTENCECONFIGURATION$0);
         return target;
      }
   }
}
