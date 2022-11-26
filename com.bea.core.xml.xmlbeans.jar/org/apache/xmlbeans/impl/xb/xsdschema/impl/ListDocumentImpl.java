package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlQName;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;

public class ListDocumentImpl extends XmlComplexContentImpl implements ListDocument {
   private static final QName LIST$0 = new QName("http://www.w3.org/2001/XMLSchema", "list");

   public ListDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ListDocument.List getList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().find_element_user((QName)LIST$0, 0);
         return target == null ? null : target;
      }
   }

   public void setList(ListDocument.List list) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().find_element_user((QName)LIST$0, 0);
         if (target == null) {
            target = (ListDocument.List)this.get_store().add_element_user(LIST$0);
         }

         target.set(list);
      }
   }

   public ListDocument.List addNewList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ListDocument.List target = null;
         target = (ListDocument.List)this.get_store().add_element_user(LIST$0);
         return target;
      }
   }

   public static class ListImpl extends AnnotatedImpl implements ListDocument.List {
      private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
      private static final QName ITEMTYPE$2 = new QName("", "itemType");

      public ListImpl(SchemaType sType) {
         super(sType);
      }

      public LocalSimpleType getSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SIMPLETYPE$0) != 0;
         }
      }

      public void setSimpleType(LocalSimpleType simpleType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
            if (target == null) {
               target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
            }

            target.set(simpleType);
         }
      }

      public LocalSimpleType addNewSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LocalSimpleType target = null;
            target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
            return target;
         }
      }

      public void unsetSimpleType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)SIMPLETYPE$0, 0);
         }
      }

      public QName getItemType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ITEMTYPE$2);
            return target == null ? null : target.getQNameValue();
         }
      }

      public XmlQName xgetItemType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(ITEMTYPE$2);
            return target;
         }
      }

      public boolean isSetItemType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(ITEMTYPE$2) != null;
         }
      }

      public void setItemType(QName itemType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(ITEMTYPE$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(ITEMTYPE$2);
            }

            target.setQNameValue(itemType);
         }
      }

      public void xsetItemType(XmlQName itemType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlQName target = null;
            target = (XmlQName)this.get_store().find_attribute_user(ITEMTYPE$2);
            if (target == null) {
               target = (XmlQName)this.get_store().add_attribute_user(ITEMTYPE$2);
            }

            target.set(itemType);
         }
      }

      public void unsetItemType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(ITEMTYPE$2);
         }
      }
   }
}
