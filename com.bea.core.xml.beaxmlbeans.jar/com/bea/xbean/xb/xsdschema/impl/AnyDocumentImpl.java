package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.AllNNI;
import com.bea.xbean.xb.xsdschema.AnyDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNonNegativeInteger;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class AnyDocumentImpl extends XmlComplexContentImpl implements AnyDocument {
   private static final QName ANY$0 = new QName("http://www.w3.org/2001/XMLSchema", "any");

   public AnyDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AnyDocument.Any getAny() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().find_element_user((QName)ANY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAny(AnyDocument.Any any) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().find_element_user((QName)ANY$0, 0);
         if (target == null) {
            target = (AnyDocument.Any)this.get_store().add_element_user(ANY$0);
         }

         target.set(any);
      }
   }

   public AnyDocument.Any addNewAny() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnyDocument.Any target = null;
         target = (AnyDocument.Any)this.get_store().add_element_user(ANY$0);
         return target;
      }
   }

   public static class AnyImpl extends WildcardImpl implements AnyDocument.Any {
      private static final QName MINOCCURS$0 = new QName("", "minOccurs");
      private static final QName MAXOCCURS$2 = new QName("", "maxOccurs");

      public AnyImpl(SchemaType sType) {
         super(sType);
      }

      public BigInteger getMinOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$0);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(MINOCCURS$0);
            }

            return target == null ? null : target.getBigIntegerValue();
         }
      }

      public XmlNonNegativeInteger xgetMinOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlNonNegativeInteger target = null;
            target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$0);
            if (target == null) {
               target = (XmlNonNegativeInteger)this.get_default_attribute_value(MINOCCURS$0);
            }

            return target;
         }
      }

      public boolean isSetMinOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(MINOCCURS$0) != null;
         }
      }

      public void setMinOccurs(BigInteger minOccurs) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(MINOCCURS$0);
            }

            target.setBigIntegerValue(minOccurs);
         }
      }

      public void xsetMinOccurs(XmlNonNegativeInteger minOccurs) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlNonNegativeInteger target = null;
            target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$0);
            if (target == null) {
               target = (XmlNonNegativeInteger)this.get_store().add_attribute_user(MINOCCURS$0);
            }

            target.set(minOccurs);
         }
      }

      public void unsetMinOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(MINOCCURS$0);
         }
      }

      public Object getMaxOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$2);
            if (target == null) {
               target = (SimpleValue)this.get_default_attribute_value(MAXOCCURS$2);
            }

            return target == null ? null : target.getObjectValue();
         }
      }

      public AllNNI xgetMaxOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AllNNI target = null;
            target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$2);
            if (target == null) {
               target = (AllNNI)this.get_default_attribute_value(MAXOCCURS$2);
            }

            return target;
         }
      }

      public boolean isSetMaxOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(MAXOCCURS$2) != null;
         }
      }

      public void setMaxOccurs(Object maxOccurs) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(MAXOCCURS$2);
            }

            target.setObjectValue(maxOccurs);
         }
      }

      public void xsetMaxOccurs(AllNNI maxOccurs) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AllNNI target = null;
            target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$2);
            if (target == null) {
               target = (AllNNI)this.get_store().add_attribute_user(MAXOCCURS$2);
            }

            target.set(maxOccurs);
         }
      }

      public void unsetMaxOccurs() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(MAXOCCURS$2);
         }
      }
   }
}
