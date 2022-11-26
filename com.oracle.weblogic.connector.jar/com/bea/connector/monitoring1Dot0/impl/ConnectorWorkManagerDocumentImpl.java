package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectorWorkManagerDocument;
import com.bea.connector.monitoring1Dot0.ConnectorWorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class ConnectorWorkManagerDocumentImpl extends XmlComplexContentImpl implements ConnectorWorkManagerDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTORWORKMANAGER$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connector-work-manager");

   public ConnectorWorkManagerDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectorWorkManagerType getConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorWorkManagerType target = null;
         target = (ConnectorWorkManagerType)this.get_store().find_element_user(CONNECTORWORKMANAGER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectorWorkManager(ConnectorWorkManagerType connectorWorkManager) {
      this.generatedSetterHelperImpl(connectorWorkManager, CONNECTORWORKMANAGER$0, 0, (short)1);
   }

   public ConnectorWorkManagerType addNewConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorWorkManagerType target = null;
         target = (ConnectorWorkManagerType)this.get_store().add_element_user(CONNECTORWORKMANAGER$0);
         return target;
      }
   }
}
