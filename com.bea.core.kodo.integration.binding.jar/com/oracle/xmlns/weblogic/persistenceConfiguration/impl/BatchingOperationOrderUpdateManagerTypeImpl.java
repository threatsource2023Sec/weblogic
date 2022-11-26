package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.BatchingOperationOrderUpdateManagerType;
import javax.xml.namespace.QName;

public class BatchingOperationOrderUpdateManagerTypeImpl extends UpdateManagerTypeImpl implements BatchingOperationOrderUpdateManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXIMIZEBATCHSIZE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "maximize-batch-size");

   public BatchingOperationOrderUpdateManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getMaximizeBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMIZEBATCHSIZE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMaximizeBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MAXIMIZEBATCHSIZE$0, 0);
         return target;
      }
   }

   public boolean isSetMaximizeBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIMIZEBATCHSIZE$0) != 0;
      }
   }

   public void setMaximizeBatchSize(boolean maximizeBatchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMIZEBATCHSIZE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXIMIZEBATCHSIZE$0);
         }

         target.setBooleanValue(maximizeBatchSize);
      }
   }

   public void xsetMaximizeBatchSize(XmlBoolean maximizeBatchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MAXIMIZEBATCHSIZE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MAXIMIZEBATCHSIZE$0);
         }

         target.set(maximizeBatchSize);
      }
   }

   public void unsetMaximizeBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIMIZEBATCHSIZE$0, 0);
      }
   }
}
