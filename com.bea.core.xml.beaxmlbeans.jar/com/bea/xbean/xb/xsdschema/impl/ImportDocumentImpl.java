package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.ImportDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnyURI;
import javax.xml.namespace.QName;

public class ImportDocumentImpl extends XmlComplexContentImpl implements ImportDocument {
   private static final QName IMPORT$0 = new QName("http://www.w3.org/2001/XMLSchema", "import");

   public ImportDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ImportDocument.Import getImport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ImportDocument.Import target = null;
         target = (ImportDocument.Import)this.get_store().find_element_user((QName)IMPORT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setImport(ImportDocument.Import ximport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ImportDocument.Import target = null;
         target = (ImportDocument.Import)this.get_store().find_element_user((QName)IMPORT$0, 0);
         if (target == null) {
            target = (ImportDocument.Import)this.get_store().add_element_user(IMPORT$0);
         }

         target.set(ximport);
      }
   }

   public ImportDocument.Import addNewImport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ImportDocument.Import target = null;
         target = (ImportDocument.Import)this.get_store().add_element_user(IMPORT$0);
         return target;
      }
   }

   public static class ImportImpl extends AnnotatedImpl implements ImportDocument.Import {
      private static final QName NAMESPACE$0 = new QName("", "namespace");
      private static final QName SCHEMALOCATION$2 = new QName("", "schemaLocation");

      public ImportImpl(SchemaType sType) {
         super(sType);
      }

      public String getNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(NAMESPACE$0);
            return target;
         }
      }

      public boolean isSetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(NAMESPACE$0) != null;
         }
      }

      public void setNamespace(String namespace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAMESPACE$0);
            }

            target.setStringValue(namespace);
         }
      }

      public void xsetNamespace(XmlAnyURI namespace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(NAMESPACE$0);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(NAMESPACE$0);
            }

            target.set(namespace);
         }
      }

      public void unsetNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(NAMESPACE$0);
         }
      }

      public String getSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$2);
            return target;
         }
      }

      public boolean isSetSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(SCHEMALOCATION$2) != null;
         }
      }

      public void setSchemaLocation(String schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SCHEMALOCATION$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SCHEMALOCATION$2);
            }

            target.setStringValue(schemaLocation);
         }
      }

      public void xsetSchemaLocation(XmlAnyURI schemaLocation) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SCHEMALOCATION$2);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SCHEMALOCATION$2);
            }

            target.set(schemaLocation);
         }
      }

      public void unsetSchemaLocation() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(SCHEMALOCATION$2);
         }
      }
   }
}
