package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertyType;
import com.oracle.xmlns.weblogic.weblogicConnector.PropertyDocument;
import javax.xml.namespace.QName;

public class PropertyDocumentImpl extends XmlComplexContentImpl implements PropertyDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "property");

   public PropertyDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigPropertyType getProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(PROPERTY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setProperty(ConfigPropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$0, 0, (short)1);
   }

   public ConfigPropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(PROPERTY$0);
         return target;
      }
   }
}
