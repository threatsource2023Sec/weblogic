package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ModuleNameDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ModuleNameDocumentImpl extends XmlComplexContentImpl implements ModuleNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName MODULENAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "module-name");

   public ModuleNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULENAME$0, 0);
         return target;
      }
   }

   public void setModuleName(String moduleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MODULENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MODULENAME$0);
         }

         target.setStringValue(moduleName);
      }
   }

   public void xsetModuleName(XmlString moduleName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MODULENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MODULENAME$0);
         }

         target.set(moduleName);
      }
   }
}
