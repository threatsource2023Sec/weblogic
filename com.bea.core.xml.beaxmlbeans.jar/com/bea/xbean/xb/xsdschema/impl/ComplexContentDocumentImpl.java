package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.ComplexContentDocument;
import com.bea.xbean.xb.xsdschema.ComplexRestrictionType;
import com.bea.xbean.xb.xsdschema.ExtensionType;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class ComplexContentDocumentImpl extends XmlComplexContentImpl implements ComplexContentDocument {
   private static final QName COMPLEXCONTENT$0 = new QName("http://www.w3.org/2001/XMLSchema", "complexContent");

   public ComplexContentDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ComplexContentDocument.ComplexContent getComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().find_element_user((QName)COMPLEXCONTENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setComplexContent(ComplexContentDocument.ComplexContent complexContent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().find_element_user((QName)COMPLEXCONTENT$0, 0);
         if (target == null) {
            target = (ComplexContentDocument.ComplexContent)this.get_store().add_element_user(COMPLEXCONTENT$0);
         }

         target.set(complexContent);
      }
   }

   public ComplexContentDocument.ComplexContent addNewComplexContent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ComplexContentDocument.ComplexContent target = null;
         target = (ComplexContentDocument.ComplexContent)this.get_store().add_element_user(COMPLEXCONTENT$0);
         return target;
      }
   }

   public static class ComplexContentImpl extends AnnotatedImpl implements ComplexContentDocument.ComplexContent {
      private static final QName RESTRICTION$0 = new QName("http://www.w3.org/2001/XMLSchema", "restriction");
      private static final QName EXTENSION$2 = new QName("http://www.w3.org/2001/XMLSchema", "extension");
      private static final QName MIXED$4 = new QName("", "mixed");

      public ComplexContentImpl(SchemaType sType) {
         super(sType);
      }

      public ComplexRestrictionType getRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ComplexRestrictionType target = null;
            target = (ComplexRestrictionType)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(RESTRICTION$0) != 0;
         }
      }

      public void setRestriction(ComplexRestrictionType restriction) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ComplexRestrictionType target = null;
            target = (ComplexRestrictionType)this.get_store().find_element_user((QName)RESTRICTION$0, 0);
            if (target == null) {
               target = (ComplexRestrictionType)this.get_store().add_element_user(RESTRICTION$0);
            }

            target.set(restriction);
         }
      }

      public ComplexRestrictionType addNewRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ComplexRestrictionType target = null;
            target = (ComplexRestrictionType)this.get_store().add_element_user(RESTRICTION$0);
            return target;
         }
      }

      public void unsetRestriction() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)RESTRICTION$0, 0);
         }
      }

      public ExtensionType getExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ExtensionType target = null;
            target = (ExtensionType)this.get_store().find_element_user((QName)EXTENSION$2, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(EXTENSION$2) != 0;
         }
      }

      public void setExtension(ExtensionType extension) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ExtensionType target = null;
            target = (ExtensionType)this.get_store().find_element_user((QName)EXTENSION$2, 0);
            if (target == null) {
               target = (ExtensionType)this.get_store().add_element_user(EXTENSION$2);
            }

            target.set(extension);
         }
      }

      public ExtensionType addNewExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ExtensionType target = null;
            target = (ExtensionType)this.get_store().add_element_user(EXTENSION$2);
            return target;
         }
      }

      public void unsetExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element((QName)EXTENSION$2, 0);
         }
      }

      public boolean getMixed() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MIXED$4);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetMixed() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_attribute_user(MIXED$4);
            return target;
         }
      }

      public boolean isSetMixed() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(MIXED$4) != null;
         }
      }

      public void setMixed(boolean mixed) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MIXED$4);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(MIXED$4);
            }

            target.setBooleanValue(mixed);
         }
      }

      public void xsetMixed(XmlBoolean mixed) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_attribute_user(MIXED$4);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_attribute_user(MIXED$4);
            }

            target.set(mixed);
         }
      }

      public void unsetMixed() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(MIXED$4);
         }
      }
   }
}
