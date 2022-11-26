package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.KeyrefDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlQName;
import javax.xml.namespace.QName;

public class KeyrefDocumentImpl extends XmlComplexContentImpl implements KeyrefDocument {
   private static final QName KEYREF$0 = new QName("http://www.w3.org/2001/XMLSchema", "keyref");

   public KeyrefDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public KeyrefDocument.Keyref getKeyref() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().find_element_user((QName)KEYREF$0, 0);
         return target == null ? null : target;
      }
   }

   public void setKeyref(KeyrefDocument.Keyref keyref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().find_element_user((QName)KEYREF$0, 0);
         if (target == null) {
            target = (KeyrefDocument.Keyref)this.get_store().add_element_user(KEYREF$0);
         }

         target.set(keyref);
      }
   }

   public KeyrefDocument.Keyref addNewKeyref() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().add_element_user(KEYREF$0);
         return target;
      }
   }

   public static class KeyrefImpl extends KeybaseImpl implements KeyrefDocument.Keyref {
      private static final QName REFER$0 = new QName("", "refer");

      public KeyrefImpl(SchemaType sType) {
         super(sType);
      }

      public QName getRefer() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(REFER$0);
            return target == null ? null : target.getQNameValue();
         }
      }

      public XmlQName xgetRefer() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(REFER$0);
            return target;
         }
      }

      public void setRefer(QName refer) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(REFER$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(REFER$0);
            }

            target.setQNameValue(refer);
         }
      }

      public void xsetRefer(XmlQName refer) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(REFER$0);
            if (target == null) {
               target = (XmlQName)this.get_store().add_attribute_user(REFER$0);
            }

            target.set(refer);
         }
      }
   }
}
