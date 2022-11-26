package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.WorkManagerDocument;
import com.bea.connector.monitoring1Dot0.WorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class WorkManagerDocumentImpl extends XmlComplexContentImpl implements WorkManagerDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WORKMANAGER$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "work-manager");

   public WorkManagerDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WorkManagerType getWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWorkManager(WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$0, 0, (short)1);
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$0);
         return target;
      }
   }
}
