package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.VersionDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class VersionDocumentImpl extends XmlComplexContentImpl implements VersionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName VERSION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "version");

   public VersionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$0, 0);
         return target;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSION$0);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSION$0);
         }

         target.set(version);
      }
   }
}
