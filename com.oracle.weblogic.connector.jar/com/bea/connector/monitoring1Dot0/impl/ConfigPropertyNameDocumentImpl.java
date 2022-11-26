package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConfigPropertyNameDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ConfigPropertyNameDocumentImpl extends XmlComplexContentImpl implements ConfigPropertyNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONFIGPROPERTYNAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "config-property-name");

   public ConfigPropertyNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGPROPERTYNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigPropertyName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGPROPERTYNAME$0, 0);
         return target;
      }
   }

   public void setConfigPropertyName(String configPropertyName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGPROPERTYNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGPROPERTYNAME$0);
         }

         target.setStringValue(configPropertyName);
      }
   }

   public void xsetConfigPropertyName(XmlString configPropertyName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGPROPERTYNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGPROPERTYNAME$0);
         }

         target.set(configPropertyName);
      }
   }
}
