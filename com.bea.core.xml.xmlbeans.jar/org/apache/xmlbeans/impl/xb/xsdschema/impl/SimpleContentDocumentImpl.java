package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleContentDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleExtensionType;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleRestrictionType;

public class SimpleContentDocumentImpl extends XmlComplexContentImpl implements SimpleContentDocument {
   private static final QName SIMPLECONTENT$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleContent");

   public SimpleContentDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public SimpleContentDocument.SimpleContent getSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().find_element_user((QName)SIMPLECONTENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSimpleContent(SimpleContentDocument.SimpleContent simpleContent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().find_element_user((QName)SIMPLECONTENT$0, 0);
         if (target == null) {
            target = (SimpleContentDocument.SimpleContent)this.get_store().add_element_user(SIMPLECONTENT$0);
         }

         target.set(simpleContent);
      }
   }

   public SimpleContentDocument.SimpleContent addNewSimpleContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentDocument.SimpleContent target = null;
         target = (SimpleContentDocument.SimpleContent)this.get_store().add_element_user(SIMPLECONTENT$0);
         return target;
      }
   }

   public static class SimpleContentImpl extends AnnotatedImpl implements SimpleContentDocument.SimpleContent {
      private static final QName RESTRICTION$0 = new QName("http://www.w3.org/2001/XMLSchema", "restriction");
      private static final QName EXTENSION$2 = new QName("http://www.w3.org/2001/XMLSchema", "extension");

      public SimpleContentImpl(SchemaType sType) {
         super(sType);
      }

      public SimpleRestrictionType getRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleRestrictionType target = null;
            target = (SimpleRestrictionType)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(RESTRICTION$0) != 0;
         }
      }

      public void setRestriction(SimpleRestrictionType restriction) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleRestrictionType target = null;
            target = (SimpleRestrictionType)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
            if (target == null) {
               target = (SimpleRestrictionType)this.get_store().add_element_user(RESTRICTION$0);
            }

            target.set(restriction);
         }
      }

      public SimpleRestrictionType addNewRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleRestrictionType target = null;
            target = (SimpleRestrictionType)this.get_store().add_element_user(RESTRICTION$0);
            return target;
         }
      }

      public void unsetRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)RESTRICTION$0, 0);
         }
      }

      public SimpleExtensionType getExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleExtensionType target = null;
            target = (SimpleExtensionType)this.get_store().find_element_user((QName)EXTENSION$2, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(EXTENSION$2) != 0;
         }
      }

      public void setExtension(SimpleExtensionType extension) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleExtensionType target = null;
            target = (SimpleExtensionType)this.get_store().find_element_user((QName)EXTENSION$2, 0);
            if (target == null) {
               target = (SimpleExtensionType)this.get_store().add_element_user(EXTENSION$2);
            }

            target.set(extension);
         }
      }

      public SimpleExtensionType addNewExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleExtensionType target = null;
            target = (SimpleExtensionType)this.get_store().add_element_user(EXTENSION$2);
            return target;
         }
      }

      public void unsetExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)EXTENSION$2, 0);
         }
      }
   }
}
