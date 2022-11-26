package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DestinationKeyType;
import com.oracle.xmlns.weblogic.weblogicJms.KeyTypeType;
import com.oracle.xmlns.weblogic.weblogicJms.SortOrderType;
import javax.xml.namespace.QName;

public class DestinationKeyTypeImpl extends NamedEntityTypeImpl implements DestinationKeyType {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "property");
   private static final QName KEYTYPE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "key-type");
   private static final QName SORTORDER$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "sort-order");

   public DestinationKeyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROPERTY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROPERTY$0, 0);
         return target;
      }
   }

   public boolean isNilProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROPERTY$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTY$0) != 0;
      }
   }

   public void setProperty(String property) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROPERTY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROPERTY$0);
         }

         target.setStringValue(property);
      }
   }

   public void xsetProperty(XmlString property) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROPERTY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PROPERTY$0);
         }

         target.set(property);
      }
   }

   public void setNilProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROPERTY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PROPERTY$0);
         }

         target.setNil();
      }
   }

   public void unsetProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$0, 0);
      }
   }

   public KeyTypeType.Enum getKeyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEYTYPE$2, 0);
         return target == null ? null : (KeyTypeType.Enum)target.getEnumValue();
      }
   }

   public KeyTypeType xgetKeyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyTypeType target = null;
         target = (KeyTypeType)this.get_store().find_element_user(KEYTYPE$2, 0);
         return target;
      }
   }

   public boolean isSetKeyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEYTYPE$2) != 0;
      }
   }

   public void setKeyType(KeyTypeType.Enum keyType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEYTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(KEYTYPE$2);
         }

         target.setEnumValue(keyType);
      }
   }

   public void xsetKeyType(KeyTypeType keyType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyTypeType target = null;
         target = (KeyTypeType)this.get_store().find_element_user(KEYTYPE$2, 0);
         if (target == null) {
            target = (KeyTypeType)this.get_store().add_element_user(KEYTYPE$2);
         }

         target.set(keyType);
      }
   }

   public void unsetKeyType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEYTYPE$2, 0);
      }
   }

   public SortOrderType.Enum getSortOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SORTORDER$4, 0);
         return target == null ? null : (SortOrderType.Enum)target.getEnumValue();
      }
   }

   public SortOrderType xgetSortOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SortOrderType target = null;
         target = (SortOrderType)this.get_store().find_element_user(SORTORDER$4, 0);
         return target;
      }
   }

   public boolean isSetSortOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SORTORDER$4) != 0;
      }
   }

   public void setSortOrder(SortOrderType.Enum sortOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SORTORDER$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SORTORDER$4);
         }

         target.setEnumValue(sortOrder);
      }
   }

   public void xsetSortOrder(SortOrderType sortOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SortOrderType target = null;
         target = (SortOrderType)this.get_store().find_element_user(SORTORDER$4, 0);
         if (target == null) {
            target = (SortOrderType)this.get_store().add_element_user(SORTORDER$4);
         }

         target.set(sortOrder);
      }
   }

   public void unsetSortOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SORTORDER$4, 0);
      }
   }
}
