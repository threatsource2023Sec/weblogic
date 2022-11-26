package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.DefaultConnectionPropertiesDocument;
import javax.xml.namespace.QName;

public class DefaultConnectionPropertiesDocumentImpl extends XmlComplexContentImpl implements DefaultConnectionPropertiesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTCONNECTIONPROPERTIES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-connection-properties");

   public DefaultConnectionPropertiesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionDefinitionPropertiesType getDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().find_element_user(DEFAULTCONNECTIONPROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType defaultConnectionProperties) {
      this.generatedSetterHelperImpl(defaultConnectionProperties, DEFAULTCONNECTIONPROPERTIES$0, 0, (short)1);
   }

   public ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().add_element_user(DEFAULTCONNECTIONPROPERTIES$0);
         return target;
      }
   }
}
