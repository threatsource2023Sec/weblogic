package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlLanguage;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.DocumentationDocument;

public class DocumentationDocumentImpl extends XmlComplexContentImpl implements DocumentationDocument {
   private static final QName DOCUMENTATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "documentation");

   public DocumentationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DocumentationDocument.Documentation getDocumentation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DocumentationDocument.Documentation target = null;
         target = (DocumentationDocument.Documentation)this.get_store().find_element_user((QName)DOCUMENTATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDocumentation(DocumentationDocument.Documentation documentation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DocumentationDocument.Documentation target = null;
         target = (DocumentationDocument.Documentation)this.get_store().find_element_user((QName)DOCUMENTATION$0, 0);
         if (target == null) {
            target = (DocumentationDocument.Documentation)this.get_store().add_element_user(DOCUMENTATION$0);
         }

         target.set(documentation);
      }
   }

   public DocumentationDocument.Documentation addNewDocumentation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DocumentationDocument.Documentation target = null;
         target = (DocumentationDocument.Documentation)this.get_store().add_element_user(DOCUMENTATION$0);
         return target;
      }
   }

   public static class DocumentationImpl extends XmlComplexContentImpl implements DocumentationDocument.Documentation {
      private static final QName SOURCE$0 = new QName("", "source");
      private static final QName LANG$2 = new QName("http://www.w3.org/XML/1998/namespace", "lang");

      public DocumentationImpl(SchemaType sType) {
         super(sType);
      }

      public String getSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SOURCE$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SOURCE$0);
            return target;
         }
      }

      public boolean isSetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(SOURCE$0) != null;
         }
      }

      public void setSource(String source) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SOURCE$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SOURCE$0);
            }

            target.setStringValue(source);
         }
      }

      public void xsetSource(XmlAnyURI source) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SOURCE$0);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SOURCE$0);
            }

            target.set(source);
         }
      }

      public void unsetSource() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(SOURCE$0);
         }
      }

      public String getLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(LANG$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlLanguage xgetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlLanguage target = null;
            target = (XmlLanguage)this.get_store().find_attribute_user(LANG$2);
            return target;
         }
      }

      public boolean isSetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(LANG$2) != null;
         }
      }

      public void setLang(String lang) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(LANG$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(LANG$2);
            }

            target.setStringValue(lang);
         }
      }

      public void xsetLang(XmlLanguage lang) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlLanguage target = null;
            target = (XmlLanguage)this.get_store().find_attribute_user(LANG$2);
            if (target == null) {
               target = (XmlLanguage)this.get_store().add_attribute_user(LANG$2);
            }

            target.set(lang);
         }
      }

      public void unsetLang() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(LANG$2);
         }
      }
   }
}
