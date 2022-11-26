package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.DefaultPropertiesDocument;
import javax.xml.namespace.QName;

public class DefaultPropertiesDocumentImpl extends XmlComplexContentImpl implements DefaultPropertiesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTPROPERTIES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-properties");

   public DefaultPropertiesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigPropertiesType getDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(DEFAULTPROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDefaultProperties(ConfigPropertiesType defaultProperties) {
      this.generatedSetterHelperImpl(defaultProperties, DEFAULTPROPERTIES$0, 0, (short)1);
   }

   public ConfigPropertiesType addNewDefaultProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(DEFAULTPROPERTIES$0);
         return target;
      }
   }
}
