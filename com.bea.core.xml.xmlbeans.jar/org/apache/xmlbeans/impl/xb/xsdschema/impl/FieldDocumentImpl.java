package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.impl.values.JavaStringHolderEx;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;

public class FieldDocumentImpl extends XmlComplexContentImpl implements FieldDocument {
   private static final QName FIELD$0 = new QName("http://www.w3.org/2001/XMLSchema", "field");

   public FieldDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public FieldDocument.Field getField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().find_element_user((QName)FIELD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setField(FieldDocument.Field field) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().find_element_user((QName)FIELD$0, 0);
         if (target == null) {
            target = (FieldDocument.Field)this.get_store().add_element_user(FIELD$0);
         }

         target.set(field);
      }
   }

   public FieldDocument.Field addNewField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldDocument.Field target = null;
         target = (FieldDocument.Field)this.get_store().add_element_user(FIELD$0);
         return target;
      }
   }

   public static class FieldImpl extends AnnotatedImpl implements FieldDocument.Field {
      private static final QName XPATH$0 = new QName("", "xpath");

      public FieldImpl(SchemaType sType) {
         super(sType);
      }

      public String getXpath() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(XPATH$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public FieldDocument.Field.Xpath xgetXpath() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FieldDocument.Field.Xpath target = null;
            target = (FieldDocument.Field.Xpath)this.get_store().find_attribute_user(XPATH$0);
            return target;
         }
      }

      public void setXpath(String xpath) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(XPATH$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(XPATH$0);
            }

            target.setStringValue(xpath);
         }
      }

      public void xsetXpath(FieldDocument.Field.Xpath xpath) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FieldDocument.Field.Xpath target = null;
            target = (FieldDocument.Field.Xpath)this.get_store().find_attribute_user(XPATH$0);
            if (target == null) {
               target = (FieldDocument.Field.Xpath)this.get_store().add_attribute_user(XPATH$0);
            }

            target.set(xpath);
         }
      }

      public static class XpathImpl extends JavaStringHolderEx implements FieldDocument.Field.Xpath {
         public XpathImpl(SchemaType sType) {
            super(sType, false);
         }

         protected XpathImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }
}
