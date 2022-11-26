package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.LinkRefDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class LinkRefDocumentImpl extends XmlComplexContentImpl implements LinkRefDocument {
   private static final long serialVersionUID = 1L;
   private static final QName LINKREF$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "link-ref");

   public LinkRefDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LINKREF$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LINKREF$0, 0);
         return target;
      }
   }

   public void setLinkRef(String linkRef) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LINKREF$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LINKREF$0);
         }

         target.setStringValue(linkRef);
      }
   }

   public void xsetLinkRef(XmlString linkRef) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LINKREF$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LINKREF$0);
         }

         target.set(linkRef);
      }
   }
}
