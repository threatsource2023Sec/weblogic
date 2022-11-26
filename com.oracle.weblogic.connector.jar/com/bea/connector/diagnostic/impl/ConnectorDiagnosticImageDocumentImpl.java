package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.ConnectorDiagnosticImageDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlObject;
import javax.xml.namespace.QName;

public class ConnectorDiagnosticImageDocumentImpl extends XmlComplexContentImpl implements ConnectorDiagnosticImageDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTORDIAGNOSTICIMAGE$0 = new QName("http://www.bea.com/connector/diagnostic", "ConnectorDiagnosticImage");

   public ConnectorDiagnosticImageDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public XmlObject getConnectorDiagnosticImage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlObject target = null;
         target = (XmlObject)this.get_store().find_element_user(CONNECTORDIAGNOSTICIMAGE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectorDiagnosticImage(XmlObject connectorDiagnosticImage) {
      this.generatedSetterHelperImpl(connectorDiagnosticImage, CONNECTORDIAGNOSTICIMAGE$0, 0, (short)1);
   }

   public XmlObject addNewConnectorDiagnosticImage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlObject target = null;
         target = (XmlObject)this.get_store().add_element_user(CONNECTORDIAGNOSTICIMAGE$0);
         return target;
      }
   }
}
