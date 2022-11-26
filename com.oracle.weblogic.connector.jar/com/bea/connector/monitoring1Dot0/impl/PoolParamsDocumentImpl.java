package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionPoolParamsType;
import com.bea.connector.monitoring1Dot0.PoolParamsDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class PoolParamsDocumentImpl extends XmlComplexContentImpl implements PoolParamsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName POOLPARAMS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "pool-params");

   public PoolParamsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionPoolParamsType getPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionPoolParamsType target = null;
         target = (ConnectionPoolParamsType)this.get_store().find_element_user(POOLPARAMS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPoolParams(ConnectionPoolParamsType poolParams) {
      this.generatedSetterHelperImpl(poolParams, POOLPARAMS$0, 0, (short)1);
   }

   public ConnectionPoolParamsType addNewPoolParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionPoolParamsType target = null;
         target = (ConnectionPoolParamsType)this.get_store().add_element_user(POOLPARAMS$0);
         return target;
      }
   }
}
