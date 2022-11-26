package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionPropertiesDocument;
import javax.xml.namespace.QName;

public class ConnectionPropertiesDocumentImpl extends XmlComplexContentImpl implements ConnectionPropertiesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONPROPERTIES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-properties");

   public ConnectionPropertiesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionDefinitionPropertiesType getConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionProperties(ConnectionDefinitionPropertiesType connectionProperties) {
      this.generatedSetterHelperImpl(connectionProperties, CONNECTIONPROPERTIES$0, 0, (short)1);
   }

   public ConnectionDefinitionPropertiesType addNewConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().add_element_user(CONNECTIONPROPERTIES$0);
         return target;
      }
   }
}
