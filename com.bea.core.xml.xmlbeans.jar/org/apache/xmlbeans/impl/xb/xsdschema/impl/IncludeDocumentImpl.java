package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;

public class IncludeDocumentImpl extends XmlComplexContentImpl implements IncludeDocument {
   private static final QName INCLUDE$0 = new QName("http://www.w3.org/2001/XMLSchema", "include");

   public IncludeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public IncludeDocument.Include getInclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IncludeDocument.Include target = null;
         target = (IncludeDocument.Include)this.get_store().find_element_user((QName)INCLUDE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setInclude(IncludeDocument.Include include) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IncludeDocument.Include target = null;
         target = (IncludeDocument.Include)this.get_store().find_element_user((QName)INCLUDE$0, 0);
         if (target == null) {
            target = (IncludeDocument.Include)this.get_store().add_element_user(INCLUDE$0);
         }

         target.set(include);
      }
   }

   public IncludeDocument.Include addNewInclude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IncludeDocument.Include target = null;
         target = (IncludeDocument.Include)this.get_store().add_element_user(INCLUDE$0);
         return target;
      }
   }

   public static class IncludeImpl extends AnnotatedImpl implements IncludeDocument.Include {
      private static final QName SCHEMALOCATION$0 = new QName("", "schemaLocation");

      public IncludeImpl(SchemaType sType) {
         super(sType);
      }

      public String getSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$0);
            return target;
         }
      }

      public void setSchemaLocation(String schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SCHEMALOCATION$0);
            }

            target.setStringValue(schemaLocation);
         }
      }

      public void xsetSchemaLocation(XmlAnyURI schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$0);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SCHEMALOCATION$0);
            }

            target.set(schemaLocation);
         }
      }
   }
}
