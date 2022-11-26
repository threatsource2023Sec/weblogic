package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.ConnectorDocument;
import com.sun.java.xml.ns.j2Ee.ConnectorType;
import javax.xml.namespace.QName;

public class ConnectorDocumentImpl extends XmlComplexContentImpl implements ConnectorDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTOR$0 = new QName("http://java.sun.com/xml/ns/j2ee", "connector");

   public ConnectorDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectorType getConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorType target = null;
         target = (ConnectorType)this.get_store().find_element_user(CONNECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnector(ConnectorType connector) {
      this.generatedSetterHelperImpl(connector, CONNECTOR$0, 0, (short)1);
   }

   public ConnectorType addNewConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorType target = null;
         target = (ConnectorType)this.get_store().add_element_user(CONNECTOR$0);
         return target;
      }
   }
}
