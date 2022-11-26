package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.PropertiesDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class PropertiesDocumentImpl extends XmlComplexContentImpl implements PropertiesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "properties");

   public PropertiesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigPropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setProperties(ConfigPropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$0, 0, (short)1);
   }

   public ConfigPropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$0);
         return target;
      }
   }
}
